package planet.com.helokings.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentViscountBinding;


public class ViscountFragment extends Fragment {
    FragmentViscountBinding viscountBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viscountBinding = FragmentViscountBinding.inflate(inflater, container, false);
        View root = viscountBinding.getRoot();
        return root;
    }
}