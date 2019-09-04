package www.app.ypy.com.mvpandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.bean.DailogBean;
import www.app.ypy.com.mvpandroid.bean.EventMessage;
import www.app.ypy.com.mvpandroid.bean.Scanbean;
import www.app.ypy.com.mvpandroid.dialog.BaseDialog;
import www.app.ypy.com.mvpandroid.dialog.CurrencyDailogs;
import www.app.ypy.com.mvpandroid.event.BaseEvent;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.CustomerCaptureManager;
import www.app.ypy.com.mvpandroid.utils.HLog;
import www.app.ypy.com.mvpandroid.utils.JsonUtil;

/**
 * Created by ypu
 * on 2019/8/27 0027
 */
public class ScanningActivity extends BaseMvpActivity {
    @BindView(R.id.dbv_custom)
    DecoratedBarcodeView dbvCustom;
    @BindView(R.id.img_scan_back)
    ImageView imgScanBack;
    @BindView(R.id.text_equment_num)
    TextView textEqumentNum;
    @BindView(R.id.flash_btn)
    TextView flashBtn;
    @BindView(R.id.text_scan_ok)
    TextView textScanOk;
    private CustomerCaptureManager captureManager;//扫描
    private ArrayList<Scanbean> scanList = new ArrayList<>(); //扫描的数据源
    CurrencyDailogs mCurrencyDailog;
    @Override
    protected int getXmlView() {
        return R.layout.activity_scanning;
    }

    @Override
    protected void loadData() {
        initCapture(); }

    @Override
    public void showNetError() { }

    @Override
    public void noNetWork() { }

    @Override
    public void setPresenter(Object presenter) { }

      @OnClick({R.id.text_scan_ok,R.id.img_scan_back})
      public void OnClick(View view){
          switch (view.getId()) {
              case R.id.text_scan_ok:
                  if(scanList.size()>0) {
                      Bundle bundle = new Bundle();
                      bundle.putParcelableArrayList(Contact.SCANNINGDATA, scanList);
                      startIntentWithExtras(ScanResultActivity.class, bundle);
                  }else {
                      HLog.toast(this,"未扫描任何设备!");
                  }
                  break;
              case R.id.img_scan_back:
                  //保存当前页面数据，
                  finish();
                  break;
          }
      }
    /**
     * 初始化捕捉
     */
    private void initCapture() {
        if (captureManager != null) {
            captureManager.onDestroy();
            captureManager = null;
        }
        captureManager = new CustomerCaptureManager(this, dbvCustom, scanningListener);
        captureManager.initializeFromIntent(getIntent(), null);
        captureManager.decode();
    }

    /**
     * 扫描结果监听
     */
    private CustomerCaptureManager.ScanningListener scanningListener = new CustomerCaptureManager.ScanningListener() {
        @Override
        public void scanResult(String result) {
            if (TextUtils.isEmpty(result))
                return;
            String[] split = new String[0];
            String deviceid = "";
            String accountnumber = "";
            try {
                split = result.split(",", 2);
                deviceid = split[0];
                accountnumber = split[1];
            } catch (Exception e) {
                e.printStackTrace();
                toastShort("未能识别信息，请检查格式是否正确");
                addCampture();//重启扫码页面
                return;
            }
            if (scanList.size() > 0) {
                boolean isExist = false;
                for (int i = 0; i < scanList.size(); i++) {
                    Scanbean scanbean = scanList.get(i);
                    if (deviceid.equals(scanbean.getDeviceId())) {
                        Toast.makeText(ScanningActivity.this, "该设备码已经存在", Toast.LENGTH_SHORT).show();
                        addCampture();//重启扫码页面
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    addSizeData(deviceid, accountnumber);//扫码后的数据添加到集合中
                    addCampture();

                }
            } else {
                addSizeData(deviceid, accountnumber);//初始时不考虑集合中是否存在码，先扫进去一个
                if (scanList.size() == 100) {
                    addCampture();
                    DailogBean dailogBean = new DailogBean();
                    dailogBean.setTitle("已经到达最大扫描数量,请绑定后再扫描");
                    dailogBean.setSure("查看扫描结果");
                    dailogBean.setPuse("取消弹窗");
                    if(mCurrencyDailog==null) {
                        mCurrencyDailog = new CurrencyDailogs(dailogBean, ScanningActivity.this);
                        mCurrencyDailog.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                            @Override
                            public void getOk() {
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList(Contact.SCANNINGDATA, scanList);
                                startIntentWithExtras(ScanResultActivity.class, bundle);
                                mCurrencyDailog.dismiss();

                            }

                            @Override
                            public void getCancle() {
                                mCurrencyDailog.dismiss();
                            }
                        });

                    }
                    mCurrencyDailog.show();
                } else {
                    addCampture();
                }

            }


        }
    };
    /**
     * 计算扫描的设备数量
     *
     * @param deviceid
     * @param accountnumber
     */
    private void addSizeData(String deviceid, String accountnumber) {
        Scanbean scanbean = new Scanbean();
        scanbean.setDeviceModelCode(accountnumber);
        scanbean.setDeviceId(deviceid);
        scanList.add(scanbean);
        textEqumentNum.setText("设备" + "(" + scanList.size() + ")");

    }
    /**
     * 持续存在扫码页面
     */
    private void addCampture() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initCapture();
            }
        }, 500);
    }
    public void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    public void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return dbvCustom.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
    /**
     * 加载数据
     *
     * @param eventMessage 传过来的值
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventMessage eventMessage) {
        if (Contact.SCAN.equals(eventMessage.getMsg2())) {
            scanList.clear();
            String msg2 = eventMessage.getMsg();
            List<Scanbean> list = JsonUtil.parseArrayObject(msg2, Scanbean.class);
            Log.d("TAG", msg2 + "-----------OK-");
            scanList.addAll(list);
            textEqumentNum.setText("设备" + "(" + scanList.size() + ")");
        }
    }
}
