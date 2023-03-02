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
    String user_id = "", mobile_number = "", otp = "",token="";
    ProgressDialog progressDialog;
    private UserSharePreferancess userSharePreferancess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpactivityBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(otpactivityBinding.getRoot());

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


        otpactivityBinding.etLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                startActivity(intent);*/
                if(Validation()){
                    OtpVerify();

                }
            }
        });
    }

    private void OtpVerify() {
        progressDialog.show();
        Call<OTPModel> call = RetrofitClient.getInstance().myInterFaceData().OtpData(mobile_number,otpactivityBinding.etOtp.getText().toString());
        call.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                progressDialog.dismiss();
                OTPModel otpModel = response.body();
                if(response.isSuccessful()){
                    if(otpModel.getStatus().equalsIgnoreCase("true")){
                        if (otpModel.getProfileComplete().equalsIgnoreCase("1")) {

                            userSharePreferancess.setStringValue("user_id", otpModel.getToken());
                            Comman.getInstance().setUser_id(otpModel.getToken());

                            userSharePreferancess.setStringValue("id", otpModel.getUserId());
                            Comman.getInstance().setId(otpModel.getUserId());

                            userSharePreferancess.setStringValue("username", otpModel.getUsername());
                            Comman.getInstance().setUsername(otpModel.getUsername());

                            userSharePreferancess.setStringValue("name", otpModel.getNickName());
                            Comman.getInstance().setName(otpModel.getNickName());


                            userSharePreferancess.setStringValue("token", otpModel.getToken());
                            Comman.getInstance().setToken(otpModel.getToken());





                            Intent oldUser = new Intent(OTPActivity.this, MainActivity.class);
                            startActivity(oldUser);
                            finish();
                            Log.e("Comman", Comman.getInstance().getUser_id());

                        } else  {
                            Intent newUser = new Intent(OTPActivity.this, RegisterationActivity.class);
                            newUser.putExtra("user_id", otpModel.getToken());
                            startActivity(newUser);
                            finish();
                        }


                        }

                        }
                }



            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {

            }
        });


    }

    private boolean Validation() {
        if(otpactivityBinding.etOtp.getText().toString().equalsIgnoreCase("")){
            otpactivityBinding.etOtp.setError("Please enter otp");
            otpactivityBinding.etOtp.requestFocus();
            return false;

        }
        else if(otpactivityBinding.etOtp.getText().toString().length()<4){
            otpactivityBinding.etOtp.setError("Please enter valid otp ");
            otpactivityBinding.etOtp.requestFocus();
            return false;
        }

        return true;
    }
}