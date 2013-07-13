package com.example.kinderbooks;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener {

	MyView myView;

	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;
	boolean showMenu = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myView = new MyView(this);
		myView.setOnTouchListener(this);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});
		soundID = soundPool.load(this, R.raw.sound1, 1);
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
		if (showMenu) {
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.1)
					&& event.getY() < (myView.getHeight() * 0.2)) {
				myView.drawCat();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.21)
					&& event.getY() < (myView.getHeight() * 0.3)) {
				myView.drawDog();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.31)
					&& event.getY() < (myView.getHeight() * 0.4)) {
				myView.drawDuck();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.41)
					&& event.getY() < (myView.getHeight() * 0.5)) {
				myView.drawChickenNotFull();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.51)
					&& event.getY() < (myView.getHeight() * 0.6)) {
				myView.drawChickenFull();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.61)
					&& event.getY() < (myView.getHeight() * 0.7)) {
				myView.OnOffDrawDrag();
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.71)
					&& event.getY() < (myView.getHeight() * 0.8)) {
				AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
				float actualVolume = (float) audioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				float maxVolume = (float) audioManager
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = actualVolume / maxVolume;
				if (loaded) {
					soundPool.play(soundID, volume, volume, 1, 0, 1f);
				}
			}
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.81)
					&& event.getY() < (myView.getHeight() * 0.9)) {
				showMenu = false;
				myView.setShowMenu(false);
			}
		} else {
			if (event.getX() < (myView.getWidth() * 0.4)
					&& event.getY() > (myView.getHeight() * 0.1)
					&& event.getY() < (myView.getHeight() * 0.2)) {
				showMenu = true;
				myView.setShowMenu(true);
			}
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.i("CAT2", event.getX() + "");
		myView.drawDrag(event.getX(), event.getY());
		return false;
	}
}
