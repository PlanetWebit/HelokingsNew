package planet.com.helokings.SharedPref;

import android.content.Context;

import org.json.JSONException;



public class AuthInfoManager {

    private AuthInfoManager() {
    }

    private static final class Holder {

        private static final AuthInfoManager INSTANCE = new AuthInfoManager();
    }

    public static AuthInfoManager getInstance() {
        return Holder.INSTANCE;
    }

    String appSign = "5a19ae55ccd73cd763126c5ebd4f93042ad73a43c1f9d25288df02669478cc55";

    private String serverSecret="cf3338f181a8bc377bed89354d1bbe22";
    private long appID=455394839;
    public Context context;

    public long getAppID() {
        return appID;
    }

    public String  getAppSign() {
        return appSign;
    }

    public void init(Context context) {
        this.context = context;

    }
/*
    private String readJsonFile(Context context) {
        String jsonStr = "";
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            inputStream.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/


}