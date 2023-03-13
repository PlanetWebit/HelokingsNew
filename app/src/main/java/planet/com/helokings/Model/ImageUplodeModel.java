package planet.com.helokings.Model;

import java.util.ArrayList;

import planet.com.helokings.Pojo.PostPojo;

public class ImageUplodeModel {
    private String status;
    private String msg;
    ArrayList<PostPojo> data;

    public ImageUplodeModel(String status, String msg, ArrayList<PostPojo> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
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

    public ArrayList<PostPojo> getData() {
        return data;
    }

    public void setData(ArrayList<PostPojo> data) {
        this.data = data;
    }
}
