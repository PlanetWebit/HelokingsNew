package planet.com.helokings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import planet.com.helokings.R;
import planet.com.helokings.databinding.CommentLayoutBinding;

public class PostRecommentAdapter extends RecyclerView.Adapter<PostRecommentAdapter.ViewHolder> {
    private Context context;
    String[] postPojoArrayList;

    public PostRecommentAdapter(Context context, String[] postPojoArrayList) {
        this.context = context;
        this.postPojoArrayList = postPojoArrayList;
    }

    @NonNull
    @Override
    public PostRecommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecommentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return postPojoArrayList.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CommentLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommentLayoutBinding.bind(itemView);
        }
    }


}
