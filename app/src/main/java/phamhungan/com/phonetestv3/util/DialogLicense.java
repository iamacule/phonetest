package phamhungan.com.phonetestv3.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;

/**
 * Created by Covisoft on 07/01/2016.
 */
public class DialogLicense {
    public static class Build {
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        private TextView button;
        private WebView webView;

        public Build(Activity activity) {
            builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_license, null);
            builder.setView(view);
            dialog = builder.create();
            webView = (WebView) view.findViewById(R.id.webView);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(false);
            webView.loadUrl("file:///android_asset/license.html");
            button = (TextView) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public void show() {
            if (dialog != null & !dialog.isShowing()) {
                dialog.show();
            }
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
