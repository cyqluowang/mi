package com.cyq.application;

import android.app.Application;

import com.cyq.dao.DBHelper;
import com.cyq.view.LockPatternUtils;


public class MyApplication extends Application{
	public DBHelper mDatabaseHelper;
	public long updateTime=0l;
	public Boolean needUpdate=true;
	
	
	private static MyApplication mInstance;
	private LockPatternUtils mLockPatternUtils;

	public static MyApplication getInstance() {
		return mInstance;
	}



	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}
	
	
	@Override
	public void onCreate() {
		getDBHelper();
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
	}
	public DBHelper getDBHelper() {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = new DBHelper(MyApplication.this);
		}
		return mDatabaseHelper;
	}

	
	
	
}
