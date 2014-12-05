package com.cyq.bean;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.cyq.dao.AppInfoDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "APP_INFO", daoClass = AppInfoDao.class)
public class AppInfo implements Parcelable, Serializable{

	private static final long serialVersionUID = 1L;
	@DatabaseField
	public String appName;
	@DatabaseField(id = true)
	public String packageName;
	@DatabaseField
	public String versionName;
	@DatabaseField
	public int versionCode;
	@DatabaseField
	public String isLock = "";	
	
	public Drawable appIcon;


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(appName);
		dest.writeString(packageName);
		dest.writeString(versionName);
		dest.writeInt(versionCode);
		dest.writeString(isLock);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	 /**
     * @return the creator
     */
    public static Parcelable.Creator<AppInfo> getCreator() {
        return CREATOR;
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Creator<AppInfo>() {

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }

        @Override
        public AppInfo createFromParcel(Parcel source) {
        	AppInfo data = new AppInfo();
            data.appName = source.readString();
            data.packageName = source.readString();
            data.versionName = source.readString();
            data.isLock = source.readString();
            data.versionCode = source.readInt();
            return data;
        }
    };
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}



	
}
