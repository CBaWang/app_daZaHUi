package com.example.beijing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	RelativeLayout Relative;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Relative = (RelativeLayout) findViewById(R.id.start_Animation);
		startAnimation();

	}

	private void jumpActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, LeaderActivity.class);
		startActivity(intent);
		finish();
	}

	private void startAnimation() {
		// TODO Auto-generated method stub
		RotateAnimation Rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		Rotate.setFillAfter(true);
		Rotate.setDuration(1000);

		AlphaAnimation Alpha = new AlphaAnimation(0, 1);
		Alpha.setFillAfter(true);
		Alpha.setDuration(1000);

		ScaleAnimation Scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		Scale.setFillAfter(true);
		Scale.setDuration(1000);

		AnimationSet set = new AnimationSet(true);
		set.addAnimation(Scale);
		set.addAnimation(Alpha);
		set.addAnimation(Rotate);

		Relative.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				jumpActivity();
			}
		});
	}
}
