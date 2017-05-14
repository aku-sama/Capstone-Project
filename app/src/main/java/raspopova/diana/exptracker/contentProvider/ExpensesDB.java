package raspopova.diana.exptracker.contentProvider;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

@Database(version = ExpensesDB.VERSION)
public class ExpensesDB {

    private ExpensesDB() {
    }

    public static final int VERSION = 1;

    @Table(ExpensesColumns.class)
    public static final String EXPENSES = "expenses";
}
