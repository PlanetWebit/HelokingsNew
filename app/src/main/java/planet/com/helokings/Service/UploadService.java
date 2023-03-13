package planet.com.helokings.Service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;

import planet.com.helokings.Login.MainActivity;
import planet.com.helokings.Model.PostUploadModel;
import planet.com.helokings.Model.VideoUploadModel;
import planet.com.helokings.R;
import planet.com.helokings.SharedPref.UserSharePreferancess;


/**
 * Created by qboxus on 6/7/2018.
 */


// this the background service which will upload the video into database
public class UploadService extends Service {


    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public UploadService getService() {
            return UploadService.this;
        }
    }

    boolean mAllowRebind;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }
    String description,users_json,hashtags_json,auth_token;
    UserSharePreferancess sharedPreferences;
    FileUploader fileUploader;

    public static String image="",image1="",image2="";
    public static File video;

    public UploadService() {
        super();
    }


    @Override
    public void onCreate() {
        sharedPreferences = new UserSharePreferancess(this);
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        // get the all the selected date for send to server during the post video

        if (intent != null && intent.getAction().equals("startservice")) {
            showNotification();

            description = intent.getStringExtra("description");
            users_json = intent.getStringExtra("users_json");
            hashtags_json = intent.getStringExtra("hashtags_json");
            auth_token = intent.getStringExtra("auth_token");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    PostUploadModel uploadModel=new PostUploadModel();
                    uploadModel.setDescription(description);
                    uploadModel.setAuth_token(auth_token);
                    uploadModel.setHashtags_json(hashtags_json);
                    uploadModel.setUsers_json(users_json);

                    Log.e("videopathvideopath","="+video);
                    fileUploader = new FileUploader(video,getApplicationContext(),uploadModel);
                    fileUploader.SetCallBack(new FileUploader.FileUploaderCallback() {
                        @Override
                        public void onError() {
                            //send error broadcast

                            Log.e("MyCreated5","Error");
                            stopForeground(true);
                            stopSelf();

                            sendBroadcast(new Intent("uploadVideo"));
                            sendBroadcast(new Intent("newVideo"));
                        }

                        @Override
                        public void onFinish(VideoUploadModel responses) {


                            Log.e("MyCreated4",""+responses);

                            stopForeground(true);
                            stopSelf();

                            sendBroadcast(new Intent("uploadVideo"));
                            sendBroadcast(new Intent("newVideo"));
                            //send finish broadcast
                        }

                        @Override
                        public void onProgressUpdate(int currentpercent, int totalpercent,String msg) {
                            //send progress broadcast
                            if (currentpercent>0)
                            {
                                Bundle bundle=new Bundle();
                                bundle.putBoolean("isShow",true);
                                bundle.putInt("currentpercent",currentpercent);
                                bundle.putInt("totalpercent",totalpercent);
                                if (MainActivity.uploadingCallback!=null)
                                {
                                    MainActivity.uploadingCallback.onResponse(bundle);
                                }

                            }
                        }
                    });


                }
            }).start();


        } else if (intent != null && intent.getAction().equals("stopservice")) {
            stopForeground(true);
            stopSelf();
        }


        return Service.START_STICKY;
    }


    // this will show the sticky notification during uploading video
    private void showNotification() {

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        }

        final String CHANNEL_ID = "default";
        final String CHANNEL_NAME = "Default";

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel defaultChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(defaultChannel);
        }

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setContentTitle(UploadService.this.getString(R.string.uploading_video))
                .setContentText(UploadService.this.getString(R.string.please_wait_video_is_uploading))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        android.R.drawable.stat_sys_upload))
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        startForeground(101, notification);

    }



}