package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.skydoves.elasticviews.ElasticButton;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.RoomData.ResponseRoom;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.VideoStreamingActivity.AudioStreamingActivity;
import planet.com.helokings.helper.PermissionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAudioRoomActivity extends AppCompatActivity {
    private EditText room_name;
    private EditText room_desc;
    private ElasticButton btnLogin;

    // login info
    private String userID;
    private String roomId;
    private String userName;
    CircleImageView room_image;
    LinearLayout pickImage;
    ImageView chooseImage;
    private File finalFile;
    ProgressDialog progressDialog;

    private UserSharePreferancess userSharePreferancess;

    String room_tag="Chat";
    TextView chatTag,danceTag,musictag,shayariTag,enjoyTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_audio_room);

        setTheme(R.style.Theme_HeloKings);
        setContentView(R.layout.activity_create_audio_room);

        progressDialog = new ProgressDialog(CreateAudioRoomActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        userSharePreferancess = new UserSharePreferancess(this);


        initUI();
        PermissionHelper.requestRecordAudio(this, null);



    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        room_name = findViewById(R.id.room_name);
        room_desc = findViewById(R.id.room_desc);
        btnLogin = findViewById(R.id.btn_login);
        room_image = findViewById(R.id.room_image);
        chooseImage = findViewById(R.id.chooseImage);
        pickImage = findViewById(R.id.pick_image);
        chatTag = findViewById(R.id.chatTag);
        danceTag = findViewById(R.id.danceTag);
        musictag = findViewById(R.id.musicTag);
        shayariTag = findViewById(R.id.shayaritag);
        enjoyTag = findViewById(R.id.enjoyTag);


        chatTag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagLayout(0);
                    }
                }
        );
        danceTag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagLayout(1);
                    }
                }
        );
        musictag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagLayout(2);
                    }
                }
        );
        shayariTag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagLayout(3);
                    }
                }
        );
        enjoyTag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagLayout(4);
                    }
                }
        );
        btnLogin.setOnClickListener(v -> {
            userID = Comman.getInstance().getId();
            userName = room_name.getText().toString();

            if(roomValidation()){
                createRoom();
            }

        });

        room_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        findViewById(R.id.back).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

    }

    void selectImage(){
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }
    void tagLayout(int i){
        if(i==0){
            chatTag.setBackgroundResource(R.drawable.btn_bg);
            danceTag.setBackgroundResource(R.drawable.buttongray);
            musictag.setBackgroundResource(R.drawable.buttongray);
            shayariTag.setBackgroundResource(R.drawable.buttongray);
            enjoyTag.setBackgroundResource(R.drawable.buttongray);

            chatTag.setTextColor(getResources().getColor(R.color.white));
            danceTag.setTextColor(getResources().getColor(R.color.grayDark));
            musictag.setTextColor(getResources().getColor(R.color.grayDark));
            shayariTag.setTextColor(getResources().getColor(R.color.grayDark));
            enjoyTag.setTextColor(getResources().getColor(R.color.grayDark));

            room_tag="Chat";
        }else if(i==1){

            chatTag.setBackgroundResource(R.drawable.buttongray);
            danceTag.setBackgroundResource(R.drawable.btn_bg);
            musictag.setBackgroundResource(R.drawable.buttongray);
            shayariTag.setBackgroundResource(R.drawable.buttongray);
            enjoyTag.setBackgroundResource(R.drawable.buttongray);

            chatTag.setTextColor(getResources().getColor(R.color.grayDark));
            danceTag.setTextColor(getResources().getColor(R.color.white));
            musictag.setTextColor(getResources().getColor(R.color.grayDark));
            shayariTag.setTextColor(getResources().getColor(R.color.grayDark));
            enjoyTag.setTextColor(getResources().getColor(R.color.grayDark));
            room_tag="Dance";

        }else if(i==2){

            chatTag.setBackgroundResource(R.drawable.buttongray);
            danceTag.setBackgroundResource(R.drawable.buttongray);
            musictag.setBackgroundResource(R.drawable.btn_bg);
            shayariTag.setBackgroundResource(R.drawable.buttongray);
            enjoyTag.setBackgroundResource(R.drawable.buttongray);

            chatTag.setTextColor(getResources().getColor(R.color.grayDark));
            danceTag.setTextColor(getResources().getColor(R.color.grayDark));
            musictag.setTextColor(getResources().getColor(R.color.white));
            shayariTag.setTextColor(getResources().getColor(R.color.grayDark));
            enjoyTag.setTextColor(getResources().getColor(R.color.grayDark));
            room_tag="Music";

        }else if(i==3){

            chatTag.setBackgroundResource(R.drawable.buttongray);
            danceTag.setBackgroundResource(R.drawable.buttongray);
            musictag.setBackgroundResource(R.drawable.buttongray);
            shayariTag.setBackgroundResource(R.drawable.btn_bg);
            enjoyTag.setBackgroundResource(R.drawable.buttongray);

            chatTag.setTextColor(getResources().getColor(R.color.grayDark));
            danceTag.setTextColor(getResources().getColor(R.color.grayDark));
            musictag.setTextColor(getResources().getColor(R.color.grayDark));
            shayariTag.setTextColor(getResources().getColor(R.color.white));
            enjoyTag.setTextColor(getResources().getColor(R.color.grayDark));
            room_tag="Shayari";

        }else if(i==4){

            chatTag.setBackgroundResource(R.drawable.buttongray);
            danceTag.setBackgroundResource(R.drawable.buttongray);
            musictag.setBackgroundResource(R.drawable.buttongray);
            shayariTag.setBackgroundResource(R.drawable.buttongray);
            enjoyTag.setBackgroundResource(R.drawable.btn_bg);

            chatTag.setTextColor(getResources().getColor(R.color.grayDark));
            danceTag.setTextColor(getResources().getColor(R.color.grayDark));
            musictag.setTextColor(getResources().getColor(R.color.grayDark));
            shayariTag.setTextColor(getResources().getColor(R.color.grayDark));
            enjoyTag.setTextColor(getResources().getColor(R.color.white));
            room_tag="Enjoy";

        }
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void createRoom() {
        progressDialog.show();

        MultipartBody.Part image1 = null;
        if (room_image != null) {
            try {
                if (finalFile.exists()) {
                    Toast.makeText(this, ""+finalFile, Toast.LENGTH_SHORT).show();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
                    image1 = MultipartBody.Part.createFormData("room_image", finalFile.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            image1 = MultipartBody.Part.createFormData("room_image", "", attachmentEmpty);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), Comman.getInstance().getUser_id());
        RequestBody rmNmae = RequestBody.create(MediaType.parse("multipart/form-data"), room_name.getText().toString());
        RequestBody rmTag = RequestBody.create(MediaType.parse("multipart/form-data"), room_tag);
        RequestBody rmAnosnmt = RequestBody.create(MediaType.parse("multipart/form-data"), room_desc.getText().toString());
        RequestBody review = RequestBody.create(MediaType.parse("multipart/form-data"), "false");
        RequestBody user_level = RequestBody.create(MediaType.parse("multipart/form-data"), "0");

        Call<ResponseRoom> moduleCall = RetrofitClient.getInstance().getAllApiResponse()
                .crRoom(user_id, rmNmae, rmTag, rmAnosnmt, image1, review, user_level);
        moduleCall.enqueue(new Callback<ResponseRoom>() {
            @Override
            public void onResponse(Call<ResponseRoom> call, Response<ResponseRoom> response) {
                progressDialog.dismiss();
                ResponseRoom module = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(CreateAudioRoomActivity.this, "done", Toast.LENGTH_SHORT).show();
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        roomId = Comman.getInstance().getUsername();
                        Intent intent =new Intent(CreateAudioRoomActivity.this, AudioStreamingActivity.class);
                        intent.putExtra("roomid",roomId);
                        intent.putExtra("userid", Comman.getInstance().getUsername());
                        intent.putExtra("username", Comman.getInstance().getName());
                        intent.putExtra("roomtype","audio");
                        intent.putExtra("host",true);
                        finish();
                        startActivity(intent);

                    } else{
                        if(module.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseRoom> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateAudioRoomActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private boolean roomValidation() {
        if (finalFile == null) {
            Toast.makeText(this, "Please select room file picture", Toast.LENGTH_SHORT).show();
            return false;
        } else if (room_name.getText().toString().length() == 0) {
            room_name.setError("Please enter room name");
            room_name.requestFocus();
            return false;
        } else if (room_name.getText().toString().length() < 5) {
            room_name.setError("Fill 5 to 8 words");
            room_name.requestFocus();
            return false;
        } else if (room_desc.getText().toString().length() == 0) {
            room_desc.setError("Please write about your room");
            room_desc.requestFocus();
            return false;

        }
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            room_image.setImageURI(selectedImage);
            finalFile = new File(selectedImage.getPath());
            room_image.setVisibility(View.VISIBLE);
            chooseImage.setVisibility(View.GONE);
        }

    }


}