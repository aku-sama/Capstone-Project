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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
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
        implements IChartView, LoaderManager.LoaderCallbacks {

    @BindView(R.id.spinnerMode)
    Spinner spinnerMode;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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
        setSpinnerMenu();
        setPiechart();
        setRecycler();
        for (int aCategoryId : categoryId) {
            getSupportLoaderManager().initLoader(aCategoryId, null, this);
        }
    }

    private void setPiechart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText(presenter.generateCenterSpannableText());
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
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                //// TODO: 5/13/2017
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSpinnerMenu() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adapter);
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) startActivity(ExpensesDetailsActivity.class);

                spinnerMode.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        return new CursorLoader(
                this,
                ExpensesProvider.getExpensesPath(),
                Expenses.DEFAULT_PROJECTION,
                "CATEGORY_ID = " + String.valueOf(id),
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
        }
    }
}
