package main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famen1.feiyangkeji.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import base.BaseActivity;
import base.Loading;
import base.MyAsyncHttpClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.Urls;
import cz.msebera.android.httpclient.Header;
import util.LogUtil;

public class RegistActivity extends BaseActivity {


    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.editUserName)
    EditText editUserName;
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
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.ivEye1)
    ImageView ivEye1;
    @BindView(R.id.rlEye1)
    RelativeLayout rlEye1;
    @BindView(R.id.editConfirmPassword)
    EditText editConfirmPassword;
    @BindView(R.id.ivEye2)
    ImageView ivEye2;
    @BindView(R.id.rlEye2)
    RelativeLayout rlEye2;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editHint)
    EditText editHint;
    @BindView(R.id.editAnswer)
    EditText editAnswer;
    @BindView(R.id.ck)
    CheckBox ck;
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
        setContentView(R.layout.activity_regsit);
        ButterKnife.bind(this);

        tvTitle.setText("注册");

    }


    @OnClick({R.id.lvSelectAreaNumber, R.id.tvGetpin, R.id.rlEye1, R.id.rlEye2, R.id.btnLogin, R.id.rlBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvSelectAreaNumber:
                break;
            case R.id.tvGetpin:
                getPin();
                break;
            case R.id.rlEye1:
                if (editPassword.getInputType() == 129) {
                    ivEye1.setImageResource(R.mipmap.eye_closed);
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivEye1.setImageResource(R.mipmap.eye_open);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.rlEye2:
                if (editConfirmPassword.getInputType() == 129) {
                    ivEye2.setImageResource(R.mipmap.eye_closed);
                    editConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivEye2.setImageResource(R.mipmap.eye_open);
                    editConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btnLogin:
                submit();
                break;
            case R.id.rlBack:
                finish();
                break;
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

    /**
     * 提交
     */
    private void submit() {
        String userName = editUserName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String pin = editPin.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirm = editConfirmPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String hint = editHint.getText().toString().trim();
        String answer = editAnswer.getText().toString().trim();
        boolean isCkecked = ck.isChecked();


        if (TextUtils.isEmpty(userName)) {
            LogUtil.showToast(getApplicationContext(), "请输入用户名");
        } else if (userName.length() < 6) {
            LogUtil.showToast(getApplicationContext(), "用户名不能少于6位");
        } else if (TextUtils.isEmpty(phone)) {
            LogUtil.showToast(getApplicationContext(), "请输入手机号");
        }
//        else if (TextUtils.isEmpty(pin)) {
//            LogUtil.showToast(getApplicationContext(), "请输入验证码");
//        }
        else if (TextUtils.isEmpty(password)) {
            LogUtil.showToast(getApplicationContext(), "请输入登陆密码");
        } else if (password.length() < 6) {
            LogUtil.showToast(getApplicationContext(), "密码不能少于6位");
        } else if (TextUtils.isEmpty(confirm)) {
            LogUtil.showToast(getApplicationContext(), "请输再次输入登陆密码");
        } else if (!password.equals(confirm)) {
            LogUtil.showToast(getApplicationContext(), "两次输入的密码不一致");
        } else if (TextUtils.isEmpty(email)) {
            LogUtil.showToast(getApplicationContext(), "请输入邮箱");
        } else if (TextUtils.isEmpty(hint)) {
            LogUtil.showToast(getApplicationContext(), "请输入密码提示问题");
        } else if (TextUtils.isEmpty(answer)) {
            LogUtil.showToast(getApplicationContext(), "请输入答案");
        } else if (!phone.matches("^1[34578]\\d{9}$")) {
            LogUtil.showToast(getApplicationContext(), "请输入正确的手机号");
        } else if (!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            LogUtil.showToast(getApplicationContext(), "请输入正确邮箱");
        } else if (!isCkecked) {
            LogUtil.showToast(getApplicationContext(), "请阅读并同意条款");
        } else {
            Loading.showDialog(this);
            RequestParams params = new RequestParams();
            params.put("username", userName);
            params.put("password", password);
            params.put("email", email);
            params.put("phone", phone);
            params.put("question", hint);
            params.put("answer", answer);

            httpClient = new MyAsyncHttpClient();

            httpClient.post(getApplicationContext(), Urls.REGIST, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    LogUtil.showToast(getApplicationContext(), "注册失败");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    LogUtil.Log(responseString);

                    try {
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("0")) {
                            LogUtil.showToast(getApplicationContext(), "注册成功");
                            RegistActivity.this.finish();
                        } else {
                            LogUtil.showToast(getApplicationContext(), jsonObject.getString("msg"));
                        }

                    } catch (Exception e) {
                        LogUtil.showToast(getApplicationContext(), "注册失败");
                    }
                }



                @Override
                public void onFinish() {
                    super.onFinish();
                    Loading.dismiss();
                }
            });


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
}
