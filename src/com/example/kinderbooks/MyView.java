package com.example.kinderbooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements Runnable {
	Paint paint;
	SurfaceHolder holder;
	Thread renderThread = null;
	
	private Bitmap mBackground;

	private int mWidth;
	private int mHeight;

	public int getmWidth() {
		return mWidth;
	}

	public int getmHeight() {
		return mHeight;
	}

	volatile boolean running = false;
	boolean catVisible;
	boolean dogVisible;
	boolean duckVisible;
	boolean chickenFullVisible;
	boolean chickenNotFullVisible;

	boolean showMenu;
	boolean drawDrag;
	float dragX;
	float dragY;

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

		showMenu = false;
		drawDrag = false;

		renderThread = new Thread(this);
		renderThread.start();
	}

	@Override
	public void run() {
		while (running) {
			if (!holder.getSurface().isValid())
				continue;

			Canvas canvas = holder.lockCanvas();
			
			mBackground = BitmapFactory.decodeResource(getResources(),
					R.drawable.background);
			mBackground= Bitmap.createScaledBitmap(mBackground, getWidth(), getHeight(), true);
			canvas.drawBitmap(mBackground, 0, 0, paint);

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

			if (drawDrag) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.ball);
				canvas.drawBitmap(bitmap, this.dragX - bitmap.getWidth() / 2,
						this.dragY - bitmap.getHeight(), paint);
			}
			if (showMenu) {
				drawButtons(canvas);
			} else {
				drawShowMenu(canvas);
			}
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

	public void drawDrag(float x, float y) {
		this.dragX = x;
		this.dragY = y;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		setMeasuredDimension(mWidth, mHeight);
	}

	public void drawButtons(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize((float) (mHeight * 0.06));
		canvas.drawText("Gato", (float) (mWidth * 0.05),
				(float) (mHeight * 0.15), paint);
		canvas.drawText("Cao", (float) (mWidth * 0.05),
				(float) (mHeight * 0.25), paint);
		canvas.drawText("Pato", (float) (mWidth * 0.05),
				(float) (mHeight * 0.35), paint);
		canvas.drawText("Galinha1", (float) (mWidth * 0.05),
				(float) (mHeight * 0.45), paint);
		canvas.drawText("Galinha2", (float) (mWidth * 0.05),
				(float) (mHeight * 0.55), paint);
		canvas.drawText("Bola*", (float) (mWidth * 0.05),
				(float) (mHeight * 0.65), paint);
		canvas.drawText("Play", (float) (mWidth * 0.05),
				(float) (mHeight * 0.75), paint);
		canvas.drawText("Esconder", (float) (mWidth * 0.05),
				(float) (mHeight * 0.85), paint);
	}
	
	public void drawShowMenu(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize((float) (mHeight * 0.06));
		canvas.drawText("Menu", (float) (mWidth * 0.05),
				(float) (mHeight * 0.15), paint);
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

	public void OnOffDrawDrag() {
		drawDrag = !drawDrag;
	}

	public void setShowMenu(boolean b) {
		this.showMenu = b;
	}
}
