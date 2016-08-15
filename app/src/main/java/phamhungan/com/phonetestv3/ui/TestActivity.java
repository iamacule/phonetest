package phamhungan.com.phonetestv3.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.fragment.BrightnessFragment;
import phamhungan.com.phonetestv3.ui.fragment.CameraFragment;
import phamhungan.com.phonetestv3.ui.fragment.LCDScreenFragment;
import phamhungan.com.phonetestv3.ui.fragment.MultiTouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.ResultFragment;
import phamhungan.com.phonetestv3.ui.fragment.SensorFragment;
import phamhungan.com.phonetestv3.ui.fragment.SingleTest;
import phamhungan.com.phonetestv3.ui.fragment.SoundFragment;
import phamhungan.com.phonetestv3.ui.fragment.TouchFragment;
import phamhungan.com.phonetestv3.ui.toast.Boast;
import phamhungan.com.phonetestv3.util.AminationUtil;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

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
    private AudioManager audioManager;

    private String stringExtra;
    private boolean isShowLnBottom = false;
    public static TestActivity instance;
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringExtra = extras.getString(getResources().getString(R.string.which_test));
        }
        setLayout(stringExtra);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.clearData(this);
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
        Fragment fr = getFragmentManager().findFragmentById(R.id.mainFragment);
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
        Fragment fr = getFragmentManager().findFragmentById(R.id.mainFragment);
        if (fr instanceof LCDScreenFragment ||
                fr instanceof TouchFragment ||
                fr instanceof MultiTouchFragment)
            return true;
        else return false;
    }

    private boolean acceptSwitchLnBottom() {
        Fragment fr = getFragmentManager().findFragmentById(R.id.mainFragment);
        if (fr instanceof LCDScreenFragment ||
                fr instanceof TouchFragment ||
                fr instanceof MultiTouchFragment ||
                fr instanceof SensorFragment)
            return true;
        else return false;
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        instance.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        instance.getWindow().getDecorView().setAnimation(AminationUtil.fadeOut(this));
    }

    private void showSystemUI() {
        instance.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        instance.getWindow().getDecorView().setAnimation(AminationUtil.fadeIn(this));
    }

    private void setLayout(String stringExtra) {
        if (stringExtra.equals(getResources().getString(R.string.single_test))) {
            EventUtil.clearData(this);
            lnBottom.setVisibility(View.GONE);
            isShowLnBottom = false;
            ScreenUtil.changeFragment(new SingleTest(), getFragmentManager());
        } else if (stringExtra.equals(getResources().getString(R.string.lcd_test))) {
            ScreenUtil.changeFragment(new BrightnessFragment(), getFragmentManager());
        } else if (stringExtra.equals(getResources().getString(R.string.brightness_test))) {
            ScreenUtil.changeFragment(new TouchFragment(), getFragmentManager());
        } else if (stringExtra.equals(getResources().getString(R.string.touch_test))) {
            ScreenUtil.changeFragment(new MultiTouchFragment(), getFragmentManager());
        } else if (stringExtra.equals(getResources().getString(R.string.multitouch_test))) {
            ScreenUtil.changeFragment(new CameraFragment(), getFragmentManager());
        } else {
            EventUtil.clearData(this);
            lnBottom.setVisibility(View.VISIBLE);
            isShowLnBottom = true;
            ScreenUtil.changeFragment(new SoundFragment(), getFragmentManager());
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fr = getFragmentManager().findFragmentById(R.id.mainFragment);
        if (fr instanceof SingleTest) {
            Intent i = new Intent(this, ChooserActivity.class);
            startActivity(i);
        } else {
            EventUtil.onBackPress(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butPass:
                EventUtil.onPressButtonFulltest(this, DataUtil.pass);
                break;
            case R.id.butSkip:
                EventUtil.onPressButtonFulltest(this, DataUtil.skip);
                break;
            case R.id.butFail:
                EventUtil.onPressButtonFulltest(this, DataUtil.fail);
                break;
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
        instance = this;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    public void setMaxMusicVolume() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    @Override
    public void initializeChildAction() {
        butPass.setOnClickListener(this);
        butSkip.setOnClickListener(this);
        butFail.setOnClickListener(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_test;
    }
}
