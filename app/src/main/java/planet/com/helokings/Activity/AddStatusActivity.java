package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Adapter.HashTagAdapter;
import planet.com.helokings.Adapter.MentionAdapter;
import planet.com.helokings.Login.MainActivity;
import planet.com.helokings.Model.ImageUplodeModel;
import planet.com.helokings.Model.MentionModel;
import planet.com.helokings.Model.TagModel;
import planet.com.helokings.Pojo.MentionPojo;
import planet.com.helokings.Pojo.TagPojo;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.Service.Constants;
import planet.com.helokings.Service.UploadService;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.ActivityAddStatusBinding;
import planet.com.helokings.databinding.HastglistdialogBinding;
import planet.com.helokings.databinding.MenitonDialogBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStatusActivity extends AppCompatActivity implements HashTagAdapter.HashItemInterFace, MentionAdapter.MentionInterface {
    ActivityAddStatusBinding activityAddStatusBinding;
    File finalFile, finalFile1;
    String video = "", Path = " ";
    public String finalFileImage = "", type = "", thumbnail = "";
    ProgressDialog progressDialog;


    // HasTag
    HastglistdialogBinding hastglistdialogBinding;
    BottomSheetDialog dialog, mention_dialog;
    ArrayList<TagPojo> tagPojos = new ArrayList<>();
    String hashTagValue = "";
    String hasttag_json = "";
    ArrayList<String> hashlist;
    HashTagAdapter hashTagAdapter;

    // Mention
    MenitonDialogBinding menitonDialogBinding;
    ArrayList<MentionPojo> mentionPojos = new ArrayList<>();
    String users_json = "";
    String mention_value = "";
    ArrayList<String> mentionlist;
    MentionAdapter mentionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddStatusBinding = ActivityAddStatusBinding.inflate(getLayoutInflater());
        setContentView(activityAddStatusBinding.getRoot());
        Toolbar();
        hashlist = new ArrayList<>();
        mentionlist = new ArrayList<>();

        progressDialog = new ProgressDialog(AddStatusActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // get video and img and type
        Intent in = getIntent();
        type = in.getStringExtra("type");
        finalFileImage = in.getStringExtra("finalFile");
        video = in.getStringExtra("video");
        thumbnail = in.getStringExtra("finalFile");

// set according to type
        if (type.equalsIgnoreCase("image")) {
            finalFile = new File(finalFileImage);
        } else {
            finalFile1 = new File(video);
            Path = String.valueOf(finalFile1);
        }
        Log.e("AllData", type + "    video = " + video);


        if (finalFileImage.equalsIgnoreCase("")) {

        } else {
            if (type.equalsIgnoreCase("image")) {
                activityAddStatusBinding.visitedImage.setImageURI(Uri.parse(finalFileImage));

            } else {
                byte[] bytes = Base64.decode(thumbnail, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                activityAddStatusBinding.visitedImage.setImageBitmap(bitmap);

            }

        }


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);


        activityAddStatusBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("video")) {
                    startService();
                } else if (type.equalsIgnoreCase("image")) {
                    ImageUpload();
                }


            }
        });

        activityAddStatusBinding.tvMention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MentianBottamSheet();
                MentionData();

            }
        });

        activityAddStatusBinding.lHstg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashTagBottomSheet();
                HashTagData();


            }
        });

        activityAddStatusBinding.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    activityAddStatusBinding.visitedImage.setImageURI(null);
                    activityAddStatusBinding.visitedImage.setImageBitmap(null);
                    finalFile = null;

                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent backInatent = new Intent(AddStatusActivity.this, MainActivity.class);
                            startActivity(backInatent);

                        }
                    }, 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void MentionData() {
        progressDialog.show();
        Call<MentionModel> call = RetrofitClient.getInstance().getnewResponseData().Mentiondata(Comman.comman_obj.getUser_id(), "user", "a", "0");
        call.enqueue(new Callback<MentionModel>() {
            @Override
            public void onResponse(Call<MentionModel> call, Response<MentionModel> response) {
                progressDialog.dismiss();
                MentionModel mentionModel = response.body();
                if (response.isSuccessful()) {
                    if (mentionModel.getStatus().equalsIgnoreCase("true")) {
                        mentionPojos = mentionModel.getData();
                        mentionAdapter.MensetData(mentionModel.getData());


                    } else {
                        Toast.makeText(AddStatusActivity.this, "" + mentionModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<MentionModel> call, Throwable t) {
                Toast.makeText(AddStatusActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void MentianBottamSheet() {
        mention_dialog = new BottomSheetDialog(this, R.style.MyDialogStyle);
        menitonDialogBinding = MenitonDialogBinding.inflate(getLayoutInflater(), null, false);
        mention_dialog.setContentView(menitonDialogBinding.getRoot());
        mentionAdapter = new MentionAdapter(AddStatusActivity.this, mentionPojos, AddStatusActivity.this);
        LinearLayoutManager llm = new LinearLayoutManager(AddStatusActivity.this, RecyclerView.VERTICAL, false);
        menitonDialogBinding.mentionRecyclerView.setAdapter(mentionAdapter);
        menitonDialogBinding.mentionRecyclerView.setLayoutManager(llm);
        mention_dialog.show();

        menitonDialogBinding.mentionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MentionFilter(newText);
                return false;
            }
        });
        menitonDialogBinding.mentionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mention_dialog.dismiss();
            }
        });


    }

    private void MentionFilter(String newText) {
        ArrayList<MentionPojo> mentionfilterlist = new ArrayList<>();
        for (MentionPojo mention_item : mentionPojos) {
            if (mention_item.getNick_name().toLowerCase().contains(newText.toLowerCase())) {
                mentionfilterlist.add(mention_item);
            }
        }
        if (mentionfilterlist.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

        } else {
            mentionAdapter.filterList(mentionfilterlist);
        }


    }


    private void HashTagBottomSheet() {
        dialog = new BottomSheetDialog(this, R.style.MyDialogStyle);
        hastglistdialogBinding = HastglistdialogBinding.inflate(getLayoutInflater(), null, false);
        dialog.setContentView(hastglistdialogBinding.getRoot());
        hashTagAdapter = new HashTagAdapter(AddStatusActivity.this, tagPojos, AddStatusActivity.this::HasData);
        LinearLayoutManager llm = new LinearLayoutManager(AddStatusActivity.this, RecyclerView.VERTICAL, false);
        hastglistdialogBinding.hastRecyclerView.setAdapter(hashTagAdapter);
        hastglistdialogBinding.hastRecyclerView.setLayoutManager(llm);
        dialog.show();


        hastglistdialogBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        hastglistdialogBinding.ivHashsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;


            }
        });


    }

    private void filter(String newText) {
        ArrayList<TagPojo> filteredlist = new ArrayList<>();
        for (TagPojo item : tagPojos) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            hashTagAdapter.filterList(filteredlist);
        }


    }

    private void HashTagData() {
        progressDialog.show();
        Call<TagModel> call = RetrofitClient.getInstance().getnewResponseData().getTagData(Comman.getInstance().getUser_id(), "hashtag", "0");
        call.enqueue(new Callback<TagModel>() {
            @Override
            public void onResponse(Call<TagModel> call, Response<TagModel> response) {
                progressDialog.dismiss();
                TagModel tagModel = response.body();
                if (response.isSuccessful()) {
                    if (tagModel.getStatus().equalsIgnoreCase("true")) {
                        tagPojos = tagModel.getData();
                        //
                        hashTagAdapter.setHashData(tagModel.getData());
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddStatusActivity.this, "" + tagModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<TagModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddStatusActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT);

            }
        });

    }


    // Image Upload
    private void ImageUpload() {
        progressDialog.show();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), finalFile);
        MultipartBody.Part img_lode = MultipartBody.Part.createFormData("image", finalFile.getName(), requestBody);
        RequestBody token = RequestBody.create(MediaType.parse("Multipart/from-data"), Comman.getInstance().getUser_id());
        RequestBody user_description = RequestBody.create(MediaType.parse("Multipart/from-data"), activityAddStatusBinding.edtHstg.getText().toString());
        RequestBody user_id = RequestBody.create(MediaType.parse("Multipart/from-data"), users_json);
        RequestBody requesthastg = RequestBody.create(MediaType.parse("multipart/from-data"), hasttag_json);


        Call<ImageUplodeModel> call = RetrofitClient.getInstance().getnewResponseData().uploadimagedata(img_lode, token, user_description, user_id, requesthastg);
        call.enqueue(new Callback<ImageUplodeModel>() {
            @Override
            public void onResponse(Call<ImageUplodeModel> call, Response<ImageUplodeModel> response) {
                progressDialog.dismiss();
                ImageUplodeModel model = response.body();
                if (response.isSuccessful()) {
                    if (model.getStatus().equalsIgnoreCase("true")) {
                        Intent postintent = new Intent(AddStatusActivity.this, MainActivity.class);
                        Toast.makeText(AddStatusActivity.this, "Post SuccessFully", Toast.LENGTH_SHORT).show();
                        startActivity(postintent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddStatusActivity.this, "" + model.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUplodeModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddStatusActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void startService() {

        UploadService mService = new UploadService();
        if (!Constants.isMyServiceRunning(this, mService.getClass())) {
            Intent mServiceIntent = new Intent(this, mService.getClass());
            mServiceIntent.setAction("startservice");
            mServiceIntent.putExtra("description", activityAddStatusBinding.edtHstg.getText().toString());
            mServiceIntent.putExtra("users_json", users_json);
            mServiceIntent.putExtra("hashtags_json", hasttag_json);
            mServiceIntent.putExtra("auth_token", Comman.getInstance().getUser_id());
            UploadService.video = new File(video);
            startService(mServiceIntent);

            AddStatusActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendBroadcast(new Intent("uploadVideo"));
                    startActivity(new Intent(AddStatusActivity.this, MainActivity.class));
                }
            });


        } else {
            Toast.makeText(this, "Already a product addition in progress", Toast.LENGTH_SHORT).show();
        }


    }

    private void Toolbar() {
        setSupportActionBar(activityAddStatusBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityAddStatusBinding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);


    }

    @Override
    public void HasData(ArrayList<TagPojo> data, int position) {
        hasttag_json = hasttag_json + "#" + data.get(position).getName();
        hashTagValue = activityAddStatusBinding.edtHstg.getText().toString() + " " + " " + data.get(position).getName();
        activityAddStatusBinding.edtHstg.setText(hashTagValue);
        hashlist.add(data.get(position).getName());
        Log.e("list_hash_tag", "" + hashlist.size());
        for (String str : hashlist) {

        }
        dialog.dismiss();


    }

    @Override
    public void mentiondata(ArrayList<MentionPojo> data, int position) {
        users_json = users_json + "@" + mentionPojos.get(position).getNick_name();
        mention_value = activityAddStatusBinding.edtHstg.getText().toString() + " " + "@" + data.get(position).getNick_name();
        activityAddStatusBinding.edtHstg.setText(mention_value);
        mentionlist.add(data.get(position).getNick_name());
        Log.e("mention_list", "" + mentionlist.size());

        for (String mtr : mentionlist) {

        }
        mention_dialog.dismiss();


    }
}