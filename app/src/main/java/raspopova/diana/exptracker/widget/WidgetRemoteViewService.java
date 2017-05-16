package raspopova.diana.exptracker.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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
                data = getContentResolver().query(ExpensesProvider.Expenses.CONTENT_URI,
                        null, null, null, null);

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
                if (purchase.getAttachment().isEmpty()) {
                    views.setViewVisibility(R.id.photoImage, View.GONE);
                } else {
                    views.setViewVisibility(R.id.photoImage, View.VISIBLE);
                }


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
                    return data.getLong(data.getColumnIndex(ExpensesColumns._ID));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };

    }
}
