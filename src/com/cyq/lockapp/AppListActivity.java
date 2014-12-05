package com.cyq.lockapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.cyq.application.MyApplication;
import com.cyq.bean.AppInfo;
import com.cyq.dao.AppInfoDao;
import com.cyq.mi.R;

public class AppListActivity extends SherlockActivity {
	ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
	private ListView appListView;
	private GetGoldListAdapter adapter;
	private AppInfoDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list);
		initDao();
		appListView = (ListView) findViewById(R.id.app_list);
		adapter = new GetGoldListAdapter(AppListActivity.this, appList);
		appListView.setAdapter(adapter);
		
		List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				appList.add(tmpInfo);// 如果非系统应用，则添加至appList
			}
		}
		
		adapter.notifyDataSetChanged();
	}
	
	
	
	
	private class GetGoldListAdapter extends ArrayAdapter<AppInfo>{
		public GetGoldListAdapter(Context context, List<AppInfo> objects) {
			super(context, 0, objects);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(AppListActivity.this).inflate(R.layout.app_list_item, null);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.lock = (ImageView) convertView.findViewById(R.id.lock);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.icon.setImageDrawable(appList.get(position).getAppIcon());
			holder.name.setText(appList.get(position).getAppName());
			try {
				AppInfo info = dao.queryForSameId(appList.get(position));
				if(null != info && info.getIsLock().equals("true")){
					holder.lock.setImageResource(R.drawable.lock);
				}else{
					holder.lock.setImageResource(R.drawable.unlock);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}



			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						AppInfo infoTemp =appList.get(position);
						if(null != infoTemp && infoTemp.getIsLock().equals("true")){
							infoTemp.setIsLock("false");
							appList.get(position).setIsLock("false");
						}else{
							appList.get(position).setIsLock("true");
							infoTemp.setIsLock("true");
						}
						dao.createOrUpdate(infoTemp);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					adapter.notifyDataSetChanged();
					((MyApplication)getApplication()).updateTime = System.currentTimeMillis();
					((MyApplication)getApplication()).needUpdate = true;
					Log.e("click---->", position+"");
				}
			});
			return convertView;
		}
		
		class ViewHolder {
			ImageView icon;
			ImageView lock;
			TextView name;
		}
		@Override
		public int getCount() {
			return appList.size();
		}
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
	
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
