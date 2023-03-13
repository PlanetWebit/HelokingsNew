package planet.com.helokings.VideoStreamingActivity.chat;


import android.content.Context;
import android.util.Log;
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

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.helokings.R;


/**
 * Show all the information sent
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageHolder> {

    List<ChatMessagePojo> messageList;

    public MessageListAdapter(List<ChatMessagePojo> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_messagevideo, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.setIsRecyclable(false);
        ChatMessagePojo message = messageList.get(position);

         Context context = holder.itemView.getContext();
        holder.tvSendMessage.setText(message.getMessage());
        holder.tvUserName.setText(message.getName());

        Log.e("testrec",""+message.getMessage()) ;
//        if(message.getName().equalsIgnoreCase("")||message.getName()==null) {
//            if (isHostMessage) {
//                holder.tvUserName.setText(message.getName());
//            }else{
//                holder.tvUserName.setText(message.getName());
//            }
//
//        }else{
//            if (isHostMessage) {
//                holder.tvUserName.setText(message.getName());
//            }else{
//                holder.tvUserName.setText(message.getName());
//            }
//
//        }
//        if (isHostMessage){
//            holder.ownerblock.setVisibility(View.VISIBLE);
//
//        }



        if(message.getImage()!=null&&!message.getImage().equalsIgnoreCase("null")){
            Glide.with(context).load(message.getImage()).placeholder(R.drawable.people).into(holder.profileImage);
        }


        if(message.getFrame()!=null&&!message.getFrame().equalsIgnoreCase("null")){
            Glide.with(context).load(message.getFrame()).into(holder.frameImage);
        }


        if (message.getName().trim().equals("Gift"))
        {
             Glide.with(context).load(message.getImage()).placeholder(R.drawable.people).into(holder.profileImage);

        }

//        holder.textBubble.setBackgroundDrawable(FunctionClass.getImageDrawable());


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageHolder extends RecyclerView.ViewHolder {

        public TextView tvSendMessage;
        public TextView ownerblock;
        public TextView tvUserName;
        private LinearLayout textBubble;
        private CircleImageView profileImage;
        private ImageView frameImage;
        private RelativeLayout rOccupied;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            tvSendMessage = itemView.findViewById(R.id.tv_send_message);
            ownerblock = itemView.findViewById(R.id.ownerblock);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            textBubble = itemView.findViewById(R.id.textBubble);
            profileImage = itemView.findViewById(R.id.profileImage);
            frameImage = itemView.findViewById(R.id.videoFrame);
            rOccupied = itemView.findViewById(R.id.rOccupied);
        }
    }
}
