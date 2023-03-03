package planet.com.helokings.VideoStreamingActivity.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import planet.com.helokings.Pojo.HomeData.DataItem;
import planet.com.helokings.R;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.VideoHostActivity;


public class PartyplrFdataAdapter extends RecyclerView.Adapter<PartyplrFdataAdapter.ViewHolder> {
    Activity context;

    List<DataItem> data;


    public PartyplrFdataAdapter(Activity context, List<DataItem> data) {

        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.roomitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dance.setText(data.get(position).getRoomName());
        holder.tag.setText(data.get(position).getRoomTag());
        holder.desc.setText(data.get(position).getRoomDesc());

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinRommServer(data.get(position).getRoomId(),data.get(position).getRoom_type());
                    }
                }
        );
        Glide.with(context).load(data.get(position).getRoomImage()).into(holder.pp_image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView pp_image;
        TextView dance,tag,desc;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pp_image = itemView.findViewById(R.id.pp_image);
            dance = itemView.findViewById(R.id.dance);
            tag = itemView.findViewById(R.id.tag);
            desc = itemView.findViewById(R.id.desc);
        }
    }


    private static final int REQUEST_CODE_ROOM_ENDED = 0x01;



    private void joinRommServer(String roomID,String type) {
        if (type.equals("party")) {
            Intent intent=new Intent(context, VideoHostActivity.class);
            intent.putExtra("roomid",roomID);
            intent.putExtra("roomname", Comman.getInstance().getUsername());
            intent.putExtra("host",false);
            intent.putExtra("roomtype","party");

            context.startActivity(intent);

        }


        else   if (type.equals("live")){
            Intent intent=new Intent(context, VideoHostActivity.class);
            intent.putExtra("roomid",roomID);
            intent.putExtra("roomname", Comman.getInstance().getUsername());
            intent.putExtra("host",false);
            intent.putExtra("roomtype","live");


            context.startActivity(intent);
        }


    }
}
