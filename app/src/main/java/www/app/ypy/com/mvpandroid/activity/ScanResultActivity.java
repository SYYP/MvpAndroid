package www.app.ypy.com.mvpandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.bean.DailogBean;
import www.app.ypy.com.mvpandroid.bean.EventMessage;
import www.app.ypy.com.mvpandroid.bean.Scanbean;
import www.app.ypy.com.mvpandroid.dialog.BaseDialog;
import www.app.ypy.com.mvpandroid.dialog.CurrencyDailogs;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.JsonUtil;
import www.app.ypy.com.mvpandroid.utils.SpUtils;

/**
 * Created by ypu
 * on 2019/9/3 0003
 */
public class ScanResultActivity extends BaseMvpActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_right)
    TextView textRight;
    @BindView(R.id.img_serachs)
    ImageView imgSerachs;
    @BindView(R.id.recycler_data)
    RecyclerView recyclerData;
    @BindView(R.id.img_select_all)
    ImageView imgSelectAll;
    @BindView(R.id.text_select_all)
    TextView textSelectAll;
    @BindView(R.id.liner_bninding)
    LinearLayout linerBninding;
    @BindView(R.id.text_delete)
    TextView textDelete;
    private List<Scanbean> mScanbeanArrayList;//数据源
    private CommonRecylerAdapter mCommonRecylerAdapter;
    boolean selectbool = false;
    CurrencyDailogs mCurrencyDailogs;

    @Override
    protected int getXmlView() {
        return R.layout.activity_scan_result;
    }

    @Override
    protected void loadData() {
        textTitle.setText("扫描结果");
        textRight.setText("继续扫描");
        if (mScanbeanArrayList.size() < 100) {
            textRight.setClickable(true);
        } else {
            textRight.setClickable(false);
            textRight.setTextColor(getResources().getColor(R.color.search_color));
        }

        //添加数据源
        addAdapter();
        if (mScanbeanArrayList.size() > 0) {
            for (int i = 0; i < mScanbeanArrayList.size(); i++) {
                if (mScanbeanArrayList.get(i).isBoolean()) {
                    continue;
                } else {
                    mScanbeanArrayList.get(i).setBoolean(true);
                }
            }
            mCommonRecylerAdapter.setList(mScanbeanArrayList);
            //拿到设备的数量
            textSelectAll.setText("全选（" + mScanbeanArrayList.size() + "/" + mScanbeanArrayList.size() + ")");
        }
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

    @Override
    public void processExtraData() {
        super.processExtraData();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (!extras.isEmpty()) {
            mScanbeanArrayList = extras.getParcelableArrayList(Contact.SCANNINGDATA);
        }
    }

    @OnClick({R.id.img_back, R.id.img_select_all, R.id.liner_bninding, R.id.text_delete, R.id.text_right})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getBack();
                break;
            case R.id.img_select_all://全选

                getSelectAll();

                break;
            case R.id.liner_bninding://绑定
                //传给下个页面选中的数据
                getBind();
                break;
            case R.id.text_delete://删除
                int selectNumber = 0;
                for (int i = 0; i < mScanbeanArrayList.size(); i++) {
                    if (mScanbeanArrayList.get(i).isBoolean())
                        selectNumber++;
                }
                DailogBean dailogBean = new DailogBean();
                dailogBean.setTitle("选择" + selectNumber + "台设备" + "确定移除已选择设备?");
                if (mCurrencyDailogs == null) {
                    mCurrencyDailogs = new CurrencyDailogs(dailogBean, this);
                    mCurrencyDailogs.setOnClickListener(new CurrencyDailogs.SetOnClick() {
                        @Override
                        public void getOk() {
                            for (int i = 0; i < mScanbeanArrayList.size(); i++) {
                                if (mScanbeanArrayList.get(i).isBoolean())
                                    mScanbeanArrayList.remove(i);
                                mCommonRecylerAdapter.notifyDataSetChanged();
                            }
                            int selectSize = getSelectSize();
                            if (selectSize <= 0) {
                                textDelete.setFocusable(false);
                                textDelete.setClickable(false);
                                textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
                                linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
                            } else {
                                textDelete.setFocusable(true);
                                textDelete.setClickable(true);
                                textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backline));
                                linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblue));
                            }
                            textSelectAll.setText("全选（" + selectSize + "/" + mScanbeanArrayList.size() + ")");
                            mCurrencyDailogs.dismiss();
                        }

                        @Override
                        public void getCancle() {
                            mCurrencyDailogs.dismiss();
                        }
                    });
                }
                mCurrencyDailogs.show();
                break;

            case R.id.text_right://继续扫码
                //结束当前页面，保存当前页面数据
                EventMessage eventMessages = new EventMessage();
                eventMessages.setMsg2(Contact.SCAN);
                eventMessages.setMsg(JsonUtil.parseString(mScanbeanArrayList));
                EventBus.getDefault().postSticky(eventMessages);
                finish();
                break;
        }
    }

    /**
     * 列表页面适配器
     */
    private void addAdapter() {
        recyclerData.setLayoutManager(new LinearLayoutManager(this));
        if (mCommonRecylerAdapter == null) {
            mCommonRecylerAdapter = new CommonRecylerAdapter(this, mScanbeanArrayList, R.layout.item_scan_result) {
                @Override
                public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                    Scanbean scanbean = (Scanbean) item;
                    holder.setText(R.id.text_equment_type, scanbean.getDeviceModelCode());
                    holder.setText(R.id.text_equmentnumber, scanbean.getDeviceId());
                    if (scanbean.isBoolean())
                        holder.setImageResource(R.id.img_select, R.drawable.select_true);
                    else
                        holder.setImageResource(R.id.img_select, R.drawable.select_false);
                    //获取集合中是否有未选中的进来默认全部选中，所以，全选默认选中，该判断为了点击取消一个选中
                    boolean selectbool = isSelectbool();
                    if (selectbool) {
                        imgSelectAll.setImageResource(R.drawable.select_true);
                    } else {
                        imgSelectAll.setImageResource(R.drawable.select_false);
                    }
                    getAdapterOnClick(holder, scanbean);
                }

                private void getAdapterOnClick(CommonRecylerHolder holder, Scanbean scanbean) {
                    holder.setOnRecyclerItemClickListener(R.id.img_select, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            scanbean.setBoolean(!scanbean.isBoolean());
                            //假如列表中只要有一个没有选中，全选按钮就不会被选中否则选中
                            if (!getSelectState())
                                imgSelectAll.setImageResource(R.drawable.select_false);
                            else
                                imgSelectAll.setImageResource(R.drawable.select_true);
                            //查找集合中的选中的数量
                            int selectSize = getSelectSize();
                            textSelectAll.setText("全选（" + selectSize + "/" + mScanbeanArrayList.size() + ")");
                            if (selectSize == 0) {
                                textDelete.setFocusable(false);
                                textDelete.setClickable(false);
                                textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
                                linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
                            } else {
                                textDelete.setFocusable(true);
                                textDelete.setClickable(true);
                                textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backline));
                                linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblue));
                            }
                            notifyDataSetChanged();
                        }
                    });
                }
            };
            recyclerData.setAdapter(mCommonRecylerAdapter);
        } else {
            mCommonRecylerAdapter.setList(mScanbeanArrayList);
        }
    }

    //查询集合中是否有未选择的
    private boolean isSelectbool() {
        for (int i = 0; i < mScanbeanArrayList.size(); i++) {
            if (!mScanbeanArrayList.get(i).isBoolean()) {
                return false;
            }
        }
        return true;
    }

    private boolean getSelectState() {
        boolean selectstate = true;
        for (int i = 0; i < mScanbeanArrayList.size(); i++) {
            if (mScanbeanArrayList.get(i).isBoolean() == false)
                selectstate = false;
        }
        return selectstate;
    }

    //查询集合中选中的数量
    private int getSelectSize() {
        int num = 0;
        for (int i = 0; i < mScanbeanArrayList.size(); i++) {
            if (mScanbeanArrayList.get(i).isBoolean()) {
                num++;
            }
        }
        return num;
    }

    private void getBack() {
        if (mScanbeanArrayList.size() == 100) {
            toastShort("扫描设备达到100台请先绑定数据");
            return;
        }
        EventMessage eventMessage = new EventMessage();
        eventMessage.setMsg2(Contact.SCAN);
        eventMessage.setMsg(JsonUtil.parseString(mScanbeanArrayList));
        EventBus.getDefault().postSticky(eventMessage);
        finish();
    }

    /**
     * 绑定数据
     */
    private void getBind() {
        int selectSize = getSelectSize();
        if (selectSize == 0) {
            toastShort("请选择绑定的设备");
            return;
        }
        ArrayList<Scanbean> listselect = new ArrayList<>();//选中
        List<Scanbean> listnoselect = new ArrayList<>();//未绑定的
        for (int i = 0; i < mScanbeanArrayList.size(); i++) {
            //将选中的放在一个集合传递，未选中的放在另一个集合保存，绑定失败时候回来加上
            //未选中的跟未绑定的
            if (mScanbeanArrayList.get(i).isBoolean())
                listselect.add(mScanbeanArrayList.get(i));
            else
                listnoselect.add(mScanbeanArrayList.get(i));

        }
        //将未选中的保存到 sp值中。后面需要使用
        SpUtils.putString(this, Contact.NOSELECTDATA, JSON.toJSONString(listnoselect));
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Contact.SELECTDATA, listselect);//传递下个页面选中的数据
        startActivityForResult(DistribtuionNumberActivity.class, bundle, Contact.SCANRESULT);
    }

    /**
     * 全选页面数据
     */
    private void getSelectAll() {
        if (!isSelectbool()) {//以一个boobean 来判断全选跟取消全选
            imgSelectAll.setImageResource(R.drawable.select_true);
            for (int i = 0; i < mScanbeanArrayList.size(); i++) {
                if (mScanbeanArrayList.get(i).isBoolean())
                    continue;
                else
                    mScanbeanArrayList.get(i).setBoolean(true);
            }
            linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblue));
            textSelectAll.setText("全选（" + mScanbeanArrayList.size() + "/" + mScanbeanArrayList.size() + ")");
            textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backline));
            textDelete.setFocusable(true);
            textDelete.setClickable(true);
        } else {
            textDelete.setFocusable(false);
            textDelete.setClickable(false);
            addNoselect();//取消全選
            textSelectAll.setText("全选（" + 0 + "/" + mScanbeanArrayList.size() + ")");
            linerBninding.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
            textDelete.setBackground(getResources().getDrawable(R.drawable.shape_backblack_all));
        }

        mCommonRecylerAdapter.notifyDataSetChanged();
    }

    /**
     * 將选中的值改为未选中
     */
    private void addNoselect() {
        imgSelectAll.setImageResource(R.drawable.select_false);
        for (int i = 0; i < mScanbeanArrayList.size(); i++) {
            if (mScanbeanArrayList.get(i).isBoolean())
                mScanbeanArrayList.get(i).setBoolean(false);
            else
                continue;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调值
        if (requestCode == Contact.SCANRESULT && resultCode == Contact.DISTRIBUTON) {
            //获取未选择的页面的数据
            String string = SpUtils.getString(Myapplication.getApplication(), Contact.NOSELECTDATA, "");
            if (!TextUtils.isEmpty(string)) {
                mScanbeanArrayList.clear();
                List<Scanbean> mScanbeanList = JsonUtil.parseArrayObject(string, Scanbean.class);
                mScanbeanArrayList.addAll(mScanbeanList);
                textSelectAll.setText("全选（" + 0 + "/ " + mScanbeanArrayList.size() + ")");
                mCommonRecylerAdapter.setList(mScanbeanArrayList);


            }
        }
    }

    @Override
    public void onBackPressed() {
        getBack();
        super.onBackPressed();

    }
}
