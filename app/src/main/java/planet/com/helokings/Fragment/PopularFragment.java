package planet.com.helokings.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentPopularBinding;
import retrofit2.http.POST;


public class PopularFragment extends Fragment {
    FragmentPopularBinding popularBinding;
    private String changebg = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        popularBinding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = popularBinding.getRoot();
        ChangeBg();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new MyFragment()).commit();
        changebg = "1";

        popularBinding.layMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changebg = "1";
                ChangeBg();

            }
        });
        popularBinding.layJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changebg = "2";
                ChangeBg();

            }
        });
        popularBinding.layRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changebg = "3";
                ChangeBg();

            }
        });

        popularBinding.layFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changebg = "4";
                ChangeBg();

            }
        });


        return root;
    }

    @SuppressLint("ResourceAsColor")
    private void ChangeBg() {
        if (changebg.equalsIgnoreCase("1")) {
            popularBinding.tvMy.setTextColor(Color.BLACK);
            popularBinding.viewMy.setBackgroundResource(R.color.light_blue);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new MyFragment()).commit();

            popularBinding.tvRecent.setTextColor(Color.GRAY);
            popularBinding.tvFollowing.setTextColor(Color.GRAY);
            popularBinding.tvJoined.setTextColor(Color.GRAY);

            popularBinding.viewRecent.setBackgroundResource(R.color.white);
            popularBinding.viewJoined.setBackgroundResource(R.color.white);
            popularBinding.viewFollowing.setBackgroundResource(R.color.white);
        } else if (changebg.equalsIgnoreCase("2")) {
            popularBinding.tvJoined.setTextColor(Color.BLACK);
            popularBinding.viewJoined.setBackgroundResource(R.color.light_blue);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new JoinedFragment()).commit();

            popularBinding.tvMy.setTextColor(Color.GRAY);
            popularBinding.tvRecent.setTextColor(Color.GRAY);
            popularBinding.tvFollowing.setTextColor(Color.GRAY);

            popularBinding.viewMy.setBackgroundResource(R.color.white);
            popularBinding.viewRecent.setBackgroundResource(R.color.white);
            popularBinding.viewFollowing.setBackgroundResource(R.color.white);

        } else if (changebg.equalsIgnoreCase("3")) {
            popularBinding.tvRecent.setTextColor(Color.BLACK);
            popularBinding.viewRecent.setBackgroundResource(R.color.light_blue);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new RecentFragment()).commit();

            popularBinding.tvMy.setTextColor(Color.GRAY);
            popularBinding.tvJoined.setTextColor(Color.GRAY);
            popularBinding.tvFollowing.setTextColor(Color.GRAY);

            popularBinding.viewMy.setBackgroundResource(R.color.white);
            popularBinding.viewJoined.setBackgroundResource(R.color.white);
            popularBinding.viewFollowing.setBackgroundResource(R.color.white);
        } else if (changebg.equalsIgnoreCase("4")) {
            popularBinding.tvFollowing.setTextColor(Color.BLACK);
            popularBinding.viewFollowing.setBackgroundResource(R.color.light_blue);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new FollowingFragment()).commit();
            popularBinding.tvMy.setTextColor(Color.GRAY);
            popularBinding.tvJoined.setTextColor(Color.GRAY);
            popularBinding.tvRecent.setTextColor(Color.GRAY);

            popularBinding.viewMy.setBackgroundResource(R.color.white);
            popularBinding.viewJoined.setBackgroundResource(R.color.white);
            popularBinding.viewRecent.setBackgroundResource(R.color.white);
        }
    }
}