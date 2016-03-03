package com.zzh.app.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zzh.app.recevier.MyDeviceAdminReceiver;
import com.zzh.onkeylocksreen.R;

public class MainActivity extends Activity {

	private DevicePolicyManager mDPM;

	private ComponentName mDeviceAdminSample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(this,
				MyDeviceAdminReceiver.class);
		
		lockScreen();
	}

	public void lockScreen() {
		if(isActiveAdmin()){
			//如果已经被激活，直接锁屏
			mDPM.lockNow();
			finish();
		}else{
			openAdmin();
		}
	}

	public void openAdmin() {
		if(isActiveAdmin()){
			Toast.makeText(MainActivity.this, "您已激活软件管理器，请直接点击锁屏按钮", 0).show();
		}else{
			// Launch the activity to have the user enable our admin.
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					mDeviceAdminSample);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "请激活管理者权限");
			startActivityForResult(intent, 0);
		}
	}
	
	
	/**
	 * 判断管理者是否被激活
	 * 
	 * @return
	 */
	private boolean isActiveAdmin() {
		return mDPM.isAdminActive(mDeviceAdminSample);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		finish();
		super.startActivityForResult(intent, requestCode);
	}
}
