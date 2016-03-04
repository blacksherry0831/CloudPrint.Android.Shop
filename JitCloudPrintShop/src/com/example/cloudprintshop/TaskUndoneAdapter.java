package com.example.cloudprintshop;

import java.util.ArrayList;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskUndoneAdapter  extends TaskAdapterCuiBase
{
  
	 private static final int LAYOUT=R.layout.item_listview_task;
	 
	public void SetData(final ArrayList<Object> Selected) {				
		
		
		if(Selected!=null){
			mData.clear();
			mData.addAll(Selected);
			this.NotifynotifyDataSetChanged();
		}			
		
		
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
    		 
       }
		
		return convertView;
	}	
}
