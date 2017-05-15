package raspopova.diana.exptracker.ui.chart;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.contentProvider.SummaryObject;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.CursorRecyclerViewAdapter;
import raspopova.diana.exptracker.utils.Utils;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    private List<SummaryObject> list;

    public SummaryAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<SummaryObject> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenses_main, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SummaryObject myListItem = list.get(position);
        holder.categoryText.setText(myListItem.getCategoryName());
        holder.categoryLogo.setImageResource(myListItem.getCategoryLogo());
        holder.amountText.setText(Config.amount.format(myListItem.getAmount()) + Utils.getCurrency());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
