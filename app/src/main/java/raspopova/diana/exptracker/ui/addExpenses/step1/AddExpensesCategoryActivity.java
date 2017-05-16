package raspopova.diana.exptracker.ui.addExpenses.step1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.base.IGeneralView;
import raspopova.diana.exptracker.utils.SpacesItemDecoration;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class AddExpensesCategoryActivity extends GeneralActivity<IGeneralView, MvpBasePresenter<IGeneralView>,
        RestorableViewState<IGeneralView>> implements IGeneralView {

    @BindView(R.id.categoryList)
    RecyclerView categoryList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase_category);
        ButterKnife.bind(this);

        setToolbarBackButton(toolbar);
        setupRecycler();
    }

    private void setupRecycler() {

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.grid_column_count),
                StaggeredGridLayoutManager.VERTICAL);
        categoryList.setLayoutManager(sglm);


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.gap_4);
        categoryList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        CategoryAdapter adapter = new CategoryAdapter(this);
        categoryList.setAdapter(adapter);
    }

    @NonNull
    @Override
    public MvpBasePresenter<IGeneralView> createPresenter() {
        return new MvpBasePresenter<>();
    }

    @NonNull
    @Override
    public RestorableViewState<IGeneralView> createViewState() {
        return new RestorableViewState<IGeneralView>() {
            @Override
            public void saveInstanceState(@NonNull Bundle out) {

            }

            @Override
            public RestorableViewState<IGeneralView> restoreInstanceState(Bundle in) {
                return this;
            }

            @Override
            public void apply(IGeneralView view, boolean retained) {

            }
        };
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
}
