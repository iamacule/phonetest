package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.canvas.DrawTouch;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 24-Jan-16.
 */
public class TouchFragment extends BaseFragment {
    private LinearLayout lnMain;
    private LinearLayout.LayoutParams layoutParams;
    private DrawTouch drawTouch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        lnMain = (LinearLayout)view.findViewById(R.id.lnMain);
        drawTouch = new DrawTouch(getActivity().getApplicationContext());
        lnMain.addView(drawTouch);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFullScreen(true);
        TestActivity.instance.lnBottom.setVisibility(View.GONE);
        layoutParams= (LinearLayout.LayoutParams) lnMain.getLayoutParams();
        layoutParams.weight= ScreenUtil.getScreenHeight(getActivity().getWindowManager());
        lnMain.setLayoutParams(layoutParams);
        TestActivity.instance.toastResultNavigation();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_lcd_test;
    }
}
