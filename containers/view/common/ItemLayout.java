package com.hyj.containers.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemLayout extends ItemContainLayout {
	private static final String TAG = "ItemLayout";
	private List<ItemContainView> itemContainViews;
	
	private Map<String, Integer> mapRelation;
	
	
	public List<ItemContainView> getItemContainViews() {
		return itemContainViews;
	}

	public Map<String, Integer> getMapRelation() {
		return mapRelation;
	}

	public void setMapRelation(Map<String, Integer> mapRelation) {
		this.mapRelation = mapRelation;
	}

	public ItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		itemContainViews = new ArrayList<ItemContainView>(0);
	}

	@Override
	protected void onFinishInflate() {
		mapRelation = new HashMap<String, Integer>();
		findRelation(this);
	}
	
	public void findRelation(ViewGroup viewGroup){
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			if(viewGroup.getChildAt(i) instanceof LinearLayout 
					|| viewGroup.getChildAt(i) instanceof RelativeLayout 
					|| viewGroup.getChildAt(i) instanceof TableLayout 
					|| viewGroup.getChildAt(i) instanceof TableRow
					|| viewGroup.getChildAt(i) instanceof FrameLayout){
				findRelation((ViewGroup) viewGroup.getChildAt(i));
			}else{
				if(viewGroup.getChildAt(i) instanceof ItemContainView){
					ItemContainView item = (ItemContainView) viewGroup.getChildAt(i);
					if(item.getRelation()!=null&&!item.getRelation().equals("")){
						mapRelation.put(item.getRelation(), Integer.valueOf(item.getId()));
					}
					
				//	if ( item.getArrayId() != 0) {
				//		mapRelation.put(String.valueOf(item.getArrayId()), Integer.valueOf(item.getId()));
				//	}
				}
			}
		}
		setMapRelation(mapRelation);
	}

	/*
	add 05.11.2017
	 */
	public int getPosition(){

		String sTag = this.getTag().toString();

		int index = sTag.indexOf("position=");

		if (index >= 0){
			int index2 = sTag.indexOf(",", index);

			if (index2 > 0){
				return Integer.parseInt(sTag.substring(index+9, index2));
			}

		}



		return -1;
	}

}