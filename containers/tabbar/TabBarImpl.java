package com.hyj.containers.tabbar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ��Ҫ�����Ǵ�����ǩ��ť���Զ��󲢱����ڸ��������
 *
 */
public class TabBarImpl{
	
	private TabBarItemImpl rightBarItem;
	
	private List<TabBarItemImpl> leftBarItems;
	
	public TabBarImpl(Context context) {
		leftBarItems = new ArrayList<TabBarItemImpl>(0);
	}

	public TabBarItemImpl getRightBarItem() {
		return rightBarItem;
	}

	public List<TabBarItemImpl> getLeftBarItems() {
		return leftBarItems;
	}
	
	public TabBarItemImpl addLeftBar(CharSequence title){
		TabBarItemImpl menuItemImpl = add(title,0,0);
		leftBarItems.add(menuItemImpl);
		return menuItemImpl;
	}
	
	public TabBarItemImpl addRightBar(CharSequence title){
		rightBarItem = add(title,0,0);
		return rightBarItem;
	}
	
	public TabBarItemImpl add(CharSequence title,int itemId,int orderId){
		return new TabBarItemImpl(title,itemId,orderId);
	}
	
	public void clear(){
		rightBarItem = null;
		leftBarItems.clear();
	}

}
