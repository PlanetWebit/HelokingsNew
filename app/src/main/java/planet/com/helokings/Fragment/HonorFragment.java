package planet.com.helokings.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.Adapter.HonorAdapter;
import planet.com.helokings.Model.Honormodel;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentHonorBinding;

public class HonorFragment extends Fragment {
    FragmentHonorBinding honorBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        honorBinding = FragmentHonorBinding.inflate(inflater, container, false);
        View root = honorBinding.getRoot();
        honorlist();
        return root;
    }

    private void honorlist() {
        Honormodel[] honormodels = new Honormodel[]{
                new Honormodel("Badge", R.drawable.newlogo),
                new Honormodel("Badge", R.drawable.newlogo),
                new Honormodel("Badge", R.drawable.newlogo)
        };
        HonorAdapter honorAdapter = new HonorAdapter(getContext(), honormodels);
        GridLayoutManager gllm = new GridLayoutManager(getContext(), 2);

        honorBinding.honorRecyclerView.setAdapter(honorAdapter);
        honorBinding.honorRecyclerView.setLayoutManager(gllm);
    }
}