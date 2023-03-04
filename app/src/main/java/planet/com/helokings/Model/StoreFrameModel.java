package planet.com.helokings.Model;

import java.util.ArrayList;

import planet.com.helokings.Pojo.StoreModelPojo;

public class StoreFrameModel {
    private String status;
    public String msg;
    ArrayList<StoreModelPojo> data;

    public StoreFrameModel(String status, String msg, ArrayList<StoreModelPojo> data) {
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

    public ArrayList<StoreModelPojo> getData() {
        return data;
    }

    public void setData(ArrayList<StoreModelPojo> data) {
        this.data = data;
    }
}
