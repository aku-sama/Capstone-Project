package raspopova.diana.exptracker.ui.chart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.ui.addExpenses.step1.AddExpensesCategoryActivity;
import raspopova.diana.exptracker.ui.extensesDetails.ExpensesDetailsActivity;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ChartActivity extends GeneralActivity<IChartView, ChartPresenter, ChartViewState>
        implements IChartView {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSpinnerMenu();

        setRecycler();

    }

    private void setRecycler() {
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


}
