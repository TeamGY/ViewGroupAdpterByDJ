package com.hyj.containers.view.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.kemov.elecmobiletools.R;

import java.util.ArrayList;

public class EditItemView extends EditText implements ItemContainView{

	private String relation;
	
	private OnKeyListener mOnKeyListener;
	
	private OnClickListener mOnClickListener;
	
	private OnFocusChangeListener focusChangeListener;
	
	private boolean isTextWatcher = false;//�Ƿ�����༭��ĸı�

	private double dMax = 1000000.0;
	private double dMin = 0.0;
	private int dLen = 9;
	private int dDecimal = 3;
	private String suffix = null;
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
	public double getMax() {
		return dMax;
	}

	public void setMax(double dMax) {
		this.dMax = dMax;
	}

	public double getMin() {
		return dMin;
	}

	public void setMin(double dMin) {
		this.dMin = dMin;
	}

	public int getLen() {
		return dLen;
	}

	public void setLen(int dLen) {
		if(dLen <= 0) return;
		this.dLen = dLen;
	}

	public int getDecimal() {
		return dDecimal;
	}

	public void setDecimal(int dDecimal) {
		if(dDecimal < 0) return;
		this.dDecimal = dDecimal;
	}

	@Override
	public String getRelation() {
		return relation;
	}

	@Override
	public void setRelation(String relation) {
		this.relation = relation;
	}

	public EditItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
		setRelation(array.getString(R.styleable.ItemView_relation));
		array.recycle();

		initListner();
	}

	private void initListner(){

//		focusChangeListener = new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				format();
//				
//			}	
//		};
//		this.setOnFocusChangeListener(focusChangeListener);
	}
	public String jujstStringf(String text){
		ArrayList<String> ss = new ArrayList<String>(0);
		ss.add(" ");
		ss.add("A");
		ss.add("%");
		ss.add("kV");
		ss.add("V");
		int k = ss.size();
		for(int i = 0;i < k;i++){
			if(text.contains(ss.get(i))){
				text = text.replaceAll(ss.get(i), "");
			}	
		}
		return text;		
	}
	
	private String removeSuffix(String text){
		if(suffix != null && text.contains(suffix)){
			text = text.replaceAll(suffix, "");
		}
		return text;
		
	}
	private String addSuffix(String text){
		if(suffix != null){
			text += suffix;
		}
		return text;
		
	}
	
	public void format(){
		
		String s= getFormat();
		if(!s.equals(getText()))
		{
			setText(s);
			//hasValueChanged(this,s);
		}
	}
	
public String getFormat(){
		
		String s = getValue();
		
		s = addSuffix(s);
		
		return s;
	}


public String getValue(){
	
	String s = getText().toString();
	s = removeSuffix(s);
	
	double f = 0.0f;
	
	if(s.toString() == null || s.toString().equals("")|| s.toString().equals(".")){
		f = 0.0f;
	}
	else if(s.toString().contains(".")){

		try {
			f = Double.parseDouble(s.toString());	
		} catch (Exception e) {
			f = getMax();
		}
	}
	else {
		try {
			f = Integer.parseInt(s.toString());	
		} catch (Exception e) {
			f = getMax();
		}

	}
	if(f > dMax)
	{
		f =  dMax;
	}
	else if(f < dMin)
	{
		f = dMin;
	}
	
	s = String.format("%."+dDecimal+"f", f);
	
	return s;
}
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.mOnClickListener = l;
	}

	@Override
	public void setOnKeyListener(OnKeyListener l) {
		//this.mOnKeyListener = l;
		super.setOnKeyListener(l);
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
	
	public boolean isTextWatcher() {
		return isTextWatcher;
	}

	public void setTextWatcher(boolean isTextWatcher) {
		this.isTextWatcher = isTextWatcher;
	}
}
