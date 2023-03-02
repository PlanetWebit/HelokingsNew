package planet.com.helokings.VideoStreamingActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONObject;

import im.zego.zegoexpress.ZegoExpressEngine;

import im.zego.zegoexpress.callback.IZegoApiCalledEventHandler;

import im.zego.zegoexpress.callback.IZegoEventHandler;

import im.zego.zegoexpress.constants.ZegoPlayerState;

import im.zego.zegoexpress.constants.ZegoPublisherState;

import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;

import im.zego.zegoexpress.constants.ZegoScenario;

import im.zego.zegoexpress.constants.ZegoViewMode;

import im.zego.zegoexpress.entity.ZegoCanvas;

import im.zego.zegoexpress.entity.ZegoEngineProfile;

import im.zego.zegoexpress.entity.ZegoUser;

import planet.com.helokings.SharedPref.AuthInfoManager;

import planet.com.helokings.databinding.ActivityVideoHostBinding;

public class VideoHostActivity extends AppCompatActivity {


TextureView preview;

    Long appID;
    String userID;
    String appSign;
    String roomID;
    String publishStreamID;
    String playStreamID;
    ZegoExpressEngine engine;
    ActivityVideoHostBinding activityUserVideoBinding;
    ZegoCanvas zegoCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserVideoBinding = ActivityVideoHostBinding.inflate(getLayoutInflater());
        // getting our root layout in our view.
        View view = activityUserVideoBinding.getRoot();

        setContentView(view);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

         requestPermission();
        getAppIDAndUserIDAndAppSign();
        setDefaultValue();
        setCreateEngineButtonEvent();
        setLoginRoomButtonEvent();
        setStartPublishingButton();



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


    //get appID and userID and appSign
    public void getAppIDAndUserIDAndAppSign(){
        appID = AuthInfoManager.getInstance().getAppID();
         appSign = AuthInfoManager.getInstance().getAppSign();
    }
    public void setDefaultValue(){


        roomID=getIntent().getStringExtra("roomid");
        publishStreamID=getIntent().getStringExtra("roomid");
        userID=getIntent().getStringExtra("roomname");
        zegoCanvas = new ZegoCanvas(activityUserVideoBinding.PreviewView);
        zegoCanvas.viewMode = ZegoViewMode.ASPECT_FILL;


    }



    public void setCreateEngineButtonEvent(){

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

        // createEngineButton.setText(ZegoViewUtil.GetEmojiStringByUnicode(ZegoViewUtil.checkEmoji)+getString(R.string.create_engine));
        setApiCalledResult();
        setEventHandler();

    }
    public void setLoginRoomButtonEvent(){

        if (engine == null) {

         return;
        }
        ;
        // Create user
        ZegoUser user = new ZegoUser(userID);

        // Begin to login room
        engine.loginRoom(roomID, user);

    }
    public void setStartPublishingButton(){

        if (engine == null) {
             return;
        }
         // Start publishing stream
        engine.startPublishingStream(publishStreamID);
        // Start preview and set local preview
        engine.startPreview(zegoCanvas);


    }
    public void setApiCalledResult(){
        ZegoExpressEngine.setApiCalledCallback(new IZegoApiCalledEventHandler() {
            @Override
            public void onApiCalledResult(int errorCode, String funcName, String info) {
                super.onApiCalledResult(errorCode, funcName, info);
                if (errorCode == 0){
                 } else {
                 }
            }
        });
    }
    public void setEventHandler(){


         ZegoExpressEngine.getEngine().setEventHandler(new IZegoEventHandler() {
            @Override
            public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
                super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
                if (state == ZegoPublisherState.PUBLISHING) {
                   // startPublishingButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.checkEmoji) + getString(R.string.start_publishing));
                } else if (state == ZegoPublisherState.NO_PUBLISH){
                   // startPublishingButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.crossEmoji) + getString(R.string.start_publishing));
                }
            }

            @Override
            public void onPlayerStateUpdate(String streamID, ZegoPlayerState state, int errorCode, JSONObject extendedData) {
                super.onPlayerStateUpdate(streamID, state, errorCode, extendedData);
                if (streamID.equals(playStreamID)){
                    if (state == ZegoPlayerState.PLAYING) {
                       // startPlayingButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.checkEmoji) + getString(R.string.start_playing));
                    } else if (state == ZegoPlayerState.NO_PLAY){
                       // startPlayingButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.crossEmoji) + getString(R.string.start_playing));
                    }
                }
            }

            @Override
            public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int errorCode, JSONObject extendedData) {
                if (roomID.equals(roomID)){
                    if(reason == ZegoRoomStateChangedReason.LOGINED || reason == ZegoRoomStateChangedReason.RECONNECTED)
                    {
                        //loginRoomButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.checkEmoji)+getString(R.string.login_room));
                    }
                    else
                    {
                       // loginRoomButton.setText(GetEmojiStringByUnicode(ZegoViewUtil.crossEmoji)+getString(R.string.login_room));
                    }
                }
            }

        });
    }
    private String GetEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}