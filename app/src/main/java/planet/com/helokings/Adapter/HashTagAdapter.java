package planet.com.helokings.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import planet.com.helokings.Pojo.TagPojo;
import planet.com.helokings.R;
import planet.com.helokings.databinding.HashtagItemBinding;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {
    private Context context;
    ArrayList<TagPojo> data;
    HashItemInterFace hashItemInterFace;

    public HashTagAdapter(Context context, ArrayList<TagPojo> data, HashItemInterFace hashItemInterFace) {
        this.context = context;
        this.data = data;
        this.hashItemInterFace = hashItemInterFace;
    }
    public void filterList(ArrayList<TagPojo> filteredlist) {
        data = filteredlist;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public HashTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hashtag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HashTagAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.hashtagItemBinding.tvHashtag.setText(data.get(position).getName());
        holder.hashtagItemBinding.hashCount.setText(data.get(position).getVideos_count());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashItemInterFace.HasData(data, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public void setHashData(ArrayList<TagPojo> data) {
        data.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HashtagItemBinding hashtagItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hashtagItemBinding = HashtagItemBinding.bind(itemView);
        }

    }

    public interface HashItemInterFace {
        public void HasData(ArrayList<TagPojo> data, int position);

    }
}
