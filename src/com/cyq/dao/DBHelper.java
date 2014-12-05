package com.cyq.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cyq.bean.AppInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    /**
     * 构造函数
     * 
     * @param context
     */
    public DBHelper(Context context) {
        super(context, "cyq", null, 1);
    }

    /**
     * 初始化操作：建表
     * 
     * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase,
     *      com.j256.ormlite.support.ConnectionSource)
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createTables(db, connectionSource);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		if (newVersion <= oldVersion) {
			return;
		}

		try {
			TableUtils.dropTable(connectionSource,AppInfo.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
			db.delete("APP_INFO", null, null);
		}
		createTables(db, connectionSource); 

    }
    
    private void createTables(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, AppInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
