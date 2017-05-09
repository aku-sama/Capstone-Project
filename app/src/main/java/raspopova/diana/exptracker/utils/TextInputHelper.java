package raspopova.diana.exptracker.utils;

import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import raspopova.diana.exptracker.app.ExpApplication;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class TextInputHelper {

    private static final int ERROR_SHOW_TIMEOUT = 4000;

    public static void showError(final TextInputLayout layout, final int stringId) {
        layout.setErrorEnabled(true);
        layout.setError(ExpApplication.getInstance().getString(stringId));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setError("");
                layout.setErrorEnabled(false);
            }
        }, ERROR_SHOW_TIMEOUT);

        layout.requestFocus();
    }

    public static String getValue(final TextInputLayout layout) {
        EditText edit = layout.getEditText();
        if (edit != null) {
            return layout.getEditText().getText().toString();
        }

        return "";
    }
}
