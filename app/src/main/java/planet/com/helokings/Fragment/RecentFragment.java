package planet.com.helokings.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.HomeData.ResponseHomedata;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.VideoStreamingActivity.Adapter.AudioplrFdataAdapter;
import planet.com.helokings.VideoStreamingActivity.Adapter.PartyplrFdataAdapter;
 import planet.com.helokings.databinding.FragmentRecentBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View root;
    ProgressDialog progressDialog;

    AudioplrFdataAdapter pplrFdataAdapter;


    FragmentRecentBinding fragmentRecentBinding;


    int count = 0;
    private UserSharePreferancess userSharePreferancess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRecentBinding = FragmentRecentBinding.inflate(inflater, container, false);

        progressDialog = new ProgressDialog(getActivity(), R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        userSharePreferancess = new UserSharePreferancess(getActivity());



        fragmentRecentBinding.popRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentRecentBinding.popRcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        fragmentRecentBinding.swipeRefresh.setColorScheme(R.color.dark_blue,
                R.color.grayDark, R.color.red, R.color.light_blue, R.color.dark_blue);


        fragmentRecentBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHomeData();
                        fragmentRecentBinding.swipeRefresh.setRefreshing(false);
                    }
                }, 2500);
            }
        });


        getHomeData();



        return fragmentRecentBinding.getRoot();
    }


    private void getHomeData() {
        Call<ResponseHomedata> pBnrModeleCall = RetrofitClient.getInstance().getAllApiResponse().getHomedata(Comman.getInstance().getUser_id());
        pBnrModeleCall.enqueue(new Callback<ResponseHomedata>() {
            @Override
            public void onResponse(Call<ResponseHomedata> call, Response<ResponseHomedata> response) {
                ResponseHomedata pBnrModele = response.body();
                if (response.isSuccessful()) {
                    if (pBnrModele.getStatus().equalsIgnoreCase("true")) {

                        fragmentRecentBinding.liveFollower.setText(pBnrModele.getFollowerLiveCount()+" Followers");




                        pplrFdataAdapter = new AudioplrFdataAdapter(getActivity(), pBnrModele.getData());
                        fragmentRecentBinding.popRcv.setAdapter(pplrFdataAdapter);


                    }else{
                        if(pBnrModele.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(getActivity(),LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseHomedata> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}