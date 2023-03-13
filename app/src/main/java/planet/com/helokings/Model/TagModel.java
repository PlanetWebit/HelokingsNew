package planet.com.helokings.Model;

import java.util.ArrayList;

import planet.com.helokings.Pojo.TagPojo;

public class TagModel {
    String status;
    String msg;
    ArrayList<TagPojo> data;

    public TagModel(String status, String msg, ArrayList<TagPojo> data) {
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

    public ArrayList<TagPojo> getData() {
        return data;
    }

    public void setData(ArrayList<TagPojo> data) {
        this.data = data;
    }
}
