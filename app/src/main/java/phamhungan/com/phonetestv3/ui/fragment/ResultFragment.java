package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.adapter.ResultAdapter;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 27-Jan-16.
 */
public class ResultFragment extends BaseFragment {
    private ListView listResult;
    private String[] resultArray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        listResult = (ListView)view.findViewById(R.id.listView);
        StringBuilder stringBuilder = new StringBuilder(EventUtil.getData(getActivity()));
        stringBuilder.deleteCharAt(0);
        resultArray = stringBuilder.toString().split(" ");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        super.setHasOptionsMenu(true);
        TestActivity.instance.lnBottom.setVisibility(View.GONE);
        listResult.setAdapter(new ResultAdapter(DataUtil.getListItem(getActivity()),resultArray, context, ScreenUtil.getScreenWidth(getActivity().getWindowManager())));
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_single_test;
    }
}
