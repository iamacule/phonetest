package phamhungan.com.phonetestv3.ui.toast;

import android.app.Activity;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import phamhungan.com.phonetestv3.R;

/**
 * Created by MrAn on 04-May-16.
 */
public class ToastInfo {
    private Toast toast;

    public ToastInfo(Activity activity, Object message) {
        create(activity,message);
    }

    public void cancel()
    {
        toast.cancel();
    }

    public void show()
    {
        toast.show();
    }

    private void create(Activity activity, Object message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) activity.findViewById(R.id.toast_parent));
        TextView text = (TextView) layout.findViewById(R.id.toast_message);
        if(message instanceof String)
            text.setText((String)message);

        if(message instanceof Spanned)
            text.setText((Spanned)message);
        toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }
}
