package raspopova.diana.exptracker.ui.extensesDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class DetailsViewModel implements RestorableViewState<IDetailsView> {

    public static final String TITLE = "title";
    private String toolbarTitle;

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(TITLE, toolbarTitle);
    }

    @Override
    public RestorableViewState<IDetailsView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        toolbarTitle = in.getString(TITLE);
        return this;
    }

    @Override
    public void apply(IDetailsView view, boolean retained) {
        view.setToolbarTitle(toolbarTitle);
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }
}
