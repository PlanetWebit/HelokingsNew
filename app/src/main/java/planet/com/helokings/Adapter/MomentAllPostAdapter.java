package planet.com.helokings.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import planet.com.helokings.Activity.MomentComment;
import planet.com.helokings.Pojo.LikeUnlikeModel;
import planet.com.helokings.Pojo.MomentAllPostPojo;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.AllpsotaadpetrItemBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MomentAllPostAdapter extends RecyclerView.Adapter<MomentAllPostAdapter.ViewHolder> {
    private Context context;
    ArrayList<MomentAllPostPojo> postPojoArrayList;

    public MomentAllPostAdapter(Context context, ArrayList<MomentAllPostPojo> postPojoArrayList) {
        this.context = context;
        this.postPojoArrayList = postPojoArrayList;
    }

    @NonNull
    @Override
    public MomentAllPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allpsotaadpetr_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomentAllPostAdapter.ViewHolder holder, int position) {


        try{
            Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postPojoArrayList.get(position).getCreated());
            long diff=new Date().getTime()-date.getTime();
            long day = TimeUnit.MILLISECONDS.toDays(diff);
            long hour = TimeUnit.MILLISECONDS.toHours(diff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(diff);
            long second = TimeUnit.MILLISECONDS.toSeconds(diff);
            if(second>60){
                if(minute>60){
                    if(hour>24){
                        holder.allpsotaadpetrItemBinding.tvAllPostDate.setText(day+" Day Ago");
                    }else{
                        holder.allpsotaadpetrItemBinding.tvAllPostDate.setText(hour+" Hour Ago");
                    }
                }else{
                    holder.allpsotaadpetrItemBinding.tvAllPostDate.setText(minute+" Minute Ago");
                }
            }else{
                holder.allpsotaadpetrItemBinding.tvAllPostDate.setText(second+" Second Ago");
            }

        }catch (Exception e){
            holder.allpsotaadpetrItemBinding.tvAllPostDate.setText("0 Day Ago");
        }

        Glide.with(context).load(postPojoArrayList.get(position).getUser_details().getImage()).into(holder.allpsotaadpetrItemBinding.allPostUserIv);
        holder.allpsotaadpetrItemBinding.allPostUserName.setText(postPojoArrayList.get(position).getUser_details().getNick_name());
        holder.allpsotaadpetrItemBinding.allPostHashTag.setText(postPojoArrayList.get(position).getDescription());

        if (postPojoArrayList.get(position).getType().equalsIgnoreCase("image")) {
            holder.allpsotaadpetrItemBinding.muteImg.setVisibility(View.GONE);
            Glide.with(context).load(Uri.parse(postPojoArrayList.get(position).getThum())).into(holder.allpsotaadpetrItemBinding.allPostImage);
        } else if (postPojoArrayList.get(position).getType().equalsIgnoreCase("video")) {
            holder.allpsotaadpetrItemBinding.muteImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(postPojoArrayList.get(position).getGif()).into(holder.allpsotaadpetrItemBinding.allPostImage);
        }

        holder.allpsotaadpetrItemBinding.showOption.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(context, view);
                        popup.getMenuInflater().inflate(R.menu.post_menu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });

                        popup.show();
                    }
                }
        );


        if (postPojoArrayList.get(position).getLike().equalsIgnoreCase("0")) {
            holder.allpsotaadpetrItemBinding.ivPostLike.setImageResource(R.drawable.unfavorite);
            holder.allpsotaadpetrItemBinding.ivPostLike.setImageTintList(ColorStateList.valueOf(Color.parseColor("#5E5E5E")));
        } else {
            holder.allpsotaadpetrItemBinding.ivPostLike.setImageResource(R.drawable.baseline_favorite_24);
            holder.allpsotaadpetrItemBinding.ivPostLike.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));

        }

        holder.allpsotaadpetrItemBinding.postLikeCount.setText(postPojoArrayList.get(position).getLike_count());

        holder.allpsotaadpetrItemBinding.ivPostLike.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (postPojoArrayList.get(position).getLike().equalsIgnoreCase("0")) {
                            postPojoArrayList.get(position).setLike("1");
                            holder.allpsotaadpetrItemBinding.ivPostLike.setImageResource(R.drawable.baseline_favorite_24);
                            holder.allpsotaadpetrItemBinding.ivPostLike.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));

                            int likecount = Integer.parseInt(postPojoArrayList.get(position).getLike_count()) + 1;
                            holder.allpsotaadpetrItemBinding.postLikeCount.setText(likecount + "");
                            postPojoArrayList.get(position).setLike_count(likecount + "");

                        } else {
                            postPojoArrayList.get(position).setLike("0");
                            holder.allpsotaadpetrItemBinding.ivPostLike.setImageResource(R.drawable.unfavorite);
                            int dislikecount = Integer.parseInt(postPojoArrayList.get(position).getLike_count()) - 1;
                            holder.allpsotaadpetrItemBinding.ivPostLike.setImageTintList(ColorStateList.valueOf(Color.parseColor("#5E5E5E")));
                            holder.allpsotaadpetrItemBinding.postLikeCount.setText(dislikecount + "");
                            postPojoArrayList.get(position).setLike_count(dislikecount + "");

                        }
                        updateLike(postPojoArrayList.get(position).getId());

                    }
                }
        );


        holder.allpsotaadpetrItemBinding.ivComment.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context, MomentComment.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return postPojoArrayList.size();
    }

    public void setData(ArrayList<MomentAllPostPojo> data) {
        postPojoArrayList.addAll(data);
        notifyDataSetChanged();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AllpsotaadpetrItemBinding allpsotaadpetrItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allpsotaadpetrItemBinding = AllpsotaadpetrItemBinding.bind(itemView);
        }
    }


    private void updateLike(String id) {
        Call<LikeUnlikeModel> call = RetrofitClient.getInstance().getAllApiResponse().likedata(Comman.getInstance().getUser_id(), id);
        call.enqueue(new Callback<LikeUnlikeModel>() {
            @Override
            public void onResponse(Call<LikeUnlikeModel> call, Response<LikeUnlikeModel> response) {
                LikeUnlikeModel likeUnlikeModel = response.body();
                if (response.isSuccessful()) {
                    if (likeUnlikeModel.getStatus().equalsIgnoreCase("true")) {


                    } else {
                        Log.e("likedata", "" + likeUnlikeModel.getMsg());
                    }

                }

            }

            @Override
            public void onFailure(Call<LikeUnlikeModel> call, Throwable t) {
                Log.e("likedata", "" + t.getMessage());

            }
        });

    }

}
