package planet.com.helokings.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;
    String ChangeBgValue = "", roomName = "", roomimage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = homeBinding.getRoot();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.h_frame, new PopularFragment()).commit();
        ChangeBgValue = "2";
        Bg_Data();


        homeBinding.tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBgValue = "1";
                Bg_Data();

            }
        });

        homeBinding.tvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBgValue = "2";
                Bg_Data();
            }
        });
        homeBinding.tvParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBgValue = "3";
                Bg_Data();
            }
        });
        homeBinding.tvSpotlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBgValue = "4";
                Bg_Data();
            }
        });

        return root;
    }

    private void Bg_Data() {
        if (ChangeBgValue.equalsIgnoreCase("1")) {

            homeBinding.vNew.setVisibility(View.VISIBLE);
            homeBinding.tvNew.setVisibility(View.VISIBLE);
            homeBinding.vPopular.setVisibility(View.GONE);
            homeBinding.vParty.setVisibility(View.GONE);
            homeBinding.vSpotlight.setVisibility(View.GONE);

            homeBinding.tvNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_19dp));
            homeBinding.tvPopular.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvParty.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvSpotlight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.h_frame, new NewFragment()).commit();

        } else if (ChangeBgValue.equalsIgnoreCase("2")) {
            homeBinding.vPopular.setVisibility(View.VISIBLE);
            homeBinding.vNew.setVisibility(View.GONE);
            homeBinding.tvNew.setVisibility(View.VISIBLE);
            homeBinding.vParty.setVisibility(View.GONE);
            homeBinding.vSpotlight.setVisibility(View.GONE);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.h_frame, new PopularFragment()).commit();

            homeBinding.tvPopular.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_19dp));
            homeBinding.tvNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvParty.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvSpotlight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
        } else if (ChangeBgValue.equalsIgnoreCase("3")) {
            homeBinding.vParty.setVisibility(View.VISIBLE);
            homeBinding.vNew.setVisibility(View.GONE);
            homeBinding.tvNew.setVisibility(View.VISIBLE);
            homeBinding.vPopular.setVisibility(View.GONE);
            homeBinding.vSpotlight.setVisibility(View.GONE);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.h_frame, new PartyFragment()).commit();

            homeBinding.tvParty.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_19dp));
            homeBinding.tvNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvPopular.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvSpotlight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));


        } else if (ChangeBgValue.equalsIgnoreCase("4")) {
            homeBinding.vSpotlight.setVisibility(View.VISIBLE);
            homeBinding.tvNew.setVisibility(View.GONE);
            homeBinding.vNew.setVisibility(View.GONE);
            homeBinding.vPopular.setVisibility(View.GONE);
            homeBinding.vParty.setVisibility(View.GONE);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.h_frame, new SpotLightFragment()).commit();

            homeBinding.tvSpotlight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_19dp));
            homeBinding.tvNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvPopular.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
            homeBinding.tvParty.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15dp));
        }

    }
}