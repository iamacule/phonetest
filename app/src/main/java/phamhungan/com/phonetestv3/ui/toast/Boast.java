package phamhungan.com.phonetestv3.ui.toast;

import android.annotation.SuppressLint;
import android.app.Activity;

public class Boast
{
    private volatile static Boast globalBoast = null;
    private ToastInfo internalToast;
    private Boast(ToastInfo toast)
    {
        internalToast = toast;
    }

    @SuppressLint("ShowToast")
    public static Boast makeText(Activity activity,Object message)
    {
        return new Boast(new ToastInfo(activity,message));
    }

    public void cancel()
    {
        internalToast.cancel();
    }

    public void show()
    {
        show(true);
    }

    public void show(boolean cancelCurrent)
    {
        // cancel current
        if (cancelCurrent && (globalBoast != null))
        {
            globalBoast.cancel();
        }

        // save an instance of this current notification
        globalBoast = this;

        internalToast.show();
    }
}