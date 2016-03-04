package com.example.cloudprintshop;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;


public class MarkerBaiduView 
{
	public ArrayList<Marker> mMarkerList=new ArrayList<Marker>();
	
	public MarkerBaiduView ()
	{
		
	}
	

	
	
	public static void AddDefaultMarker_M(BaiduMap mBaiduMap,ArrayList<CloudPrintAddress> info)
	{
		for(CloudPrintAddress cpa : info){
			AddDefaultMarker_S(mBaiduMap,cpa);
		}
		
	}
	public static OverlayOptions AddDefaultMarker_S(BaiduMap mBaiduMap,CloudPrintAddress info)
	{		
		LatLng llA = new LatLng(info.getLatitude_Double(),info.getLongitude_Double());
		BitmapDescriptor bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
				.zIndex(9).draggable(true);
		
		Overlay view=mBaiduMap.addOverlay(ooA);
		Bundle bundle = new Bundle();
		bundle.putSerializable("info", info);
		view.setExtraInfo(bundle);
		return ooA;
	}
	public static void AddDefaultMarker(BaiduMap mBaiduMap)
	{
		ArrayList<CloudPrintAddress> info_array=new ArrayList<CloudPrintAddress>();
		
		{
			double LA_qin=31.911387;
			double LO_qin=118.90477;
			CloudPrintAddress info=new CloudPrintAddress(
					LA_qin,
					LO_qin,
					0,
					"亲打印-A",
					"距离1km",
					"金科南区食堂",
					 0);
			info_array.add(info);
		}
		{
			double LA_qin=31.910153;
			double LO_qin=118.904105;
			CloudPrintAddress info=new CloudPrintAddress(
					LA_qin,
					LO_qin,
					0,
					"亲打印-B",
					"距离1km",
					"金科南区食堂",
					 0);
			info_array.add(info);
		}
		
		
		AddDefaultMarker_M(mBaiduMap,info_array);
	
		
	}
	public void addInfosOverlay(List< CloudPrintAddress> infos,BaiduMap mBaiduMap)
	{
		BitmapDescriptor mIconMaker = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		mBaiduMap.clear();
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		for (CloudPrintAddress info : infos)
		{
			// 位置
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			// 图标
			overlayOptions = new MarkerOptions().position(latLng)
					.icon(mIconMaker).zIndex(5);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);
		}
		// 将地图移到到最后一个经纬度位置
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
	}


}
