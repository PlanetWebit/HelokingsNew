package planet.com.helokings.Pojo;

public class LikeUnlikeModel {
    private String status;
    private String msg;

    public LikeUnlikeModel(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
