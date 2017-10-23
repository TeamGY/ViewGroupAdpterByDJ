package com.hyj.containers.flowlayout;

import android.view.View;
/*
 * 
 * 
 * 

1.自定义view 定义Callback：（eg. MoreFlowLayout.java）

	private Callback mCheckedMonitor;//    CheckedMonitor可以发出OnCheckChanged（View v）消息。
	
	
	//发送消息
    if(mCheckedMonitor != null){
							mCheckedMonitor.OnCheckChanged(v);
						}
						
 2.应用自定义view：（eg. DatasetFragment.java）
 datasetFlowLayout.setCallback(new OnClickFlowLayout());
 
 
 	public class OnClickFlowLayout implements OnClickedMode.Callback{

		@Override
		public void OnCheckChanged(View v) {
			// TODO Auto-generated method stub

			//RadioButton rBtn = (RadioButton) v;
			Log.e(TAG, "Id = " +v.getId());

			updateDataList();
			
			notifyDatasetChanged();
		}

	}
	
 */

public abstract class OnClickedMode {
	
 
    public interface Callback {
    		
    	void OnCheckChanged(View v);
	
    }
}
