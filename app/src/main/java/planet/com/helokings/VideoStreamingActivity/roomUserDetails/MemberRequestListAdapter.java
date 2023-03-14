package planet.com.helokings.VideoStreamingActivity.roomUserDetails;

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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.userlists.RoomMemberList.DataItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberRequestListAdapter extends RecyclerView.Adapter<MemberRequestListAdapter.ViewHolder> {
    Context context;
    List<DataItem> liveNowModels;
 String roomid;

    public MemberRequestListAdapter(Context context, List<DataItem> liveNowModels,String roomid) {
        this.context = context;
        this.liveNowModels = liveNowModels;
        this.roomid = roomid;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_member_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem liveNowModel = liveNowModels.get(position);
        Glide.with(context).load(liveNowModel.getImage()).placeholder(R.drawable.avtar).into(holder.live_user);
        if(!liveNowModel.getFrameImage().equalsIgnoreCase("")) {
            Glide.with(context).load(liveNowModel.getFrameImage()).into(holder.videoFrame);
        }
        holder.name.setText(liveNowModel.getNickName());
       // holder.type.setText(liveNowModel.getRole());

    //    holder.sn.setText((position+1)+"");


        holder.approve.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeStatus(liveNowModel.getId(),"1");
                    }
                }
        );
//
//        holder.delete.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        changeStatus(liveNowModel.getId(),"2");
//                    }
//                }
//        );

    }

    @Override
    public int getItemCount() {
        return liveNowModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView live_user;
        TextView name,type,level;
        ImageView videoFrame,approve,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            live_user = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.tv_user_name);
            type = itemView.findViewById(R.id.type);
            level = itemView.findViewById(R.id.userlevel);
            videoFrame = itemView.findViewById(R.id.videoFrame);
            approve = itemView.findViewById(R.id.approve);
         }
    }


    private void changeStatus(String id,String status) {



         Call<LoginModule> moduleCall = RetrofitClient.getInstance().getAllApiResponse().
                approveMember(Comman.getInstance().getUser_id(),roomid,id,status);
        moduleCall.enqueue(new Callback<LoginModule>() {
            @Override
            public void onResponse(Call<LoginModule> call, Response<LoginModule> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        if(status.equalsIgnoreCase("1")) {
                            Toast.makeText(context, "Request Approved", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Request Rejected", Toast.LENGTH_LONG).show();
                        }
                        if(RequestedMemberFragment.refresh!=null){
                            RequestedMemberFragment.refresh.onRefresh();
                        }
                    } else{

                        Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModule> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
