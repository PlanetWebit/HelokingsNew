package planet.com.helokings.VideoStreamingActivity.features;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.skydoves.elasticviews.ElasticButton;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.helokings.R;

public class RoomDetailsFragment extends BottomSheetDialogFragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    private CircleImageView hostImage;
    private ImageView roomImage;
    private ImageView videoFrame;
    private TextView roomName;
    private TextView hostName;
    private TextView hostLevel;
    private TextView roomID;
    private ElasticButton roomSetting;
    private ElasticButton becomeMember;
    private ElasticButton follow;
    private RelativeLayout manual;
    String authtoken;String userId;String roomid;boolean islocaluserhost;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.MyDialogStyle);
    }

    public RoomDetailsFragment(String authtoken, String userId, String roomid, boolean islocaluserhost) {
        this.authtoken = authtoken;
        this.userId = userId;
        this.roomid = roomid;
        this.islocaluserhost = islocaluserhost;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.room_details_layout, container, false);


        getRoom();
        return view;

    }



    private void getRoom() {


    }




}
