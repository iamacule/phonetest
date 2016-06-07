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
    public static class Build {
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        private TextView button;
        private TextView tvMessage;

        public Build(Activity activity) {
            builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.alert_dialog_one_button, null);
            builder.setView(view);
            dialog = builder.create();
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            button = (TextView) view.findViewById(R.id.button);
        }

        public Build setMessage(String message) {
            tvMessage.setText(message);
            return this;
        }

        public Build setButton(String text){
            this.button.setText(text);
            return this;
        }

        public Build setDefaultButtonClick() {
            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return this;
        }

        public TextView getButton() {
            return this.button;
        }

        public void show() {
            if(dialog!=null&!dialog.isShowing()){
                dialog.show();
            }
        }

        public void dismiss() {
            if(dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
