package planet.com.helokings.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentPopularBinding;


public class PopularFragment extends Fragment {
    FragmentPopularBinding popularBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        popularBinding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = popularBinding.getRoot();
        return root;
    }
}