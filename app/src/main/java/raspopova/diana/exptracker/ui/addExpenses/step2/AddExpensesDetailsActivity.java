package raspopova.diana.exptracker.ui.addExpenses.step2;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.BundleConfig;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.ui.chart.ChartActivity;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.TextInputHelper;
import raspopova.diana.exptracker.utils.Utils;
import raspopova.diana.exptracker.views.DatePickerFragment;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class AddExpensesDetailsActivity extends GeneralActivity<IAddDetailsView, AddDetailsPresenter, AddDetailsViewState>
        implements IAddDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonDate)
    TextView buttonDate;

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
        presenter.setDateValue();
        presenter.setCategoryId(categoryId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewState.setAmount(TextInputHelper.getValue(amountInput));
        viewState.setDate(buttonDate.getText().toString());
        viewState.setDescription(TextInputHelper.getValue(descriptionInput));

    }

    @NonNull
    @Override
    public AddDetailsPresenter createPresenter() {
        return new AddDetailsPresenter();
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
        buttonDate.setText(date);
        TextInputHelper.setValue(amountInput, amount);
        TextInputHelper.setValue(descriptionInput, description);
    }

    @Override
    public void showAmountValidationError() {
        TextInputHelper.showError(amountInput, R.string.purchase_amount_error);
    }

    @Override
    public void showDescriptionValidationError() {
        TextInputHelper.showError(descriptionInput, R.string.purchase_description_error);

    }

    @Override
    public void setDateValue(String date) {
        buttonDate.setText(date);
    }

    @Override
    public void addFileToList(String fileName) {
        View layoutAttachment = LayoutInflater.from(this).inflate(R.layout.item_attachment, attachmentList, false);

        TextView textView = (TextView) layoutAttachment.findViewById(R.id.attacheTitle);
        ImageView imageView = (ImageView) layoutAttachment.findViewById(R.id.deleteAttache);

        textView.setText(fileName);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAttache();
                attachmentList.removeAllViews();
            }
        });

        attachmentList.addView(layoutAttachment);
    }

    @Override
    public void attachmentDisable() {
        attacheButton.setTextColor(getResources().getColor(R.color.colorAccent));
        attacheButton.setEnabled(false);
    }

    @Override
    public void attacheEnable() {
        attacheButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        attacheButton.setEnabled(true);
    }

    @Override
    public void restoreAttachment() {
        presenter.restoreAttachment();
    }

    @Override
    public void onAddSuccess() {
        startActivity(ChartActivity.class, true);
    }

    @OnClick(R.id.buttonDate)
    void onDateSelect() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Date");

        ((DatePickerFragment) newFragment).setCallback(new DatePickerFragment.Callback() {
            @Override
            public void onDateSet(int year, int month, int day) {
                presenter.onDateSet(year, month, day);
            }
        });
    }

    @OnClick(R.id.attacheButton)
    void onPhotoAttache() {
        if (isStoragePermissionGranted()) {
            callCameraIntent();
        }
    }


    @OnClick(R.id.addButton)
    void onPurchaseAdd() {
        presenter.savePurchase(TextInputHelper.getValue(amountInput), TextInputHelper.getValue(descriptionInput));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            presenter.onPhotoGetResult(requestCode, resultCode, data);
        }

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
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            callCameraIntent();
        }
    }

    private void callCameraIntent() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, presenter.getImageUri());
        startActivityForResult(intent, presenter.REQ_CODE_IMAGE_FROM_CAMERA);
    }
}
