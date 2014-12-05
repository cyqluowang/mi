package com.cyq.gesture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cyq.application.MyApplication;
import com.cyq.mi.R;

public class GuidePasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = new Intent();
		if(MyApplication.getInstance().getLockPatternUtils().savedPatternExists()){
			intent.setClass(GuidePasswordActivity.this,UnlockPasswordActivity.class);
			startActivity(intent);
			finish();
		}
		
		setContentView(R.layout.gesturepassword_guide);
		findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				intent.setClass(GuidePasswordActivity.this,CreatePasswordActivity.class);
				// 打开新的Activity
				startActivity(intent);
				finish();
			}
		});
	}

}
