package com.learning.baseprojectforkan.common.dialogs;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * 作者 : andy
 * 日期 : 15/11/8 20:42
 * 邮箱 : andyxialm@gmail.com
 * 描述 : 换算工具类
 */
public class DisplayUtil {

    /**
     * Convert from dp units to px (pixels) according to the resolution
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * According to the resolution, the unit is converted from px (pixel) to dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得屏幕尺寸
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        return point;
    }
}