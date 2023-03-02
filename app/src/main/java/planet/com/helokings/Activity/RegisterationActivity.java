package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.skydoves.elasticviews.ElasticButton;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Login.MainActivity;
import planet.com.helokings.Model.RegistrModule;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterationActivity extends AppCompatActivity {
    ElasticButton register;
    EditText user_nivkname, bio;
    RadioGroup genderdgrp;
    RadioButton malebtmn, femalebtmn, otherbtmn;
    String user_id = "", gender = "";
    ProgressDialog progressDialog;
    private UserSharePreferancess userSharePreferancess;
    private Context context;
    private CircleImageView profileImage;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private File finalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        context = this;
        userSharePreferancess = new UserSharePreferancess(context);

        progressDialog = new ProgressDialog(RegisterationActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        Intent in = getIntent();
        user_id = in.getStringExtra("user_id");
        initViews();

        genderdgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.malebtmn:
                        if (malebtmn.getText().toString().equalsIgnoreCase("Male")) {
                            gender = "Male";
                        }
                        break;
                    case R.id.femalebtmn:
                        if (femalebtmn.getText().toString().equalsIgnoreCase("Female")) {
                            gender = "Female";

                        }
                        break;
                    case R.id.otherbtmn:
                        if (otherbtmn.getText().toString().equalsIgnoreCase("Other")) {
                            gender = "Other";

                        }
                        break;
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgstrValidation()) {
                    userRegister();

                }
            }
        });
    }

    private void userRegister() {
        progressDialog.show();
        MultipartBody.Part image1 = null;
        if (profileImage != null){
            try {
                if (finalFile.exists()) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
                    image1 = MultipartBody.Part.createFormData("file", finalFile.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            image1 = MultipartBody.Part.createFormData("file", "", attachmentEmpty);
        }

        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),  user_nivkname.getText().toString());
        RequestBody bios = RequestBody.create(MediaType.parse("multipart/form-data"),  bio.getText().toString());
        RequestBody gen = RequestBody.create(MediaType.parse("multipart/form-data"),  gender);


        Call<RegistrModule> call = RetrofitClient.getInstance().myInterFaceData().userUpdate(id,name,bios, gen,image1);
        call.enqueue(new Callback<RegistrModule>() {
            @Override
            public void onResponse(Call<RegistrModule> call, Response<RegistrModule> response) {
                progressDialog.dismiss();
                RegistrModule module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        userSharePreferancess.setStringValue("user_id",user_id);
                        Comman.getInstance().setUser_id(user_id);

                        userSharePreferancess.setStringValue("id", module.getId());
                        Comman.getInstance().setId(module.getId());

                        userSharePreferancess.setStringValue("username", module.getUsername());
                        Comman.getInstance().setUsername(module.getUsername());

                        userSharePreferancess.setStringValue("name", module.getNickName());
                        Comman.getInstance().setName(module.getNickName());

                        Toast.makeText(RegisterationActivity.this, "" + module.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(RegisterationActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterationActivity.this, "" + module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<RegistrModule> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });



    }

    private boolean rgstrValidation() {
        if (user_nivkname.getText().toString().length() == 0) {
            user_nivkname.setError("Please fill your name");
            user_nivkname.requestFocus();
            return false;
        } else if (user_nivkname.getText().toString().length() < 5) {
            user_nivkname.setError("Fill 5 to 8 words");
            user_nivkname.requestFocus();
            return false;
        }
        return true;


    }

    private void initViews() {
        register = findViewById(R.id.register);
        user_nivkname = findViewById(R.id.user_nivkname);
        bio = findViewById(R.id.user_region);
        genderdgrp = findViewById(R.id.genderdgrp);
        malebtmn = findViewById(R.id.malebtmn);
        femalebtmn = findViewById(R.id.femalebtmn);
        otherbtmn = findViewById(R.id.otherbtmn);
        profileImage = findViewById(R.id.image);
    }
}