package planet.com.helokings.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;

import planet.com.helokings.Fragment.MomentAllFragment;
import planet.com.helokings.Fragment.MomentFmlyFragment;
import planet.com.helokings.Fragment.MomentFollowedFragment;
import planet.com.helokings.R;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.ActivityMomentBinding;

public class MomentActivity extends AppCompatActivity {
    ActivityMomentBinding momentBinding;
    String Moment_Value = "", Moment_Post = "0", VideoFilePath;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String DataValue = "";
    File finalFile;
    String base64Image = "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAsQAAALEBxi1JjQAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAACtSURBVEiJ7dM9DgFRGIXhh6gI+9DpNKyBwmY0YhnWo5aIBUioaRQmJBIKU4xhJsPMFJM4yZfcmy/nvPeX79TEFBsEuOKILbpfZr2pgxXuCdXLC1ikhOcGtHApEzD4EDj3PLZCNI6F39DIYqxnBMTDziGkMMDPKh2QprX0VxOtZVJIUTvYVR6wT2rUUkx9tMPxELNIL8AoMt/g8OvqYOL1Uk9ZjdX/B3/AH1ABwAPkaT24O75COAAAAABJRU5E";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        momentBinding = ActivityMomentBinding.inflate(getLayoutInflater());
        setContentView(momentBinding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.moment_frame, new MomentAllFragment()).commit();

        Log.e("token", "" + Comman.getInstance().getToken());
        //   MomentBG();

        momentBinding.tvNewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Moment_Post.equalsIgnoreCase("0")) {
                    Moment_Post = "1";
                    momentBinding.layVideo.setVisibility(View.VISIBLE);
                    momentBinding.layGallery.setVisibility(View.VISIBLE);
                    momentBinding.layCamera.setVisibility(View.VISIBLE);
                    momentBinding.tvNewpost.setText("Close");

                } else if (Moment_Post.equalsIgnoreCase("1")) {
                    Moment_Post = "0";
                    momentBinding.layVideo.setVisibility(View.GONE);
                    momentBinding.layGallery.setVisibility(View.GONE);
                    momentBinding.layCamera.setVisibility(View.GONE);
                    momentBinding.tvNewpost.setText("New Post");
                }
            }
        });

        momentBinding.tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moment_Value = "1";
                MomentBG();
            }
        });

        momentBinding.tvFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moment_Value = "2";
                MomentBG();
            }
        });

        momentBinding.tvFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moment_Value = "3";
                MomentBG();

            }
        });
        momentBinding.layVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent videoIntent = new Intent();
                    videoIntent.setType("video/*");
                    startActivityForResult(videoIntent, 2);
                } else {
                    requestPermission();
                }
            }
        });
        momentBinding.layGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataValue = "2";
                CropImageF();
            }
        });
        momentBinding.layCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    requestPermission();

                }

            }
        });


    }

    private void CropImageF() {
        ImagePicker.with(this).crop(16f,15f).start();
              //  .crop()
                //.compress(1024)
                //.maxResultSize(1080, 1080)
                //.start();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);

    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /// fro gallery
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (DataValue.equalsIgnoreCase("2")) {
                Uri selectedImage = data.getData();
                finalFile = new File(selectedImage.getPath());
                Intent in = new Intent(this, AddStatusActivity.class);
                in.putExtra("finalFile", finalFile.getPath());
                in.putExtra("type", "image");
                startActivity(in);
                //  finish();
            }
        }
        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(this, photo);
                    finalFile = new File(getRealPathFromURI(tempUri));
                    Intent in = new Intent(this, AddStatusActivity.class);
                    in.putExtra("finalFile", finalFile.getPath());
                    in.putExtra("type", "image");
                    startActivity(in);
                    // finish();

                    break;

                }

            case 2:
                if (resultCode == RESULT_OK) {
                    if (data != null) {

                        Uri selectedVieo = data.getData();
                        VideoFilePath = getPath(selectedVieo);
                        String[] filePathColumn = {MediaStore.Video.Media.DATA};
                        Cursor cursor = this.getContentResolver().query(selectedVieo,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();
                        try {
                            if (filePath != null) {
                                MediaPlayer mp = MediaPlayer.create(this, Uri.parse(filePath));
                                int duration = mp.getDuration();
                                mp.release();
                                if ((duration / 1000) > 30) {
                                    Toast.makeText(this, "Please select maximum 30 second video", Toast.LENGTH_SHORT).show();
                                } else {
                                    runVideo(filePath);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

        }


    }


    private void runVideo(String filePath) {

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MINI_KIND);
        base64Image = convertBitmapToBase64(thumb);
        Intent in = new Intent(getApplicationContext(), AddStatusActivity.class);
        in.putExtra("video", "" + filePath);
        in.putExtra("finalFile", base64Image);
        in.putExtra("type", "video");
        startActivity(in);
        //    finish();


    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    private String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getApplicationContext().getApplicationContext().getContentResolver() != null) {
            Cursor cursor = getApplicationContext().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void MomentBG() {
        if (Moment_Value.equalsIgnoreCase("1")) {
            momentBinding.tvAll.setTextColor(Color.WHITE);
            momentBinding.tvFamily.setTextColor(Color.BLACK);
            momentBinding.tvFollowed.setTextColor(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.moment_frame, new MomentAllFragment()).commit();

            momentBinding.tvAll.setBackgroundResource(R.drawable.button);
            momentBinding.tvFamily.setBackgroundResource(R.drawable.white_bule_rounded);
            momentBinding.tvFollowed.setBackgroundResource(R.drawable.white_bule_rounded);

        } else if (Moment_Value.equalsIgnoreCase("2")) {
            momentBinding.tvFollowed.setTextColor(Color.WHITE);
            momentBinding.tvAll.setTextColor(Color.BLACK);
            momentBinding.tvFamily.setTextColor(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.moment_frame, new MomentFollowedFragment()).commit();
            momentBinding.tvAll.setBackgroundResource(R.drawable.white_bule_rounded);
            momentBinding.tvFollowed.setBackgroundResource(R.drawable.button);
            momentBinding.tvFamily.setBackgroundResource(R.drawable.white_bule_rounded);
        } else if (Moment_Value.equalsIgnoreCase("3")) {
            momentBinding.tvFamily.setTextColor(Color.WHITE);
            momentBinding.tvAll.setTextColor(Color.BLACK);
            momentBinding.tvFollowed.setTextColor(Color.BLACK);
            getSupportFragmentManager().beginTransaction().replace(R.id.moment_frame, new MomentFmlyFragment()).commit();

            momentBinding.tvFamily.setBackgroundResource(R.drawable.button);
            momentBinding.tvAll.setBackgroundResource(R.drawable.white_bule_rounded);
            momentBinding.tvFollowed.setBackgroundResource(R.drawable.white_bule_rounded);
        }
    }
}