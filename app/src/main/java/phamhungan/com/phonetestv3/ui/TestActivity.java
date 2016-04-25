package phamhungan.com.phonetestv3.ui;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.fragment.BrightnessFragment;
import phamhungan.com.phonetestv3.ui.fragment.CameraFragment;
import phamhungan.com.phonetestv3.ui.fragment.FlashFragment;
import phamhungan.com.phonetestv3.ui.fragment.LCDScreenFragment;
import phamhungan.com.phonetestv3.ui.fragment.MicrophoneFragment;
import phamhungan.com.phonetestv3.ui.fragment.MultiTouchFragment;
import phamhungan.com.phonetestv3.ui.fragment.ResultFragment;
import phamhungan.com.phonetestv3.ui.fragment.SensorFragment;
import phamhungan.com.phonetestv3.ui.fragment.SingleTest;
import phamhungan.com.phonetestv3.ui.fragment.SoundFragment;
import phamhungan.com.phonetestv3.ui.fragment.TouchFragment;
import phamhungan.com.phonetestv3.util.AminationUtil;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private String stringExtra;
    private Button butPass;
    private Button butSkip;
    private Button butFail;
    public LinearLayout lnBottom;
    private boolean isShowLnBottom = false;
    public static TestActivity instance;
    private boolean isFullScreen = false;
    public static boolean isPermissionCameraGranted = false;
    public static boolean isPermissionRecordGranted = false;
    public static boolean isPermissionWriteStorageGranted = false;
    public static boolean isPermissionFlashGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        butPass = (Button) findViewById(R.id.butPass);
        butSkip = (Button) findViewById(R.id.butSkip);
        butFail = (Button) findViewById(R.id.butFail);
        butPass.setOnClickListener(this);
        butSkip.setOnClickListener(this);
        butFail.setOnClickListener(this);
        lnBottom = (LinearLayout) findViewById(R.id.lnBottom);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringExtra = extras.getString(getResources().getString(R.string.which_test));
        }
        setLayout(stringExtra);
        instance = this;
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
            super.onBackPressed();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CameraFragment.MY_REQUEST_CAMERA_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isPermissionCameraGranted = true;

                } else {
                    isPermissionCameraGranted = false;
                }
                break;
            }

            case MicrophoneFragment.MY_REQUEST_RECORD_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isPermissionRecordGranted = true;

                } else {
                    isPermissionRecordGranted = false;
                }
                break;
            }

            case MicrophoneFragment.MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isPermissionWriteStorageGranted = true;

                } else {
                    isPermissionWriteStorageGranted = false;
                }
                break;
            }
            case FlashFragment.MY_REQUEST_FLASH_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isPermissionFlashGranted = true;

                } else {
                    isPermissionFlashGranted = false;
                }
                break;
            }
            default:
                isPermissionCameraGranted = false;
                isPermissionRecordGranted = false;
                isPermissionWriteStorageGranted = false;
                isPermissionFlashGranted = false;
                break;
        }
    }

    public void toastResultNavigation() {
        if (DataUtil.whichTest) {
            Toast.makeText(this, Html.fromHtml(getResources().getString(R.string.message_show_hide_full)), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, Html.fromHtml(getResources().getString(R.string.message_show_hide_single)), Toast.LENGTH_LONG).show();
        }
    }
}
