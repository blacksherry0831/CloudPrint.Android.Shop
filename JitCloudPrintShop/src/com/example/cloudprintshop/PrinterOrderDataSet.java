package com.example.cloudprintshop;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.text.format.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
{
    "Fileid":"201510211858078240",
    "Status":"已提交",
    "Place":"金陵科技学院15365078745",
    "Copies":"1",
    "Beizhu":"ss",
    "UserID":"blacksherry",
    "Uploadtime":"2015/10/21 18:58:28",
    "Telphone":"15365078745",
    "PriceID":"1",
    "Receivetime":"18:57:30-19:57:30",
    "Filename":"我是张晓晨不要删除这个条目.txt",
    "Fanwei":"所有页面"
}*/

public class PrinterOrderDataSet 
{
	public interface NewTaskRcv_OneFile{
	  void	WhenOneFileRcv(ArrayList<PrinterOrderItem> Undo);
	}
	Context mContext;

	//String Mobile=android.os.Build.MODEL;
	String Url;
	//String RequestData;
	private volatile boolean mThreadRun=true;
	String ListId=UUID.randomUUID().toString();
	//private final ArrayList<PrinterOrderItem> mPrintSelfTemp = (new  ArrayList<PrinterOrderItem>());
	private final ArrayList<PrinterOrderItem> mPrintSelfUnProcess=  (new  ArrayList<PrinterOrderItem>());
	private final ArrayList<PrinterOrderItem> mPrintSelfProcessed =(new  ArrayList<PrinterOrderItem>());
	public  ArrayList<String> mErrorStrArray=new ArrayList<String>();
	public final Time LastUpdataTime= new Time();
	private  NewTaskRcv_OneFile  mNewTaskRcv=null;
	//public File FILE_TASK_DONE=LibCui.GetCloudPrintTaskDone();
	//public File FILE_COTACTS=LibCui.GetCloudPrintContacts();
	public PrinterOrderDataSet (Context ctx){
		this.mContext=ctx;
		
		if(ctx!=null){
			this.Url=Connect2Server.GetReportUrl(ctx);
			new Thread(new RefashPrinterServer()).start();
		}
	
		
	}
	public  String GetRequestData()
	{
		return Connect2Server.GetTaskFromServer(Connect2Server.UserID, Connect2Server.LicenseCode);
	}
	public String GetVer()
	{
		if(this.ListId==null){
			return UUID.randomUUID().toString();
		}else{
			return this.ListId;
		}
	
	}
	public  ArrayList<PrinterOrderItem> GetFileUnDo()
	{
			return this.mPrintSelfUnProcess;
	}
	
	public ArrayList<PrinterOrderItem> GetFileDone()
	{
		 return this.mPrintSelfProcessed;
	}
	public void UpdataListVersion()
	{
		ListId=UUID.randomUUID().toString();
		
	} 
	
	public void SetTaskRcvNotify( NewTaskRcv_OneFile NI)
	{
	
			 mNewTaskRcv=NI;		 
	
	} 
	
	 public void OnDestory()
		{
			mThreadRun=false;
		}
	 public class RefashPrinterServer implements Runnable
	 {
		 public void Post2RecvOrders()
			{
				String json_text=Connect2Server.PostString2Server(Url,GetRequestData());
					try {
						
						if(json_text!=null){
							 ParseJson2Task(json_text);
							 LastUpdataTime.setToNow();
						}						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			public void ParseJson2Task(String json_text) throws JSONException
			{
				 ArrayList<PrinterOrderItem> List_New = new ArrayList<PrinterOrderItem>();
				 JSONArray ja = new  JSONArray(json_text);
				 for (int i = 0; i < ja.length(); i++) {
					 JSONObject jo = (JSONObject) ja.get(i);
					 PrinterOrderItem pot=new PrinterOrderItem();
					 if(pot.Parse(jo)){
						 List_New.add(pot);
					 }			
				 }
				 /*交集*/
				 List<PrinterOrderItem> UNION;
					synchronized(mPrintSelfUnProcess){
						 UNION=(List<PrinterOrderItem>) mPrintSelfUnProcess.clone();
						 UNION.retainAll(List_New);
					}
				 /*--*/				 
					 List<PrinterOrderItem> List_Remove;
					synchronized(mPrintSelfUnProcess){
						List_Remove=(List<PrinterOrderItem>)mPrintSelfUnProcess.clone();
						 /*--从Undo移除--*/
						 List_Remove.removeAll(UNION);
						
					}				
				
				 /*--新增加-到Undo的内容LIST*/
				 List_New.removeAll(UNION);
				
				 /*-----完成的导入--UnProcess-----------*/
				 
				 if(List_New.size()>0||List_Remove.size()>0){
					 /*------增加--------------*/
					 Add2UndoList(List_New);//添加到Undo
					 /*------移除--------------*/
					//从Undo移除
					 Remove2UndoList(List_Remove);
					 /*------增加--------------*/
					 Add2DoneList(List_Remove);
				    /*-------------------------*/
					 UpdataListVersion();	
					/*-------------------------*/
				 }
				
			}
			public void Add2UndoList(List<PrinterOrderItem> pois)
			{
				for(PrinterOrderItem poi :pois){
					Add2UndoList(poi);
				}
			}
			public void Add2DoneList(List<PrinterOrderItem> pois){
				for(PrinterOrderItem poi :pois){
					Add2DoneList(poi);
				}
			}
			public void Add2UndoList(PrinterOrderItem poi)
			{
				synchronized(mPrintSelfUnProcess){
					if(mPrintSelfUnProcess.contains(poi)==false){
						  if(mPrintSelfUnProcess.add(poi)){
							  if( mNewTaskRcv!=null){
									 mNewTaskRcv.WhenOneFileRcv(mPrintSelfUnProcess);			 
								}
						  }  
						  
					}
				 }
				
			}
			public void Add2DoneList(PrinterOrderItem poi){
				synchronized(mPrintSelfProcessed){
							if(mPrintSelfProcessed.contains(poi)==false){
								mPrintSelfProcessed.add(poi);  
							}			        
				    }
				OperLocalFile.SaveTask2Disk(poi);
				OperLocalFile.SaveContacts2Disk(poi);
			}
			public void Remove2UndoList(List<PrinterOrderItem> pois) 
			{
				// TODO Auto-generated method stub
				for(PrinterOrderItem poi :pois){
					Remove2UndoList(poi);
				}
			}
			public void Remove2UndoList(PrinterOrderItem pois) 
			{
				// TODO Auto-generated method stub
				synchronized(mPrintSelfUnProcess){
					
						  mPrintSelfUnProcess.remove(pois);

				 }
			}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int SleeepTime=1000*5;
			while(mThreadRun)
			{
				Post2RecvOrders();
				try {
					Thread.sleep(SleeepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		 
	 }

 /**
  * 
  */
	 
}
