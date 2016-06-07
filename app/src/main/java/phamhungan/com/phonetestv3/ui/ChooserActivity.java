package phamhungan.com.phonetestv3.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import phamhungan.com.phonetestv3.Main;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogAsk;
import phamhungan.com.phonetestv3.util.DialogInfo;
import phamhungan.com.phonetestv3.util.DialogUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

import com.facebook.FacebookSdk;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by MrAn PC on 20-Jan-16.
 */
public class ChooserActivity extends AppCompatActivity implements View.OnClickListener {
    private Button butInfo;
    private Button butSingle;
    private Button butFull;
    private ChooserActivity context;
    private String stringExtra;
    private ImageView imgLogo;
    private RelativeLayout main;
    private final String TAG = "PhoneTest";
    private PermissionUtil permissionUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_chooser);
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPhoneTestPermission();
        if (!PermissionUtil.isPermissionCameraGranted) {
            showDialogAskPermission(getString(R.string.permission_camera_ask),
                    Manifest.permission.CAMERA, PermissionUtil.MY_REQUEST_CAMERA_PERMISSION_CODE);
        } else if (!PermissionUtil.isPermissionRecordGranted) {
            showDialogAskPermission(getString(R.string.permission_record_ask),
                    Manifest.permission.RECORD_AUDIO, PermissionUtil.MY_REQUEST_RECORD_PERMISSION_CODE);
        } else if (!PermissionUtil.isPermissionWriteStorageGranted) {
            showDialogAskPermission(getString(R.string.permission_write_ask),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionUtil.MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE);
        } else {
            checkExtra();
            setOnClick();
        }
    }

    private void checkExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringExtra = extras.getString(getResources().getString(R.string.which_test));
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra(getResources().getString(R.string.which_test), stringExtra);
            startActivity(intent);
        }
    }

    private void initLayout() {
        context = this;
        permissionUtil = new PermissionUtil(this);
        butInfo = (Button) findViewById(R.id.butInfo);
        butSingle = (Button) findViewById(R.id.butSingle);
        butFull = (Button) findViewById(R.id.butFull);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        main = (RelativeLayout) findViewById(R.id.main);
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 4));
        loadAdsMob();
    }

    private void checkPhoneTestPermission() {
        PermissionUtil.isPermissionCameraGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ? true : false;
        PermissionUtil.isPermissionRecordGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED ? true : false;
        PermissionUtil.isPermissionWriteStorageGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ? true : false;
        Log.d(TAG, "CAMERA : " + PermissionUtil.isPermissionCameraGranted);
        Log.d(TAG, "RECORD_AUDIO : " + PermissionUtil.isPermissionRecordGranted);
        Log.d(TAG, "WRITE_EXTERNAL_STORAGE : " + PermissionUtil.isPermissionWriteStorageGranted);
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
                } else {
                    PermissionUtil.isPermissionCameraGranted = false;
                    EventUtil.backPressExitApp(ChooserActivity.this);
                }
                return;
            }
            case PermissionUtil.MY_REQUEST_RECORD_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionRecordGranted = true;
                } else {
                    PermissionUtil.isPermissionRecordGranted = false;
                    EventUtil.backPressExitApp(ChooserActivity.this);
                }
                return;
            }
            case PermissionUtil.MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionUtil.isPermissionWriteStorageGranted = true;
                } else {
                    PermissionUtil.isPermissionWriteStorageGranted = false;
                    EventUtil.backPressExitApp(ChooserActivity.this);
                }
                return;
            }
        }
    }

    private void loadAdsMob() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Log.d("Google AdsMob", "Started");
    }

    private void setOnClick() {
        butInfo.setOnClickListener(this);
        butSingle.setOnClickListener(this);
        butFull.setOnClickListener(this);
    }

    public void showDialogOK(String s) {
        DialogInfo.Build dialog = new DialogInfo.Build(this);
        dialog.setMessage(s)
                .setButton(getString(R.string.start))
                .getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtil.whichTest = true;
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra(getResources().getString(R.string.which_test), getResources().getString(R.string.full_test));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        dialog.show();
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
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!checkRating())
            DialogUtil.showRatingDialog(this);
        else
            EventUtil.backPressExitApp(this);
    }

    private boolean checkRating() {
        return Main.preferences.getRating();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.butInfo:
                intent = new Intent(this, PhoneInfoActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.butSingle:
                DataUtil.whichTest = false;
                intent = new Intent(this, TestActivity.class);
                intent.putExtra(getResources().getString(R.string.which_test), getResources().getString(R.string.single_test));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.butFull:
                showDialogOK(Html.fromHtml(getResources().getString(R.string.notice_full_test)).toString());
                break;
        }
    }


    private void showDialogAskPermission(final String message, final String permission, final int idCallBack) {
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
                EventUtil.backPressExitApp(ChooserActivity.this);
            }
        });
        dialog.show();
    }
}
