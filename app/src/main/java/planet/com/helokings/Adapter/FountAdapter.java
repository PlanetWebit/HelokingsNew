package planet.com.helokings.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import planet.com.helokings.Pojo.Fonts.DataItem;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FontLayoutBinding;

public class FountAdapter extends RecyclerView.Adapter<FountAdapter.ViewHolder> {
    private Activity context;
    private List<DataItem> kingsModels;
    ProgressDialog progressDialog;

    public FountAdapter(Activity context, List<DataItem> kingsModels) {
        this.context = context;
        this.kingsModels = kingsModels;

        progressDialog = new ProgressDialog(context, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @NonNull
    @Override
    public FountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FountAdapter.ViewHolder holder, int position) {
        DataItem listModel = kingsModels.get(position);
        holder.fontLayoutBinding.tvFountValue.setText(listModel.getAmount());
        holder.fontLayoutBinding.tvFountTime.setText("Validity " + listModel.getTotalDays() + " Days");
        holder.fontLayoutBinding.fountName.setText(listModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return kingsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FontLayoutBinding fontLayoutBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fontLayoutBinding = FontLayoutBinding.bind(itemView);
        }
    }
}
