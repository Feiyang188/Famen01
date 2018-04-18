package main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import util.LogUtil;

public class FindPasswordByHint_Step1 extends BaseActivity {

    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.editUserName)
    EditText editUserName;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    MyAsyncHttpClient myAsyncHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpasswordbyhint_step1);
        ButterKnife.bind(this);

        tvTitle.setText("密码问题找回密码");
    }

    @OnClick({R.id.rlBack, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.btnLogin:
                submit();
                break;
        }
    }

    private void submit() {
        final String userName = editUserName.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            LogUtil.showToast(getApplicationContext(), "请输入用户名");
        } else if (userName.length() < 6) {
            LogUtil.showToast(getApplicationContext(), "用户名不能少于6位");
        } else {
            Loading.showDialog(this);

            RequestParams params = new RequestParams();
            params.put("username", userName);

            myAsyncHttpClient = new MyAsyncHttpClient();
            myAsyncHttpClient.post(getApplicationContext(), Urls.GET_QUESTION,params , new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    LogUtil.showToast(getApplicationContext(), "获取问题失败");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    LogUtil.Log(responseString);
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("0")) {
                            Intent it=new Intent(getApplicationContext(),FindPasswordByHint_Step2.class);
                            it.putExtra("username",userName);
                            it.putExtra("question",jsonObject.getString("data"));
                            startActivityForResult(it, 0);
                        } else {
                            LogUtil.showToast(getApplicationContext(), jsonObject.getString("msg"));
                        }

                    } catch (Exception e) {
                        LogUtil.showToast(getApplicationContext(), "获取问题失败");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
                setResult(2);
                finish();
                break;
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
