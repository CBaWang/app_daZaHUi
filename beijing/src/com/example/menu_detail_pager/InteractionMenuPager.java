package com.example.menu_detail_pager;

import com.example.beijing.R;
import com.example.interact_chat.ChatMessage;
import com.example.interact_chat.ChatMessageAdapter;
import com.example.interact_chat.HongHttpUtils;

import android.app.Activity;
import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InteractionMenuPager extends BaseMenuDetailPager {

	public InteractionMenuPager(Activity activity) {
		super(activity);
	}

	private ListView mChatView;

	private EditText mMsg;

	private Button sendButton;

	private List<ChatMessage> mDatas ;

	private ChatMessageAdapter mAdapter;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ChatMessage from = (ChatMessage) msg.obj;
			mDatas.add(from);
			mAdapter.notifyDataSetChanged();
			mChatView.setSelection(mDatas.size()-1);
		}
	};
	@Override
	public View initView() {
		View chatView = View.inflate(Mactivity,R.layout.main_chatting,null);

		mChatView = (ListView) chatView.findViewById(R.id.id_chat_listView);
		mMsg = (EditText) chatView.findViewById(R.id.id_chat_msg);

		mDatas  = new ArrayList<ChatMessage>();
		sendButton = (Button) chatView.findViewById(R.id.id_chat_send);

		initData();

		return chatView;
		
	}

	@Override
	public void initData() {
		super.initData();

		mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "我是祖国花朵，我骄傲"));


		mAdapter = new ChatMessageAdapter(Mactivity,mDatas);

		mChatView.setAdapter(mAdapter);

		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String msg = mMsg.getText().toString();
				if(TextUtils.isEmpty(msg)){
					Toast.makeText(Mactivity, "您还没有填写信息呢...", Toast.LENGTH_SHORT).show();
					return;
				}

				ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT,msg);
				to.setData(new Date());
				mDatas.add(to);

				mAdapter.notifyDataSetChanged();
				mChatView.setSelection(mDatas.size() - 1);

				mMsg.setText("");


				InputMethodManager imm = (InputMethodManager) Mactivity.getSystemService(Context.INPUT_METHOD_SERVICE);

				if(imm.isActive()){
					imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_IMPLICIT_ONLY);
					
				}

				new Thread(new Runnable() {
					@Override
					public void run() {

						ChatMessage from = null;

						try {
							from = HongHttpUtils.sendMsg(msg);
						} catch (Exception e) {
							from = new ChatMessage(ChatMessage.Type.INPUT,"服务器傻了");
						}

						Message message = Message.obtain();
						message.obj = from;
						mHandler.sendMessage(message);


					}
				}).start();



			}
		});//end

	}
}
