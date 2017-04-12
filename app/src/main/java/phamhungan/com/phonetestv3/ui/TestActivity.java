package phamhungan.com.phonetestv3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.fragment.BatteryFragment;
import phamhungan.com.phonetestv3.ui.fragment.BlueToothFragment;
import phamhungan.com.phonetestv3.ui.fragment.BrightnessFragment;
import phamhungan.com.phonetestv3.ui.fragment.CameraFragment;
import phamhungan.com.phonetestv3.ui.fragment.CompassFragment;
import phamhungan.com.phonetestv3.ui.fragment.LCDScreenFragment;
import phamhungan.com.phonetestv3.ui.fragment.MicrophoneFragment;
import phamhungan.com.phonetestv3.ui.fragment.MultiTouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.NullFragment;
import phamhungan.com.phonetestv3.ui.fragment.ResultFragment;
import phamhungan.com.phonetestv3.ui.fragment.SensorFragment;
import phamhungan.com.phonetestv3.ui.fragment.SingleTest;
import phamhungan.com.phonetestv3.ui.fragment.SoundFragment;
import phamhungan.com.phonetestv3.ui.fragment.TouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.VibrateFragment;
import phamhungan.com.phonetestv3.ui.fragment.WifiFragment;
import phamhungan.com.phonetestv3.ui.helper.FragmentNavigator;
import phamhungan.com.phonetestv3.ui.toast.Boast;
import phamhungan.com.phonetestv3.util.AminationUtil;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogAsk;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class TestActivity extends MrAnActivity implements View.OnClickListener {
    @BindView(R.id.butPass)
    Button butPass;
    @BindView(R.id.butSkip)
    Button butSkip;
    @BindView(R.id.butFail)
    Button butFail;
    @BindView(R.id.lnBottom)
    public LinearLayout lnBottom;


    public static final String DATANAME = "MyResult";
    public static final String RESULTSTRING = "result";
    private AudioManager audioManager;

    private String stringExtra;
    private boolean isShowLnBottom = false;
    private boolean isFullScreen = false;
    private FragmentNavigator fragmentNavigator;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (acceptSwitchFullScreenMode()) {
                    if (action == KeyEvent.ACTION_DOWN) {
                        if (!isFullScreen) {
                            setFullScreen(true);
                        } else {
                            setFullScreen(false);
                        }
                    }
                } else
                    super.dispatchKeyEvent(event);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (acceptSwitchLnBottom()) {
                    if (action == KeyEvent.ACTION_DOWN) {
                        showHideLnBottom();
                    }
                } else
                    super.dispatchKeyEvent(event);
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private void showHideLnBottom() {
        Fragment fr = fragmentNavigator.getActiveFragment();
        if (fr instanceof ResultFragment) {
        } else {
            if (DataUtil.whichTest) {
                if (isShowLnBottom) {
                    lnBottom.setVisibility(View.GONE);
                    lnBottom.setAnimation(AminationUtil.sliceOutToBottom(this));
                } else {
                    lnBottom.setVisibility(View.VISIBLE);
                    lnBottom.setAnimation(AminationUtil.sliceInToTop(this));
                }
                isShowLnBottom = !isShowLnBottom;
            }
        }
    }

    public void setFullScreen(boolean fullScreen) {
        if (acceptSwitchFullScreenMode()) {
            if (fullScreen) {
                hideSystemUI();
                isFullScreen = true;
            } else {
                showSystemUI();
                isFullScreen = false;
            }
        }
    }

    private boolean acceptSwitchFullScreenMode() {
        Fragment fr = fragmentNavigator.getActiveFragment();
        if (fr instanceof LCDScreenFragment ||
                fr instanceof TouchFragment ||
                fr instanceof MultiTouchFragment)
            return true;
        else return false;
    }

    private boolean acceptSwitchLnBottom() {
        Fragment fr = fragmentNavigator.getActiveFragment();
        if (fr instanceof LCDScreenFragment ||
                fr instanceof TouchFragment ||
                fr instanceof MultiTouchFragment ||
                fr instanceof SensorFragment)
            return true;
        else return false;
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().getDecorView().setAnimation(AminationUtil.fadeOut(this));
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().getDecorView().setAnimation(AminationUtil.fadeIn(this));
    }

    private void setLayout(String stringExtra) {
        if (stringExtra.equals(getResources().getString(R.string.single_test))) {
            clearData();
            lnBottom.setVisibility(View.GONE);
            isShowLnBottom = false;
            fragmentNavigator.goTo(new SingleTest());
        } else if (stringExtra.equals(getResources().getString(R.string.lcd_test))) {
            fragmentNavigator.goTo(new BrightnessFragment());
        } else if (stringExtra.equals(getResources().getString(R.string.brightness_test))) {
            fragmentNavigator.goTo(new TouchFragment());
        } else if (stringExtra.equals(getResources().getString(R.string.touch_test))) {
            fragmentNavigator.goTo(new MultiTouchFragment());
        } else if (stringExtra.equals(getResources().getString(R.string.multitouch_test))) {
            fragmentNavigator.goTo(new CameraFragment());
        } else {
            clearData();
            lnBottom.setVisibility(View.VISIBLE);
            isShowLnBottom = true;
            fragmentNavigator.goTo(new SoundFragment());
        }
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, ChooserActivity.class);
        Fragment fragment = fragmentNavigator.getActiveFragment();
        if (fragment instanceof SingleTest) {
            Intent i = new Intent(this, ChooserActivity.class);
            startActivity(i);
        } else {
            if(!DataUtil.whichTest){
                if(fragment instanceof SoundFragment||
                        fragment instanceof VibrateFragment ||
                        fragment instanceof MicrophoneFragment ||
                        fragment instanceof CameraFragment||
                        fragment instanceof SensorFragment||
                        fragment instanceof WifiFragment ||
                        fragment instanceof BlueToothFragment ||
                        fragment instanceof BatteryFragment ||
                        fragment instanceof CompassFragment){
                    fragmentNavigator.clearHistory();
                    fragmentNavigator.goTo(new SingleTest());
                }
                else if (fragment instanceof LCDScreenFragment||
                        fragment instanceof TouchFragment||
                        fragment instanceof MultiTouchFragment||
                        fragment instanceof BrightnessFragment){
                    intent.putExtra(fragment.getActivity().getResources().getString(R.string.which_test),fragment.getActivity().getResources().getString(R.string.single_test));
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(intent);
                    finish();
                }
            }else {
                if(fragment instanceof ResultFragment){
                    startActivity(intent);
                    finish();
                }else {
                    DialogAsk.Build dialog = new DialogAsk.Build(this);
                    dialog.setMessage(getString(R.string.full_test_quit_ask))
                            .setNegativeButton(getString(R.string.no))
                            .setPositiveButton(getString(R.string.yes))
                            .setNegativeButtonDefaultClick()
                            .getPositiveButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent intent = new Intent(TestActivity.this, ChooserActivity.class);
                            startActivity(intent);
                            clearData();
                            finish();
                        }
                    });
                    dialog.show();
                }
            }
        }
        if (needShowAds()) {
            DataUtil.countToLoadAd++;
            loadRewardedVideoAd();
            checkToLoadAd();
        }
    }

    @Override
    public void onClick(View v) {
        String data = null;
        switch (v.getId()) {
            case R.id.butPass:
                data = DataUtil.PASSED;
                break;
            case R.id.butSkip:
                data = DataUtil.SKIPPED;
                break;
            case R.id.butFail:
                data = DataUtil.FAILED;
                break;
        }

        Fragment fragment = fragmentNavigator.getActiveFragment();
        final Intent intent = new Intent(fragment.getActivity(), ChooserActivity.class);
        if(fragment instanceof ResultFragment){
            fragment.getActivity().startActivity(intent);
        }else {
            setData(data);
            if(fragment instanceof BatteryFragment){
                fragmentNavigator.goTo(new ResultFragment());
            }else {
                if (fragment instanceof SoundFragment) {
                    fragmentNavigator.goTo(new VibrateFragment());
                } else if (fragment instanceof VibrateFragment) {
                    fragmentNavigator.goTo(new MicrophoneFragment());
                } else if (fragment instanceof MicrophoneFragment) {
                    fragmentNavigator.goTo(new LCDScreenFragment());
                } else if (fragment instanceof LCDScreenFragment) {
                    intent.putExtra(getString(R.string.which_test), getString(R.string.lcd_test));
                    startActivity(intent);
                } else if (fragment instanceof BrightnessFragment) {
                    intent.putExtra(getString(R.string.which_test), getString(R.string.brightness_test));
                    startActivity(intent);
                } else if (fragment instanceof TouchFragment) {
                    intent.putExtra(getString(R.string.which_test), getString(R.string.touch_test));
                    startActivity(intent);
                } else if (fragment instanceof MultiTouchFragment) {
                    intent.putExtra(getString(R.string.which_test), getString(R.string.multitouch_test));
                    startActivity(intent);
                } else if (fragment instanceof CameraFragment) {
                    fragmentNavigator.goTo(new SensorFragment());
                }  else if (fragment instanceof SensorFragment) {
                    fragmentNavigator.goTo(new CompassFragment());
                } else if (fragment instanceof CompassFragment) {
                    fragmentNavigator.goTo(new WifiFragment());
                }else if (fragment instanceof WifiFragment) {
                    fragmentNavigator.goTo(new BlueToothFragment());
                } else if (fragment instanceof LCDScreenFragment) {
                    fragmentNavigator.goTo(new LCDScreenFragment());
                } else if (fragment instanceof BlueToothFragment) {
                    fragmentNavigator.goTo(new BatteryFragment());
                }
            }
        }
    }

    public void toastResultNavigation() {
        if (DataUtil.whichTest) {
            Boast.makeText(this, Html.fromHtml(getResources().getString(R.string.message_show_hide_full))).show();
        } else {
            Boast.makeText(this, Html.fromHtml(getResources().getString(R.string.message_show_hide_single))).show();
        }
    }

    @Override
    public void initializeChildView() {

    }

    @Override
    public void initializeChildValue() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initFragment();
    }

    private void initFragment() {
        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), R.id.frameContainer);
        fragmentNavigator.setRootFragment(new NullFragment());
    }

    public void setMaxMusicVolume() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    @Override
    public void initializeChildAction() {
        butPass.setOnClickListener(this);
        butSkip.setOnClickListener(this);
        butFail.setOnClickListener(this);
        setUI();
    }

    private void setUI() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringExtra = extras.getString(getResources().getString(R.string.which_test));
        }
        setLayout(stringExtra);
    }

    @Override
    protected int getView() {
        return R.layout.activity_test;
    }

    /**
     * Switch fragment
     * @param fragment
     */
    public void switchFragment(Fragment fragment){
        fragmentNavigator.goTo(fragment);
    }

    /**
     * Clear data
     */
    public void clearData (){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(DATANAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Set data
     * @param data
     */
    private void setData(String data){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(DATANAME, MODE_PRIVATE);
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
}
