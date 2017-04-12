package phamhungan.com.phonetestv3.util;

import android.app.Activity;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.model.Item;

/**
 * Created by MrAn PC on 27-Jan-16.
 */
public class DataUtil {
    public static int countToLoadAd = 0;
    public static boolean whichTest = false;
    public static int radius;
    public static final String PASSED = " PASSED";
    public static final String SKIPPED = " SKIPPED";
    public static final String FAILED = " FAILED";

    public static Item[] getListItem(Activity activity) {
        return new Item[]{
                new Item(R.drawable.butsound, activity.getResources().getString(R.string.sound_test)),
                new Item(R.drawable.butvibrate, activity.getResources().getString(R.string.vibrate_test)),
                new Item(R.drawable.butmicro, activity.getResources().getString(R.string.microphone_test)),
                new Item(R.drawable.butlcd, activity.getResources().getString(R.string.lcd_test)),
                new Item(R.drawable.butlbri, activity.getResources().getString(R.string.brightness_test)),
                new Item(R.drawable.buttouch, activity.getResources().getString(R.string.touch_test)),
                new Item(R.drawable.butmultitouch, activity.getResources().getString(R.string.multitouch_test)),
                new Item(R.drawable.butcamera, activity.getResources().getString(R.string.camera_test)),
                new Item(R.drawable.butsen, activity.getResources().getString(R.string.sensor_test)),
                new Item(R.drawable.butcompass, activity.getResources().getString(R.string.compass_test)),
                new Item(R.drawable.butwifi, activity.getResources().getString(R.string.wifi_test)),
                new Item(R.drawable.butblue, activity.getResources().getString(R.string.bluetooth_test)),
                new Item(R.drawable.butbattery, activity.getResources().getString(R.string.battery_test))
        };
    }
}
