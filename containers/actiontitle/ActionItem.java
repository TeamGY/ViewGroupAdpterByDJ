package com.hyj.containers.actiontitle;

import java.util.List;
import java.util.Map;

public interface ActionItem {
	
	public ActionItem setPopuWindowList(List<? extends Map<String, Object>> list);
	
	public List<? extends Map<String, Object>> getPopuWindowList();
	
	public ActionItem setTitle(CharSequence title);
	
	public ActionItem setIco(int resId);
	
	public ActionItem setId(int id);
	
	public ActionItem setOrder(int order);
	
	public ActionItem setAction(int actionRes);
	
	public int getOrder();
	
	public int getId();
	
	public CharSequence getTitle();

	
	public int getTopDrawableId();
	
	public ActionItem setTopDrawableId(int topDrawableId);
	
}
