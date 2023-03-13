package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import planet.com.helokings.R;
import planet.com.helokings.SharedPref.Comman;

public class PaytmWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletll);
       // Log.e("bdkskbksc", "" + Comman.getInstance().getUser_id());
       // Toast.makeText(this, ""+ Comman.getInstance().getToken(),Toast.LENGTH_SHORT).show();

    }
}