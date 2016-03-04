package com.example.cloudprintshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskDoneAdapter  extends   TaskAdapterCuiBase
{  
	  final File ROOTFILE=LibCui.GetCloudPrintTaskDone();
	  private static final int LAYOUT=R.layout.item_listview_done;
    TaskDoneAdapter()
    {
        this.SearchFileOnDisk();
    }	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
       Object pdata_t=mData.get(position);
       
       if(convertView==null)
       {
    	   convertView=LibCui.getViewFromeXml(LAYOUT,parent.getContext());
	   }
     
        View v=convertView.findViewById(R.id.textview_list_item_text);
        if(v instanceof TextView)
        {
        	if(pdata_t instanceof PrinterOrderItem){
        		  String a=((PrinterOrderItem) pdata_t).GetDes();
        	     ((TextView) v).setText(a);	
        	}
       
        }
        if(pdata_t instanceof PrinterOrderItem){
     		 SetPhoneCall(convertView,((PrinterOrderItem) pdata_t).Telphone);
     		 SetMsgCall(convertView,pdata_t);
     		 SetDeleteCall(convertView,pdata_t);
        }
	       
       
		
		return convertView;
	}	
	public  void SetDeleteCall(View v,final Object O)
    {
    	if(v==null) return;
    	View v_button=v.findViewById(R.id.button_delete);
    	if(v_button!=null){
    		v_button.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					DeleteTask(O,v.getContext());
					return false;
				}
			
			});
    	}
    }
	   public  void DeleteTask(Object o,Context ctx)
	   {
		   
		   DeleteObject(o,ctx,ROOTFILE);
		   
	   }
	public void SearchFileOnDisk()
	{
		  new Thread(new SearchThread()).start();	
	}
	
   
	public class SearchThread implements Runnable
	{
		private boolean SearchFileOnDisk()
		{
			File root=ROOTFILE;
			boolean Updata=false;
			File[] files = root.listFiles();
		    for(File file:files){ 
		    	
				          if(file.isFile()){
				        	  if( "txt".equals(LibCui.getExtensionName(file).toLowerCase(Locale.getDefault()))){
				        		            		  	        			   
				        			   PrinterOrderItem poi=new PrinterOrderItem();
				        			    if(poi.Parse_str(ReadFile(file))){
				        				  //有效文件
				        			    	Updata=mData.add(poi);
				        			    	
				        			    } 
				        		   }
				        		   
				        	  }          
		      
		    }
		     
		    return Updata;
		    
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(SearchFileOnDisk()){
				NotifynotifyDataSetChanged();
			}
			
		}
		
	}
}
