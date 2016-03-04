package com.example.cloudprintshop;

import org.json.JSONException;
import org.json.JSONObject;

public class PrinterOrderItemBase 
{
	JSONObject _Json_o;
	String UserID;
	String BussinessID=null;
	String FileName;
	String FileSavePath=null;
	String FileID;
	String PriceID;
	String Copies;
	String fanwei;
	String Place;
	String Telphone;
	String ReceiveTime;
	String Status;
	String uploadtime;
	String beizhu;
	PrinterOrderItemBase()
	{
		
	}
	
	public boolean Parse(JSONObject jo) 
	{
		this._Json_o=jo;
		beizhu=GetData(jo,"Beizhu");
		Copies=GetData(jo,"Copies");
		fanwei=GetData(jo,"Fanwei");
		
		FileName=GetData(jo,"Filename");	
		BussinessID=GetData(jo,"BussinessID");
		Place=GetData(jo,"Place");
		PriceID=GetData(jo,"PriceID");
		ReceiveTime=GetData(jo,"Receivetime");
		Status=GetData(jo,"Status");
		Telphone=GetData(jo,"Telphone");
		uploadtime=GetData(jo,"Uploadtime");
		UserID=GetData(jo,"UserID");
		FileID=GetData(jo,"Fileid");
		
		
		if(isNullOrEmpty(UserID)){
		    return false;
		}else{
			return true;
		}
		
	}
	public boolean Parse_str(String txt) 
	{
		if(txt==null){
			return false;
		}
		try {
			JSONObject Json_o=new JSONObject(txt);
			return Parse(Json_o);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean isNullOrEmpty(String input) {
	    return input == null || input.length() ==0;
	}
	public String GetData(JSONObject jo,String key){
		try {
			return jo.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
	     	return null;
		}
	}
	public String GetKey()
	{
		return  this._Json_o.toString();
	}
	
	@Override
	public String toString(){
		return GetKey();
	}
}
