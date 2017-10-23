package com.hyj.containers.actiontitle;

import android.content.Context;
import android.widget.TextView;

public class ActionItemView extends TextView{
	
	public OnPrepareAction onPrepareAction;
	
	public boolean actionAnimation  = false;

	public void setActionAnimation(boolean actionAnimation) {
		this.actionAnimation = actionAnimation;
	}

	public void setOnPrepareAction(OnPrepareAction onPrepareAction) {
		this.onPrepareAction = onPrepareAction;
	}

	public ActionItemView(Context context) {
		super(context);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(onPrepareAction!= null && actionAnimation){
			onPrepareAction.OnPreAction(this);
		}
		actionAnimation = false;
	}
	
	public interface OnPrepareAction{
		
		public void OnPreAction(ActionItemView view);
		
	}
	
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
