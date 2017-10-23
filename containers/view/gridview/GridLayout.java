package com.hyj.containers.view.gridview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.hyj.containers.adapter.CustomAdapter;
import com.kemov.elecmobiletools.R;

import java.util.List;
import java.util.Map;

public class GridLayout extends GridView{
	/**
	 * DragGridView��item������Ӧ��ʱ�䣬 Ĭ����1000���룬Ҳ������������
	 */
	private long dragResponseMS = 1000;
	
	/**
	 * �Ƿ������ק��Ĭ�ϲ�����
	 */
	private boolean isDrag = false;
	
	private int mDownX;
	private int mDownY;
	private int moveX;
	private int moveY;
	/**
	 * ������ק��position
	 */
	private int mDragPosition;
	private int mFirstDragPosition;
	/**
	 * �տ�ʼ��ק��item��Ӧ��View
	 */
	private View mStartDragItemView = null;
	
	/**
	 * ������ק�ľ�������ֱ����һ��ImageView
	 */
	private ImageView mDragImageView;
	
	/**
	 * ����
	 */
	private Vibrator mVibrator;
	
	private WindowManager mWindowManager;
	/**
	 * item����Ĳ��ֲ���
	 */
	private WindowManager.LayoutParams mWindowLayoutParams;
	
	/**
	 * ������ק��item��Ӧ��Bitmap
	 */
	private Bitmap mDragBitmap;
	
	/**
	 * ���µĵ㵽����item���ϱ�Ե�ľ���
	 */
	private int mPoint2ItemTop ; 
	
	/**
	 * ���µĵ㵽����item�����Ե�ľ���
	 */
	private int mPoint2ItemLeft;
	
	/**
	 * DragGridView������Ļ������ƫ����
	 */
	private int mOffset2Top;
	
	/**
	 * DragGridView������Ļ��ߵ�ƫ����
	 */
	private int mOffset2Left;
	
	/**
	 * ״̬���ĸ߶�
	 */
	private int mStatusHeight; 
	
	/**
	 * DragGridView�Զ����¹����ı߽�ֵ
	 */
	private int mDownScrollBorder;
	
	/**
	 * DragGridView�Զ����Ϲ����ı߽�ֵ
	 */
	private int mUpScrollBorder;
	
	/**
	 * DragGridView�Զ��������ٶ�
	 */
	private static final int speed = 80;
	
	/**
	 * item�����仯�ص��Ľӿ�
	 */
	private OnChangeListener onChangeListener;
	
	
	private Context mContext;

	private CustomAdapter mCustomAdapter;
	
	private int layoutSource;

	private SparseArray<View.OnClickListener> mOnClickListeners;
	
	private Map<Integer, OnFocusChangeListener> mOnFocusChangeListeners;
	
	private Map<Integer, OnLongClickListener> mOnLongClickListeners;
	
	public CustomAdapter getmCustomAdapter() {
		return mCustomAdapter;
	}

	public void setmCustomAdapter(CustomAdapter mCustomAdapter) {
		this.mCustomAdapter = mCustomAdapter;
	}

	public int getLayoutSource() {
		return layoutSource;
	}

	public void setLayoutSource(int layoutSource) {
		this.layoutSource = layoutSource;
	}

	public SparseArray<View.OnClickListener> getmOnClickListeners() {
		return mOnClickListeners;
	}
	public Map<Integer, OnFocusChangeListener> getOnFocusChangeListeners() {
		return mOnFocusChangeListeners;
	}

	public Map<Integer, OnLongClickListener> getOnLongClickListeners() {
		return mOnLongClickListeners;
	}

	public void setOnFocusChangeListeners(
			Map<Integer, OnFocusChangeListener> onFocusChangeListeners) {
		this.mOnFocusChangeListeners = onFocusChangeListeners;
	}
	public void setOnLongClickListener(
			Map<Integer, OnLongClickListener> onLongClickListener) {
		this.mOnLongClickListeners = onLongClickListener;
	}
	public GridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mStatusHeight = getStatusHeight(context); //��ȡ״̬���ĸ߶�
		
		this.mContext = context;
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
		setLayoutSource(array.getResourceId(R.styleable.ItemLayout_item_layout, 0));
		array.recycle();
	}
	public void notifyDataSetChanged(List<? extends Map<String, Object>> data) {
		mCustomAdapter.setmData(data);
		mCustomAdapter.notifyDataSetChanged();
	}
	public void setAdapterValue(List<? extends Map<String, Object>> data,boolean select) {
		mCustomAdapter = new CustomAdapter(mContext, data, getLayoutSource(), 
				getmOnClickListeners(), getOnFocusChangeListeners(), getOnLongClickListeners(),select);
		setAdapter(mCustomAdapter);
	}
private Handler mHandler = new Handler();
	
	//���������Ƿ�Ϊ������Runnable
	private Runnable mLongClickRunnable = new Runnable() {
		
		@Override
		public void run() {
			isDrag = true; //���ÿ�����ק
			mVibrator.vibrate(50); //��һ��
			
			mStartDragItemView.setVisibility(View.INVISIBLE);//���ظ�item
			
			//�������ǰ��µĵ���ʾitem����
			createDragImage(mDragBitmap, mDownX, mDownY);
			
			
		}
	};
	
	/**
	 * ���ûص��ӿ�
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener){
		this.onChangeListener = onChangeListener;
	}
	
	/**
	 * ������Ӧ��ק�ĺ�������Ĭ����1000����
	 * @param dragResponseMS
	 */
	public void setDragResponseMS(long dragResponseMS) {
		this.dragResponseMS = dragResponseMS;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	//   public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			//ʹ��Handler�ӳ�dragResponseMSִ��mLongClickRunnable
			mHandler.postDelayed(mLongClickRunnable, dragResponseMS);	
			mDownX = (int) ev.getX();
			mDownY = (int) ev.getY();
			//���ݰ��µ�X,Y�����ȡ�����item��position
			mDragPosition = pointToPosition(mDownX, mDownY);
			if(mDragPosition == AdapterView.INVALID_POSITION){
				return super.dispatchTouchEvent(ev);
				//return super.onInterceptTouchEvent(ev);
			}
			mFirstDragPosition = mDragPosition;
			//����position��ȡ��item����Ӧ��View
			mStartDragItemView = getChildAt(mDragPosition - getFirstVisiblePosition());			
			//�����⼸�������ҿ��Բο��ҵĲ��������ͼ�������
			mPoint2ItemTop = mDownY - mStartDragItemView.getTop();
			mPoint2ItemLeft = mDownX - mStartDragItemView.getLeft();	
			mOffset2Top = (int) (ev.getRawY() - mDownY);
			mOffset2Left = (int) (ev.getRawX() - mDownX);	
			//��ȡDragGridView�Զ����Ϲ�����ƫ������С�����ֵ��DragGridView���¹���
			mDownScrollBorder = getHeight() /4;
			//��ȡDragGridView�Զ����¹�����ƫ�������������ֵ��DragGridView���Ϲ���
			mUpScrollBorder = getHeight() * 3/4;
			//����mDragItemView��ͼ����
			mStartDragItemView.setDrawingCacheEnabled(true);
			//��ȡmDragItemView�ڻ����е�Bitmap����
			mDragBitmap = Bitmap.createBitmap(mStartDragItemView.getDrawingCache());
			//��һ���ܹؼ����ͷŻ�ͼ���棬��������ظ��ľ���
			mStartDragItemView.destroyDrawingCache();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int)ev.getX();
			int moveY = (int) ev.getY();
			//��������ڰ��µ�item�����ƶ���ֻҪ������item�ı߽����ǾͲ��Ƴ�mRunnable
			if(!isTouchInItem(mStartDragItemView, moveX, moveY)){
				mHandler.removeCallbacks(mLongClickRunnable);
			}
			break;
		case MotionEvent.ACTION_UP:
			mHandler.removeCallbacks(mLongClickRunnable);
			mHandler.removeCallbacks(mScrollRunnable);
			break;
		}
		return super.dispatchTouchEvent(ev);
	//	return super.onInterceptTouchEvent(ev);
	}

	
	/**
	 * �Ƿ�����GridView��item����
	 * @param itemView
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isTouchInItem(View dragView, int x, int y){
		if (dragView == null) return false;
		int leftOffset = dragView.getLeft();
		int topOffset = dragView.getTop();
		if(x < leftOffset || x > leftOffset + dragView.getWidth()){
			return false;
		}
		if(y < topOffset || y > topOffset + dragView.getHeight()){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(isDrag && mDragImageView != null){
			switch(ev.getAction()){
			/*del by gy 170919
			case MotionEvent.ACTION_MOVE:
				moveX = (int) ev.getX();
				moveY = (int) ev.getY();
				//�϶�item
				onDragItem(moveX, moveY);
				break;*/
			case MotionEvent.ACTION_UP:
				moveX = (int) ev.getX();
				moveY = (int) ev.getY();
				//�϶�item
				onStopDrag();
				isDrag = false;
				break;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	
	/**
	 * �����϶��ľ���
	 * @param bitmap 
	 * @param downX
	 * 			���µĵ���Ը��ؼ���X����
	 * @param downY
	 * 			���µĵ���Ը��ؼ���X����
	 */
	private void createDragImage(Bitmap bitmap, int downX , int downY){
		mWindowLayoutParams = new WindowManager.LayoutParams();
		mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; //ͼƬ֮��������ط�͸��
		mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
		mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
		mWindowLayoutParams.alpha = 0.55f; //͸����
		mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;  
		mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  
		mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
	                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE ;
		  
		mDragImageView = new ImageView(getContext());  
		mDragImageView.setImageBitmap(bitmap);  
		mWindowManager.addView(mDragImageView, mWindowLayoutParams);  
	}
	
	/**
	 * �ӽ��������ƶ��϶�����
	 */
	private void removeDragImage(){
		if(mDragImageView != null){
			mWindowManager.removeView(mDragImageView);
			mDragImageView = null;
		}
		
	}
	
	
	
	/**
	 * �϶�item��������ʵ����item�����λ�ø��£�item���໥�����Լ�GridView�����й���
	 * @param x
	 * @param y
	 */
	private void onDragItem(int moveX, int moveY){
		mWindowLayoutParams.x = moveX - mPoint2ItemLeft + mOffset2Left;
		mWindowLayoutParams.y = moveY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
		mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams); //���¾����λ��
		onSwapItem(moveX, moveY);
		
		//GridView�Զ�����
		mHandler.post(mScrollRunnable);
	}
	// ��������ͼ��һֱ��̬��ʾ��
	@SuppressWarnings("unused")
	private Runnable mTrendsRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	/**
	 * ��moveY��ֵ�������Ϲ����ı߽�ֵ������GridView�Զ����Ϲ���
	 * ��moveY��ֵС�����¹����ı߽�ֵ������GridView�Զ����¹���
	 * ���򲻽��й���
	 */
	private Runnable mScrollRunnable = new Runnable() {
		
		@Override
		public void run() {
			int scrollY;
			if(moveY > mUpScrollBorder){
				 scrollY = -speed;
				 mHandler.postDelayed(mScrollRunnable, 25);
			}else if(moveY < mDownScrollBorder){
				scrollY = speed;
				 mHandler.postDelayed(mScrollRunnable, 25);
			}else{
				scrollY = 0;
				mHandler.removeCallbacks(mScrollRunnable);
			}
			
			//�����ǵ���ָ����GridView���ϻ������¹�����ƫ������ʱ�򣬿���������ָû���ƶ�������DragGridView���Զ��Ĺ���
			//�������������������onSwapItem()����������item
			onSwapItem(moveX, moveY);
			
			View view = getChildAt(mDragPosition - getFirstVisiblePosition());
			//ʵ��GridView���Զ�����
			smoothScrollBy(mDragPosition, view.getTop() + scrollY);
		}
	};
	
	
	/**
	 * ����item,���ҿ���item֮�����ʾ������Ч��
	 * @param moveX
	 * @param moveY
	 */
	private void onSwapItem(int moveX, int moveY){
		//��ȡ������ָ�ƶ������Ǹ�item��position
		int tempPosition = pointToPosition(moveX, moveY);
		//����tempPosition �ı��˲���tempPosition������-1,����н���
		if(tempPosition != mDragPosition && tempPosition != AdapterView.INVALID_POSITION){
			getChildAt(tempPosition - getFirstVisiblePosition()).setVisibility(View.INVISIBLE);//�϶������µ�item,�µ�item���ص�
			getChildAt(mDragPosition - getFirstVisiblePosition()).setVisibility(View.VISIBLE);//֮ǰ��item��ʾ����
			
			if(onChangeListener != null){
				onChangeListener.onChanging(mFirstDragPosition,mDragPosition, tempPosition);
			}
			mDragPosition = tempPosition;
		} else {
			onChangeListener.noChange();
		}
	}
	
	
	/**
	 * ֹͣ��ק���ǽ�֮ǰ���ص�item��ʾ���������������Ƴ�
	 */
	private void onStopDrag(){
		mFirstDragPosition = -1;
		getChildAt(mDragPosition - getFirstVisiblePosition()).setVisibility(View.VISIBLE);	
		this.postInvalidate();
		removeDragImage();
	}
	
	/**
	 * ��ȡ״̬���ĸ߶�
	 * @param context
	 * @return
	 */
	private static int getStatusHeight(Context context){
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return statusHeight;
    }
	
	
	/**
	 * 
	 * @author xiaanming
	 *
	 */
	public interface OnChangeListener{
		
		/**
		 * ��item����λ�õ�ʱ��ص��ķ���������ֻ��Ҫ�ڸ÷�����ʵ�����ݵĽ�������
		 * @param form
		 * 			��ʼ��position
		 * @param to 
		 * 			��ק����position
		 */
		// �ϵ���ʱ�� �������Ǽ��� ֻ�����̧����֮���ȷ������
		public void onChanging(int first,int form, int to);
		public void noChange();
	}
}
