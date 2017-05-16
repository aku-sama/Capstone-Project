package raspopova.diana.exptracker.ui.photoView;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Diana.Raspopova on 5/16/2017.
 */

public class PhotoViewState implements RestorableViewState<IPhotoView> {

    private static final String PATH = "path";
    private String path;


    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putString(PATH, path);
    }

    @Override
    public RestorableViewState<IPhotoView> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        path = in.getString(PATH);
        return this;
    }

    @Override
    public void apply(IPhotoView view, boolean retained) {
        view.restoreState(path);
    }
}
