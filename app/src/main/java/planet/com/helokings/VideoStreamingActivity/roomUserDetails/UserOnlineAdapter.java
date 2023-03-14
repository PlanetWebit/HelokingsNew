package planet.com.helokings.VideoStreamingActivity.roomUserDetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import im.zego.zegoexpress.entity.ZegoUser;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.OnlineItemBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOnlineAdapter extends RecyclerView.Adapter<UserOnlineAdapter.ViewHolder> {
    private Context context;
   List<ZegoUser> userOnlineModels=new ArrayList<>();
    public UserOnlineAdapter(Context context,   List<ZegoUser> userOnlineModels ) {
        this.context = context;
        this.userOnlineModels = userOnlineModels;
    }
    private UserSharePreferancess userSharePreferancess;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.online_item,parent,false);
        return new ViewHolder(view);
    }
    ResponseProfile module;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.itemBinding.userIv.setImageResource(userOnlineModels.getOnlineuser_iv());
        ZegoUser liveNowModel = userOnlineModels.get(position);

        holder.itemBinding.userName.setText(liveNowModel.userName);

        userSharePreferancess = new UserSharePreferancess(context);

        Call<ResponseProfile> mCall = RetrofitClient.getInstance().getAllApiResponse().getUserProfile(
                Comman.getInstance().getUser_id());
        mCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        if (module.getData().getImage() != null) {
                            if (module.getData().getImage().equalsIgnoreCase("")) {
                               holder.itemBinding.userIv.setImageResource(R.drawable.avtar);
                            } else {
                                Glide.with(context).load(module.getData().getImage()).placeholder(R.drawable.user).into(holder.itemBinding.userIv);
                                //  uimage = module.getData().getImage();
                            }
                        }

                        if (module.getData().getFrame_image().equalsIgnoreCase("")) {
                            // videoFrame.setVisibility(View.GONE);
                        } else {
                            //      Glide.with(context).load(module.getData().getFrame_image()).into(videoFrame);
                            //  videoFrame.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (module.getMsg().equalsIgnoreCase("unauthorized login")) {
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("status", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                        Toast.makeText(context, module.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userOnlineModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OnlineItemBinding itemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBinding = OnlineItemBinding.bind(itemView);
        }
    }
}
