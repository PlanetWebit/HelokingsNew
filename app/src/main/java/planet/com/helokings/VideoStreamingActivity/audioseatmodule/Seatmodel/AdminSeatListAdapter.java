package planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.helokings.R;

import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.pojos.ResponseOtherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminSeatListAdapter extends RecyclerView.Adapter<AdminSeatListAdapter.SeatListHolder> {

    private static final String TAG = "SeatListAdapter";

    private static final float SOUND_LEVEL_THRESHOLD = 10F;

    public OnadminSeatClickListener onSeatClickListener = null;

    private List<ZegoSpeakerSeatModel> seatList = new ArrayList<>();
    public static List<SeatListHolder> holderList = new ArrayList<>();
    String authtoken , roomid,useridd;

    Activity context;

    public AdminSeatListAdapter(String authtoken, String roomid, String useridd, Activity context) {
          this.authtoken = authtoken;
        this.roomid = roomid;
        this.useridd = useridd;
        this.context = context;
    }

    @NonNull
    @Override
    public SeatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat_user, parent, false);
        return new SeatListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatListHolder holder, int position) {
        ZegoSpeakerSeatModel speakerSeatModel = seatList.get(position);
        holderList.add(holder);
        holder.seatCount.setText("Host");

        holder.itemView.setOnClickListener(v -> {
            if (onSeatClickListener != null) {

                onSeatClickListener.onClick(seatList.get(position),v);
            }
        });

         boolean isSelfUserOnSeat = false;
        for (ZegoSpeakerSeatModel model : seatList) {
             if (useridd.equals(model.userID)) {
                isSelfUserOnSeat = true;
                break;
            }
        }

        switch (speakerSeatModel.status) {
            case Occupied:
                holder.toOnSeatUI();

                if(speakerSeatModel.getImage()!=null&&speakerSeatModel.getImage()!=""){
                    Glide.with(context.getApplicationContext()).load(speakerSeatModel.getImage()).placeholder(R.drawable.avtar).into(holder.occupiedSeat);
                    if(speakerSeatModel.getFrame()!=null&&speakerSeatModel.getFrame()!="") {
                        Glide.with(context.getApplicationContext()).load(speakerSeatModel.getFrame()).into(holder.videoFrame);
                        holder.videoFrame.setVisibility(View.VISIBLE);
                    }else{
                        holder.videoFrame.setVisibility(View.GONE);

                    }

                }else {
                    holder.occupiedSeat.setImageResource(R.drawable.avtar);
                    holder.videoFrame.setVisibility(View.GONE);
                }

                if(speakerSeatModel.room_role!=null) {
                    if (speakerSeatModel.room_role.equalsIgnoreCase("Admin")) {
                        holder.speakerName.setText("Admin");
                        holder.speakerName.setVisibility(View.VISIBLE);
                    } else {
                        holder.speakerName.setVisibility(View.INVISIBLE);
                        holder.speakerName.setText("");
                    }
                }else{
                    holder.speakerName.setVisibility(View.INVISIBLE);
                    holder.speakerName.setText("");
                }
                holder.lSeat.setVisibility(View.GONE);
                holder.ivLock.setVisibility(View.GONE);
                holder.rOccupied.setVisibility(View.VISIBLE);

//                if (UserInfoHelper.isUserOwner(userID)) {
//                    holder.ivOwnerAvatar.setVisibility(View.VISIBLE);
//                    if (LanguageUtils.getSystemLanguage().equals(Locale.CHINESE)) {
//                        holder.ivOwnerAvatar
//                                .setImageDrawable(ResourceUtils.getDrawable(R.drawable.icon_owner_zh));
//                    } else {
//                        holder.ivOwnerAvatar
//                                .setImageDrawable(ResourceUtils.getDrawable(R.drawable.icon_owner));
//                    }
//                } else {
//                    holder.ivOwnerAvatar.setVisibility(View.INVISIBLE);
//                }

                if (canShowSoundWaves(speakerSeatModel.soundLevel)) {
                    holder.ivAvatarTalking.setVisibility(View.VISIBLE);
                } else {
                    holder.ivAvatarTalking.setVisibility(View.INVISIBLE);
                }

                if (!speakerSeatModel.mic) {
                    holder.ivMicOff.setVisibility(View.VISIBLE);
                } else {
                    holder.ivMicOff.setVisibility(View.GONE);
                }

                switch (speakerSeatModel.network) {
                    case Good:
                        holder.ivNetworkStatus.setImageDrawable(
                                ResourceUtils.getDrawable(R.drawable.icon_network_good));
                        break;
                    case Medium:
                        holder.ivNetworkStatus.setImageDrawable(
                                ResourceUtils.getDrawable(R.drawable.icon_network_medium));
                        break;
                    case Bad:
                        holder.ivNetworkStatus.setImageDrawable(
                                ResourceUtils.getDrawable(R.drawable.icon_network_bad));
                        break;
                }
                break;
            case Untaken:
                holder.toOffSeatUI();

                holder.ivLock.setVisibility(View.GONE);
                 holder.lSeat.setVisibility(View.VISIBLE);
                holder.rOccupied.setVisibility(View.GONE);
                holder.speakerName.setVisibility(View.INVISIBLE);

                break;
            case Closed:
                holder.toOffSeatUI();

                holder.ivLock.setVisibility(View.VISIBLE);
                holder.lSeat.setVisibility(View.GONE);
                holder.rOccupied.setVisibility(View.GONE);
                holder.speakerName.setVisibility(View.INVISIBLE);

                break;
        }
    }

    private boolean canShowSoundWaves(float soundLevel) {
        return soundLevel > SOUND_LEVEL_THRESHOLD;
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public void setSeatList(List<ZegoSpeakerSeatModel> seatList,Activity context) {
        this.seatList = seatList;
        this.context = context;

        holderList.clear();
    }

    public void updateUserInfo(ZegoSpeakerSeatModel seatModel,int pos) {
        if(seatModel.status!= ZegoSpeakerSeatStatus.Occupied){
            seatModel.setImage("");
            seatModel.setFrame("");
//            seatModel.setRoom_role("");
            seatList.set(pos, seatModel);
            notifyDataSetChanged();
            notifyItemChanged(pos, new Object());
        }else {
            if(seatModel.getImage()==null){
                    getData(pos, seatModel);
                Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();

            }else{
                if(seatModel.getImage().equalsIgnoreCase("")) {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                    getData(pos, seatModel);
                }else {
                    Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                    seatList.set(pos, seatModel);
                    notifyDataSetChanged();
                    notifyItemChanged(pos, new Object());
                }
            }

        }

    }

    public void setadminOnSeatClickListener(OnadminSeatClickListener itemListener) {
        this.onSeatClickListener = itemListener;
    }

    public interface OnadminSeatClickListener {

        void onClick(ZegoSpeakerSeatModel seatModel,View v);
    }

    static class SeatListHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatarTalking;
        private ImageView ivAvatar;
        private ImageView ivMicOff;
        private ImageView ivOwnerAvatar;
        private ImageView ivNetworkStatus;
        private TextView speakerName;
        private LinearLayout ivLock;
        private ImageView ivSeat;
        private ImageView ivJoin;
        private CircleImageView occupiedSeat;
        private LinearLayout lSeat;
        private TextView seatCount;
        private ImageView videoFrame;
        private RelativeLayout rOccupied;

        public SeatListHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatarTalking = itemView.findViewById(R.id.iv_avatar_talking);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            ivMicOff = itemView.findViewById(R.id.iv_mic_off);
            ivOwnerAvatar = itemView.findViewById(R.id.iv_owner_avatar);
            ivNetworkStatus = itemView.findViewById(R.id.ic_network_status);
            ivLock = itemView.findViewById(R.id.iv_lock);
            ivSeat = itemView.findViewById(R.id.iv_seat);
            ivJoin = itemView.findViewById(R.id.iv_join);
            seatCount = itemView.findViewById(R.id.count);
            occupiedSeat = itemView.findViewById(R.id.occupiedSeat);
            lSeat = itemView.findViewById(R.id.lSeat);
            videoFrame = itemView.findViewById(R.id.videoFrame);
            rOccupied = itemView.findViewById(R.id.rOccupied);
            speakerName = itemView.findViewById(R.id.speakerName);
        }

        void toOnSeatUI() {
//            ivAvatarTalking.setVisibility(View.VISIBLE);
//            ivAvatar.setVisibility(View.VISIBLE);
            ivMicOff.setVisibility(View.VISIBLE);
//            ivOwnerAvatar.setVisibility(View.VISIBLE);
//            ivNetworkStatus.setVisibility(View.VISIBLE);
//            tvUserName.setVisibility(View.VISIBLE);
//            ivLock.setVisibility(View.INVISIBLE);
//            ivSeat.setVisibility(View.INVISIBLE);
//            ivJoin.setVisibility(View.INVISIBLE);

            lSeat.setVisibility(View.VISIBLE);
            ivLock.setVisibility(View.GONE);
            rOccupied.setVisibility(View.GONE);
        }

        void toOffSeatUI() {
//            ivAvatarTalking.setVisibility(View.INVISIBLE);
//            ivAvatar.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
//            ivOwnerAvatar.setVisibility(View.INVISIBLE);
//            ivNetworkStatus.setVisibility(View.INVISIBLE);
//            tvUserName.setVisibility(View.INVISIBLE);
//            ivLock.setVisibility(View.VISIBLE);
//            ivSeat.setVisibility(View.VISIBLE);
//            ivJoin.setVisibility(View.VISIBLE);

            lSeat.setVisibility(View.GONE);
            ivLock.setVisibility(View.VISIBLE);
            rOccupied.setVisibility(View.GONE);
        }
    }

    private void getData(int pos,ZegoSpeakerSeatModel seatModel) {
//        Toast.makeText(context,"Image",Toast.LENGTH_LONG).show();
         Call<ResponseOtherData> moduleCall = RetrofitClient.getInstance().getAllApiResponse().getOtherUserData(authtoken,seatModel.getIid(),roomid);
        moduleCall.enqueue(new Callback<ResponseOtherData>() {
            @Override
            public void onResponse(Call<ResponseOtherData> call, Response<ResponseOtherData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        seatModel.setImage(response.body().getData().getImage());
                        Toast.makeText(context, ""+response.body().getData().getImage(), Toast.LENGTH_SHORT).show();
//                        seatModel.setRoom_role(response.body().getData().getRoom_role());
                        seatList.set(pos, seatModel);
                        notifyDataSetChanged();
                        notifyItemChanged(pos, new Object());
                    } else {
                        Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseOtherData> call, Throwable t) {

                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
