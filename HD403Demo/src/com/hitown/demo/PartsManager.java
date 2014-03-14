package com.hitown.demo;

import java.io.IOException;

import android.os.Handler;
import android.util.Log;

public class PartsManager {
	private static PartsManager mInstance;

	private PartsManager() {
	}

	public static PartsManager getInstance() {
		if (mInstance == null) {
			mInstance = new PartsManager();
		}
		return mInstance;
	}

	static {
		System.loadLibrary("parts");
	}

	private static Handler mHandler;
	private static final String TAG = "PartsManager";
	public static final int MSG_JNI_POST = 1000;

	public void addHandler(Handler handler) {
		mHandler = handler;
	}

	public native int openDev(String devName);

	public native int closeDev(String devName);

	public native int readDev(byte[] buffer, int size) ;

	public native int writeDev(byte[] buffer, int size) ;

	public native int ioctrlDev(int cmd, int num);

	private static void postMessage(String msg) {
		Log.d(TAG, "post msg = " + msg + " handler = " + mHandler);
		mHandler.sendMessage(mHandler.obtainMessage(MSG_JNI_POST, msg));
	}
}
