package com.hyj.containers.view.common;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kemov.elecmobiletools.R;

public class ImageItemView extends ImageView implements ItemContainView{

	private String relation;
	
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public ImageItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		array.recycle();
	}
}
