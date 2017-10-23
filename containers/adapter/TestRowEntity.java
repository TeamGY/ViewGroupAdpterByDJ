package com.hyj.containers.adapter;

import android.content.Context;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 
 * @author hyj
 * 
 */
public class TestRowEntity implements Externalizable{
	
		public static final long serialVersionUID = 1;
		
	public Context context;
	public final static int BUTTON_ITEM = 0;
	public final static int EDIT_ITEM = 1;
	public final static int SPINNER_ITEM = 2;
	
	private String title = "";// ����
	private String text = ""; // ����
	private int type = -1;	// �ؼ�����  ��0���л���ť�� 1���༭�� , 2:������
	private int stringArrayId = -1;//����������
	
	private int inputType = android.text.InputType.TYPE_CLASS_PHONE;//��������
	private char[] mAcceptedChars;//�����ַ�
	public int len = 10;//����
	public double max = 100000.000;
	public double min = 0.000;

 
	//private List<String> list;
	//private String[] spinnerArray;//����������
	/*
	 * ����   ������
	 * ��	�ܣ����textΪnull����Ĭ��ΪStringArray�ĵ�һ��Ԫ��
	 */
	public void initTestRowEntity(Context context, String title, String text, int type,int stringArrayId) {

	//	super();
		
		this.context = context;
		this.title = title;
		this.text = text;
		this.type = type;
		this.stringArrayId = stringArrayId;

		if(this.text == null){
			this.text = context.getResources().getStringArray(stringArrayId)[0];
		//	LogUtil.v("vvv",  "new  text = " + this.text);
		}
	}
	
	/*
	 * ����str��StringArray�е�����������������򷵻�-1.
	 */
	public int getTextIndex(String str){
		
		if(str == null){
			return -1;
		}
		
		String[] sList = context.getResources().getStringArray(stringArrayId);
		for(int i = 0;i < sList.length;i++){
			if(str.equals(sList[i]))
				return i;
				
		}
		return -1;
	}
	public int getSelectIndex(){
		
		if(text == null){
			return -1;
		}
		
		String[] sList = context.getResources().getStringArray(stringArrayId);
		for(int i = 0;i < sList.length;i++){
			if(text.equals(sList[i]))
				return i;
				
		}
		return -1;
	}
	public void setText(int selectIndex){
		
//		if(text == null){
//			return;
//		}
		
		String[] sList = context.getResources().getStringArray(stringArrayId);
		text = sList[selectIndex];

	}
	
	public void initTestRowEntity(String title, String text, int type,int stringArrayId) {

		//super();
		this.title = title;
		this.text = text;
		this.type = type;
		this.stringArrayId = stringArrayId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	/*
	 * �����������������һ����������
	 */
	public String getNextTextOfBtn() {
		
		String[] sList = context.getResources().getStringArray(stringArrayId);
		if(sList.length == 2){
			if(text.equals(sList[0])){
				return sList[1];
			}else{
				return sList[0];
			}	
		}		
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getSpinnerArrayId() {
		return stringArrayId;
	}


	public void setSpinnerArrayId(int spinnerArrayId) {
		this.stringArrayId = spinnerArrayId;
	}
	
	
	/**********�༭����ش���***********/
	public void setEditFilter(int inputType, char[] acceptedChars)
	{
		this.inputType = inputType;
		this.mAcceptedChars = acceptedChars;

	}
	public void setEditAttr(double max, double min,int len)
	{
		this.max = max;
		this.min = min;
		this.len = len;
	}
	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public char[] getAcceptedChars() {
		if(mAcceptedChars == null){
			mAcceptedChars = new char[]{'0', '1', '2', '3', '4', '5','6', '7', '8', '9', '-', '.'};
		}
		return mAcceptedChars;
	}

	public void setAcceptedChars(char[] arg) {
		this.mAcceptedChars = arg;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		title = (String)in.readObject();
		text = (String)in.readObject();
		type = in.readInt();
		stringArrayId = in.readInt();
		inputType = in.readInt();
		len = in.readInt();
		max = in.readDouble();
		min = in.readDouble();		 
	}

	@Override
	public void writeExternal(ObjectOutput output) throws IOException {
		// TODO Auto-generated method stub
		output.writeObject(title);
		output.writeObject(text);		
		output.writeInt(type);
		output.writeInt(stringArrayId);
		output.writeInt(inputType);
		output.writeInt(len);
		output.writeDouble(max);
		output.writeDouble(min);
	}

}
