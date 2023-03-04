package planet.com.helokings.VideoStreamingActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import planet.com.helokings.Activity.GoLiveActivity;
import planet.com.helokings.Login.LoginTypeActivity;
import planet.com.helokings.Login.SplashActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.AuthInfoManager;

import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.chat.ChatMessagePojo;
import planet.com.helokings.VideoStreamingActivity.chat.IMInputDialog;
import planet.com.helokings.VideoStreamingActivity.chat.MessageListAdapter;
import planet.com.helokings.VideoStreamingActivity.features.RoomDetailsFragment;
import planet.com.helokings.databinding.ActivityVideoHostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoHostActivity extends AppCompatActivity {


    Long appID;
    String userID;
    String appSign;
   public static String roomID;
    String publishStreamID;
    String playStreamID;
    ZegoExpressEngine engine;
    ActivityVideoHostBinding activityUserVideoBinding;
    ZegoCanvas zegoCanvas;
    IMInputDialog imInputDialog;
   public String profileFrame;
   public String profileImage;
   boolean ishostornot;
   String roomType;
    // Chat Socket Implementation
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://139.84.168.15:3000");
            Log.e("Socket", String.valueOf(mSocket));


        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserVideoBinding = ActivityVideoHostBinding.inflate(getLayoutInflater());
        // getting our root layout in our view.
        View view = activityUserVideoBinding.getRoot();
        roomID=getIntent().getStringExtra("roomid");
        userID=getIntent().getStringExtra("roomname");
        ishostornot=getIntent().getBooleanExtra("host",false);




        setContentView(view);




        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        new Handler().postDelayed(() -> {
            if (!Comman.getInstance().getUser_id().equalsIgnoreCase("")) {
                getProfile();
            } else {
                Intent intent = new Intent(VideoHostActivity.this, LoginTypeActivity.class);
                startActivity(intent);
                finish();
            }

        }, 600);








        updateUI();

          requestPermission();
        getAppIDAndUserIDAndAppSign();
        setDefaultValue();
        setCreateEngineButtonEvent();
        setLoginRoomButtonEvent();
        if (ishostornot){
            setStartPublishingButton();

        }else{
            setStartPlayingButton();

        }


        mSocket.on("roomData", onReceive);

        activityUserVideoBinding.liveHostIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 RoomDetailsFragment roomDetailsFragment =
                        new RoomDetailsFragment(Comman.getInstance().getUser_id(),userID,roomID,ishostornot);
                roomDetailsFragment.show(getSupportFragmentManager(),"");
            }
        });

        activityUserVideoBinding.ivIm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                    imInputDialog = new IMInputDialog(VideoHostActivity.this);
                    imInputDialog.setOnSendListener(imText -> {

                                JSONObject messageJson1 = new JSONObject();
                                try {
                                    messageJson1.put("name", Comman.getInstance().name);
                                    messageJson1.put("user_id", userID);
                                    messageJson1.put("image", profileImage);
                                    messageJson1.put("frame_image", profileFrame);
                                    messageJson1.put("font_family", "");
                                    messageJson1.put("text_buble", "");
                                    messageJson1.put("wall_papers", "");
                                    messageJson1.put("date", "14/11/2022");
                                    messageJson1.put("channel_no", roomID);
                                    messageJson1.put("message", imText);
                                    messageJson1.put("type", roomType);
                                    messageJson1.put("room_type", roomType);


                                } catch (Exception e) {

                                    Log.e("socket fail", " fff    " + e.getMessage());

                                }


                                mSocket.emit("sendMessage", messageJson1);
                                Log.e("socketmessage", " Send  " + messageJson1);

                            });

                    imInputDialog.show();
            }
        });


    }

    public void setDefaultValue(){


        roomID=getIntent().getStringExtra("roomid");
        publishStreamID=getIntent().getStringExtra("roomid");
        userID=getIntent().getStringExtra("roomname");
        roomType=getIntent().getStringExtra("roomtype");
        zegoCanvas = new ZegoCanvas(activityUserVideoBinding.PreviewView);
        zegoCanvas.viewMode = ZegoViewMode.ASPECT_FILL;


    }

    private void updateUI() {

        messageListAdapter = new MessageListAdapter(chatdata);
        activityUserVideoBinding.userDataRecyclerView.setAdapter(messageListAdapter);
        activityUserVideoBinding.userDataRecyclerView
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));




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



    public void socketconnection(){


        mSocket.on("connection", connection);
        mSocket.connect();


        joinSocket("joined");

    }


    void joinSocket(String msg){

        Toast.makeText(this, ""+profileFrame, Toast.LENGTH_SHORT).show();
        JSONObject messageJson = new JSONObject();
        try {
            messageJson.put("name",Comman.getInstance().name);
            messageJson.put("user_id", userID);
            messageJson.put("image", profileImage);
            messageJson.put("frame_image", profileFrame);
            messageJson.put("font_family", "");
            messageJson.put("text_buble", "");
            messageJson.put("wall_papers", "");
            messageJson.put("date", "14/11/2022");
            messageJson.put("channel_no", roomID);
            messageJson.put("message",Comman.getInstance().name+ " "+msg+" the room " );
            messageJson.put("type", roomType);
            messageJson.put("room_type", roomType);

        } catch (Exception e)
        {
            Log.e("socket joining time",""+e.getMessage());

        }
        if(msg.equalsIgnoreCase("leave")){
            Log.e("dididdi","donedis"+messageJson);
            mSocket.emit("disconnected", messageJson);
        }else{
            mSocket.emit("join", messageJson);
            Log.e("Socket Response","Join");
            Log.e("DATA",messageJson.toString());
        }


    }


    private Emitter.Listener connection = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.e("Socket connect", "Connected");

        }
    };


    private MessageListAdapter messageListAdapter;

    String model_id = "", slot_time = "", date = "", channel = "", type = "", EndTime = "", user_type = "", gif_image;
    String userName;
    ChatMessagePojo chatMessagePojo;
    List<ChatMessagePojo> chatdata = new ArrayList<>();


    private Emitter.Listener onReceive  = new Emitter.Listener() {
        @Override    public void call(final Object... args) {

            Log.e("socket","Recieved");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (args.length > 0) {
                        JSONObject chatData = (JSONObject) args[0];
                        Log.e("DATA", String.valueOf(chatData));
                        try {
                            JSONObject object = chatData.getJSONObject("data");
                            String user_id = object.getString("user_id");
                            String name = object.getString("name");
                            String text = object.getString("text");
                            String date = object.getString("date");
                            String image = object.getString("image");
                            String frame = object.getString("frame_image");
                            String font_family = object.getString("font_family");
                            String text_buble = object.getString("text_buble");
                            String wall_papers = object.getString("wall_papers");
                            String type = object.getString("type");
                            //    String roomTyp = object.getString("room_type");
                            String audience = object.getString("audience");
                            Log.e("textaman",""+text);

                            if(type.equalsIgnoreCase("Gift")){
                                showgift(1,frame,name,text,image);
                                chatMessagePojo = new ChatMessagePojo(model_id, userID,"Gift", text, text+" recived gift from "+name, image,"", type);
                                chatdata.add(chatMessagePojo);
                            }else {

                                chatMessagePojo = new ChatMessagePojo(model_id, userID,name, text, text, image,"", type);
                                chatdata.add(chatMessagePojo);
                            }



                            messageListAdapter.notifyItemInserted(chatdata.size());
                            activityUserVideoBinding.userDataRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
                            //  refreshMessageList();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("socket get room data", "" + e.getMessage());

                        }
                    }else{
                        Log.e("Socket room data null",""+args.length);
                    }
                }
            });

        }};


    String senderTxt="",receiverTxt="";
    private Runnable hideGiftTips = () -> {
//        binding.tvGiftToast.setText("");
//        binding.tvGiftToast.setVisibility(View.INVISIBLE);
    };

    private void showgift(int callStatus,String frame,String userID,String touser,String giftName) {



        if(callStatus==1){
            String giftTips="";
            giftTips =   touser+" received gift " + giftName.split("HeloKing")[0] + " from "+userID;

            if (userName.equals(userID)){
                giftTips =   touser+" received gift " + giftName.split("HeloKing")[0] + " from you";

            }

            if (touser.equals(userName)){
                giftTips =  "Host received gift " + giftName.split("HeloKing")[0] + " from "+userID;

            }

            SpannableString string = new SpannableString(giftTips);
            ForegroundColorSpan yellowSpan = new ForegroundColorSpan(
                    ContextCompat.getColor(this, R.color.red)
            );

            int indexOfGiftName = giftTips.indexOf(giftName.split("HeloKing")[0]);
            string.setSpan(yellowSpan, indexOfGiftName,
                    indexOfGiftName + giftName.split("HeloKing")[0].length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

//            binding.tvGiftToast.setVisibility(View.VISIBLE);
//            binding.tvGiftToast.setText(string);
//            binding.tvGiftToast.removeCallbacks(hideGiftTips);
//            binding.tvGiftToast.postDelayed(hideGiftTips, 10_000L);
            animateObject(giftName.split("HeloKing")[1]);
        }





    }

    void animateObject(String image){
        Glide.with(this).load(image).into(activityUserVideoBinding.giftImage);
        activityUserVideoBinding.giftImage.setVisibility(View.VISIBLE);



        new Handler().postDelayed(() -> {
            activityUserVideoBinding.giftImage.setVisibility(View.GONE);

        }, 2000);


    }

    @Override
    protected void onStart() {
        super.onStart();



    }


public void getProfile(){

    Call<ResponseProfile> mCall = RetrofitClient.getInstance().getAllApiResponse().getUserProfile(
            Comman.getInstance().getUser_id());

    mCall.enqueue(new Callback<ResponseProfile>() {
        @Override
        public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    if(response.body().getData().getFrame_image()!=null&&!response.body().getData().getFrame_image().equalsIgnoreCase("")) {

                        profileFrame = response.body().getData().getFrame_image();
                    }else{
                        profileFrame = "no frame";

                    }

                    if(response.body().getData().getImage()!=null&&!response.body().getData().getImage().equalsIgnoreCase("")) {
                        profileImage = response.body().getData().getImage();

                    }else{
                        profileImage = "no image ";
                    }


                } else{
                    Toast.makeText(VideoHostActivity.this, "nondta", Toast.LENGTH_SHORT).show();
                }
                socketconnection();
            }

        }

        @Override
        public void onFailure(Call<ResponseProfile> call, Throwable t) {
            Toast.makeText(VideoHostActivity.this, "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
    public void setStartPlayingButton(){

        if (engine == null) {
             return;
        }
        // Start playing stream
        engine.startPlayingStream(publishStreamID, zegoCanvas);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        joinSocket("leave");
        engine.logoutRoom(roomID);
        engine.destroyEngine(null);

    }
}