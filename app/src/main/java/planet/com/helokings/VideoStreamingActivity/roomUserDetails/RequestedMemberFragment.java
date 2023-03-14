package planet.com.helokings.VideoStreamingActivity.roomUserDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.Service.Refresh;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.userlists.RoomMemberList.ResponseRoomMember;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestedMemberFragment extends BottomSheetDialogFragment implements Refresh {


    private ImageView back;
    private RecyclerView requests;
    public static Refresh refresh;
    String roomid;

    public RequestedMemberFragment( String roomid, boolean islocaluserhost) {
        this.roomid=roomid;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.room_requested_user_layout, container, false);

        initUI(view);
        refresh=this;



        getRoomMember();
        return view;

    }

    void initUI(View view){

        back = view.findViewById(R.id.back);
        requests = view.findViewById(R.id.requests);

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );


    }

    private void getRoomMember() {


         Call<ResponseRoomMember> moduleCall = RetrofitClient.getInstance().getAllApiResponse().memberList(Comman.getInstance().getUser_id(), roomid,"0");
        moduleCall.enqueue(new Callback<ResponseRoomMember>() {
            @Override
            public void onResponse(Call<ResponseRoomMember> call, Response<ResponseRoomMember> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        requests.setLayoutManager(new LinearLayoutManager(getActivity()));
                        requests.setHasFixedSize(true);
                        requests.setAdapter(new MemberRequestListAdapter(getActivity(),response.body().getData(),roomid));
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseRoomMember> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onRefresh() {
        getRoomMember();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
