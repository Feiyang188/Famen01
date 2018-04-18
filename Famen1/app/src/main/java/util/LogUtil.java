package util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil {


    public static void Log(String msg) {
        Log.i("xml", msg);
    }


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
