package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityAccountBindingBinding;

public class AccountBindingActivity extends AppCompatActivity {
    ActivityAccountBindingBinding accountBindingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBindingBinding = ActivityAccountBindingBinding.inflate(getLayoutInflater());
        setContentView(accountBindingBinding.getRoot());
        toolbar();
    }

    private void toolbar() {
        setSupportActionBar(accountBindingBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        accountBindingBinding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}