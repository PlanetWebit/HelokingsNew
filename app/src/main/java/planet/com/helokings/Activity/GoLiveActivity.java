package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
 import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.constants.ZegoViewMode;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoEngineProfile;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoUser;
import im.zego.zegoexpress.entity.ZegoVideoConfig;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.RoomData.ResponseRoom;
import planet.com.helokings.Pojo.RoomDetails.ResponseRoomDetails;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.AuthInfoManager;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.VideoStreamingActivity.VideoHostActivity;
import planet.com.helokings.databinding.ActivityGoLiveBinding;
import planet.com.helokings.databinding.RuleWarningDialogueBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoLiveActivity extends AppCompatActivity {
    LinearLayout tvlive, lvvideoparty;
    String imageurl = "";
    TextView golive;
    int flag = 1;
    String[] arrOfStr;
    ProgressDialog progressDialog;

    RelativeLayout gopartyActivity;
    RelativeLayout frameLayout;
    String roomId;
    LinearLayout tvliveback, tvparrtyback;
    ImageView visit_image;
    private File finalFile = null;
    BottomSheetDialog mBottomSheetDialogsetting;
    private static final int PERMISSION_REQUEST_CODE = 200;
    LinearLayout layoutchair;
    private TextView roomName;
    private TextView roomID;
    ImageView networkstats, switchcamera;
    ZegoRoomConfig zegoRoomConfig = new ZegoRoomConfig();
    Boolean cameraTYpe = false;
    ZegoCanvas previewCanvas,previewCAnvas2;
    TextureView preview;
    private UserSharePreferancess userSharePreferancess;
    ActivityGoLiveBinding activityGoLiveBinding;




    ZegoExpressEngine engine;
    Long appID;
    String userID;
    String appSign;
    ZegoUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGoLiveBinding = ActivityGoLiveBinding.inflate(getLayoutInflater());
        View root = activityGoLiveBinding.getRoot();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(root);
        progressDialog=new ProgressDialog(this);

        getRoom();

        previewCanvas = new ZegoCanvas(activityGoLiveBinding.PreviewView);
        previewCanvas.backgroundColor = Color.WHITE;
        previewCanvas.viewMode = ZegoViewMode.ASPECT_FILL;


        bottomNEtworkStting();
        requestPermission();
        getAppIDAndUserIDAndAppSign();
        initEngineAndUser();
        LoginRoom();



        engine.startPreview(previewCanvas);


        activityGoLiveBinding.networkstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialogsetting.show();
            }
        });

        activityGoLiveBinding.switchcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cameraTYpe){
                     engine.useFrontCamera(true);
                    cameraTYpe=false;
                }else{

                    engine.useFrontCamera(false);
                    cameraTYpe=true;
                }


            }
        });

        activityGoLiveBinding.tvlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityGoLiveBinding.PreviewView1.setVisibility(View.GONE);
                activityGoLiveBinding.PreviewView.setVisibility(View.VISIBLE);
                activityGoLiveBinding.videoliveclickback.setBackgroundDrawable(ContextCompat.getDrawable(GoLiveActivity.this, R.drawable.vip_bg) );
                activityGoLiveBinding.videopartyclickback.setBackgroundDrawable(ContextCompat.getDrawable(GoLiveActivity.this, R.drawable.circletrasnaoarent) );

                activityGoLiveBinding.crPckimage.setClickable(false);
                activityGoLiveBinding.videopartypart1.setVisibility(View.GONE);


                engine.stopPreview();

                previewCanvas = new ZegoCanvas(activityGoLiveBinding.PreviewView);
                previewCanvas.backgroundColor = Color.WHITE;
                previewCanvas.viewMode = ZegoViewMode.ASPECT_FILL;
                engine.startPreview(previewCanvas);

                flag =1;

                activityGoLiveBinding.videoedit.setVisibility(View.VISIBLE);
            }
        });

        activityGoLiveBinding.lvvideoparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityGoLiveBinding.videoedit.setVisibility(View.GONE);
                activityGoLiveBinding.PreviewView1.setVisibility(View.VISIBLE);
                activityGoLiveBinding.PreviewView.setVisibility(View.GONE);
                activityGoLiveBinding.PreviewView1.setBackground(getResources().getDrawable(R.drawable.splash));
                activityGoLiveBinding.videopartyclickback.setBackgroundDrawable(ContextCompat.getDrawable(GoLiveActivity.this, R.drawable.vip_bg) );
                activityGoLiveBinding.videoliveclickback.setBackgroundDrawable(ContextCompat.getDrawable(GoLiveActivity.this, R.drawable.circletrasnaoarent) );
                activityGoLiveBinding.crPckimage.setClickable(true);
                activityGoLiveBinding.videopartypart1.setVisibility(View.VISIBLE);




                engine.stopPreview();
                previewCanvas = new ZegoCanvas(activityGoLiveBinding.gopartyActivity);
                previewCanvas.backgroundColor = Color.WHITE;
                previewCanvas.viewMode = ZegoViewMode.ASPECT_FILL;


                engine.startPreview(previewCanvas);



                flag =3;


            }
        });


        activityGoLiveBinding.crPckimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Toast.makeText(GoLiveActivity.this, "hi", Toast.LENGTH_SHORT).show();

                    ImagePicker.with(GoLiveActivity.this)
                            .crop()
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .start();


                } else {

                    requestPermission1();

                }
            }
        });
        activityGoLiveBinding.golive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Dialog  dialog = new Dialog(GoLiveActivity.this, R.style.MyDialogStyle);
                RuleWarningDialogueBinding underStandDialogBinding = RuleWarningDialogueBinding.inflate(getLayoutInflater());
                dialog.setContentView(underStandDialogBinding.getRoot());

                underStandDialogBinding.iundersatnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag == 1){



                            createRoomLive();
                        }
                        if (flag == 3){


                            //  createRoom();




                        }
                    }
                });





                        dialog.show();





            }
        });

    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    public void requestPermission1() {

        ActivityCompat.requestPermissions(GoLiveActivity.this,
                new String[]{Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.READ_MEDIA_IMAGES},
                0);


    }
     @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     }


    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
    }


    public void getAppIDAndUserIDAndAppSign(){
        appID = AuthInfoManager.getInstance().getAppID();
        userID = Comman.getInstance().username;
        appSign = AuthInfoManager.getInstance().getAppSign();
    }
    //initial Engine and user
    public void initEngineAndUser(){
        // Initialize ZegoExpressEngine
        ZegoEngineProfile profile = new ZegoEngineProfile();
        profile.appID = appID;
        profile.appSign = appSign;

        // Here we use the high quality video call scenario as an example,
        // you should choose the appropriate scenario according to your actual situation,
        // for the differences between scenarios and how to choose a suitable scenario,
        // please refer to https://docs.zegocloud.com/article/14940
        profile.scenario = ZegoScenario.HIGH_QUALITY_VIDEO_CALL;

        profile.application = getApplication();
        engine = ZegoExpressEngine.createEngine(profile, null);

        //create the user
        user = new ZegoUser(userID);
    }
    public void LoginRoom(){
        //login room
        engine.loginRoom(Comman.getInstance().username, user);
        //   AppLogger.getInstance().callApi("LoginRoom: %s",roomID);
        //enable the camera
        engine.enableCamera(true);
        //enable the microphone
        engine.muteMicrophone(false);
        //enable the speaker
        engine.muteSpeaker(false);
        //engine.startPreview(previewCanvas);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            activityGoLiveBinding.crPckimage.setImageURI(selectedImage);

            finalFile = new File(selectedImage.getPath());
            Toast.makeText(this, ""+finalFile, Toast.LENGTH_SHORT).show();
            activityGoLiveBinding.crPckimage.setImageURI(selectedImage);
        }
    }


    public void requestPermission() {
        String[] PERMISSIONS_STORAGE = {
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO"};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS_STORAGE, 101);
            }
        }
    }









    CardView uhdq, hdq, mdq, ldq;
    ZegoVideoConfig zegoVideoConfig = new ZegoVideoConfig();

    private void bottomNEtworkStting() {


        mBottomSheetDialogsetting = new BottomSheetDialog(this);


        mBottomSheetDialogsetting.getDismissWithAnimation();
        View sheetView1 = getLayoutInflater().inflate(R.layout.bottomnetwrok, null);


        mBottomSheetDialogsetting.setContentView(sheetView1);

        uhdq = mBottomSheetDialogsetting.findViewById(R.id.uhdq);
        hdq = mBottomSheetDialogsetting.findViewById(R.id.hdq);
        mdq = mBottomSheetDialogsetting.findViewById(R.id.mdq);
        ldq = mBottomSheetDialogsetting.findViewById(R.id.ldq);
        ldq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ldq.setCardBackgroundColor(getResources().getColor(R.color.light_blue));
                mdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                hdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                uhdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                zegoVideoConfig.setEncodeResolution(180, 320);
                engine.setVideoConfig(zegoVideoConfig);
            }
        });

        mdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ldq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                mdq.setCardBackgroundColor(getResources().getColor(R.color.light_blue));
                hdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                uhdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                zegoVideoConfig.setEncodeResolution(540,960);
                engine.setVideoConfig(zegoVideoConfig);
            }
        });

        hdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ldq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                mdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                hdq.setCardBackgroundColor(getResources().getColor(R.color.light_blue));
                uhdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                zegoVideoConfig.setEncodeResolution(720,1280);
                engine.setVideoConfig(zegoVideoConfig);
            }
        });
        uhdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ldq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                mdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                hdq.setCardBackgroundColor(getResources().getColor(R.color.dark_blue));
                uhdq.setCardBackgroundColor(getResources().getColor(R.color.light_blue));
                zegoVideoConfig.setEncodeResolution(1080, 1920);
                engine.setVideoConfig(zegoVideoConfig);
            }
        });
    }


    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }


    private void createRoomLive() {


        progressDialog.show();

        MultipartBody.Part image1 = null;

        roomId = Comman.getInstance().getUsername();


        if (activityGoLiveBinding.crPckimage != null) {
            try {
                if (finalFile.exists()) {

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
                    image1 = MultipartBody.Part.createFormData("room_image", finalFile.getName(), requestBody);
                }else{

                    RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), imageurl);
                    image1 = MultipartBody.Part.createFormData("room_image", "", attachmentEmpty);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {


            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), imageurl);
            image1 = MultipartBody.Part.createFormData("room_image", "", attachmentEmpty);


        }

        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), Comman.getInstance().getUser_id());
        RequestBody rmNmae = RequestBody.create(MediaType.parse("multipart/form-data"), roomId);
        RequestBody rmTag = RequestBody.create(MediaType.parse("multipart/form-data"), "i am tag");
        RequestBody rmAnosnmt = RequestBody.create(MediaType.parse("multipart/form-data"), "i am desc");
        RequestBody review = RequestBody.create(MediaType.parse("multipart/form-data"), "userReview dvs gre");
        RequestBody user_level = RequestBody.create(MediaType.parse("multipart/form-data"), "userLevelData 4tfg werger ");
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "live");

        Call<ResponseRoom> moduleCall = RetrofitClient.getInstance().getAllApiResponse()
                .crRoompartyvideo(user_id, rmNmae, rmTag, rmAnosnmt, image1, review, user_level,type);
        moduleCall.enqueue(new Callback<ResponseRoom>() {
            @Override
            public void onResponse(Call<ResponseRoom> call, Response<ResponseRoom> response) {
                progressDialog.dismiss();
                ResponseRoom module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        Intent intent =new Intent(GoLiveActivity.this, VideoHostActivity.class);
                        intent.putExtra("roomid",roomId);
                        intent.putExtra("roomname",Comman.getInstance().getUsername());
                        intent.putExtra("roomtype","live");
                        intent.putExtra("host",true);
                        finish();
                        startActivity(intent);


                    } else{
                        if(module.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(GoLiveActivity.this, LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseRoom> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GoLiveActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });





    }



    private void getRoom() {
        String roomInfo =Comman.getInstance().getUsername();
         Call<ResponseRoomDetails> moduleCall = RetrofitClient.getInstance().getAllApiResponse().
                roomDetailsVideo(Comman.getInstance().getUser_id(),roomInfo,"live");
        moduleCall.enqueue(new Callback<ResponseRoomDetails>() {
            @Override
            public void onResponse(Call<ResponseRoomDetails> call, Response<ResponseRoomDetails> response) {
                ResponseRoomDetails module = response.body();
                if (response.isSuccessful()) {
                     if (module.getStatus().equalsIgnoreCase("true")) {

                         Toast.makeText(GoLiveActivity.this, "sixze "+response.body().getData().size(), Toast.LENGTH_SHORT).show();
                         if(response.body().getData().size()>0){

                            Toast.makeText(GoLiveActivity.this, "wds", Toast.LENGTH_SHORT).show();
                            activityGoLiveBinding.roomName.setText(response.body().getData().get(0).getRoomName());
                            activityGoLiveBinding.roomName.setText("ID: "+response.body().getData().get(0).getRoomId());

                            Toast.makeText(GoLiveActivity.this, ""+response.body().getData().get(0).getRoomImage(), Toast.LENGTH_SHORT).show();
                            Glide.with(GoLiveActivity.this).load(response.body().getData().get(0).getRoomImage()).placeholder(R.drawable.splash).into(activityGoLiveBinding.crPckimage);


                            imageurl=response.body().getData().get(0).getRoomImage();
                            arrOfStr = imageurl.split("/");
                            Toast.makeText(GoLiveActivity.this, ""+imageurl, Toast.LENGTH_LONG).show();
                            Log.e("imgerror",arrOfStr.length+"              "+response.body().getData().get(0).getRoomImage());
                            if (arrOfStr.length ==3 ){
                                Toast.makeText(GoLiveActivity.this, "Upload Image", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else{

                        Toast.makeText(GoLiveActivity.this,module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }
                else{


                    Toast.makeText(GoLiveActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseRoomDetails> call, Throwable t) {

                Log.e("fetchoing imA",""+t.getMessage());
            }
        });
    }
}