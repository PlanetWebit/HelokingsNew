package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import planet.com.helokings.databinding.ActivityColeaderDashboardBinding;
import planet.com.helokings.databinding.ActivityJoinAgencyBinding;

public class ColeaderDashboard extends AppCompatActivity {

    ActivityColeaderDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityColeaderDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }
}