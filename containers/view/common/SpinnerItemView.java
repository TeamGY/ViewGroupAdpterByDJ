package com.hyj.containers.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.kemov.elecmobiletools.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerItemView extends Spinner implements ItemContainView{

	private String relation;
	
	private OnClickListener mOnClickListener;
	
	private int arrayId;

	private ArrayAdapter<String> adpter;
	
	
	
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

	public SpinnerItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		setArrayId(array.getResourceId(R.styleable.ItemView_string_array, 0));
		array.recycle();
		setAdapter(null);
	}
	@Override
	public void setAdapter(SpinnerAdapter a) {
		if(a==null){
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, getContext().getResources().getStringArray(getArrayId()));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			super.setAdapter(adapter);
		}else{
			super.setAdapter(a);
		}
	}
	
	public void setOnClickListener(final OnClickListener onClickListener) {
		this.mOnClickListener = onClickListener; 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(mOnClickListener!=null){
				mOnClickListener.onClick(this);
			}
		}
		return super.onTouchEvent(event);
	}
	
	// ���������Spinner
	public void fillSpinner(Context context,String[] items) {

		List<String> list = new ArrayList<String>(0);
		for (int i = 0; i < items.length; i++) {
			list.add(items[i]);
		}
		adpter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(adpter);
	}
	
	public void fillSpinner(Context context,int arrayID) {

//		List<String> list = new ArrayList<String>(0);
//		for (int i = 0; i < items.length; i++) {
//			list.add(items[i]);
//		}
		String[] list = this.getResources().getStringArray(arrayID);	
		adpter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(adpter);
	}
}
