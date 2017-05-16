package raspopova.diana.exptracker.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Calendar;

import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.contentProvider.ExpensesColumns;
import raspopova.diana.exptracker.contentProvider.ExpensesProvider;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.Utils;

/**
 * Created by Diana.Raspopova on 5/16/2017.
 */

public class WidgetRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                Calendar cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 1);
                long dateStart = cal.getTime().getTime();

                if (cal.get(Calendar.MONTH) < 12)
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 1);
                else
                    cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH) - 11, 1, 0, 0, 1);

                long dateEnd = cal.getTime().getTime();

                data = getContentResolver().query(ExpensesProvider.Expenses.CONTENT_URI,
                        Expenses.DEFAULT_PROJECTION,
                        "(OWNER_ID  = '" + Config.getAuthorizationToken() + "') AND " +
                                "(PURCHASE_DATE >= " + dateStart +
                                ") AND (PURCHASE_DATE < " + dateEnd + ")",
                        null,
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.item_widget_details);

                Expenses purchase = Expenses.fromCursor(data);

                views.setTextViewText(R.id.descriptionText, purchase.getDescription());
                views.setImageViewResource(R.id.categoryImage, CategoryHelper.getDarkImageForCategory(purchase.getCategoryId()));
                views.setTextViewText(R.id.amountText, Config.amount.format(purchase.getAmount()) + Utils.getCurrency());
                views.setTextViewText(R.id.dateText, Config.DATE_FORMAT_OUTPUT.format(purchase.getPurchaseDate()));


                final Intent fillInIntent = new Intent();

                Uri stockUri = ExpensesProvider.Expenses.withTimestamp(purchase.getPurchaseDate());
                fillInIntent.setData(stockUri);
                views.setOnClickFillInIntent(R.id.rootRelative, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.item_widget_details);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(data.getColumnIndex(ExpensesColumns.PURCHASE_DATE));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };

    }
}
