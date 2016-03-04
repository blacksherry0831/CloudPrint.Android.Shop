package com.example.cloudprintshop;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import android.content.Context;


public class Connect2Server 
{
	public static final String UserID="zhangxiaochen";
	public static final String LicenseCode="123123";
	public static String GetReportUrl(Context ctx)
	{
		String Url=ctx.getResources().getString(R.string.Server_host_url)+ctx.getResources().getString(R.string.Server_url_get_task);
        return Url;
	}
	public static String GetTaskFromServer(String UserID,String LicenseCode)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("userID=");sb.append(UserID);
		sb.append("&licenseCode=");sb.append(LicenseCode);
		
		return sb.toString() ;
	}
	public static String ReportLocationData(
			String UserID,
			String LicenseCode,
			double La,
			double lo,
			Context ctx)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("userID=");sb.append(UserID);
		sb.append("&licenseCode=");sb.append(LicenseCode);
		sb.append("&Mobile=");sb.append(android.os.Build.MODEL);
		sb.append("&Latitude=");sb.append(La);
		sb.append("&Longitude=");sb.append(lo);
		sb.append("&MobileUUID=");sb.append(LibCui.GetDeviceID(ctx));
		
		
		return sb.toString() ;
	}
	public static  String PostString2Server(String str_url,String RequestData)
	 {
		 
			 String str=str_url;
			
	        try {
	            URL url=new URL(str);
	            
	            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	            /*----------------------读取文件上传到服务器--------------------*/
				            BufferedOutputStream  out=new BufferedOutputStream(connection.getOutputStream());
				            byte [] bytes_t=RequestData.getBytes("UTF8");
			                out.write(bytes_t, 0, bytes_t.length);
			                out.flush();          
	            /*---------------------读取文件上传到服务器----------------------------*/
	            //读取URLConnection的响应
	            int http_response_code=connection.getResponseCode();
	                   String FileID="";
							if(http_response_code == 200){
								 byte [] bytes=new byte[1024];
						            DataInputStream in=new DataInputStream(connection.getInputStream());
						            Arrays.fill(bytes, (byte) 0x00);
						            
						            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
						            int len_read=0;
						            while(true){
						            	len_read=in.read(bytes,0,bytes.length);
						            	if(len_read==-1) break;
						            	outStream.write(bytes, 0, len_read);
						            	
						            	
						            }
						            
						            
						            
						            FileID=outStream.toString("UTF-8");
						            if(FileID==null) FileID="";
						            FileID.replace("\000", "");						            	 
						           			          
						           if(FileID==null||FileID.equals("")){
						        	   return null;
						           }else{
						        	   return FileID;
						           }
						          
						  }
	        } catch (Exception e) {
	            
	        }finally{
	        	
	        }
	        return null;
		}
}
