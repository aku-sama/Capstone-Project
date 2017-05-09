package raspopova.diana.exptracker.ui.login;

import android.support.annotation.NonNull;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class LoginActivity extends GeneralActivity<ILoginView, LoginPresenter> implements ILoginView {
    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message, int... code) {

    }

    @Override
    public void showError(int message, int... code) {

    }
}
