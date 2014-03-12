package com.hitown.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private LogView mLogView;
	private EditText mDevice, mReadLeth, mWritCmd, mIoctlCmd;
	private Button mReadBtn, mWriteBtn, mIoctlBtn, mOpenBtn, mCloseBtn;
	private PartsManager mManager = null;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		mManager =  PartsManager.getInstance();
		mManager.addHandler(mHandler);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mLogView = (LogView) findViewById(R.id.demo_log);
		mDevice = (EditText) findViewById(R.id.devname);
		mReadLeth = (EditText) findViewById(R.id.readleth);
		mWritCmd = (EditText) findViewById(R.id.writecmd);
		mIoctlCmd = (EditText) findViewById(R.id.ioctlcmd);

		mReadBtn = (Button) findViewById(R.id.btn_read);
		mWriteBtn = (Button) findViewById(R.id.btn_write);
		mIoctlBtn = (Button) findViewById(R.id.btn_ioctl);
		mOpenBtn = (Button) findViewById(R.id.btn_open);
		mCloseBtn = (Button) findViewById(R.id.btn_close);
		mLogView.setDebug(true);

		mReadBtn.setOnClickListener(new OnClickListenerImpl());
		mWriteBtn.setOnClickListener(new OnClickListenerImpl());
		mIoctlBtn.setOnClickListener(new OnClickListenerImpl());
		mOpenBtn.setOnClickListener(new OnClickListenerImpl());
		mCloseBtn.setOnClickListener(new OnClickListenerImpl());

		mDevice.setText("/dev/pca9555");

	}

	private void showLog(String logs) {
		int color = count % 2 == 0 ? Color.RED : Color.BLUE;
		count++;
		mLogView.debug(logs, color);
	}

	private void onOpenBtnClick() {
		if (!TextUtils.isEmpty(mDevice.getText().toString())) {
			mManager.openDev(mDevice.getText().toString());
		} else {
			showLog("设备名称为空");
		}

	}

	private void onCloseBtnClick() {
		if (!TextUtils.isEmpty(mDevice.getText().toString())) {
			mManager.closeDev(mDevice.getText().toString());
		} else {
			showLog("设备名称为空");
		}
	}

	private void onReadBtnClick() {
		
		if (!TextUtils.isEmpty(mReadLeth.getText().toString())) {
			String lenth = mReadLeth.getText().toString();
			byte[] buffer = new byte[1024];
			mManager.readDev((buffer));
		} else {
			showLog("读取长度为空");
		}
	}

	private void onWriteBtnClick() {
		if (!TextUtils.isEmpty(mWritCmd.getText().toString())) {
			byte data = Byte.parseByte(mWritCmd.getText().toString());
			byte[] buffer = new byte[]{(byte) data};
			int lenth = mManager.writeDev(buffer);
			showLog("write lenth = "+lenth);
		} else {
			showLog("写入指令为空");
		}
	}

	private void onIoctlBtnClick() {
		mManager.ioctrlDev(22, 0);
	}

	private class OnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.btn_read:
				onReadBtnClick();
				break;
			case R.id.btn_write:
				onWriteBtnClick();
				break;
			case R.id.btn_ioctl:
				onIoctlBtnClick();
				break;
			case R.id.btn_open:
				onOpenBtnClick();
				break;
			case R.id.btn_close:
				onCloseBtnClick();
				break;

			default:
				break;
			}
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case PartsManager.MSG_JNI_POST:
				String info = "JNI : " + (String) msg.obj;
				mLogView.debug(info, Color.GREEN);
				break;

			default:
				break;
			}
		}

	};
}
