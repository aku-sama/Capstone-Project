package raspopova.diana.exptracker.ui.pin.pinSet;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class PinSetActivity extends GeneralActivity<IPinSetView, PinSetPresenter,RestorableViewState<IPinSetView>> implements IPinSetView {
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

    @NonNull
    @Override
    public RestorableViewState<IPinSetView> createViewState() {
        return null;
    }

    @Override
    public void onNewViewStateInstance() {

    }
}
