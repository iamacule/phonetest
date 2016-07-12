package phamhungan.com.phonetestv3.util;

import android.content.Context;
import android.os.Bundle;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import phamhungan.com.phonetestv3.R;

/**
 * Created by MrAn on 12-Jul-16.
 */
public class AdUtil implements RewardedVideoAdListener {
    private String AD_UNIT_ID;
    private String APP_ID;

    private final Object mLock = new Object();

    private boolean mIsRewardedVideoLoading;
    private RewardedVideoAd mRewardedVideoAd;

    public AdUtil(Context context){
        // Get an app ID
        AD_UNIT_ID = context.getString(R.string.AD_UNIT_ID);
        APP_ID = context.getString(R.string.APP_ID);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(context, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(AdUtil.this);
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
