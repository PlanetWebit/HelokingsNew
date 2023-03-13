package planet.com.helokings.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.Activity.BadgeActivity;
import planet.com.helokings.Activity.HelokingVIPActivity;
import planet.com.helokings.Activity.MyToolsActivity;
import planet.com.helokings.Activity.NobleActivity;
import planet.com.helokings.Activity.SettingActivity;
import planet.com.helokings.Activity.StoreActivity;
import planet.com.helokings.Activity.WalletActivity;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentMeBinding;


public class MeFragment extends Fragment {
    FragmentMeBinding meBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meBinding = FragmentMeBinding.inflate(inflater, container, false);
        View root = meBinding.getRoot();
        meBinding.layVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelokingVIPActivity.class);
                startActivity(intent);
            }
        });
        meBinding.layMytools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyToolsActivity.class);
                startActivity(intent);
            }
        });
        meBinding.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        meBinding.layWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WalletActivity.class);
                startActivity(intent);
            }
        });

        meBinding.layStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        meBinding.layNoble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NobleActivity.class);
                startActivity(intent);
            }
        });

        meBinding.layBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BadgeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}