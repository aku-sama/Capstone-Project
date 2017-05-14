package raspopova.diana.exptracker.ui.chart;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ChartViewState implements RestorableViewState<IChartView> {

    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<IChartView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        return this;
    }

    @Override
    public void apply(IChartView view, boolean retained) {
        view.resetChart();
    }
}
