package main;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.ActivityResultStatus;
import override.MyViewPager;
import util.SharedPreferenceUtil;


public class MainActivity extends BaseActivity {


    MyPagerAdapter adapter;
    Fragment1 f1;
    Fragment f2;
    Fragment f3;
    Fragment f4;

    @BindView(R.id.viewPager)
    MyViewPager viewPager;
    @BindView(R.id.ivIndex)
    ImageView ivIndex;
    @BindView(R.id.tvIndex)
    TextView tvIndex;
    @BindView(R.id.lvIndex)
    LinearLayout lvIndex;
    @BindView(R.id.ivShopping)
    ImageView ivShopping;
    @BindView(R.id.tvShopping)
    TextView tvShopping;
    @BindView(R.id.lvShopping)
    LinearLayout lvShopping;
    @BindView(R.id.ivConsult)
    ImageView ivConsult;
    @BindView(R.id.tvConsult)
    TextView tvConsult;
    @BindView(R.id.lvConsult)
    LinearLayout lvConsult;
    @BindView(R.id.ivMine)
    ImageView ivMine;
    @BindView(R.id.tvMine)
    TextView tvMine;
    @BindView(R.id.lvMine)
    LinearLayout lvMine;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        viewPager.setOffscreenPageLimit(3);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 改变下方4个按钮的按颜色
     */
    private void changeColor(int selected) {
        ivIndex.setImageResource(R.mipmap.index1);
        ivConsult.setImageResource(R.mipmap.consult1);
        ivMine.setImageResource(R.mipmap.mine1);
        ivShopping.setImageResource(R.mipmap.shopping1);

        tvIndex.setTextColor(Color.parseColor("#666666"));
        tvConsult.setTextColor(Color.parseColor("#666666"));
        tvMine.setTextColor(Color.parseColor("#666666"));
        tvShopping.setTextColor(Color.parseColor("#666666"));

        switch (selected) {
            case 0:
                ivIndex.setImageResource(R.mipmap.index2);
                tvIndex.setTextColor(Color.parseColor("#0a8496"));
                break;
            case 1:
                ivShopping.setImageResource(R.mipmap.shopping2);
                tvShopping.setTextColor(Color.parseColor("#0a8496"));
                break;
            case 2:
                ivConsult.setImageResource(R.mipmap.consult2);
                tvConsult.setTextColor(Color.parseColor("#0a8496"));
                break;
            case 3:
                ivMine.setImageResource(R.mipmap.mine2);
                tvMine.setTextColor(Color.parseColor("#0a8496"));
                break;
        }
    }

    @OnClick({R.id.lvIndex, R.id.lvShopping, R.id.lvConsult, R.id.lvMine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lvIndex:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.lvShopping:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.lvConsult:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.lvMine:
                if (SharedPreferenceUtil.isLogin(getApplicationContext())) {
                    viewPager.setCurrentItem(3, true);
                } else {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), 0);
                }
                break;
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (null == f1) {
                        f1 = new Fragment1();
                    }
                    return f1;
                case 1:
                    if (null == f2) {
                        f2 = new Fragment2();
                    }
                    return f2;
                case 2:
                    if (null == f3) {
                        f3 = new Fragment3();
                    }
                    return f3;
                case 3:
                    if (null == f4) {
                        f4 = new Fragment4();
                    }
                    return f4;
                default:
                    if (null == f1) {
                        f1 = new Fragment1();
                    }
                    return f1;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ActivityResultStatus.LOGIN_OK://登录成功

                break;
        }

    }
}
