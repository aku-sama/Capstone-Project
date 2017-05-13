package raspopova.diana.exptracker.ui.pin.pinEdit;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

import raspopova.diana.exptracker.ui.pin.pinSet.IPinSetView;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class PinEditViewState implements RestorableViewState<IPinEditView> {

    private static final String PIN = "pin";
    private String pin;


    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(PIN, pin);
    }

    @Override
    public RestorableViewState<IPinEditView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        pin = in.getString(PIN);
        return this;
    }

    @Override
    public void apply(IPinEditView view, boolean retained) {
        view.fillView(pin);
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
