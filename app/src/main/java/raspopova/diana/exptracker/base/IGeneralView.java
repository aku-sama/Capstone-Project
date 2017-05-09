package raspopova.diana.exptracker.base;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public  interface IGeneralView extends MvpView {

    void showProgress();

    void hideProgress();

    void showError(String message, int... code);

    void showError(int message, int... code);
}
