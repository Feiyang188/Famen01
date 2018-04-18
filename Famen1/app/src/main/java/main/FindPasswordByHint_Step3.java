package main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.LogUtil;

public class FindPasswordByHint_Step3 extends BaseActivity {

    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
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

    String userName;
    String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpasswordbyhint_step3);
        ButterKnife.bind(this);

        tvTitle.setText("找回密码");
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
        String newPassword = editNewpassword.getText().toString().trim();
        String confirmNewPassword = editConfirmNewpassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            LogUtil.showToast(getApplicationContext(), "请输入新密码");
        } else if (newPassword.length() < 6) {
            LogUtil.showToast(getApplicationContext(), "新密码不能少于6位");
        } else if (TextUtils.isEmpty(confirmNewPassword)) {
            LogUtil.showToast(getApplicationContext(), "请输再次输入新密码");
        } else if (!newPassword.equals(confirmNewPassword)) {
            LogUtil.showToast(getApplicationContext(), "两次输入的密码不一致");
        } else {
            startActivityForResult(new Intent(getApplicationContext(), ResetPasswordSuccessActivity.class), 0);
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
}
