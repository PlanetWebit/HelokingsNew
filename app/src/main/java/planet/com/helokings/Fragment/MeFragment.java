package planet.com.helokings.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import planet.com.helokings.Activity.BadgeActivity;
import planet.com.helokings.Activity.HelokingVIPActivity;
import planet.com.helokings.Activity.MyToolsActivity;
import planet.com.helokings.Activity.NobleActivity;
import planet.com.helokings.Activity.SettingActivity;
import planet.com.helokings.Activity.StoreActivity;
import planet.com.helokings.Activity.WalletActivity;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.FragmentMeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MeFragment extends Fragment {
    FragmentMeBinding meBinding;


    UserSharePreferancess userSharePreferancess;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meBinding = FragmentMeBinding.inflate(inflater, container, false);
        View root = meBinding.getRoot();
        userSharePreferancess=new UserSharePreferancess(getActivity());
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

        meBinding.hostManagement.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        getUserProfile();
        return root;
    }


    private void getUserProfile() {
        Call<ResponseProfile> mCall = RetrofitClient.getInstance().getAllApiResponse().getUserProfile(
                Comman.getInstance().getUser_id());
        mCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {

                ResponseProfile module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        meBinding.name.setText(module.getData().getNickName());
                        meBinding.username.setText(module.getData().getUsername());
                        meBinding.followers.setText(module.getData().getFollowersCount()+"");
                        meBinding.following.setText(module.getData().getFollowingCount()+"");
//                        meBinding.gifts.setText(module.getData().get());
//                        meBinding.visitors.setText(module.getData().getUsername());
                        if(module.getData().getImage()!=null) {
                            if (module.getData().getImage().equalsIgnoreCase("")) {
                                meBinding.profile.setImageResource(R.drawable.profile);
                            } else {
                                Glide.with(getActivity()).load(module.getData().getImage()).placeholder(R.drawable.profile).into(meBinding.profile);
                            }
                        }

                        if (module.getData().getFrame_image().equalsIgnoreCase("")) {
                            meBinding.videoFrame.setVisibility(View.GONE);
                        } else {
                            Glide.with(getActivity()).load(module.getData().getFrame_image()).into(meBinding.videoFrame);
                            meBinding.videoFrame.setVisibility(View.VISIBLE);
                        }

                    } else{
                        if(module.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        Toast.makeText(getActivity(),module.getMsg(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



}