package raspopova.diana.exptracker.ui.addExpenses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.utils.SpacesItemDecoration;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class AddExpensesCategoryActivity extends GeneralActivity<ICategoryView, CategoryPresenter, CategoryViewModel> implements ICategoryView {

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

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        categoryList.setLayoutManager(sglm);


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.gap_8);
        categoryList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        CategoryAdapter adapter = new CategoryAdapter(this);
        categoryList.setAdapter(adapter);
    }

    @NonNull
    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter();
    }

    @NonNull
    @Override
    public CategoryViewModel createViewState() {
        return new CategoryViewModel();
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
