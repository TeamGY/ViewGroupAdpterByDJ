package com.hyj.containers.actiontitle;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionBarWrapper extends ActionMode{
	
	private Activity mActivity;
    
    private CharSequence mTitle;
    
    private Callback mCallback;
    
    private int mBackgroundRes;
    
    private ActionMenu mActionBarMenu;
    
    private LinearLayout mActionBarContentLayout;
    
    private ActionItemView mActionBarTitleView;
    
    private ActionItemView mLeftBarView;
    
    private List<ActionItemView> mRightBarView;
    
	public void setActionMenu(ActionMenu actionBarMenu) {
		this.mActionBarMenu = actionBarMenu;
	}

	public ActionMenu getActionMenu() {
		return mActionBarMenu;
	}

	private ActionBarWrapper(Activity activity,Callback callback,int backgroundRes) {
		this.mActivity = activity;
		this.mCallback = callback;
		mTitle = activity.getTitle();
		mBackgroundRes = backgroundRes;
	}
	
	public static ActionBarWrapper wrap(Activity activity,Callback callback,int backgroundRes){
		return new ActionBarWrapper(activity,callback,backgroundRes);
	}
	
	public LinearLayout startAction(){
		
		mActionBarContentLayout = new LinearLayout(mActivity);
		mActionBarContentLayout.setBackgroundResource(mBackgroundRes);
		mActionBarContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		mActionBarContentLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		ActionMenu actionBarMeun = this.getActionMenu();
		
		RelativeLayout itemsWapper = new RelativeLayout(mActivity);
		itemsWapper.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
				
		mActionBarTitleView = new ActionItemView(mActivity);
		RelativeLayout.LayoutParams titlebarViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		titlebarViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mActionBarTitleView.setLayoutParams(titlebarViewLayoutParams);
		mActionBarTitleView.setTextColor(Color.WHITE);
		mActionBarTitleView.setText(mTitle);
		mActionBarTitleView.setTextSize(23);
		mActionBarTitleView.setMaxWidth(300);
//		mActionBarTitleView.setShadowLayer(1.0f, 10.0f, 20.0f, Color.RED);
		mActionBarTitleView.setEllipsize(TruncateAt.MARQUEE);
		mActionBarTitleView.setMarqueeRepeatLimit(-1);
		mActionBarTitleView.setFocusableInTouchMode(true);
		mActionBarTitleView.setSingleLine(true);
		mActionBarTitleView.setHorizontallyScrolling(true);
		mActionBarTitleView.setActionAnimation(true);
		mActionBarTitleView.setClickable(false);
		mActionBarTitleView.setOnPrepareAction(new ActionItemView.OnPrepareAction() {
			@Override
			public void OnPreAction(ActionItemView barItem) {	
				rightAnimation(barItem);
			}
		});
		
		itemsWapper.addView(mActionBarTitleView);
		
		if(mCallback != null){
			
			boolean create = mCallback.onCreateAction(this,actionBarMeun);
			
			if(create){
				
				ActionItemImpl leftItem =  (ActionItemImpl) actionBarMeun.getLeftBarItem();
				
				if(leftItem!=null){
					mLeftBarView = new ActionItemView(mActivity);
					mLeftBarView.setBackgroundResource(leftItem.getIcoRes());
					mLeftBarView.setId(leftItem.getId());
					mLeftBarView.setText(leftItem.getTitle());
					RelativeLayout.LayoutParams leftBarLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				//	mLeftBarView.setCompoundDrawablesWithIntrinsicBounds(0, leftItem.getTopDrawableId(), 0, 0);
					mLeftBarView.setLayoutParams(leftBarLayoutParams);
					mLeftBarView.setGravity(Gravity.CENTER);
					mLeftBarView.setTextColor(Color.WHITE);
					mLeftBarView.setTextSize(15);
				
					mLeftBarView.setActionAnimation(true);
					mLeftBarView.setClickable(true);
					mLeftBarView.setOnClickListener(new onActionItemClickListener(leftItem));
					mLeftBarView.setOnPrepareAction(new ActionItemView.OnPrepareAction() {
						@Override
						public void OnPreAction(ActionItemView barItem) {
							
							AnimationSet animationSet = new AnimationSet(true);
							
							AlphaAnimation AlphaAnimation = new AlphaAnimation(0,1);
							
							AlphaAnimation.setDuration(300);
							
							animationSet.addAnimation(AlphaAnimation);
							
							TranslateAnimation translateAnimation = new TranslateAnimation(-barItem.getWidth(), 0, 0, 0);
							
							translateAnimation.setDuration(300);
							
							animationSet.addAnimation(translateAnimation);
							
							animationSet.setFillAfter(true);
							
							barItem.setAnimation(animationSet);
						}
					});
					itemsWapper.addView(mLeftBarView);
				}
				
				List<ActionItem> rightItems =  actionBarMeun.getRightBarItems();
				if(rightItems.size()>0){
					
					int lastItemId = 0x00000000;
					
					int radix = 0x00000001;
					
					ActionItemImpl leftOfItem = null;
					
					mRightBarView = new ArrayList<ActionItemView>(0);
					
					for(Iterator<ActionItem> iter= rightItems.iterator();iter.hasNext();){
						
						ActionItemImpl rightItem = (ActionItemImpl) iter.next();
						
						ActionItemView rightbarView = new ActionItemView(mActivity);
						
						rightbarView.setBackgroundResource(rightItem.getIcoRes());
						if(rightItem.getId()!= 0){
							rightbarView.setId(rightItem.getId());
						}else{
							rightItem.setId(lastItemId +=radix);
							rightbarView.setId(rightItem.getId());
						}
						
						rightbarView.setText(rightItem.getTitle());
						RelativeLayout.LayoutParams rightBarLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						if(leftOfItem!=null){
							rightBarLayoutParams.addRule(RelativeLayout.LEFT_OF,leftOfItem.getId());
						}else{
							rightBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						}
						//rightbarView.setCompoundDrawablesWithIntrinsicBounds(0, rightItem.getTopDrawableId(), 0, 0);
						rightbarView.setLayoutParams(rightBarLayoutParams);
						rightbarView.setGravity(Gravity.CENTER);
						rightbarView.setTextColor(Color.WHITE);
						rightbarView.setTextSize(15);
						
						rightbarView.setActionAnimation(true);
						rightbarView.setClickable(true);
						rightbarView.setOnClickListener(new onActionItemClickListener(rightItem));
						rightbarView.setOnPrepareAction(new ActionItemView.OnPrepareAction() {
							@Override
							public void OnPreAction(ActionItemView barItem) {		
								rightAnimation(barItem);
							}
						});
						
						leftOfItem = rightItem;
						mRightBarView.add(rightbarView);
						itemsWapper.addView(rightbarView);
					}
				}
			}
		}
		mActionBarContentLayout.addView(itemsWapper);
		return mActionBarContentLayout;
	}
	
	
	public void rightAnimation(ActionItemView view){
		
		AnimationSet animationSet = new AnimationSet(true);
		
		AlphaAnimation AlphaAnimation = new AlphaAnimation(0,1);
		
		AlphaAnimation.setDuration(300);
		
		animationSet.addAnimation(AlphaAnimation);
		
		TranslateAnimation translateAnimation = new TranslateAnimation( view.getHeight(), 0, 0, 0);
		
		translateAnimation.setDuration(300);
		
		animationSet.addAnimation(translateAnimation);
		
		animationSet.setFillAfter(true);
		
		view.setAnimation(animationSet);
		
	}
	
	public class onActionItemClickListener implements OnClickListener{
		
		private ActionItem mItem;
		
		public onActionItemClickListener(ActionItem item) {
			this.mItem = item;
		}

		@Override
		public void onClick(View v) {
			mCallback.onActionItemClicked(v,ActionBarWrapper.this, mItem);
		}
		
	}

	@Override
	public void setTitle(CharSequence title) {
		this.mTitle = title;
	}

	@Override
	public void setTitle(int resId) {
		this.mTitle = mActivity.getResources().getString(resId);
	}

	@Override
	public CharSequence getTitle() {
		return mTitle;
	}

	public void updateTitle() {
		mActionBarTitleView.setText(this.getTitle());
	}
	
	@Override
	public void invalidate(boolean actionAnimation) {
		
		mActionBarTitleView.setActionAnimation(actionAnimation);
		if(!mTitle.equals(mActionBarTitleView.getText())){
			mActionBarTitleView.setText(this.getTitle());
		}
		
		if(mLeftBarView != null && mActionBarMenu.getLeftBarItem() != null){
			ActionItemImpl leftItem = (ActionItemImpl) mActionBarMenu.getLeftBarItem();
			mLeftBarView.setActionAnimation(actionAnimation);
			if(!leftItem.getTitle().equals(mLeftBarView.getText())){
				mLeftBarView.setText(leftItem.getTitle());
			}
			mLeftBarView.setBackgroundResource(leftItem.getIcoRes());
		//	mLeftBarView.setCompoundDrawablesWithIntrinsicBounds(0,leftItem.getTopDrawableId(),0,0);
		}
		
		if(mRightBarView != null && mActionBarMenu.getRightBarItems()!= null){
			for(int i = 0;i < mRightBarView.size();i++){
				ActionItemView rightItemView = mRightBarView.get(i);
				ActionItemImpl rightItem = (ActionItemImpl) mActionBarMenu.getRightBarItems().get(i);
				rightItemView.setActionAnimation(actionAnimation);
				if(!rightItem.getTitle().equals(rightItemView.getText())){
					rightItemView.setText(rightItem.getTitle());
				}
				rightItemView.setBackgroundResource(rightItem.getTopDrawableId());
			//	rightItemView.setCompoundDrawablesWithIntrinsicBounds(0,rightItem.getTopDrawableId(),0,0);
			}
		}
	}
}
