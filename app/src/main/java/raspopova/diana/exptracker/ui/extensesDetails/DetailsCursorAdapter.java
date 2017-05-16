package raspopova.diana.exptracker.ui.extensesDetails;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.BundleConfig;
import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.ui.photoView.PhotoViewActivity;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.CursorRecyclerViewAdapter;
import raspopova.diana.exptracker.utils.Utils;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class DetailsCursorAdapter extends CursorRecyclerViewAdapter<DetailsCursorAdapter.ViewHolder> {

    private Context context;

    public DetailsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {
        final Expenses purchase = Expenses.fromCursor(cursor);
        viewHolder.descriptionText.setText(purchase.getDescription());
        viewHolder.categoryImage.setImageResource(CategoryHelper.getDarkImageForCategory(purchase.getCategoryId()));
        viewHolder.amountText.setText(Config.amount.format(purchase.getAmount()) + Utils.getCurrency());
        viewHolder.dateText.setText(Config.DATE_FORMAT_OUTPUT.format(purchase.getPurchaseDate()));

        if (purchase.getAttachment().isEmpty()) {
            viewHolder.photoImage.setVisibility(View.GONE);
        } else {
            viewHolder.photoImage.setVisibility(View.VISIBLE);
            viewHolder.rootRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoViewActivity.class);
                    intent.putExtra(BundleConfig.IMAGE, purchase.getAttachment());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_purchase_details, parent, false);

        return new ViewHolder(itemView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryImage)
        ImageView categoryImage;
        @BindView(R.id.photoImage)
        ImageView photoImage;

        @BindView(R.id.amountText)
        TextView amountText;
        @BindView(R.id.dateText)
        TextView dateText;
        @BindView(R.id.descriptionText)
        TextView descriptionText;

        @BindView(R.id.rootRelative)
        RelativeLayout rootRelative;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
