package it.arisetech.app.arish;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class Utils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private Context _context;

    // constructor
    public Utils(Context context) {
        this._context = context;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }
}
//    public static int getScreenWidth(Context c) {
//        if (screenWidth == 0) {
//            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            screenWidth = size.x;
//        }
//
//        return screenWidth;
//    }
//
//    public static boolean isAndroid5() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
//    }
//
//    public int getScreenWidth() {
//        int columnWidth;
//        WindowManager wm = (WindowManager) _context
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        final Point point = new Point();
//        try {
//            display.getSize(point);
//        } catch (java.lang.NoSuchMethodError ignore) { // Older device
//            point.x = display.getWidth();
//            point.y = display.getHeight();
//        }
//        columnWidth = point.x;
//        return columnWidth;
//    }
//}
