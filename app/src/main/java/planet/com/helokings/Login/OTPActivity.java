package planet.com.helokings.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import planet.com.helokings.Activity.ProfileActivity;
import planet.com.helokings.Model.OTPModel;
import planet.com.helokings.Model.OtpModule;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityOtpactivityBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding OTPActivityBinding;
    String user_id = "", mobile_number = "", otp = "", token = "";
    ProgressDialog progressDialog;
    UserSharePreferancess userSharePreferancess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OTPActivityBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(OTPActivityBinding.getRoot());

        userSharePreferancess = new UserSharePreferancess(this);
        progressDialog = new ProgressDialog(OTPActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        mobile_number = intent.getStringExtra("mobile_number");
        otp = intent.getStringExtra("otp");
        token = intent.getStringExtra("token");
        Toast.makeText(this, "" + otp, Toast.LENGTH_LONG).show();


        userSharePreferancess = new UserSharePreferancess(this);

        OTPActivityBinding.etLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation()) {
                    otpVerify();

                }
            }
        });
    }

    //

    private void otpVerify() {
        progressDialog.show();
        Call<OtpModule> otpModuleCall = RetrofitClient.getInstance().getAllApiResponse().userOtp(mobile_number,
                OTPActivityBinding.etOtp.getText().toString());
        otpModuleCall.enqueue(new Callback<OtpModule>() {
            @Override
            public void onResponse(Call<OtpModule> call, Response<OtpModule> response) {
                progressDialog.dismiss();
                OtpModule module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        userSharePreferancess.setStringValue("user_id", module.getToken());
                        Comman.getInstance().setUser_id(module.getToken());
                        if (module.getProfileComplete().equalsIgnoreCase("1")) {


                            userSharePreferancess.setStringValue("id", module.getUserId());
                            Comman.getInstance().setId(module.getUserId());

                            userSharePreferancess.setStringValue("username", module.getUsername());
                            Comman.getInstance().setUsername(module.getUsername());

                            userSharePreferancess.setStringValue("name", module.getNickName());
                            Comman.getInstance().setName(module.getNickName());


                            userSharePreferancess.setStringValue("token", module.getToken());
                            Comman.getInstance().setToken(module.getToken());





                            Intent oldUser = new Intent(OTPActivity.this, MainActivity.class);
                            startActivity(oldUser);
                            finish();
                            Log.e("Comman", Comman.getInstance().getUser_id());

                        } else  {
                            Intent newUser = new Intent(OTPActivity.this, ProfileActivity.class);
                             startActivity(newUser);
                            finish();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(OTPActivity.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<OtpModule> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OTPActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean Validation() {
        if (OTPActivityBinding.etOtp.getText().toString().equalsIgnoreCase("")) {
            OTPActivityBinding.etOtp.setError("Please enter otp");
            OTPActivityBinding.etOtp.requestFocus();
            return false;

        } else if (OTPActivityBinding.etOtp.getText().toString().length() < 4) {
            OTPActivityBinding.etOtp.setError("Please enter valid otp ");
            OTPActivityBinding.etOtp.requestFocus();
            return false;
        }

        return true;
    }
}