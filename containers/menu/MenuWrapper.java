package com.hyj.containers.menu;

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
public class MenuWrapper extends MenuMode{
	
	private Activity mActivity;
	
	private Callback mCallback;
    
    private int mBackgroundRes;
    
    private MenuImpl mImpl;
    
    private LinearLayout mActionBarContentLayout;
    
    private List<MenuItemView> menuButtons = new ArrayList<MenuItemView>(0);
    
    private List<MenuItemImpl> menuItemImpls = new ArrayList<MenuItemImpl>(0);
    
	private MenuWrapper(Activity activity,Callback callback,int mBackgroundRes) {
		this.mActivity = activity;
		this.mCallback = callback;
		this.mBackgroundRes = mBackgroundRes;
		this.mImpl = new MenuImpl(mActivity);
	}
	
	public static MenuWrapper wrap(Activity activity,Callback callback,int mBackgroundRes){
		return new MenuWrapper(activity,callback,mBackgroundRes);
	}
	
	/**
	 * @return �������ɵĵײ�LinearLayout
	 */
	public LinearLayout startAction(){
		
		mActionBarContentLayout = new LinearLayout(mActivity);
		mActionBarContentLayout.setBackgroundResource(mBackgroundRes);
		mActionBarContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		mActionBarContentLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		RelativeLayout itemsWapper = new RelativeLayout(mActivity);
		itemsWapper.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		if(mCallback != null){
			
			boolean create = mCallback.onCreateMenu(this,mImpl); //�ص�����onCreateMenu(this,mImpl)����
			
			if(create){
				
				List<MenuItemImpl> leftMenuItems = mImpl.getLeftBarItems();
				if(!leftMenuItems.isEmpty()){
					
					int lastItemId = 0;
					
					int radix = 1;
					
					MenuItemImpl firstItem = null;
					
					for (Iterator<MenuItemImpl> iterator = leftMenuItems.iterator(); iterator.hasNext();) {
						
						MenuItemImpl leftImpl = (MenuItemImpl) iterator.next();
						MenuItemView mLeftbarView = new MenuItemView(mActivity);

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
						mLeftbarView.setCompoundDrawablesWithIntrinsicBounds(0, leftImpl.getTopDrawableId(), 0, 0);
						mLeftbarView.setLayoutParams(rightBarLayoutParams);
						mLeftbarView.setGravity(Gravity.CENTER);
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
				
				MenuItemImpl rightImpl =  (MenuItemImpl) mImpl.getRightBarItem();
				if(rightImpl!=null){
					MenuItemView mRightBarView = new MenuItemView(mActivity);
					mRightBarView.setBackgroundResource(rightImpl.getIcoRes());
					mRightBarView.setId(0x10000000);
					mRightBarView.setText(rightImpl.getTitle());
					mRightBarView.setCompoundDrawablesWithIntrinsicBounds(0, rightImpl.getTopDrawableId(), 0, 0);
					RelativeLayout.LayoutParams leftBarLayoutParams = new RelativeLayout.LayoutParams(rightImpl.getWidth(),rightImpl.getHeight());
					leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
					mRightBarView.setLayoutParams(leftBarLayoutParams);
					mRightBarView.setGravity(Gravity.CENTER);
					mRightBarView.setTextColor(Color.WHITE);
					mRightBarView.setTextSize(rightImpl.getTextSize());
					
					mRightBarView.setClickable(true);
					mRightBarView.setOnClickListener(new onActionItemClickListener(rightImpl));
					itemsWapper.addView(mRightBarView);
					menuButtons.add(mRightBarView);
					menuItemImpls.add(rightImpl);
				}
			}
		}
		
		mActionBarContentLayout.addView(itemsWapper);
		return mActionBarContentLayout;
	}
	public class onActionItemClickListener implements OnClickListener{
		
		private MenuItemImpl mItem;
		
		public onActionItemClickListener(MenuItemImpl item) {
			this.mItem = item;
		}

		@Override
		public void onClick(View v) {
			mCallback.onMenuItemClicked(v, mItem);
		}
	}
	
	public void invalidate(int position,InvalidateCallBack invalidateCallBack){
		for (int i = 0; i < menuButtons.size(); i++) {
			if(i==position){
				MenuItemImpl mItem = menuItemImpls.get(position);
				MenuItemView menuItemView = menuButtons.get(position);
				invalidateCallBack.callBack(mItem);
				menuItemView.setText(mItem.getTitle());
				menuItemView.setBackgroundResource(mItem.getIcoRes());
				menuItemView.setCompoundDrawablesWithIntrinsicBounds(0, mItem.getTopDrawableId(), 0, 0);
				menuItemView.setTextSize(mItem.getTextSize());
				//menuItemView.setWidth(mItem.getWidth());
			}
		}
	}
}
