package com.rt.sm.activity;

import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.common.BaseFragment;
import com.rt.sm.adapter.FragmentAdapter;
import com.rt.sm.fragment.HomeFragment;
import com.rt.sm.fragment.SleepLogFragment;
import com.rt.sm.fragment.StatisticsFragment;
import com.rt.sm.fragment.FindFragment;
import com.rt.sm.fragment.MineFragment;
import com.rt.sm.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haohw on 2018/1/7.
 * <p>
 * ----------Dragon be here!----------/
 * 　　　┏┓　　 ┏┓
 * 　　┏┛┻━━━┛┻┓━━━
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　  ┃
 * 　　┃　┳┛　┗┳
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　  ┃
 * 　　┃　　　　   ┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private HomeFragment fragment1;
    private SleepLogFragment sleepLogFragment;
    private StatisticsFragment fragment3;
    private FindFragment findFragment;
    private MineFragment mineFragment;
    private FragmentAdapter fragmentAdapter;
    @BindView(R.id.rg_bottom)
    RadioGroup rg_bottom;
    private long exitTime = 0;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initFragment();
        //refreshSession();
        //judgeSesstion();
    }

    private void initFragment() {
        fragment1 = new HomeFragment();
        sleepLogFragment = new SleepLogFragment();
        fragment3 = new StatisticsFragment();
        findFragment = new FindFragment();
        mineFragment = new MineFragment();
        fragmentList.add(fragment1);
        fragmentList.add(sleepLogFragment);
        fragmentList.add(fragment3);
        fragmentList.add(findFragment);
        fragmentList.add(mineFragment);
        fragmentAdapter = new FragmentAdapter(fragmentList, getSupportFragmentManager());
        viewPage.setOffscreenPageLimit(5);//ViewPager的缓存为4帧
        viewPage.setAdapter(fragmentAdapter);
        viewPage.setCurrentItem(0);//初始设置ViewPager选中第一帧
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rg_bottom.check(R.id.rb_bottom1);
                        break;
                    case 1:
                        rg_bottom.check(R.id.rb_bottom2);
                        break;
                    case 2:
                        rg_bottom.check(R.id.rb_bottom3);
                        break;
                    case 3:
                        rg_bottom.check(R.id.rb_bottom4);
                        break;
                    case 4:
                        rg_bottom.check(R.id.rb_bottom5);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bottom1:
                        viewPage.setCurrentItem(0);
                        break;
                    case R.id.rb_bottom2:
                        viewPage.setCurrentItem(1);
                        break;
                    case R.id.rb_bottom3:
                        viewPage.setCurrentItem(2);
                        break;
                    case R.id.rb_bottom4:
                        viewPage.setCurrentItem(3);
                        break;
                    case R.id.rb_bottom5:
                        viewPage.setCurrentItem(4);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                ToastUtils.showShortMsg("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
