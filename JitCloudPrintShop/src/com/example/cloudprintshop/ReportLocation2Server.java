package com.example.cloudprintshop;

import android.content.Context;

public class ReportLocation2Server 
{
	private volatile boolean mThread=true;
	String  Url=null;
	double la=0;
	double lo=0;
	Context ctx;
	static double  la_old;
	static double  lo_old;
	ReportLocation2Server(Context ctx)
	{
		this.ctx=ctx;
		this.Url=Connect2Server.GetReportUrl(ctx);
		
	}
	public void Start(double la,double lo){
		this.la=la;
		this.lo=lo;
		if(Url!=null&&((this.la+this.lo)>1)){
			if(PositionChange()){
				new Thread(new Report()).start();
			}			
		}
	}
	public boolean PositionChange()
	{
		double diff=Math.abs(la_old-la)+Math.abs(lo_old-lo);
		la_old=la;
		lo_old=lo;
		if(diff<1E-5){			//位置不变			
			return false;
		}else{
			return true;
		}
	
	}
	private class Report implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String Reportdata=Connect2Server.ReportLocationData(
					Connect2Server.UserID,
					Connect2Server.LicenseCode,
					la,
					lo,
					ctx);
			Connect2Server.PostString2Server(Url, Reportdata);
		}
		
	}
	public void Destory(){
		mThread=false;
	}
}
