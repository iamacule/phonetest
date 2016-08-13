package phamhungan.com.phonetestv3.ui.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 30-Jan-16.
 */
public class MyTableRow extends TableRow {
    private TextView txtName;
    private TextView txtValue;
    private String unit;

    public MyTableRow(Context context, String name, String value, String unit) {
        super(context);
        this.unit = unit;
        setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.setBackground(getResources().getDrawable(R.drawable.bolder_bottom));
        this.setPadding(0, 0, 0, ScreenUtil.convertDpToPixel(1, context));

        txtName = new TextView(context);
        txtName.setText(name);
        txtName.setTextColor(Color.BLUE);
        txtName.setGravity(Gravity.RIGHT);
        txtName.setTypeface(null, Typeface.BOLD);
        txtName.setPadding(0, 0, ScreenUtil.convertDpToPixel(5, context), 0);

        TableRow.LayoutParams p1 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f);
        txtName.setLayoutParams(p1);

        txtValue = new TextView(context);
        txtValue.setText(value + " " + unit);
        txtValue.setTextColor(Color.BLUE);
        txtValue.setGravity(Gravity.LEFT);
        txtValue.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
        txtValue.setPadding(ScreenUtil.convertDpToPixel(5, context), ScreenUtil.convertDpToPixel(10, context), 0, ScreenUtil.convertDpToPixel(10, context));

        TableRow.LayoutParams p2 = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f);
        txtValue.setLayoutParams(p2);

        this.addView(txtName);
        this.addView(txtValue);
    }

    public void setValue(String newValue) {
        txtValue.setText(newValue + " " + unit);
    }

    public TextView getValue (){
        return txtValue;
    }
}
