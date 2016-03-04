package com.example.cloudprintshop;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;

public class DevideStatusMultiView extends CloudPrintViewTemplate{

	private final static int mLayoutId=R.layout.activity_device_status_multi;
	public ListView mListView;
	public  PrintDevicesStatusSet mDevicesSet;
	public DevideStatusMultiView(Context context) 
	{
		super(context,mLayoutId);
		// TODO Auto-generated constructor stub
		mDevicesSet=new  PrintDevicesStatusSet();
		this.InitChildView();
		
	}
	public void InitChildView(){
		this.mListView=(ListView) mView.findViewById(R.id.id_devices_status_multi_list);
		if(this.mListView!=null){
			
			this.mListView.setAdapter(
					new PrinterListAdapter(
							this.mContext,
							R.layout.devices_status_summary_item));
		}
	}
	public void getDevicesView()
	{
		
	}

	class PrinterListAdapter extends BaseAdapter 
	{
		private Context mContext_t;
		private int mResource_t;

		public PrinterListAdapter(Context context, int resource) {
			mContext_t = context;
			mResource_t = resource;
		}

		@Override
		public int getCount() {
			return mDevicesSet.getCount();
		}

		@Override
		public Object getItem(int position) {
			return  mDevicesSet.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		 @Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			 
			 convertView = LayoutInflater.from(mContext_t).inflate(mResource_t, parent, false);
			 
			 Button button=(Button) convertView.findViewById(R.id.id_devives_detail_item_button);
			  button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DevideStatusMultiView.this.StartActivityView(DevicesStatusDetailSingleActivity.class);
				}
			});
			 /*
			File file = mFilesList.get(position);

			
			ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

			CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			if (mSelected.contains(file.getName())) {
				checkbox.setChecked(true);
				setItemBackground(convertView, true);
			} else {
				checkbox.setChecked(false);
				setItemBackground(convertView, false);
			}
			if (mIsMultiChoice) checkbox.setVisibility(View.VISIBLE);

			FileOperations.SetFileItemBackground(file,thumbnail);

			TextView filename = (TextView) convertView.findViewById(R.id.filename);
			filename.setText(file.getName());

			TextView filesize = (TextView) convertView.findViewById(R.id.filesize);
			if (filesize != null) {
				if (file.isFile()) filesize.setText(getHumanFileSize(file.length()));
				else filesize.setText("");
			}*/
			return convertView;
		}
		
}
}
