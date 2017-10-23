package com.hyj.containers.menu;

import android.view.ViewGroup.LayoutParams;

/**
 * @author �������Ҫ�����Ǳ��水ť������
 *
 */
public class MenuItemImpl{
	
	private CharSequence mTitle;
	
	private int mOrder;
	
	private int mId;
	
	private int mIcoRes;
	
	private int textSize = 16;
	
	private int topDrawableId;
	
	private int width = LayoutParams.WRAP_CONTENT;
	
	private int height = LayoutParams.WRAP_CONTENT;
	
	public int getHeight() {
		return height;
	}

	public MenuItemImpl setHeight(int height) {
		this.height = height;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public MenuItemImpl setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getTextSize() {
		return textSize;
	}

	public MenuItemImpl setTextSize(int textSize) {
		this.textSize = textSize;
		return this;
	}

	public MenuItemImpl(CharSequence title,int order,int id){
		this.mTitle = title;
		this.mId = id;
		this.mOrder = order;
	}

	public int getTopDrawableId() {
		return topDrawableId;
	}

	public MenuItemImpl setTopDrawableId(int topDrawableId) {
		this.topDrawableId = topDrawableId;
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

	public MenuItemImpl setIco(int resId){
		this.mIcoRes = resId;
		return this;
	}

	public MenuItemImpl setId(int id) {
		this.mId = id;
		return this;
	}

	public MenuItemImpl setOrder(int order) {
		this.mOrder = order;
		return this;
	}

	public MenuItemImpl setTitle(CharSequence title) {
		 this.mTitle = title;
		 return this;
	}
}
