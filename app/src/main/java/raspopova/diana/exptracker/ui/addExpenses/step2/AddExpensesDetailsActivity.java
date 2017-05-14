package raspopova.diana.exptracker.ui.addExpenses.step2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.BundleConfig;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.utils.CategoryHelper;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class AddExpensesDetailsActivity extends GeneralActivity<IAddDetailsView, AddDetailsPresenter, AddDetailsViewState>
        implements IAddDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonDate)
    Button buttonDate;

    @BindView(R.id.amountInput)
    TextInputLayout amountInput;
    @BindView(R.id.descriptionInput)
    TextInputLayout descriptionInput;

    @BindView(R.id.attachmentList)
    LinearLayout attachmentList;

    @BindView(R.id.attacheButton)
    Button attacheButton;
    @BindView(R.id.addButton)
    Button addButton;

    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase_data);
        ButterKnife.bind(this);

        setToolbarBackButton(toolbar);
        categoryId = getIntent().getIntExtra(BundleConfig.CATEGORY_PURCHASE, CategoryHelper.getDefaultCategory());
    }

    @NonNull
    @Override
    public AddDetailsPresenter createPresenter() {
        return new AddDetailsPresenter(categoryId);
    }

    @NonNull
    @Override
    public AddDetailsViewState createViewState() {
        return new AddDetailsViewState();
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
    public void fillView(String date, String amount, String description) {

    }

    @Override
    public void showAmountValidationError() {

    }

    @Override
    public void showDescriptionValidationError() {

    }

    @Override
    public void setDefaultDateValue() {

    }
}
