package com.example.cloudprintshop;





import android.os.Bundle;

import android.view.KeyEvent;




/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class f_CloudPrintShopActivity extends CloudPrintActivityTemplate {
	//public MapView mMapView = null;  
	//public BaiduMap mBaiduMap;
	//boolean isFirstLoc = true;// 是否首次定位
	
	//public MyLocationListenner myListener;
	//public LocationClient mLocClient;
	//private LocationMode mCurrentMode;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        f_CloudPrintShopView view;
       
        
        setContentView((this.mViewTemplate=view=new f_CloudPrintShopView(this)).GetView());
        /*
        view.GetView().findViewById(R.id.id_widgets_exit_app).findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				f_CloudPrintShopActivity.this.finish();
			}
		});*/
        
       // this.mMapView=this.view.GetBaiduMap();
       // this.mBaiduMap=this.mMapView.getMap();
       // this.InitLocation();
    }
    /*
    public void InitLocation()
    {
    	 // 开启定位图层
    	mCurrentMode = LocationMode.NORMAL;
 		mBaiduMap.setMyLocationEnabled(true);
 		myListener = new MyLocationListenner();
 	// 定位初始化
 			mLocClient = new LocationClient(this.getApplicationContext());
 			mLocClient.registerLocationListener(myListener);
 			LocationClientOption option = new LocationClientOption();
 			option.setOpenGps(true);// 打开gps
 			option.setCoorType("bd09ll"); // 设置坐标类型
 			option.setScanSpan(LocationClientOption.MIN_SCAN_SPAN_NETWORK);
 			option.setIsNeedAddress(true);
 			mLocClient.setLocOption(option);
 			mLocClient.start();
 			
 			
    }*/
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        
      //  this.view.onDestroy();
        	//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        	
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
       // this.view.onResume();
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
     //  this.view.onPause();
 
        }  
    /**
	 * 定位SDK监听函数
	 */
    
/*	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(location.getDirection())
					.latitude(location.getLatitude())
					.longitude(location.getLongitude())
					.build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				mBaiduMap.setMyLocationData(locData);
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
		
	}
	*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
           ((f_CloudPrintShopView) this.mViewTemplate).YouConntCLoseApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
