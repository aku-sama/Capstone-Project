package raspopova.diana.exptracker.ui.extensesDetails;

import android.database.Cursor;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import raspopova.diana.exptracker.contentProvider.Expenses;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class DetailsPresenter extends MvpBasePresenter<IDetailsView> {

    private static final SimpleDateFormat MONT_FORMATTER = new SimpleDateFormat("MMM dd");

    private List<Expenses> expensesList;
    private long dateStart;
    private long dateEnd;

    void initializeDefaultPeriodData() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 1);
        dateStart = cal.getTime().getTime();

        if (cal.get(Calendar.MONTH) < 12)
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 1);
        else
            cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH) - 11, 1, 0, 0, 1);

        dateEnd = cal.getTime().getTime();

        getView().initLoaders(dateStart, dateEnd, true);
        getView().setToolbarTitle(generateToolbarText());
    }

    void updatePurchaseDate(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 1);
        dateStart = cal.getTime().getTime();

        cal.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 0, 0, 1);
        dateEnd = cal.getTime().getTime();

        getView().initLoaders(dateStart, dateEnd, false);
        getView().setToolbarTitle(generateToolbarText());
    }


    private String generateToolbarText() {
        String month = MONT_FORMATTER.format(dateStart);
        String monthEnd = MONT_FORMATTER.format(dateEnd);
        return month + " - " + monthEnd;
    }

    void getDataFromCursor(Cursor cursor) {
        expensesList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Expenses expenses = Expenses.fromCursor(cursor);
            expensesList.add(expenses);
        }
    }

    public List<Expenses> getExpenses() {
        return expensesList;
    }
}
