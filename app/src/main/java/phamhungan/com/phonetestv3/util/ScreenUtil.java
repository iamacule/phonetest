package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.activity.AboutActivity;

/**
 * Created by MrAn PC on 19-Jan-16.
 */
public class ScreenUtil {
    private static DisplayMetrics dm = new DisplayMetrics();

    public static Point getScreenDimentions(WindowManager windowManager){
        Point point = new Point();
        int width = 0, height = 0;
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;

        try {
            // For JellyBean 4.2 (API 17) and onward
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);

                width = metrics.widthPixels;
                height = metrics.heightPixels;
            } else {
                mGetRawH = Display.class.getMethod("getRawHeight");
                mGetRawW = Display.class.getMethod("getRawWidth");

                try {
                    width = (Integer) mGetRawW.invoke(display);
                    height = (Integer) mGetRawH.invoke(display);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        }
        point.set(width,height);
        return point;
    }

    //Get Screen width
    public static float getScreenWidth(WindowManager windowManager){
        return getScreenDimentions(windowManager).x;
    }

    //Get screen height
    public static float getScreenHeight(WindowManager windowManager){
        return getScreenDimentions(windowManager).y;
    }

    /*
     * Set Fragment for Activities
     */
    public static void goAbout(Activity activity){
        Intent intent = new Intent(activity,AboutActivity.class);
        activity.startActivity(intent);
    }

    /**
     * show FaceBook share dialog
     */
    public static void showSharedialog(Activity context){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog shareDialog = new ShareDialog(context);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentDescription(Html.fromHtml(context.getResources().getString(R.string.content)).toString())
                    .setContentUrl(Uri.parse(context.getResources().getString(R.string.url)))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    public static AnimationDrawable createAnimationDrawable(Bitmap[] list,Context context){
        AnimationDrawable newAnim = new AnimationDrawable();
        newAnim.setOneShot(false);
        for (Bitmap bp : list){
            Drawable drawable = new BitmapDrawable(context.getResources(),bp);
            newAnim.addFrame(drawable, 100);
        }
        return newAnim;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return (int)dp;
    }
}
