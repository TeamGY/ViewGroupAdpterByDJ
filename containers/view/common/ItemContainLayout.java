package com.hyj.containers.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Map;

public abstract class ItemContainLayout extends LinearLayout{

	public ItemContainLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public abstract Map<String, Integer> getMapRelation();

	public abstract void setMapRelation(Map<String, Integer> mapRelation);
}
