package phamhungan.com.phonetestv3.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogInfo;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 25-Jan-16.
 */
public class CameraFragment extends BaseFragment implements View.OnClickListener {
    private ImageView imgSpeaker;
    private TextView txtMessage;
    private Bitmap bpLens;
    public static final int MY_REQUEST_CAMERA_PERMISSION_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        if (DataUtil.whichTest) {
            super.setHasOptionsMenu(false);
        } else {
            super.setHasOptionsMenu(true);
        }
        imgSpeaker = (ImageView) view.findViewById(R.id.imgSpeaker);
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        bpLens = ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.lens), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 4);
        imgSpeaker.setImageBitmap(bpLens);
        txtMessage.setText(getActivity().getResources().getString(R.string.toast_camera));
        imgSpeaker.setOnClickListener(this);
        checkPermissionCamera();
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
                        MY_REQUEST_CAMERA_PERMISSION_CODE);
            }
        }else {
            TestActivity.isPermissionCameraGranted = true;
        }
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_sound_test;
    }

    private void openCamera() {
        if(TestActivity.isPermissionCameraGranted){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }else {
            DialogInfo.createDialog(getActivity(),"Let PhoneTest access your camera to perform the camera test?");
            DialogInfo.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInfo.dialog.dismiss();
                    if(!TestActivity.isPermissionCameraGranted){
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_REQUEST_CAMERA_PERMISSION_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap bp = ResizeBitmap.resize((Bitmap) data.getExtras().get("data"), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);

            imgSpeaker.setImageBitmap(bp);
            txtMessage.setText(getActivity().getResources().getString(R.string.toast_camera2));
        } catch (Exception ex) {
            imgSpeaker.setImageBitmap(bpLens);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSpeaker:
                openCamera();
                break;
        }
    }
}
