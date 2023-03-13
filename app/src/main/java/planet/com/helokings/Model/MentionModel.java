package planet.com.helokings.Model;

import java.util.ArrayList;

import planet.com.helokings.Pojo.MentionPojo;

public class MentionModel {
    private String status;
    private String msg;
    ArrayList<MentionPojo> data;

    public MentionModel(String status, String msg, ArrayList<MentionPojo> data) {
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

    public ArrayList<MentionPojo> getData() {
        return data;
    }

    public void setData(ArrayList<MentionPojo> data) {
        this.data = data;
    }
}
