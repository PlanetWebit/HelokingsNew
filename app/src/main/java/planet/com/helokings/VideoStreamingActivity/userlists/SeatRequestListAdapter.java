package planet.com.helokings.VideoStreamingActivity.userlists;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.zego.zegoexpress.entity.ZegoUser;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.AdminSeatListAdapter;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.ZegoSpeakerSeatModel;
import planet.com.helokings.VideoStreamingActivity.userlists.RoomMemberList.DataItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatRequestListAdapter extends RecyclerView.Adapter<SeatRequestListAdapter.SeatRquestListHolder> {
    Context context;
    List<ZegoUser> liveNowModels =new ArrayList<>();


     String authtoken;String userId;String roomid;boolean islocaluserhost;
    public ApproveSeatClicklistener onrequestseatClickListnener = null;


    public static List<SeatRquestListHolder> holderList = new ArrayList<>();


    public SeatRequestListAdapter(Context context, List<ZegoUser> liveNowModels, String authtoken, String userId, String roomid, boolean islocaluserhost) {
        this.context = context;
        this.liveNowModels = liveNowModels;
        this.authtoken = authtoken;
        this.userId = userId;
        this.roomid = roomid;
        this.islocaluserhost = islocaluserhost;
    }

    @NonNull
    @Override
    public SeatRquestListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_member_list, parent, false);
        return new SeatRquestListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatRquestListHolder holder, int position) {
        ZegoUser liveNowModel = liveNowModels.get(position);

        holder.name.setText(liveNowModel.userID+liveNowModel.userName);

        //holder.sn.setText((position+1)+"");

if (islocaluserhost){
    holder.approve.setVisibility(View.VISIBLE);
    holder.delete.setVisibility(View.VISIBLE);
}

        holder.approve.setOnClickListener(v -> {
            if (onrequestseatClickListnener != null) {

                onrequestseatClickListnener.onClick(liveNowModel.userID,liveNowModel.userName);

            }
        });
        holder.delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  changeStatus(liveNowModel.getId(),"2");
                    }
                }
        );

    }
    public void onrequestseatClickListnener(ApproveSeatClicklistener itemListener) {
        this.onrequestseatClickListnener = itemListener;
    }
    public interface ApproveSeatClicklistener {

        void onClick(String userId,String username);
    }

    @Override
    public int getItemCount() {
        return liveNowModels.size();
    }

    public class SeatRquestListHolder extends RecyclerView.ViewHolder {
        CircleImageView live_user;
        TextView name,type,level,sn;
        ImageView videoFrame,approve,delete;



        public SeatRquestListHolder(@NonNull View itemView) {
            super(itemView);

            live_user = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.tv_user_name);
            type = itemView.findViewById(R.id.type);
            videoFrame = itemView.findViewById(R.id.videoFrame);
            approve = itemView.findViewById(R.id.approve);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    public void setSeatList(List<ZegoUser> seatList, Activity context) {
        this.liveNowModels = seatList;
        this.context = context;

        holderList.clear();
    }

}
