package com.hyj.containers.actiontitle;

import java.util.List;


public interface ActionMenu {
	
	public ActionItem addLeftBar(CharSequence title);
	
	public ActionItem addLeftBar(int titleResId);
	
	public ActionItem addLeftBar(CharSequence title,int orderId,int itemId);
	
	public ActionItem addLeftBar(int titleResId,int orderId,int itemId);
	
	public ActionItem getLeftBarItem();
	
	public ActionItem addRightBar(CharSequence title);
	
	public ActionItem addRightBar(int titleResId);
	
	public ActionItem addRightBar(CharSequence title,int orderId,int itemId);
	
	public ActionItem addRightBar(int titleResId,int orderId,int itemId);
	
	public List<ActionItem> getRightBarItems();
	
	public ActionItem add(CharSequence title,int itemId,int orderId);
	
}
