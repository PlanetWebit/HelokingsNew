package planet.com.helokings.Login;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import planet.com.helokings.Model.LoginModel;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.databinding.ActivityLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    String phone_id = "";
    ProgressDialog progressDialog;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        loginBinding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    String nvalue = String.valueOf(loginBinding.etNumber.getText().charAt(0));
                    if (Integer.parseInt(nvalue) < 6) {
                        loginBinding.etNumber.setError("Please enter valid number ");
                        loginBinding.etNumber.requestFocus();

                    } else {
                        Login();
                    }


                }
            }
        });


    }

    private void Login() {
        progressDialog.show();
        Call<LoginModel> call = RetrofitClient.getInstance().myInterFaceData().logindata(loginBinding.etNumber.getText().toString().replace(" ", ""), phone_id, "android");
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                LoginModel loginModel = response.body();
                if (response.isSuccessful()) {
                    if (loginModel.getStatus().equalsIgnoreCase("true")) {
                        Intent inLogin = new Intent(LoginActivity.this, OTPActivity.class);
                        inLogin.putExtra("user_id", phone_id);
                        inLogin.putExtra("mobile_number", loginBinding.etNumber.getText().toString());
                        inLogin.putExtra("otp", loginModel.getVerification_code());
                        inLogin.putExtra("token", phone_id);
                        startActivity(inLogin);
                        finish();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + loginModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "failll", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validation() {
        if (loginBinding.etNumber.getText().toString().equalsIgnoreCase("")) {
            loginBinding.etNumber.setError("Please enter number");
            loginBinding.etNumber.requestFocus();
            return false;
        } else if (loginBinding.etNumber.getText().toString().length() < 10) {
            loginBinding.etNumber.setError("Please enter correct number");
            loginBinding.etNumber.requestFocus();
            return false;
        }


        return true;
    }
}