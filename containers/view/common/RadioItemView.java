package com.hyj.containers.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.kemov.elecmobiletools.R;

/***********************����*************************/
/*
	<com.hyj.containers.view.common.CheckItemView
	    android:id="@+id/check"
	    android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="8"
        android:gravity="center"
        android:textStyle="bold" 
        netanalyzer:relation="check"
	    />
 */
public class RadioItemView extends RadioButton implements ItemContainView{

	private String relation ;

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public RadioItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		array.recycle();
	}
}
