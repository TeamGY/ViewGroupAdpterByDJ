package com.hyj.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


//获得屏幕相关的辅助类
public class distinguishDeviceUtil
{
	private static final String tag = "com.hyj.utils.distinguishDeviceUtil";

	
	/**
	 * 看是否有通话功能
	 */
	public static boolean isTabletDevice(Context mContext) {
		TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_NONE) {//no function of call
			LogUtil.i(tag, "is Tablet!");
			return true;
		} else {
			LogUtil.i(tag,"is phone!");
			return false;
		}
	}
	/**
	 * 检测是平板（电视）还是手机(通过判断屏幕尺寸，此方法中认为尺寸大于6寸的都是平板)
	 * @return
	 */
	public static boolean isPad(Activity instense) {

		DisplayMetrics dm = new DisplayMetrics();
		instense.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		LogUtil.v(tag, "width is "+wi+" height is "+hi);
		double screenInches = Math.sqrt(x+y);

		LogUtil.v(tag, "screenInches is "+screenInches);

		// MsgUtils.SendSingleMsg(splash.handlerTools,"screenInches is "+screenInches , HandlerUtils.SHOW_NORMAL_TOAST);

		// 大于6尺寸则为Pad
		if (screenInches >= 6.0) {
			return true;
		}

		return false;
	}
}
