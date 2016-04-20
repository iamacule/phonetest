package phamhungan.com.phonetestv3.ui.layout;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableLayout;

/**
 * Created by MrAn PC on 30-Jan-16.
 */
public class MyTableLayout extends TableLayout {
    public MyTableLayout(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
}
