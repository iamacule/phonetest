package phamhungan.com.phonetestv3.inappbilling.util;

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
