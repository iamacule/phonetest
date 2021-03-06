package phamhungan.com.phonetestv3.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogInfo;
import phamhungan.com.phonetestv3.util.DialogUtil;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by MrAn PC on 20-Jan-16.
 */
public class ChooserActivity extends MrAnActivity implements View.OnClickListener {
    @BindView(R.id.butInfo)
    Button butInfo;
    @BindView(R.id.butSingle)
    Button butSingle;
    @BindView(R.id.butFull)
    Button butFull;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    private ChooserActivity context;
    private String stringExtra;
    private final String TAG = "PhoneTest";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needShowAds()){
            DataUtil.countToLoadAd++;
            loadRewardedVideoAd();
            checkToLoadAd();
        }
        checkExtra();
        setOnClick();
        checkPhoneTestPermission();
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

    private void checkPhoneTestPermission() {
        PermissionUtil.isPermissionCameraGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ? true : false;
        PermissionUtil.isPermissionRecordGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED ? true : false;
        PermissionUtil.isPermissionWriteStorageGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ? true : false;
        PermissionUtil.isPermissionReadPhoneStateGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ? true : false;
        Log.d(TAG, "CAMERA : " + PermissionUtil.isPermissionCameraGranted);
        Log.d(TAG, "RECORD_AUDIO : " + PermissionUtil.isPermissionRecordGranted);
        Log.d(TAG, "WRITE_EXTERNAL_STORAGE : " + PermissionUtil.isPermissionWriteStorageGranted);
        Log.d(TAG, "READ_PHONE_STATE : " + PermissionUtil.isPermissionReadPhoneStateGranted);
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
    public void onBackPressed() {
        try {
            if (!checkRating())
                DialogUtil.showRatingDialog(this);
            else
                moveTaskToBack(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.butInfo:
                if (PermissionUtil.isPermissionReadPhoneStateGranted) {
                    intent = new Intent(ChooserActivity.this, PhoneInfoActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    showDialogAskPermission(getString(R.string.permission_phone_ask), Manifest.permission.READ_PHONE_STATE,
                            PermissionUtil.MY_REQUEST_READ_PHONE_STATE_PERMISSION_CODE);
                }
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

    @Override
    public void initializeChildView() {
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 4));
    }

    @Override
    public void initializeChildValue() {
        context = this;
    }

    @Override
    public void initializeChildAction() {
        if(preferences.getAds()){
            initAd();
            loadAdsMob();
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_chooser;
    }
}
