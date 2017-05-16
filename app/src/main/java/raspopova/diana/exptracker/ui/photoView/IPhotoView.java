package raspopova.diana.exptracker.ui.photoView;

import java.io.File;

import raspopova.diana.exptracker.base.IGeneralView;

/**
 * Created by Diana.Raspopova on 5/16/2017.
 */

public interface IPhotoView extends IGeneralView {
    void showPhoto(File file);

    void restoreState(String path);
}
