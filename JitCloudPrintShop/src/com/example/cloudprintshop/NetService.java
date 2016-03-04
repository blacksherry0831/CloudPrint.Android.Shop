package com.example.cloudprintshop;
import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.cloudprintshop.PrinterOrderDataSet.NewTaskRcv_OneFile;



import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;

public class NetService extends Service {
	 private MediaPlayer mMediaPlayer;
	 PrinterOrderDataSet mPrinterTask;
	 protected BaiduLocation mBaiduLocation=null;
	 protected BDLocation mLocation=null;
	 public NetService() {
		
			
			
	}
	@Override
    public void onCreate() {
        super.onCreate();
        /*-------------------------------*/
       
        mPrinterTask=new PrinterOrderDataSet(this.getApplicationContext());
        mPrinterTask.SetTaskRcvNotify(new WhenTaskRcv(this));
        mBaiduLocation=new BaiduLocation(this,new MyLocationListenner());
        /*-------------------------------*/
    
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
    	

        super.onDestroy();
        
        if( mMediaPlayer!=null){
        	mMediaPlayer.release();
        	//mMediaPlayer=null;
        }
        if(mPrinterTask!=null){
        	mPrinterTask.OnDestory();
        	//mPrinterTask=null;
        }
        if(mBaiduLocation!=null){
        	this.mBaiduLocation.Destory();
        	
        }
        
    }
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return new MsgBinder();
	}
	public void NotifyAndroid()
	{
		/*
		//消息通知栏
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		//定义通知栏展现的内容信息
	        int icon = R.drawable.file_rcv_notify;
	        CharSequence tickerText = "云打印收到文件";
	        long when = System.currentTimeMillis();
	        Notification notification = new Notification(icon, tickerText, when);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        //定义下拉通知栏时要展现的内容信息
	        Context context = getApplicationContext();
	        CharSequence contentTitle = "云打印收到文件";
	        CharSequence contentText = "";
	        Intent notificationIntent = new Intent(this,File_SelectActivity.class);
	        
	        Bundle bundle = new Bundle();
	        bundle.putString("TITLE", this.getString(R.string.dm_file_my_phone));
	        bundle.putString("TYPE","FORDER");
	        notificationIntent.putExtras(bundle);
	        
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	                notificationIntent, 0);
	        notification.setLatestEventInfo(context, contentTitle, contentText,
	                contentIntent);
	         
	        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
	        mNotificationManager.notify(1, notification);
	        */
	} 
	public void OpenPhoneU()
	{
		    /*Intent notificationIntent = new Intent(this.getBaseContext(),File_FileSelect_U_Activity.class);
		    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        getApplication().startActivity(notificationIntent);*/
	}
	public PrinterOrderDataSet  GetPrintTaskSet()
	{		
		if(mPrinterTask==null){
			return null;
		}else{
			return mPrinterTask;
		}
		
	}
	/**
	 * 
	 * 
	 * @author Administrator
	 *
	 */
	String getTopActivity(Context context)
	{
	  ActivityManager manager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE) ;

	  List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(3);
	
		if(runningTaskInfos != null&&runningTaskInfos.size()!=0){
			   ComponentName cn = runningTaskInfos.get(0).topActivity;
			   return cn.getShortClassName();
	
		}else{
	
		     return null ;
		}
	}
	/**
	 * 
	 * 
	 * @author Administrator
	 *
	 */
	public class MsgBinder extends Binder{
		/**
		 * 获取当前Service的实例
		 * @return
		 */
		public NetService getService()
		{
			return NetService.this;
		}
		public void startDownload(){
			 
		}
	}
	
	public class WhenTaskRcv implements NewTaskRcv_OneFile
	{
		Context _ctx;
		WhenTaskRcv(Context ctx){
			_ctx=ctx;
			// mMediaPlayer=MediaPlayer.create(ctx,R.raw.file_rcv_same_pc);
		}
		/**
		 * 
		 * 当接受到新任务，播放音乐
		 * 
		 **/
		@Override
		public void WhenOneFileRcv(ArrayList<PrinterOrderItem> Undo) {
			// TODO Auto-generated method stub
			 Time time = new Time("GMT+8");       
		     time.setToNow();      
		     int hour = time.hour;
		    // if(hour>9&&hour<23)
		     {
		    	 try{
		    		 if( mMediaPlayer==null)
			    		   mMediaPlayer=MediaPlayer.create(_ctx,R.raw.file_rcv_same_pc);
			    	 
			    	    if (mMediaPlayer.isPlaying()) {
							
						}else{
							mMediaPlayer.start();
						}
		    	 }catch(Exception e){
		    		 
		    	 }
		    	  
		     }
			
		}
		
	}
	
	public class MyLocationListenner implements BDLocationListener {
		protected void  WhenOnReceiveLocation(BDLocation location)
		{
			 Time time = new Time("GMT+8");       
		     time.setToNow();      
			
		     ReportLocation2Server rl2s=new ReportLocation2Server(getApplicationContext());
			 rl2s.Start(location.getLatitude(),location.getLongitude());
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location!=null&&location.getLatitude()!=0&&location.getLongitude()!=0){
				mLocation=location;
				WhenOnReceiveLocation(location);
			}
		
		}
	}
}

