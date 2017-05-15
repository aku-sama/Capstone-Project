package raspopova.diana.exptracker.app;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import raspopova.diana.exptracker.contentProvider.Expenses;
import raspopova.diana.exptracker.utils.CategoryHelper;
import raspopova.diana.exptracker.utils.Utils;

/**
 * Created by Diana.Raspopova on 5/15/2017.
 */

public class PdfCreateAsyncTask extends AsyncTask<List<Expenses>, Void, File> {
    private String filename;
    private Callback callback;

    public PdfCreateAsyncTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected File doInBackground(List<Expenses>... params) {

        List<Expenses> summary = new ArrayList<>(params[0]);

        Calendar cal = Calendar.getInstance();
        filename = "ExpTracker_Report_" + String.valueOf(cal.getTime().getTime()) + ".pdf";

        Document document = new Document();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ExpTracker");
        myDir.mkdirs();
        File file = new File(myDir, filename);
        if (file.exists()) file.delete();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.open();

        try {
            addContent(document, summary);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        return file;
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        callback.onFileCreated(file);
    }

    public interface Callback {
        void onFileCreated(File file);
    }


    private static void addContent(Document document, List<Expenses> summary) throws DocumentException {

        PdfPTable table = createTable(summary);
        document.add(table);
    }

    private static PdfPTable createTable(List<Expenses> summary)
            throws BadElementException {

        // number, date ,category,description,sum
        float[] columnWidths = {0.2f, 0.7f, 1, 1.5f, 1};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        table.addCell(getSubtitleCell("ExpTracker app"));
        table.addCell(getEmptyCell(5));
        table.addCell(getTitleCell());
        table.addCell(getContentCell("#"));
        table.addCell(getContentCell("Date"));
        table.addCell(getContentCell("Category"));
        table.addCell(getContentCell("Description"));
        table.addCell(getContentCell("Amount"));

        table.setHeaderRows(1);

        int i = 1;
        for (Expenses item : summary) {

            table.addCell(getContentCell(String.valueOf(i)));
            table.addCell(getContentCell(Config.DATE_FORMAT_OUTPUT.format(item.getPurchaseDate())));
            table.addCell(getContentCell(CategoryHelper.getNameForCategory(item.getCategoryId())));
            table.addCell(getContentCell(item.getDescription()));
            table.addCell(getContentCell(Config.amount.format(item.getAmount()) + Utils.getCurrency()));
            i++;
        }

        return table;

    }

    private static PdfPCell getSubtitleCell(String text) {
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL, GrayColor.GRAY);
        PdfPCell cell = new PdfPCell(new Phrase(text, f));
        cell.setBackgroundColor(GrayColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(5);
        cell.setBorder(0);
        return cell;
    }

    @NonNull
    private static PdfPCell getEmptyCell(int colSpan) {
        PdfPCell cell = new PdfPCell(new Phrase(""));
        cell.setBackgroundColor(GrayColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cell.setMinimumHeight(50);
        cell.setColspan(colSpan);
        return cell;
    }


    @NonNull
    private static PdfPCell getTitleCell() {
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.NORMAL, GrayColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase("Expenditure report", f));
        cell.setBackgroundColor(GrayColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setMinimumHeight(50);
        cell.setColspan(5);
        cell.setBorder(0);
        return cell;
    }

   private static PdfPCell getContentCell(String text) {
        PdfPCell c1;
        c1 = new PdfPCell(new Phrase(text));
        c1.setMinimumHeight(20);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(GrayColor.WHITE);
        return c1;
    }
}
