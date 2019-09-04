package www.app.ypy.com.mvpandroid.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.bean.DailogBean;
import www.app.ypy.com.mvpandroid.utils.UIUtils;

/**
 * Created by ypu
 * on 2019/7/24 0024
 * 通用的dialog
 */
public class CurrencyDailogs extends BaseDialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_canel)
    TextView tvCanel;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    private SetOnClick mSetOnClick;
    private DailogBean mDailogBean;

    public CurrencyDailogs(DailogBean dailogBean, Context context) {
        super(context);
        this.mDailogBean = dailogBean;

    }

    @Override
    protected void init() {
        if (TextUtils.isEmpty(mDailogBean.getTitle())) {
            tvTitle.setVisibility(View.INVISIBLE);
        } else {
            tvTitle.setText(mDailogBean.getTitle());
        }
        tvYes.setText(mDailogBean.getSure() == null ? "确定" : mDailogBean.getSure());
        tvCanel.setText(mDailogBean.getPuse() == null ? "取消" : mDailogBean.getPuse());
        if (TextUtils.isEmpty(mDailogBean.getCount())) {
            tvCount.setVisibility(View.INVISIBLE);
        } else {
            tvCount.setText(mDailogBean.getCount());
        }
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                //确定继续绑定跳转到 绑定列表页面
                if (mSetOnClick != null) {
                    mSetOnClick.getOk();

                }
            }
        });
        tvCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSetOnClick!=null){
                    mSetOnClick.getCancle();
                }
            }
        });

    }

    @Override
    protected View initLayout() {
        View inflate = UIUtils.inflate(R.layout.dialog_bindfail);
        setContentView(inflate);
        return inflate;
    }

    public interface SetOnClick {
        void getOk();
        void getCancle();

    }

    public void setOnClickListener(SetOnClick setOnClick) {
        this.mSetOnClick = setOnClick;
    }
}
