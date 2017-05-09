package raspopova.diana.exptracker.ui.pin.pinEdit;

import android.support.annotation.NonNull;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class PinEditActivity extends GeneralActivity<IPinEditView, PinEditPresenter> implements IPinEditView {
    @NonNull
    @Override
    public PinEditPresenter createPresenter() {
        return new PinEditPresenter();
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
