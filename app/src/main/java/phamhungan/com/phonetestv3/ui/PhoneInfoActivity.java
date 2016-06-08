package phamhungan.com.phonetestv3.ui;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.jaredrummler.android.device.DeviceName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 21-Jan-16.
 */
public class PhoneInfoActivity extends MrAnActivity {
    @BindView(R.id.lnMain)
    LinearLayout lnMain;

    private String cpuInfo;
    private MyTitleTextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showTeleponyInfo() {
        txtTitle = new MyTitleTextView(this, "TELEPHONY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow row1;
        MyTableRow row2;
        MyTableRow row3;
        MyTableRow row4;
        MyTableRow row5;
        MyTableRow row6;
        MyTableRow row7;
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
            String IMEINumber = tm.getDeviceId();
            String subscriberID = tm.getDeviceId();
            String SIMSerialNumber = tm.getSimSerialNumber();
            String networkCountryISO = tm.getNetworkCountryIso();
            String SIMCountryISO = tm.getSimCountryIso();
            int phoneType = tm.getPhoneType();
            String phonetype = null;
            switch (phoneType) {
                case (TelephonyManager.PHONE_TYPE_CDMA):
                    phonetype = "CDMA";
                    break;
                case (TelephonyManager.PHONE_TYPE_GSM):
                    phonetype = "GSM";
                    break;
                case (TelephonyManager.PHONE_TYPE_NONE):
                    phonetype = "NONE";
                    break;
            }
            String locale = getResources().getConfiguration().locale.getDisplayCountry();
            row1 = new MyTableRow(this, "IMEI NUMBER", IMEINumber, "");
            row2 = new MyTableRow(this, "SUBSCRIBER ID", subscriberID, "");
            row3 = new MyTableRow(this, "SIM SERIAL NUMBER", SIMSerialNumber, "");
            row4 = new MyTableRow(this, "NETWORK COUNTRY ISO", networkCountryISO, "");
            row5 = new MyTableRow(this, "SIM COUNTRY ISO", SIMCountryISO, "");
            row6 = new MyTableRow(this, "PHONE TYPE", phonetype, "");
            row7 = new MyTableRow(this, "COUNTRY", locale, "");

            myTableLayout.addView(row1);
            myTableLayout.addView(row2);
            myTableLayout.addView(row3);
            myTableLayout.addView(row4);
            myTableLayout.addView(row5);
            myTableLayout.addView(row6);
            myTableLayout.addView(row7);
        } catch (Exception e) {
            row1 = new MyTableRow(this, "", "NOT INCLUDE SIM", "");
            myTableLayout.addView(row1);
        }

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showMemoryInfo() {
        txtTitle = new MyTitleTextView(this, "INTERNAL MEMORY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        long[] longs = getInternalMemorySize();
        MyTableRow row = new MyTableRow(this, "TOTAL MEMORY SIZE", formatSize(longs[0]), "");
        myTableLayout.addView(row);
        row = new MyTableRow(this, "AVAILABLE MEMORY SIZE", formatSize(longs[1]), "");
        myTableLayout.addView(row);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private long[] getInternalMemorySize() {
        long[] longs = new long[3];
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        long availableSize = statFs.getAvailableBlocks() * blockSize;
        long freeSize = statFs.getFreeBlocks() * blockSize;
        longs[0] = totalSize;
        longs[1] = availableSize;
        longs[2] = freeSize;
        return longs;
    }

    private void showOSInfo() {
        //Show OS info
        String kernel = System.getProperty("os.version");
        String firmware = Build.VERSION.INCREMENTAL;
        int sdk = Build.VERSION.SDK_INT;
        String version = Build.VERSION.RELEASE;
        ;

        txtTitle = new MyTitleTextView(this, "OS");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow rowKernel = new MyTableRow(this, "KERNEL", kernel, "");
        MyTableRow rowFirmware = new MyTableRow(this, "FIRMWARE", firmware, "");
        MyTableRow rowSdk = new MyTableRow(this, "SDK", sdk + "", "");
        MyTableRow rowVersion = new MyTableRow(this, "VERSION", version, "");
        myTableLayout.addView(rowKernel);
        myTableLayout.addView(rowFirmware);
        myTableLayout.addView(rowSdk);
        myTableLayout.addView(rowVersion);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showSystemDisplayInfo() {
        int width = (int) ScreenUtil.getScreenWidth(getWindowManager());
        int height = (int) ScreenUtil.getScreenHeight(getWindowManager());
        String dimensions = width + " x " + height + " pixels";

        //Show screen density
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int densityDpi = (int) (displayMetrics.density * 160f);
        String density = "" + densityDpi + " dpi";

        double x = Math.pow(width / displayMetrics.xdpi, 2);
        double y = Math.pow(height / displayMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        DecimalFormat df = new DecimalFormat("####0.00");
        String screenSize = df.format(screenInches) + " \"";

        txtTitle = new MyTitleTextView(this, "SYSTEM DISPLAY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow row1 = new MyTableRow(this, "DIMENSIONS", dimensions, "");
        MyTableRow row2 = new MyTableRow(this, "DENSITY", density, "");
        MyTableRow row3 = new MyTableRow(this, "SCREEN SIZE", screenSize, "");
        myTableLayout.addView(row1);
        myTableLayout.addView(row2);
        myTableLayout.addView(row3);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showDeviceInfo() {
        DeviceName.DeviceInfo deviceInfo = DeviceName.getDeviceInfo(this);
        txtTitle = new MyTitleTextView(PhoneInfoActivity.this, getResources().getString(R.string.device));
        MyTableLayout myTableLayout = new MyTableLayout(PhoneInfoActivity.this);
        MyTableRow manufacturer = new MyTableRow(PhoneInfoActivity.this, "MANUFACTURER", deviceInfo.manufacturer, "");
        MyTableRow name = new MyTableRow(PhoneInfoActivity.this, "NAME", deviceInfo.marketName, "");
        MyTableRow model = new MyTableRow(PhoneInfoActivity.this, "MODEL", deviceInfo.model, "");

        //Read Professor
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStream is = proc.getInputStream();
            cpuInfo = getStringFromInputStream(is);
        } catch (Exception e) {
        }

        MyTableRow cpu = new MyTableRow(PhoneInfoActivity.this, "CPU", cpuInfo, "");
        MyTableRow cpuAbi = new MyTableRow(PhoneInfoActivity.this, "CPU ABI", Build.CPU_ABI, "");

        //Read RAM
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long totalRam = mi.totalMem / 1048576;

        MyTableRow ram = new MyTableRow(PhoneInfoActivity.this, "RAM", totalRam + "", "Mb");

        myTableLayout.addView(manufacturer);
        myTableLayout.addView(name);
        myTableLayout.addView(model);
        myTableLayout.addView(cpu);
        myTableLayout.addView(cpuAbi);
        myTableLayout.addView(ram);
        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    //Get CPU info
    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append("   " + line);
                sb.append("\n");
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


    @Override
    protected int getView() {
        return R.layout.activity_phone_info;
    }

    @Override
    public void initializeChildView() {
        showDeviceInfo();
        showOSInfo();
        showSystemDisplayInfo();
        showMemoryInfo();
        showTeleponyInfo();
    }

    @Override
    public void initializeChildValue() {

    }

    @Override
    public void initializeChildAction() {

    }
}


