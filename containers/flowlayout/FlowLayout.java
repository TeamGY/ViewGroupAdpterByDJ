package com.hyj.containers.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup
{

	private static final String TAG = "FlowLayout";

	private int lastMoreWidh = 40;

	private int VisilbRow = -1;

	
	/**
	 * 存储所有的View，按行记录
	 */
	private List<List<View>> mAllViews = new ArrayList<List<View>>();
	/**
	 * 记录每一行的最大高度
	 */
	private List<Integer> mLineHeight = new ArrayList<Integer>();
	
	
	public int getVisilbRow() {
		return VisilbRow;
	}

	protected void setVisilbRow(int visilbRow) {
		VisilbRow = visilbRow;
	}

	public FlowLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p)
	{
		return new MarginLayoutParams(p);
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
	{
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams()
	{
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}


	/**
	 * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		Log.d(TAG, "onMeasure" );

		// 获得它的父容器为它设置的测量模式和大小
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		Log.d(TAG, "sizeWidth = "+sizeWidth + "," + sizeHeight);

		// 如果是warp_content情况下，记录宽和高
		int width = 0;
		int height = 0;
		/**
		 * 记录每一行的宽度，width不断取最大宽度
		 */
		int lineWidth = 0;
		/**
		 * 每一行的高度，累加至height
		 */
		int lineHeight = 0;

		int cCount = getChildCount();

		int cRow = 0;

		boolean isMoreAdded = false;
		// 遍历每个子元素
		for (int i = 0; i < cCount; i++)
		{
			View child = getChildAt(i);

			// 测量每一个child的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// 得到child的lp
			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			// 当前子空间实际占据的宽度
			int childWidth = child.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;
			//if(childWidth > 200)childWidth = 200;
			// 当前子空间实际占据的高度
			int childHeight = child.getMeasuredHeight() + lp.topMargin
					+ lp.bottomMargin;

			//Log.d(TAG, "childWidth = " + childWidth + ", childHeight = "+childHeight);
			/**
			 * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
			 */
			
			if(cRow == getVisilbRow() && child.getId() == 200){//换行并结束遍历
				lineWidth += childWidth;
				width = lineWidth;// 取最大的
				height = lineHeight;
				break;
			}
			
			
			if (lineWidth + childWidth > sizeWidth-lastMoreWidh)
			{
				if(cRow == getVisilbRow() && !isMoreAdded){
					lineWidth += childWidth;
					lineHeight = Math.max(lineHeight, childHeight);	
					
					// 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
					if (i == cCount - 1 && VisilbRow < 0)
					{
						width = Math.max(width, lineWidth);
						height += lineHeight;
					}
					height = lineHeight;
					width = lineWidth;
					isMoreAdded = true;
					break;
				}
				Log.d(TAG, "i = "+i+",childWidth = "+(childWidth+lineWidth));

				width = Math.max(lineWidth, childWidth);// 取最大的
				lineWidth = childWidth; // 重新开启新行，开始记录

				// 叠加当前高度，
				if(VisilbRow < 0){
					height += lineHeight;
				}	
				
				// 开启记录下一行的高度
				lineHeight = childHeight;
				cRow++;
				
			} else
				// 否则累加值lineWidth,lineHeight取最大高度
			{
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);	
			}

			// 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
			if (i == cCount - 1)
			{
				width = Math.max(width, lineWidth);
				if(VisilbRow < 0){
					height += lineHeight;
				}else{
					height = lineHeight;
				}
			}

		}

		Log.d(TAG, sizeWidth + "," + sizeHeight);
		Log.d(TAG, width + "," + height);
		setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
				: width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
						: height);

	}


	protected int findRow(View v)
	{
		int lineNum = mAllViews.size();

		for(int i = 0;i < lineNum;i++){
			List<View> rowList = mAllViews.get(i);

			if(rowList.indexOf(v) >= 0){
				return i;
			}

		}
		return 0;
	}



	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		//Log.d(TAG, "onLayout:" + "changed = "+changed +", l = "+ l + ", t = " +t + ", b = "+ b);
		setVisibility( getVisilbRow());
		
		mAllViews.clear();
		mLineHeight.clear();

		int width = getWidth()-lastMoreWidh;

		Log.d(TAG, "sizeWidth2 = "+getWidth());

		int lineWidth = 0;
		int lineHeight = 0;
		// 存储每一行所有的childView
		List<View> lineViews = new ArrayList<View>();
		int cCount = getChildCount();

		boolean isMoreAdded = false;
		// 遍历所有的孩子
		for (int i = 0; i < cCount; i++)
		{
			View child = getChildAt(i);

			MarginLayoutParams lp = (MarginLayoutParams) child
					.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

		
			if(mAllViews.size() == getVisilbRow() && child.getId() == 200){	// 如果已经需要换行
			
				
				lineWidth += (childWidth + lp.leftMargin + lp.rightMargin);
				lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
						+ lp.bottomMargin);
				lineViews.add(child);
				
				//换行
				isMoreAdded = true;
				// 记录这一行所有的View以及最大高度
				mLineHeight.add(lineHeight);
				// 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
				mAllViews.add(lineViews);
				lineWidth = 0;// 重置行宽
				lineViews = new ArrayList<View>();
				continue;
			}			
			else if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width)	// 如果已经需要换行
			{
				if(mAllViews.size() == getVisilbRow() && !isMoreAdded){

					isMoreAdded = true;
				}else{
					Log.d(TAG, "i = "+i+",childWidth2 = "+(childWidth + lp.leftMargin + lp.rightMargin + lineWidth));
					// 记录这一行所有的View以及最大高度
					mLineHeight.add(lineHeight);
					// 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
					mAllViews.add(lineViews);
					lineWidth = 0;// 重置行宽
					lineViews = new ArrayList<View>();
				}
			}
			/**
			 * 如果不需要换行，则累加
			 */
			lineWidth += (childWidth + lp.leftMargin + lp.rightMargin);
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
					+ lp.bottomMargin);
			lineViews.add(child);
		}
		// 记录最后一行
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);

		int left = 0;
		int top = 0;
		// 得到总行数
		int lineNums = mAllViews.size();
		for (int i = 0; i <lineNums; i++)
		{
			// 每一行的所有的views
			lineViews = mAllViews.get(i);
			// 当前行的最大高度
			lineHeight = mLineHeight.get(i);

			//Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
			Log.e(TAG, "第" + i + "行， ：" + lineHeight);

			// 遍历当前行所有的View
			//linesize = 1;
			for (int j = 0; j < lineViews.size(); j++)
			{
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE)
				{
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child
						.getLayoutParams();

				//计算childView的left,top,right,bottom
				int lc = left + lp.leftMargin;
				int tc = top + lp.topMargin;
				int rc =lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();

				Log.e(TAG, "id = "+child.getId() + " , l = " + lc + " , t = " + t + " , r ="
						+ rc + " , b = " + bc);

				child.layout(lc, tc, rc, bc);

				left += child.getMeasuredWidth() + lp.rightMargin
						+ lp.leftMargin;
			}
			left = 0;
			if(VisilbRow < 0){
				top += lineHeight;
			}

		}

	}

	int getMoreIndex(int row){
		
		if(row >= mAllViews.size() || row < 0) return -1;
		
		int MoreIndex = 0;

		List<View> rowList = mAllViews.get(row);
		View lastV = rowList.get(rowList.size() - 1);
		MoreIndex = lastV.getId() + 1;

		return MoreIndex;
	}
	
	public int getRowCount(){
		return mAllViews.size();
	}
	public void setVisibility(int row){

		if(row >= 0){
			int  lineNum = mAllViews.size();
			for(int i = 0;i < lineNum;i++){
				if(row != i){

					List<View> rowList = mAllViews.get(i);
					for(View v :rowList){

						v.setVisibility(View.GONE);
					}
				}else{
					List<View> rowList = mAllViews.get(i);
					for(View v :rowList){

						v.setVisibility(View.VISIBLE);
					}
				}
			}

		}
		else
		{
			int  lineNum = mAllViews.size();
			for(int i = 0;i < lineNum;i++){
				if(row != i){

					List<View> rowList = mAllViews.get(i);
					for(View v :rowList){

						v.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}
}
