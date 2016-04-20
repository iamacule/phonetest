package phamhungan.com.phonetestv3.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 24-Jan-16.
 */
public class LCDScreenFragment extends BaseFragment {
    private LinearLayout lnLCD;
    private LinearLayout.LayoutParams layoutParams;
    private int position=0;
    private int[] colorArray = {Color.RED,Color.GREEN,Color.BLUE,Color.WHITE,Color.YELLOW,Color.BLACK};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        lnLCD = (LinearLayout)view.findViewById(R.id.lnMain);
        lnLCD.setBackgroundColor(colorArray[position]);
        lnLCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position == 5)
                    position = 0;
                lnLCD.setBackgroundColor(colorArray[position]);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFullScreen(true);
        TestActivity.instance.lnBottom.setVisibility(View.GONE);
        layoutParams= (LinearLayout.LayoutParams) lnLCD.getLayoutParams();
        layoutParams.weight= ScreenUtil.getScreenHeight(getActivity().getWindowManager());
        lnLCD.setLayoutParams(layoutParams);
        TestActivity.instance.toastResultNavigation();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_lcd_test;
    }
}
