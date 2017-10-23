package com.hyj.containers.tabbar;

import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;

/**
 * @author �������Ҫ�����Ǳ��水ť������
 *
 */
public class TabBarItemImpl{
	
	private CharSequence mTitle;
	
	private int mOrder;
	
	private int mId;
	
	private int mIcoRes;
	
	private int textSize = 22;
	
	private int checkIcon;
	
	private int color = Color.WHITE;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	private int width = LayoutParams.WRAP_CONTENT;
	
	private int height = LayoutParams.WRAP_CONTENT;
	
	public int getHeight() {
		return height;
	}

	public TabBarItemImpl setHeight(int height) {
		this.height = height;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public TabBarItemImpl setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getTextSize() {
		return textSize;
	}

	public TabBarItemImpl setTextSize(int textSize) {
		this.textSize = textSize;
		return this;
	}

	public TabBarItemImpl(CharSequence title,int order,int id){
		this.mTitle = title;
		this.mId = id;
		this.mOrder = order;
	}

	public int getCheckIcon() {
		return checkIcon;
	}

	public TabBarItemImpl setCheckIcon(int topDrawableId) {
		this.checkIcon = topDrawableId;
		return this;
	}
	
	public CharSequence getTitle() {
		return mTitle;
	}

	public int getOrder() {
		return mOrder;
	}

	public int getId() {
		return mId;
	}

	public int getIcoRes() {
		return mIcoRes;
	}

	public TabBarItemImpl setIco(int resId){
		this.mIcoRes = resId;
		return this;
	}

	public TabBarItemImpl setId(int id) {
		this.mId = id;
		return this;
	}

	public TabBarItemImpl setOrder(int order) {
		this.mOrder = order;
		return this;
	}

	public TabBarItemImpl setTitle(CharSequence title) {
		 this.mTitle = title;
		
		 return this;
	}
}
