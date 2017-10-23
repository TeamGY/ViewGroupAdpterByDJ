package com.hyj.containers.tabbar;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * ����������һ���ײ���LinearLayout;
 *
 */
public class TabBarWrapper extends TabMode{
	
	private Activity mActivity;
	
	private Callback mCallback;
    
    @SuppressWarnings("unused")
	private int mBackgroundRes;
    
    private TabBarImpl mImpl;
    
    private LinearLayout mActionBarContentLayout;
    
    private List<TabItemView> menuButtons = new ArrayList<TabItemView>(0);
    
    private List<TabBarItemImpl> menuItemImpls = new ArrayList<TabBarItemImpl>(0);
    
	private TabBarWrapper(Activity activity,Callback callback,int mBackgroundRes) {
		this.mActivity = activity;
		this.mCallback = callback;
		this.mBackgroundRes = mBackgroundRes;
		this.mImpl = new TabBarImpl(mActivity);
	}
	
	public static TabBarWrapper wrap(Activity activity,Callback callback,int mBackgroundRes){
		return new TabBarWrapper(activity,callback,mBackgroundRes);
	}
	
	/**
	 * @return �������ɵĵײ�LinearLayout
	 */
	public LinearLayout startAction(){
		
		mActionBarContentLayout = new LinearLayout(mActivity);
		//mActionBarContentLayout.setBackgroundResource(mBackgroundRes);
		mActionBarContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		mActionBarContentLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		RelativeLayout itemsWapper = new RelativeLayout(mActivity);
		itemsWapper.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		if(mCallback != null){
			
			boolean create = mCallback.onCreateTabBar(this,mImpl); //�ص�����onCreateMenu(this,mImpl)����
			
			if(create){
				
				List<TabBarItemImpl> leftMenuItems = mImpl.getLeftBarItems();
				if(!leftMenuItems.isEmpty()){
					
					int lastItemId = 0;
					
					int radix = 1;
					
					TabBarItemImpl firstItem = null;
					
					for (Iterator<TabBarItemImpl> iterator = leftMenuItems.iterator(); iterator.hasNext();) {
						
						TabBarItemImpl leftImpl = (TabBarItemImpl) iterator.next();
						TabItemView mLeftbarView = new TabItemView(mActivity);
	
						mLeftbarView.setBackgroundResource(leftImpl.getIcoRes());
						if(leftImpl.getId()!= 0){
							mLeftbarView.setId(leftImpl.getId());
						}else{
							leftImpl.setId(lastItemId +=radix);
							mLeftbarView.setId(leftImpl.getId());
						}
						
						mLeftbarView.setText(leftImpl.getTitle());
					
						RelativeLayout.LayoutParams rightBarLayoutParams = new RelativeLayout.LayoutParams(leftImpl.getWidth(),leftImpl.getHeight());
						if(firstItem!=null){
							rightBarLayoutParams.addRule(RelativeLayout.RIGHT_OF,firstItem.getId());
						}else{
							rightBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						}
						mLeftbarView.setLayoutParams(rightBarLayoutParams);
						mLeftbarView.setGravity(Gravity.LEFT);
						mLeftbarView.setTextColor(Color.WHITE);
						mLeftbarView.setTextSize(leftImpl.getTextSize());
						
						mLeftbarView.setClickable(true);
						mLeftbarView.setOnClickListener(new onActionItemClickListener(leftImpl));
						
						firstItem = leftImpl;
						itemsWapper.addView(mLeftbarView);
						menuButtons.add(mLeftbarView);
						menuItemImpls.add(leftImpl);
					}
				}
			}
		}
		
		mActionBarContentLayout.addView(itemsWapper);
		return mActionBarContentLayout;
	}
	public class onActionItemClickListener implements OnClickListener{
		
		private TabBarItemImpl mItem;
		
		public onActionItemClickListener(TabBarItemImpl item) {
			this.mItem = item;
		}

		@Override
		public void onClick(View v) {
			mCallback.onTabItemClicked(v, mItem);
		}
	}	
	
	public void chooseItem(int positionBF,int positionNew,InvalidateCallBack invalidateCallBack){

	}


	public void invalidate(int nPos, InvalidateCallBack invalidateCallBack) {
		// TODO Auto-generated method stub
		for (int i = 0; i < menuButtons.size(); i++) {
		if(i==nPos){
			TabBarItemImpl mItem = menuItemImpls.get(nPos);
			TabItemView menuItemView = menuButtons.get(nPos);
			invalidateCallBack.callBack(mItem);	
			menuItemView.setText(mItem.getTitle());
			menuItemView.setTextColor(mItem.getColor());
			menuItemView.setBackgroundResource(mItem.getIcoRes());
			menuItemView.setTextSize(mItem.getTextSize());	
	
		}
	}
		
	}
}
