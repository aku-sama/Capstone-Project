package raspopova.diana.exptracker.ui.addExpenses.step2;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class AddDetailsPresenter extends MvpBasePresenter<IAddDetailsView> {

    private int categoryId;

    public AddDetailsPresenter(int categoryId) {
        this.categoryId = categoryId;
    }

    public void savePurchase(String date, String amount, String description) {
    }


    public void takePhoto() {
    }

}
