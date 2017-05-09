package raspopova.diana.exptracker.contentProvider;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class ExpensesTable {

    // table name
    public static final String TABLE_NAME = "expenses";

    // columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_OWNER_ID = "owner_id";
    public static final String COLUMN_PURCHASE_DATE_STAMP = "purchase_date";
    public static final String COLUMN_ATTACHMENT = "attachment";


    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY_ID + " INTEGER, "
            + COLUMN_AMOUNT + " REAL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_OWNER_ID + " TEXT, "
            + COLUMN_PURCHASE_DATE_STAMP + " INTEGER, "
            + COLUMN_ATTACHMENT + " BLOB "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

    public static String[] getColumns() {
        String[] columns = new String[] {
                COLUMN_ID,
                COLUMN_CATEGORY_ID,
                COLUMN_AMOUNT,
                COLUMN_DESCRIPTION,
                COLUMN_OWNER_ID,
                COLUMN_PURCHASE_DATE_STAMP,
                COLUMN_ATTACHMENT
        };
        return columns;
    }
}
