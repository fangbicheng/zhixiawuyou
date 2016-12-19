package com.fang.zhixiawuyou.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.adapter.MyFragmentPagerAdapter;
import com.fang.zhixiawuyou.base.BaseActivity;
import com.fang.zhixiawuyou.fragment.DemandFragment;
import com.fang.zhixiawuyou.fragment.InternshipFragment;
import com.fang.zhixiawuyou.fragment.RecruitmentFragment;
import com.fang.zhixiawuyou.fragment.UserFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private long exitTime;

    private ImageView ivTip;
    private RadioGroup rgBar;
    private RadioButton rbRecruitment, rbInternship, rbDemand, rbUser;
    private ViewPager viewPager;

    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ivTip = (ImageView) findViewById(R.id.main_iv_tip);
        rgBar = (RadioGroup) findViewById(R.id.main_rg_bar);
        rbRecruitment = (RadioButton) findViewById(R.id.main_rb_recuitment);
        rbInternship = (RadioButton) findViewById(R.id.main_rb_internship);
        rbDemand = (RadioButton) findViewById(R.id.main_rb_demand);
        rbUser = (RadioButton) findViewById(R.id.main_rb_user);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        isFirstIn();

        ivTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivTip.setVisibility(View.GONE);

                SharedPreferences.Editor editor = getSharedPreferences("first_pre", MODE_PRIVATE).edit();
                editor.putBoolean("isFirstInMain", false);
                editor.commit();
            }
        });
        rbRecruitment.setChecked(true);
        rgBar.setOnCheckedChangeListener(new mOnCheckedChangeListener());

        initViewPager();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        RecruitmentFragment recruitmentFragment = new RecruitmentFragment();
        InternshipFragment internshipFragment = new InternshipFragment();
        DemandFragment demandFragment = new DemandFragment();
        UserFragment userFragment = new UserFragment();
        fragmentList.add(recruitmentFragment);
        fragmentList.add(internshipFragment);
        fragmentList.add(demandFragment);
        fragmentList.add(userFragment);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new mOnPageChangeListener());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }

    private class mOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_rb_recuitment:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.main_rb_internship:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.main_rb_demand:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.main_rb_user:
                    viewPager.setCurrentItem(3);
                    break;
            }
        }
    }

    private class mOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if(i == 2) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        rbRecruitment.setChecked(true);
                        break;
                    case 1:
                        rbInternship.setChecked(true);
                        break;
                    case 2:
                        rbDemand.setChecked(true);
                        break;
                    case 3:
                        rbUser.setChecked(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 判断是否是第一次进入
     */
    private void isFirstIn() {
        SharedPreferences preferences = getSharedPreferences("first_pre", MODE_PRIVATE);
        Boolean isFirstIn = preferences.getBoolean("isFirstInMain", true);

        if(isFirstIn) {
            ivTip.setVisibility(View.VISIBLE);
        } else {
            ivTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
