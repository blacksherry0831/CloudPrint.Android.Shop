package com.example.cloudprintshop;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import com.example.cloudprintshop.TaskDoneAdapter.SearchThread;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskContactsAdapter  extends TaskAdapterCuiBase 
{

	  final File ROOTFILE=LibCui.GetCloudPrintContacts();
      private static final int LAYOUT=R.layout.item_listview_contacts;
	    TaskContactsAdapter ()
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
	        		  String a=((PrinterOrderItem) pdata_t).GetContacts();
	        	     ((TextView) v).setText(a);	
	        	}
	       
	        }
	        if(pdata_t instanceof PrinterOrderItem){
      		 SetPhoneCall(convertView,((PrinterOrderItem) pdata_t).Telphone);
      		 SetDeleteCall(convertView,pdata_t);
      		 SetMsgCall(convertView,pdata_t);
      	    }
	       
			
			return convertView;
		}	
		public void SearchFileOnDisk()
		{
			  new Thread(new SearchThread()).start();	
		}
		
	   
		public class SearchThread implements Runnable
		{
			private boolean SearchFileOnDisk()
			{
				
				boolean Updata=false;
				File[] files = ROOTFILE.listFiles();
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

	   public  void SetDeleteCall(View v,final Object o)
	    {
	    	if(v==null) return;
	    	View v_button=v.findViewById(R.id.button_delete);
	    	if(v_button!=null){
	    		v_button.setOnLongClickListener(new View.OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						DeleteContacts(o,v.getContext());
						return false;
					}
			
					
					
				});
	    	}
	    }
	   public  void DeleteContacts(Object o,Context ctx)
	   {
		   DeleteObject(o,ctx,ROOTFILE);	  
		   
	   }
		   
		   
}
