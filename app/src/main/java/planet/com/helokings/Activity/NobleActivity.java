package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import planet.com.helokings.Fragment.CountFragment;
import planet.com.helokings.Fragment.DukeFragment;
import planet.com.helokings.Fragment.KingFragment;
import planet.com.helokings.Fragment.MarquisFragment;
import planet.com.helokings.Fragment.MeFragment;
import planet.com.helokings.Fragment.ViscountFragment;
import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityNobleBinding;

public class NobleActivity extends AppCompatActivity {
    ActivityNobleBinding nobleBinding;
    private String NobleValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nobleBinding = ActivityNobleBinding.inflate(getLayoutInflater());
        setContentView(nobleBinding.getRoot());
        fullscreen();

        toolbar();
        getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new ViscountFragment()).commit();

        nobleBinding.laySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NobleActivity.this, SettingActivity.class);
                startActivity(intent);


            }
        });
        nobleBinding.tvViscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobleValue = "1";
                ChangeNobleBG();

            }
        });
        nobleBinding.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobleValue = "2";
                ChangeNobleBG();

            }
        });
        nobleBinding.tvMarquis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobleValue = "3";
                ChangeNobleBG();

            }
        });
        nobleBinding.tvDuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobleValue = "4";
                ChangeNobleBG();

            }
        });
        nobleBinding.tvKing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobleValue = "5";
                ChangeNobleBG();

            }
        });

    }

    private void fullscreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = ContextCompat.getDrawable(this, R.color.light_blue);
        getWindow().setBackgroundDrawable(background);


    }

    @SuppressLint("ResourceAsColor")
    private void ChangeNobleBG() {
        if (NobleValue.equalsIgnoreCase("1")) {
            nobleBinding.tvViscount.setTextColor(Color.parseColor("#06CED1"));
            getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new ViscountFragment()).commit();

            nobleBinding.tvCount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvMarquis.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvDuke.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvKing.setTextColor(Color.parseColor("#000000"));

        } else if (NobleValue.equalsIgnoreCase("2")) {
            nobleBinding.tvCount.setTextColor(Color.parseColor("#06CED1"));
            getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new CountFragment()).commit();

            nobleBinding.tvViscount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvMarquis.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvDuke.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvKing.setTextColor(Color.parseColor("#000000"));

        } else if (NobleValue.equalsIgnoreCase("3")) {
            nobleBinding.tvMarquis.setTextColor(Color.parseColor("#06CED1"));
            getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new MarquisFragment()).commit();

            nobleBinding.tvCount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvViscount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvDuke.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvKing.setTextColor(Color.parseColor("#000000"));
        } else if (NobleValue.equalsIgnoreCase("4")) {
            nobleBinding.tvDuke.setTextColor(Color.parseColor("#06CED1"));
            getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new DukeFragment()).commit();

            nobleBinding.tvCount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvMarquis.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvViscount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvKing.setTextColor(Color.parseColor("#000000"));
        } else if (NobleValue.equalsIgnoreCase("5")) {
            nobleBinding.tvKing.setTextColor(Color.parseColor("#06CED1"));
            getSupportFragmentManager().beginTransaction().replace(R.id.noble_frame, new KingFragment()).commit();
            nobleBinding.tvCount.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvMarquis.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvDuke.setTextColor(Color.parseColor("#000000"));
            nobleBinding.tvViscount.setTextColor(Color.parseColor("#000000"));
        }


    }

    private void toolbar() {
        setSupportActionBar(nobleBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nobleBinding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}