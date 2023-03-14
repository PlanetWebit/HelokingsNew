package planet.com.helokings.Adapter;

import android.content.Context;
import android.graphics.Color;
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

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import planet.com.helokings.Fragment.MeFragment;
import planet.com.helokings.Pojo.StoreModelPojo;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.roomUserDetails.LoginModule;
import planet.com.helokings.databinding.FramItemBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreFrameAdapter extends RecyclerView.Adapter<StoreFrameAdapter.ViewHolder> {
    private Context context;
    ArrayList<StoreModelPojo> data;

    public StoreFrameAdapter(Context context, ArrayList<StoreModelPojo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public StoreFrameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fram_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreFrameAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(data.get(position).getImage()).into(holder.framItemBinding.ivframe);
        holder.framItemBinding.tvFrameValue.setText(data.get(position).getAmount());
        holder.framItemBinding.tvFrameTime.setText("/" + data.get(position).getTotal_days() + " days");
        holder.framItemBinding.frameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.MyDialogStyle);
                TextView tvcolse;
                RelativeLayout tvbalance;

                bottomSheetDialog.setContentView(R.layout.frame_bottom_sheet_item);
                bottomSheetDialog.show();

                tvcolse = bottomSheetDialog.findViewById(R.id.tvclose);
                tvbalance = bottomSheetDialog.findViewById(R.id.rl_balance);
                ImageView image = bottomSheetDialog.findViewById(R.id.image);
                TextView userName = bottomSheetDialog.findViewById(R.id.userName);
                TextView tvWeek = bottomSheetDialog.findViewById(R.id.tvWeek);
                TextView tvMonth = bottomSheetDialog.findViewById(R.id.tvMonth);
                TextView tvSix = bottomSheetDialog.findViewById(R.id.tvSix);
                TextView tvTwelve = bottomSheetDialog.findViewById(R.id.tvTwelve);
                TextView btnMsg = bottomSheetDialog.findViewById(R.id.btnMsg);

                LinearLayout lWeek = bottomSheetDialog.findViewById(R.id.lWeek);
                LinearLayout lMonth = bottomSheetDialog.findViewById(R.id.lMonth);
                LinearLayout lSix = bottomSheetDialog.findViewById(R.id.lSix);
                LinearLayout lTwelve = bottomSheetDialog.findViewById(R.id.lTwelve);


                TextView coins = bottomSheetDialog.findViewById(R.id.coins);

                Glide.with(context).load(data.get(position).getImage()).into(image);
                coins.setText(data.get(position).getAmount()+" Coins");
                btnMsg.setText("Buy Now ("+data.get(position).getAmount()+" Coins)");
                userName.setText(Comman.getInstance().getName());

                int days=Integer.parseInt(data.get(position).getTotal_days());
                if(days/7>0){
                    if(days/30>0){
                        if((days/30)/6>0){
                            tvWeek.setTextColor(Color.parseColor("#ffffff"));
                            lWeek.setBackgroundResource(R.drawable.btn_bg);

                            tvMonth.setTextColor(Color.parseColor("#ffffff"));
                            lMonth.setBackgroundResource(R.drawable.btn_bg);

                            tvSix.setTextColor(Color.parseColor("#ffffff"));
                            lSix.setBackgroundResource(R.drawable.btn_bg);

                            tvTwelve.setTextColor(Color.parseColor("#000000"));
                            lTwelve.setBackgroundResource(R.drawable.typebuttonback);
                        }else{
                            tvWeek.setTextColor(Color.parseColor("#ffffff"));
                            lWeek.setBackgroundResource(R.drawable.btn_bg);

                            tvMonth.setTextColor(Color.parseColor("#ffffff"));
                            lMonth.setBackgroundResource(R.drawable.btn_bg);

                            tvSix.setTextColor(Color.parseColor("#000000"));
                            lSix.setBackgroundResource(R.drawable.typebuttonback);

                            tvTwelve.setTextColor(Color.parseColor("#ffffff"));
                            lTwelve.setBackgroundResource(R.drawable.btn_bg);
                        }
                    }else{
                        tvWeek.setTextColor(Color.parseColor("#ffffff"));
                        lWeek.setBackgroundResource(R.drawable.btn_bg);

                        tvMonth.setTextColor(Color.parseColor("#000000"));
                        lMonth.setBackgroundResource(R.drawable.typebuttonback);

                        tvSix.setTextColor(Color.parseColor("#ffffff"));
                        lSix.setBackgroundResource(R.drawable.btn_bg);

                        tvTwelve.setTextColor(Color.parseColor("#ffffff"));
                        lTwelve.setBackgroundResource(R.drawable.btn_bg);
                    }
                }else{
                    tvWeek.setTextColor(Color.parseColor("#000000"));
                    lWeek.setBackgroundResource(R.drawable.typebuttonback);

                    tvMonth.setTextColor(Color.parseColor("#ffffff"));
                    lMonth.setBackgroundResource(R.drawable.btn_bg);

                    tvSix.setTextColor(Color.parseColor("#ffffff"));
                    lSix.setBackgroundResource(R.drawable.btn_bg);

                    tvTwelve.setTextColor(Color.parseColor("#ffffff"));
                    lTwelve.setBackgroundResource(R.drawable.btn_bg);
                }
                tvbalance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buyFrame(data.get(position).getId());
                        bottomSheetDialog.dismiss();
                    }
                });

                tvcolse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FramItemBinding framItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            framItemBinding = FramItemBinding.bind(itemView);
        }
    }

    private void buyFrame(String frameId) {

        Call<LoginModule> moduleCall = RetrofitClient.getInstance().getAllApiResponse().buyFrame(Comman.getInstance().user_id,frameId,"2");
        moduleCall.enqueue(new Callback<LoginModule>() {
            @Override
            public void onResponse(Call<LoginModule> call, Response<LoginModule> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(context, "Frame Purchased", Toast.LENGTH_SHORT).show();
                        if(MeFragment.refresh!=null){
                            MeFragment.refresh.onRefresh();
                        }


                    } else {
                        Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
