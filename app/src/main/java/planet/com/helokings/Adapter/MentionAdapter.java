package planet.com.helokings.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import planet.com.helokings.Pojo.MentionPojo;
import planet.com.helokings.R;

public class MentionAdapter extends RecyclerView.Adapter<MentionAdapter.ViewHolder> {
    protected Context context;
    ArrayList<MentionPojo> mentionPojo;
    MentionInterface mentionInterface;

    public MentionAdapter(Context context, ArrayList<MentionPojo> mentionPojo, MentionInterface mentionInterface) {
        this.context = context;
        this.mentionPojo = mentionPojo;
        this.mentionInterface = mentionInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mention_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_mention.setText(mentionPojo.get(position).getNick_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mentionInterface.mentiondata(mentionPojo,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mentionPojo.size();
    }

    public void filterList(ArrayList<MentionPojo> mentionfilterlist) {
        mentionPojo = mentionfilterlist;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mention;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mention = itemView.findViewById(R.id.tv_mention);
        }
    }

    public interface MentionInterface {
        public void mentiondata(ArrayList<MentionPojo> data, int position);
    }
    public void MensetData(ArrayList<MentionPojo> mentionPojos){
        mentionPojo.addAll(mentionPojos);
        notifyDataSetChanged();

    }
}
