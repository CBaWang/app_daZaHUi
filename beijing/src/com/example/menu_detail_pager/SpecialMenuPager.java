package com.example.menu_detail_pager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.beijing.R;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

public class SpecialMenuPager extends BaseMenuDetailPager {


    LinearLayout ll_max_chose_layout;
    CheckBox cb_is_single_mode, cb_is_need_camera, cb_is_need_crop, cb_is_square;
    EditText ed_span_count, ed_maxchose_count;
    LinearLayout ll_cropinfo;
    SeekBar seekBar;
    Button button;

    public SpecialMenuPager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        View view = View.inflate(Mactivity, R.layout.special_layout, null);

        seekBar =  (SeekBar)view.findViewById(R.id.seekbar);
        cb_is_single_mode = (CheckBox) view.findViewById(R.id.cb_is_single_mode);
        cb_is_need_camera =(CheckBox) view.findViewById(R.id.cb_is_need_camera);
        cb_is_need_crop = (CheckBox) view.findViewById(R.id.cb_is_need_crop);
        ed_span_count = (EditText) view.findViewById(R.id.ed_span_count);
        cb_is_square = (CheckBox) view.findViewById(R.id.cb_is_square);
        ed_maxchose_count = (EditText) view.findViewById(R.id.ed_maxchose_count);
        ll_cropinfo = (LinearLayout) view.findViewById(R.id.ll_cropinfo);
        ll_max_chose_layout = (LinearLayout) view.findViewById(R.id.ll_max_chose_layout);
        button = (Button) view.findViewById(R.id.bt_start_chose);
        initData();

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        cb_is_single_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cb_is_need_crop.setEnabled(true);
                    ll_max_chose_layout.setVisibility(View.GONE);
                }else {
                    cb_is_need_crop.setChecked(false);
                    cb_is_need_crop.setEnabled(false);
                    ll_max_chose_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        cb_is_need_crop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ll_cropinfo.setVisibility(View.VISIBLE);
                }else {
                    ll_cropinfo.setVisibility(View.GONE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chose_mode = cb_is_single_mode.isChecked() ? PickConfig.MODE_SINGLE_PICK : PickConfig.MODE_MULTIP_PICK;
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(seekBar.getProgress());
                new PickConfig.Builder(Mactivity)
                        .isneedcrop(cb_is_need_crop.isChecked())
                        .actionBarcolor(Color.parseColor("#E91E63"))
                        .statusBarcolor(Color.parseColor("#D81B60"))
                        .isneedcamera(cb_is_need_camera.isChecked())
                        .isSqureCrop(cb_is_square.isChecked())
                        .setUropOptions(options)
                        .maxPickSize(Integer.parseInt(ed_maxchose_count.getText().toString()))
                        .spanCount(Integer.parseInt(ed_span_count.getText().toString()))
                        .pickMode(chose_mode).build();
            }
        });


    }
}
