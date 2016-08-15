package phamhungan.com.phonetestv3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.appevents.AppEventsLogger;

import butterknife.BindView;
import phamhungan.com.phonetestv3.ui.ChooserActivity;
import phamhungan.com.phonetestv3.ui.MrAnActivity;
import phamhungan.com.phonetestv3.util.Preferences;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

public class Main extends MrAnActivity {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void showHideActionBar() {
        getSupportActionBar().hide();
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    @Override
    public void initializeChildView() {
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 3));
    }

    @Override
    public void initializeChildValue() {
    }

    @Override
    public void initializeChildAction() {
//        //Delay 1s
//        try
//        {
//            Thread.sleep(1000);
//        }
//        catch(Exception e){}

        //Delay 3s to load activity_choose
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Main.this, ChooserActivity.class);
                startActivity(in);
            }
        }, 800);
    }
}
