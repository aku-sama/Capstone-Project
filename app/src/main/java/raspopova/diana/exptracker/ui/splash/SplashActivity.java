package raspopova.diana.exptracker.ui.splash;

import android.support.annotation.NonNull;
import android.os.Bundle;

import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.ui.login.LoginActivity;
import raspopova.diana.exptracker.ui.pin.pinEdit.PinEditActivity;
import raspopova.diana.exptracker.ui.pin.pinSet.PinSetActivity;

public class SplashActivity extends GeneralActivity<ISplashView, SplashPresenter>
        implements ISplashView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void goToSignIn() {
        startActivity(LoginActivity.class, true);
    }

    @Override
    public void goToPinSet() {
        startActivity(PinSetActivity.class, true);
    }

    @Override
    public void goToPinEnter() {
        startActivity(PinEditActivity.class, true);

    }
}
