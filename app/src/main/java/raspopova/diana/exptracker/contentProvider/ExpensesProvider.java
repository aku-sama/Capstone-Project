package raspopova.diana.exptracker.contentProvider;

import android.content.Context;

import me.everything.providers.core.AbstractProvider;
import me.everything.providers.core.Data;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class ExpensesProvider extends AbstractProvider {

    protected ExpensesProvider(Context context) {
        super(context);
    }

    /**
     * Get all expenses
     */
    public Data<Expenses> getExpenses() {
        Data<Expenses> expenses = getContentTableData(Expenses.uri, Expenses.class);
        return expenses;
    }
}
