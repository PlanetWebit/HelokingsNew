package planet.com.helokings.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import planet.com.helokings.Activity.GoLiveActivity;
import planet.com.helokings.Activity.MomentActivity;
import planet.com.helokings.Fragment.HomeFragment;
import planet.com.helokings.Fragment.MeFragment;
import planet.com.helokings.R;
import planet.com.helokings.Service.Constants;
import planet.com.helokings.Service.FragmentCallBack;
import planet.com.helokings.Service.Refresh;
import planet.com.helokings.Service.UploadService;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    public static MainActivity context;
    public static Refresh refresh;

    private static ProgressBar progressBar;

    private static TextView tvProgressCount;

    public static CardView uploadingLayout;
    UploadingVideoBroadCast mReceiver;


    // uploading broadcast
    private class UploadingVideoBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Constants.isMyServiceRunning(context, UploadService.class)) {
                Log.e("TAG", "Upload Started");
                uploadingLayout.setVisibility(View.VISIBLE);
            } else {
                Log.e("TAG", "Upload Finish");
                uploadingLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        progressBar = findViewById(R.id.progressBar);
        tvProgressCount = findViewById(R.id.tvProgressCount);
        uploadingLayout = findViewById(R.id.upload_video_layout);
        // video uploading service
        if (Constants.isMyServiceRunning(this, UploadService.class)) {
            uploadingLayout.setVisibility(View.VISIBLE);

        } else {

            uploadingLayout.setVisibility(View.GONE);
        }
        mReceiver = new UploadingVideoBroadCast();
        registerReceiver(mReceiver, new IntentFilter("uploadVideo"));

        //////////

        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();


        activityMainBinding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
                        return true;

                    case R.id.moment:
                        startActivity(new Intent(MainActivity.this, MomentActivity.class));

                        return true;
                    case R.id.live:
                        startActivity(new Intent(MainActivity.this, GoLiveActivity.class));
                        return true;


                    case R.id.me:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MeFragment()).commit();

                        return true;
                }
                return false;
            }
        });
    }

    public static FragmentCallBack uploadingCallback = new FragmentCallBack() {
        @Override
        public void onResponse(Bundle bundle) {
            if (bundle.getBoolean("isShow")) {
                int currentProgress = bundle.getInt("currentpercent", 0);
                Log.e("TAG", "" + currentProgress);
                if (progressBar != null && tvProgressCount != null) {
                    progressBar.setProgress(currentProgress);
                    tvProgressCount.setText(currentProgress + "%");
                }
            }
        }

    };

}