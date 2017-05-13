package raspopova.diana.exptracker.ui.chart;

import android.support.annotation.NonNull;

import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ChartActivity extends GeneralActivity<IChartView, ChartPresenter, ChartViewState> implements IChartView {
    @NonNull
    @Override
    public ChartPresenter createPresenter() {
        return new ChartPresenter();
    }

    @NonNull
    @Override
    public ChartViewState createViewState() {
        return new ChartViewState();
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
