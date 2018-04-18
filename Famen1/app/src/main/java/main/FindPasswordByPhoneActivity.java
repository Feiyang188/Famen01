package main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import base.MyAsyncHttpClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.LogUtil;

public class FindPasswordByPhoneActivity extends BaseActivity {


    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAreaNumber)
    TextView tvAreaNumber;
    @BindView(R.id.lvSelectAreaNumber)
    LinearLayout lvSelectAreaNumber;
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.editPin)
    EditText editPin;
    @BindView(R.id.tvGetpin)
    TextView tvGetpin;
    @BindView(R.id.editNewpassword)
    EditText editNewpassword;
    @BindView(R.id.ivEye1)
    ImageView ivEye1;
    @BindView(R.id.rlEye1)
    RelativeLayout rlEye1;
    @BindView(R.id.editConfirmNewpassword)
    EditText editConfirmNewpassword;
    @BindView(R.id.ivEye2)
    ImageView ivEye2;
    @BindView(R.id.rlEye2)
    RelativeLayout rlEye2;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    CountingThread countingThread;

    MyAsyncHttpClient httpClient;

    private Handler getPinHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://倒计时中
                    tvGetpin.setClickable(false);
                    tvGetpin.setFocusableInTouchMode(false);
                    tvGetpin.setEnabled(false);
                    tvGetpin.setFocusable(false);
                    tvGetpin.setText(msg.obj.toString());
                    break;
                case 1://倒计时结束
                    tvGetpin.setClickable(true);
                    tvGetpin.setFocusableInTouchMode(true);
                    tvGetpin.setEnabled(true);
                    tvGetpin.setFocusable(true);
                    tvGetpin.setText("获取验证码");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpasswordbyphone);
        ButterKnife.bind(this);


        tvTitle.setText("手机找回密码");
    }

    @OnClick({R.id.rlBack, R.id.lvSelectAreaNumber, R.id.tvGetpin, R.id.rlEye1, R.id.rlEye2, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.lvSelectAreaNumber:

                break;
            case R.id.tvGetpin:
                getPin();
                break;
            case R.id.rlEye1:
                if (editNewpassword.getInputType() == 129) {
                    ivEye1.setImageResource(R.mipmap.eye_closed);
                    editNewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivEye1.setImageResource(R.mipmap.eye_open);
                    editNewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.rlEye2:
                if (editConfirmNewpassword.getInputType() == 129) {
                    ivEye2.setImageResource(R.mipmap.eye_closed);
                    editConfirmNewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivEye2.setImageResource(R.mipmap.eye_open);
                    editConfirmNewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btnLogin:
                submit();
                break;
        }
    }

    private void submit() {
           String phone=editPhone.getText().toString().trim();
           String pin=editPin.getText().toString().trim();
           String newPassword=editNewpassword.getText().toString().trim();
           String confirmNewpassword=editConfirmNewpassword.getText().toString().trim();


           if(TextUtils.isEmpty(phone)){
               LogUtil.showToast(getApplicationContext(),"请输入手机号");
           }else if(!phone.matches("^1[34578]\\d{9}$")){
               LogUtil.showToast(getApplicationContext(),"请输入正确的手机号");
           }else if(TextUtils.isEmpty(pin)){
               LogUtil.showToast(getApplicationContext(),"请输入验证码");
           }else if(TextUtils.isEmpty(newPassword)){
               LogUtil.showToast(getApplicationContext(),"请输入新密码");
           }else if(newPassword.length()<6){
               LogUtil.showToast(getApplicationContext(),"新密码不能小于6位");
           }else if(TextUtils.isEmpty(confirmNewpassword)){
               LogUtil.showToast(getApplicationContext(),"请再次输入新密码");
           }else if(!newPassword.equals(confirmNewpassword)){
               LogUtil.showToast(getApplicationContext(),"两次输入密码不一致");
           }else{
                startActivityForResult(new Intent(getApplicationContext(),ResetPasswordSuccessActivity.class),0);
           }
    }

    private void getPin() {
        //editPhone.setEnabled(false);
        String phone = editPhone.getText().toString().trim();
        if (phone.matches("^1[34578]\\d{9}$")) {
            startCounting();
        } else {
            LogUtil.showToast(getApplicationContext(), "请输入正确的手机号");
        }
    }

    private void startCounting() {
        if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
            LogUtil.showToast(getApplicationContext(), "请输入手机号码");
            return;
        }


        if (countingThread == null || !countingThread.isAlive()) {
            countingThread = new CountingThread();
            countingThread.start();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPinHander = null;
        if (null != httpClient) {
            httpClient.cancelAllRequests(true);
        }
    }


    private class CountingThread extends Thread {

        @Override
        public void run() {
            super.run();
            for (int i = 60; i > 0; i--) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = i;
                if (null != getPinHander) {
                    getPinHander.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }


            if (null != getPinHander) {
                Message msg = new Message();
                msg.what = 1;
                getPinHander.sendMessage(msg);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 2:
                finish();
                break;
        }
    }
}


