package raspopova.diana.exptracker.ui.photoView;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.io.File;

/**
 * Created by Diana.Raspopova on 5/16/2017.
 */

public class PhotoPresenter extends MvpBasePresenter<IPhotoView> {

    private String path;

    public void setPath(String path) {
        this.path = path;
        getFileFromSource();
    }

    private void getFileFromSource(){
        File file = new File(path);
        getView().showPhoto(file);
    }
}
