package raspopova.diana.exptracker.contentProvider;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class SummaryObject {

    int categoryId;
    String categoryName;
    int categoryLogo;
    double amount;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryLogo() {
        return categoryLogo;
    }

    public void setCategoryLogo(int categoryLogo) {
        this.categoryLogo = categoryLogo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
