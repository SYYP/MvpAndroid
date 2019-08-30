package www.app.ypy.com.mvpandroid.gradeadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import www.app.ypy.com.mvpandroid.R;
import www.app.ypy.com.mvpandroid.adapter.CommonFiveRecylerAdapter;
import www.app.ypy.com.mvpandroid.adapter.CommonRecylerHolder;
import www.app.ypy.com.mvpandroid.bean.Historybean;

/**
 * Created by ypu
 * on 2019/8/29 0029
 */
public class SerachAdapter {

    CommonFiveRecylerAdapter mCommonRecylerAdapter;
    private SetOnClick mSetOnClick;

    public void addHistoryAdapter(List<Historybean> historybeans, Context context, RecyclerView recyclerView) {
        mCommonRecylerAdapter = new CommonFiveRecylerAdapter(context, historybeans, R.layout.item_serach_info) {
            @Override
            public void convert(CommonRecylerHolder holder, Object item, int position, boolean isScrolling) {
                Historybean historybean = (Historybean) item;
                holder.setText(R.id.text_history_info, historybean.getName());
                holder.setOnRecyclerItemClickListener(R.id.liner_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mSetOnClick != null) {
                            mSetOnClick.getOnClick(historybean);
                        }
                    }
                });
            }

        };
        recyclerView.setAdapter(mCommonRecylerAdapter);
    }

    public interface SetOnClick {
        void getOnClick(Historybean historybean);

    }

    public void setOnClickListener(SetOnClick setOnClick) {
        this.mSetOnClick = setOnClick;
    }
}
