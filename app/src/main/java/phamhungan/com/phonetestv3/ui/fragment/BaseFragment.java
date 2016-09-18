package phamhungan.com.phonetestv3.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        context = getActivity();
        return inflater.inflate(getRootLayout(), container, false);
    }

    protected void setFullScreen(boolean fullScreen){
        TestActivity.instance.setFullScreen(fullScreen);
    }

    protected int getRootLayout(){
        return -1;
    }
}
