package com.example.cloudprintshop;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.readystatesoftware.viewbadger.BadgeView;

/*import com.example.LeftRightHand.LeftRightGestureDetector.PhoneInHand;*/


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class f_CloudPrintShopView extends CloudPrintViewTemplate{
	public MapView mMapView = null; 
	public BaiduMap mBaiduMap=null;
	public View mMapLayout=null;
	public Button   mButtonLocation;
	BadgeView mBadgeView;
	public EditText mEditText;
	private LinearLayout mItemLayout;
	private View mSearchViewItem;
	boolean isFirstLoc = true;// 是否首次定位
	/*
	 *
	 * 
	 * 
	 */
	private PrinterOrderDataSet mPOD;
	/*
	 *
	 * 
	 * 
	 */
	public final static int mMapBalloonImg=R.drawable.popup;
	private final int[] mImageItem={
			R.drawable.main_business,
			R.drawable.main_device_state_info,
			R.drawable.main_finance_info,
			R.drawable.main_search_order_info,
			R.drawable.main_exit_app};
	private final int[] mStringItem={
			R.string.view_item_business,
			R.string.view_item_device_state_info,
			R.string.view_item_finance_info,
			R.string.view_item_search_order_info,
			R.string.view_item_exit_app
	};
	private final int[] mViewItemId={
		R.id.id_widgets_business,
		R.id.id_widgets_device_state_info,
		R.id.id_widgets_finance_info,
		R.id.id_widgets_search_order_info,
		R.id.id_widgets_exit_app,
	};
	private final static int mLayoutId=R.layout.activity_cloud_print;
	public f_CloudPrintShopView(Context context) {
		
		super(context,mLayoutId);
		SmallMemUsage=false;
		// TODO Auto-generated constructor stub
		this.initChildView();
		this.InitMapView();
		new Thread(new UpdataUI()).start();
		
	}
	
	@Override
	public void LayoutChanged(){
		
		this.Set5ButtonLayoutParams();
		
	}
	public void Set5ButtonLayoutParams(){
		
		if(this.mItemLayout!=null){
			int img_width=this.mRootWidth/mImageItem.length;
			int img_height=this.mItemLayout.getHeight();
			int item_wh=Math.min(img_width,img_height);
			int item_top_mar=this.mItemLayout.getHeight()*30/150;
			 LinearLayout.LayoutParams mItemParam=new LinearLayout.LayoutParams(item_wh,item_wh);
			 mItemParam.setMargins(0, item_top_mar,0,0);
			 for(int i=0;i<this.mItemLayout.getChildCount();i++){
				this.mItemLayout.getChildAt(i).setLayoutParams(mItemParam);
			}
			
		}
	}
	public void Set5ButtonClickLinsenar(){
		//--1
		mItemLayout.findViewById(R.id.id_widgets_business).findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				f_CloudPrintShopView.this.StartActivityView(BusinessActivity.class);
			}});
		//--2
		mItemLayout.findViewById(R.id.id_widgets_device_state_info).findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				f_CloudPrintShopView.this.StartActivityView(DeviceStatusMultiActivity.class);
			}});
		//--3
		mItemLayout.findViewById(R.id.id_widgets_finance_info).findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
	
	   @Override
	   public void onClick(View v) {
		// TODO Auto-generated method stub
		   f_CloudPrintShopView.this.StartActivityView(FinanceActivity.class);
	   }});
		//4
		mSearchViewItem=mItemLayout.findViewById(R.id.id_widgets_search_order_info);
		mSearchViewItem.findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
	
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
	    	f_CloudPrintShopView.this.StartActivityView(QueryOrderMainActivity.class);
	    }});
		//5
        mItemLayout.findViewById(R.id.id_widgets_exit_app).findViewById(R.id.id_item_image).setOnClickListener(new View.OnClickListener() {
	
	    @Override
	     public void onClick(View v) {
		// TODO Auto-generated method stub
	    	YouConntCLoseApp();
	     }});
	}
	public void YouConntCLoseApp()
	{
		StringBuffer sb=new StringBuffer();
		sb.append("     Staff      ");
		sb.append("\n");
		sb.append("现在还未到下班时间");
		Toast_make_show(sb.toString(),Toast.LENGTH_SHORT,Gravity.CENTER);
	}
	public void initChildView()
	{
		this.mEditText=(EditText) this.findViewById(R.id.edittext_shop_search);
		this.mEditText.addTextChangedListener(new EditChangedListener());
		this.mItemLayout=(LinearLayout) mView.findViewById(R.id.id_main_select_item);
		this.mButtonLocation=(Button) this.findViewById(R.id.map_location_request);
		this.mMapLayout=this.mView.findViewById(R.id.id_map_pannel);
		if(this.mItemLayout!=null){
			int itemLength=this.mImageItem.length;
			for(int i=0;i<itemLength;i++){
				
				View v=this.GetViewItem(mImageItem[i],mStringItem[i]);
				v.setId(mViewItemId[i]);
				this.mItemLayout.addView(v);
			}
			this.Set5ButtonClickLinsenar();
			
		}
	
		
	}
	public View GetViewItem(int imgres,int strres)
	{
		View v=LibCui.getViewFromeXml(R.layout.shop_select_item, mContext);
	    if(v!=null){
	    	ImageView iv=(ImageView) v.findViewById(R.id.id_item_image);
	    	if(iv!=null){
	    		iv.setImageDrawable(res.getDrawable(imgres));
	    	}
	    	TextView tv=(TextView) v.findViewById(R.id.id_item_text);
	    	if(tv!=null){
	    		tv.setText(strres);
	    	}
	    	
	    }
		return v;
	}
	public void InitMapView()
	{
	
		if(this.mMapLayout!=null&&true){
			this.initMap();			
			if(this.mMapLayout instanceof ViewGroup){
				
				( (ViewGroup) (this.mMapLayout)).addView(this.mMapView);
			}
			
		}		
	}
	public void initMap()
	{
		this.mMapView= new MapView(getContext(), new BaiduMapOptions());
		this.mBaiduMap = mMapView.getMap();		
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom((float) 16.5).build()));
		this.InitLocation();	
		this.initCloudPoint();
	}
	 public void InitLocation()
	    {
	    	 // 开启定位图层
	    	//mCurrentMode = LocationMode.NORMAL;
	 		mBaiduMap.setMyLocationEnabled(true);	 	
	 	// 定位初始化
	 			this.InitLocationClient();	 			
	 			mButtonLocation.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						isFirstLoc=true;
						RequestLocationClient();
						Toast toast_t=Toast.makeText(v.getContext(),"正在定位......",Toast.LENGTH_SHORT);
						toast_t.setGravity(Gravity.CENTER,0,0);
						toast_t.getView().setBackgroundColor(0xc8ffffff);
						toast_t.show();
						;
					}
				});
	    }
 /**
  * 
  */
	 public void initCloudPoint()
		{
			//this.mBaiduMap.addOverlay(MarkerBaiduView.GetDefaultMarker());
			
			MarkerBaiduView.AddDefaultMarker(mBaiduMap);
			OnMarkClick OMC=new OnMarkClick();
			this.mBaiduMap.setOnMapClickListener(OMC);
			this.mBaiduMap.setOnMarkerClickListener(OMC);
		}
	 @Override
	 protected void  WhenOnReceiveLocation(BDLocation location)
	 {
	 	// map view 销毁后不在处理新接收的位置
	 	super.WhenOnReceiveLocation(location);
	 	if (location == null || mMapView == null)
	 		return;
	 	MyLocationData locData = new MyLocationData.Builder()
	 			.accuracy(location.getRadius())
	 			// 此处设置开发者获取到的方向信息，顺时针0-360
	 			.direction(location.getDirection())
	 			.latitude(location.getLatitude())
	 			.longitude(location.getLongitude())
	 			.build();
	 	mBaiduMap.setMyLocationData(locData);
	 	if (isFirstLoc) {
	 		isFirstLoc = false;
	 		mBaiduMap.setMyLocationData(locData);
	 		LatLng ll = new LatLng(location.getLatitude(),
	 				location.getLongitude());
	 		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
	 		mBaiduMap.animateMapStatus(u);
	 	}
	 	

	 }

	 /*public MapView GetBaiduMap()
	{
		return this.mMapView;
	}*/
	  @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        	//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	        	 
	    		
	    		if(mBaiduMap!=null){
	    			// 关闭定位图层
	        		mBaiduMap.setMyLocationEnabled(false); 
	        		mBaiduMap=null;
	    		}
	    		
	    		
	    		if(mMapView!=null){    		
	    		   mMapView.onDestroy();
	    		   mMapView = null;
	    		}
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        if(mMapView!=null){
	          mMapView.onResume(); 
	        }
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        if(mMapView!=null){
	            mMapView.onPause(); 
	        }
	 
	        }  
	    @Override
	    public void OrderTaskUpdata(PrinterOrderDataSet pds)
		{
		  //  this.ChangeView(this.mPOD=pds);	
	    	  this.ChangeView_V2(this.mPOD=pds);
		   
		   
		}
	    public void ChangeView_V2(PrinterOrderDataSet pds)
	    {
	    	 if(pds==null)
	    		 return;
		    View view_Search=mSearchViewItem;
		    View target =view_Search;
		    int Count=pds.GetFileUnDo().size();
		    if(this.mBadgeView==null){
		    	this.mBadgeView=new BadgeView(getContext(), target);
		    	this.mBadgeView.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
		    	this.mBadgeView.setTextSize(24);
		    }
		    if(this.mBadgeView==null){
		    	return;
		    }else{
		    	if(Count==0){
		    		if(this.mBadgeView.isShown()){
		    			this.mBadgeView.hide();
		    		}
		    		
			    	 
			    }else if(Count>0&&Count<99){
			    	this.mBadgeView.setText(String.valueOf(Count));
			    	if(this.mBadgeView.isShown()==false){
			    		this.mBadgeView.show();
			    	}
			    
			    }else{
			    	this.mBadgeView.setText("99");				    
			    	if(this.mBadgeView.isShown()==false){
			    		this.mBadgeView.show();
			    	}
			    }
		    }
		    
	    }
	    public void ChangeView(PrinterOrderDataSet pds)
	    {
	    	View view_Search=mSearchViewItem.findViewById(R.id.id_item_image);
	    	final int TaskSize=pds.GetFileUnDo().size()+2;
	    	int DrawableID=R.drawable.main_search_order_info;
	    		
		    	if(view_Search instanceof ImageView){
		    		 ImageView iv=(ImageView) view_Search;
		    		if(TaskSize==0){
			    		iv.setImageResource( DrawableID);
			    	}else{
			    		 /**/			    		
			    		 Drawable iv_bg=(getContext().getResources().getDrawable( DrawableID));	    		 //
			    		 Bitmap mBitmap = ((BitmapDrawable)iv_bg).getBitmap().copy(Config.ARGB_4444,true);
			    		 Canvas canvas=new Canvas(mBitmap);
			    		 /*动态绘制*/
			    		 Paint paint=new Paint();
			    		 paint.setColor(Color.RED);	
			    		 paint.setTextSize(90); 
			    		 FontMetrics fm = paint.getFontMetrics();
			    		 int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);  
			    		 //int Pos=mBitmap.getHeight()/2;
			    		 String str_draw=null;
			    		 if(TaskSize>10){
			    			 str_draw="∞";
			    		 }else{
			    			 str_draw=String.valueOf(TaskSize);
			    		 }
			    		
			    		 paint.setColor(Color.RED);	
			    		 canvas.drawCircle(textHeight/2,textHeight/2,textHeight/2, paint);
			    		 paint.setColor(Color.WHITE);	
			    		 canvas.drawText(str_draw,0,textHeight, paint);
			    		 iv.setImageBitmap(mBitmap);
			    	}
		    		
		    	}
		    	
	    	
	    		
	    		
	    	
	    }

	   

	    
	    
	       class EditChangedListener implements TextWatcher {  
	    	  //private boolean DEBUG=false;
	           private CharSequence temp;//监听前的文本  
	           private int editStart;//光标开始位置  
	           private int editEnd;//光标结束位置  
	           private final int charMaxNum = 10;  
	      
	           @Override  
	           public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
	              
	               temp = s;  
	           }  
	      
	           @Override  
	           public void onTextChanged(CharSequence s, int start, int before, int count) {  
	             
	              
	      
	           }  
	      
	           @Override  
	           public void afterTextChanged(Editable s) {  
	               
	        	   	temp=s;
	        	   	if("close".equals(temp.toString().toLowerCase())){
	        	   		finish();
	        	   	}
	      
	           }  
	       };   
	       
	       public  class OnMarkClick implements  OnMarkerClickListener,OnMapClickListener
	 	  {
	 		  public OnMarkClick(){
	 			  
	 		  }
	 		  public View GetView( CloudPrintAddress info)
	 		  {
	 			  StringBuffer sb=new StringBuffer();
	 			  if(mPOD!=null){
	 				  sb.append(info.getName());sb.append("\n");
	 				  sb.append("打印池：");
	 				  sb.append(mPOD.GetFileUnDo().size());	 			  
	 			  }
	 			  //View v=null;
	 			    Button button = new Button(getContext());
	 				button.setBackgroundResource(mMapBalloonImg);
	 				button.setText(sb.toString());
	 				button.setTextColor(Color.BLACK);
	 			  
	 			  return button;
	 		  }
	 		  public InfoWindow getInfoWindow( CloudPrintAddress info)
	 		  {
	 			  View v=this.GetView(info);
	 			  OnInfoWindowClickListener listener = null;
	 				listener = new OnInfoWindowClickListener() {
	 					public void onInfoWindowClick() {
	 						//隐藏InfoWindow
	 						mBaiduMap.hideInfoWindow();
	 						try {
	 							this.clone();
	 						} catch (CloneNotSupportedException e) {
	 							// TODO Auto-generated catch block
	 							e.printStackTrace();
	 						}
	 					}
	 				};
	 				
	 				LatLng ll = new LatLng(info.getLatitude_Double(),info.getLongitude_Double());
	 				return  new InfoWindow(BitmapDescriptorFactory.fromView(v), ll,0, listener);
	 				
	 		  }
	 		  @Override
	 			public boolean onMarkerClick(Marker arg0) {
	 				// TODO Auto-generated method stub
	 			  CloudPrintAddress info = ( CloudPrintAddress) arg0.getExtraInfo().get("info");
	 				
	 				//SetDefaultCloudPrintInfo2Mem(info);
	 				
	 				/*
	 				LatLng ll = arg0.getPosition();
	 				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
	 				Log.e("TAG", "--!" + p.x + " , " + p.y);
	 				//p.x +=50;
	 				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
	 				*/
	 				
	 				mBaiduMap.showInfoWindow(this.getInfoWindow(info));
	 				return false;
	 			}
	 		  @Override
	 			public void onMapClick(LatLng arg0) {
	 				// TODO Auto-generated method stub
	 				mBaiduMap.hideInfoWindow();
	 			}

	 			@Override
	 			public boolean onMapPoiClick(MapPoi arg0) {
	 				// TODO Auto-generated method stub
	 				return false;
	 			}
	 	  }
	       
	       public class UpdataUI implements Runnable
	       {
	    	   public void UpdataItem()
	    	   {
	    		   View v=findViewById(R.id.text_view_shop_updata_time);
	               StringBuilder sb=new StringBuilder();
	    		    
	 		    	if(mPOD!=null&&v!=null){
	 		    		 if(mPOD.LastUpdataTime!=null){
	 		    			  sb.append("数据更新至:\n");
	 		    		      sb.append(mPOD.LastUpdataTime.format("%m月%d日\n%H:%M:%S"));
	 		    		 }else{
	 		    			 //sb.append("数据更新失败");
	 		    		 }	
	 		    		   ((TextView) v).setText(sb.toString());   
	 		    	}   
	    	   }
			@Override
			public void run() {
				while(mThreadRun){
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					postDelay(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							UpdataItem();
						}
						
					});
					
				}
				
			}
	    	   
	       }
}
