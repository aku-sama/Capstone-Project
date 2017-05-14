package raspopova.diana.exptracker.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;

import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.ExpApplication;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class CategoryHelper {

    private static final int DEFAULT_CATEGORY = 4; //other category position

    public static int getDefaultCategory() {
        Resources res = ExpApplication.getInstance().getResources();
        int[] ids = res.getIntArray(R.array.category_id);
        return ids[DEFAULT_CATEGORY];
    }

    public static int getImageForCategory(int category) {

        Resources res = ExpApplication.getInstance().getResources();
        TypedArray imgs = res.obtainTypedArray(R.array.category_img_array);

        if (category >= imgs.length()) category = DEFAULT_CATEGORY;

        int resId = imgs.getResourceId(category, -1);
        imgs.recycle();
        return resId;
    }

    public static String getNameForCategory(int category) {
        Resources res = ExpApplication.getInstance().getResources();
        String[] names = res.getStringArray(R.array.category_array);

        return category < names.length ? names[category] : names[DEFAULT_CATEGORY];
    }

}
