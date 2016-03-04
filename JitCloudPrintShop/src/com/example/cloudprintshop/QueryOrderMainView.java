package com.example.cloudprintshop;

import java.util.ArrayList;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QueryOrderMainView extends CloudPrintViewTemplate{

	private final static int mLayoutId=R.layout.activity_query_order_main;
	Spinner  mSpinner;/**<*/
	ListView mListView;
	final TaskUndoneAdapter mTaskUndoneAdapter=new TaskUndoneAdapter();
	final TaskDoneAdapter mTaskDoneAdapter=new TaskDoneAdapter();
	final TaskContactsAdapter mTaskContactsAdapter=new TaskContactsAdapter();
	public  QueryOrderMainView(Context context) 
	{
		super(context, mLayoutId);
		// TODO Auto-generated constructor stub
		this.initChildControls();
	}
	public void initChildControls()
	{
		this.initSpinner();
		this.initListView();
	}
	public void initListView()
	{
		this.mListView=(ListView) findViewById(R.id.ListView_task);
		if(this.mListView==null) return;
		this.mListView.setAdapter( mTaskUndoneAdapter);
	}
	
	public void initSpinner(){
	 this.mSpinner=(Spinner) this.findViewById(R.id.spinner_view_slelcte);
	 if(this.mSpinner!=null){

	        //数据
			final ArrayList<String> data_list = new ArrayList<String>();
			data_list.add("待打印");
	        
			data_list.add("已完成");
            
			data_list.add("联系人");
	     
	        ArrayAdapter<String>  arr_adapter;
	        //适配器
	        arr_adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
	        //设置样式
	        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        //加载适配器
	        this.mSpinner.setAdapter(arr_adapter);
	        this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					 if(position==0){
						Toast.makeText(parent.getContext(), ".......待打印",Toast.LENGTH_SHORT).show();
						mListView.setAdapter(mTaskUndoneAdapter);
					 }
					 if(position==1){
						 Toast.makeText(parent.getContext(), "......已完成",Toast.LENGTH_SHORT).show();
						 mListView.setAdapter(mTaskDoneAdapter);
					 }
					 if(position==2){
						 Toast.makeText(parent.getContext(), "......联系人",Toast.LENGTH_SHORT).show();
						 mListView.setAdapter(mTaskContactsAdapter);
					 }
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}});
	        }
	}
	@Override
	public void OrderTaskUpdata(PrinterOrderDataSet pds)
	{
		ArrayList<PrinterOrderItem> poi=pds.GetFileUnDo();
		ArrayList<Object> o=new ArrayList<Object>();
		
		for(PrinterOrderItem p: poi){
			o.add(p);
		}
		this.mTaskUndoneAdapter.SetData(o);
		this.mTaskDoneAdapter.SearchFileOnDisk();
		this.mTaskContactsAdapter.SearchFileOnDisk();
	}
	
}

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
/*
{   
    "UserID":"blacksherry",  "Telphone":"15365078745",
       
    "Place":"金陵科技学院15365078745", 
    "Beizhu":"ss",
   
   
    
    "PriceID":"1",
    "Fanwei":"所有页面"
    "Copies":"1",
    
    "Filename":"我是张晓晨不要删除这个条目.txt",
    "Status":"已提交",
    "Receivetime":"18:57:30-19:57:30",
}*/