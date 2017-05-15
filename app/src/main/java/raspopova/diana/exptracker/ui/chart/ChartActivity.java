package raspopova.diana.exptracker.ui.chart;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.contentProvider.ExpensesProvider;
import raspopova.diana.exptracker.contentProvider.SummaryObject;
import raspopova.diana.exptracker.ui.addExpenses.step1.AddExpensesCategoryActivity;
import raspopova.diana.exptracker.ui.extensesDetails.ExpensesDetailsActivity;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ChartActivity extends GeneralActivity<IChartView, ChartPresenter, ChartViewState>
        implements IChartView, LoaderManager.LoaderCallbacks, DatePickerDialog.OnDateSetListener {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.textSummary)
    TextView textSummary;

    @BindView(R.id.expensesList)
    RecyclerView expensesList;

    @BindArray(R.array.category_id)
    int[] categoryId;

    private SummaryAdapter adapter;
    private List<SummaryObject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setRecycler();
        presenter.initializeDefaultPeriodData();
        setPiechart();
    }

    @Override
    public void initLoaders(long startDate, long endDate, boolean isFirstStart) {
        Bundle args = new Bundle();
        args.putLong(START_DATE, startDate);
        args.putLong(END_DATE, endDate);
        if (isFirstStart) {
            for (int aCategoryId : categoryId)
                getSupportLoaderManager().initLoader(aCategoryId, args, this);
        } else {
            for (int aCategoryId : categoryId)
                getSupportLoaderManager().restartLoader(aCategoryId, args, this);
        }
    }

    @Override
    public void setChartCenterText(CharSequence text) {
        pieChart.setCenterText(text);
    }

    @Override
    public void setSummaryText(String text) {
        textSummary.setText(text);
    }

    private void setPiechart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.getLegend().setEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(53f);

        pieChart.setDrawCenterText(true);

        pieChart.setEntryLabelColor(Color.GRAY);
        pieChart.setEntryLabelTextSize(8f);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

    }

    private void setRecycler() {
        adapter = new SummaryAdapter();
        expensesList.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        expensesList.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showDateFilter();
                return true;
            case R.id.action_list:
                startActivity(ExpensesDetailsActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDateFilter() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ChartActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

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

    @OnClick(R.id.fab)
    void onAddFabClick() {
        startActivity(AddExpensesCategoryActivity.class);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        long startDate = args.getLong(START_DATE);
        long endDate = args.getLong(END_DATE);
        return new CursorLoader(
                this,
                ExpensesProvider.getExpensesPath(),
                Expenses.DEFAULT_PROJECTION,
                "(OWNER_ID  = '" + Config.getAuthorizationToken() + "') AND " +
                        "(CATEGORY_ID = " + String.valueOf(id) +
                        ") AND (PURCHASE_DATE >= " + startDate +
                        ") AND (PURCHASE_DATE < " + endDate + ")",
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        presenter.getDataFromCursor(cursor, loader.getId());
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.setData(new ArrayList<SummaryObject>());
    }

    @Override
    public void setPieChartData(List<SummaryObject> list) {

        ArrayList<PieEntry> entries = new ArrayList<>(list.size());

        for (SummaryObject item : list) {

            entries.add(new PieEntry((float) item.getAmount(),
                    item.getCategoryName(), null));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.GRAY);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    public void setListData(List<SummaryObject> list) {
        adapter.setData(list);
    }

    @Override
    public void resetChart() {
        if (pieChart.getData() != null) {
            pieChart.getData().clearValues();
            pieChart.highlightValues(null);
            pieChart.invalidate();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        presenter.updatePurchaseDate(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd);
    }
}
