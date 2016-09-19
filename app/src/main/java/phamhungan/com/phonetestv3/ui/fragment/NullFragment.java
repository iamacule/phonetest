package phamhungan.com.phonetestv3.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phamhungan.com.phonetestv3.R;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class NullFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.layout_null, container, false);
    }
}
