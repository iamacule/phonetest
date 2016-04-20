package phamhungan.com.phonetestv3.ui.fragment;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 25-Jan-16.
 */
public class FlashFragment extends BaseFragment {
    private ImageView img;
    private TextView txtMessage;
    private Bitmap imgFlastOn;
    private Bitmap imgFlastOff;
    private boolean isLightOn = false;
    private Camera camera;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if(DataUtil.whichTest){
            super.setHasOptionsMenu(false);
        }else {
            super.setHasOptionsMenu(true);
        }
        img = (ImageView)view.findViewById(R.id.imgSpeaker);
        txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        txtMessage.setText(getActivity().getResources().getString(R.string.flash_message));
        imgFlastOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.on), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 4);
        imgFlastOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.off), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 4);
        img.setImageBitmap(imgFlastOff);
        camera = Camera.open();
        final Camera.Parameters parameters = camera.getParameters();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasFlash()) {
                    txtMessage.setText(getActivity().getResources().getString(R.string.toast_flash_error));
                }
                else {
                    if (isLightOn)
                    {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isLightOn = false;
                        img.setImageBitmap(imgFlastOff);
                        txtMessage.setText("Click the button to turn on flashlight");
                    }
                    else
                    {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isLightOn = true;
                        img.setImageBitmap(imgFlastOn);
                        txtMessage.setText("Click the button to turn off flashlight");
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
        }
    }

    private boolean isFlashSupported(PackageManager packageManager){
        try{
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public boolean hasFlash() {
        if (camera == null) {
            return false;
        }

        if(isFlashSupported(getActivity().getPackageManager())){
            Camera.Parameters parameters = camera.getParameters();

            if (parameters.getFlashMode() == null) {
                return false;
            }

            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if (supportedFlashModes == null || supportedFlashModes.isEmpty() || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF)) {
                return false;
            }

            return true;
        }
        else
            return false;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_sound_test;
    }
}
