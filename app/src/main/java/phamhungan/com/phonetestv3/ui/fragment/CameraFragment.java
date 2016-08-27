package phamhungan.com.phonetestv3.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.TestActivity;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.PermissionUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 25-Jan-16.
 */
public class CameraFragment extends BaseFragment implements View.OnClickListener {
    private ImageView imgCamera;
    private TextView txtMessage;
    private Bitmap bpLens;

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
        imgCamera = (ImageView) view.findViewById(R.id.imgCamera);
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        bpLens = ResizeBitmap.resize(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.lens), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 4);
        imgCamera.setImageBitmap(bpLens);
        txtMessage.setText(getActivity().getResources().getString(R.string.toast_camera));
        imgCamera.setOnClickListener(this);
        return view;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_camera_test;
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap bp = ResizeBitmap.resize((Bitmap) data.getExtras().get("data"), ScreenUtil.getScreenWidth(getActivity().getWindowManager()) / 2);
            Bitmap test = fixOrientation(bp);
            if(null==test)
                imgCamera.setImageBitmap(bp);
            else
                imgCamera.setImageBitmap(test);
            txtMessage.setText(getActivity().getResources().getString(R.string.toast_camera2));
        } catch (Exception ex) {
            imgCamera.setImageBitmap(bpLens);
        }
    }

    public Bitmap fixOrientation(Bitmap bp) {
        if (bp.getWidth() > bp.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bp = Bitmap.createBitmap(bp , 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
            return bp;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCamera:
                if (!PermissionUtil.isPermissionCameraGranted) {
                    ((TestActivity) getActivity()).showDialogAskPermission(getString(R.string.permission_camera_ask),
                            Manifest.permission.CAMERA,
                            PermissionUtil.MY_REQUEST_CAMERA_PERMISSION_CODE);
                } else {
                    openCamera();
                }
                break;
        }
    }
}
