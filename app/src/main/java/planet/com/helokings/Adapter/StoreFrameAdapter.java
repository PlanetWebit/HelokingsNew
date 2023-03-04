package planet.com.helokings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import planet.com.helokings.Pojo.StoreModelPojo;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FramItemBinding;

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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.BottamDialogStyle);
                bottomSheetDialog.setContentView(R.layout.frame_bottom_sheet_item);
                bottomSheetDialog.show();

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
}
