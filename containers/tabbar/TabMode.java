package com.hyj.containers.tabbar;

import android.view.View;


/**
 * @author ������һ�������࣬��Ҫ���ܾ��Ƕ�����һ���ӿ�Callback��������ӿ����ڽ�����ʵ�֣�����д�ýӿڵķ�����
 * �ýӿ��з����ĵ���ȴ����MenuWrapper�������
 *
 */
public abstract class TabMode {

	/**
	 * ����: ���»���ָ���İ�ť
	 * @param position Ҫ���ư�ť��λ��
	 * @param invalidateCallBack ͨ���˶������callBack(TabBarItemImpl mItem)�������޸ĵ��������õ�mTtem�У������ﵽ������ػ����������ֵ
	 */
	public void invalidate(int position,InvalidateCallBack invalidateCallBack){};
	
	/**
	 * @author �˽ӿڵ����ã�
	 * �ڵ���invalidate(int position,InvalidateCallBack invalidateCallBack)����ʱ�ص�callBack(MenuItemImpl mItem)�����������ػ�λ��(��position)��ӦMenuItemImpl���������
	 *
	 */
	public interface InvalidateCallBack{
		
		public void callBack(TabBarItemImpl mItem);
	}
	
    public interface Callback {
    	/**
    	 * @param actionMode
    	 * @param menuImpl
    	 * @return boolean ���Ϊtrue ����Ӱ�ť��Ϊfalse �����ֻ��һ������
    	 */
    	public boolean onCreateTabBar(TabMode actionMode,TabBarImpl tabImpl);

    	/**
    	 * @param view
    	 * @param menuItemImpl
    	 *  ��Ӧ��ť�ĵ���¼�����������MenuItemImpl�е�����orderId�������ֵ�������ĸ���ť
    	 */
    	public void onTabItemClicked(View view,TabBarItemImpl tabItemImpl);
   	
    }
}
