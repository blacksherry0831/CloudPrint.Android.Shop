package com.example.cloudprintshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class TaskAdapterCuiBase extends  BaseAdapter{
	private Handler mHandler=new Handler();
	protected  AdapterDataOBject mData=new AdapterDataOBject();
	
    TaskAdapterCuiBase()
    {
      
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
       Object pdata_t=mData.get(position);
       
       if(convertView==null)
       {
    	   convertView=LibCui.getViewFromeXml(R.layout.item_listview_task,parent.getContext());
	   }
     
        View v=convertView.findViewById(R.id.textview_list_item_text);
        if(v instanceof TextView)
        {
        	if(pdata_t instanceof PrinterOrderItem){
        		  String a=((PrinterOrderItem) pdata_t).GetDes();
        	     ((TextView) v).setText(a);	
        	}
       
        }
		
       
		
		return convertView;
	}	

	public static String ReadFile(File file)
	{
		
		StringBuffer content=new StringBuffer();
		try {
            InputStream instream = new FileInputStream(file);
            if (instream != null)
            {
            	if(instream.available()<1024*24){
            		InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content.append(line);
                    }                
                    instream.close();
            	}
                
            }
            return content.toString();
        }
        catch (java.io.FileNotFoundException e)
        {
           return null;
        }
        catch (IOException e)
        {
           return null;
        }
	}
    public void NotifynotifyDataSetChanged(){
    	mHandler.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				notifyDataSetChanged();
			}
			
		});
    }

    public static void SetPhoneCall(View v,final String num)
    {
    	if(v==null) return;
    	View v_button=v.findViewById(R.id.button_call);
    	if(v_button!=null){
    		v_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 CallPhone(num,v.getContext());
				}
			});
    	}
    }
    public static void CallMsg(Object o,Context ctx)
    {
    	 String phoneno=((PrinterOrderItem)o).Telphone;
    	 String hello=((PrinterOrderItem)o).GetHello();
    	 Toast.makeText(ctx,"正在打开短信", Toast.LENGTH_SHORT).show();
    	
    	 Uri smsToUri = Uri.parse("smsto:"+phoneno);  
    	  
    	 Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
    	   
    	 intent.putExtra("sms_body",hello);  
    	   
    	 ctx.startActivity(intent);  
    }
    public static void SetMsgCall(View v,final Object o)
    {
    	if(v==null) return;
    	View v_button=v.findViewById(R.id.button_msg);
    	
    	if(v_button!=null){
    		v_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CallMsg(o,v.getContext());
				}
			});
    	}
    }
    public static void CallPhone(String phoneno,Context ctx)
    {
    	Toast.makeText(ctx,"正在打开电话", Toast.LENGTH_SHORT).show();
    	Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
    	ctx.startActivity(intent);
    }
    public static void DeleteFile(File root,String FileName)
	  {
		  try{
			  File d=new File(root,FileName);
			  if(d.exists()){
				  d.delete();
			  }
		  }catch(Exception e){
			  
		  }
		 
	  }
    public  void DeleteObject(Object o,Context ctx,File Root)
	   {
		   if(this.mData.Remove(o)){
			   if(o instanceof PrinterOrderItem){
				   DeleteFile(Root,((PrinterOrderItem)o).getFileName());
			   }
			  
			    this.notifyDataSetChanged();
			   // this.SearchFileOnDisk();
		   }
		   
		   
		  
		   
	   }
}
