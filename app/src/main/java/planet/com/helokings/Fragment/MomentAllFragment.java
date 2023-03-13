package planet.com.helokings.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import planet.com.helokings.Adapter.MomentAllPostAdapter;
import planet.com.helokings.Model.MomentAllPostModel;
import planet.com.helokings.Pojo.MomentAllPostPojo;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.databinding.FragmentMomentAllBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MomentAllFragment extends Fragment {
    FragmentMomentAllBinding fragmentMomentAllBinding;
    ArrayList<MomentAllPostPojo> pojoArrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    MomentAllPostAdapter adapter;

    int scrollStatus;
    int valStatusStatus;
    int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMomentAllBinding = FragmentMomentAllBinding.inflate(inflater, container, false);
        View root = fragmentMomentAllBinding.getRoot();
        progressDialog = new ProgressDialog(getActivity(), R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        adapter = new MomentAllPostAdapter(getContext(), pojoArrayList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fragmentMomentAllBinding.momentAllPostRecyclerView.setAdapter(adapter);
        fragmentMomentAllBinding.momentAllPostRecyclerView.setLayoutManager(llm);

        PostData(0);

        fragmentMomentAllBinding.momentAllPostRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1))
                    onScrolledToBottom();
            }
        });


        return root;
    }

    private void onScrolledToBottom() {
        if (scrollStatus == 0) {
            scrollStatus = 0;
            if (valStatusStatus == 0) {
                page = page + 1;
                PostData(page);

            } else {
                page = 0;
                PostData(page);
            }
        }


    }

    private void PostData(int page) {
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        Call<MomentAllPostModel> call = RetrofitClient.getInstance().getAllApiResponse().allPostData(Comman.getInstance().getUser_id(), page + "");
        call.enqueue(new Callback<MomentAllPostModel>() {
            @Override
            public void onResponse(Call<MomentAllPostModel> call, Response<MomentAllPostModel> response) {
                progressDialog.dismiss();
                MomentAllPostModel momentAllPostModel = response.body();
                if (response.isSuccessful()) {
                    if (momentAllPostModel.getStatus().equalsIgnoreCase("true")) {
                        pojoArrayList = momentAllPostModel.getData();

                        adapter.setData(momentAllPostModel.getData());

                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "" + momentAllPostModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MomentAllPostModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}