package planet.com.helokings.Model;

import java.util.ArrayList;

import planet.com.helokings.Pojo.MomentAllPostPojo;

public class MomentAllPostModel {

    private String status;
    private String msg;
    ArrayList<MomentAllPostPojo> data;

    public MomentAllPostModel(String status, String msg, ArrayList<MomentAllPostPojo> data) {
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

    public ArrayList<MomentAllPostPojo> getData() {
        return data;
    }

    public void setData(ArrayList<MomentAllPostPojo> data) {
        this.data = data;
    }
}
