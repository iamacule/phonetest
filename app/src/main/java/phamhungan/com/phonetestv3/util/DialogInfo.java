package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;

/**
 * Created by Covisoft on 07/01/2016.
 */
public class DialogInfo {
    public static TextView btnOK;
    public static TextView btnCancel;
    private static TextView tvMessage;
    public static AlertDialog dialog;


    public static void createDialog(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        btnOK = (TextView) view.findViewById(R.id.btn_ok);
        tvMessage.setText(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void show() {
        dialog.show();
    }
}
