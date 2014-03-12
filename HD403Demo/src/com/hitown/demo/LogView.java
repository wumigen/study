package com.hitown.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class LogView extends EditText {
	private SpannableStringBuilder mSpannableStringBuilder;
	private SimpleDateFormat mDateFormat;
	private int mLineCount;
	private int mMaxLines = 100;
	private boolean mDebug;

	public LogView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSpannableStringBuilder = new SpannableStringBuilder();
		setText(mSpannableStringBuilder);
		setKeyListener(null);
		mDateFormat = new SimpleDateFormat("HH:mm:ss");
	}

	public void setDebug(boolean value) {
		mDebug = value;
	}

	public void debug(String line, int color) {
		if (mDebug) {
			print(line, color);
		}
	}

	public void error(String line) {
		print(line, Color.RED);
	}

	public void print(String line) {
		print(line, Color.GREEN);
	}

	public void print(String line, int color) {
		if (line == null) {
			return;
		}
		int start = mSpannableStringBuilder.length();
		if (mLineCount % mMaxLines == 0) {
			mSpannableStringBuilder.delete(0, mSpannableStringBuilder.length());
			mLineCount = 0;
			start = 0;
		}
		line = String.format("\n%03d:[%s] %s", mLineCount,
				mDateFormat.format(new Date()), line);
		int end = start + line.length();
		mSpannableStringBuilder.append(line);
		mSpannableStringBuilder.setSpan(new ForegroundColorSpan(color), start,
				end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		setText(mSpannableStringBuilder);
		setSelection(end);
		mLineCount++;
	}

	public void setMaxLines(int value) {
		mMaxLines = value;
	}

	public void clear() {
		mSpannableStringBuilder.delete(0, mSpannableStringBuilder.length());
		setText(mSpannableStringBuilder);
		mLineCount = 0;
	}
}
