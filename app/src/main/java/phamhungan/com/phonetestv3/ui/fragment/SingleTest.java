package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import phamhungan.com.phonetestv3.R;
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
        getRootActivity().lnBottom.setVisibility(View.GONE);
        listSingleTest.setAdapter(new SingleTestAdapter(DataUtil.getListItem(getActivity()),getRootActivity(), ScreenUtil.getScreenWidth(getActivity().getWindowManager())));
        setOnClickListView();
    }

    private void setOnClickListView() {
        listSingleTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new SoundFragment();
                        break;
                    case 1:
                        fragment = new VibrateFragment();
                        break;
                    case 2:
                        fragment = new MicrophoneFragment();
                        break;
                    case 3:
                        fragment = new LCDScreenFragment();
                        break;
                    case 4:
                        fragment = new BrightnessFragment();
                        break;
                    case 5:
                        fragment = new TouchFragment();
                        break;
                    case 6:
                        fragment = new MultiTouchFragment();
                        break;
                    case 7:
                        fragment = new CameraFragment();
                        break;
                    case 8:
                        fragment = new SensorFragment();
                        break;
                    case 9:
                        fragment = new CompassFragment();
                        break;
                    case 10:
                        fragment = new WifiFragment();
                        break;
                    case 11:
                        fragment = new BlueToothFragment();
                        break;
                    case 12:
                        fragment = new BatteryFragment();
                        break;
                }
                getRootActivity().switchFragment(fragment);
            }
        });
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_single_test;
    }
}
