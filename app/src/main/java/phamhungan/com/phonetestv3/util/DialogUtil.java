package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import phamhungan.com.phonetestv3.Main;
import phamhungan.com.phonetestv3.R;

/**
 * Created by MrAn PC on 17-Feb-16.
 */
public class DialogUtil {
    public static void showRatingDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.rating_dialog);
        dialog.setTitle("Rating");

        RatingBar ratingBar = (RatingBar)dialog.findViewById(R.id.ratingBar);
        ratingBar.setRating(5);
        Button btnRate = (Button) dialog.findViewById(R.id.btnRate);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getResources().getString(R.string.url_market))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getResources().getString(R.string.url))));
                }
                Main.preferences.storeData(Main.preferences.RATING_KEY,true);
            }
        });

        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Main.preferences.storeData(Main.preferences.RATING_KEY, false);
                EventUtil.backPressExitApp(activity);
            }
        });

        dialog.show();
    }


}
