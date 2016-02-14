package com.example.beijing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class WebActivity extends Activity {

	private ImageButton back, share, textSize;

	private WebView webview;

	private final static String url = "http://wap.lexun.com/index.php?_r=48560954&cd=0&lxt=c1222562877&_r=363660889&vs=0";

	private final static String[] size = { "超大号字体", "大号字体", "正常字体", "小号字体",
			"超小号字体" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity);
		intiView();
		initData();

	}

	private void intiView() {
		// TODO Auto-generated method stub
		back = (ImageButton) findViewById(R.id.web_activity_back);
		share = (ImageButton) findViewById(R.id.web_activity_share);
		textSize = (ImageButton) findViewById(R.id.web_activity_textsize);

		webview = (WebView) findViewById(R.id.web_activity_webView);
	}

	private void initData() {
		// TODO Auto-generated method stub
		setTheWebView(); // 设置webView
		setTheBackImage(); // 设置返回按键
		setTheShareImage(); // 设置分享按键
		setTheTextSizeImage(); // 设置字体放大缩小按键

	}

	private int ChooseWhich; // 设置一个当前对话框被选中位置的变量

	private int ChooseThis = 2;//操作者选中哪个的变量

	private void setTheTextSizeImage() {
		// TODO Auto-generated method stub

		textSize.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						WebActivity.this);
				dialog.setTitle("请选择字体大小");
				dialog.setSingleChoiceItems(size,ChooseThis,
						new DialogInterface.OnClickListener() { // 设置一个可选择字体大小
							// 的多列对话框

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								// TODO Auto-generated method stub
								ChooseWhich = which;

							}
						});

				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) { // 这里的which永远是0，所以得从上面的which获取到底选中的是哪一个

								// SMALLEST(50),
								// SMALLER(75),
								// NORMAL(100),
								// LARGER(150),
								// LARGEST(200);
								WebSettings setting = webview.getSettings();
								switch (ChooseWhich) {
									case 0:

										setting.setTextZoom(200);

										break;
									case 1:

										setting.setTextZoom(150);
										break;
									case 2:

										setting.setTextZoom(100);
										break;
									case 3:

										setting.setTextZoom(75);
										break;
									case 4:
										setting.setTextZoom(50);

										break;

									default:
										break;
								}

								ChooseThis = ChooseWhich;
							}
						});

				dialog.setNegativeButton("取消", null);

				dialog.show();

			}

		});
	}

	private void setTheShareImage() {
		// TODO Auto-generated method stub
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setTheBackImage() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebActivity.this.finish();
			}
		});
	}

	private void setTheWebView() {
		// TODO Auto-generated method stub

		WebSettings setting = webview.getSettings();
		setting.setJavaScriptEnabled(true); // 支持JavaScript
		setting.setBuiltInZoomControls(true); // zoom是放大 缩小

		webview.loadUrl(url);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				view.loadUrl(url);
				return true;
			}
		});

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
		});

	}

}
