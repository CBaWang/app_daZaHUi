package com.example.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.beijing.R;

public class CustomVolumControlBar extends View {
	private int mFirstColor;

	private int mSecondColor;

	private int mCircleWidth;

	private Paint mPaint;

	private int mCurrentCount = 3;// 默认的进度

	private Bitmap mImage; // 中间的图片

	private int mSplitSize;// 每块块间的间隙

	private int mCount;

	private Rect mRect;

	public CustomVolumControlBar(Context context, AttributeSet attrs,
								 int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray mTypeArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomVolumControlBar);

		mFirstColor = mTypeArray.getColor(
				R.styleable.CustomVolumControlBar_firstColor, Color.GREEN);

		mSecondColor = mTypeArray.getColor(
				R.styleable.CustomVolumControlBar_secondColor, Color.CYAN);

		mImage = BitmapFactory.decodeResource(getResources(), mTypeArray
				.getResourceId(R.styleable.CustomVolumControlBar_bg, 0));

		mCircleWidth = (int) mTypeArray.getDimension(
				R.styleable.CustomVolumControlBar_circleWidth, 20);

		mCount = mTypeArray.getInteger(
				R.styleable.CustomVolumControlBar_dotCount, 20);

		mSplitSize = mTypeArray.getInteger(
				R.styleable.CustomVolumControlBar_splitSize, 20);
		mTypeArray.recycle();
		mPaint = new Paint();
		mRect = new Rect();

	}

	public CustomVolumControlBar(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
	}

	public CustomVolumControlBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(mCircleWidth);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);

		int centre = getWidth() / 2;
		int radius = centre - mCircleWidth / 2;

		drawOval(canvas, centre, radius);

		int relRadius = radius - mCircleWidth / 2;

		mRect.left = (int) ((relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);

		mRect.top = (int) ((relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);

		mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
		mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

		if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
			mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f
					/ 2 - mImage.getHeight() * 1.0f / 2);
			mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage
					.getHeight() * 1.0f / 2);
			mRect.right = mRect.left + mImage.getWidth();
			mRect.bottom = mRect.top + mImage.getHeight();
		}

		canvas.drawBitmap(mImage, null, mRect, mPaint);

	}

	private void drawOval(Canvas canvas, int centre, int radius) { // 画圆

		float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;

		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);

		mPaint.setColor(mFirstColor);

		for (int i = 0; i < mCount; i++) {
			canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize,
					false, mPaint);
		}

		mPaint.setColor(mSecondColor);

		for (int i = 0; i < mCurrentCount; i++) {
			canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize,
					false, mPaint);
		}

	}

	private int xDown, xUp;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDown = (int) event.getY();

				break;

			case MotionEvent.ACTION_UP:

				xUp = (int) event.getY();
				if (xUp > xDown) {

					down();

				} else {

					up();

				}
				break;
		}
		return true;
	}

	private void up() {

		mCurrentCount++;
		postInvalidate();

	}

	private void down() {

		mCurrentCount--;
		postInvalidate();

	}

}
