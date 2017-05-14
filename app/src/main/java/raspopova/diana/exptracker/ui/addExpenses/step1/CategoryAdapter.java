package raspopova.diana.exptracker.ui.addExpenses.step1;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.BundleConfig;
import raspopova.diana.exptracker.ui.addExpenses.step2.AddExpensesDetailsActivity;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private static LayoutInflater inflater = null;
    private TypedArray imgs;
    private String[] names;
    private int[] ids;

    CategoryAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgs = context.getResources().obtainTypedArray(R.array.category_img_array);
        names = context.getResources().getStringArray(R.array.category_array);
        ids = context.getResources().getIntArray(R.array.category_id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = inflater.inflate(R.layout.item_category, null);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.categoryText.setText(names[position]);
        holder.categoryText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, imgs.getResourceId(position, -1), 0, 0);

        holder.rootCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddExpensesDetailsActivity.class);
                intent.putExtra(BundleConfig.CATEGORY_PURCHASE, ids[holder.getAdapterPosition()]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryText)
        TextView categoryText;

        @BindView(R.id.rootCard)
        CardView rootCard;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
