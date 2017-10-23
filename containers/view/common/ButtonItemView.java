package com.hyj.containers.view.common;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.kemov.elecmobiletools.R;

public class ButtonItemView extends Button implements ItemContainView{

	private String relation;

	private int nType = 0;//����:0-Ĭ�ϣ�1-�л�

	private int arrayId = -1;


	public int getType() {
		return nType;
	}

	public void setType(int nType) {
		this.nType = nType;
	}

	public int getArrayId() {
		return arrayId;
	}

	public void setArrayId(int arrayId) {
		this.arrayId = arrayId;
		
	}


	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public ButtonItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		array.recycle();

		initListner();
	}

	private void initListner(){

//		OnClickListener mClicListener = new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ButtonItemView buttonItemView = (ButtonItemView) v;
//				switchText();
//	
//			//	this.onClick(buttonItemView);
//			}
//		};
//		this.setOnClickListener(mClicListener);

	}

	public void switchText() {
		if(arrayId >= 0){
			String[] sList = getResources().getStringArray(arrayId);

			if(sList.length == 2){
				if(this.getText().equals(sList[0])){
					this.setText(sList[1]);
				}else{
					this.setText(sList[0]);
				}	
			}	
		}
	}
}
