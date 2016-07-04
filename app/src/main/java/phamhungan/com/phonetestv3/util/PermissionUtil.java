package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by MrAn on 29-Apr-16.
 */
public class PermissionUtil {
    public static final int MY_REQUEST_CAMERA_PERMISSION_CODE = 1;
    public static final int MY_REQUEST_RECORD_PERMISSION_CODE = 2;
    public static final int MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE = 3;
    public static final int MY_REQUEST_READ_PHONE_STATE_PERMISSION_CODE = 4;
    public static boolean isPermissionCameraGranted = false;
    public static boolean isPermissionRecordGranted = false;
    public static boolean isPermissionWriteStorageGranted = false;
    public static boolean isPermissionReadPhoneStateGranted = false;
    private Activity activity;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public void checkPermission(String permission, int idCallBack) {
        Preferences preferences = new Preferences(activity);
        boolean firstTimeAccount = preferences.getPermission(permission);

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // 2. Asked before, and the user said "no"
            ActivityCompat.requestPermissions(activity, new String[]{permission}, idCallBack);
        } else {
            if (firstTimeAccount) {
                // 1. first time, never asked
                preferences.storeData(permission, false);
                // Account permission has not been granted, request it directly.
                ActivityCompat.requestPermissions(activity, new String[]{permission}, idCallBack);
            } else {
                // 3. If you asked a couple of times before, and the user has said "no, and stop asking"
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        }
    }
}
