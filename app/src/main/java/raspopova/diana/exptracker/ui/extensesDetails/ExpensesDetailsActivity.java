package raspopova.diana.exptracker.ui.extensesDetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.app.PdfCreateAsyncTask;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.contentProvider.ExpensesProvider;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class ExpensesDetailsActivity extends GeneralActivity<IDetailsView, DetailsPresenter, DetailsViewModel>
        implements IDetailsView, LoaderManager.LoaderCallbacks, DatePickerDialog.OnDateSetListener {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    @BindView(R.id.expensesList)
    RecyclerView expensesList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.emptyListText)
    TextView emptyListText;

    private DetailsCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_details);
        ButterKnife.bind(this);
        setToolbarBackButton(toolbar);
        setRecycler();
        presenter.initializeDefaultPeriodData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewState.setToolbarTitle(getSupportActionBar().getTitle().toString());
    }

    @Override
    public void initLoaders(long startDate, long endDate, boolean isFirstStart) {
        Bundle args = new Bundle();
        args.putLong(START_DATE, startDate);
        args.putLong(END_DATE, endDate);
        if (isFirstStart) {
            getSupportLoaderManager().initLoader(0, args, this);
        } else {
            getSupportLoaderManager().restartLoader(0, args, this);
        }
    }

    @Override
    public void setToolbarTitle(String text) {
        getSupportActionBar().setTitle(text);
    }

    @Override
    public void showEmptyList() {
        emptyListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyList() {
        emptyListText.setVisibility(View.GONE);
    }

    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        expensesList.setLayoutManager(layoutManager);

        mAdapter = new DetailsCursorAdapter(this, null);
        expensesList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showDateFilter();
                return true;
            case R.id.action_download:
                if (isStoragePermissionGranted()) {
                    downloadPdfReport();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downloadPdfReport() {
        if (presenter.getExpenses().size() > 0) {
            showProgress();
            PdfCreateAsyncTask task = new PdfCreateAsyncTask(new PdfCreateAsyncTask.Callback() {
                @Override
                public void onFileCreated(File file) {
                    hideProgress();
                    showPdfVewIntent(file);
                }
            });
            task.execute(presenter.getExpenses());
        } else
            showErrorSnackBar(getString(R.string.empty_report_alert));
    }

    private void showPdfVewIntent(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (Exception e) {
            showErrorSnackBar(e.getLocalizedMessage());
        }
    }

    private void showDateFilter() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ExpensesDetailsActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadPdfReport();
        }
    }


    @NonNull
    @Override
    public DetailsPresenter createPresenter() {
        return new DetailsPresenter();
    }

    @NonNull
    @Override
    public DetailsViewModel createViewState() {
        return new DetailsViewModel();
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

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        long startDate = args.getLong(START_DATE);
        long endDate = args.getLong(END_DATE);
        return new CursorLoader(
                this,
                ExpensesProvider.getExpensesPath(),
                Expenses.DEFAULT_PROJECTION,
                "(OWNER_ID  = '" + Config.getAuthorizationToken() + "') AND " +
                        "(PURCHASE_DATE >= " + startDate +
                        ") AND (PURCHASE_DATE < " + endDate + ")",
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        presenter.getDataFromCursor((Cursor) data);
        mAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        presenter.updatePurchaseDate(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd);

    }
}
