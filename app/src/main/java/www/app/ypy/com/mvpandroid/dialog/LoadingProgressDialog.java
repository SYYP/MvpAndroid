package www.app.ypy.com.mvpandroid.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.utils.UIUtils;


/**
 * Created by Administrator on 2016/10/10.
 */
public class LoadingProgressDialog extends BaseDialog {
    private TextView mTvMsg;

    public LoadingProgressDialog(Context context) {
        super(context);
//        setOwnerActivity((Activity) context);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
//        setOwnerActivity((Activity) context);
    }

    @Override
    protected void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected View initLayout() {
//        setContentView(R.layout.dialog_loading_progress);
        View view = UIUtils.inflate(R.layout.load_layout);
        setContentView(view);
        mTvMsg = (TextView) this.findViewById(R.id.tv_loadingMsg);
         return view;
    }

    public void setTitle(String title) {
        mTvMsg.setText(title);
    }

    public void setTitle(int resId) {
        mTvMsg.setText(resId);
    }


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(layoutParams);
    }
}
