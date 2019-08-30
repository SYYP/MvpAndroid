package www.app.ypy.com.mvpandroid.utils;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;


import java.lang.reflect.Field;

/**
 * Created by ypu
 * 利用反射，改变BottomNavigationView的 item 中 mShiftingMode 的值
 * on 2019/5/9 0009
 */
public class BottomNavigationViewHelper {

    private BottomNavigationViewHelper() {
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)navigationView.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for(int i = 0; i < menuView.getChildCount(); ++i) {
                BottomNavigationItemView itemView = (BottomNavigationItemView)menuView.getChildAt(i);
                 itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (IllegalAccessException | NoSuchFieldException var5) {

        }

    }
}
