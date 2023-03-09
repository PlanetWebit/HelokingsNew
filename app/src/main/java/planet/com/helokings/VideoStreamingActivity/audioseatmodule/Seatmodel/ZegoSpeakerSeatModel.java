package planet.com.helokings.VideoStreamingActivity.audioseatmodule.Seatmodel;

import com.google.gson.annotations.SerializedName;


/**
 * Class speaker seat status information.
 * <p>Description: This class contains the speaker seat status information.</>
 */
public class ZegoSpeakerSeatModel {

    /**
     * User ID, null indicates the current speaker seat is available/untaken.
     */
    @SerializedName("id")
    public String userID;

    /**
     * The seat index.
     */
    @SerializedName("index")
    public int seatIndex;

    /**
     * The speaker seat mic status.
     */
    @SerializedName("mic")
    public boolean mic;

    /**
     * The speaker seat mic status.
     */
    @SerializedName("room_role")
    public String room_role;
    @SerializedName("iid")
    public String iid;


    @SerializedName("frame")
    String frame;


    @SerializedName("image")
    String image;


    public ZegoSpeakerSeatModel(String userID, int seatIndex, boolean mic, String room_role, String image, String frame, ZegoSpeakerSeatStatus n, float soundLevel, ZegoNetWorkQuality network) {
        this.userID = userID;
        this.seatIndex = seatIndex;
        this.mic = mic;
        this.room_role = room_role;
        this.image = image;
        this.frame = frame;
        this.status = status;
        this.soundLevel = soundLevel;
        this.network = network;
    }

    public ZegoSpeakerSeatModel(String userID, String iid,int seatIndex, boolean mic, String room_role, String frame, String image, ZegoSpeakerSeatStatus status) {
        this.userID = userID;
        this.seatIndex = seatIndex;
        this.mic = mic;
        this.room_role = room_role;
        this.frame = frame;
        this.image = image;
        this.status = status;
        this.iid = iid ;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getRoom_role() {
        return room_role;
    }

    public void setRoom_role(String room_role) {
        this.room_role = room_role;
    }

    public String getImage() {
        return image;
    }

    public String getFrame() {
        return frame;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public void setFrame(String frame) {
        this.frame = frame;
    }

    /**
     * The speaker seat status, it is untaken by default.
     */
    public ZegoSpeakerSeatStatus status;

    /**
     * Volume value, a local record attribute, used for displaying the sound level.
     */
    public transient float soundLevel;

    /**
     * status, a local record attributes. It is calculated based on stream quality, can be used for displaying the network status.
     */
    public transient ZegoNetWorkQuality network = ZegoNetWorkQuality.Good;

    @Override
    public String toString() {
        return "ZegoSpeakerSeatModel{" +
            "userID='" + userID + '\'' +
            ", seatIndex=" + seatIndex +
            ", mic=" + mic +
            ", soundLevel=" + soundLevel +
            ", status=" + status +
            '}';
    }
}
