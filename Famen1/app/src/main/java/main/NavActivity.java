package main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    MyAdapter adapter;

    Fragment f1;
    Fragment f2;
    Fragment f3;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        ButterKnife.bind(this);


        viewPager.setOffscreenPageLimit(3);
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv1.setImageResource(R.mipmap.graydot);
                iv2.setImageResource(R.mipmap.graydot);
                iv3.setImageResource(R.mipmap.graydot);
                switch (position) {
                    case 0:
                        iv1.setImageResource(R.mipmap.whtiedot);
                        break;
                    case 1:
                        iv2.setImageResource(R.mipmap.whtiedot);
                        break;
                    case 2:
                        iv3.setImageResource(R.mipmap.whtiedot);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
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
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
