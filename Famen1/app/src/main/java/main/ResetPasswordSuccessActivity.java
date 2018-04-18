package main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordSuccessActivity extends BaseActivity {


    @BindView(R.id.btnFinish)
    Button btnFinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswordsuccess);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnFinish)
    public void onViewClicked() {
        setResult(2);
        finish();
    }
}
