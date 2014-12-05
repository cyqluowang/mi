package com.cyq.lockapp;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.cyq.mi.R;

public class LockAppActivity extends SherlockActivity {
	private Button button;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_app);
		button = (Button) findViewById(R.id.button1);
		password = (EditText) findViewById(R.id.password);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if("666666".equals(password.getText().toString().trim())){
					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(LockAppActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
					finish();
				}
			}
		});
	}
	
	//不让用户按后退键  
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			//屏蔽后退键  
	        if(KeyEvent.KEYCODE_BACK == event.getKeyCode())  
	        {  
	            return true;//阻止事件继续向下分发  
	        }  
	        return super.onKeyDown(keyCode, event); 
		}


}
