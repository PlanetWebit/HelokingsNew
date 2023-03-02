package planet.com.helokings.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharePreferancess {
    public static SharedPreferences settings;
    private final SharedPreferences.Editor editor;
    private static final String FILENAME = "HeloKings";
    Context context;

    public UserSharePreferancess(Context ctx) {

        settings = ctx.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        context = ctx;

    }

    public String setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        return key;
    }


    public void setIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public String getStringValue(String key) {
        return settings.getString(key, "");
    }

    public static int getIntValue(String key) {
        return settings.getInt(key, 0);
    }

}
