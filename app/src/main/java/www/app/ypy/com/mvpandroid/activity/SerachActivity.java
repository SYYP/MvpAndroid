package www.app.ypy.com.mvpandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.application.Myapplication;
import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
import www.app.ypy.com.mvpandroid.base.BaseMvpFragment;
import www.app.ypy.com.mvpandroid.bean.EventMessage;
import www.app.ypy.com.mvpandroid.bean.Historybean;
import www.app.ypy.com.mvpandroid.gradeadapter.SerachAdapter;
import www.app.ypy.com.mvpandroid.utils.Contact;
import www.app.ypy.com.mvpandroid.utils.JsonUtil;
import www.app.ypy.com.mvpandroid.utils.SpUtils;

/**
 * Created by ypu
 * on 2019/8/29 0029
 */
public class SerachActivity extends BaseMvpActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_right)
    TextView textRight;
    @BindView(R.id.img_serachs)
    ImageView imgSerachs;
    @BindView(R.id.edit_query_name)
    EditText editQueryName;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.text_serach)
    TextView textSerach;
    @BindView(R.id.real_toobar)
    RelativeLayout realToobar;
    @BindView(R.id.img_clean)
    ImageView imgClean;
    @BindView(R.id.recycler_serach)
    RecyclerView recyclerSerach;
    @BindView(R.id.liner_recycler)
    LinearLayout linerRecycler;
    private String mDistrinbution = "";
    List<Historybean> historybeans = new ArrayList<>();
    private SerachAdapter mSerachAdapter;

    @Override
    public void showNetError() {

    }

    @Override
    public void noNetWork() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }
    /**
     * 加载列表页面适配器
     *
     * @param historybeans
     */
    private void addHistoryAdater(List<Historybean> historybeans) {
        recyclerSerach.setLayoutManager(new LinearLayoutManager(this));
        recyclerSerach.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        if(mSerachAdapter==null) {
            mSerachAdapter = new SerachAdapter();
            mSerachAdapter.setOnClickListener(new SerachAdapter.SetOnClick() {
                @Override
                public void getOnClick(Historybean historybean) {
                    EventMessage eventMessage = new EventMessage();
                    //选择条目点击事件传值
                    if (mDistrinbution.equals(Contact.HISTORTDATA)) {
                        eventMessage.setMsg(Contact.HISTORTREQURESCODE);
                    } else {
                        eventMessage.setMsg(Contact.DISTRIBUTION);
                    }
                    eventMessage.setMsg2(((Historybean) historybean).getName());
                    EventBus.getDefault().postSticky(eventMessage);
                    finish();
                }
            });
        }
         mSerachAdapter.addHistoryAdapter(historybeans,this,recyclerSerach);

    }

    @Override
    protected int getXmlView() {
        return R.layout.activity_serach;
    }

    @Override
    protected void loadData() {
        textTitle.setText("搜索");
        String string;
        if (mDistrinbution.equals(Contact.DISTRIBUTION)) {
            string = SpUtils.getString(Myapplication.getApplication(), Contact.DISTRIBUTION, "");
        } else {
            //获取数据
            string = SpUtils.getString(Myapplication.getApplication(), Contact.HISTORTDATA, "");

        }
        if (!TextUtils.isEmpty(string)) {
            historybeans = JsonUtil.parseArrayObject(string, Historybean.class);
            addHistoryAdater(historybeans);//历史记录
        }
        //如果当前集合没有数据，则展示无数据
        if (historybeans.size() == 0) {
            linerRecycler.setVisibility(View.GONE);
        }

    }

     @OnClick({R.id.img_back,R.id.img_delete,R.id.text_serach,R.id.img_clean})
     public void  OnClick(View view){

     }
}
