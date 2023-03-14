package planet.com.helokings.VideoStreamingActivity.audioseatmodule.Adapter;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.zego.zegoexpress.entity.ZegoUser;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.HomeData.DataItem;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveNowAdapter extends RecyclerView.Adapter<LiveNowAdapter.ViewHolder> {
    Context context;
    List<ZegoUser> liveNowModels;
    private UserSharePreferancess userSharePreferancess;

    public LiveNowAdapter(Context context,List<ZegoUser> liveNowModels) {
        this.context = context;
        this.liveNowModels = liveNowModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_now_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZegoUser liveNowModel = liveNowModels.get(position);


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
                                holder.live_user.setImageResource(R.drawable.avtar);
                            } else {
                                  Glide.with(context).load(module.getData().getImage()).placeholder(R.drawable.user).into(holder.live_user);
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
        return liveNowModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView live_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            live_user = itemView.findViewById(R.id.main_iv_avtar);
        }
    }


    ResponseProfile module;
    String image, frame;


}
