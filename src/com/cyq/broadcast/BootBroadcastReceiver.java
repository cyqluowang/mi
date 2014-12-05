package com.cyq.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.cyq.lockapp.WatchAppService;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		  Intent service = new Intent(context,WatchAppService.class);  
          context.startService(service);  
          Log.e("TAG", "开机自动服务自动启动.....");  
          
          Toast   toast = Toast.makeText(context,
        		     "自定义位置Toast", Toast.LENGTH_LONG);
        		   toast.setGravity(Gravity.CENTER, 0, 0);
        		   toast.show();
         //启动应用，参数为需要自动启动的应用的包名
//		  Intent intent = getPackageManager().getLaunchIntentForPackage("");
//		  context.startActivity(intent );        
	}

}
