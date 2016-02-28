package com.example.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.beijing.R;
import com.example.custom.TouchImageView;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/2/28.
 */
public class ImageDialogUtils {

  public static void showDiaLogImage(Context context,String url){
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      BitmapUtils Utils = BitmapHelper.getInstance();
      TouchImageView image = new TouchImageView(context);

//      ViewGroup.LayoutParams params = image.getLayoutParams();
//      params.width =700;
//      params.height = 900;
//
//      image.setLayoutParams(params);

      Utils.display(image, url);
      AlertDialog dialog = builder.create();
      dialog.setView(image, 0, 0, 0, 0);
      dialog.show();
      WindowManager.LayoutParams windowsparams = dialog.getWindow().getAttributes();
      windowsparams.gravity = Gravity.CENTER;
//      windowsparams.width=500;
//      windowsparams.height=700;
      dialog.getWindow().setAttributes(windowsparams);

  }





}
