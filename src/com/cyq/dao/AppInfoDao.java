package com.cyq.dao;

import java.sql.SQLException;

import com.cyq.bean.AppInfo;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
public class AppInfoDao extends BaseDaoImpl<AppInfo, String> {

	protected AppInfoDao(Class<AppInfo> dataClass) throws SQLException {
		super(dataClass);
	}
	
	public AppInfoDao(DBHelper helper) throws SQLException {
		super(helper.getConnectionSource(), AppInfo.class);
	}
	
    public AppInfoDao(ConnectionSource connectionSource, Class<AppInfo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
    

	

	
}
