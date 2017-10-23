package com.hyj.containers.menu;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ��Ҫ�����Ǵ�����ť���Զ��󲢱����ڸ��������
 *
 */
public class MenuImpl{
	
	private MenuItemImpl rightBarItem;
	
	private List<MenuItemImpl> leftBarItems;
	
	public MenuImpl(Context context) {
		leftBarItems = new ArrayList<MenuItemImpl>();
	}

	public MenuItemImpl getRightBarItem() {
		return rightBarItem;
	}

	public List<MenuItemImpl> getLeftBarItems() {
		return leftBarItems;
	}
	
	public MenuItemImpl addLeftBar(CharSequence title){
		MenuItemImpl menuItemImpl = add(title,0,0);
		leftBarItems.add(menuItemImpl);
		return menuItemImpl;
	}
	
	public MenuItemImpl addRightBar(CharSequence title){
		rightBarItem = add(title,0,0);
		return rightBarItem;
	}
	
	public MenuItemImpl add(CharSequence title,int itemId,int orderId){
		return new MenuItemImpl(title,itemId,orderId);
	}
	
	public void clear(){
		rightBarItem = null;
		leftBarItems.clear();
	}

}
