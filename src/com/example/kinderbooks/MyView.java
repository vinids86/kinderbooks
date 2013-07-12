package com.example.kinderbooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements Runnable {
	Paint paint;
	SurfaceHolder holder;
	Thread renderThread = null;

	volatile boolean running = false;
	boolean catVisible;
	boolean dogVisible;
	boolean duckVisible;
	boolean chickenFullVisible;
	boolean chickenNotFullVisible;

	public MyView(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	public void resume() {
		running = true;

		catVisible = false;
		dogVisible = false;
		duckVisible = false;
		chickenFullVisible = false;
		chickenNotFullVisible = false;

		renderThread = new Thread(this);
		renderThread.start();
	}

	@Override
	public void run() {
		while (running) {
			if (!holder.getSurface().isValid())
				continue;
			Canvas canvas = holder.lockCanvas();
			// background
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
					R.drawable.background), 0, 0, paint);
			if (chickenNotFullVisible)
				drawCharacter(canvas, R.drawable.chicken_not_full, 0.05f, 0.65f);
			if (chickenFullVisible)
				drawCharacter(canvas, R.drawable.chicken_full, 0.05f, 0.65f);
			if (duckVisible)
				drawCharacter(canvas, R.drawable.duck, 0.59f, 0.75f);
			if (dogVisible)
				drawCharacter(canvas, R.drawable.dog, 1f, 0.65f);
			if (catVisible)
				drawCharacter(canvas, R.drawable.cat, 1f, 0.80f);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private void drawCharacter(Canvas canvas, int character, float positionX,
			float positionY) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), character);
		int cx = (int) ((mWidth - bitmap.getWidth()) * positionX);
		int cy = (int) ((mHeight - bitmap.getHeight()) * positionY);
		canvas.drawBitmap(bitmap, cx, cy, paint);
	}

	private int mWidth;
	private int mHeight;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		setMeasuredDimension(mWidth, mHeight);
	}

	public void drawCat() {
		catVisible = !catVisible;
	}

	public void drawDog() {
		dogVisible = !dogVisible;
	}

	public void drawDuck() {
		duckVisible = !duckVisible;
	}

	public void drawChickenFull() {
		chickenFullVisible = !chickenFullVisible;
	}

	public void drawChickenNotFull() {
		chickenNotFullVisible = !chickenNotFullVisible;
	}
	/*
	 * private void drawCat(Canvas canvas) { Bitmap bitmap =
	 * BitmapFactory.decodeResource(getResources(), R.drawable.cat); int cx =
	 * (int) ((mWidth - bitmap.getWidth())); int cy = (int) ((mHeight -
	 * bitmap.getHeight()) * 0.85);
	 * 
	 * // canvas.rotate(-mValues[0], mWidth/2, mHeight/2); // Thanks @NickT
	 * 
	 * canvas.drawBitmap(bitmap, cx, cy, null);
	 * 
	 * canvas.drawBitmap( bitmap,
	 * getResources().getDisplayMetrics().heightPixels / 2 - bitmap.getHeight()
	 * / 2, getResources().getDisplayMetrics().widthPixels / 2 -
	 * bitmap.getWidth() / 2, paint);
	 * 
	 * Log.i("CAT2", bitmap.getWidth() + ""); }
	 */
}
