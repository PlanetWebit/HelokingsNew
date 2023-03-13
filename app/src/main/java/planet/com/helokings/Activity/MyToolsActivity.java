package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityMyToolsBinding;

public class MyToolsActivity extends AppCompatActivity {
    ActivityMyToolsBinding myToolsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToolsBinding = ActivityMyToolsBinding.inflate(getLayoutInflater());
        setContentView(myToolsBinding.getRoot());
    }
}