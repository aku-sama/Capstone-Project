package raspopova.diana.exptracker.contentProvider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public interface ExpensesColumns {

    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    @NotNull
    String _ID = "_id";

    @DataType(INTEGER)
    @NotNull
    String CATEGORY_ID = "category_id";

    @DataType(REAL)
    @NotNull
    String AMOUNT = "amount";

    @DataType(TEXT)
    @NotNull
    String DESCRIPTION = "description";

    @DataType(TEXT)
    @NotNull
    String OWNER_ID = "owner_id";

    @DataType(INTEGER)
    @NotNull
    String PURCHASE_DATE = "purchase_date";

    @DataType(TEXT)
    @NotNull
    String ATTACHMENT = "attachment";

}
