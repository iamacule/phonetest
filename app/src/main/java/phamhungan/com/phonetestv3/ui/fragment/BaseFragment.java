package phamhungan.com.phonetestv3.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phamhungan.com.phonetestv3.ui.TestActivity;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(getRootLayout(), container, false);
    }

    protected void setFullScreen(boolean fullScreen){
        getRootActivity().setFullScreen(fullScreen);
    }

    protected TestActivity getRootActivity(){
        return ((TestActivity)getActivity());
    }

    protected int getRootLayout(){
        return -1;
    }
}
