package com.hyj.containers.actiontitle;

import java.util.List;
import java.util.Map;

public class ActionItemImpl implements ActionItem{
	
	private CharSequence mTitle;
	
	private int mOrder;
	
	private int mId;
	
	private int mIcoRes;
	
	private int topDrawableId;
	
	private int mAction;
	
	private List<? extends Map<String, Object>> mList;
	
	public ActionItemImpl(CharSequence title,int order,int id){
		this.mTitle = title;
		this.mId = id;
		this.mOrder = order;
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

	public ActionItem setIco(int resId){
		this.mIcoRes = resId;
		return this;
	}

	@Override
	public ActionItem setId(int id) {
		this.mId = id;
		return this;
	}

	@Override
	public ActionItem setOrder(int order) {
		this.mOrder = order;
		return this;
	}

	@Override
	public ActionItem setAction(int actionRes) {
		this.mAction = actionRes;
		return this;
	}

	@Override
	public ActionItem setTitle(CharSequence title) {
		 this.mTitle = title;
		 return this;
	}

	public int getAction() {
		return mAction;
	}

	@Override
	public ActionItem setPopuWindowList(List<? extends Map<String, Object>> list) {
		this.mList = list;
		return this;
	}

	@Override
	public List<? extends Map<String, Object>> getPopuWindowList() {
		return mList;
	}

	@Override
	public int getTopDrawableId() {
		// TODO Auto-generated method stub
		return topDrawableId;
	}

	@Override
	public ActionItem setTopDrawableId(int topDrawableId) {
		// TODO Auto-generated method stub
		this.topDrawableId = topDrawableId;
		return this;
	}
	
}
