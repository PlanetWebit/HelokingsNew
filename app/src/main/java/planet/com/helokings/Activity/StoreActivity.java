package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import planet.com.helokings.Adapter.StoreFrameAdapter;
import planet.com.helokings.Adapter.StoreItemAdapter;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Model.StoreFrameModel;
import planet.com.helokings.Model.StoreModel;
import planet.com.helokings.Pojo.Fonts.ResponseFonts;
import planet.com.helokings.Pojo.Frames.ResponseFrames;
import planet.com.helokings.Pojo.StoreModelPojo;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityStoreBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity implements StoreItemAdapter.ContentClick {
    ActivityStoreBinding storeBinding;
    ArrayList<StoreModelPojo> data;
    private UserSharePreferancess userSharePreferancess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeBinding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(storeBinding.getRoot());

        toolbar();
        contenttextView();
        getFramesList();
        ;
        //Log.e("token", "" + Comman.comman_obj.getToken());
    }

    private void getFramesList() {

        Call<StoreFrameModel> call = RetrofitClient.getInstance().getAllApiResponse().getFrames(Comman.getInstance().getUser_id(), "2");
        call.enqueue(new Callback<StoreFrameModel>() {
            @Override
            public void onResponse(Call<StoreFrameModel> call, Response<StoreFrameModel> response) {
                StoreFrameModel storeFrameModel = response.body();
                if (response.isSuccessful()) {
                    if (storeFrameModel.getStatus().equalsIgnoreCase("true")) {
                        data = storeFrameModel.getData();

                        StoreFrameAdapter adapter = new StoreFrameAdapter(StoreActivity.this, data);
                        storeBinding.frameListRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        storeBinding.frameListRecyclerView.setAdapter(adapter);

                    } else {
                        if (response.body().getMsg().equalsIgnoreCase("unauthorized login")) {
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            userSharePreferancess.setStringValue("username", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setUsername("");
                            Comman.getInstance().setId("");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("status", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<StoreFrameModel> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getWallpapers() {
        Call<StoreFrameModel> call = RetrofitClient.getInstance().getAllApiResponse().getWallpapers(Comman.comman_obj.getUser_id(), "5");
        call.enqueue(new Callback<StoreFrameModel>() {
            @Override
            public void onResponse(Call<StoreFrameModel> call, Response<StoreFrameModel> response) {
                StoreFrameModel storeFrameModel = response.body();
                if (response.isSuccessful()) {
                    if (storeFrameModel.getStatus().equalsIgnoreCase("true")) ;
                    data = storeFrameModel.getData();
                    StoreFrameAdapter adapter = new StoreFrameAdapter(StoreActivity.this, data);
                    storeBinding.frameListRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    storeBinding.frameListRecyclerView.setAdapter(adapter);

                } else {

                }


            }

            @Override
            public void onFailure(Call<StoreFrameModel> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void contenttextView() {
        StoreModel[] storeModels = new StoreModel[]{
                new StoreModel("Frame"),
                new StoreModel("Text Bubble"),
                new StoreModel("Fonts"),
                new StoreModel("Entrance effect"),
                new StoreModel("Profile Meteor"),
                new StoreModel("Ribbon"),
                new StoreModel("Special"),
                new StoreModel("Background"),
                new StoreModel("Seat cover"),
                new StoreModel("Seat Emoji")
        };

        StoreItemAdapter storeItemAdapter = new StoreItemAdapter(this, storeModels, this::contentdata);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        storeBinding.storeContentRecyclerView.setAdapter(storeItemAdapter);
        storeBinding.storeContentRecyclerView.setLayoutManager(llm);


    }


    private void toolbar() {
        setSupportActionBar(storeBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        storeBinding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);


    }


    @Override
    public void contentdata(StoreModel[] storeModels, int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();

        if (position == 0) {
            getFramesList();
        } else if (position == 1) {
            getWallpapers();

        } else if (position == 2) {
            getFount();
        }


    }

    private void getFount() {

        Call<ResponseFonts> call = RetrofitClient.getInstance().getAllApiResponse().getFonts(Comman.getInstance().getUser_id(), "3");
        call.enqueue(new Callback<ResponseFonts>() {
            @Override
            public void onResponse(Call<ResponseFonts> call, Response<ResponseFonts> response) {

            }

            @Override
            public void onFailure(Call<ResponseFonts> call, Throwable t) {

            }
        });
    }
}