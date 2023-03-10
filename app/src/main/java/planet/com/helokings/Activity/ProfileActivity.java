package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import planet.com.helokings.Login.MainActivity;
import planet.com.helokings.Model.RegistrModule;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityProfileactivityBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileactivityBinding profileactivityBinding;
    ProgressDialog progressDialog;
    private UserSharePreferancess userSharePreferancess;
    private Context context;
    String user_id = "", gender = "";
    String[] gender1 = {"Gender", "Male", "Female", "Other"};
    String[] Region = {"Region", "India", "PAK", "Nepal", "China", "AFG"};
    String[] State = {"State", "India", "Mumbai", "Agra", "UK", "Delhi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileactivityBinding = ActivityProfileactivityBinding.inflate(getLayoutInflater());
        setContentView(profileactivityBinding.getRoot());

        context = this;
        userSharePreferancess = new UserSharePreferancess(context);

        progressDialog = new ProgressDialog(ProfileActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Intent in = getIntent();
        user_id = in.getStringExtra("user_id");







        profileactivityBinding.layGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileactivityBinding.ganedrNewLay.getVisibility() == View.GONE) {
                    profileactivityBinding.ivDropDown.setVisibility(View.GONE);
                    profileactivityBinding.ivDropUp.setVisibility(View.VISIBLE);
                    expand();
                } else {
                    //  collapse();
                }
            }
        });
        profileactivityBinding.ivDropUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileactivityBinding.ivDropUp.setVisibility(View.GONE);
                profileactivityBinding.ivDropDown.setVisibility(View.VISIBLE);
                collapse();
            }
        });

        profileactivityBinding.tvMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileactivityBinding.tvGender.setText("Male");
                profileactivityBinding.ivDropUp.setVisibility(View.GONE);
                profileactivityBinding.ivDropDown.setVisibility(View.VISIBLE);
                collapse();
            }
        });
        profileactivityBinding.tvFemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileactivityBinding.tvGender.setText("FeMale");
                profileactivityBinding.ivDropUp.setVisibility(View.GONE);
                profileactivityBinding.ivDropDown.setVisibility(View.VISIBLE);
                collapse();
            }
        });
        profileactivityBinding.tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileactivityBinding.tvGender.setText("Other");
                profileactivityBinding.ivDropUp.setVisibility(View.GONE);
                profileactivityBinding.ivDropDown.setVisibility(View.VISIBLE);
                collapse();
            }
        });

        profileactivityBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
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
        Call<RegistrModule> call = RetrofitClient.getInstance().getAllApiResponse().userUpdate(Comman.getInstance().user_id,profileactivityBinding.etName.getText().toString(),profileactivityBinding.etBio.getText().toString(),profileactivityBinding.tvGender.getText().toString());
        call.enqueue(new Callback<RegistrModule>() {
            @Override
            public void onResponse(Call<RegistrModule> call, Response<RegistrModule> response) {
                progressDialog.dismiss();
                RegistrModule module = response.body();
                if (response.isSuccessful()){
                    if (module.getStatus().equalsIgnoreCase("true")){

                        userSharePreferancess.setStringValue("id", module.getId());
                        Comman.getInstance().setId(module.getId());

                        userSharePreferancess.setStringValue("username", module.getUsername());
                        Comman.getInstance().setUsername(module.getUsername());

                        userSharePreferancess.setStringValue("name", module.getNickName());
                        Comman.getInstance().setName(module.getNickName());

                        Toast.makeText(ProfileActivity.this, "" + module.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else {

                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "" + module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrModule> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });







    }

    private boolean rgstrValidation() {
        if (profileactivityBinding.etName.getText().toString().length() == 0) {
            profileactivityBinding.etName.setError("Please fill your name");
            profileactivityBinding.etName.requestFocus();
            return false;
        } else if (profileactivityBinding.etName.getText().toString().length() < 5) {
            profileactivityBinding.etName.setError("Fill 5 to 8 words");
            profileactivityBinding.etName.requestFocus();
            return false;
        }
        return true;

    }

    private void collapse() {
        int finalHeight = profileactivityBinding.ganedrNewLay.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                profileactivityBinding.ganedrNewLay.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        mAnimator.start();


    }

    private void expand() {
        profileactivityBinding.ganedrNewLay.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        profileactivityBinding.ganedrNewLay.measure(widthSpec, heightSpec);
        ValueAnimator mAnimator = slideAnimator(0, profileactivityBinding.ganedrNewLay.getMeasuredHeight());
        mAnimator.start();


    }

    private ValueAnimator slideAnimator(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = profileactivityBinding.ganedrNewLay.getLayoutParams();
                layoutParams.height = value;
                profileactivityBinding.ganedrNewLay.setLayoutParams(layoutParams);
            }
        });
        return animator;


    }
/*
    private void StateSpinner() {
        ArrayAdapter state_adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, State);
        profileactivityBinding.stateSpinner.setAdapter(state_adapter);
        profileactivityBinding.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sdata = parent.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void RegionSpinner() {
        ArrayAdapter spinnder = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Region);
        profileactivityBinding.regionSpinner.setAdapter(spinnder);

        profileactivityBinding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rdata = parent.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void genderSpinner() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, gender);
        profileactivityBinding.spinnerGender.setAdapter(spinnerAdapter);
        profileactivityBinding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ldata = parent.getAdapter().getItem(position).toString();
              *//*  ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER_VERTICAL);*//*

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/
}