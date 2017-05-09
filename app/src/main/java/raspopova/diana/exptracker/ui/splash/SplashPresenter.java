package raspopova.diana.exptracker.ui.splash;

import android.os.Handler;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import raspopova.diana.exptracker.app.Config;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class SplashPresenter extends MvpBasePresenter<ISplashView> {
    private static final int DELAY_MILLIS = 1500;

    public SplashPresenter() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        }, DELAY_MILLIS);
    }

    private void initialize() {
        if (Config.isAuthExist() && Config.isPinCreated()) {
            getView().goToPinEnter();
        } else if (Config.isAuthExist() && !Config.isPinCreated()) {
            getView().goToPinSet();
        } else
            getView().goToSignIn();
    }
}
