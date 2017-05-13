package raspopova.diana.exptracker.ui.extensesDetails;

import android.support.annotation.NonNull;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ExpensesDetailsActivity extends GeneralActivity<IDetailsView, DetailsPresenter, DetailsViewModel> implements IDetailsView {
    @NonNull
    @Override
    public DetailsPresenter createPresenter() {
        return new DetailsPresenter();
    }

    @NonNull
    @Override
    public DetailsViewModel createViewState() {
        return new DetailsViewModel();
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public void showProgress() {
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void showError(String message, int... code) {
        showErrorSnackBar(message);
    }

    @Override
    public void showError(int message, int... code) {
        showErrorSnackBar(message);
    }

}
