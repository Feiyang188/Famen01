package override;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.famen1.feiyangkeji.R;

import main.FindPasswordByHint_Step1;
import main.FindPasswordByPhoneActivity;

public class PopupwindowInLoginActivity extends PopupWindow {

    View view;
    Activity activity;
    TextView tvFindByPhone;
    TextView tvFindByHint;

    public PopupwindowInLoginActivity(Context context) {
        super(context);

    }

    public PopupwindowInLoginActivity(Activity context, int width, int height) {
        super(context);
        activity=context;
        view = View.inflate(activity, R.layout.popwindow_forgetpassword, null);
        setWidth(width);
        setHeight(height);
        setContentView(view);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.popanim01);

        tvFindByPhone = view.findViewById(R.id.tvFindPasswordByPhone);
        tvFindByHint = view.findViewById(R.id.tvFindPasswordByHint);

        tvFindByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                activity.startActivity(new Intent(activity, FindPasswordByPhoneActivity.class));
            }
        });

        tvFindByHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                activity.startActivity(new Intent(activity, FindPasswordByHint_Step1.class));
            }
        });


    }

    public PopupwindowInLoginActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupwindowInLoginActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PopupwindowInLoginActivity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PopupwindowInLoginActivity() {
    }

    public PopupwindowInLoginActivity(View contentView) {
        super(contentView);
    }

    public PopupwindowInLoginActivity(int width, int height) {
        super(width, height);

    }

    public PopupwindowInLoginActivity(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public PopupwindowInLoginActivity(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }


}
