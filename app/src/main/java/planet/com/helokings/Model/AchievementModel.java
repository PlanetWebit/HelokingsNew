package planet.com.helokings.Model;

public class AchievementModel {
    private String tv_name;
    private int iv_logo;

    public AchievementModel(String tv_name, int iv_logo) {
        this.tv_name = tv_name;
        this.iv_logo = iv_logo;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public int getIv_logo() {
        return iv_logo;
    }

    public void setIv_logo(int iv_logo) {
        this.iv_logo = iv_logo;
    }
}
