package planet.com.helokings.Service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import planet.com.helokings.Model.PostUploadModel;
import planet.com.helokings.Model.VideoUploadModel;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploader {

    private FileUploaderCallback mFileUploaderCallback;
    long filesize = 0l;
    PostUploadModel uploadModel;

    public FileUploader(File file, Context context, PostUploadModel uploadModel) {
        this.uploadModel=uploadModel;
        filesize = file.length();


        PRRequestBody prRequestBody=new PRRequestBody(file);
        RequestBody  mFile = RequestBody.create( MediaType.parse("video/mp4"),file);
        MultipartBody.Part video = MultipartBody.Part.createFormData("video", file.getName(), prRequestBody);
        RequestBody auth_token = RequestBody.create(MediaType.parse("text/plain"),uploadModel.getAuth_token());
        RequestBody description = RequestBody.create(MediaType.parse("text/pain"),uploadModel.getDescription());
        RequestBody users_json = RequestBody.create(MediaType.parse("text/pain"),uploadModel.getUsers_json());
        RequestBody hashtags_json = RequestBody.create(MediaType.parse("text/pain"),uploadModel.getHashtags_json());


        Call<VideoUploadModel> fileUpload = RetrofitClient.getInstance().getnewResponseData().uploadvideodata(video,auth_token,description,users_json,hashtags_json);
        fileUpload.enqueue(new Callback<VideoUploadModel>() {
            @Override
            public void onResponse(@NonNull Call<VideoUploadModel> call, @NonNull Response<VideoUploadModel> response) {
                Log.e("MyCreated3",""+response);

                if (response != null && response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        mFileUploaderCallback.onFinish(response.body());
                    }else {
                        Log.e("TAG",response.body().getMsg());
                        mFileUploaderCallback.onFinish(response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<VideoUploadModel> call, Throwable t) {
                Log.d("TAG","Exception onFailure :"+t.toString());
                mFileUploaderCallback.onError();
            }
        });


    }

    public void SetCallBack(FileUploaderCallback fileUploaderCallback) {
        this.mFileUploaderCallback = fileUploaderCallback;
    }


    public class PRRequestBody extends RequestBody {
        private File mFile;

        private static final int DEFAULT_BUFFER_SIZE = 1024;

        public PRRequestBody(final File file) {
            mFile = file;
        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            return MediaType.parse("multipart/form-data");
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            Log.d("LOG_D_TAG","Check progress callback");

            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;
//            Source source = null;

            try {
                int read;
//                source = Okio.source(mFile);
                Handler handler = new Handler(Looper.getMainLooper());
                while ((read = in.read(buffer)) != -1) {

                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                    uploaded += read;
                    sink.write(buffer, 0, read);
//                    Log.d("LOG_D_TAG", String.valueOf(uploaded));
                }
            } catch (Exception e){
                Log.d("LOG_D_TAG","Exception : "+e);
            } finally {
                in.close();
            }
        }
    }


    private class ProgressUpdater implements Runnable {
        private long mUploaded=0;
        private long mTotal=0;

        ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int) (100 * mUploaded / mTotal);
            int total_percent = (int) (100 * (mUploaded) / mTotal);
            mFileUploaderCallback.onProgressUpdate(current_percent, total_percent,
                    "File Size: " + Constants.readableFileSize(filesize));
        }
    }

    public interface FileUploaderCallback {

        void onError();

        void onFinish(VideoUploadModel responses);

        void onProgressUpdate(int currentpercent, int totalpercent, String msg);
    }


}
