package planet.com.helokings.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.HomeData.ResponseHomedata;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.VideoStreamingActivity.Adapter.PartyplrFdataAdapter;
import planet.com.helokings.databinding.FragmentNewBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View root;
    ProgressDialog progressDialog;

    PartyplrFdataAdapter pplrFdataAdapter;


    FragmentNewBinding fragmentNewBinding;


     int count = 0;
     private UserSharePreferancess userSharePreferancess;
    public NewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFragment newInstance(String param1, String param2) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentNewBinding = FragmentNewBinding.inflate(inflater, container, false);

        progressDialog = new ProgressDialog(getActivity(), R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        userSharePreferancess = new UserSharePreferancess(getActivity());



        fragmentNewBinding.popRcv.setItemAnimator(new DefaultItemAnimator());
        fragmentNewBinding.popRcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        fragmentNewBinding.swipeRefresh.setColorScheme(R.color.dark_blue,
                R.color.grayDark, R.color.red, R.color.light_blue, R.color.dark_blue);


        fragmentNewBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHomeData();
                        fragmentNewBinding.swipeRefresh.setRefreshing(false);
                    }
                }, 2500);
            }
        });


        getHomeData();

        return fragmentNewBinding.getRoot();
    }



    private void getHomeData() {
        Call<ResponseHomedata> pBnrModeleCall = RetrofitClient.getInstance().getAllApiResponse().getStreamData(Comman.getInstance().getUser_id(),"live");
        pBnrModeleCall.enqueue(new Callback<ResponseHomedata>() {
            @Override
            public void onResponse(Call<ResponseHomedata> call, Response<ResponseHomedata> response) {
                ResponseHomedata pBnrModele = response.body();
                if (response.isSuccessful()) {
                    if (pBnrModele.getStatus().equalsIgnoreCase("true")) {

                        fragmentNewBinding.liveFollower.setText(pBnrModele.getFollowerLiveCount()+" Followers");


                        final Handler handler = new Handler();
                        final Runnable update = new Runnable() {
                            @Override
                            public void run() {
                                if (count == pBnrModele.getBanner().size()) {
                                    count = 0;
                                }
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 1000, 1000);


                        pplrFdataAdapter = new PartyplrFdataAdapter(getActivity(), pBnrModele.getData());
                        fragmentNewBinding.popRcv.setAdapter(pplrFdataAdapter);


                    }else{
                        if(pBnrModele.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                }else{
                    Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHomedata> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}