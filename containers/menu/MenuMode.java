package com.hyj.containers.menu;

import android.view.View;


/**
 * @author ������һ�������࣬��Ҫ���ܾ��Ƕ�����һ���ӿ�Callback��������ӿ����ڽ�����ʵ�֣�����д�ýӿڵķ�����
 * �ýӿ��з����ĵ���ȴ����MenuWrapper�������
 *
 */
public abstract class MenuMode {

	/**
	 * ����: ���»���ָ���İ�ť
	 * @param position Ҫ���ư�ť��λ��
	 * @param invalidateCallBack ͨ���˶������callBack(MenuItemImpl mItem)�������޸ĵ��������õ�mTtem�У������ﵽ������ػ����������ֵ
	 */
	public void invalidate(int position,InvalidateCallBack invalidateCallBack){};
	
	/**
	 * @author �˽ӿڵ����ã�
	 * �ڵ���invalidate(int position,InvalidateCallBack invalidateCallBack)����ʱ�ص�callBack(MenuItemImpl mItem)�����������ػ�λ��(��position)��ӦMenuItemImpl���������
	 *
	 */
	public interface InvalidateCallBack{
		
		public void callBack(MenuItemImpl mItem);
	}
	
    public interface Callback {
    	/**
    	 * @param actionMode
    	 * @param menuImpl
    	 * @return boolean ���Ϊtrue ����Ӱ�ť��Ϊfalse �����ֻ��һ������
    	 */
    	public boolean onCreateMenu(MenuMode actionMode,MenuImpl menuImpl);

    	/**
    	 * @param view
    	 * @param menuItemImpl
    	 *  ��Ӧ��ť�ĵ���¼�����������MenuItemImpl�е�����orderId�������ֵ�������ĸ���ť
    	 */
    	public void onMenuItemClicked(View view,MenuItemImpl menuItemImpl);
   	
    }
}
