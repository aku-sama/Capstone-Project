package raspopova.diana.exptracker.contentProvider;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class Expenses implements BaseColumns {

    private Integer categoryId;
    private Double amount;
    private String description;
    private String ownerId;
    private Long purchaseDate;
    private String attachment;

    public Expenses() {
    }

    public Expenses(long id,
                    double amount,
                    String attachment,
                    int category_id,
                    String description,
                    long purchaseDate,
                    String owner) {
        this.amount = amount;
        this.attachment = attachment;
        this.categoryId = category_id;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.ownerId = owner;
    }


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public static Expenses fromCursor(Cursor cursor) {

        Expenses expenses = new Expenses(
                cursor.getLong(cursor.getColumnIndex(ExpensesColumns._ID)),
                cursor.getDouble(cursor.getColumnIndex(ExpensesColumns.AMOUNT)),
                cursor.getString(cursor.getColumnIndex(ExpensesColumns.ATTACHMENT)),
                cursor.getInt(cursor.getColumnIndex(ExpensesColumns.CATEGORY_ID)),
                cursor.getString(cursor.getColumnIndex(ExpensesColumns.DESCRIPTION)),
                cursor.getLong(cursor.getColumnIndex(ExpensesColumns.PURCHASE_DATE)),
                cursor.getString(cursor.getColumnIndex(ExpensesColumns.OWNER_ID)));

        return expenses;


    }

    public static final String[] DEFAULT_PROJECTION = new String[]{
            ExpensesColumns._ID,
            ExpensesColumns.AMOUNT,
            ExpensesColumns.ATTACHMENT,
            ExpensesColumns.CATEGORY_ID,
            ExpensesColumns.DESCRIPTION,
            ExpensesColumns.PURCHASE_DATE,
            ExpensesColumns.OWNER_ID
    };
}
