package phamhungan.com.phonetestv3.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 23-Jan-16.
 */
public class VibrateFragment extends BaseFragment {
    private Vibrator vibrator;
    private ImageView imgVibrate;
    private AnimationDrawable adspeaker;
    private TextView txtMessage;
    private Handler handler;
    private Thread thread;
    private Bitmap bp1;
    private boolean isStarting = false;
    private Button btnStart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if(DataUtil.whichTest){
            super.setHasOptionsMenu(false);
            TestActivity.instance.lnBottom.setVisibility(View.VISIBLE);
        }else {
            super.setHasOptionsMenu(true);
        }
        imgVibrate = (ImageView)view.findViewById(R.id.imgVibrate);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        bp1 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate1), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        imgVibrate.setImageBitmap(bp1);
        txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        action();
        return view;
    }

    private void action() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=3;i>=0;i--){
                    SystemClock.sleep(1000);
                    Message msg=handler.obtainMessage();
                    msg.arg1=i;
                    handler.sendMessage(msg);
                }
            }
        });
        thread.start();
        handler=new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try{
                    txtMessage.setText(getActivity().getResources().getString(R.string.message_vibrate)+" "+msg.arg1+"");
                    if(msg.arg1==0){
                        playAnimation();
                        vibrate();
                        txtMessage.setText(getString(R.string.toast_vibrate));
                        btnStart.setVisibility(View.VISIBLE);
                        isStarting = true;
                    }
                }catch (Exception e){}
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStarting){
                    isStarting = false;
                    btnStart.setText(getString(R.string.start_small));
                    txtMessage.setText(getString(R.string.start_again));
                    stopAnimation();
                    stopVibrate();
                }else {
                    isStarting = true;
                    btnStart.setText(getString(R.string.stop));
                    playAnimation();
                    vibrate();
                    txtMessage.setText(getString(R.string.toast_vibrate));
                }
            }
        });
    }

    private void playAnimation() {
        adspeaker = createAnimationDrawable();
        imgVibrate.setImageDrawable(adspeaker);
        adspeaker.start();
    }

    private AnimationDrawable createAnimationDrawable() {
        Bitmap bp2 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate2), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp3 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate3), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp4 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate4), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp5 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate5), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp6 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate6), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp7 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate7), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        Bitmap bp8 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.vibrate8), ScreenUtil.getScreenWidth(getActivity().getWindowManager())/2);
        return ScreenUtil.createAnimationDrawable(new Bitmap[]{bp1,bp2,bp3,bp4,bp5,bp6,bp7,bp8},getActivity());
    }

    private void vibrate() {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long []{0, 100, 200,300,400,500,600,700,800,900,1000}, 1);
    }

    private void stopAnimation(){
        if(adspeaker!=null){
            if(adspeaker.isRunning()){
                adspeaker.stop();
                adspeaker=null;
            }
        }
    }

    private void stopVibrate()
    {
        if(vibrator!=null){
            if(vibrator.hasVibrator()){
                vibrator.cancel();
                vibrator=null;
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(thread.isAlive()){
            thread.interrupt();
        }
        stopAnimation();
        stopVibrate();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_vibrate_test;
    }
}
