package planet.com.helokings.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import planet.com.helokings.Model.StoreModel;
import planet.com.helokings.R;
import planet.com.helokings.databinding.StoreContentItemBinding;

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.ViewHolder> {
    private Context context;
    StoreModel[] storeModels;
    ContentClick contentClick;

    public StoreItemAdapter(Context context, StoreModel[] storeModels, ContentClick contentClick) {
        this.context = context;
        this.storeModels = storeModels;
        this.contentClick = contentClick;
    }

    @NonNull
    @Override
    public StoreItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_content_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.storeContentItemBinding.tvContentTv.setText(storeModels[position].getTv_content());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if(position == 0){
                    Toast.makeText(context, "Frame", Toast.LENGTH_SHORT).show();

                }
                else if(position == 2){
                    Toast.makeText(context, "Entrance Frame", Toast.LENGTH_SHORT).show();
                }
                else if(position == 3){
                    Toast.makeText(context, "Profile Frame", Toast.LENGTH_SHORT).show();

                }else if(position == 4){
                    Toast.makeText(context, "Ribbon Frame", Toast.LENGTH_SHORT).show();
                }
                else if(position == 5){
                    Toast.makeText(context, "Special Frame", Toast.LENGTH_SHORT).show();
                }else if(position == 6){
                    Toast.makeText(context, "Text Frame", Toast.LENGTH_SHORT).show();
                }*/

                contentClick.contentdata(storeModels,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return storeModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StoreContentItemBinding storeContentItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeContentItemBinding = StoreContentItemBinding.bind(itemView);

        }
    }
    public interface ContentClick {
        void contentdata( StoreModel[] storeModels, int position);

    }

}
