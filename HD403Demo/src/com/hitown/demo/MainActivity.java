package com.hitown.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LogView mLogView;
	private EditText mDevice, mReadLeth, mWritCmd, mIoctlCmd;
	private Button mReadBtn, mWriteBtn, mIoctlBtn, mOpenBtn, mCloseBtn;
	private TextView mResult;
	private PartsManager mManager = null;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		mManager = PartsManager.getInstance();
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

		mResult = (TextView) findViewById(R.id.write_resutl);
		mLogView.setDebug(true);

		mReadBtn.setOnClickListener(new OnClickListenerImpl());
		mWriteBtn.setOnClickListener(new OnClickListenerImpl());
		mIoctlBtn.setOnClickListener(new OnClickListenerImpl());
		mOpenBtn.setOnClickListener(new OnClickListenerImpl());
		mCloseBtn.setOnClickListener(new OnClickListenerImpl());

		// mDevice.setText("/dev/pca9555");
		mDevice.setText("/dev/ds277x");
		mWritCmd.setText("1");
		mDevice.setEnabled(false);
		mWritCmd.setEnabled(false);

	}

	private void showLog(String logs) {
		int color = count % 2 == 0 ? Color.RED : Color.BLUE;
		count++;
		mLogView.debug(logs, color);
	}

	private void onOpenBtnClick() {
		if (!TextUtils.isEmpty(mDevice.getText().toString())) {
			int fd = mManager.openDev(mDevice.getText().toString());
			showLog(getString(fd < 0 ? R.string.open_device_fail
					: R.string.open_device_succed));
		} else {
			showLog(getString(R.string.device_null));
		}

	}

	private void onCloseBtnClick() {
		if (!TextUtils.isEmpty(mDevice.getText().toString())) {
			mManager.closeDev(mDevice.getText().toString());
		} else {
			showLog(getString(R.string.device_null));
		}
	}

	private void onReadBtnClick() {
		if (!TextUtils.isEmpty(mReadLeth.getText().toString())) {
			int lenth = Integer.parseInt(mReadLeth.getText().toString());
			byte[] buffer = new byte[lenth];
			mManager.readDev(buffer, buffer.length);
			showLog("read : " + BaseUtil.bytesToHexString(buffer));
		} else {
			showLog(getString(R.string.check_read_cmd));
		}
	}

	private void onWriteBtnClick() {
		mResult.setText("");
		String[] sarray = mWritCmd.getText().toString().split(",");
		int[] data = new int[sarray.length];
		boolean canParse = true;
		for (int i = 0; i < sarray.length; i++) {
			try {
				data[i] = Integer.parseInt(sarray[i]);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				canParse = false;
				e.printStackTrace();
				Log.e("wmg", "data " + i + " ," + sarray[i]);
			}
		}
		if (sarray.length > 0 && canParse) {
			byte[] buffer = BaseUtil.getByteArray(data);
			int lenth = mManager.writeDev(buffer, buffer.length);
			mResult.setTextColor(lenth >= 0 ? Color.GREEN : Color.RED);
			mResult.setText(lenth >= 0 ? "OK" : "NG");
			Log.v("wmg", "return len = " + lenth);
			showLog("buffer lenth = " + buffer.length + ", return lenth = "
					+ lenth);
		} else {
			showLog(getString(R.string.check_write_cmd));
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
