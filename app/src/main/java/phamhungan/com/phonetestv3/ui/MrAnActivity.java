package phamhungan.com.phonetestv3.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import butterknife.ButterKnife;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn on 08-Jun-16.
 */
public abstract class MrAnActivity extends AppCompatActivity implements InitializeView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHideActionBar();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getView());
        ButterKnife.bind(this);
        initializeChildView();
        initializeChildValue();
        initializeChildAction();
    }

    protected int getView(){
        return -1;
    }

    protected void showHideActionBar(){
        getSupportActionBar().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemShare:
                ScreenUtil.showSharedialog(this);
                break;
            case R.id.itemAbout:
                ScreenUtil.goAbout(this);
                break;
        }
        return true;
    }

}
