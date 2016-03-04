package com.example.cloudprintshop;

import java.util.Vector;

public class PrintDevicesStatusSet {
public final int mImgFree=R.drawable.ps_print_free_status;
public final int mImgOffline=R.drawable.ps_print_offline_status;
public final int mImgPrintint=R.drawable.ps_print_printing_status;

public Vector<PrintStatus>  mData;

public PrintDevicesStatusSet() {
	this.mData=new Vector<PrintStatus>();
	this.mData.add(new PrintStatus());
	this.mData.add(new PrintStatus());
	this.mData.add(new PrintStatus());
}
public int getCount() {
	return mData.size();
}


public Object getItem(int position) {
	return mData.get(position);
}
class PrintStatus{
	 PrintDevices mPdecices;
	 PrintDevicesDetail mPdevicesDetail;
	 PrintStatus(){
		 this.mPdecices=new PrintDevices();
		 this.mPdevicesDetail=new PrintDevicesDetail();
	 } 
}
class PrintDevices{
    public	String mName;
    public	String maddr;
    public	String mtype;
    public	String mStatus;
    PrintDevices(){
        mName="SHARP";
        maddr="192.168.0.20";
        mtype="网络打印机";
        mStatus="空闲";
    }
}
class PrintDevicesDetail{
    public	String mPrinterStatus;
    public	String mCopyingStatus;
    public	String mScannerStatus;
    PrintDevicesDetail(){
       mPrinterStatus=".............";
       mCopyingStatus=".............";
       mScannerStatus=".............";
    }
}

}
