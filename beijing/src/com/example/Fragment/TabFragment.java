package com.example.Fragment;

import com.example.beijing.R;
import com.example.custom.NoScrollViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabFragment extends Fragment {

	private View view;

	private RadioGroup group;

	private NoScrollViewPager Mpager;

	public TabFragment() {

	}

	public TabFragment(NoScrollViewPager pager) {

		this.Mpager = pager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab_fragment, container, false);
		setcheckAndListener();

		return view;
	}

	private void setcheckAndListener() {
		// TODO Auto-generated method stub
		group = (RadioGroup) view.findViewById(R.id.tab_group);
		group.check(R.id.tab_button1);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.tab_button1:
						Mpager.setCurrentItem(0);
						break;
					case R.id.tab_button2:
						Mpager.setCurrentItem(1);
						break;
					case R.id.tab_button3:
						Mpager.setCurrentItem(2);
						break;
					case R.id.tab_button4:
						Mpager.setCurrentItem(3);
						break;
					case R.id.tab_button5:
						Mpager.setCurrentItem(4);
						break;
					default:
						break;
				}
			}
		});
	}
}
