package main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import util.SharedPreferenceUtil;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceUtil.isFirst(getApplicationContext())) {
                    SharedPreferenceUtil.setIsFirst(getApplicationContext(), false);
                    startActivity(new Intent(getApplicationContext(), NavActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), WebMainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
