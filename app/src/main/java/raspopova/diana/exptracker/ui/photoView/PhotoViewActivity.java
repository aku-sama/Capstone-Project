package raspopova.diana.exptracker.ui.photoView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.BundleConfig;
import raspopova.diana.exptracker.base.GeneralActivity;

/**
 * Created by Diana.Raspopova on 5/16/2017.
 */

public class PhotoViewActivity extends GeneralActivity<IPhotoView, PhotoPresenter, PhotoViewState> implements IPhotoView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.photoImage)
    PhotoView photoView;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        path = getIntent().getStringExtra(BundleConfig.IMAGE);
        ButterKnife.bind(this);

        setToolbarBackButton(toolbar);
        presenter.setPath(path);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @NonNull
    @Override
    public PhotoPresenter createPresenter() {
        return new PhotoPresenter();
    }

    @NonNull
    @Override
    public PhotoViewState createViewState() {
        return new PhotoViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public void showProgress() {
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void showError(String message, int... code) {
        showErrorSnackBar(message);
    }

    @Override
    public void showError(int message, int... code) {
        showErrorSnackBar(message);
    }

    @Override
    public void showPhoto(File file) {
        getSupportActionBar().setTitle(file.getName());
        Glide.with(this)
                .load(file)
                .into(photoView);
    }

    @Override
    public void restoreState(String path) {
        this.path = path;
        presenter.setPath(path);
    }
}
