package planet.com.helokings.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.Activity.StoreActivity;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentMeBinding;


public class MeFragment extends Fragment {
    FragmentMeBinding meBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meBinding = FragmentMeBinding.inflate(inflater, container, false);
        View root = meBinding.getRoot();


        meBinding.layStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}