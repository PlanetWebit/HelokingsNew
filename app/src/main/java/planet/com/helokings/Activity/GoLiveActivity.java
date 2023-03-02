package planet.com.helokings.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 import planet.com.helokings.R;
import planet.com.helokings.SharedPref.AuthInfoManager;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityGoLiveBinding;

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
        setContentView(root);


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

                activityGoLiveBinding.crPckimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissionForReadExtertalStorage()) {

                            ImagePicker.with(GoLiveActivity.this)
                                    .crop()
                                    .compress(1024)
                                    .maxResultSize(1080, 1080)
                                    .start();


                        } else {

                            requestPermission();

                        }
                    }
                });
            }
        });

        activityGoLiveBinding.golive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (flag == 1){



                   // createRoomLive();

//                if (arrOfStr.length ==3){
//                    Toast.makeText(GoLiveActivity.this, "UPpoad Room Image", Toast.LENGTH_SHORT).show();
//                }else{
//
//                }











                }
                if (flag == 3){


                  //  createRoom();

//                    if (arrOfStr.length ==3){
//                        Toast.makeText(GoLiveActivity.this, "UPpoad Room Image", Toast.LENGTH_SHORT).show();
//                    }else{
//                        createRoom();
//
//                    }












                }


            }
        });

    }



    public void getAppIDAndUserIDAndAppSign(){
        appID = AuthInfoManager.getInstance().getAppID();
        userID = "000002";
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
        engine.loginRoom("000002", user);
        //   AppLogger.getInstance().callApi("LoginRoom: %s",roomID);
        //enable the camera
        engine.enableCamera(true);
        //enable the microphone
        engine.muteMicrophone(false);
        //enable the speaker
        engine.muteSpeaker(false);
        //engine.startPreview(previewCanvas);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            visit_image.setImageURI(selectedImage);

            finalFile = new File(selectedImage.getPath());
            Toast.makeText(this, ""+finalFile, Toast.LENGTH_SHORT).show();
            visit_image.setImageURI(selectedImage);
        }
    }


    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

}