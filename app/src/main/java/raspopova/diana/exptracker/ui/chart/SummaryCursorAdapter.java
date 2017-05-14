package raspopova.diana.exptracker.ui.chart;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.CursorRecyclerViewAdapter;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class SummaryCursorAdapter extends CursorRecyclerViewAdapter<SummaryCursorAdapter.ViewHolder> {

    private Context context;

    public SummaryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        final Expenses myListItem = Expenses.fromCursor(cursor);
        viewHolder.categoryText.setText(CategoryHelper.getNameForCategory(myListItem.getCategoryId()));
        viewHolder.categoryLogo.setImageResource(CategoryHelper.getDarkImageForCategory(myListItem.getCategoryId()));
        viewHolder.amountText.setText(Config.amount.format(myListItem.getAmount()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenses_main, parent, false);

        return new ViewHolder(itemView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryLogo)
        ImageView categoryLogo;

        @BindView(R.id.categoryText)
        TextView categoryText;
        @BindView(R.id.amountText)
        TextView amountText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
