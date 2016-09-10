package phamhungan.com.phonetestv3.ui.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.ui.toast.Boast;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 26-Jan-16.
 */
public class SensorFragment extends BaseFragment implements SensorEventListener {
    private SensorManager sMgr;
    private Sensor AC,GY,ON,MA,PRO,LI;
    private LinearLayout lnMain;
    private MyTableLayout tbAC,tbGY,tbON,tbMA,tbPRO,tbLI;
    private MyTableRow rowXAC,rowYAC,rowZAC;
    private MyTableRow rowXGY,rowYGY,rowZGY;
    private MyTableRow rowXMA,rowYMA,rowZMA;
    private MyTableRow rowYAWON,rowPITCHON,rowROLLON;
    private MyTableRow rowReadPRO;
    private MyTableRow rowLight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
            TestActivity.instance.lnBottom.setVisibility(View.GONE);
        } else {
            super.setHasOptionsMenu(true);
        }
        TestActivity.instance.lnBottom.setVisibility(View.GONE);
        lnMain = (LinearLayout)view.findViewById(R.id.lnMain);
        tbAC = new MyTableLayout(getActivity());
        tbGY = new MyTableLayout(getActivity());
        tbON = new MyTableLayout(getActivity());
        tbMA = new MyTableLayout(getActivity());
        tbPRO = new MyTableLayout(getActivity());
        tbLI = new MyTableLayout(getActivity());
        action();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sMgr.registerListener(this, AC, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, GY, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, ON, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, MA, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, PRO, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, LI, SensorManager.SENSOR_DELAY_NORMAL);
        if(DataUtil.whichTest){
            Boast.makeText(getActivity(), Html.fromHtml(getActivity().getResources().getString(R.string.message_show_hide_single_sensor))).show();
        }
    }

    private void action() {
        lnMain.removeAllViews();
        sMgr = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        AC = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        GY = sMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ON = sMgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        MA = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        PRO = sMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        LI = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"ACCELEROMETER");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",AC.getVendor(),"");
            rowXAC = new MyTableRow(getActivity(),"X","","m/s");
            rowYAC = new MyTableRow(getActivity(),"Y","","m/s");
            rowZAC = new MyTableRow(getActivity(),"Z","","m/s");
            tbAC.addView(rowBrand);
            tbAC.addView(rowXAC);
            tbAC.addView(rowYAC);
            tbAC.addView(rowZAC);

            lnMain.addView(title);
            lnMain.addView(tbAC);
        }
        else
        {
            MyTitleTextView titleAC = new MyTitleTextView(getActivity(),"ACCELEROMETER");
            MyTableRow row = new MyTableRow(getActivity(),"No ACCELEROMETER Sensor","","");
            tbAC.addView(row);
            lnMain.addView(titleAC);
            lnMain.addView(tbAC);
        }
        if (sMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"GYROSCOPE");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",GY.getVendor(),"");
            rowXGY = new MyTableRow(getActivity(),"X","","rad/s");
            rowYGY = new MyTableRow(getActivity(),"Y","","rad/s");
            rowZGY = new MyTableRow(getActivity(),"Z","","rad/s");
            tbGY.addView(rowBrand);
            tbGY.addView(rowXGY);
            tbGY.addView(rowYGY);
            tbGY.addView(rowZGY);

            lnMain.addView(title);
            lnMain.addView(tbGY);
        }
        else
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"GYROSCOPE");
            MyTableRow row = new MyTableRow(getActivity(),"No GYROSCOPE Sensor","","");
            tbGY.addView(row);
            lnMain.addView(title);
            lnMain.addView(tbGY);
        }
        if (sMgr.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"ORIENTATION");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",ON.getVendor(),"");
            rowYAWON = new MyTableRow(getActivity(),"YAW","","");
            rowPITCHON = new MyTableRow(getActivity(),"PITCH","","");
            rowROLLON = new MyTableRow(getActivity(),"ROLL","","");
            tbON.addView(rowBrand);
            tbON.addView(rowYAWON);
            tbON.addView(rowPITCHON);
            tbON.addView(rowROLLON);

            lnMain.addView(title);
            lnMain.addView(tbON);
        }
        else
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"ORIENTATION");
            MyTableRow row = new MyTableRow(getActivity(),"No ORIENTATION Sensor","","");
            tbON.addView(row);
            lnMain.addView(title);
            lnMain.addView(tbON);
        }
        if (sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"MAGNETIC FIELD");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",MA.getVendor(),"");
            rowXMA = new MyTableRow(getActivity(),"X","","uT");
            rowYMA = new MyTableRow(getActivity(),"Y","","uT");
            rowZMA = new MyTableRow(getActivity(),"Z","","uT");
            tbMA.addView(rowBrand);
            tbMA.addView(rowXMA);
            tbMA.addView(rowYMA);
            tbMA.addView(rowZMA);

            lnMain.addView(title);
            lnMain.addView(tbMA);
        }
        else
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"MAGNETIC FIELD");
            MyTableRow row = new MyTableRow(getActivity(),"No MAGNETIC FIELD Sensor","","");
            tbMA.addView(row);
            lnMain.addView(title);
            lnMain.addView(tbMA);
        }
        if (sMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"PROXIMITY");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",PRO.getVendor(),"");
            rowReadPRO = new MyTableRow(getActivity(),"READING","","");
            tbPRO.addView(rowBrand);
            tbPRO.addView(rowReadPRO);

            lnMain.addView(title);
            lnMain.addView(tbPRO);
        }
        else
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"PROXIMITY");
            MyTableRow row = new MyTableRow(getActivity(),"No PROXIMITY Sensor","","");
            tbPRO.addView(row);
            lnMain.addView(title);
            lnMain.addView(tbPRO);
        }
        if (sMgr.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"LIGHT");
            MyTableRow rowBrand = new MyTableRow(getActivity(),"BRAND",LI.getVendor(),"");
            rowLight = new MyTableRow(getActivity(),"READING","","lux");
            tbLI.addView(rowBrand);
            tbLI.addView(rowLight);

            lnMain.addView(title);
            lnMain.addView(tbLI);
        }
        else
        {
            MyTitleTextView title = new MyTitleTextView(getActivity(),"LIGHT");
            MyTableRow row = new MyTableRow(getActivity(),"No LIGHT Sensor","","");
            tbLI.addView(row);
            lnMain.addView(title);
            lnMain.addView(tbLI);
        }
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_phone_info;
    }

    @Override
    public void onPause() {
        super.onPause();
        sMgr.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            rowXAC.setValue(event.values[0]+"");
            rowYAC.setValue(event.values[1]+"");
            rowZAC.setValue(event.values[2]+"");

        }
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            rowXGY.setValue(event.values[0]+"");
            rowYGY.setValue(event.values[1]+"");
            rowZGY.setValue(event.values[2]+"");
        }
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION)
        {
            rowYAWON.setValue(event.values[0]+"");
            rowPITCHON.setValue(event.values[1]+"");
            rowROLLON.setValue(event.values[2] + "");
        }
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            rowXMA.setValue(event.values[0]+"");
            rowYMA.setValue(event.values[1]+"");
            rowZMA.setValue(event.values[2]+"");
        }
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            rowReadPRO.setValue(event.values[0]==0?"NEAR":"FAR");
        }
        if(event.sensor.getType()==Sensor.TYPE_LIGHT)
        {
            rowLight.setValue(event.values[0]+"");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
