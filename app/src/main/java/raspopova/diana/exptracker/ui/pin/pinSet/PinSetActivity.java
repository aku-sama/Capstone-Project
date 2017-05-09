package raspopova.diana.exptracker.ui.pin.pinSet;

import android.support.annotation.NonNull;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class PinSetActivity extends GeneralActivity<IPinSetView, PinSetPresenter> implements IPinSetView {
    @NonNull
    @Override
    public PinSetPresenter createPresenter() {
        return new PinSetPresenter();
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
