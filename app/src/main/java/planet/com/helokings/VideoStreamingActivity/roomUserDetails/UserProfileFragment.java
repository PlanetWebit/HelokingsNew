package planet.com.helokings.VideoStreamingActivity.roomUserDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import im.zego.zegoexpress.entity.ZegoUser;
import planet.com.helokings.Pojo.RoomDetails.ResponseRoomDetails;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.VideoStreamingActivity.userlists.RoomMemberList.ResponseRoomMember;
import planet.com.helokings.databinding.FragmentUserProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends BottomSheetDialogFragment {
    FragmentUserProfileBinding binding;
    ZegoUser zegoUser;
    String roomid;
    List<ZegoUser> zegoUserList=new ArrayList<>();
    List<ZegoUser> zegoMemberUserList=new ArrayList<>();
    boolean hostisnot;

    public UserProfileFragment(List<ZegoUser> zegoUserList,String roomid,boolean hostisnot) {
        this.zegoUserList=zegoUserList;
        this.roomid=roomid;
        this.hostisnot=hostisnot;
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        OnlineList(zegoUserList);






        binding.onlinemembers.setText("Online("+zegoUserList.size()+")");
            getRoom();



            if (hostisnot){
                binding.joinorroomsetting.setText("Room Setting");

                binding.joinorroomsetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                    }
                });


            }else{
                binding.joinorroomsetting.setText("Join");

                binding.joinorroomsetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        requestMember();



                    }
                });

            }

            binding.onlinemembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.membersrequestlayout.setVisibility(View.GONE);
                OnlineList(zegoUserList);

                binding.joinedmembers.setTextColor(getResources().getColor(R.color.grayDark));
                binding.onlinemembers.setTextColor(getResources().getColor(R.color.dark_blue));


            }
        });




            binding.joinedmembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hostisnot){
                        binding.membersrequestlayout.setVisibility(View.VISIBLE);

                    }

                    binding.joinedmembers.setTextColor(getResources().getColor(R.color.dark_blue));
                    binding.onlinemembers.setTextColor(getResources().getColor(R.color.grayDark));
                    getRoomMember();
                }
            });

            binding.membersrequestlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestedMemberFragment requestedMemberFragment=new RequestedMemberFragment(roomid,hostisnot);
                    requestedMemberFragment.show(getChildFragmentManager(),"");
                }
            });


        return root;
    }



    public void requestMember() {



        Call<LoginModule> moduleCall = RetrofitClient.getInstance().getAllApiResponse().
                requestMember(Comman.getInstance().getUser_id(),roomid);
        moduleCall.enqueue(new Callback<LoginModule>() {
            @Override
            public void onResponse(Call<LoginModule> call, Response<LoginModule> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(getActivity(),"Request Send",Toast.LENGTH_LONG).show();
                    } else{

                        Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModule> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                binding.joinorroomsetting.setText("Already a member");
                binding.joinorroomsetting.setEnabled(false);
            }
        });
    }


    ZegoUser zegoUserMember;
    private void getRoomMember() {

        zegoMemberUserList.clear();

        Call<ResponseRoomMember> moduleCall = RetrofitClient.getInstance().getAllApiResponse().memberList(Comman.getInstance().getUser_id(), roomid,"1");
        moduleCall.enqueue(new Callback<ResponseRoomMember>() {
            @Override
            public void onResponse(Call<ResponseRoomMember> call, Response<ResponseRoomMember> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        for (int i=0; i<response.body().getData().size();i++){
                            zegoUserMember=new ZegoUser(response.body().getData().get(i).getUsername(),response.body().getData().get(i).getNickName());
                            zegoMemberUserList.add(zegoUserMember);
                        }
                        binding.joinedmembers.setText("Member("+zegoMemberUserList.size()+")");

                        memberList(zegoMemberUserList);

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

    private void OnlineList(List<ZegoUser> zegoUserList) {


        UserOnlineAdapter adapter = new UserOnlineAdapter(getContext(), zegoUserList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(llm);


    }
    private void memberList(List<ZegoUser> zegoUserList) {


        UserOnlineAdapter adapter = new UserOnlineAdapter(getContext(), zegoUserList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(llm);


    }


    private void getRoom() {


        Call<ResponseRoomDetails> moduleCall = RetrofitClient.getInstance().getAllApiResponse().
                roomDetails(Comman.getInstance().getUser_id(), roomid);
        moduleCall.enqueue(new Callback<ResponseRoomDetails>() {
            @Override
            public void onResponse(Call<ResponseRoomDetails> call, Response<ResponseRoomDetails> response) {
                ResponseRoomDetails module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        if(response.body().getData().size()>0){

                            binding.roomname.setText(response.body().getData().get(0).getRoomName());
                            binding.roomId.setText(response.body().getData().get(0).getRoomId());
                            binding.roomownername.setText(response.body().getData().get(0).getNickName());
//                            hostLevel.setText("ID: "+response.body().getData().get(0).getRoomId());
                            Glide.with(getActivity()).load(response.body().getData().get(0).getRoomImage()).into(binding.roomImage);
                            Glide.with(getActivity()).load(response.body().getData().get(0).getImage()).into(binding.roomhostprofile);
                            if(!response.body().getData().get(0).getFrameImage().equalsIgnoreCase("")) {
                              //  Glide.with(getActivity()).load(response.body().getData().get(0).getFrameImage()).into(videoFrame);
                            }
                        }
                    } else{

                        Toast.makeText(getActivity(),module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseRoomDetails> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}