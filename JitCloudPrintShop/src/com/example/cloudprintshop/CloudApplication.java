package com.example.cloudprintshop;

import com.baidu.mapapi.SDKInitializer;


import android.app.Application;

public class CloudApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		  CrashHandler crashHandler = CrashHandler.getInstance();  
	       // 注册crashHandler  
	       crashHandler.init(getApplicationContext());  
	       // 发送以前没发送的报告(可选)  
	       crashHandler.sendPreviousReportsToServer();  
	}

}
