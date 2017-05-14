package raspopova.diana.exptracker.ui.addExpenses.step2;

import raspopova.diana.exptracker.base.IGeneralView;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public interface IAddDetailsView extends IGeneralView {

    void fillView(String date, String amount, String description);

    void showAmountValidationError();

    void showDescriptionValidationError();

    void setDefaultDateValue();
}
