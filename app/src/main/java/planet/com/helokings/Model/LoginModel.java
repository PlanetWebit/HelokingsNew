package planet.com.helokings.Model;

public class LoginModel {
    String status;
    String msg;
    String verification_code;

    public LoginModel(String status, String msg, String verification_code) {
        this.status = status;
        this.msg = msg;
        this.verification_code = verification_code;
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

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
