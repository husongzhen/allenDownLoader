package com.allen.code.allendownloader.utils;

import android.content.Context;

/**
 * 屏幕属性工具类
 *
 * @author Alien
 */
public class ScreenAdaptiveHelper {

    public static int status_height; // 状态栏的高度
    public static double SCREEN_SIZE; // 屏幕的尺寸
    public static int densityDpi;
    public static float density;

    public static int height; // 屏幕的高度
    public static int width; // 屏幕的宽度
    public static int wp; // 屏幕宽度的1/80
    public static int hp;// 屏幕高度的1/80

    public static void init(Context context) {
        DeviceInfor.init(context);
        ScreenAdaptiveHelper.SCREEN_SIZE = DeviceInfor.getScreenSize();
        height = DeviceInfor.getSH();
        width = DeviceInfor.getSW();
        wp = width / 80;
        hp = height / 80;
        densityDpi = DeviceInfor.getDensityDpi();
        density = DeviceInfor.getDensity();
    }

}
