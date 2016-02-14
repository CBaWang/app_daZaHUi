package com.example.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.beijing.R;
import com.example.custom.RoundProgressBar;

public class SmartPager extends BasePager {

	private View view;

	private RoundProgressBar one, two, three;

	private Button button;

	private int progress = 0;

	public SmartPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Text.setText("智慧服务");
		setSlidingMenuEnable(true);
		view = View.inflate(mactivity, R.layout.smart_server, null);

		setView();
		buttonListener();
		Frame.addView(view);
	}

	private void buttonListener() {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						while (progress <= 100) {
							progress += 3;
							one.setProgress(progress);
							two.setProgress(progress + 2);
							three.setProgress(progress + 3);

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}).start();

			}

		});

	}

	private void setView() {
		one = (RoundProgressBar) view.findViewById(R.id.smart_server_one);
		two = (RoundProgressBar) view.findViewById(R.id.smart_server_two);
		three = (RoundProgressBar) view.findViewById(R.id.smart_server_three);
		button = (Button) view.findViewById(R.id.smart_server_button);
	}

}
