package com.example.kinderbooks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener {

	MyView myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myView = new MyView(this);
		myView.setOnTouchListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(myView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		myView.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.i("CAT2", event.getX() + "/" + event.getY());
		if (event.getX() < 100 && event.getY() > 90 && event.getY() < 110) {
			myView.drawCat();
		}
		if (event.getX() < 100 && event.getY() > 190 && event.getY() < 210) {
			myView.drawDog();
		}
		if (event.getX() < 100 && event.getY() > 290 && event.getY() < 310) {
			myView.drawDuck();
		}
		if (event.getX() < 100 && event.getY() > 390 && event.getY() < 410) {
			myView.drawChickenNotFull();
		}
		if (event.getX() > 100 && event.getY() > 390 && event.getY() < 410) {
			myView.drawChickenFull();
		}
		return false;
	}
}
