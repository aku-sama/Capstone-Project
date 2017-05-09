package raspopova.diana.exptracker.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class Config {
    public static final SimpleDateFormat DATE_FORMAT_OUTPUT = new SimpleDateFormat("yyyy.MM.dd");
    public static final long PIN_TIMEOUT = 30000L;

    private static final String PREF_FILE = "veo_prefs";

    private static final String TOKEN = "exp_token";
    private static final String PIN = "exp_pin";


    private static SharedPreferences getSharedPreferences() {
        return ExpApplication.getInstance().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        SharedPreferences settings = getSharedPreferences();
        return settings.edit();
    }


    public static String getAuthorizationToken() {
        SharedPreferences sharedPref = getSharedPreferences();
        return sharedPref.getString(TOKEN, "");
    }


    public static void setAuthorizationToken(String token) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static boolean isAuthExist() {
        String token = getAuthorizationToken();
        return !token.isEmpty();

    }

    public static String getPin() {
        SharedPreferences sharedPref = getSharedPreferences();
        return sharedPref.getString(PIN, "");
    }


    public static void setPin(String pin) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(PIN, pin);
        editor.apply();
    }


    public static boolean isPinCreated() {
        String pin = getPin();
        return pin != null && pin.length() == 4;

    }
}
