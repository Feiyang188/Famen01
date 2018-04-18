package main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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

public class FindPasswordByHint_Step2 extends BaseActivity {


    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvHint)
    TextView tvHint;
    @BindView(R.id.editAnswer)
    EditText editAnswer;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    String userName;
    String question;

    MyAsyncHttpClient myAsyncHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpasswordbyhint_step2);
        ButterKnife.bind(this);

        tvTitle.setText("密码问题找回密码");
        userName = getIntent().getStringExtra("username");
        question = getIntent().getStringExtra("question");
        tvHint.setText(question);
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
        String answer = editAnswer.getText().toString().trim();
        if (TextUtils.isEmpty(answer)) {
            LogUtil.showToast(getApplicationContext(), "请输入提示答案");
        } else {
            Loading.showDialog(this);

            RequestParams params=new RequestParams();
            params.put("username",userName);
            params.put("question",question);
            params.put("answer",answer);

            myAsyncHttpClient=new MyAsyncHttpClient();
            myAsyncHttpClient.post(getApplicationContext(), Urls.ANSWER, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    LogUtil.showToast(getApplicationContext(),"连接失败");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    LogUtil.Log(responseString);
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (jsonObject.getString("status").equals("0")) {
                            Intent it=new Intent(getApplicationContext(),FindPasswordByHint_Step3.class);
                            it.putExtra("username",userName);
                            it.putExtra("token",jsonObject.getString("data"));
                            startActivityForResult(it, 0);
                        } else {
                            LogUtil.showToast(getApplicationContext(), jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        LogUtil.showToast(getApplicationContext(),"连接失败");
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
        if(null!=myAsyncHttpClient){
            myAsyncHttpClient.cancelAllRequests(true);
        }
    }
}
