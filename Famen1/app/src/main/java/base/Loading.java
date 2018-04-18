package base;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;

import com.famen1.feiyangkeji.R;

public class Loading {


    private static AlertDialog dialog;

    private static DisplayMetrics metrics = new DisplayMetrics();

    public static void showDialog(Activity activity) {
        if (null == dialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            dialog.getWindow().setContentView(R.layout.loadingdialog);
            dialog.getWindow().setLayout(metrics.widthPixels / 3 * 2, metrics.widthPixels / 3 * 2);
        }
    }


    public static void dismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
