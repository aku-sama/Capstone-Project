package raspopova.diana.exptracker.ui.pin.pinSet;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class PinSetViewState implements RestorableViewState<IPinSetView> {

    private static final String PIN = "pin";
    private String pin;


    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(PIN, pin);
    }

    @Override
    public RestorableViewState<IPinSetView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        pin = in.getString(PIN);
        return this;
    }

    @Override
    public void apply(IPinSetView view, boolean retained) {
        view.fillView(pin);
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
