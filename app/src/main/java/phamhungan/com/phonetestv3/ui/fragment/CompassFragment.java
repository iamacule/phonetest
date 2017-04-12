package phamhungan.com.phonetestv3.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 26-Jan-16.
 */
public class CompassFragment extends BaseFragment implements SensorEventListener{
    private ImageView imgCompass;
    private TextView txtHeading;
    private Bitmap bpCompass;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
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
        mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        txtHeading = (TextView)view.findViewById(R.id.txtHeading);
        imgCompass = (ImageView)view.findViewById(R.id.imgCompass);
        return view;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) == null)
        {
            txtHeading.setText("Your Device doesn't have ORIENTATION Sensor !");
        }
        else
        {
            bpCompass = ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.compass), ScreenUtil.getScreenWidth(getActivity().getWindowManager())*3/4);
            imgCompass.setImageBitmap(bpCompass);
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_compass_test;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        txtHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        RotateAnimation ra = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        // Start the animation
        imgCompass.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
