package raspopova.diana.exptracker.ui.chart;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import raspopova.diana.exptracker.contentProvider.ExpensesColumns;
import raspopova.diana.exptracker.contentProvider.SummaryObject;
import raspopova.diana.exptracker.utils.CategoryHelper;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

class ChartPresenter extends MvpBasePresenter<IChartView> {
    List<SummaryObject> list;

    ChartPresenter() {
        list = new ArrayList<>();
    }

    void getDataFromCursor(Cursor cursor, int id) {
        double amount = getAmount(cursor);

        if (amount > 0) {
            SummaryObject item = new SummaryObject();
            item.setCategoryId(id);
            item.setCategoryLogo(CategoryHelper.getDarkImageForCategory(id));
            item.setCategoryName(CategoryHelper.getNameForCategory(id));
            item.setAmount(amount);
            list.add(item);
        }
        getView().setPieChartData(list);
        getView().setListData(list);

    }

    private double getAmount(Cursor cursor) {
        double amount = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            amount += (cursor.getDouble(cursor.getColumnIndex(ExpensesColumns.AMOUNT)));
        }
        return amount;
    }

    public CharSequence generateCenterSpannableText() {
        SpannableString s = new SpannableString("May 01 - 14");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        return s;
    }
}
