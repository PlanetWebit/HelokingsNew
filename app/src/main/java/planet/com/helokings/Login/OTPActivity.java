package planet.com.helokings.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import planet.com.helokings.databinding.ActivityOtpactivityBinding;

public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding otpactivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpactivityBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(otpactivityBinding.getRoot());

        otpactivityBinding.etLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}