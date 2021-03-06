package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 24-Jan-16.
 */
public class TouchFragment extends BaseFragment {
    private ImageView btnInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
        } else {
            super.setHasOptionsMenu(true);
        }
        btnInfo = (ImageView) view.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRootActivity().toastResultNavigation();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFullScreen(true);
        getRootActivity().lnBottom.setVisibility(View.GONE);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_touch_test;
    }
}
