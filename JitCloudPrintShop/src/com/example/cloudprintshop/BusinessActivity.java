package com.example.cloudprintshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BusinessActivity extends CloudPrintActivityTemplate {

	
	//BusinessView mView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		BusinessView mView;
		super.onCreate(savedInstanceState);
		this.mViewTemplate= mView=new BusinessView(this);
		setContentView(mView.GetView());
	}
 /*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
