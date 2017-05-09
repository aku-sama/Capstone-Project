package raspopova.diana.exptracker.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class ExpApplication  extends Application {

    public static final String APP_LOCKED_TIME = "appLockedTime";
    public static final String APP_LOCKED = "appLocked";

    private static ExpApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static ExpApplication getInstance() {
        return instance;
    }

    public boolean getWasBackgroundState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean(APP_LOCKED, false) && Config.isAuthExist() && Config.isPinCreated();
    }

    public void saveWasBackgroundState(final boolean status) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean(APP_LOCKED, status).apply();
    }

    public long getBackgroundStateTimestamp() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getLong(APP_LOCKED_TIME, new Date().getTime());
    }

    public void saveBackgroundStateTimestamp(final long time) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putLong(APP_LOCKED_TIME, time).apply();
    }

    @SuppressWarnings("deprecation")
    public boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
