package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import planet.com.helokings.R;
import planet.com.helokings.databinding.ActivityWalletBinding;

public class WalletActivity extends AppCompatActivity {
    ActivityWalletBinding walletBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletBinding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(walletBinding.getRoot());
        fullScreen();


        walletBinding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        walletBinding.layPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this, PaytmWalletActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = ContextCompat.getDrawable(this, R.drawable.login);
        getWindow().setBackgroundDrawable(background);
    }
}