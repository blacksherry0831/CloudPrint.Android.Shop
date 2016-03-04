package com.example.cloudprintshop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;





import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class LibCui {
    /* 以最省内存的方式读取本地资源的图片
     * @param context
   * @param resId
   * @return
   */  
 public static Bitmap readBitMap(Context context, int resId){  
     BitmapFactory.Options opt = new BitmapFactory.Options();  
     opt.inPreferredConfig = Bitmap.Config.RGB_565;   
     opt.inPurgeable = true;  
     opt.inInputShareable = true;  
     //获取资源图片  
       InputStream is = context.getResources().openRawResource(resId);  
     return BitmapFactory.decodeStream(is,null,opt);  
   }
 public static void SetViewBackGround(View v,Context context, int resId){  
	 	Bitmap bmp=readBitMap(context,resId);
	 	BitmapDrawable bmpdaw=new BitmapDrawable(context.getResources(),bmp);
	 	v.setBackground(bmpdaw);
   }
public static View getViewFromeXml(int mLayoutId,Context mContext)
    {
	   View mView=null;
	   
	   
			try{
				  LayoutInflater mInflater = LayoutInflater.from(mContext);	  
				  mView= mInflater.inflate(mLayoutId,null, false);
			}catch(InflateException ife){
				Log.v("1",ife.getMessage());
			}
			if(mView==null){
				mView=new View(mContext);
				mView.setBackgroundColor(0xff66ccff);
			}
			return mView;
	  
	}
public static  void showPopupWindow(
		 Context context,
		 View parent,
		 View vPopupWindow)
{	       
	      Rect t=new Rect();
	      parent.getGlobalVisibleRect(t);
	      vPopupWindow.setFocusable(true); // 这个很重要
	      vPopupWindow.setFocusableInTouchMode(true);		    
        final PopupWindow pw= new PopupWindow(vPopupWindow,t.width(),t.height(),true);  
        pw.setFocusable(true);
        pw.setOutsideTouchable(false);
        pw.setBackgroundDrawable(new BitmapDrawable());
         vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
		            pw.dismiss();
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
		            return true;
		        }
		        return false;
			}
		});
        //显示popupWindow对话框  
	        // pw.showAtLocation(parent, gravity, x, y)
	    pw.showAtLocation(parent, Gravity.LEFT|Gravity.TOP,t.left,t.top);  
}  

public static int getStatusHeight(Activity activity){
    int statusHeight = 0;
    Rect localRect = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
    statusHeight = localRect.top;
    if (0 == statusHeight){
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
            statusHeight = activity.getResources().getDimensionPixelSize(i5);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    return statusHeight;
}
//宽
public static int getViewWidth(LinearLayout view){
    view.measure(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    return view.getMeasuredWidth();
}
//高
public static int getViewHeight(LinearLayout view){
    view.measure(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    return view.getMeasuredHeight();
}

public static String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index == -1) return "";
		return fileName.substring(index + 1, fileName.length()).toLowerCase(Locale.getDefault());
	}
public static void SetBackground2View(View v,int bgmId,Context context)
{
	 Bitmap bmp=readBitMap(context,bgmId);
	 SetBackground2View(v,bmp,context.getResources());
}
public static void SetBackground2View(View v,Bitmap bmp,Resources res)
{
	int vWidth=v.getWidth();
	int vHeight=v.getHeight();
	int bmpWidth=bmp.getWidth();
	int bmpHeight=bmp.getHeight();
	double bmpwhScale=1.0*bmpHeight/bmpWidth;
	Bitmap bmpNew;
	int []bmpWidth_new=new int[2];
	int []bmpHeight_new=new int[2];
	int []bmpArea_new=new int[2];
	    bmpWidth_new[0]=vWidth;
	    bmpHeight_new[0]=(int)(vWidth*bmpwhScale);
	    
	    bmpHeight_new[1]=vHeight;
		bmpWidth_new[1]=(int)(vHeight/bmpwhScale);
		for(int i=0;i<2;i++){
			bmpArea_new[i]=bmpWidth_new[i]*bmpHeight_new[i]-vWidth*vHeight;
		}
		
		int newwhIdx;
		if(bmpArea_new[1]>bmpArea_new[0]){
			newwhIdx=1;
		}else{
			newwhIdx=0;
		}
		bmpNew=LibCui.ScaleBitmap(bmp, bmpWidth_new[newwhIdx], bmpHeight_new[newwhIdx]);
		
	    Bitmap	bmpCanvas=Bitmap.createBitmap(v.getWidth(),v.getHeight(),bmp.getConfig());
		
	   
	    Canvas cv=new Canvas(bmpCanvas);
	    
	    cv.drawBitmap(bmpNew,0,0,null);
		
	  
		BitmapDrawable background =new BitmapDrawable(res,bmpCanvas);	    
	    v.setBackground(background);    
}
public static Bitmap  ScaleBitmap(Bitmap bmp,int width_new,int height_new)
{		 
	 Matrix matrix = new Matrix();
	 matrix.postScale((float)(1.0*width_new/bmp.getWidth()),(float)(1.0*height_new/bmp.getHeight()));
	 Bitmap newbmp = Bitmap.createBitmap(bmp, 0, 0,bmp.getWidth(),bmp.getHeight(), matrix, true);
	 int w=newbmp.getWidth();
	 int h=newbmp.getHeight();
	 return newbmp;
}

public void GetSDcard(Context context)
{
	//顺带描述怎么取得sdcard的空间大小，
	File sdcardDir = Environment.getExternalStorageDirectory();
	StatFs sf = new StatFs(sdcardDir.getPath()); //sdcardDir.getPath())值为/mnt/sdcard，想取外置sd卡大小的话，直接代入/mnt/sdcard2
	long blockSize = sf.getBlockSize(); //总大小
	long blockCount = sf.getBlockCount();
	long availCount = sf.getAvailableBlocks(); //有效大小
}
public static File[]  GetSDcards(){
	   File currentDir=Environment.getExternalStorageDirectory();
	   File parent=currentDir.getParentFile();
	   return  parent.listFiles();
}
public static long GetFileAvailable(File file)
{

	StatFs sf = new StatFs(file.getPath()); //sdcardDir.getPath())值为/mnt/sdcard，想取外置sd卡大小的话，直接代入/mnt/sdcard2
	long blockSize = sf.getBlockSize(); //总大小
	long blockCount = sf.getBlockCount();
	long availCount = sf.getAvailableBlocks(); //有效大小
	return availCount;
}
public static File GetTopDirectory()
{
String FirstFolder="JitClouldPrint";
  String ALBUM_PATH;
  File [] sdcards=LibCui.GetSDcards();

  long a_min=0;
  int   Idx=0;
  for (int i=0;i<sdcards.length;i++){  
      if(sdcards[i].isDirectory()){  
         Long a=LibCui.GetFileAvailable(sdcards[i]); 
          if(a>a_min){
        	  a_min=a;
        	  Idx=i;
          }
      }
  }  

    	 ALBUM_PATH= sdcards[Idx].getAbsolutePath()+File.separator+FirstFolder+File.separator;

         File dirFirstFile=new File(ALBUM_PATH);//新建一级主目录

         if(!dirFirstFile.exists()){//判断文件夹目录是否存在

              dirFirstFile.mkdirs();//如果不存在则创建

         }
        return dirFirstFile;
     }
public static String GetTimeStr()
{
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日");       
	Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
	String str=formatter.format(curDate);  	
	return str;
}
public static File GetCloudPrintTaskDone()
{
	File path=new File( GetCloudPrintCfgFile() + "/TaskDone");
	if(path.exists()){
		
	}else{
		path.mkdirs();
	}
	return path;
}
public static File GetCloudPrintContacts()
{
	File path=new File( GetCloudPrintCfgFile() + "/Contacts");
	if(path.exists()){
		
	}else{
		path.mkdirs();
	}
	return path;
}
public static File GetCloudPrintCfgFile()
{
	File path=new File(Environment.getExternalStorageDirectory() + "/Documents");
	if(path.exists()){
		
	}else{
		path.mkdirs();
	}
	return path;
}
public static String getExtensionName(File file)
{ 
	String filename=file.getName();
    if ((filename != null) && (filename.length() > 0)) { 
        int dot = filename.lastIndexOf('.'); 
        if ((dot >-1) && (dot < (filename.length() - 1))) { 
            return filename.substring(dot + 1); 
        } 
    } 
    return filename; 
} 
public static void SaveString2Fille(String fileName,String str,File path)
{
	if(str==null||fileName==null)
	{
		return;
	}
	 File file=new File(path,fileName);
	try {  
	          FileOutputStream fos = new FileOutputStream(file);  
	          String info =str;  
	          fos.write(info.getBytes()); 
	          fos.flush();
	          fos.close();  
	} catch (Exception e) {  
	    e.printStackTrace();  
	}  
}
/*
public static String getExtensionName(File file)
{ 
	String filename=file.getName();
    if ((filename != null) && (filename.length() > 0)) { 
        int dot = filename.lastIndexOf('.'); 
        if ((dot >-1) && (dot < (filename.length() - 1))) { 
            return filename.substring(dot + 1); 
        } 
    } 
    return filename; 
} */
public static String GetDeviceID(Context ctx)
{
	final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

    final String tmDevice, tmSerial, tmPhone, androidId;
    tmDevice = "" + tm.getDeviceId();
    tmSerial = "" + tm.getSimSerialNumber();
    androidId = "" + android.provider.Settings.Secure.getString(ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
    return deviceUuid.toString();    
}
public static boolean isWifiEnabled(Context mContext)
{
    Context myContext = mContext;
    if (myContext == null) {
        throw new NullPointerException("Global context is null");
    }
    WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
    if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
        ConnectivityManager connManager = (ConnectivityManager) myContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
    } else {
        return false;
    }
}
/**
 *获取Wifi名称 
 */
public static String getWifiName(Context mContext)
{
	   String wifi_info=null;
	   WifiManager wifiManager = (WifiManager) (mContext).getSystemService(Context.WIFI_SERVICE);
	   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	 
	    if(isWifiEnabled(mContext)){
	    	wifi_info=wifiInfo.getSSID();
	    }else{
	    	wifi_info="WiFi 未连接";
	    }
	   
	   return wifi_info;
}
}