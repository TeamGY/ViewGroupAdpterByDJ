package com.hyj.containers.actiontitle;

import android.view.View;


public abstract class ActionMode {
	
    public abstract void setTitle(CharSequence title);

    public abstract void setTitle(int resId);
    
    public abstract ActionMenu getActionMenu();

    public abstract CharSequence getTitle();
    
    protected abstract View startAction();
    
    protected abstract void setActionMenu(ActionMenu actionMenu);
    
    public abstract void invalidate(boolean actionAnimation);
    
    public void requestLayout(){
    }
    
    public interface Callback {
    	
    	public boolean onCreateAction(ActionMode actionMode,ActionMenu menu);

    	public void onActionItemClicked(View view,ActionMode actionMode,ActionItem item);
   	
    }
}
