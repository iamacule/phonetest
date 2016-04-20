package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import phamhungan.com.phonetestv3.R;

/**
 * Created by MrAn PC on 27-Jan-16.
 */
public class AminationUtil {

    public static Animation sliceOutToBottom(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_out_top_to_bottom);
    }

    public static Animation sliceInToTop(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_in_bottom_to_top);
    }

    public static Animation sliceOutToLeft(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_out_right_to_left);
    }

    public static Animation sliceOutToRight(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_out_left_to_right);
    }

    public static Animation sliceInToLeft(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_in_right_to_left);
    }

    public static Animation sliceInToRight(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.slide_in_left_to_right);
    }

    public static Animation fadeOut(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), android.R.anim.fade_out);
    }

    public static Animation fadeIn(Activity activity){
        return AnimationUtils.loadAnimation(activity.getApplicationContext(), android.R.anim.fade_in);
    }
}
