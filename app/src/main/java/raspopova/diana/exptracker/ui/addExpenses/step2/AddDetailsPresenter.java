package raspopova.diana.exptracker.ui.addExpenses.step2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Calendar;
import java.util.Date;

import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.app.ExpApplication;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.contentProvider.ExpensesColumns;
import raspopova.diana.exptracker.contentProvider.ExpensesProvider;
import raspopova.diana.exptracker.utils.Utils;


/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class AddDetailsPresenter extends MvpBasePresenter<IAddDetailsView> {
    private static final String LOG_TAG = "expenses_dp";
    public final int REQ_CODE_IMAGE_FROM_CAMERA = 2320;

    private int categoryId;
    private long timestamp;
    private Uri imageUri;
    private String pathToPhoto = "";
    private Context context;

    AddDetailsPresenter(Context context) {
        this.context = context;
        Calendar cal = Calendar.getInstance();
        timestamp = cal.getTime().getTime();
    }

    void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    void savePurchase(String amount, String description) {
        if (validate(amount, description)) {
            Expenses expenses = new Expenses();
            expenses.setOwnerId(Config.getAuthorizationToken());
            expenses.setDescription(description);
            expenses.setAmount(Double.valueOf(amount));
            expenses.setPurchaseDate(timestamp);
            expenses.setCategoryId(categoryId);
            expenses.setAttachment(pathToPhoto);

            getView().showProgress();
            insertToExpenses(expenses);

        }
    }

    private void insertToExpenses(Expenses expenses) {
        Log.d(LOG_TAG, "insert");
        ContentValues cv = new ContentValues();
        cv.put(ExpensesColumns.AMOUNT, expenses.getAmount());
        cv.put(ExpensesColumns.ATTACHMENT, expenses.getAttachment());
        cv.put(ExpensesColumns.CATEGORY_ID, expenses.getCategoryId());
        cv.put(ExpensesColumns.DESCRIPTION, expenses.getDescription());
        cv.put(ExpensesColumns.OWNER_ID, expenses.getOwnerId());
        cv.put(ExpensesColumns.PURCHASE_DATE, expenses.getPurchaseDate());

        ExpApplication.getInstance().getContentResolver().delete(ExpensesProvider.Expenses.withTimestamp(expenses.getPurchaseDate()),
                null, null);
        ExpApplication.getInstance().getContentResolver().insert(ExpensesProvider.Expenses.withTimestamp(expenses.getPurchaseDate()), cv);

        Intent dataUpdatedIntent = new Intent(ExpensesProvider.ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);

        getView().hideProgress();
        getView().onAddSuccess(expenses);

    }

    private boolean validate(String amount, String description) {
        if (amount.isEmpty()) {
            getView().showAmountValidationError();
            return false;
        } else if (description.isEmpty()) {
            getView().showDescriptionValidationError();
            return false;
        }

        try {
            Double.valueOf(amount);
        } catch (Exception ignore) {
            getView().showAmountValidationError();
            return false;
        }

        return true;

    }

    void onDateSet(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        timestamp = cal.getTime().getTime();
        setDateValue();
    }


    void setDateValue() {
        Date d = new Date(timestamp);
        String date = Config.DATE_FORMAT_OUTPUT.format(d);
        getView().setDateValue(date);
    }

    void onPhotoGetResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQ_CODE_IMAGE_FROM_CAMERA) {
                pathToPhoto = Utils.getRealPathFromURI(ExpApplication.getInstance(), imageUri);
                getView().addFileToList(Utils.getFileName(ExpApplication.getInstance(), imageUri));
                getView().attachmentDisable();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Uri getImageUri() {
        imageUri = Uri.fromFile(Utils.getTempFile(ExpApplication.getInstance()));
        return imageUri;
    }

    void deleteAttache() {
        getView().attacheEnable();
        pathToPhoto = "";
        imageUri = null;
    }

    void restoreAttachment() {
        if (!pathToPhoto.isEmpty()) {
            getView().addFileToList(Utils.getFileName(ExpApplication.getInstance(), imageUri));
            getView().attachmentDisable();
        }
    }
}
