package planet.com.helokings.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import planet.com.helokings.Activity.RegisterationActivity;
import planet.com.helokings.Model.OTPModel;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityOtpactivityBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding otpactivityBinding;
    String user_id = "", mobile_number = "", otp = "", token = "";
    ProgressDialog progressDialog;
    UserSharePreferancess userSharePreferancess;
     Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpactivityBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(otpactivityBinding.getRoot());

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


        userSharePreferancess=new UserSharePreferancess(this);

        otpactivityBinding.etLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation()) {
                    OtpVerify();

                }
            }
        });
    }

    private void OtpVerify() {
        progressDialog.show();
        retrofit2.Call<OTPModel> otpModuleCall = RetrofitClient.getInstance().myInterFaceData().OtpData(mobile_number, otpactivityBinding.etOtp.getText().toString());
        otpModuleCall.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(retrofit2.Call<OTPModel> call, Response<OTPModel> response) {
                progressDialog.dismiss();
                OTPModel module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        if (module.getProfileComplete().equalsIgnoreCase("1")) {
                            userSharePreferancess.setStringValue("user_id", module.getToken());
                            Comman.getInstance().setUser_id(module.getToken());

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

                        } else {
                            Intent newUser = new Intent(OTPActivity.this, RegisterationActivity.class);
                            newUser.putExtra("user_id", module.getToken());
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
            public void onFailure(Call<OTPModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OTPActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean Validation() {
        if (otpactivityBinding.etOtp.getText().toString().equalsIgnoreCase("")) {
            otpactivityBinding.etOtp.setError("Please enter otp");
            otpactivityBinding.etOtp.requestFocus();
            return false;

        } else if (otpactivityBinding.etOtp.getText().toString().length() < 4) {
            otpactivityBinding.etOtp.setError("Please enter valid otp ");
            otpactivityBinding.etOtp.requestFocus();
            return false;
        }

        return true;
    }
}