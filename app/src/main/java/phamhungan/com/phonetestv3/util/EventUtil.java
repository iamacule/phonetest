package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;


import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.ChooserActivity;
import phamhungan.com.phonetestv3.ui.fragment.BatteryFragment;
import phamhungan.com.phonetestv3.ui.fragment.BlueToothFragment;
import phamhungan.com.phonetestv3.ui.fragment.BrightnessFragment;
import phamhungan.com.phonetestv3.ui.fragment.CameraFragment;
import phamhungan.com.phonetestv3.ui.fragment.CompassFragment;
import phamhungan.com.phonetestv3.ui.fragment.LCDScreenFragment;
import phamhungan.com.phonetestv3.ui.fragment.MicrophoneFragment;
import phamhungan.com.phonetestv3.ui.fragment.MultiTouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.ResultFragment;
import phamhungan.com.phonetestv3.ui.fragment.SensorFragment;
import phamhungan.com.phonetestv3.ui.fragment.SingleTest;
import phamhungan.com.phonetestv3.ui.fragment.SoundFragment;
import phamhungan.com.phonetestv3.ui.fragment.TouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.VibrateFragment;
import phamhungan.com.phonetestv3.ui.fragment.WifiFragment;

/**
 * Created by MrAn PC on 21-Jan-16.
 */
public class EventUtil {
    public static final String DATANAME = "MyResult";
    public static final String RESULTSTRING = "result";
    public static void backPressExitApp(Activity activity){
        activity.moveTaskToBack(true);
    }

    public static void onBackPress(final Activity activity){
        final Fragment fr = activity.getFragmentManager().findFragmentById(R.id.mainFragment);
        final Intent intent = new Intent(fr.getActivity(), ChooserActivity.class);
        if(!DataUtil.whichTest){
            if(fr instanceof SoundFragment||
                    fr instanceof VibrateFragment||
                    fr instanceof MicrophoneFragment||
                    fr instanceof CameraFragment||
                    fr instanceof SensorFragment||
                    fr instanceof WifiFragment ||
                    fr instanceof BlueToothFragment ||
                    fr instanceof BatteryFragment ||
                    fr instanceof CompassFragment){
                ScreenUtil.changeFragment(new SingleTest(),activity.getFragmentManager());
            }
            else if (fr instanceof LCDScreenFragment||
                    fr instanceof TouchFragment||
                    fr instanceof MultiTouchFragment||
                    fr instanceof BrightnessFragment){
                intent.putExtra(fr.getActivity().getResources().getString(R.string.which_test),fr.getActivity().getResources().getString(R.string.single_test));
                fr.getActivity().startActivity(intent);
                activity.finish();
            }else {
                fr.getActivity().startActivity(intent);
                activity.finish();
            }
        }else {
            if(fr instanceof ResultFragment){
                fr.getActivity().startActivity(intent);
                activity.finish();
            }else {
                DialogAsk.Build dialog = new DialogAsk.Build(activity);
                dialog.setMessage(activity.getString(R.string.full_test_quit_ask))
                        .setNegativeButton(activity.getString(R.string.no))
                        .setPositiveButton(activity.getString(R.string.yes))
                        .setNegativeButtonDefaultClick()
                        .getPositiveButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fr.getActivity().startActivity(intent);
                        clearData(activity);
                        activity.finish();
                    }
                });
                dialog.show();
            }
        }
    }

    public static void onPressButtonFulltest(Activity activity,String data){
        Fragment fr = activity.getFragmentManager().findFragmentById(R.id.mainFragment);
        Intent intent = new Intent(fr.getActivity(), ChooserActivity.class);
        if(fr instanceof ResultFragment){
            fr.getActivity().startActivity(intent);
        }else {
            setData(data, activity);
            if(fr instanceof BatteryFragment){
                ScreenUtil.changeFragment(new ResultFragment(),activity.getFragmentManager());
            }else {
                if (fr instanceof SoundFragment) {
                    ScreenUtil.changeFragment(new VibrateFragment(), activity.getFragmentManager());
                } else if (fr instanceof VibrateFragment) {
                    ScreenUtil.changeFragment(new MicrophoneFragment(), activity.getFragmentManager());
                } else if (fr instanceof MicrophoneFragment) {
                    ScreenUtil.changeFragment(new LCDScreenFragment(), activity.getFragmentManager());
                } else if (fr instanceof LCDScreenFragment) {
                    intent.putExtra(fr.getActivity().getResources().getString(R.string.which_test),
                            fr.getActivity().getResources().getString(R.string.lcd_test));
                    fr.getActivity().startActivity(intent);
                } else if (fr instanceof BrightnessFragment) {
                    intent.putExtra(fr.getActivity().getResources().getString(R.string.which_test),
                            fr.getActivity().getResources().getString(R.string.brightness_test));
                    fr.getActivity().startActivity(intent);
                } else if (fr instanceof TouchFragment) {
                    intent.putExtra(fr.getActivity().getResources().getString(R.string.which_test),
                            fr.getActivity().getResources().getString(R.string.touch_test));
                    fr.getActivity().startActivity(intent);
                } else if (fr instanceof MultiTouchFragment) {
                    intent.putExtra(fr.getActivity().getResources().getString(R.string.which_test),
                            fr.getActivity().getResources().getString(R.string.multitouch_test));
                    fr.getActivity().startActivity(intent);
                } else if (fr instanceof CameraFragment) {
                    ScreenUtil.changeFragment(new SensorFragment(), activity.getFragmentManager());
                }  else if (fr instanceof SensorFragment) {
                    ScreenUtil.changeFragment(new CompassFragment(), activity.getFragmentManager());
                } else if (fr instanceof CompassFragment) {
                    ScreenUtil.changeFragment(new WifiFragment(), activity.getFragmentManager());
                } else if (fr instanceof WifiFragment) {
                    ScreenUtil.changeFragment(new BlueToothFragment(), activity.getFragmentManager());
                } else if (fr instanceof BlueToothFragment) {
                    ScreenUtil.changeFragment(new BatteryFragment(), activity.getFragmentManager());
                }
            }
        }
    }

    private static void setData(String data,Activity activity){
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences(DATANAME, activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String dataStore = "";
        dataStore = pref.getString(RESULTSTRING,null);
        if(dataStore!=null){
            dataStore = dataStore+data;
        }else {
            dataStore=data;
        }
        Log.d("Data : ",dataStore);
        editor.putString(RESULTSTRING,dataStore);
        editor.commit();
    }

    public static String getData(Activity activity){
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences(DATANAME, activity.MODE_PRIVATE);
        return pref.getString(RESULTSTRING,null);
    }

    public static void clearData (Activity activity){
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences(DATANAME, activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
