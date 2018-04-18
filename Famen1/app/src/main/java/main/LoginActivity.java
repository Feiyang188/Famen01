package main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famen1.feiyangkeji.R;
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
import override.PopupwindowInLoginActivity;
import util.LogUtil;
import util.SharedPreferenceUtil;

public class LoginActivity extends BaseActivity {


    String[] areaList;
    @BindView(R.id.tvAreaNumber)
    TextView tvAreaNumber;
    @BindView(R.id.lvSelectAreaNumber)
    LinearLayout lvSelectAreaNumber;
    @BindView(R.id.tvRegist)
    TextView tvRegist;
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.ivEye)
    ImageView ivEye;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.ivQQ)
    ImageView ivQQ;
    @BindView(R.id.lvQQLogin)
    LinearLayout lvQQLogin;
    @BindView(R.id.ivWechat)
    ImageView ivWechat;
    @BindView(R.id.lvWechatLogin)
    LinearLayout lvWechatLogin;
    @BindView(R.id.ivWeibo)
    ImageView ivWeibo;
    @BindView(R.id.lvWeiBoLogin)
    LinearLayout lvWeiBoLogin;
    @BindView(R.id.rlOpenEye)
    RelativeLayout rlOpenEye;
    @BindView(R.id.ivTitle)
    ImageView ivTitle;


    DisplayMetrics metrics = new DisplayMetrics();

    MyAsyncHttpClient myAsyncHttpClient;

    PopupwindowInLoginActivity popupwindowInLoginActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        areaList = getResources().getStringArray(R.array.areanumber);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        initComponents();


    }


    private void initComponents() {
        int titleBgheight = (int) (metrics.widthPixels * 0.47);
        ViewGroup.LayoutParams lm = ivTitle.getLayoutParams();
        lm.width = metrics.widthPixels;
        lm.height = titleBgheight;
        ivTitle.setLayoutParams(lm);

        popupwindowInLoginActivity = new PopupwindowInLoginActivity(this, (int)(metrics.widthPixels*0.9), metrics.heightPixels / 5);
    }


    @OnClick({R.id.lvSelectAreaNumber, R.id.tvForgetPassword, R.id.btnLogin, R.id.lvQQLogin, R.id.lvWechatLogin, R.id.lvWeiBoLogin, R.id.rlOpenEye, R.id.tvRegist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvSelectAreaNumber:
                break;
            case R.id.rlOpenEye:
                if (editPassword.getInputType() == 129) {
                    ivEye.setImageResource(R.mipmap.eye_closed);
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivEye.setImageResource(R.mipmap.eye_open);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.tvForgetPassword:
                showPopwindow();
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.lvQQLogin:
                break;
            case R.id.lvWechatLogin:
                break;
            case R.id.lvWeiBoLogin:
                break;
            case R.id.tvRegist:
                startActivityForResult(new Intent(getApplicationContext(), RegistActivity.class), 0);
                break;
        }
    }

    private void showPopwindow() {
        if (null != popupwindowInLoginActivity && popupwindowInLoginActivity.isShowing()) {
            popupwindowInLoginActivity.dismiss();
        } else {

            popupwindowInLoginActivity.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        }
    }

    private void login() {
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

//        if (TextUtils.isEmpty(password)) {
//            LogUtil.showToast(getApplicationContext(), "请输入手机号码");
//        } else if (!phone.matches("^1[34578]\\d{9}$")) {
//            LogUtil.showToast(getApplicationContext(), "请输入正确的手机号码");
//        } else
        if (TextUtils.isEmpty(password)) {
            LogUtil.showToast(getApplicationContext(), "请输入密码");
        } else if (password.length() < 6) {
            LogUtil.showToast(getApplicationContext(), "密码至少为6位");
        } else {

            Loading.showDialog(this);

            myAsyncHttpClient = new MyAsyncHttpClient();

            RequestParams params = new RequestParams();
            params.put("username", phone);
            params.put("password", password);

            myAsyncHttpClient.post(getApplicationContext(), Urls.LOGIN, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    LogUtil.showToast(getApplicationContext(), "登录失败");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        LogUtil.Log(responseString);
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("0")) {
                            LogUtil.showToast(getApplicationContext(), "登录成功");
                            JSONObject userInfo = jsonObject.getJSONObject("data");
                            SharedPreferenceUtil.saveLoginInfo(getApplicationContext(), userInfo);

                        } else {
                            LogUtil.showToast(getApplicationContext(), jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        LogUtil.showToast(getApplicationContext(), "登录失败");
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
        if (null != myAsyncHttpClient) {
            myAsyncHttpClient.cancelAllRequests(true);
        }
    }
}
