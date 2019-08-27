//package www.app.ypy.com.mvpandroid;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import www.app.ypy.com.mvpandroid.base.BaseMvpActivity;
//import www.app.ypy.com.hirinequments.bean.InforBean;
//import www.app.ypy.com.hirinequments.moudel.ILoginInterface;
//import www.app.ypy.com.hirinequments.moudel.LoginPresenter;
//import www.app.ypy.com.mvpandroid.utils.UIUtils;
//
//public class MainActivity extends BaseMvpActivity<ILoginInterface.Presenter> implements ILoginInterface.View {
//    @BindView(R.id.img_logo)
//    ImageView imgLogo;
//    @BindView(R.id.edit_num)
//    EditText editNum;
//    @BindView(R.id.edit_pwd)
//    EditText editPwd;
//    @BindView(R.id.btn_login)
//    Button btnLogin;
//    @BindView(R.id.scroll_view)
//    ScrollView scrollView;
//    private String mEdtinum;
//    @Override
//    protected int getXmlView() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void loadData() {
//     btnLogin.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             mEdtinum = editNum.getText().toString();
//             String editpwd = editPwd.getText().toString();//密码
//
//             if (TextUtils.isEmpty(mEdtinum)) {
//                 toastShort("请输入账号");
//                 return;
//             } else if (TextUtils.isEmpty(editpwd)) {
//                 toastShort("请输入密码");
//                 return;
//             }
//              presenter.getLoadData(mEdtinum,editpwd);
//         }
//     });
//    }
//
//    public void showNetError() {
//
//    }
//    @Override
//    public void noNetWork() {
//
//    }
//    @Override
//    public void setPresenter(ILoginInterface.Presenter presenter) {
//        if (null == presenter) {
//            this.presenter = new LoginPresenter(this);
//        }
//
//    }
//
//    @Override
//
//    public void SuccessData(InforBean loginBean) {
//
//         UIUtils.showToastSafe("登录成功",0);
//    }
//
//
//}
