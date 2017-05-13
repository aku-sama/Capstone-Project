package raspopova.diana.exptracker.ui.addExpenses;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import raspopova.diana.exptracker.R;

/**
 * Created by Diana.Raspopova on 5/13/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    private TypedArray imgs;
    private String[] names;

    public CategoryAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgs = context.getResources().obtainTypedArray(R.array.category_img_array);
        names = context.getResources().getStringArray(R.array.category_array);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = inflater.inflate(R.layout.item_category, null);
        return new ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.categoryText.setText(names[position]);
        holder.categoryText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, imgs.getResourceId(position, -1), 0, 0);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.categoryText)
        TextView categoryText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
