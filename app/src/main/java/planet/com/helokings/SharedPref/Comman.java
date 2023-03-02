package planet.com.helokings.SharedPref;

public class Comman {
    public static Comman comman_obj;
    public String user_id = "";
    public String id = "";
    public String username = "";
    public String name = "";
    public String wallet_amount = "";
    public String image = "";
    String token = "";

    public boolean add_status = false;

    public static Comman getInstance() {
        if (comman_obj == null) {
            comman_obj = new Comman();

        }
        return comman_obj;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
