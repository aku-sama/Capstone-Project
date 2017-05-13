package raspopova.diana.exptracker.ui.addExpenses;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class CategoryViewModel implements RestorableViewState <ICategoryView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<ICategoryView> restoreInstanceState(Bundle in) {
        return null;
    }

    @Override
    public void apply(ICategoryView view, boolean retained) {

    }
}
