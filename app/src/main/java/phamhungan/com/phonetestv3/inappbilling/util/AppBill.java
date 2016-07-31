package phamhungan.com.phonetestv3.inappbilling.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import phamhungan.com.phonetestv3.R;

/**
 * Created by Mr An on 31/07/2016.
 */
public class AppBill {
    private final String TAG = "AppBill";
    private Context context;
    private static AppBill instance;
    private IabHelper mHelper;
    private final String ITEM = "Remove Ads";
    private final int APP_BILL_REQUEST_CODE = 0;

    private AppBill(Context context) {
        this.context = context;
        startSetupMHelper();
    }


    private void startSetupMHelper() {
        mHelper = new IabHelper(context, context.getString(R.string.base64EncodedPublicKey));
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                } else {
                    Log.d(TAG, "Setup success: " + result);
                }
            }
        });
    }

    public static AppBill getInstance(Context context) {
        if (instance == null)
            instance = new AppBill(context);
        return instance;
    }

    public void removeAds(){
        try {
            mHelper.launchPurchaseFlow((Activity)context, ITEM, APP_BILL_REQUEST_CODE,
                    mPurchaseFinishedListener, "");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            else if (purchase.getSku().equals(ITEM)) {
                Log.d(TAG, "Purchase success: " + result);
            }
        }
    };

    public void destroy() {
        try {
            if (mHelper != null) mHelper.dispose();
            mHelper = null;
            instance = null;
        } catch (Exception e) {
            Log.d(TAG, "Can not destroy");
        }
    }
}
