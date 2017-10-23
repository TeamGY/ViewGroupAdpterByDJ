package com.hyj.containers.view.listview;
/**
 * 作用：继承ListView 主要方法setAdapterValue(List<? extends Map<String, Object>> data)和notifyDataSetChanged(List<? extends Map<String, Object>> data)。
 * 前提是先设置setAdapterValue(List<? extends Map<String, Object>> data)之后才可以调用notifyDataSetChanged(List<? extends Map<String, Object>> data)。
 * 该类中的属性设置完了之后必须执行setAdapterValue(List<? extends Map<String, Object>> data)否则设置的属性不能被体现出来例如：onClickListeners这个Map
 * @author Administrator
 * 
 * 修改日志：2016.04.08 增加监听按键重载函数
 **/

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hyj.containers.adapter.CustomAdapter;
import com.kemov.elecmobiletools.R;
import com.kemov.elecmobiletools.R.color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListLayout extends ListView {

	private static final String TAG = "ListLayout";
	private Context context;

	private CustomAdapter mCustomAdapter;

	private int layoutSource;

	private Map<Integer, OnKeyListener> mOnKeyListeners;

	private View.OnClickListener mOnClickListener;
	private SparseArray<View.OnClickListener> mOnClickListeners;

	private Map<Integer, OnFocusChangeListener> mOnFocusChangeListeners;

	private OnLongClickListener OnLongClickListener;
	private Map<Integer, OnLongClickListener> mOnLongClickListeners;


	@SuppressWarnings("unused")
	private OnScrollListener mOnScrollListeners;
	private int mPosition =0,lvChildTop=0;
	public int getLayoutSource() {
		return layoutSource;
	}

	public void setLayoutSource(int layoutSource) {
		this.layoutSource = layoutSource;
	}

	public SparseArray<View.OnClickListener> getOnClickListeners() {
		return mOnClickListeners;
	}

	public Map<Integer, OnFocusChangeListener> getOnFocusChangeListeners() {
		return mOnFocusChangeListeners;
	}

	public Map<Integer, OnKeyListener> getOnKeyListeners() {
		return mOnKeyListeners;
	}

	public View.OnLongClickListener getOnLongClickListener() {
		return OnLongClickListener;
	}

	public Map<Integer, OnLongClickListener> getOnLongClickListeners() {
		return mOnLongClickListeners;
	}

	public OnClickListener getmOnClickListener() {
		return mOnClickListener;
	}

	public void setOnClickListener(OnClickListener mOnClickListener) {
		this.mOnClickListener = mOnClickListener;
	}

	public void setOnClickListeners(
			SparseArray<View.OnClickListener> onClickListeners) {
		this.mOnClickListeners = onClickListeners;
	}
	public void setOnClickListener(OnClickListener onClickListener, int id) {
		if(mOnClickListeners == null){
			mOnClickListeners = new SparseArray<View.OnClickListener>();
		}
		this.mOnClickListeners.put(id, onClickListener);
	}
	public void setOnFocusChangeListeners(
			Map<Integer, OnFocusChangeListener> onFocusChangeListeners) {
		this.mOnFocusChangeListeners = onFocusChangeListeners;
	}
	public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
		OnLongClickListener = onLongClickListener;
	}
	public void setOnLongClickListener(
			Map<Integer, OnLongClickListener> onLongClickListener) {
		this.mOnLongClickListeners = onLongClickListener;
	}
	public void setOnKeyListeners(Map<Integer, OnKeyListener> mOnKeyListeners) {
		this.mOnKeyListeners = mOnKeyListeners;
	}
	public void setOnKeyListener(
			OnKeyListener onKeyClickListener, int id) {
		if(mOnKeyListeners == null){
			mOnKeyListeners =  new  HashMap<Integer, OnKeyListener>();
		}
		this.mOnKeyListeners.put(id, onKeyClickListener);	
	}

	public ListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
		setLayoutSource(array.getResourceId(R.styleable.ItemLayout_item_layout, 0));
		array.recycle();
		this.context = context;
	}

	public void notifyDataSetChanged(List<? extends Map<String, Object>> data) {
		if(mCustomAdapter == null){
			setAdapterValue(data,false);
		}

		mCustomAdapter.setmData(data);
		mCustomAdapter.notifyDataSetChanged();
	}

	public void setAdapterValue(List<? extends Map<String, Object>> data,boolean select) {
		mCustomAdapter = null;
		mCustomAdapter = new CustomAdapter(context, data, getLayoutSource(), 
				getOnKeyListeners(), getOnClickListeners(), getOnFocusChangeListeners(),getOnLongClickListeners(), select);
		mCustomAdapter.setmOnClickListener(mOnClickListener);
		mCustomAdapter.setOnLongClickListener(OnLongClickListener);
		mCustomAdapter.setEnabled_listlayout(isEnabled());
		setAdapter(mCustomAdapter);	

		this.setOnScrollListener(new OnScrollListener() {   
			/**  
			 * 滚动状态改变时调用  
			 */  
			@Override  
			public void onScrollStateChanged(AbsListView view, int scrollState) {   
				// 不滚动时保存当前滚动到的位置  
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {  
					mPosition = view.getFirstVisiblePosition();  
					View v = view.getChildAt(0);  
					lvChildTop = (v == null) ? 0 : v.getTop();  

					//   LogUtil.v("scroll", mPosition+"+++"+lvChildTop);
				}  
			}   
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		}); 

		this.setSelectionFromTop(mPosition, lvChildTop);
	}

	public List<View> getContainViewList(){

		return mCustomAdapter.getmItemContainLayout();
	}
	/*
	 * 根据数据设置ListLayout高度 
	 */
	public void updateHeight() {

		ListAdapter listAdapter = getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, this);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		android.view.ViewGroup.LayoutParams params = this.getLayoutParams();

		params.height = totalHeight
				+ (getDividerHeight() * (listAdapter.getCount() - 1));

		//((MarginLayoutParams) params).setMargins(10, 10, 10, 10);

		setLayoutParams(params);
	}
	public void updateHeight2(int adjustHeight) {

		ListAdapter listAdapter = getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		int he0 = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, this);
			listItem.measure(0, 0);

			he0 = listItem.getMeasuredHeight();

			totalHeight += he0;//totalHeight += listItem.getMeasuredHeight();

			Log.e(TAG, "updateHeight2: listItem.getMeasuredHeightAndState() = "+he0);
		}

		android.view.ViewGroup.LayoutParams params = this.getLayoutParams();

		params.height = totalHeight
				+ (getDividerHeight() * (listAdapter.getCount() - 1)) + adjustHeight;

		//((MarginLayoutParams) params).setMargins(10, 10, 10, 10);

		setLayoutParams(params);
	}
	public void updateHeight(int nHeigh) {

		//		  ListAdapter listAdapter = getAdapter();
		//
		//		  if (listAdapter == null) {
		//		   return;
		//		  }
		android.view.ViewGroup.LayoutParams params = this.getLayoutParams();

		params.height = nHeigh;
		setLayoutParams(params);
	}

	public void updateWidth(int nWidth) {

		ListAdapter listAdapter = getAdapter();

		if (listAdapter == null) {
			return;
		}
		android.view.ViewGroup.LayoutParams params = this.getLayoutParams();

		params.width = nWidth;
		setLayoutParams(params);
	}

	public String getItemText(int row, int col){

		return mCustomAdapter.getItemValue(row, col);

	}

	public void setItemText(int row, int col, String text){

		mCustomAdapter.setItemValue(row, col, text);
		//	mCustomAdapter.notifyDataSetChanged();

	}

	public void setItemArrayID(int row, int col, int arrayID){

		mCustomAdapter.setItemAttr(row, col, arrayID);
		//	mCustomAdapter.notifyDataSetChanged();		
	}
	public void setFormat(int row, int col, double max,double min, int decimal, int len){


		mCustomAdapter.setFormat(row, col, max, min, decimal,len);
		//mCustomAdapter.notifyDataSetChanged();

	}
	public void setFormat(int row, int col, double max,double min, int decimal, int len, String suffix){

		mCustomAdapter.setFormat(row, col, max, min, decimal,len,suffix);
		//mCustomAdapter.notifyDataSetChanged();

	}
	public void setFormat(int row, int col,String suffix){

		mCustomAdapter.setFormat(row, col, suffix);
		//mCustomAdapter.notifyDataSetChanged();

	}
	public void setFormatCol(int col, double max,double min, int decimal, int len){

		mCustomAdapter.setFormatCol(col, max, min, decimal,len);
		//mCustomAdapter.notifyDataSetChanged();		
	}
	public void setFormatCol(int col, double max,double min, int decimal, int len, String suffix){

		mCustomAdapter.setFormatCol(col, max, min, decimal,len,suffix);
		//mCustomAdapter.notifyDataSetChanged();		
	}
	public void setFormatRow(int col, double max,double min, int decimal, int len){

		mCustomAdapter.setFormatRow(col, max, min, decimal,len);
		//mCustomAdapter.notifyDataSetChanged();		
	}


	/****************/
	public void initBackgroundColor(){

		mCustomAdapter.initBackgroundColor(Color.WHITE);
	}
	/*
	 * 设置row行的颜色为color
	 * @param：row--自0开始。row>=0,row < mData.size()
	 */
	public void setBackgroundColorOfRow(int row, int color){

		mCustomAdapter.setBackgroundColorOfRow(row, color);
	}

	public void setBackgroundColorOfRow(int[] rows, int color){

		int k = rows.length;
		for(int i = 0;i < k;i++){
			mCustomAdapter.setBackgroundColorOfRow(rows[i], color);
		}
	}


	/*******SelectedRow********/
	private int CurrentRow = -1;

	public void setSelectedRow(int row){

		//if(getCurrentRow() != row){
			if(getCurrentRow() != -1){
				setBackgroundColorOfRow(getCurrentRow(), Color.WHITE);	
			}

			if(row != -1 ) {
				setBackgroundColorOfRow(row, color.papayawhip);
			}
			setCurrentRow(row);
	//	}
	}

	public int getCurrentRow() {
		return CurrentRow;
	}

	public void setCurrentRow(int currentRow) {
		CurrentRow = currentRow;
	}
}
