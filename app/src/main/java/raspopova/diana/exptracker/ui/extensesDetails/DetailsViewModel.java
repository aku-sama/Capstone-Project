package raspopova.diana.exptracker.ui.extensesDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class DetailsViewModel implements RestorableViewState<IDetailsView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<IDetailsView> restoreInstanceState(Bundle in) {
        return null;
    }

    @Override
    public void apply(IDetailsView view, boolean retained) {

    }
}
