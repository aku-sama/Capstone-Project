package raspopova.diana.exptracker.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import java.io.File;

import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.app.ExpApplication;

/**
 * Created by Diana.Raspopova on 5/14/2017.
 */

public class Utils {

    public static File getTempFile(Context context) {
        final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, getTimestampFileName());
    }

    public static String getTimestampFileName() {
        long timestamp = System.currentTimeMillis();
        return "image_" + String.valueOf(timestamp) + ".jpeg";
    }


    public static String getRealPathFromURI(Context mContext, Uri contentURI) {
        String result;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }


    public static String getFileName(Context mContext, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static String getCurrency() {
        return " " + ExpApplication.getInstance().getResources().getString(R.string.currency);
    }
}
