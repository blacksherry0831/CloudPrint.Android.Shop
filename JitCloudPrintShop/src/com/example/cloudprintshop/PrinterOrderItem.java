package com.example.cloudprintshop;

import org.json.JSONException;
import org.json.JSONObject;



public class PrinterOrderItem extends PrinterOrderItemBase
{
	
/**
 * 
 */
	 public boolean equals(Object o) 
	 {
		 if (o instanceof PrinterOrderItem) {
			 PrinterOrderItem s = (PrinterOrderItem) o;
			 
			 return s.GetKey().equals(this.GetKey());
		 }else{
			 
			 return false;
		 }
		 
	    //return super.equals(o);  
       }
/**
 * 
 */
	 public String GetDes()
	 {
		 StringBuffer sb=new  StringBuffer();
		 /*-
		 sb.append("用户ID：");sb.append(this.UserID); sb.append("电话:"); sb.append(this.Telphone);
		 sb.append("\r\n");
	
		 sb.append("地点：");sb.append(this.Place);
		 sb.append("\r\n");
		
		 sb.append("价格: ");sb.append(this.PriceID); sb.append("X");sb.append(this.Copies+"份数X");sb.append(this.fanwei); 
		 sb.append("\r\n");
		 
		 sb.append("文件：");sb.append(this.FileName);
		 sb.append("\r\n");
		 sb.append("状态：");sb.append(this.Status);
		 sb.append("\r\n");
		 sb.append("接受时间");sb.append(this.ReceiveTime);
	---*/
		
		 sb.append(this.UserID);         sb.append("\r\n");
		 sb.append(this.Telphone); 		 sb.append("\r\n");	
		 sb.append(this.Place);	         sb.append("\r\n");
	    
		 sb.append(this.PriceID); 
	     sb.append("X");
	     sb.append(this.Copies+"份数X");
	     sb.append(this.fanwei); 
		 sb.append("\r\n");
		 
		 sb.append(this.FileName);  sb.append("\r\n");
		 sb.append(this.ReceiveTime);sb.append(" ");  sb.append(this.Status);
		 return sb.toString();
	 }
	 /**
	  * 
	  */
	 	 public String GetContacts()
	 	 {
	 		 StringBuffer sb=new  StringBuffer();
	 		 /*----------------------------------------*/
	 		// sb.append("用户：");
	 		 sb.append(this.UserID);
	 		 sb.append("\r\n");
	 		// sb.append("电话:");
	 		 sb.append(this.Telphone);
	 		 sb.append("\r\n");
	 		// sb.append("地点：");
	 		 sb.append(this.Place);
	 		 sb.append("\r\n");
	 		 /*----------------------------------------*/
	 		 
	 		 return sb.toString();
	 	 }
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
/**
 * 
 */	 
	 public String getFileName()
	 {
		 StringBuffer sb=new StringBuffer();
		 sb.append(this.UserID);
		 sb.append(this.Telphone);
		 sb.append(".txt");
		 return sb.toString();
		 
	 }
/**
 * 
 */
	 public String getTaskContent()
	 {
		 return this._Json_o.toString();
	 }
/**
 * 
 */
	 public String getContactsContent()
	 {
		 JSONObject jo=new JSONObject();
		 
		 try {
			jo.put("UserID", this.UserID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 try {
			jo.put("Telphone",this.Telphone);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 try {
			jo.put("Place",this.Place);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 
		 return jo.toString();
	 }
/**
 * 
 */
	 public String GetHello()
	 {
		 StringBuffer sb=new StringBuffer();
		 sb.append("【");
		 sb.append("亲打印");
		 sb.append(">>");
		 sb.append(this.UserID);
		 sb.append(",您好！");
		 sb.append("】");		 
		 return sb.toString();
	 }
 /**
  * 
  */
}
