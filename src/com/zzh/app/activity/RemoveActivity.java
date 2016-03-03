package com.zzh.app.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.zzh.app.recevier.MyDeviceAdminReceiver;

public class RemoveActivity extends Activity {

	private DevicePolicyManager mDPM;

	private ComponentName mDeviceAdminSample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(this,
				MyDeviceAdminReceiver.class);
		
		removeOneKey();
	}

	public void removeOneKey() {
		mDPM.removeActiveAdmin(mDeviceAdminSample);// 取消激活设备管理员
		/**
		 * 接下来就是卸载程序的代码了： <intent-filter> <action
		 * android:name="android.intent.action.VIEW" /> <action
		 * android:name="android.intent.action.DELETE" /> <category
		 * android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="package" /> </intent-filter>
		 */
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivityForResult(intent, 0);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		finish();
		super.startActivityForResult(intent, requestCode);
	}
}
