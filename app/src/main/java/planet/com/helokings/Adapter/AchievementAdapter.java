package planet.com.helokings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import planet.com.helokings.Model.AchievementModel;
import planet.com.helokings.R;
import planet.com.helokings.databinding.AchievementItemBinding;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {
    private Context context;
    AchievementModel[] achievementModels;

    public AchievementAdapter(Context context, AchievementModel[] achievementModels) {
        this.context = context;
        this.achievementModels = achievementModels;
    }

    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.achievement_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.ViewHolder holder, int position) {
        holder.achievementItemBinding.ivLogimg.setImageResource(achievementModels[position].getIv_logo());
        holder.achievementItemBinding.tvNewtext.setText(achievementModels[position].getTv_name());

    }

    @Override
    public int getItemCount() {
        return achievementModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AchievementItemBinding achievementItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            achievementItemBinding = AchievementItemBinding.bind(itemView);
        }
    }
}
