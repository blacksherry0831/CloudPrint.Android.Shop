package com.example.cloudprintshop;


import java.util.ArrayList;
import java.util.UUID;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;

import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import android.widget.Toast;

public class CloudPrintViewTemplate  implements OnGlobalLayoutListener
{
	protected	View     mView;
	protected Context  mContext;
	protected Resources 		  res;
	protected PhoneInHand mPhoneHand;
	private  int mLayoutIdx;
	int mRootWidth;
	int mRootHeight;
	int mRootWidthOld;
	int mRootHeightOld;
	 final int SCAN_SPAN_NETWORK=1000*60*5;//5分钟
	 public  boolean SmallMemUsage=true; 
	 private  Handler mHandler = new Handler();
	 private  NetService mMsgService;
	 BaiduLocation mBaiduLocation;
	// private   LocationClient mLocClient;
	 protected BDLocation mLocation=null;
	 protected volatile boolean mThreadRun=true;
	 /*
	public CloudPrintViewTemplate (Context context)
	  {
			mRootWidth=0;
			mRootHeight=0;
			mRootWidthOld=0;
			mRootHeightOld=0;
			
		  this.mContext=context;
		  this.res=context.getResources();
		  this.InitView();
		  this.SetPhoneInHand(null);
	  }*/
	public CloudPrintViewTemplate (Context context,int mLayoutId)
	  {
			mRootWidth=0;
			mRootHeight=0;
			mRootWidthOld=0;
			mRootHeightOld=0;
			this.mLayoutIdx=mLayoutId;
		  this.mContext=context;
		  this.res=context.getResources();
		  this.InitView();
		  this.SetPhoneInHand(null);
		  this.StratNetSetvices();
		  this.StartThread();
	  }
	private void initBackgroundbyMem()
	{
		
		
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		int memClass=activityManager.getMemoryClass();
		//LibCui.SaveString2Fille("Mem.txt",String.valueOf(memClass));
		if(memClass>200){
			//手机非常豪华
			SmallMemUsage=false;
		}
		
		this.initBackgroundbyHand(this.mPhoneHand);
		
	}
	/*
	public CloudPrintViewTemplate (Context context,int mLayoutId,PhoneInHand   PhoneInHand)
	  {
			mRootWidth=0;
			mRootHeight=0;
			mRootWidthOld=0;
			mRootHeightOld=0;
			this.mLayoutIdx=mLayoutId;
		  this.mContext=context;
		  this.res=context.getResources();
		  this.InitView();
		  this.SetPhoneInHand(PhoneInHand);
	  }*/
	private void StratNetSetvices()
	  {
		  Intent bindIntent = new Intent(this.mContext, NetService.class);  
		     
		  this.mContext.bindService(bindIntent,new WhenServiceConnection(),Context.BIND_AUTO_CREATE); 
	  }
	private void StartThread()
	{		
		new Thread(new NewTaskUpdate()).start();
	}
	public PrinterOrderDataSet GetPrintTasks()
	{
		return mMsgService.GetPrintTaskSet();
	}
	public class WhenServiceConnection implements ServiceConnection
	{
		@Override
		public void onServiceDisconnected(ComponentName name) 
		{
			Toast.makeText(getContext(),"onServiceDisconnected,资源不足", Toast.LENGTH_LONG).show();
		}
		
	@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//返回一个MsgService对象
			mMsgService = ((NetService.MsgBinder)service).getService();
			//this.mNetPhonePc=this.mMsgService.mNetPhonePC;
			//this.mNetPrints=this.mMsgService.mNetPrints;
			
		}
	}

	private void InitView()
	{
		 this.InitMainView();
		 this.SetBackground();
		 this.mView.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}
	public void onGlobalLayout()
	{     
	      mRootWidth=mView.getMeasuredWidth();
	      mRootHeight=mView.getMeasuredHeight(); 
	      //Log.i("CDH", "Global W:"+mRootWidthOld+"  H:"+mRootHeightOld);	
			//first in this Place	
			if((mRootWidthOld!=mRootWidth)||(mRootHeightOld!=mRootHeight)){
				 
				// Layout Change
				 // this.mLeftRightDetector.mPhoneHand=PhoneInHand.PhoneLeftHand;
				  this.initBackgroundbyHand(mPhoneHand);
				  this.LayoutChanged();
				 // this.HandChange(this.mLeftRightDetector.mPhoneHand);
				  
			}	
			mRootWidthOld=mRootWidth;
			mRootHeightOld=mRootHeight;
		 
		}
	protected void LayoutChanged(){
		
	}
	private void InitMainView()
    {

		
		this.mView=LibCui.getViewFromeXml(mLayoutIdx,mContext);
	  
	}
	
	private void initBackgroundbyHand(PhoneInHand  hand)
	{
		 int BGColor=0xff00CED1;
		 if(mRootWidth+mRootHeight==0)		 return;
		 try{
			  if(hand==PhoneInHand.PhoneLeftHand){
				  if(SmallMemUsage){
					  mView.setBackgroundColor(BGColor);
				  }else{
					  LibCui.SetBackground2View(mView, R.drawable.bg_left_minor, mContext);	
				  }				 
				}else if(hand==PhoneInHand.PhoneRightHand){
					 if(SmallMemUsage){
						 mView.setBackgroundColor(BGColor);
					  }else{
						  LibCui.SetBackground2View(mView, R.drawable.bg_right_minor, mContext);
					  }					
				}else{
					 mView.setBackgroundColor(BGColor);
				}
		  }catch(Exception nfe){
			  Log.v("1",nfe.getMessage());
			
		  }
	}
	public void InitLocationClient()
	{
			this.mBaiduLocation=new BaiduLocation(getContext(),new MyLocationListenner());
	}
	private void SetBackground()
	{
		 this.mView.setBackgroundColor(Color.RED);
	}
	private void SetPhoneInHand(PhoneInHand  hand)
	{
		initBackgroundbyMem();
		if(hand!=null){
			 this.mPhoneHand=hand;
		}else{
			Bundle extras = ((Activity)mContext).getIntent().getExtras();

			if (extras != null) {
				String handstr=extras.getString(GetHandKey());
				if(handstr!=null){
					if(handstr.equals(PhoneInHand.PhoneRightHand.toString())){
						 this.mPhoneHand=PhoneInHand.PhoneRightHand;
					}else if(handstr.equals(PhoneInHand.PhoneLeftHand.toString())){
						 this.mPhoneHand=PhoneInHand.PhoneLeftHand;
					}else{
						
					}
								
				}

			}else{
				 this.mPhoneHand=PhoneInHand.PhoneRightHand;
			}
		}
		
		 this.initBackgroundbyHand(hand);
	}

	public static String GetHandKey(){
		final String key="lrType";
		return key;
	}
	
	public View GetView()
	{
	   
		return mView;
	}
	public void showPopupWindow( View parent)
	{
		  LibCui.showPopupWindow(this.mContext,parent,this.mView);
	}
	
	    protected void onDestroy() 
	    {  
	    	this.mThreadRun=false;
	    	if(this.mBaiduLocation!=null){
	    		this.mBaiduLocation.Destory();
	    	}
	    }  
	      
	    protected void onResume() 
	    {  
	       
	    }  
 
	    protected void onPause() {  
	       
	 
	    }  
	public void StartActivityView( Class<?> cls){
		 
		  Bundle bundle=new Bundle();
	       this.StartActivityView(cls, bundle);
	       
	}
	public void StartActivityView( Class<?> cls,Bundle bundle){
		   Intent intent = new Intent();
	      
	       bundle.putString("lrType",mPhoneHand.toString());
	      
	       
	       intent.setClass(this.mContext,cls);
	       intent.putExtras(bundle);
	       //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	       this.mContext.startActivity(intent);    
	}
	public final View findViewById(int id)
	{
		View v=this.mView.findViewById(id);
		return v;
	}
	protected final Context getContext() {
		// TODO Auto-generated method stub
		return this.mContext;
	}
	@SuppressLint("NewApi") public final boolean IsDestory()
	{
		Activity activity=((Activity)mContext);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
			try{
				if(activity==null||activity.isDestroyed()){
					return true;
				}
			}catch(Exception e){
				
			}
			
			
		}
		//
		if(activity==null||activity.isFinishing()){
			return true;
		}
		//
		
		return false;
	}
	/**
	 * 
	 * 
	 * 
	 */
	protected final void  postDelayed(Runnable runnable, int delayMillis) {
		// TODO Auto-generated method stub
		if(this.IsDestory()){
			
			 return;
		}else{
			if(this.mThreadRun){
			      this.mHandler.postDelayed(runnable, delayMillis);
			}
		}
		
	}
	/**
	 * 
	 * 
	 * 
	 */
	protected final void postDelay(Runnable runnable) {
		// TODO Auto-generated method stub
		postDelayed(runnable,0);
	}
	public final Resources getResources()
	{
		return mContext.getResources();
	}
	/**
	 * 
	 * 
	 * 
	 */
	public final void finish() 
	{
		
		((Activity)(mContext)).finish();
		
	}
	/**
	 * 
	 * 
	 * 
	 */
	protected final  void Toast_make_show(final String msg,final int dur,final int gravity)
	{
		postDelay(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast t=Toast.makeText(mContext,msg,dur);
				t.getView().setBackgroundColor(0xc8ffffff);
				t.setGravity(gravity,0,0);
				t.show();
			}});
		
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * */
	public void RequestLocationClient()
	{
		if(this.mBaiduLocation!=null){
			this.mBaiduLocation.RequestLocationClient();
		}
	}
	/**
	 * 
	 */
	protected void  WhenOnReceiveLocation(BDLocation location)
	{
		this.mLocation=location;
		/*if(location!=null&&this.mPrintPointTemp!=null){
			this.mLocation=location;
			this.mPrintPointTemp.SeBDtLocation(location);
		}*/
	}
	/**
	 * 
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mLocation=location;
			WhenOnReceiveLocation(location);
		}
	}
	/*
	protected void WhenVersionChange(
			ArrayList<PrinterOrderItem> Undo,
			ArrayList<PrinterOrderItem> Done) {
		// TODO Auto-generated method stub
		   
	}*/
	/**
	 * 
	 */
	/*
	public class NewTaskListenner implements PrinterOrderDataSet.NewTaskRcv {

		@Override
		public void WhenVersionChange(
				ArrayList<PrinterOrderItem> Undo,
				ArrayList<PrinterOrderItem> Done) {
				// TODO Auto-generated method stub
				WhenVersionChange(Undo,Done);
		}

		
	}*/
/**
 * 从此处获取打印订单
 * 
 */
	public void OrderTaskUpdata(PrinterOrderDataSet pds)
	{
		
	}
/**
 *
 *
 *
 **/
	public class NewTaskUpdate implements Runnable
	{
         String VerID=UUID.randomUUID().toString();
	
         public String GetNewVer()
         {
        	 if(mMsgService!=null){
        		 if(mMsgService.GetPrintTaskSet()!=null){
        			return mMsgService.GetPrintTaskSet().GetVer();
        		 }
        	 }
        	 return UUID.randomUUID().toString();
         }
         public PrinterOrderDataSet GetTaskr()
         {
        	 if(mMsgService!=null){
        		 if(mMsgService.GetPrintTaskSet()!=null){
        			return mMsgService.GetPrintTaskSet();
        		 }
        	 }
        	 return null;
         }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(mThreadRun){
				/*--------------------------------------------------*/
				try {
						Thread.sleep(300);
						/*--------------------------------------------------*/
						if(VerID.equals(GetNewVer())){
							//版本相同
						}else{
							//版本不同
								postDelay(new Runnable(){
		
									@Override
									public void run() {
										// TODO Auto-generated method stub
										
											OrderTaskUpdata(GetTaskr());							
									}});
							//更新版本
							VerID=GetNewVer();
						}
							
						
						
				       /*--------------------------------------------------*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}

		
	}
	/**
	 *
	 *
	 *
	 **/
}


