package raspopova.diana.exptracker.contentProvider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

@ContentProvider(authority = ExpensesProvider.AUTHORITY, database = ExpensesDB.class)
public final class ExpensesProvider {

    public static final String ACTION_DATA_UPDATED = "raspopova.diana.exptracker.ACTION_DATA_UPDATED";
    public static final String AUTHORITY = "raspopova.diana.exptracker.contentProvider.ExpensesProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String EXPENSES = "expenses";
    }

    public static Uri getExpensesPath() {
        return buildUri(Path.EXPENSES);
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = ExpensesDB.EXPENSES)
    public static class Expenses {

        @ContentUri(
                path = Path.EXPENSES,
                type = "vnd.android.cursor.dir/expenses",
                defaultSort = ExpensesColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.EXPENSES);

        @InexactContentUri(
                name = "EXPENSES_PURCHASE_DATE",
                path = Path.EXPENSES + "/#",
                type = "vnd.android.cursor.item/expenses",
                whereColumn = ExpensesColumns.PURCHASE_DATE,
                pathSegment = 1)
        public static Uri withTimestamp(long timestamp) {
            return buildUri(Path.EXPENSES, String.valueOf(timestamp));
        }
    }
}
