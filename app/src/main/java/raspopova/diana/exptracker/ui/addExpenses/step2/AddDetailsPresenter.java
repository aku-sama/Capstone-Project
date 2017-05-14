package raspopova.diana.exptracker.ui.addExpenses.step2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Calendar;
import java.util.Date;

import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.app.ExpApplication;
import raspopova.diana.exptracker.utils.Utils;


/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class AddDetailsPresenter extends MvpBasePresenter<IAddDetailsView> {
    public final int REQ_CODE_IMAGE_FROM_CAMERA = 2320;

    private int categoryId;
    private long timestamp;
    private Uri imageUri;
    private String pathToPhoto = "";

    AddDetailsPresenter(int categoryId) {
        this.categoryId = categoryId;
        Calendar cal = Calendar.getInstance();
        timestamp = cal.getTime().getTime();
    }

    void savePurchase(String amount, String description) {
        if (validate(amount, description)) {

        }
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
