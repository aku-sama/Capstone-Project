package raspopova.diana.exptracker.ui.addExpenses.step2;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

class AddDetailsViewState implements RestorableViewState<IAddDetailsView> {

    private static final String PURCHASE_DESCRIPTION = "purchase_description";
    private static final String PURCHASE_AMOUNT = "purchase_amount";
    private static final String PURCHASE_DATE = "purchase_date";
    private String date;
    private String amount;
    private String description;

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(PURCHASE_DATE, date);
        out.putString(PURCHASE_AMOUNT, amount);
        out.putString(PURCHASE_DESCRIPTION, description);
    }

    @Override
    public RestorableViewState<IAddDetailsView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        date = in.getString(PURCHASE_DATE);
        amount = in.getString(PURCHASE_AMOUNT);
        description = in.getString(PURCHASE_DESCRIPTION);
        return this;
    }

    @Override
    public void apply(IAddDetailsView view, boolean retained) {
        view.fillView(date, amount, description);
        view.restoreAttachment();
    }

    void setDate(String date) {
        this.date = date;
    }

    void setAmount(String amount) {
        this.amount = amount;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
