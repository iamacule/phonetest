package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.adapter.SingleTestAdapter;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class SingleTest extends BaseFragment {
    private ListView listSingleTest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        listSingleTest = (ListView)view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        super.setHasOptionsMenu(true);
        setFullScreen(false);
        TestActivity.instance.lnBottom.setVisibility(View.GONE);
        listSingleTest.setAdapter(new SingleTestAdapter(DataUtil.getListItem(getActivity()),context, ScreenUtil.getScreenWidth(getActivity().getWindowManager())));
        setOnClickListView();
    }

    private void setOnClickListView() {
        listSingleTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScreenUtil.changeFragment(DataUtil.listFragment()[position],getFragmentManager());
            }
        });
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_single_test;
    }
}
