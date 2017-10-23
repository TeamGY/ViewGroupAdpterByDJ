package com.hyj.containers.actiontitle;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class ActionMenuImpl implements ActionMenu{
	
	private ActionItem leftBarItem;
	
	private List<ActionItem> rightBarItems;
	
	private Context mContext;
	
	public ActionMenuImpl(Context context) {
		rightBarItems = new ArrayList<ActionItem>(0);
		this.mContext = context;
	}
		
	public ActionItem getLeftBarItem() {
		return leftBarItem;
	}

	public List<ActionItem> getRightBarItems() {
		return rightBarItems;
	}

	public ActionItem addLeftBar(CharSequence title){
		return leftBarItem = this.addLeftBar(title, 0, 0);
	}
	
	public ActionItem addLeftBar(int titleResId){
		return leftBarItem = this.addLeftBar(titleResId, 0, 0);
	}
	
	public ActionItem addLeftBar(CharSequence title,int orderId,int itemId){
		return leftBarItem = add(title, itemId, orderId);
	}
	
	public ActionItem addLeftBar(int titleResId,int orderId,int itemId){
		return leftBarItem = add(mContext.getResources().getString(titleResId), itemId, orderId);
	}
	
	public ActionItem addRightBar(CharSequence title){
		ActionItem actionItem = add(title,0,0);
		rightBarItems.add(actionItem);
		return actionItem;
	}
	
	public ActionItem addRightBar(int titleResId){
		ActionItem actionItem = add(mContext.getResources().getString(titleResId),0,0);
		rightBarItems.add(actionItem);
		return actionItem;
	}
	
	public ActionItem addRightBar(CharSequence title,int orderId,int itemId){
		ActionItem actionItem = add(title,orderId,itemId);
		rightBarItems.add(actionItem);
		return actionItem;
	}
	
	public ActionItem addRightBar(int titleResId,int orderId,int itemId){
		ActionItem actionItem = add(mContext.getResources().getString(titleResId),orderId,itemId);
		rightBarItems.add(actionItem);
		return actionItem;
	}
	
	public ActionItem add(CharSequence title,int itemId,int orderId){
		return new ActionItemImpl(title,itemId,orderId);
	}
	
	public void clear(){
		leftBarItem = null;
		rightBarItems.clear();
	}

}
