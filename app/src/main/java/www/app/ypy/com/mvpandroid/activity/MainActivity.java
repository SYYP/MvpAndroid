package www.app.ypy.com.mvpandroid.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.zxing.integration.android.IntentIntegrator;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.HomeViewPagerAdapter;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.fragment.DailyWorkFragment;
import www.app.ypy.com.mvpandroid.fragment.MyFragment;
import www.app.ypy.com.mvpandroid.utils.BottomNavigationViewHelper;

/**
 * Created by ypu
 * on 2019/8/27 0027
 */
public class MainActivity extends BaseMvpActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.linear_Code)
    LinearLayout linearCode;
    @BindView(R.id.fragment_bottom)
    FrameLayout fragmentBottom;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.include_qt)
    LinearLayout includeQt;
    @BindView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    HomeViewPagerAdapter homeViewPagerAdapter;
    private final String[] PERMISSION = new String[]{
            Manifest.permission.READ_CONTACTS,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.WRITE_CALL_LOG,        //读取设备信息
            Manifest.permission.CAMERA,//相机权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入权限
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS//允许挂载和反挂载文件系统可移动存储
    };
    @Override
    protected int getXmlView() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        initBottomNavgiationView();//添加底部按钮
        initViewPager();//添加画动页面
        addScaning();//添加扫码
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void noNetWork() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    private void initBottomNavgiationView() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_main:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.item_my:
                        viewpager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 添加adapter
     */
    private void initViewPager() {
        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        homeViewPagerAdapter.addFragment(DailyWorkFragment.newInstance("one"));
        homeViewPagerAdapter.addFragment(MyFragment.newInstance("two"));//可能要传递的参数
        viewpager.setAdapter(homeViewPagerAdapter);
        //创建fragment
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private MenuItem mMenuItem;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = bottomNavigation.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //扫码
    private void addScaning() {
        linearCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermissions();

            }
        });
    }
    /**
     * 设置Android6.0的权限申请
     */
    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //Android 6.0申请权限
            ActivityCompat.requestPermissions(this, PERMISSION, 1);
        } else {
            toScanning();
        }
    }

    private void toScanning() {
        Intent intent = new Intent(this, ScanningActivity.class);
        startActivity(intent);
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(ScanningActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toastLong("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finishAll();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
