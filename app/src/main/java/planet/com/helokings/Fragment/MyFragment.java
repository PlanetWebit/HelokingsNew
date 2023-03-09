package planet.com.helokings.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import planet.com.helokings.Activity.CreateAudioRoomActivity;
import planet.com.helokings.Pojo.MyRooms.ResponseRoomdata;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.AudioStreamingActivity;

import planet.com.helokings.databinding.FragmentMyBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFragment extends Fragment {

    FragmentMyBinding fragmentMyBinding;
    int roomStatus=0;
    String menuValue = "",roomName="",roomimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        getRoom();
        fragmentMyBinding.lvCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startActivity(new Intent(getActivity(), CreateAudioRoomActivity.class));

            }
        });

        fragmentMyBinding.gotoroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), AudioStreamingActivity.class);
                intent.putExtra("roomId",  Comman.getInstance().getUsername());
                intent.putExtra("userid", Comman.getInstance().getUsername());
                intent.putExtra("username", Comman.getInstance().getName());
                intent.putExtra("roomimage", roomimage);
                intent.putExtra("roomtype","audio");
                intent.putExtra("host",true);
                startActivity(intent);
            }
        });


        return fragmentMyBinding.getRoot();
    }



    private void getRoom() {
        Call<ResponseRoomdata> moduleCall = RetrofitClient.getInstance().getAllApiResponse().
                roomList(Comman.getInstance().getUser_id());
        moduleCall.enqueue(new Callback<ResponseRoomdata>() {
            @Override
            public void onResponse(Call<ResponseRoomdata> call, Response<ResponseRoomdata> response) {
                ResponseRoomdata module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        if(response.body().getData().size()>0){
                            roomStatus=1;
                            roomName=response.body().getData().get(0).getRoomName();
                            roomimage=response.body().getData().get(0).getRoomImage();
                             fragmentMyBinding.gotoroom.setVisibility(View.VISIBLE);
                            fragmentMyBinding.lvCreateRoom.setVisibility(View.GONE);
                            fragmentMyBinding.username.setText(roomName);
                            Glide.with(getContext()).load(roomimage).into(fragmentMyBinding.crPckimage);

                         }else{

                            roomStatus=0;
                            fragmentMyBinding.gotoroom.setVisibility(View.GONE);
                            fragmentMyBinding.lvCreateRoom.setVisibility(View.VISIBLE);
                        }
                    } else{
                        roomStatus=0;


                     }
                }else{
                }

            }

            @Override
            public void onFailure(Call<ResponseRoomdata> call, Throwable t) {

            }
        });
    }
}