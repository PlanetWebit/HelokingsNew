package planet.com.helokings.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityLoginTypeBinding;

public class LoginTypeActivity extends AppCompatActivity {
    ActivityLoginTypeBinding loginTypeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginTypeBinding = ActivityLoginTypeBinding.inflate(getLayoutInflater());
        setContentView(loginTypeBinding.getRoot());


        loginTypeBinding.lyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginTypeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}