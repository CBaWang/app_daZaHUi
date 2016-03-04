package com.example.menu_detail_pager;

import android.app.Activity;


import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.beijing.R;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;


public class SpecialMenuPager extends BaseMenuDetailPager {


    private BrokenView mBrokenView;

    private Paint whitePaint;

    private BrokenTouchListener colorfulListener;

    private ImageView BrokenImage;

    private BrokenTouchListener whiteListener;

    private RelativeLayout relative;

    private ImageView image;

    public SpecialMenuPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {

        View view = View.inflate(Mactivity, R.layout.dragged_item, null);

        BrokenImage = (ImageView) view.findViewById(R.id.special_image);

        relative = (RelativeLayout) view.findViewById(R.id.special_Relative);

        image = (ImageView) view.findViewById(R.id.special_reset);



        initData();

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int status  = BrokenImage.getVisibility();
                if(status==View.INVISIBLE ||status==View.GONE) {


                    RotateAnimation rotate = new RotateAnimation(0, 360,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(400);
                    rotate.setInterpolator(new LinearInterpolator());
                    rotate.setRepeatCount(2);
                    image.startAnimation(rotate);

                    rotate.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            mBrokenView.reset();
                            BrokenImage.setOnTouchListener(colorfulListener);
                            BrokenImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });


                }else{
                    Toast.makeText(Mactivity,"请大力滑动页面",Toast.LENGTH_SHORT).show();
                }



            }
        });



        mBrokenView = BrokenView.add2Window(Mactivity);

        whitePaint = new Paint();
        whitePaint.setColor(0xffffffff);

        colorfulListener = new BrokenTouchListener.Builder(mBrokenView).
                build();
        whiteListener = new BrokenTouchListener.Builder(mBrokenView).
                setPaint(whitePaint).
                build();

        BrokenImage.setOnTouchListener(colorfulListener);
    }
}
