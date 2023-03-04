package planet.com.helokings.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import planet.com.helokings.Activity.GoLiveActivity;
import planet.com.helokings.Activity.MomentActivity;
import planet.com.helokings.Fragment.HomeFragment;
import planet.com.helokings.Fragment.MeFragment;
import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();



        activityMainBinding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
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
}