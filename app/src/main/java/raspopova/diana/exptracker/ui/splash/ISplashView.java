package raspopova.diana.exptracker.ui.splash;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public interface ISplashView extends MvpView {

    void goToSignIn();

    void goToPinSet();

    void goToPinEnter();
}
