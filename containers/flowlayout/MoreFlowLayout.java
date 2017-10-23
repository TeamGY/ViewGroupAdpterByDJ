package com.hyj.containers.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.hyj.containers.flowlayout.OnClickedMode.Callback;
import com.kemov.elecmobiletools.R;

import java.util.ArrayList;
import java.util.List;

/*
 * 
xml文件:
        <com.hyj.containers.flowlayout.MoreFlowLayout
        android:id="@+id/fl_layout"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true" >
    </com.hyj.containers.flowlayout.MoreFlowLayout>

Activity.java文件：
	    MoreFlowLayout morefl = (MoreFlowLayout) this.findViewById(R.id.fl_layout);

		morefl.setCallback(new OnClickedMode11());

		List<String>  strList = new ArrayList<String> ();

		for(int i = 0;i < 50;i++){
			strList.add("test"+i);
		}
		morefl.creatView(strList);
 */
public class MoreFlowLayout extends FlowLayout
{

	private static final String TAG = "com.kemov.sclaata.VisualSCL.MoreFlowLayout";


	List<String>  strList = new ArrayList<String> ();

	Context mContext = null;

	Integer CheckTag = 0;

	RadioButton lastMore  = null;
	RadioButton checkedView  = null;


	/*
	 * 该回调对象用于传递按钮选择改变的消息，initLitster已经调用了childView的onClick函数，mCheckedMonitor可以传递按钮选择改变的消息。
	 */
	private Callback mCheckedMonitor;//    CheckedMonitor可以发出OnCheckChanged（View v）消息。

	public MoreFlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		
	
	}
	/*
	 * 初始化选中第0行
	 */
	public void initCheckedRow(){
		
		if(CheckTag != -1){
			checkedView = (RadioButton) getChildAt(CheckTag);	
		}
		else{
			checkedView = null;
		}
		
		if(checkedView != null){
			checkedView.callOnClick();
		}
	
		//mCheckedMonitor.OnCheckChanged(checkedView);
	}
	


	public void creatView(List<String>  argList){

		if(argList == null || argList.size() <= 0){
			return;
		}
		//mContext = context;
		setVisilbRow(-1);
		//CheckTag = 0;
		
		this.removeAllViews();

		strList = argList;
		int k = strList.size();

		MarginLayoutParams  lpm = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpm.width = LayoutParams.WRAP_CONTENT;
		lpm.height = LayoutParams.WRAP_CONTENT;
		lpm.setMargins(1, 1, 1, 1);

		for(int i = 0;i < k;i++){
			RadioButton radioButton  = new RadioButton(mContext);

			radioButton.setButtonDrawable(R.drawable.flag_01);
			radioButton.setText(strList.get(i));
			radioButton.setBackgroundResource(R.drawable.filterbtn2_style);

			radioButton.setId(i);

			addView(radioButton,i);

			radioButton = (RadioButton) getChildAt(i); 
			radioButton.setLayoutParams(lpm);		 			
		}

		if(lastMore == null){
			lastMore = new RadioButton(mContext);
			lastMore.setButtonDrawable(R.drawable.flag_01);
			lastMore.setText("...");
			lastMore.setBackgroundResource(R.drawable.filterbtn2_style);
			lastMore.setId(200);
		}

		if(CheckTag >= 0 && CheckTag < argList.size()){
		
			checkedView = (RadioButton) getChildAt(CheckTag);
			checkedView.setChecked(true);
		}

		initLitster();	
	}


	public void InitVisilbRow(){


	}
	public void initLitster(){

		OnClickListener clickMoreListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//removeView(lastMore);
				setVisilbRow(-1);
			}
		};
		lastMore.setOnClickListener(clickMoreListener);

		OnClickListener clickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButton ch = (RadioButton) v;
				checkedView = (RadioButton) v;

				if(getVisilbRow() >= 0){//单选底部按钮模式
					if(getCheckTag() == ch.getId()){
						return;
					}
					else
					{
						((RadioButton) getChildAt(getCheckTag())).setChecked(false);
						setCheckTag(ch.getId());
						
						if(mCheckedMonitor != null){
							mCheckedMonitor.OnCheckChanged(v);
						}
					}
				}else{//全部列出，选行模式

					if(getCheckTag() != ch.getId()){

						((RadioButton) getChildAt(getCheckTag())).setChecked(false);
						setCheckTag(ch.getId());
						
						if(mCheckedMonitor != null){
							mCheckedMonitor.OnCheckChanged(v);
						}
					}

					setVisilbRow(findRow(v));
				}
			}
		};

		for(int i = 0;i < strList.size();i++){
			RadioButton chBox  = (RadioButton) getChildAt(i); 

			chBox.setOnClickListener(clickListener);
		}
	}

	public void setVisilbRow(int row){

		int k1 = this.getChildCount();
		for(int i = 0;i < k1;i++){
			RadioButton child = (RadioButton) this.getChildAt(i);

			Log.e(TAG, "row = "+row+", i = " + child.getId()+",text = "+child.getText());
		}

		super.setVisilbRow(row);

		if(row < 0){
			removeView(lastMore);
		}else{
			//removeView(lastMore);
			//invalidate();
			if(row >= getRowCount()) return;

			MarginLayoutParams  lpm = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lpm.width = LayoutParams.WRAP_CONTENT;
			lpm.height = LayoutParams.WRAP_CONTENT;
			lpm.setMargins(4, 4, 4, 4);

			int moreIndex =  getMoreIndex(row);
			Log.e(TAG, "row = "+row+", moreIndex = " + moreIndex);

			addView(lastMore,moreIndex);
			lastMore.setEnabled(true);

			View moreView =  getChildAt(moreIndex); 
			moreView.setLayoutParams(lpm);
		}
	}

	public void addLastMoreView(){

	}
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public Integer getCheckTag() {
		return CheckTag;
	}

	public void setCheckTag(Integer checkTag) {
		CheckTag = checkTag;
	}

	public RadioButton getLastMore() {
		return lastMore;
	}

	public void setLastMore(RadioButton lastMore) {
		this.lastMore = lastMore;
	}

	public void setCallback(Callback argCallback){

		mCheckedMonitor = argCallback;
	}
	
	public RadioButton getCheckedView() {
		return checkedView;
	}

	public void setCheckedView(RadioButton checkedView) {
		this.checkedView = checkedView;
	}
}
