package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding editProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(editProfileBinding.getRoot());
        toolbar();


    }

    private void toolbar() {
        setSupportActionBar(editProfileBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editProfileBinding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}