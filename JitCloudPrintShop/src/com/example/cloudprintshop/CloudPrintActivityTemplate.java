package com.example.cloudprintshop;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * SVN
 * */
public class CloudPrintActivityTemplate extends Activity {

	 CloudPrintViewTemplate mViewTemplate;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	  this.AfterOnCreateBeforViewSet();
		
	}
	/**
	 * 
	 * 
	 */
	protected void AfterOnCreateBeforViewSet()
	{
		
	}
	/**
	 * 
	 * 
	 */
  @Override  
  protected void onDestroy() {  
	  super.onDestroy(); 
	  if(this.mViewTemplate!=null){
		  this.mViewTemplate.onDestroy();
		  this.mViewTemplate=null;
	  }
      //this.unbindService(mConnection);  
     
  } 
  @Override  
  protected void onPause() {  
      super.onPause();  
      if(this.mViewTemplate!=null){
		  this.mViewTemplate.onPause();
		 
	  }
       
      }
  @Override  
  protected void onResume() { 
	  super.onResume();
	  if(this.mViewTemplate!=null){
		  this.mViewTemplate.onResume();
	  }
  }

 
  
  @Override
  public  void onWindowFocusChanged(boolean hasFocus)
  {
	  super.onWindowFocusChanged(hasFocus); 
	  if(hasFocus==true){
		  if(this.mViewTemplate!=null){
			  //this.mViewTemplate.WindowsLoadFinish();
		  }
	  }
  }
  /**
   * 
   * 
   */
   protected void WhenOnReceiveLocation(BDLocation location) 
   {
		
		
		
	}  		
  /**
   * 定位SDK监听函数
   */
  public class MyLocationListenner implements BDLocationListener {

  			@Override
  			public void onReceiveLocation(BDLocation location) {
  			
  				WhenOnReceiveLocation(location);
  				
  			}  			
  		}
  /**
   * 
   * 
   */
  @Override  
  protected void onActivityResult(int requestCode, int resultCode, Intent data) 
  {
	  //  this.mViewTemplate.ActivityResult(requestCode, resultCode, data);
  }  
  /**
   * CloudPrintActivityTemplate
   * 
   */
}
