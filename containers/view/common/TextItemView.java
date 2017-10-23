package com.hyj.containers.view.common;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kemov.elecmobiletools.R;

public class TextItemView extends TextView implements ItemContainView{

	private String relation;
	
	private boolean underLine;
	
	public boolean isUnderLine() {
		return underLine;
	}

	public void setUnderLine(boolean underLine) {
		this.underLine = underLine;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public TextItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		setUnderLine(array.getBoolean(R.styleable.ItemView_underline, false));
		array.recycle();
		getPaint().setUnderlineText(isUnderLine());
	}
}
