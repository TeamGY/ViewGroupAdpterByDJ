package com.hyj.containers.popuwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public class PopupWindows {
	
	protected PopupWindow mWindow;
	
	protected View contentView;
	
	protected Drawable mBackground;
	
	private View mView;
	
	private int gravity;
	
	private int height;
	
	private int width;
	
	private int offX;
	
	private int offY;

	public int getOffX() {
		return offX;
	}

	public void setOffX(int offX) {
		this.offX = offX;
	}

	public int getOffY() {
		return offY;
	}

	public void setOffY(int offY) {
		this.offY = offY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public View getContentView() {
		return contentView;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}
	
	public void setBackgroundDrawable(Drawable background) {
		mBackground = background;
	}
	
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		mWindow.setOnDismissListener(listener);
	}
	
	public void dismiss() {
		mWindow.dismiss();
	}

	public PopupWindows(Context context,View mView,int contentViewId,int width,int height,int gravity) {
		this.mView = mView;
		this.gravity = gravity;
		this.contentView = LayoutInflater.from(context).inflate(contentViewId, null);
		mWindow = new PopupWindow(contentView, width, height);
		setHeight(mWindow.getHeight());
	}
	
	public void preShow() {
		if (contentView == null){
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		if (mBackground == null){
			mWindow.setBackgroundDrawable(new BitmapDrawable());
		}else{
			mWindow.setBackgroundDrawable(mBackground);
		}
		mWindow.setTouchable(true);
		mWindow.setFocusable(true);
		mWindow.setOutsideTouchable(true);
		int[] location = new int[2];
		mView.getLocationInWindow(location);
		if(getGravity()==Gravity.TOP){
			popupShowViewTop(location);
		} else if (getGravity()==Gravity.BOTTOM) {
			popupShowViewBottom(location);
		} 
	}
	
	private void popupShowViewBottom(int[] location) {
		mWindow.showAsDropDown(mView, getOffX(), getOffY());
	}

	private void popupShowViewTop(int[] location){
		mWindow.showAtLocation(mView, Gravity.NO_GRAVITY, location[0]+getOffX(), location[1]-this.getHeight());
	}
}
