package phamhungan.com.phonetestv3.ui.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 02-Feb-16.
 */
public class MyTitleTextView extends TextView {
    public MyTitleTextView(Context context, String value) {
        super(context);
        this.setText(value);
        this.setPadding(ScreenUtil.convertDpToPixel(5, context), ScreenUtil.convertDpToPixel(5,context), 0, ScreenUtil.convertDpToPixel(5,context));
        this.setTypeface(null, Typeface.BOLD);
        this.setTextColor(Color.WHITE);
        this.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);
    }

    public void setValue(String value){
        this.setText(value);
    }
}
