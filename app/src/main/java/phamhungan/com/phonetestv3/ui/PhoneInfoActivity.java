package phamhungan.com.phonetestv3.ui;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 21-Jan-16.
 */
public class PhoneInfoActivity extends AppCompatActivity {
    private String cpuInfo;
    private ScrollView main;
    private LinearLayout lnMain;
    private MyTitleTextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);
        main = (ScrollView)findViewById(R.id.main);
        lnMain = (LinearLayout)findViewById(R.id.lnMain);

        showDeviceInfo();
        showOSInfo();
        showSystemDisplayInfo();
        showMemoryInfo();
        showTeleponyInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemShare:
                ScreenUtil.showSharedialog(this);
                break;
            case R.id.itemAbout:
                ScreenUtil.goAbout(this);
                break;
        }
        return true;
    }

    private void showTeleponyInfo() {
        txtTitle = new MyTitleTextView(this,"TELEPHONY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow row1;
        MyTableRow row2;
        MyTableRow row3;
        MyTableRow row4;
        MyTableRow row5;
        MyTableRow row6;
        MyTableRow row7;
        try
        {
            TelephonyManager tm=(TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
            String IMEINumber=tm.getDeviceId();
            String subscriberID=tm.getDeviceId();
            String SIMSerialNumber=tm.getSimSerialNumber();
            String networkCountryISO=tm.getNetworkCountryIso();
            String SIMCountryISO=tm.getSimCountryIso();
            int phoneType=tm.getPhoneType();
            String phonetype = null;
            switch (phoneType)
            {
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
            row1 = new MyTableRow(this,"IMEI NUMBER",IMEINumber,"");
            row2 = new MyTableRow(this,"SUBSCRIBER ID",subscriberID,"");
            row3 = new MyTableRow(this,"SIM SERIAL NUMBER",SIMSerialNumber,"");
            row4 = new MyTableRow(this,"NETWORK COUNTRY ISO",networkCountryISO,"");
            row5 = new MyTableRow(this,"SIM COUNTRY ISO",SIMCountryISO,"");
            row6 = new MyTableRow(this,"PHONE TYPE",phonetype,"");
            row7 = new MyTableRow(this,"COUNTRY",locale,"");

            myTableLayout.addView(row1);
            myTableLayout.addView(row2);
            myTableLayout.addView(row3);
            myTableLayout.addView(row4);
            myTableLayout.addView(row5);
            myTableLayout.addView(row6);
            myTableLayout.addView(row7);
        }
        catch(Exception e)
        {
            row1 = new MyTableRow(this,"","NOT INCLUDE SIM","");
            myTableLayout.addView(row1);
        }

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showMemoryInfo() {
        txtTitle = new MyTitleTextView(this,"INTERNAL MEMORY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow row1 = new MyTableRow(this,"AVAILABLE MEMORY SIZE",getAvailableInternalMemorySize(),"");
        MyTableRow row2 = new MyTableRow(this,"TOTAL MEMORY SIZE",getTotalInternalMemorySize(),"");
        myTableLayout.addView(row1);
        myTableLayout.addView(row2);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);

        txtTitle = new MyTitleTextView(this,"EXTERNAL MEMORY");
        MyTableLayout myTableLayout2 = new MyTableLayout(this);
        MyTableRow row3 = new MyTableRow(this,"AVAILABLE MEMORY SIZE",getAvailableExternalMemorySize(),"");
        MyTableRow row4 = new MyTableRow(this,"TOTAL MEMORY SIZE",getTotalExternalMemorySize(),"");
        myTableLayout2.addView(row3);
        myTableLayout2.addView(row4);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout2);
    }

    private void showOSInfo() {
        //Show OS info
        String kernel = System.getProperty("os.version");
        String firmware = Build.VERSION.INCREMENTAL;
        int sdk = Build.VERSION.SDK_INT;
        String version = Build.VERSION.RELEASE;;

        txtTitle = new MyTitleTextView(this,"OS");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow rowKernel = new MyTableRow(this,"KERNEL",kernel,"");
        MyTableRow rowFirmware = new MyTableRow(this,"FIRMWARE",firmware,"");
        MyTableRow rowSdk = new MyTableRow(this,"SDK",sdk+"","");
        MyTableRow rowVersion = new MyTableRow(this,"VERSION",version,"");
        myTableLayout.addView(rowKernel);
        myTableLayout.addView(rowFirmware);
        myTableLayout.addView(rowSdk);
        myTableLayout.addView(rowVersion);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showSystemDisplayInfo() {
        int width = (int)ScreenUtil.getScreenWidth(getWindowManager());
        int height = (int)ScreenUtil.getScreenHeight(getWindowManager());
        String dimensions = width + " x "+height+" pixels";

        //Show screen density
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(displayMetrics.density * 160f);
        String density = ""+densityDpi+" dpi";

        double x = Math.pow(width / displayMetrics.xdpi, 2);
        double y = Math.pow(height / displayMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        DecimalFormat df = new DecimalFormat("####0.00");
        String screenSize = df.format(screenInches)+" \"";

        txtTitle = new MyTitleTextView(this,"SYSTEM DISPLAY");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow row1 = new MyTableRow(this,"DIMENSIONS",dimensions,"");
        MyTableRow row2 = new MyTableRow(this,"DENSITY",density,"");
        MyTableRow row3 = new MyTableRow(this,"SCREEN SIZE",screenSize,"");
        myTableLayout.addView(row1);
        myTableLayout.addView(row2);
        myTableLayout.addView(row3);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    private void showDeviceInfo() {
        txtTitle = new MyTitleTextView(this,getResources().getString(R.string.device));
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow manufacturer = new MyTableRow(this,"MANUFACTURER",Build.MANUFACTURER,"");
        MyTableRow model = new MyTableRow(this,"MODEL",Build.MODEL,"");
        MyTableRow brand = new MyTableRow(this,"BRAND",Build.BRAND,"");
        MyTableRow device = new MyTableRow(this,"DEVICE",Build.DEVICE,"");
        MyTableRow product = new MyTableRow(this,"PRODUCT",Build.PRODUCT,"");

        //Read Professor
        try{
            Process proc = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStream is = proc.getInputStream();
            cpuInfo = getStringFromInputStream(is);
        }catch(Exception e){}

        MyTableRow cpu = new MyTableRow(this,"CPU",cpuInfo,"");
        MyTableRow cpuAbi = new MyTableRow(this,"CPU ABI",Build.CPU_ABI,"");

        //Read RAM
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long totalRam = mi.totalMem / 1048576;

        MyTableRow ram = new MyTableRow(this,"RAM",totalRam+"","Mb");

        myTableLayout.addView(manufacturer);
        myTableLayout.addView(model);
        myTableLayout.addView(brand);
        myTableLayout.addView(device);
        myTableLayout.addView(product);
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
            while((line = br.readLine()) != null) {
                sb.append("   "+line);
                sb.append("\n");
            }
        }
        catch (IOException e) {}
        finally {
            if(br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {}
            }
        }
        return sb.toString();
    }

    //Get Internal & External info
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "None";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(blockSize * totalBlocks);
        } else {
            return "None";
        }
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
}


