package raspopova.diana.exptracker.ui.pin.pinEdit;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class PinEditPresenter extends MvpBasePresenter<IPinEditView> {

    private String pinCode = "";

    public PinEditPresenter() {
        pinCode = "";
    }

    void onSymbolAdd(String symbol) {
        pinCode = pinCode + symbol;
        setPinState();
    }

    void onRestorePinState() {
        setPinState();
    }

    void onSymbolDelete() {
        if (pinCode.length() > 0) {
            pinCode = pinCode.substring(0, pinCode.length() - 1);
            setPinState();
        }
    }

    private void setPinState() {
        switch (pinCode.length()) {
            case 0:
                getView().showEmptyPin();
                break;
            case 1:
                getView().showOneItemPinSelected();
                break;
            case 2:
                getView().showTwoItemPinSelected();
                break;
            case 3:
                getView().showThreeItemPinSelected();
                break;
            case 4:
                getView().showFullPin();
                checkPin(pinCode);
                break;
        }
    }


    void onReset() {
        pinCode = "";
        getView().showEmptyPin();
    }

    private void checkPin(String pinCode) {
        String currentPass = Config.getPin();

        if (currentPass.equals(pinCode)) {
            getView().onPinSuccess();
        } else {
            getView().showError(R.string.enter_pin_error);
            getView().showEmptyPin();
            this.pinCode = "";
        }
    }

    public void pinForget() {
        getView().showProgress();
        Config.logout();
    }

    public String getPin() {
        return pinCode;
    }
}
