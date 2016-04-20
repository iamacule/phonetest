package phamhungan.com.phonetestv3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.appevents.AppEventsLogger;

import phamhungan.com.phonetestv3.ui.ChooserActivity;
import phamhungan.com.phonetestv3.util.Preferences;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

public class Main extends AppCompatActivity {
    final Handler handler = new Handler();
    private ImageView imgLogo;
    public static Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 3));
        preferences = new Preferences(getApplicationContext());

        //Delay 1s
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e){}

        //Delay 3s to load activity_choose
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Main.this, ChooserActivity.class);
                startActivity(in);
            }
        }, 2000);
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
}
