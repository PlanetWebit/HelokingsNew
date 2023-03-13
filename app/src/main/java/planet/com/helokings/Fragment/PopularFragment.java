package planet.com.helokings.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import planet.com.helokings.Adapter.PopulorViewpadapter;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.HomeData.ResponseHomedata;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.FragmentPopularBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;


public class PopularFragment extends Fragment {
    FragmentPopularBinding popularBinding;
    private String changebg = "";
    ProgressDialog progressDialog;
    private UserSharePreferancess userSharePreferancess;
    PopulorViewpadapter viewpadapter;
    int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        popularBinding = FragmentPopularBinding.inflate(inflater, container, false);
        View root = popularBinding.getRoot();

        progressDialog = new ProgressDialog(getActivity(), R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        userSharePreferancess = new UserSharePreferancess(getActivity());

        popularBinding.swipeRefresh.setColorScheme(R.color.light_blue, R.color.dark_blue, R.color.red, R.color.red, R.color.white);
        popularBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHomeData();
                        popularBinding.swipeRefresh.setRefreshing(false);
                    }
                }, 2500);
            }
        });


        ChangeBg();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_chat_frame_layout, new JoinedFragment()).commit();
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

        getHomeData();
        return root;
    }

    private void getHomeData() {
        progressDialog.show();
        Call<ResponseHomedata> pBnrModeleCall = RetrofitClient.getInstance().getnewResponseData().getHomedata(Comman.comman_obj.getUser_id());
        pBnrModeleCall.enqueue(new Callback<ResponseHomedata>() {
            @Override
            public void onResponse(Call<ResponseHomedata> call, Response<ResponseHomedata> response) {
                progressDialog.dismiss();
                ResponseHomedata pBnrModele = response.body();
                if (response.isSuccessful()) {
                    if (pBnrModele.getStatus().equalsIgnoreCase("true")) {
                        popularBinding.liveFollower.setText(pBnrModele.getFollowerLiveCount() + " Followers");
                        if (getActivity() != null) {
                            viewpadapter = new PopulorViewpadapter(getActivity(), pBnrModele.getBanner());
                            popularBinding.pplviewPager.setAdapter(viewpadapter);
                            popularBinding.pplViewPagerDots.setViewPager(popularBinding.pplviewPager);

                        }
                        final Handler handler = new Handler();
                        final Runnable update = new Runnable() {
                            @Override
                            public void run() {
                                if (count == pBnrModele.getBanner().size()) {
                                    count = 0;

                                }
                                popularBinding.pplviewPager.setCurrentItem(count++, true);

                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        }, 1000, 1000);



                    } else {
                        if (pBnrModele.getMsg().equalsIgnoreCase("unauthorized login")) {
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra("status", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseHomedata> call, Throwable t) {

            }
        });


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