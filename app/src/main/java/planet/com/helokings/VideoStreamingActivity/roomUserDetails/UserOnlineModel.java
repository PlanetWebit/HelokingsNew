package planet.com.helokings.VideoStreamingActivity.roomUserDetails;

public class UserOnlineModel {
    private int onlineuser_iv;
    private String user_name;

    public UserOnlineModel(int onlineuser_iv, String user_name) {
        this.onlineuser_iv = onlineuser_iv;
        this.user_name = user_name;
    }

    public int getOnlineuser_iv() {
        return onlineuser_iv;
    }

    public void setOnlineuser_iv(int onlineuser_iv) {
        this.onlineuser_iv = onlineuser_iv;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
