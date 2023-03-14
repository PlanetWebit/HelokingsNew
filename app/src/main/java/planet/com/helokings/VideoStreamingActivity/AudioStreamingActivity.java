package planet.com.helokings.VideoStreamingActivity;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.fenchtose.tooltip.Tooltip;
import com.fenchtose.tooltip.TooltipAnimation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoApiCalledEventHandler;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.callback.IZegoIMSendCustomCommandCallback;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRoomState;
import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.constants.ZegoUpdateType;
import im.zego.zegoexpress.constants.ZegoViewMode;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoEngineProfile;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoRoomExtraInfo;
import im.zego.zegoexpress.entity.ZegoStream;
import im.zego.zegoexpress.entity.ZegoUser;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Adapter.LiveNowAdapter;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.AdminSeatListAdapter;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.SeatListAdapter;

import planet.com.helokings.Login.LoginTypeActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.AuthInfoManager;
import planet.com.helokings.SharedPref.Comman;

import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.ZegoSpeakerSeatModel;
import planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel.ZegoSpeakerSeatStatus;
import planet.com.helokings.VideoStreamingActivity.chat.ChatMessagePojo;
import planet.com.helokings.VideoStreamingActivity.chat.IMInputDialog;
import planet.com.helokings.VideoStreamingActivity.chat.MessageListAdapter;
import planet.com.helokings.VideoStreamingActivity.features.RoomDetailsFragment;
import planet.com.helokings.VideoStreamingActivity.roomUserDetails.UserProfileFragment;
import planet.com.helokings.VideoStreamingActivity.userlists.SeatRequestListAdapter;
import planet.com.helokings.databinding.ActivityAudioStreamingBinding;
import planet.com.helokings.databinding.SeatqueuelistandapplyforseatBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioStreamingActivity extends AppCompatActivity {

    ArrayList<ZegoUser> sendCommandtoHostlist = new ArrayList<>();
    ArrayList<ZegoUser> userlist = new ArrayList<>();
    ArrayList<ZegoUser> userappliedforseat = new ArrayList<>();




    public List<ZegoSpeakerSeatModel> seatList=new ArrayList<>();
    public List<ZegoSpeakerSeatModel> seatListhost=new ArrayList<>();

    private SeatListAdapter seatListAdapter;
    private AdminSeatListAdapter adminSeatListAdapter;
    Long appID;
    String userID,username;
    String appSign;
    public  String roomId;
    String publishStreamID;
    String playStreamID;
    ZegoExpressEngine engine;
    ActivityAudioStreamingBinding activityUserAudioBinding;
    IMInputDialog imInputDialog;
    public String profileFrame;
    public String profileImage;
    boolean ishostornot;
    String roomType;
    UserProfileFragment userProfileFragment;
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


        mSocket.on("connection", connection);
        mSocket.connect();
        activityUserAudioBinding = ActivityAudioStreamingBinding.inflate(getLayoutInflater());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // getting our root layout in our view.
        View view = activityUserAudioBinding.getRoot();
        root=activityUserAudioBinding.getRoot();
        setContentView(view);
        setDefaultValue();

        updateUI2();



        applyforseatQueue();

        new Handler().postDelayed(() -> {
            if (!Comman.getInstance().getUser_id().equalsIgnoreCase("")) {
                getProfile();
            } else {
                Intent intent = new Intent(AudioStreamingActivity.this, LoginTypeActivity.class);
                startActivity(intent);
                finish();
            }

        }, 600);
        updateUI();


        //Glide.with(this).load(R.drawable.giftgif).into(activityUserAudioBinding.giftImage);
        requestPermission();
        getAppIDAndUserIDAndAppSign();
        setCreateEngineButtonEvent();
        setLoginRoomButtonEvent();
        if (ishostornot){
            setStartPublishingButton();

        }else{
            setStartPlayingButton();

        }


        mSocket.on("roomData", onReceive);

        mSocket.on("seat_list_get", seat_list_get);






        activityUserAudioBinding.liveHostIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfileFragment=new UserProfileFragment(userlist,roomId,ishostornot);

                    userProfileFragment.show(getSupportFragmentManager(),"profiles");
            }
        });
        activityUserAudioBinding.ivIm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                imInputDialog = new IMInputDialog(AudioStreamingActivity.this);
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
                        messageJson1.put("channel_no", roomId);
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


        activityUserAudioBinding.miconoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (engine.isMicrophoneMuted()){

                Glide.with(AudioStreamingActivity.this).load("").placeholder(R.drawable.micb128).into(activityUserAudioBinding.miconoff);
                engine.muteMicrophone(false);

                }else{
                    engine.muteMicrophone(true);
                    Glide.with(AudioStreamingActivity.this).load("").placeholder(R.drawable.micoff).into(activityUserAudioBinding.miconoff);

                }

            }
        });



    }
    LiveNowAdapter userDataAdapter;
    ZegoUser zegoUseradmin;

    public void setDefaultValue(){

        roomId=getIntent().getStringExtra("roomId");
        publishStreamID=getIntent().getStringExtra("userid");
        userID=getIntent().getStringExtra("userid");
        username=getIntent().getStringExtra("username");
        roomType=getIntent().getStringExtra("roomtype");
        ishostornot=getIntent().getBooleanExtra("host",false);






        JSONObject messageJsonseat = new JSONObject();
        try {
            messageJsonseat.put("user_id", userID);
            messageJsonseat.put("channel_no", roomId);
            messageJsonseat.put("room_type", roomType);
            Toast.makeText(this, "emiited", Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e("socket error send",""+e.getMessage());

        }


        mSocket.emit("seat_list_emit", messageJsonseat);
    }

    private void updateUI() {

        messageListAdapter = new MessageListAdapter(chatdata);
        activityUserAudioBinding.userDataRecyclerView.setAdapter(messageListAdapter);
        activityUserAudioBinding.userDataRecyclerView
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
        profile.scenario = ZegoScenario.GENERAL;

        profile.application = getApplication();
        engine = ZegoExpressEngine.createEngine(profile, null);

        // createEngineButton.setText(ZegoViewUtil.GetEmojiStringByUnicode(ZegoViewUtil.checkEmoji)+getString(R.string.create_engine));
        setApiCalledResult();
        setEventHandler();


    }
    ZegoUser userman  ;

    ZegoRoomConfig zegoRoomConfig=new ZegoRoomConfig();
    public void setLoginRoomButtonEvent(){

        if (engine == null) {

            return;
        }
        userman = new ZegoUser(userID,username);

        // Begin to login room
        zegoRoomConfig.isUserStatusNotify=true;
        engine.loginRoom(roomId, userman,zegoRoomConfig);

    }

    public void setStartPublishingButton(){

        if (engine == null) {
            return;
        }
        // Start publishing stream
        engine.startPublishingStream(publishStreamID);
        // Start preview and set local preview
        //  engine.startPlayingStream(publishStreamID);


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




        engine.setEventHandler(new IZegoEventHandler() {
            @Override
            public void onRoomStateUpdate(String roomID, ZegoRoomState state, int errorCode, JSONObject extendedData) {
                super.onRoomStateUpdate(roomID, state, errorCode, extendedData);
                Toast.makeText(AudioStreamingActivity.this, "1", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int errorCode, JSONObject extendedData) {
                super.onRoomStateChanged(roomID, reason, errorCode, extendedData);

            }

            @Override
            public void onRoomUserUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoUser> userList) {
                super.onRoomUserUpdate(roomID,updateType,userList);

                for (int i=0 ;i<userList.size();i++){

                    userlist.add(userList.get(i));

                }

                activityUserAudioBinding.showAudiencecount.setText(String.valueOf(userlist.size()));


                userDataAdapter = new LiveNowAdapter(AudioStreamingActivity.this,userList);
                LinearLayoutManager llmb = new LinearLayoutManager(AudioStreamingActivity.this, RecyclerView.HORIZONTAL, false);
                activityUserAudioBinding.listiuser.setLayoutManager(llmb);
                activityUserAudioBinding.listiuser.setAdapter(userDataAdapter);

            }
            @Override
            public void onRoomOnlineUserCountUpdate(String roomID, int count) {
                super.onRoomOnlineUserCountUpdate(roomID, count);

            }

            @Override
            public void onRoomStreamUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoStream> streamList, JSONObject extendedData) {
                super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData);



                for (int o=0;o<streamList.size();o++){
                    engine.startPlayingStream(streamList.get(o).streamID);
                }



            }

            @Override
            public void onRoomStreamExtraInfoUpdate(String roomID, ArrayList<ZegoStream> streamList) {
                super.onRoomStreamExtraInfoUpdate(roomID, streamList);



            }

            @Override
            public void onRoomExtraInfoUpdate(String roomID, ArrayList<ZegoRoomExtraInfo> roomExtraInfoList) {
                super.onRoomExtraInfoUpdate(roomID, roomExtraInfoList);

            }

            @Override
            public void onRoomTokenWillExpire(String roomID, int remainTimeInSecond) {
                super.onRoomTokenWillExpire(roomID, remainTimeInSecond);

            }

            @Override
            public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
                super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);

            }
            @Override
            public void onIMRecvCustomCommand(String roomID, ZegoUser fromUser, String command) {
                super.onIMRecvCustomCommand(roomID, fromUser, command);



                if (command.equals("requestaccepted")) {



                    // Start publishing stream
                    engine.startPublishingStream(userID);
                    engine.startPreview();


                }
                if (command.equals("seatapplyback")) {



                    for (int i=0; i<userappliedforseat.size();i++){
                        if (userappliedforseat.get(i).userID.equals(fromUser.userID)){
                            userappliedforseat.remove(i);
                        }
                    }


                     seatRequestListAdapter.setSeatList(userappliedforseat,AudioStreamingActivity.this);
                    seatRequestListAdapter.notifyDataSetChanged();
                }

                if (command.equals("seatapply")){

                    Toast.makeText(AudioStreamingActivity.this, "sasax", Toast.LENGTH_SHORT).show();
                    userappliedforseat.add(fromUser);
                    seatRequestListAdapter.setSeatList(userappliedforseat,AudioStreamingActivity.this);
                    seatRequestListAdapter.notifyDataSetChanged();

                    for (int i=0 ;i<userlist.size();i++){

                        if (userlist.get(i).userID.equals(roomID)){
//                            ObjectAnimator
//                                    .ofFloat(activityUserAudioBinding.incomingcall, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
//                                    .setDuration(2000)
//                                    .start();

                        }

                    }



                }



            }

        });

    }

    private String GetEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public void socketconnection(){


        joinSocket("joined");


    }

    void joinSocket(String msg){

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
            messageJson.put("channel_no", roomId);
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
    String userName1;
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

                            if(type.equalsIgnoreCase("Gift")){
                                showgift(1,frame,name,text,image);
                                chatMessagePojo = new ChatMessagePojo(model_id, userID,"Gift", text, text+" recived gift from "+name, image,"", type);
                                chatdata.add(chatMessagePojo);
                            }else {

                                chatMessagePojo = new ChatMessagePojo(model_id, userID,name, text, text, image,"", type);
                                chatdata.add(chatMessagePojo);
                            }



                            messageListAdapter.notifyItemInserted(chatdata.size());
                            activityUserAudioBinding.userDataRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
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

            if (userName1.equals(userID)){
                giftTips =   touser+" received gift " + giftName.split("HeloKing")[0] + " from you";

            }

            if (touser.equals(userName1)){
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
        Glide.with(this).load(image).into(activityUserAudioBinding.giftImage);
        activityUserAudioBinding.giftImage.setVisibility(View.VISIBLE);



        new Handler().postDelayed(() -> {
            activityUserAudioBinding.giftImage.setVisibility(View.GONE);

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
                        Toast.makeText(AudioStreamingActivity.this, "nondta", Toast.LENGTH_SHORT).show();
                    }
                    socketconnection();
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Toast.makeText(AudioStreamingActivity.this, "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setStartPlayingButton(){

        if (engine == null) {
            return;
        }
        // Start playing stream

        engine.startPlayingStream(publishStreamID);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.on("seat_list_get", seat_list_get);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        joinSocket("leave");
        engine.logoutRoom(roomId);
        engine.destroyEngine(null);

    }




    // Seat Module






    private Emitter.Listener seat_list_get  = new Emitter.Listener() {

        @Override    public void call(final Object... args) {
            Log.e("fdsgfsgety","trgety hw5 4ejt");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {




                    JSONObject chatData = (JSONObject) args[0];
                    try {


                        seatList.clear();
                        seatListhost.clear();

                        JSONArray cast = chatData.getJSONArray("data");

                        for (int i=0; i<cast.length(); i++) {

                            JSONObject DATAa = cast.getJSONObject(i);

                            String name=DATAa.getString("name");
                            String usernameid=DATAa.getString("user_id");
                            String userid=DATAa.getString("username");
                            String seat_no=DATAa.getString("seat_no");
                            String role=DATAa.getString("role");
                            String room_id=DATAa.getString("room_id");
                            String room_status=DATAa.getString("room_status");
                            String mic=DATAa.getString("mic");
                            String frame_image=DATAa.getString("frame_image");
                            String profile_img=DATAa.getString("profile_img");
                            //   String seat_lock=DATAa.getString("seat_lock");
                            ZegoSpeakerSeatStatus zegoSpeakerSeatStatus;
                            if (room_status.equals("1")){
                                zegoSpeakerSeatStatus=ZegoSpeakerSeatStatus.Occupied;
                            }else if (room_status.equals("0")){
                                zegoSpeakerSeatStatus=ZegoSpeakerSeatStatus.Untaken;

                            }else{
                                zegoSpeakerSeatStatus=ZegoSpeakerSeatStatus.Closed;

                            }

                            if (i!=0) {
                                ZegoSpeakerSeatModel zegoSpeakerSeatModel = new ZegoSpeakerSeatModel(userid,usernameid, i, true, role, profile_img, frame_image, zegoSpeakerSeatStatus);
                                seatList.add(zegoSpeakerSeatModel);

                            }else{
                                ZegoSpeakerSeatModel zegoSpeakerSeatModel = new ZegoSpeakerSeatModel(userid, usernameid,i, true, role, profile_img, frame_image, zegoSpeakerSeatStatus);
                                seatListhost.add(zegoSpeakerSeatModel);


                            }
                        }






                        seatListAdapter.setSeatList(seatList,AudioStreamingActivity.this);
                        seatListAdapter.notifyDataSetChanged();


                        adminSeatListAdapter.setSeatList(seatListhost,AudioStreamingActivity.this);
                        adminSeatListAdapter.notifyDataSetChanged();





                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("showeeor listenr","" + e.getMessage());




                    }

                }

            });

        }};



    private void updateUI2() {
        seatListAdapter = new SeatListAdapter(Comman.getInstance().getUser_id(),roomId,userID,AudioStreamingActivity.this);
        activityUserAudioBinding.rvSeatList.setAdapter(seatListAdapter);
        activityUserAudioBinding.rvSeatList.setLayoutManager(new GridLayoutManager(AudioStreamingActivity.this, 5));

        seatListAdapter.setOnSeatClickListener(this::onSpeakerSeatClicked);



        adminSeatListAdapter = new AdminSeatListAdapter(Comman.getInstance().getUser_id(),roomId,userID,AudioStreamingActivity.this);
        activityUserAudioBinding.adminrecylerseat.setAdapter(adminSeatListAdapter);
        activityUserAudioBinding.adminrecylerseat.setLayoutManager(new LinearLayoutManager(AudioStreamingActivity.this));

        adminSeatListAdapter.setadminOnSeatClickListener(this::onadminSpeakerSeatClicked1);


    }



    private void onadminSpeakerSeatClicked1(ZegoSpeakerSeatModel seatModel, View v) {




        if(ishostornot) {


             if (seatModel.status==ZegoSpeakerSeatStatus.Untaken){


           JSONObject messageJson = new JSONObject();
                try {
                    messageJson.put("user_id",userID);
                    messageJson.put("channel_no", roomId);
                    messageJson.put("room_type", "audio");
                    messageJson.put("status", 1);
                    messageJson.put("seat_no", 0);
                    messageJson.put("role", 1);
                    messageJson.put("mic", 1);


                } catch (Exception e)
                {
                    Log.e("socket error send",""+e.getMessage());

                }
                mSocket.emit("seat_update_emit", messageJson);



            }else if (seatModel.userID.equals(userID)){
                showToolTip(seatModel,v, roomId, 0);

            }


//            int hostseststatus=0;
//
//            for (int i=1;i<seatList.size();i++)
//            {
//
//                if (seatList.get(i).userID.equals(userID)){
//                    hostseststatus=seatList.get(i).seatIndex;
//
//
//                }
//
//            }
//
//
//            if (hostseststatus ==0){
//
//
//
//                JSONObject messageJson = new JSONObject();
//                try {
//                    messageJson.put("user_id",getHostID());
//                    messageJson.put("channel_no", roomId);
//                    messageJson.put("room_type", "audio");
//                    messageJson.put("status", 1);
//                    messageJson.put("seat_no", 0);
//                    messageJson.put("role", 1);
//                    messageJson.put("mic", 1);
//
//
//                } catch (Exception e)
//                {
//                    Log.e("socket error send",""+e.getMessage());
//
//                }
//                mSocket.emit("seat_update_emit", messageJson);
//
//
//
//
//            }
//
//
//            else{
//
//
//                JSONObject messageJson1 = new JSONObject();
//                try {
//                    messageJson1.put("user_id","");
//                    messageJson1.put("channel_no", roomId);
//                    messageJson1.put("room_type", "audio");
//                    messageJson1.put("status", 0);
//                    messageJson1.put("seat_no",  hostseststatus);
//                    messageJson1.put("role", 1);
//                    messageJson1.put("mic", 1);
//
//
//                } catch (Exception e)
//                {
//                    Log.e("socket error send",""+e.getMessage());
//
//                }
//                mSocket.emit("seat_update_emit", messageJson1);
//
//
//
//                new Handler().postDelayed(() -> {
//
//
//
//                    JSONObject messageJson = new JSONObject();
//                    try {
//                        messageJson.put("user_id",getHostID());
//                        messageJson.put("channel_no", roomId);
//                        messageJson.put("room_type", "audio");
//                        messageJson.put("status", 1);
//                        messageJson.put("seat_no", 0);
//                        messageJson.put("role", 1);
//                        messageJson.put("mic", 1);
//
//
//                    } catch (Exception e)
//                    {
//                        Log.e("socket error send",""+e.getMessage());
//
//                    }
//                    mSocket.emit("seat_update_emit", messageJson);
//
//
//
//
//                }, 1000);
//
//
//
//
//
//            }







        }else{

            showToolTip(null,v, roomId, 2);

        }

    }


    private void onSpeakerSeatClicked(ZegoSpeakerSeatModel seatModel,View v) {

        if (ishostornot) {
            if (seatModel.status == ZegoSpeakerSeatStatus.Untaken) {
                showToolTip(seatModel,v,"",5);
            } else if (seatModel.status == ZegoSpeakerSeatStatus.Closed) {
                showToolTip(seatModel,v,"",6);
            } else if (seatModel.status == ZegoSpeakerSeatStatus.Occupied) {
                if (Objects.equals(seatModel.userID, getMyUserID())) {
                    showToolTip(seatModel,v,"",0);
                }else {
                    if (seatModel.status == ZegoSpeakerSeatStatus.Occupied) {
                        showToolTip(seatModel,v,"",1);
                    } else {

                    }
                }
            }
        } else {

            if (seatModel.status == ZegoSpeakerSeatStatus.Untaken) {
                showToolTip(seatModel,v,"",7);
            } else if (seatModel.status == ZegoSpeakerSeatStatus.Closed) {
                if(settingType!=0){
                    showToolTip(seatModel,v,"",6);
                }else {
                    ToastUtils.showShort(StringUtils.getString(R.string.the_wheat_position_has_been_locked));
                }
            } else if (seatModel.status == ZegoSpeakerSeatStatus.Occupied) {

                Toast.makeText(AudioStreamingActivity.this, seatModel.userID+"   "+getMyUserID(), Toast.LENGTH_LONG).show();
                if (getMyUserID().equals(seatModel.userID)) {
                    showToolTip(seatModel,v,"",4);
                } else {
                    showToolTip(seatModel,v,"",3);
                }
            }
        }
    }

    private String getMyUserID() {
        return userID ;
    }



    int settingType=0,hostSeatStatus=0;


    int requestcohoststatus=0,showswitchbutton=0;

    private int tipSizeSmall;
    private int tooltipColor;
    ViewGroup root;

    Tooltip tooltip;
    void showToolTip(ZegoSpeakerSeatModel seatModel,View v,String userId,int viewStatus){
        tooltipColor = ContextCompat.getColor(AudioStreamingActivity.this, R.color.transparent);
        Resources res = getResources();
        tipSizeSmall = res.getDimensionPixelSize(R.dimen.tip_dimen_small);

        View content=null;

        //0=Host to Host,1=Host to Speaker, 2= Speaker to Host, 3=Speaker to Speaker, 4=Speaker to Self, 5=Host to BlankSeat, 6=Host to CloseSeat, 7=Speaker to Blank
        if(viewStatus==0){
            content= getLayoutInflater().inflate(R.layout.room_host_to_host_tooltipaudio, null);
        }else  if(viewStatus==1){


            if(seatModel.room_role!=null) {
                if (seatModel.room_role.equalsIgnoreCase("Admin")) {
                    content = getLayoutInflater().inflate(R.layout.room_host_to_admin_tooltips, null);
                } else {
                    content = getLayoutInflater().inflate(R.layout.room_host_to_speakeraudio_tooltip, null);
                }
            }else {
                content = getLayoutInflater().inflate(R.layout.room_host_to_speakeraudio_tooltip, null);
            }
        }else  if(viewStatus==2){
            content= getLayoutInflater().inflate(R.layout.room_speaker_to_host_tooltip, null);
        }else  if(viewStatus==3){
            if (settingType!=0) {
                content = getLayoutInflater().inflate(R.layout.room_admin_to_speaker_tooltip, null);
            } else {
                content= getLayoutInflater().inflate(R.layout.room_speaker_to_speaker_tooltip, null);
            }
        }else  if(viewStatus==4){
            content= getLayoutInflater().inflate(R.layout.room_speaker_to_self_tooltipaudio, null);
        }else  if(viewStatus==5){

            content = getLayoutInflater().inflate(R.layout.room_host_to_blankseat_tooltip, null);

        }else  if(viewStatus==6){
            content= getLayoutInflater().inflate(R.layout.room_host_closeseat_tooltip, null);
        }else  if(viewStatus==8){
            content= getLayoutInflater().inflate(R.layout.room_host_to_hostifspeaker_tooltipaudio, null);
        }else  if(viewStatus==7){
            if (settingType!=0) {
                content = getLayoutInflater().inflate(R.layout.room_admin_to_blankseat_tooltip, null);
            } else {
                content= getLayoutInflater().inflate(R.layout.room_speaker_to_blankseat_strramtooltip, null);
            }
        }

        if(tooltip!=null){
            tooltip.dismiss();
        }
        tooltip = new Tooltip.Builder(AudioStreamingActivity.this)
                .anchor(v, Tooltip.BOTTOM)
                .animate(new TooltipAnimation(TooltipAnimation.SCALE_AND_FADE, 200, true))
                .autoAdjust(true)
                .content(content)
                .cancelable(false)
                .autoCancel(3000)
                .into(root)
                .withTip(new Tooltip.Tip(tipSizeSmall, tipSizeSmall, tooltipColor))
                .show();



        //0=Host to Host,1=Host to Speaker, 2= Speaker to Host, 3=Speaker to Speaker, 4=Speaker to Self, 5=Host to BlankSeat, 6=Host to CloseSeat, 7=Speaker to Blank
        LinearLayout gotohomapge=content.findViewById(R.id.gotohomapge);
        LinearLayout leaveHost=content.findViewById(R.id.leaveHost);
        LinearLayout leaveSpeaker=content.findViewById(R.id.leaveSpeaker);
        LinearLayout makeAdmin=content.findViewById(R.id.makeAdmin);
        LinearLayout removeAdmin=content.findViewById(R.id.removeAdmin);
        LinearLayout kickout=content.findViewById(R.id.kickout);
        LinearLayout sendGifts=content.findViewById(R.id.sendGifts);
        LinearLayout lockSeat=content.findViewById(R.id.lockSeat);
        LinearLayout openSeat=content.findViewById(R.id.openSeat);
        LinearLayout takeSeat=content.findViewById(R.id.takeSeat);
        LinearLayout adminKickout=content.findViewById(R.id.adminkickout);

        if(viewStatus==0){

            leaveHost.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            JSONObject messageJson = new JSONObject();
                            try {
                                messageJson.put("user_id", "");
                                messageJson.put("channel_no", roomId);
                                messageJson.put("room_type", "audio");
                                messageJson.put("status", 0);
                                messageJson.put("seat_no", seatModel.seatIndex);
                                messageJson.put("role", 1);
                                messageJson.put("mic", 1);


                            } catch (Exception e)
                            {
                                Toast.makeText(AudioStreamingActivity.this, "wsadfed  "+e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("socketerrorsend","hi  "+e.getMessage());

                            }

                            mSocket.emit("seat_update_emit", messageJson);












                            tooltip.dismiss();
                        }
                    }
            );
            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //   showProfileDetails(stid);
                            tooltip.dismiss();
                        }
                    }
            );

        }else  if(viewStatus==8) {



            leaveHost.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Toast.makeText(AudioStreamingActivity.this, "111111111111111111", Toast.LENGTH_SHORT).show();

                            JSONObject messageJson = new JSONObject();
                            try {
                                messageJson.put("user_id","");
                                messageJson.put("channel_no",roomId);
                                messageJson.put("room_type", "audio");
                                messageJson.put("status", 0);
                                messageJson.put("seat_no", 0);
                                messageJson.put("role", 1);
                                messageJson.put("mic", 1);


                            } catch (Exception e)
                            {
                                Log.e("socket error send",""+e.getMessage());

                            }

                            mSocket.emit("seat_update_emit", messageJson);



                            tooltip.dismiss();
                        }
                    }
            );

            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  showProfileDetails(stid);
                            tooltip.dismiss();
                        }
                    }
            );

        }else  if(viewStatus==1){
            if(seatModel.room_role!=null) {
                if (seatModel.room_role.equalsIgnoreCase("Admin")) {
                    removeAdmin.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    tooltip.dismiss();
                                }
                            }
                    );
                    //makeAdmin(roomInfo.getroomId(),seatModel.userID,"Speaker");
                } else {
                    makeAdmin.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    tooltip.dismiss();
                                }
                            }
                    );
                    //   makeAdmin(roomInfo.getroomId(),seatModel.userID,"Admin");
                }
            }else{
                makeAdmin.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                tooltip.dismiss();
                            }
                        }
                );
                // makeAdmin(roomInfo.getroomId(),seatModel.userID,"Admin");
            }

            kickout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {






                    tooltip.dismiss();

                }

            });









            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //     showProfileDetails(seatModel.getIid());
                            tooltip.dismiss();
                        }
                    }
            );

            sendGifts.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==2){
            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  showProfileDetails(stid);
                            tooltip.dismiss();
                        }
                    }
            );

            sendGifts.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==3){

            if (settingType!=0) {
                adminKickout.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                tooltip.dismiss();
                            }
                        }
                );
            }

            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  showProfileDetails(seatModel.getIid());
                            tooltip.dismiss();
                        }
                    }
            );
            sendGifts.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==4){
            gotohomapge.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //   showProfileDetails(seatModel.getIid());
                            tooltip.dismiss();
                        }
                    }
            );

            leaveSpeaker.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //   leavespeaker(seatModel.seatIndex);
                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==5){

            takeSeat.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            tooltip.dismiss();
                        }
                    }
            );


            lockSeat.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (seatModel.status == ZegoSpeakerSeatStatus.Untaken) {


                                Toast.makeText(AudioStreamingActivity.this, "lock hit", Toast.LENGTH_SHORT).show();

                                JSONObject messageJson = new JSONObject();
                                try {
                                    messageJson.put("user_id", "");
                                    messageJson.put("channel_no", roomId);
                                    messageJson.put("room_type", "audio");
                                    messageJson.put("status", 2);
                                    messageJson.put("seat_no", seatModel.seatIndex);
                                    messageJson.put("role", 1);
                                    messageJson.put("mic", 1);


                                } catch (Exception e)
                                {
                                    Log.e("socket error send",""+e.getMessage());

                                }
                                mSocket.emit("seat_update_emit", messageJson);



                            } else {
                                // ToastUtils.showShort(R.string.toast_lock_seat_already_take_seat);
                            }
                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==6){
            openSeat.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            JSONObject messageJson = new JSONObject();
                            try {
                                messageJson.put("user_id", "");
                                messageJson.put("channel_no", roomId);
                                messageJson.put("room_type", "audio");
                                messageJson.put("status", 0);
                                messageJson.put("seat_no", seatModel.seatIndex);
                                messageJson.put("role", 1);
                                messageJson.put("mic", 1);


                            } catch (Exception e)
                            {
                                Log.e("socket error send",""+e.getMessage());

                            }
                            mSocket.emit("seat_update_emit", messageJson);

                            tooltip.dismiss();
                        }
                    }
            );
        }else  if(viewStatus==7){

            if (settingType!=0) {
                lockSeat.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (seatModel.status == ZegoSpeakerSeatStatus.Untaken) {

                                    ZegoSpeakerSeatModel model = seatList
                                            .get(seatModel.seatIndex);
                                    int pos;
                                    if (model.seatIndex != 0) {
                                        pos = model.seatIndex - 1;
                                        seatListAdapter.updateUserInfo(model, pos);
                                    }
                                } else {
                                    //     ToastUtils.showShort(R.string.toast_lock_seat_already_take_seat);
                                }
                                tooltip.dismiss();
                            }
                        }
                );
            }

            takeSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    seatapplyBottonDaolog.show();

                }
            });


        }


    }


    private String getHostID() {



        return roomId;



    }



    BottomSheetDialog seatapplyBottonDaolog  ;

    SeatqueuelistandapplyforseatBinding seatqueuelistandapplyforseatBinding;


    SeatRequestListAdapter seatRequestListAdapter;


    public static int applyseatactiveornot=1;


    public void applyforseatQueue(){

        seatRequestListAdapter = new   SeatRequestListAdapter(AudioStreamingActivity.this
                ,userappliedforseat,Comman.getInstance().getUser_id(),userID,roomId,ishostornot);
        seatapplyBottonDaolog = new BottomSheetDialog(AudioStreamingActivity.this);


        seatqueuelistandapplyforseatBinding = SeatqueuelistandapplyforseatBinding.inflate(getLayoutInflater());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // getting our root layout in our view.
        View view = seatqueuelistandapplyforseatBinding.getRoot();


        seatapplyBottonDaolog.setContentView(view);

        if (!ishostornot){
            seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setVisibility(View.VISIBLE);
        }

        seatqueuelistandapplyforseatBinding.listofqueuerequestfortakeseat.setLayoutManager(new LinearLayoutManager(AudioStreamingActivity.this));
        seatqueuelistandapplyforseatBinding.listofqueuerequestfortakeseat.setHasFixedSize(true);
        seatqueuelistandapplyforseatBinding.listofqueuerequestfortakeseat.setAdapter(seatRequestListAdapter);

        seatRequestListAdapter.onrequestseatClickListnener(this::approvesaet);


        if (applyseatactiveornot==1){


            seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setText("Join for a seat");
            seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_blue)));



        }
        else{

            seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setText("Click here to get off the queue");
            seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayDark)));

        }

        seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (applyseatactiveornot==0){



                    engine.sendCustomCommand(roomId, "seatapplyback",userlist , new IZegoIMSendCustomCommandCallback() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onIMSendCustomCommandResult(int errorCode) {
                            if (errorCode == 0) {
                                applyseatactiveornot=1;
                                seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setText("Join for a seat");
                                seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_blue)));


                                userappliedforseat.remove(userman);



                                seatRequestListAdapter.setSeatList(userappliedforseat,AudioStreamingActivity.this);
                                seatRequestListAdapter.notifyDataSetChanged();
                            }

                            else {
                                // Custom command failed to send
                                Toast.makeText(AudioStreamingActivity.this, "No host is available", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else
                {

                    engine.sendCustomCommand(roomId, "seatapply",userlist , new IZegoIMSendCustomCommandCallback() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onIMSendCustomCommandResult(int errorCode) {
                            if (errorCode == 0) {
                                seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setText("Click here to get off the queue");
                                seatqueuelistandapplyforseatBinding.sendrequestfortakebackrequest.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grayDark)));

                                applyseatactiveornot=0;
                                userappliedforseat.add(userman);

                                Toast.makeText(AudioStreamingActivity.this, "dd  "+userlist.get(0).userID, Toast.LENGTH_SHORT).show();


                                seatRequestListAdapter.setSeatList(userappliedforseat,AudioStreamingActivity.this);
                                seatRequestListAdapter.notifyDataSetChanged();
                            }

                            else {
                                // Custom command failed to send
                                Toast.makeText(AudioStreamingActivity.this, "No host is available", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

            }
        });






    }


    ArrayList<ZegoUser> commandtosingleuser = new ArrayList<>();


    private void approvesaet(String userId, String userName) {
        commandtosingleuser.clear();

        for (int i=0 ;i<userlist.size();i++){


            if (userlist.get(i).userID.equals(userId)){

                commandtosingleuser.add(userlist.get(i));

            }

        }

        engine.sendCustomCommand(roomId, "requestaccepted",commandtosingleuser , new IZegoIMSendCustomCommandCallback() {
            @Override
            public void onIMSendCustomCommandResult(int errorCode) {
                if (errorCode == 0) {
                    engine.startPlayingStream(commandtosingleuser.get(0).userID);
                    Toast.makeText(AudioStreamingActivity.this, "sebd s   "+commandtosingleuser.size(), Toast.LENGTH_SHORT).show();
                } else {
                    // Custom command failed to send
                    Toast.makeText(AudioStreamingActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void audienceButton(){

    }

    public  void hostButton(){

    }
    public void adminButtom(){

    }

    public void ownerButton(){

    }

}