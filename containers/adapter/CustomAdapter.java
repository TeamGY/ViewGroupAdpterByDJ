package com.hyj.containers.adapter;
/**
 * @author Administrator
 * �޸���Ա�����޽�
 * �޸���־��2016.04.08 ���Ӽ����������غ���
 **/

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyj.containers.view.common.ButtonItemView;
import com.hyj.containers.view.common.EditItemView;
import com.hyj.containers.view.common.ItemContainLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressLint("ResourceAsColor")
public class CustomAdapter extends BaseAdapter implements Filterable {

	private static final String TAG = "CustomAdapter";
	private boolean mBinderTag;

	private Context mContext;

	private View.OnClickListener mOnClickListener;
	private SparseArray<View.OnClickListener> mlistenView;

	private Map<Integer, OnKeyListener> mOnKeyListeners;

	private Map<Integer, OnFocusChangeListener> mFocusChangelistenView;

	private OnLongClickListener OnLongClickListener;
	private Map<Integer, OnLongClickListener> mLongClickListenenView;	


	private List<View> mItemContainLayout;

	public List<View> getmItemContainLayout() {
		return mItemContainLayout;
	}

	private List<? extends Map<String, ?>> mData;

	public void setmData(List<? extends Map<String, ?>> mData) {
		this.mData = mData;
	}

	private int mResource;

	private LayoutInflater mInflater;

	private boolean enabled_listlayout = true;//列表布局是否可以点击

	public CustomAdapter(Context context, List<? extends Map<String, ?>> mData,
			int mResource, SparseArray<View.OnClickListener> mListenView,
			Map<Integer, OnFocusChangeListener> mFocusChangelistenView,
			Map<Integer, OnLongClickListener> mLongClickListenenView,
			boolean mBinderTag) {
		this.mContext = context;
		this.mData = mData;
		this.mResource = mResource;
		this.mlistenView = mListenView;

		this.mFocusChangelistenView = mFocusChangelistenView;	
		this.mLongClickListenenView = mLongClickListenenView;
		this.mBinderTag = mBinderTag;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemContainLayout = new ArrayList<View>(0);

	}
	/*
	 * ���Ӱ�������mOnKeyListeners
	 */
	public CustomAdapter(Context context, List<? extends Map<String, ?>> mData,
			int mResource, 
			Map<Integer, OnKeyListener> mOnKeyListeners,
			SparseArray<View.OnClickListener> mListenView,
			Map<Integer, OnFocusChangeListener> mFocusChangelistenView,
			Map<Integer, OnLongClickListener> mLongClickListenenView,
			boolean mBinderTag) {
		this.mContext = context;
		this.mData = mData;
		this.mResource = mResource;
		this.mlistenView = mListenView;
		this.mOnKeyListeners = mOnKeyListeners;
		this.mFocusChangelistenView = mFocusChangelistenView;	
		this.mLongClickListenenView = mLongClickListenenView;
		this.mBinderTag = mBinderTag;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemContainLayout = new ArrayList<View>(0);

	}
	public int getCount() {
		return mData.size();
	}

	private String itemAttr;

	public Object getItem(int position) {
		Object result = "";
		if (itemAttr != null)
			if (mData.get(position).containsKey(itemAttr))
				result = mData.get(position).get(itemAttr);
			else
				result = mData.get(position);

		return result;
	}

	public void setItemAttr(String attr) {
		this.itemAttr = attr;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}


	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		ItemContainLayout v = null;

		if (convertView == null) {
			v = (ItemContainLayout) mInflater.inflate(resource, parent,false);


			if (mOnClickListener != null){
				v.setOnClickListener(mOnClickListener);
			}
			else if (mlistenView != null) {
				int size = mlistenView.size();
				for(int i = 0;i < size;i++){
					int id = mlistenView.keyAt(i);

					View view = (View) v.findViewById(id);
					view.setOnClickListener(mlistenView.get(id));
					view.setTag(position);
				}

				//				Iterator<Integer> idIter = mlistenView.keySet().iterator();
				//				while (idIter.hasNext()) {
				//					int id = idIter.next();
				//					View view = (View) v.findViewById(id);
				//					view.setOnClickListener(mlistenView.queryForId(id));
				//					view.setTag(position);
				//				}
			}
			if (mFocusChangelistenView != null) {
				Iterator<Integer> idIter = mFocusChangelistenView.keySet().iterator();
				while (idIter.hasNext()) {
					int id = idIter.next();
					View view = (View) v.findViewById(id);
					view.setOnFocusChangeListener(mFocusChangelistenView.get(id));
					view.setTag(position);
				}
			}
			if (getOnLongClickListener() != null){
				v.setOnLongClickListener(getOnLongClickListener());
			}
			else  if (mLongClickListenenView != null) {
				Iterator<Integer> idIter = mLongClickListenenView.keySet().iterator();
				while (idIter.hasNext()) {
					int id = idIter.next();
					View view = (View) v.findViewById(id);
					view.setOnLongClickListener(mLongClickListenenView.get(id));
					view.setTag(position);
				}
			}
			if (mOnKeyListeners != null) {
				Iterator<Integer> idIter = mOnKeyListeners.keySet().iterator();
				while (idIter.hasNext()) {
					int id = idIter.next();
					View view = (View) v.findViewById(id);
					view.setOnKeyListener(mOnKeyListeners.get(id));
					view.setTag(position);
				}
			}
			mItemContainLayout.add(v);
		} else {
			v = (ItemContainLayout) convertView;

		}

		bindView(position, v);
		return v;
	}

	private void bindView(int position, View view) {
		@SuppressWarnings("unchecked")
		final Map<String, Object> dataSet = (Map<String, Object>) mData.get(position);
		if (dataSet == null) {
			return;
		}
		if (mBinderTag) {
			dataSet.put("position", position);
			view.setTag(dataSet);
			//view.setTag(position);
		}

		ItemContainLayout itemContainLayout = (ItemContainLayout) view;
		Map<String, Integer> mapRelation = itemContainLayout.getMapRelation();
		if(mapRelation.isEmpty()){
			return ;
		}
		Iterator<String> iterator = (Iterator<String>) mapRelation.keySet().iterator();
		//int nCol = -1;
		while(iterator.hasNext()){

			String relation = iterator.next();
			if(!dataSet.containsKey(relation)){
				continue ;
			}

			int id = mapRelation.get(relation);
			final View v = view.findViewById(id);
			if (v != null) {
				v.setTag(position);

				if (!isEnabled_listlayout()){
					((View) v).setEnabled(false);
				}
				final Object data = dataSet.get(relation);
				String text = data == null ? "" : data.toString();
				if (text == null) {
					text = "";
				}
				//nCol ++;
				String bColor = "BackgroundColor_"+relation;

				if(dataSet.get(bColor) != null)
				{

					v.setBackgroundColor((Integer) dataSet.get(bColor));
					//					if (v instanceof TextView) {
					//
					//						 ((TextView) v).setTextColor((Integer) dataSet.queryForId(bColor));
					//					}
				}

				String Key_ARRAYID = "ARRAYID_"+relation;	

				if(dataSet.get(Key_ARRAYID) != null)
				{
					ButtonItemView btn= (ButtonItemView)v;
					btn.setArrayId((Integer) dataSet.get(Key_ARRAYID));
				}

				/*********�༭���������*********/
				//				try{
				String Key_MAX = "MAX_"+relation;	 
				if(dataSet.get(Key_MAX) != null)
				{
					EditItemView EDIT= (EditItemView)v;
					EDIT.setMax((Double) dataSet.get(Key_MAX));
				}

				String Key_MIN = "MIN_"+relation;	
				if(dataSet.get(Key_MIN) != null)
				{
					EditItemView EDIT= (EditItemView)v;
					EDIT.setMin((Double) dataSet.get(Key_MIN));
				}

				String Key_LEN = "LEN_"+relation;	
				if(dataSet.get(Key_LEN) != null)
				{
					EditItemView EDIT= (EditItemView)v;
					EDIT.setLen((Integer) dataSet.get(Key_LEN));
				}

				String Key_DECIMAL = "DECIMAL_"+relation;	
				if(dataSet.get(Key_DECIMAL) != null)
				{
					EditItemView EDIT= (EditItemView)v;
					EDIT.setDecimal((Integer) dataSet.get(Key_DECIMAL));
				}

				String Key_SUFFIX = "SUFFIX_"+relation;	 
				if(dataSet.get(Key_SUFFIX) != null)
				{
					EditItemView EDIT= (EditItemView)v;
					EDIT.setSuffix((String) dataSet.get(Key_SUFFIX));
				}


				if (v instanceof Checkable) {


					if (data instanceof Boolean) {
						String fColor = "TextColor_"+relation;

						if(dataSet.get(fColor) != null)
						{
							((RadioButton) v).setTextColor((Integer) dataSet.get(fColor));
						}

						((Checkable) v).setChecked((Boolean) data);

					} else if (v instanceof TextView) {
						setViewText((TextView) v, text);
					} else {
						throw new IllegalStateException(v.getClass().getName()
								+ " should be bound to a Boolean, not a "
								+ (data == null ? "<unknown type>"
										: data.getClass()));
					}
				}
				else if (v instanceof EditText) {

					setEditText((EditText)v,text);
				}
				else if (v instanceof TextView) {

					String fColor = "TextColor_"+relation;

					if(dataSet.get(fColor) != null)
					{
						((TextView) v).setTextColor((Integer) dataSet.get(fColor));
					}

					setViewText((TextView) v, text);
				}

				else if (v instanceof Button) {

					setButtonText((Button)v,text);							

				}
				else if (v instanceof ImageView){
					if(data instanceof int[]){
						setViewImage((ImageView)v, (int[]) data);
					}else{
						setViewImage((ImageView)v, (Integer) data);
					}
				} else if (v instanceof RatingBar) {
					setRatingBar((RatingBar) v, (Integer) data);
				}else if(v instanceof Spinner){

					String []items = relation.split("\\|");

					if (items.length == 2) {
						setSpinnerContent((Spinner)v,text,dataSet.get(items[1]));

					}
					else {
						if(data instanceof String[]){
							setSpinnerContent((Spinner)v, (String[]) data);
						}else {
							setSpinnerContent((Spinner)v, text);
						}
					}
				}else {
					throw new IllegalStateException(v.getClass().getName()
							+ " is not a "
							+ " view that can be bounds by this SimpleAdapter");
				}

			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void setSpinnerContent(Spinner v,String data,Object obj) {

		if (obj == null) {
			//	setSpinnerContent(v,data);
		} else {
			ArrayAdapter  adapter= (ArrayAdapter) v.getAdapter();

			if (obj instanceof String []) {
				adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, (String [])obj);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				v.setAdapter(adapter);
			}
		}

		int nSel = 0;
		try
		{
			nSel = Integer.parseInt(data);
		} catch (NumberFormatException e ) {
			nSel = 0;
		}

		v.setSelection(nSel,false);
		v.setVisibility(View.VISIBLE);
	}



	private void setRatingBar(RatingBar v, int data) {
		v.setRating(data);
	}

	/*
	 * data �ĵ�0�����ݱ�ʾ��ʼ������
	 */
	private void setSpinnerContent(Spinner v, String data[]) {
		int k = data.length -1;
		String[] dataTemp = new String[k];
		for(int i = 0;i < k;i++)
		{
			dataTemp[i] = data[i+1];
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, dataTemp);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		v.setAdapter(adapter);
		v.setSelection(Integer.parseInt(data[0]),true);

	}

	private void setSpinnerContent(Spinner v, String data) {
		int nSel = 0;	
		try{
			nSel = Integer.parseInt(data);
		} catch (NumberFormatException e ) {
			nSel = 0;
		}

		v.setSelection(nSel, true);
	}

	private void setViewImage(ImageView v, int value) {
		if(value!=0){
			v.setImageResource(value);
		}else{
			v.setImageDrawable(null);
		}
	}

	private void setViewImage(ImageView v, int[] value) {
		if(value[0]!=0){
			v.setImageResource(value[0]);
		}else{
			v.setImageDrawable(null);
		}
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(value[1], 0, 6, 0);
		v.setLayoutParams(params);
	}

	private void setViewText(TextView v, String text) {
		v.setText(text);
	}
	private void setEditText(EditText v,String text){
		v.setText(text);

	}
	private void setButtonText(Button v,String text){
		v.setText(text);
	}

	public Filter getFilter() {
		return null;
	}

	public String getItemValue(int row, int col) {

		Map<String, Object> map = (Map<String, Object>) mData.get(row);
		return map.get("COL"+col).toString();
	}
	public void setItemValue(int row, int col, String text) {

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		map.put("COL"+col, text);
	}

	public void setItemAttr(int row, int col, int arrayID) {

		String Key_ARRAYID = "ARRAYID_"+"COL"+col;
		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		map.put(Key_ARRAYID, arrayID);	
	}

	public void setFormat(int row, int col, double max,double min, int decimal, int len){

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		map.put("MAX_"+"COL"+col, max);	
		map.put("MIN_"+"COL"+col, min);	
		map.put("LEN_"+"COL"+col, len);	
		map.put("DECIMAL_"+"COL"+col, decimal);	

	}
	public void setFormat(int row, int col, String suffix){

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		map.put("SUFFIX_"+"COL"+col, suffix);	

	}
	public void setFormat(int row, int col, double max,double min, int decimal, int len, String suffix){

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		map.put("MAX_"+"COL"+col, max);	
		map.put("MIN_"+"COL"+col, min);	
		map.put("LEN_"+"COL"+col, len);	
		map.put("DECIMAL_"+"COL"+col, decimal);	
		if(suffix != null){
			map.put("SUFFIX_"+"COL"+col, suffix);		
		}
	}

	public void setFormatCol(int col, double max,double min, int decimal, int len){
		int k = mData.size();
		for(int i = 0;i < k;i++){
			setFormat(i,col,max,min,decimal,len);
		}	
	}
	public void setFormatCol(int col, double max,double min, int decimal, int len,String suffix){
		int k = mData.size();
		for(int i = 0;i < k;i++){
			setFormat(i,col,max,min,decimal,len,suffix);
		}	
	}
	public void setFormatRow(int row, double max,double min, int decimal, int len){

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		
		int k = map.size();
		for(int i = 0;i < k;i++){
			map.put("MAX_"+"COL"+i, max);	
			map.put("MIN_"+"COL"+i, min);	
			map.put("LEN_"+"COL"+i, len);	
			map.put("DECIMAL_"+"COL"+i, decimal);	
		}	


	}
	public void initBackgroundColor(int color){
		
		int k = mData.size();
		for(int i = 0;i < k;i++){
			setBackgroundColorOfRow(i, color);
		}
		
	}
	/*
	 * 设置row行的颜色为color
	 * @param：row--自0开始。row>=0,row < mData.size()
	 */
	public void setBackgroundColorOfRow(int row, int color){

		Map<String, Object> map = (Map<String, Object>) mData.get(row);		

		Iterator iterator = map.entrySet().iterator();  
		String sbkColorPrifix = "BackgroundColor_";

		List<String> keyList  = new ArrayList<String>(0);
		while(iterator.hasNext()){	
			//Map.Entry entry = (Map.Entry)iterator.next();  
			//String key = entry.getKey().toString();  

			String key = ((Entry) iterator.next()).getKey().toString();  
			if(!key.contains(sbkColorPrifix)){
				keyList.add(key);
			}
			

		}	
		for(String skey:keyList){
			map.put(sbkColorPrifix+skey, color);
		}

	}

	public View.OnClickListener getmOnClickListener() {
		return mOnClickListener;
	}

	public void setmOnClickListener(View.OnClickListener mOnClickListener) {
		this.mOnClickListener = mOnClickListener;
	}

	public View.OnLongClickListener getOnLongClickListener() {
		return OnLongClickListener;
	}

	public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
		OnLongClickListener = onLongClickListener;
	}


	public boolean isEnabled_listlayout() {
		return enabled_listlayout;
	}

	public void setEnabled_listlayout(boolean enabled_listlayout) {
		this.enabled_listlayout = enabled_listlayout;
	}

	public View getViewByPostion(int positon){
        //aaaa;
		return null;
	}
}
