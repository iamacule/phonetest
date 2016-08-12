package phamhungan.com.phonetestv3.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import phamhungan.com.phonetestv3.Main;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.AdUtil;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogAsk;
import phamhungan.com.phonetestv3.util.DialogInfo;
import phamhungan.com.phonetestv3.util.DialogUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * Created by MrAn PC on 20-Jan-16.
 */
public class ChooserActivity extends MrAnActivity implements View.OnClickListener,RewardedVideoAdListener {
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
    private String AD_UNIT_ID;
    private String APP_ID;

    private final Object mLock = new Object();

    private boolean mIsRewardedVideoLoading;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initAd(){
        // Get an app ID
        AD_UNIT_ID = context.getString(R.string.AD_UNIT_ID);
        APP_ID = context.getString(R.string.APP_ID);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataUtil.countToLoadAd++;
        Log.d(TAG,"Count to load ad : "+DataUtil.countToLoadAd);
        if(needShowAds()){
            loadRewardedVideoAd();
            checkToLoadAd();
        }
        checkExtra();
        setOnClick();
        checkPhoneTestPermission();
    }

    private void checkToLoadAd() {
        if(DataUtil.countToLoadAd>=5){
            DataUtil.countToLoadAd=0;
            showRewardedVideo();
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
                EventUtil.backPressExitApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkRating() {
        return preferences.getRating();
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

    public void loadRewardedVideoAd() {
        synchronized (mLock) {
            if (!mIsRewardedVideoLoading && !mRewardedVideoAd.isLoaded()) {
                mIsRewardedVideoLoading = true;
                Bundle extras = new Bundle();
                extras.putBoolean("_noRefresh", true);
                AdRequest adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
                mRewardedVideoAd.loadAd(AD_UNIT_ID, adRequest);
            }
        }
    }

    public void showRewardedVideo() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        mIsRewardedVideoLoading = false;
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        mIsRewardedVideoLoading = false;
    }
}
