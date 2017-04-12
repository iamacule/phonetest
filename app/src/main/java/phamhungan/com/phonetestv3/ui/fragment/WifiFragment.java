package phamhungan.com.phonetestv3.ui.fragment;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 26-Jan-16.
 */
public class WifiFragment extends BaseFragment{
    private WifiManager myWifiManager;
    private Button butRefresh;
    private LinearLayout lnMain;
    private MyTitleTextView title;
    private MyTableLayout myTableLayout;
    private MyTableRow rowIp;
    private MyTableRow rowSSID;
    private MyTableRow rowBSSID;
    private MyTableRow rowSpeed;
    private MyTableRow rowRSSI;
    private MyTableRow rowMAC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
            getRootActivity().lnBottom.setVisibility(View.VISIBLE);
        } else {
            super.setHasOptionsMenu(true);
        }
        lnMain = (LinearLayout)view.findViewById(R.id.lnMain);
        myWifiManager = (WifiManager)getRootActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        setLayout();
        return view;
    }

    private void setLayout() {
        lnMain.removeAllViews();
        title = new MyTitleTextView(getActivity(),"");
        myTableLayout = new MyTableLayout(getActivity());
        rowIp = new MyTableRow(getActivity(),"IP ADDRESS", "","");

            rowSSID = new MyTableRow(getActivity(),"SSID", "","");
            rowBSSID = new MyTableRow(getActivity(),"BSSID", "","");
            rowSpeed = new MyTableRow(getActivity(),"SPEED", "","");
            rowRSSI = new MyTableRow(getActivity(),"RSSI", "","");
            rowMAC = new MyTableRow(getActivity(),"MAC", "","");
            myTableLayout.addView(rowIp);
            myTableLayout.addView(rowSSID);
            myTableLayout.addView(rowBSSID);
            myTableLayout.addView(rowSpeed);
            myTableLayout.addView(rowRSSI);
            myTableLayout.addView(rowMAC);

            butRefresh = new Button(getActivity());
            butRefresh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            butRefresh.setText(getActivity().getResources().getString(R.string.refresh));
            butRefresh.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            butRefresh.setGravity(Gravity.CENTER);
            butRefresh.setBackground(getActivity().getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
            butRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myWifiManager.setWifiEnabled(true);
                    refresh(myWifiManager.isWifiEnabled());
                }
            });


        myTableLayout.addView(butRefresh);
        lnMain.addView(title);
        lnMain.addView(myTableLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh(myWifiManager.isWifiEnabled());
    }

    private void refresh(boolean wifiEnabled) {
        if(wifiEnabled){
            WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
            int myIp = myWifiInfo.getIpAddress();

            title.setValue("CONNECTED");

            int intMyIp3 = myIp/0x1000000;
            int intMyIp3mod = myIp%0x1000000;

            int intMyIp2 = intMyIp3mod/0x10000;
            int intMyIp2mod = intMyIp3mod%0x10000;

            int intMyIp1 = intMyIp2mod/0x100;
            int intMyIp0 = intMyIp2mod%0x100;

            rowIp.setValue(String.valueOf(intMyIp0)
                            + "." + String.valueOf(intMyIp1)
                            + "." + String.valueOf(intMyIp2)
                            + "." + String.valueOf(intMyIp3));

            rowSSID.setValue(myWifiInfo.getSSID());
            rowBSSID.setValue(myWifiInfo.getBSSID());
            rowSpeed.setValue(String.valueOf(myWifiInfo.getLinkSpeed()) + " " + WifiInfo.LINK_SPEED_UNITS);
            rowRSSI.setValue(myWifiInfo.getRssi() + "");
            rowMAC.setValue(myWifiInfo.getMacAddress());

        }else {
            title.setValue("DISCONNECTED");
            rowIp.setValue("---");

            rowSSID.setValue("---");
            rowBSSID.setValue("---");
            rowSpeed.setValue("---");
            rowRSSI.setValue("---");
            rowMAC.setValue("---");
        }
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_phone_info;
    }
}
