package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import planet.com.helokings.Fragment.AchievementFragment;
import planet.com.helokings.Fragment.HonorFragment;
import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityBadgeBinding;

public class BadgeActivity extends AppCompatActivity {
    ActivityBadgeBinding badgeBinding;
    private String BadgeValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        badgeBinding = ActivityBadgeBinding.inflate(getLayoutInflater());

        setContentView(badgeBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.badge_frame, new AchievementFragment()).commit();

        badgeBinding.layAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgeValue = "1";
                BadageBG();
            }
        });
        badgeBinding.layHonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgeValue = "2";
                BadageBG();
            }
        });

    }

    private void BadageBG() {
        if (BadgeValue.equalsIgnoreCase("1")) {
            badgeBinding.tvAchievement.setTextColor(Color.parseColor("#06CED1"));
            badgeBinding.tvHonor.setTextColor(Color.parseColor("#808080"));
            badgeBinding.btnEdit.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.badge_frame, new AchievementFragment()).commit();

            badgeBinding.viewAchievement.setBackgroundResource(R.color.light_blue);
            badgeBinding.viewHonor.setBackgroundResource(R.color.white);
        } else if (BadgeValue.equalsIgnoreCase("2")) {
            badgeBinding.tvHonor.setTextColor(Color.parseColor("#06CED1"));
            badgeBinding.tvAchievement.setTextColor(Color.parseColor("#808080"));
            badgeBinding.btnEdit.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.badge_frame, new HonorFragment()).commit();

            badgeBinding.viewHonor.setBackgroundResource(R.color.light_blue);
            badgeBinding.viewAchievement.setBackgroundResource(R.color.white);
        }
    }
}