package raspopova.diana.exptracker.ui.chart;

import java.util.List;

import raspopova.diana.exptracker.base.IGeneralView;
import raspopova.diana.exptracker.contentProvider.SummaryObject;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public interface IChartView extends IGeneralView {
    void setPieChartData(List<SummaryObject> list);

    void setListData(List<SummaryObject> list);

    void resetChart();
}
