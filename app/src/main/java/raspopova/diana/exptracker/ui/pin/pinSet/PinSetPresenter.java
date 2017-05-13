package raspopova.diana.exptracker.ui.pin.pinSet;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import raspopova.diana.exptracker.app.Config;


/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

class PinSetPresenter extends MvpBasePresenter<IPinSetView> {

    private String pinCode = "";

    public PinSetPresenter() {
        pinCode = "";
    }

    void onSymbolAdd(String symbol) {
        pinCode = pinCode + symbol;
        setPinState();
    }

    void  onRestorePinState(){
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
                getView().showProgress();
                getView().showFullPin();
                setPin(pinCode);
                break;
        }
    }

    void onReset() {
        pinCode = "";
        getView().showEmptyPin();
    }

    private void setPin(String pinCode) {
        Config.setPin(pinCode);
        getView().hideProgress();
        getView().showPinSuccessDialog();
    }

    public String getPin() {
        return pinCode;
    }
}
