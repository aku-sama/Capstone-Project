package raspopova.diana.exptracker.ui.chart;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import raspopova.diana.exptracker.contentProvider.ExpensesColumns;
import raspopova.diana.exptracker.contentProvider.SummaryObject;
import raspopova.diana.exptracker.utils.CategoryHelper;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

class ChartPresenter extends MvpBasePresenter<IChartView> {

    public static final SimpleDateFormat MONT_FORMATTER = new SimpleDateFormat("MMM dd");

    private List<SummaryObject> list;
    private long dateStart;
    private long dateEnd;

    ChartPresenter() {
        list = new ArrayList<>();
    }

    void initializeDefaultPeriodData() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 1);
        dateStart = cal.getTime().getTime();

        if (cal.get(Calendar.MONTH) < 12)
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 1);
        else
            cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH) - 11, 1, 0, 0, 1);

        dateEnd = cal.getTime().getTime();

        list = new ArrayList<>();
        getView().initLoaders(dateStart, dateEnd, true);
        getView().setChartCenterText(generateCenterSpannableText());
    }

    void getDataFromCursor(Cursor cursor, int id) {
        getView().hideProgress();
        double amount = getAmount(cursor);

        if (amount > 0) {
            SummaryObject item = new SummaryObject();
            item.setCategoryId(id);
            item.setCategoryLogo(CategoryHelper.getDarkImageForCategory(id));
            item.setCategoryName(CategoryHelper.getNameForCategory(id));
            item.setAmount(amount);
            list.add(item);
        }

        if (list.size() > 0) {
            getView().setPieChartData(list);
            getView().setListData(list);
        }
    }

    private double getAmount(Cursor cursor) {
        double amount = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            amount += (cursor.getDouble(cursor.getColumnIndex(ExpensesColumns.AMOUNT)));
        }
        return amount;
    }

    private CharSequence generateCenterSpannableText() {
        String month = MONT_FORMATTER.format(dateStart);
        String monthEnd = MONT_FORMATTER.format(dateEnd);
        SpannableString s = new SpannableString(month + "\n" + monthEnd);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
        return s;
    }


    void updatePurchaseDate(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 1);
        dateStart = cal.getTime().getTime();

        cal.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 0, 0, 1);
        dateEnd = cal.getTime().getTime();

        list = new ArrayList<>();
        getView().showProgress();
        getView().initLoaders(dateStart, dateEnd, false);
        getView().setChartCenterText(generateCenterSpannableText());
    }


}
