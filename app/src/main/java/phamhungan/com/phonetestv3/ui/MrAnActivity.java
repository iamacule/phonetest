package phamhungan.com.phonetestv3.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import butterknife.ButterKnife;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.inappbilling.util.AppBill;
import phamhungan.com.phonetestv3.util.AdUtil;
import phamhungan.com.phonetestv3.util.DialogAsk;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn on 08-Jun-16.
 */
public abstract class MrAnActivity extends AppCompatActivity implements InitializeView{
    public PermissionUtil permissionUtil;
    private AppBill appBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHideActionBar();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getView());
        ButterKnife.bind(this);
        initializeChildView();
        initializeChildValue();
        initializeChildAction();
        initBaseValue();
        initAppBill();
    }

    private void initAppBill() {
        appBill = AppBill.getInstance(this);
    }

    private void initBaseValue() {
        permissionUtil = new PermissionUtil(this);
    }

    protected int getView() {
        return -1;
    }

    protected void showHideActionBar() {
        getSupportActionBar().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBill.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemShare:
                ScreenUtil.showSharedialog(this);
                break;
            case R.id.itemAbout:
                ScreenUtil.goAbout(this);
                break;
            case R.id.removeAds:
                //Earn money here !
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtil.MY_REQUEST_CAMERA_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionCameraGranted = true;
                }
                return;
            }
            case PermissionUtil.MY_REQUEST_RECORD_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionRecordGranted = true;
                }
                return;
            }
            case PermissionUtil.MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionWriteStorageGranted = true;
                }
                return;
            }
            case PermissionUtil.MY_REQUEST_READ_PHONE_STATE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionReadPhoneStateGranted = true;
                }
                return;
            }
        }
    }


    public void showDialogAskPermission(final String message, final String permission, final int idCallBack) {
        final DialogAsk.Build dialog = new DialogAsk.Build(this);
        dialog.setMessage(message);
        dialog.getPositiveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                permissionUtil.checkPermission(permission, idCallBack);
            }
        });
        dialog.getNegativeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
