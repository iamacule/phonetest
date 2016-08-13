package phamhungan.com.phonetestv3.ui;

import android.app.Dialog;
import android.content.Intent;
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
import phamhungan.com.phonetestv3.Main;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.inappbilling.util.IabHelper;
import phamhungan.com.phonetestv3.inappbilling.util.IabResult;
import phamhungan.com.phonetestv3.inappbilling.util.Purchase;
import phamhungan.com.phonetestv3.util.DialogAsk;
import phamhungan.com.phonetestv3.util.DialogInfo;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.Preferences;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn on 08-Jun-16.
 */
public abstract class MrAnActivity extends AppCompatActivity implements InitializeView{
    public PermissionUtil permissionUtil;
    private final String TAG = "AppBill";
    private IabHelper mHelper;
    private final String ITEM = "phamhungan.com.phonetestv3.removeads";
    private final int APP_BILL_REQUEST_CODE = 10102;
    public Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHideActionBar();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getView());
        ButterKnife.bind(this);
        initBaseValue();
        initAppBill();
        initializeChildView();
        initializeChildValue();
        initializeChildAction();
    }

    protected boolean checkRating() {
        return preferences.getRating();
    }

    private void initAppBill() {
        startSetupMHelper();
    }

    private void initBaseValue() {
        permissionUtil = new PermissionUtil(this);
        preferences = new Preferences(this);
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
        destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        showHideRemoveAds(menu);
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
                removeAds();
                break;
        }
        return true;
    }

    private void showHideRemoveAds(Menu menu) {
        for (int i = 0; i < menu.size(); i++){
            if(menu.getItem(i).getItemId()==R.id.removeAds){
                if(!needShowAds()){
                    menu.getItem(i).setVisible(false);
                    break;
                }
            }
        }
    }

    protected boolean needShowAds(){
        return preferences.getAds();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener =
            new IabHelper.OnIabPurchaseFinishedListener() {
                public void onIabPurchaseFinished(IabResult result, Purchase purchase)
                {
                    Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
                    if (result.isFailure()) {
                        Log.d(TAG, "Error purchasing: " + result);
                        if(result.getResponse()==7) //Item Already Owned
                        {
                            Log.d(TAG,"Item Already Owned . Need remove ads now !");
                            disableAds();
                        }
                        return;
                    }
                    else if (purchase.getSku().equals(ITEM)) {
                        Log.d(TAG, "Purchase success: " + result);
                        disableAds();
                    }
                }
            };

    private void startSetupMHelper() {
        mHelper = new IabHelper(this, getString(R.string.base64EncodedPublicKey));
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (mHelper == null) return;
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                } else {
                    Log.d(TAG, "Setup success: " + result);
                }
            }
        });
    }

    public void removeAds(){
        try {
            mHelper.launchPurchaseFlow(this, ITEM, APP_BILL_REQUEST_CODE,
                    onIabPurchaseFinishedListener, "");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            Log.d(TAG, "Destroying helper.");

            if (mHelper != null) {
                mHelper.disposeWhenFinished();
                mHelper = null;
            }
        } catch (Exception e) {
            Log.d(TAG, "Can not destroy");
        }
    }

    private void disableAds(){
        preferences.storeData(preferences.ADS_KEY,false);
        Log.d(TAG,"Store value success : " + needShowAds());
        DialogInfo.Build dialog = new DialogInfo.Build(this);
        dialog.setMessage(getString(R.string.remove_ads_success));
        dialog.setButton(getString(R.string.ok));
        dialog.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(MrAnActivity.this, Main.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            }
        });
        dialog.show();
    }
}
