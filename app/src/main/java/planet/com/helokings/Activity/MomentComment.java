package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import planet.com.helokings.Adapter.PostCommentAdapter;
import planet.com.helokings.databinding.ActivityMomentBinding;
import planet.com.helokings.databinding.ActivityMomentCommentBinding;

public class MomentComment extends AppCompatActivity {

    ActivityMomentCommentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMomentCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.momentAllPostRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.momentAllPostRecyclerView.setHasFixedSize(true);
        binding.momentAllPostRecyclerView.setAdapter(new PostCommentAdapter(getApplicationContext(),new String[]{"","","","",""}));
    }
}