package com.allen.code.allendownloader.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 屏幕属性工具类
 * 
 * @author Alien
 * 
 */
public class DeviceInfor {

	private static int status_height; // 状态栏的高度
	private static double SCREEN_SIZE; // 屏幕的尺寸
	private static float density;
	private static int densityDpi;
	private static int height; // 屏幕的高度
	private static int width; // 屏幕的宽度
	private static Context context;
	private static WindowManager wmManager;
	private static TelephonyManager tm;
	private static WifiManager wifiManager;

	public static void init(Context context) {
		DeviceInfor.context = context;
		wmManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		initScreen();
	}

	private static void initScreen() {
		DisplayMetrics metric = new DisplayMetrics();
		wmManager.getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		height = metric.heightPixels;
		density = metric.density;
		densityDpi = metric.densityDpi;
		double diagonalPixels = Math.sqrt(Math.pow(width, 2)
				+ Math.pow(height, 2));
		SCREEN_SIZE = diagonalPixels / (160 * density);

		// 获取状态栏高度
		try {
			Class<?> cl = Class.forName("com.android.internal.R$dimen");
			Object obj = cl.newInstance();
			Field field = cl.getField("status_bar_height");

			int x = Integer.parseInt(field.get(obj).toString());
			status_height = context.getResources().getDimensionPixelSize(x); // 状态栏的
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getStateHeight() {
		return status_height;
	}

	public static double getScreenSize() {
		return SCREEN_SIZE;
	}

	public static int getSW() {
		return width;
	}

	public static int getSH() {
		return height;
	}

	public static float getDensity() {
		return density;
	}

	public static int getDensityDpi() {
		return densityDpi;
	}

	// **********获取设备信息******************************************

	public static CellLocation getCellLocation() {
		return tm.getCellLocation();
	}

	/*
	 * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
	 * available.
	 */
	public static String getId() {
		return tm.getDeviceId();
	}

	/*
	 * 获取手机号
	 */
	public static String getPhone() {
		return tm.getLine1Number();
	}

	/*
	 * 服务商名称
	 */
	public static String getServiceName() {
		return tm.getSimOperatorName();
	}

	/*
	 * 获取mac
	 */
	public static String getMacAddress() {
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}

	/*
	 * 获取nNetworkType
	 */
	public static String getNetworkType() {
		return tm.getNetworkType() + "";
	}

	/*
	 * 获取PhoneType
	 */
	public static String getPhoneType() {
		return tm.getPhoneType() + "";
	}

	/*
	 * 获取MODEL
	 */
	public static String getMODEL() {
		return android.os.Build.MODEL;
	}

	/*
	 * 获取VERSION
	 */
	public static String getVERSION() {
		return android.os.Build.VERSION.RELEASE;
	}

	/*
	 * 获取VERSION
	 */
	public static String getSimSerialNumber() {
		return tm.getSimSerialNumber();
	}

	public static String getDe() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
		sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
		sb.append("\nLine1Number = " + tm.getLine1Number());
		sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		sb.append("\nNetworkType = " + tm.getNetworkType());
		sb.append("\nPhoneType = " + tm.getPhoneType());
		sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		sb.append("\nSimOperator = " + tm.getSimOperator());
		sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
		sb.append("\nandroid sdk version = " + android.os.Build.VERSION.SDK_INT);
		sb.append("\nandroid mode = " + android.os.Build.MODEL + ","
				+ android.os.Build.VERSION.RELEASE);
		return sb.toString();
	}

}
