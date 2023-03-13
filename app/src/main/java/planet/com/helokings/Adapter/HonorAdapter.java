package planet.com.helokings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import planet.com.helokings.Model.Honormodel;
import planet.com.helokings.R;
import planet.com.helokings.databinding.HonorItemBinding;

public class HonorAdapter extends RecyclerView.Adapter<HonorAdapter.ViewHolder> {
    private Context context;
    Honormodel[] honormodels;

    public HonorAdapter(Context context, Honormodel[] honormodels) {
        this.context = context;
        this.honormodels = honormodels;
    }

    @NonNull
    @Override
    public HonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.honor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HonorAdapter.ViewHolder holder, int position) {
        holder.honorItemBinding.ivLogimg.setImageResource(honormodels[position].getIv_logo());
        holder.honorItemBinding.tvNewtext.setText(honormodels[position].getTv_name());

    }

    @Override
    public int getItemCount() {
        return honormodels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HonorItemBinding honorItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            honorItemBinding = HonorItemBinding.bind(itemView);
        }
    }
}
