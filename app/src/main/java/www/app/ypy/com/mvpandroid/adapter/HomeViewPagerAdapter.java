package www.app.ypy.com.mvpandroid.adapter;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypu
 * on 2019/5/9 0009
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list = new ArrayList();

    public HomeViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        return list.get(position);
    }

    public int getCount() {
        return this.list.size();
    }

    public void addFragment(Fragment fragment) {
        this.list.add(fragment);
    }

    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }
}
