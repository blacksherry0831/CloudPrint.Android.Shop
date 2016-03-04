package com.example.cloudprintshop;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.widget.Toast;

import com.baidu.location.BDLocation;


public class CloudPrintAddress extends CloudPrintAddressBase  implements Serializable
{
	
	public interface Toast_msg 
	{
		void show(CharSequence text, int duration);
	}
	Toast_msg    mToastMsg=null;
	public String mErrorStr=null;
	private static final long serialVersionUID = -758459502806858414L;
	/**
	 * 图片ID，真实项目中可能是图片路径
	 */
	private int imgId;
	
	/**
	 * 距离
	 */
	private String distance;
	/**
	 * 赞数量
	 */
	//private int zan;
	public String mSearchTime;
	
	public CloudPrintAddress()
	{
		
	}
	
	public CloudPrintAddress(
			double latitude, 
			double longitude,
			int imgId,
			String name,
			String distance,
			String addr,
			int zan){
		super();
		this.mPrintFrom=PrintFrom.cloud;
		this.mPrintType=PrintType.CloudPrint;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.setPrinterPoint(name);
		this.distance = distance;
		//this.zan = zan;
		this.mAddrCharacter=addr;
		
	}
	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}
	public void setPrintType(String printtype)
	{
		if(printtype.equals(PrintType.NetPrint.toString())){			
			this.mPrintType = PrintType.NetPrint;
			
		}else if(printtype.equals(PrintType.CloudPrint.toString())){			
			this.mPrintType = PrintType.CloudPrint;
			
		}else if(printtype.equals(PrintType.PcPrint.toString())){
			this.mPrintType = PrintType.PcPrint;
			
		}else{
			
		}		
	}
	
	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public int getZan()
	{
		return 0;
	}

	public void setZan(int zan)
	{
		//this.zan = zan;
	}
	public String GetKey()
	{
		String key=this.mAddress.toString()+this.GetPrinterPointName()+this.mPrintType;
		
		return key;
	}
	public String GetDefatutPrinterInfo()
		{
			 String info="机器名 ：SHARP\r\n" +
			 		"IP地址 192.168.0.20\r\n" +
			 		"打印机类型：网络打印机\r\n" +
			 		"搜索时间：2015、5、14 11:01\r\n" +
			 		"状态：状态未注册/可用";
			 return info;
		}
	public String GetPrinterDes()
	{
		if(this.mSearchTime==null){
			this.mSearchTime=LibCui.GetTimeStr();
		}
		String PrintTypeStr="";
		if(mPrintType==PrintType.NetPrint){
			PrintTypeStr="网络打印机";
		}else	if(mPrintType==PrintType.PcPrint){
			PrintTypeStr="PC机 本地打印机";
		}else if(mPrintType==PrintType.CloudPrint){
			PrintTypeStr="云端文印店";
		}else{
			PrintTypeStr="Suck_fuck";
		}
		String PrintParent="";
		
		ServerInfo siParent=this.GetLocalParent();
		
		
		 String info="打印机名 :"+this.GetPrinterPointName()+
				 "\r\n" +"打印机归属:"+PrintParent+
				 "\r\n" +"IP地址:"+this.mIPAddrStr+
				 "\r\n" +"打印机类型:"+PrintTypeStr+
				 "\r\n"+"搜索时间:"+this.mSearchTime+
		 		 "\r\n"+"状态：";
		 
		 return info;
	}
	/**
	 * 纬度
	 */
	    
		
		public String getName()
		{
			return this.GetPrinterPointName();
		}
		public String getAddr()
		{
			if(this.mPrintType==PrintType.CloudPrint){
				return this.mAddrCharacter;
			}else if(this.mPrintType==PrintType.NetPrint){
				return  this.mIPAddrStr;
			}else if(this.mPrintType==PrintType.PcPrint){
				return this.mIPAddrStr;
			}else{
				return this.mAddrCharacter;
			}
		
		}
      
		public int getImgId()
		{
			return imgId;
		}
	 public boolean SendFile2NetPrint(String filefullName) 
	 {   

		 if(mPrintType==PrintType.NetPrint){
			 //必须是网络打印机--发送PCL 到打印机IP地址
			 FileInputStream fin=null;
			 DataOutputStream dout=null;
			 Socket s=null;
			 byte [] buf= new byte[1024]; 
			  try{ 
				     s=new Socket(this.mAddress,9100);
			         fin = new FileInputStream(filefullName); 
			         dout =new DataOutputStream(s.getOutputStream());
			         int totalNum=fin.available();
			         int sendNum=0;
			         int read = 0;
			  			// 将文件输入流 循环 读入 Socket的输出流中
			  			while ((read = fin.read(buf, 0, buf.length)) != -1) {
			  				long TimeSendStart=System.nanoTime();
			            	sendNum+=read;
							  				dout.write(buf, 0, read);
							  				dout.flush();
							 long TimeMs=(System.nanoTime()-TimeSendStart)/1000;
			  				 double SendSpeed=(1.0*read/1024)/(1.0*TimeMs/1000);//KBpS
			                
			  			}
			  			dout.flush();
			            dout.close();
						fin.close();
						s.close();
						mErrorStr="成功发送文件到打印机"+"/n"+this.mIPAddrStr;
					    this.Toast_MakeText_show(mErrorStr, Toast.LENGTH_LONG);
				        return true;
			        } 
			        catch(Exception e){ 
			        	 e.printStackTrace(); 
			        	return false;
			        
			        }finally{
			        	    try {
								if(dout!=null)  dout.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	 
							try {
								if(fin!=null) fin.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							try {
								if(s!=null) s.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        }
		 }else{
			 return false;
		 }
		   
	      
	            
	   }
 /**
  * 显示提示信息
  * 
  * 
  * @param msg
  * @param dur
  */
    private void Toast_MakeText_show(String msg,int dur) {
		// TODO Auto-generated method stub
    	
    	if(this.mToastMsg!=null){
    		mToastMsg.show(msg,dur);
    	}
    	
	}
/**
 * 显示提示信息
 * 
 * 
 * @param msg
 * @param dur
 */
    public void SetToastMsg(Toast_msg msg) 
    {
  		// TODO Auto-generated method stub      	
	   	    	mToastMsg=msg;
	         	
  	}
 
    public void SetInetAddress(InetAddress address)
     {
    	this.mAddress=address;
    	this.mIPAddrStr=this.mAddress.toString();
	 }
    
    
		
	/**
	 * 发送到打印机直接打印
	 * @param order
	 */
	
		/**
		 * 发送到PC机待打印
		 * @param order
		 */
		
/**
 * 
 * 
 **/
		public boolean Print_1_Docs()
		{
			return false;
		}
/**
 * 
 * 
 **/		
		
/**
 * 
 * 
 */

/**
 * 克隆一份
 * 
 */
		public CloudPrintAddress  Clone()
		{
			CloudPrintAddressBase cpa=new CloudPrintAddress() ;
			/*--------------------------------*/
			cpa.mPrintFrom=this.mPrintFrom;
			cpa.mPrintType=this.mPrintType;
			cpa.mAddrCharacter=this.mAddrCharacter;
			cpa.mIPAddrStr=this.mIPAddrStr;
			
			cpa.setPrinterPoint(this.GetPrinterPointName());
		//	cpa.SetLocalParent(this.GetLocalParentClone());
			cpa.mAddress=this.mAddress;
			/*--------------------------------*/
			cpa.latitude=this.latitude;			
			cpa.longitude=this.longitude;
			/*--------------------------------*/
			cpa.SetCloneFlag();
			return (CloudPrintAddress) cpa;
			
		}
		public void SeBDtLocation(BDLocation lbd)
		{
			if(lbd!=null){
					this.setLatitude(lbd.getLatitude());
					this.setLongitude(lbd.getLongitude());
					this.SetLocation(lbd.getAddrStr());
			}
		}
/**
 * 
 * 
 **/
}
