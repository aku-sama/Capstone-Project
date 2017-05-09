package raspopova.diana.exptracker.contentProvider;

import android.net.Uri;

import me.everything.providers.core.Entity;
import me.everything.providers.core.FieldMapping;
import me.everything.providers.core.IgnoreMapping;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class Expenses extends Entity {
    @IgnoreMapping
    public static Uri uri = ExpensesContentProvider.EXPENSES_URI;

    @FieldMapping(columnName = ExpensesTable.COLUMN_ID, physicalType = FieldMapping.PhysicalType.Int)
    public Integer id;

    @FieldMapping(columnName = ExpensesTable.COLUMN_CATEGORY_ID, physicalType = FieldMapping.PhysicalType.Int)
    public Integer categoryId;

    @FieldMapping(columnName = ExpensesTable.COLUMN_AMOUNT, physicalType = FieldMapping.PhysicalType.Double)
    public Double amount;

    @FieldMapping(columnName = ExpensesTable.COLUMN_DESCRIPTION, physicalType = FieldMapping.PhysicalType.String )
    public String description;

    @FieldMapping(columnName = ExpensesTable.COLUMN_OWNER_ID, physicalType = FieldMapping.PhysicalType.String)
    public String ownerId;

    @FieldMapping(columnName = ExpensesTable.COLUMN_PURCHASE_DATE_STAMP, physicalType = FieldMapping.PhysicalType.Long, logicalType = FieldMapping.LogicalType.Long)
    public Long purchaseDate;

    @FieldMapping(columnName = ExpensesTable.COLUMN_ATTACHMENT, physicalType = FieldMapping.PhysicalType.Blob)
    public byte[] attachment;


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_OWNER_ID = "owner_id";
    public static final String COLUMN_PURCHASE_DATE = "purchase_date";
    public static final String COLUMN_ATTACHMENT = "attachment";

}
