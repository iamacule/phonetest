package phamhungan.com.phonetestv3.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 26-Jan-16.
 */
public class BatteryFragment extends BaseFragment {
    private LinearLayout lnMain;
    private MyTitleTextView title;
    private MyTableLayout myTableLayout;
    private MyTableRow rowStatus;
    private MyTableRow rowHealth;
    private MyTableRow rowLevel;
    private MyTableRow rowTec;
    private MyTableRow rowTemp;
    private MyTableRow rowVol;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
            TestActivity.instance.lnBottom.setVisibility(View.VISIBLE);
        } else {
            super.setHasOptionsMenu(true);
        }
        lnMain = (LinearLayout)view.findViewById(R.id.lnMain);
        setLayout();
        return view;
    }

    private void setLayout() {
        lnMain.removeAllViews();
        title = new MyTitleTextView(getActivity(),"BATTERY INFORMATION");
        myTableLayout = new MyTableLayout(getActivity());
        rowStatus = new MyTableRow(getActivity(),"STATUS", "","");

        rowHealth = new MyTableRow(getActivity(),"HEALTH", "","");
        rowLevel = new MyTableRow(getActivity(),"LEVEL", "","");
        rowTec = new MyTableRow(getActivity(),"TECHNOLOGY", "","");
        rowTemp = new MyTableRow(getActivity(),"TEMPERATURE", "","");
        rowVol = new MyTableRow(getActivity(),"VOLTAGE", "","");
        myTableLayout.addView(rowStatus);
        myTableLayout.addView(rowHealth);
        myTableLayout.addView(rowLevel);
        myTableLayout.addView(rowTec);
        myTableLayout.addView(rowTemp);
        myTableLayout.addView(rowVol);

        lnMain.addView(title);
        lnMain.addView(myTableLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(batteryInfoReceiver);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //tinh trang PIN
            int health = intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
            //% PIN
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            //co dang sac hay khong
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            //cong nghe su dung de lam PIN
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            //nhiet do PIN
            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            //so Vol cua PIN
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            //lay thong tin Cong sac
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            String charg = null;
            if(isCharging)
            {
                if(usbCharge)
                    charg = "CHARGING BY USB";
                else if(acCharge)
                    charg = "CHARGING BY ADAPTER";
                else
                    charg = "CHARGING";
            }
            else charg = "NOT CHARGE...";

            String strHealth;
            if (health == BatteryManager.BATTERY_HEALTH_GOOD){
                strHealth = "Good";
            } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                strHealth = "Over Heat";
            } else if (health == BatteryManager.BATTERY_HEALTH_DEAD){
                strHealth = "Dead";
            } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                strHealth = "Over Voltage";
            } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                strHealth = "Unspecified Failure";
            } else{
                strHealth = "Unknown";}

            rowStatus.setValue(charg);
            rowHealth.setValue(strHealth);
            rowLevel.setValue(level+"%");
            rowTec.setValue(technology);
            rowTemp.setValue((float)temperature/10+" oC");
            rowVol.setValue(voltage+"V");
        }
    };

    @Override
    protected int getRootLayout() {
        return R.layout.activity_phone_info;
    }
}
