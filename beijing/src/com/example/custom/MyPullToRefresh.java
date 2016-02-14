package com.example.custom;

import com.example.beijing.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyPullToRefresh extends LinearLayout implements OnTouchListener {

	public static final int STATUS_PULL_TO_REFRESH = 0;

	public static final int STATUS_RELEASE_TO_REFRESH = 1;

	public static final int STATUS_REFRESHING = 2;

	public static final int STATUS_REFRESH_FINISHED = 3;

	public static final int SCROLL_SPEED = -20;

	public static final long ONE_MINUTE = 60 * 1000;

	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	public static final long ONE_DAY = 24 * ONE_HOUR;

	public static final long ONE_MONTH = 30 * ONE_DAY;

	public static final long ONE_YEAR = 12 * ONE_MONTH;

	private static final String UPDATED_AT = "updated_at";

	/**
	 * 下拉刷新的回调接口
	 */
	private PullToRefreshListener mListener;

	/**
	 * 用于存储上次更新时间
	 */
	private SharedPreferences preferences;

	/**
	 * 下拉头的View
	 */
	private View header;

	/**
	 * 需要去下拉刷新的ListView
	 */
	private ListView listView;

	/**
	 * 刷新时显示的进度条
	 */
	private ProgressBar progressBar;

	/**
	 * 指示下拉和释放的箭头
	 */
	private ImageView arrow;

	/**
	 * 指示下拉和释放的文字描述
	 */
	private TextView description;

	/**
	 * 上次更新时间的文字描述
	 */
	private TextView updateAt;

	/**
	 * 下拉头的布局参数
	 */
	private MarginLayoutParams headerLayoutParams;

	/**
	 * 上次更新时间的毫秒值
	 */
	private long lastUpdateTime;

	/**
	 * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
	 */
	private int mId = -1;

	/**
	 * 下拉头的高度
	 */
	private int hideHeaderHeight;

	/**
	 * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
	 * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
	 */
	private int currentStatus = STATUS_REFRESH_FINISHED;;

	/**
	 * 记录上一次的状态是什么，避免进行重复操作
	 */
	private int lastStatus = currentStatus;

	/**
	 * 手指按下时的屏幕纵坐标
	 */
	private float yDown;

	/**
	 * 在被判定为滚动之前用户手指可以移动的最大值。
	 */
	private int touchSlop;

	/**
	 * 是否已加载过一次layout(布局)，这里onLayout中的初始化只需加载一次
	 */
	private boolean loadOnce;

	/**
	 * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
	 */
	private boolean ableToPull;

	public MyPullToRefresh(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyPullToRefresh(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyPullToRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = LayoutInflater.from(context).inflate(
				R.layout.my_pull_to_refresh, null, true);
		progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
		arrow = (ImageView) header.findViewById(R.id.my_pull_arrow);
		description = (TextView) header.findViewById(R.id.description);
		updateAt = (TextView) header.findViewById(R.id.updated_at);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		refreshUpdateAtValue();
		setOrientation(VERTICAL);
		addView(header, 0);

	}

	private void refreshUpdateAtValue() {

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			hideHeaderHeight = -header.getHeight();
			headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			listView = (ListView) getChildAt(1);
			listView.setOnTouchListener(this);
			loadOnce = true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		setIsAblePull(event);
		if (ableToPull) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					yDown = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					float yMove = event.getRawY();
					int distance = (int) (yMove - yDown);

					if (distance <= 0
							&& headerLayoutParams.topMargin <= hideHeaderHeight) {
						return false;
					}
					if (distance < touchSlop) {
						return false;
					}

					if (currentStatus != STATUS_REFRESHING) {
						if (headerLayoutParams.topMargin > 0) {
							currentStatus = STATUS_RELEASE_TO_REFRESH;
						} else {
							currentStatus = STATUS_PULL_TO_REFRESH;
						}

						headerLayoutParams.topMargin = (distance / 2)
								+ hideHeaderHeight;
						header.setLayoutParams(headerLayoutParams);
					}
					break;
				case MotionEvent.ACTION_UP:
				default:
					if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
						new RefreshingTask().execute();
					} else if (currentStatus == STATUS_PULL_TO_REFRESH) {
						new HideHeaderTask().execute();
					}
					break;

			}

			if (currentStatus == STATUS_PULL_TO_REFRESH
					|| currentStatus == STATUS_RELEASE_TO_REFRESH) {
				updateHeaderView();

				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				lastStatus = currentStatus;
				return true;
			}

		}

		return false;
	}

	public void setOnRefreshListener(PullToRefreshListener listener,int id){
		mListener = listener;
		mId = id;
	}

	public void finishRefreshing(){
		currentStatus = STATUS_REFRESH_FINISHED;
		preferences.edit().putLong(UPDATED_AT+mId,System.currentTimeMillis()).commit();
	}
	private void updateHeaderView() {
		// TODO Auto-generated method stub

	}

	private void setIsAblePull(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	class HideHeaderTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	public interface PullToRefreshListener { // 我的回调接口方法
		void onRefresh();
	}

}
