package planet.com.helokings.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import planet.com.helokings.Pojo.MomentAllPostPojo;
import planet.com.helokings.R;
import planet.com.helokings.databinding.AllpsotaadpetrItemBinding;

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
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MomentAllPostAdapter.ViewHolder holder, int position) {

        holder.allpsotaadpetrItemBinding.tvAllPostDate.setText(postPojoArrayList.get(position).getCreated());
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
}
