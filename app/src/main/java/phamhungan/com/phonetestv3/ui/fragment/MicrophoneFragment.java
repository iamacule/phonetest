package phamhungan.com.phonetestv3.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 23-Jan-16.
 */
public class MicrophoneFragment extends BaseFragment {
    private Button butRecord, butStop, butPlay;
    private TextView txtMessage;
    private ImageView imgMicro;
    private ImageView imgRefresh;
    private MediaPlayer mediaPlayer;
    private MediaRecorder myAudioRecorder;
    private AnimationDrawable animationDrawable;
    private File cacheDirectory, file;
    private Bitmap bp1;
    private Bitmap bp2;
    private Bitmap bp3;
    private Bitmap bp4;
    private Bitmap bp5;

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
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        imgMicro = (ImageView) view.findViewById(R.id.imgMicro);
        imgRefresh = (ImageView) view.findViewById(R.id.imgRefresh);
        butRecord = (Button) view.findViewById(R.id.butRecord);
        butStop = (Button) view.findViewById(R.id.butStop);
        butPlay = (Button) view.findViewById(R.id.butPlay);

        txtMessage.setText(getActivity().getResources().getString(R.string.press_record));
        ((TestActivity)getActivity()).setMaxMusicVolume();
        action();
        imgRefresh.setVisibility(View.INVISIBLE);
        imgRefresh.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.again), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 8));
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMessage.setText(getActivity().getResources().getString(R.string.press_record));
                createFile();
                action();
            }
        });
        return view;
    }

    private void createFile() {
        //Create File
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDirectory = new File(Environment.getExternalStorageDirectory().toString(), "PhoneTest/Record/");
        else
            cacheDirectory = this.getActivity().getCacheDir();

        if (!cacheDirectory.exists())
            cacheDirectory.mkdirs();

        file = new File(cacheDirectory, "myRecordTest.3gp");
    }

    private void action() {
        createAnimation();
        butRecord.setVisibility(View.VISIBLE);
        butStop.setVisibility(View.INVISIBLE);
        butPlay.setVisibility(View.INVISIBLE);

        butRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionUtil.isPermissionRecordGranted) {
                    ((TestActivity) getActivity()).showDialogAskPermission(getString(R.string.permission_record_ask),
                            Manifest.permission.RECORD_AUDIO,
                            PermissionUtil.MY_REQUEST_RECORD_PERMISSION_CODE);
                } else if (!PermissionUtil.isPermissionWriteStorageGranted) {
                    ((TestActivity) getActivity()).showDialogAskPermission(getString(R.string.permission_write_ask),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            PermissionUtil.MY_REQUEST_WRITE_STORAGE_PERMISSION_CODE);
                } else {
                    record();
                }
            }

            private void record() {
                try {
                    createFile();
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.reset();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    myAudioRecorder.setOutputFile(file.getPath());
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                startAdmination();
                txtMessage.setText(getActivity().getResources().getString(R.string.press_stop));
                imgRefresh.setVisibility(View.INVISIBLE);
                butRecord.setVisibility(View.INVISIBLE);
                butStop.setVisibility(View.VISIBLE);
            }
        });

        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stopAnimation();
                imgMicro.setImageBitmap(bp1);
                txtMessage.setText(getActivity().getResources().getString(R.string.press_play));
                butStop.setVisibility(View.INVISIBLE);
                butPlay.setVisibility(View.VISIBLE);
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                startAdmination();
                txtMessage.setText(getActivity().getResources().getString(R.string.ask_hear));
                imgRefresh.setVisibility(View.VISIBLE);
                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAnimation();
        cleanUpSound();
    }

    // Stop the sound and cleanup the media player
    public void cleanUpSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void createAnimation() {
        animationDrawable = createAnimationDrawable();
        imgMicro.setImageDrawable(animationDrawable);
    }

    private void stopAnimation() {
        if (animationDrawable != null) {
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
        }
    }

    private void startAdmination() {
        if (animationDrawable != null) {
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        } else {
            createAnimation();
            animationDrawable.start();
        }
    }

    private AnimationDrawable createAnimationDrawable() {
        bp1 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.mic1), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        bp2 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.mic2), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        bp3 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.mic3), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        bp4 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.mic4), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        bp5 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.mic5), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
        return ScreenUtil.createAnimationDrawable(new Bitmap[]{bp1, bp2, bp3, bp4, bp5}, getActivity());
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_micro_test;
    }
}
