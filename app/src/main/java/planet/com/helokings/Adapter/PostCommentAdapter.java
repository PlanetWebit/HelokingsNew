package planet.com.helokings.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import planet.com.helokings.Pojo.LikeUnlikeModel;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.AllpsotaadpetrItemBinding;
import planet.com.helokings.databinding.CommentLayoutBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder> {
    private Context context;
    String[] postPojoArrayList;

    public PostCommentAdapter(Context context, String[] postPojoArrayList) {
        this.context = context;
        this.postPojoArrayList = postPojoArrayList;
    }

    @NonNull
    @Override
    public PostCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentAdapter.ViewHolder holder, int position) {

        if(position%2==0) {
            holder.binding.recomments.setLayoutManager(new LinearLayoutManager(context));
            holder.binding.recomments.setHasFixedSize(true);
            holder.binding.recomments.setAdapter(new PostRecommentAdapter(context, new String[]{"", "", "", "", ""}));
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 20, 0);
        holder.binding.lineLike.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return postPojoArrayList.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CommentLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommentLayoutBinding.bind(itemView);
        }
    }


}
