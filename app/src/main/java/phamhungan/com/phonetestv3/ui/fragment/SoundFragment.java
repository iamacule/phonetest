package phamhungan.com.phonetestv3.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 22-Jan-16.
 */
public class SoundFragment extends BaseFragment {
    private MediaPlayer mPlayertestSound;
    private ImageView imgSpeaker;
    private AnimationDrawable adspeaker;
    private TextView txtMessage;
    private Handler handler;
    private Thread thread;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    Button btnPlay;
    private boolean isPlaying = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if(DataUtil.whichTest){
            super.setHasOptionsMenu(false);
        }else {
            super.setHasOptionsMenu(true);
        }
        imgSpeaker = (ImageView)view.findViewById(R.id.imgSpeaker);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        bitmap1 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.speaker1), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) /2);
        bitmap2 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.speaker2), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        imgSpeaker.setImageBitmap(bitmap1);
        txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        ((TestActivity)getActivity()).setMaxMusicVolume();
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
                    txtMessage.setText(getActivity().getResources().getString(R.string.message_sound)+" "+msg.arg1+"");
                    if(msg.arg1==0){
                        txtMessage.setText("");
                        playSound();
                        playAnimation();
                        txtMessage.setText(getString(R.string.toast_sound));
                        isPlaying = true;
                        btnPlay.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){}
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying = false;
                    btnPlay.setText(getString(R.string.play));
                    txtMessage.setText(getString(R.string.play_again));
                    cleanUpSound();
                    stopAnimation();
                }else {
                    isPlaying = true;
                    btnPlay.setText(getString(R.string.stop));playSound();
                    playAnimation();
                    txtMessage.setText(getString(R.string.toast_sound));
                }
            }
        });
    }

    // Stop the sound and cleanup the media player
    public void cleanUpSound() {
        if (mPlayertestSound != null) {
            mPlayertestSound.stop();
            mPlayertestSound.release();
            mPlayertestSound = null;
        }
    }

    // Play the sound and start the timer
    private void playSound() {
        // Cleanup any previous sound files
        cleanUpSound();
        // Create a new media player instance and start it
        mPlayertestSound = MediaPlayer.create(getActivity(), Settings.System.DEFAULT_RINGTONE_URI);
        final AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mPlayertestSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayertestSound.start();
        mPlayertestSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(thread.isAlive()){
            thread.interrupt();
        }
        handler.removeMessages(0);
        cleanUpSound();
        stopAnimation();
    }

    private void playAnimation(){
        adspeaker= createAnimationDrawable();
        imgSpeaker.setImageDrawable(adspeaker);
        adspeaker.start();
    }

    private void stopAnimation(){
        if(adspeaker!=null){
            if(adspeaker.isRunning()){
                adspeaker.stop();
                adspeaker=null;
            }
        }
    }

    private AnimationDrawable createAnimationDrawable(){
        return ScreenUtil.createAnimationDrawable(new Bitmap[]{bitmap1,bitmap2},getActivity());
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_sound_test;
    }
}
