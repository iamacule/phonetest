package phamhungan.com.phonetestv3.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogInfo;
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
    public static final int MY_REQUEST_FLASH_PERMISSION_CODE = 4;
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
        checkPermissionCamera();
        checkPermissionFlash();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TestActivity.isPermissionCameraGranted){
                    camera = Camera.open();
                    final Camera.Parameters parameters = hasFlash();

                    if (parameters==null) {
                        txtMessage.setText(getActivity().getResources().getString(R.string.toast_flash_error));
                    }
                    else {
                        camera.setParameters(parameters);
                        if (isLightOn)
                        {
                            camera.stopPreview();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            isLightOn = false;
                            img.setImageBitmap(imgFlastOff);
                            txtMessage.setText("Click the button to turn on flashlight");
                        }
                        else
                        {
                            camera.startPreview();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            isLightOn = true;
                            img.setImageBitmap(imgFlastOn);
                            txtMessage.setText("Click the button to turn off flashlight");
                        }
                    }
                }else {
                    DialogInfo.createDialog(getActivity(),"Let PhoneTest access your camera to perform the camera test?");
                    DialogInfo.btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogInfo.dialog.dismiss();
                            if(!TestActivity.isPermissionCameraGranted){
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.CAMERA},
                                        CameraFragment.MY_REQUEST_CAMERA_PERMISSION_CODE);
                            }
                            if(!TestActivity.isPermissionFlashGranted){
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.FLASHLIGHT},
                                        MY_REQUEST_FLASH_PERMISSION_CODE);
                            }
                        }
                    });
                    DialogInfo.btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogInfo.dialog.dismiss();
                        }
                    });
                    DialogInfo.show();
                }
            }
        });
        return view;
    }

    private void checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        CameraFragment.MY_REQUEST_CAMERA_PERMISSION_CODE);
            }
        }else {
            TestActivity.isPermissionCameraGranted = true;
        }
    }

    private void checkPermissionFlash() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.FLASHLIGHT)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.FLASHLIGHT)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.FLASHLIGHT},
                        MY_REQUEST_FLASH_PERMISSION_CODE);
            }
        }else {
            TestActivity.isPermissionFlashGranted = true;
        }
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

    public Camera.Parameters hasFlash() {
        Camera.Parameters parameters = camera.getParameters();
        if (camera == null) {
            return null;
        }

        if(isFlashSupported(getActivity().getPackageManager())){

            if (parameters.getFlashMode() == null) {
                return null;
            }

            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if (supportedFlashModes == null || supportedFlashModes.isEmpty() || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF)) {
                return null;
            }else {
                if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)){
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    return parameters;
                }
                else {
                    return null;
                }
            }
        }
        else
            return null;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_sound_test;
    }
}
