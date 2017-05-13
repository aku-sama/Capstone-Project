package raspopova.diana.exptracker.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class LoginViewState implements RestorableViewState<ILoginView> {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    String email;
    String password;


    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(EMAIL, email);
        out.putString(PASSWORD, password);
    }

    @Override
    public RestorableViewState<ILoginView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        email = in.getString(EMAIL);
        password = in.getString(PASSWORD);
        return this;
    }

    @Override
    public void apply(ILoginView view, boolean retained) {
        view.fillView(email, password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
