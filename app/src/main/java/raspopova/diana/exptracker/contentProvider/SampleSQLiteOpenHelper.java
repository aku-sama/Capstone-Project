package raspopova.diana.exptracker.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class SampleSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expTracker.db";
    private static final int DATABASE_VERSION = 1;

    public SampleSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ExpensesTable.onCreate(db);
        //addSampleRows(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

 /*   private void addSampleRows(SQLiteDatabase db) {
        for (int i = 1; i <= 5; i++) {
            String query = String.format(Locale.US, "insert into posts (title) values ('Sample Post %d')", i);
            db.execSQL(query);
        }
    }*/
}
