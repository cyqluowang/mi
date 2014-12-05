package com.cyq.lockapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.cyq.application.MyApplication;
import com.cyq.bean.AppInfo;
import com.cyq.dao.AppInfoDao;
import com.cyq.gesture.UnlockPasswordActivity;

public class WatchAppService extends Service {
	private ActivityManager activityManager;
	private Thread thread;
	private Intent intentLockAppActivity;
	private Boolean isNeedLock = true;
	private AppInfoDao dao;
	List<String> needLockPackName = new ArrayList<String>();
	@Override
	public IBinder onBind(Intent arg0) {
		Log.e("info", "onBind");
		return null;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("info", "service已经启动");
		intentLockAppActivity = new Intent(this,UnlockPasswordActivity.class);
		activityManager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);
		initDao();
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if(((MyApplication)getApplication()).needUpdate && System.currentTimeMillis() > ((MyApplication)getApplication()).updateTime){
						try {
							needLockPackName.clear();
							for (AppInfo info : dao.queryForAll()) {
								if(info.getIsLock().equals("true")){
								  needLockPackName.add(info.getPackageName());
								}
							}
							((MyApplication)getApplication()).needUpdate = false;
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}


					String runningpackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
//					Log.e("naem----->", runningpackageName);
					if(runningpackageName.equals("com.android.systemui") || runningpackageName.equals("com.sec.android.app.launcher")){
						isNeedLock = true;
					}
					
					
					if(isNeedLock && needLockPackName.contains(runningpackageName)){
						isNeedLock = false;
						intentLockAppActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intentLockAppActivity.putExtra("from", "service");
						startActivity(intentLockAppActivity);
					}
					
					
					SystemClock.sleep(2000);
				}
			
			}
		});
		thread.start();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("desx----->", "onDestroy");
	}
	private void initDao(){

		if(null ==  dao){
			try {
				dao = new AppInfoDao(((MyApplication)getApplication()).mDatabaseHelper);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
