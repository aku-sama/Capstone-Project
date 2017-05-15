package raspopova.diana.exptracker.ui.extensesDetails;

import raspopova.diana.exptracker.base.IGeneralView;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public interface IDetailsView extends IGeneralView {

    void initLoaders(long startDate, long endDate, boolean isFirstStart);

    void setToolbarTitle(String text);
}
