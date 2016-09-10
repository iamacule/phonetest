package phamhungan.com.phonetestv3.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.ui.toast.Boast;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 24-Jan-16.
 */
public class BrightnessFragment extends BaseFragment {
    private TextView txtMessage;
    private TextView txtMessage2;
    private ImageView butAgain;
    private ProgressBar pbBri;
    private Handler handler;
    private AtomicBoolean isRunning=new AtomicBoolean(false);
    private Boolean wait=false;
    private Thread thread;
    private WindowManager.LayoutParams oldAttributes;
    private WindowManager.LayoutParams newAttributes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
            TestActivity.instance.lnBottom.setVisibility(View.VISIBLE);
        } else {
            super.setHasOptionsMenu(true);
        }
        pbBri = (ProgressBar)view.findViewById(R.id.pbBri);
        txtMessage=(TextView)view.findViewById(R.id.txtMessage);
        txtMessage2=(TextView)view.findViewById(R.id.txtMessage2);
        butAgain = (ImageView)view.findViewById(R.id.butAgain);
        oldAttributes = getActivity().getWindow().getAttributes();
        butAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wait)
                {
                    action();
                    wait = false;
                }
                else
                    Boast.makeText(getActivity(), getActivity().getResources().getString(R.string.toast_brightness2)).show();
            }
        });
        action();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(DataUtil.whichTest){
            super.setHasOptionsMenu(false);
        }else {
            super.setHasOptionsMenu(true);
        }
    }

    private void action() {
        txtMessage2.setText(getString(R.string.toast_brightness));
        pbBri.setProgress(0);
        isRunning.set(false);
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //vòng lặp chạy 100 lần
                for(int i=1;i<=100 && isRunning.get();i++)
                {
                    SystemClock.sleep(100);
                    Message msg=handler.obtainMessage();
                    msg.arg1=i;
                    handler.sendMessage(msg);
                }
            }
        });
        isRunning.set(true);
        thread.start();
        handler=new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try{
                    pbBri.setProgress(msg.arg1);
                    if(msg.arg1==100)
                    {
                        wait = true;
                    }
                    newAttributes = getActivity().getWindow().getAttributes();
                    Float light = ((float)msg.arg1/100);
                    newAttributes.screenBrightness = light;
                    getActivity().getWindow().setAttributes(newAttributes);
                    txtMessage.setText("Brightness : " + msg.arg1 + "%");
                    if(msg.arg1==100){
                        txtMessage2.setText("");
                    }
                }catch (Exception e){

                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        if(thread.isAlive()){
            thread.interrupt();
        }
        getActivity().getWindow().setAttributes(oldAttributes);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_brightness_test;
    }
}
