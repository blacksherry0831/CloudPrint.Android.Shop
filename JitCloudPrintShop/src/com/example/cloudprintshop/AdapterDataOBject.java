package com.example.cloudprintshop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *联系人，已完成，未完成，的adapter 
 * 
 * 
 */
public class AdapterDataOBject
{
	  private  ArrayList<String>        mKeys=new ArrayList<String>();
	  private  HashMap<String,Object>   mDatamap=new HashMap<String,Object>();
	  
	  public int size()
	  {
		  synchronized (this) {
			     return this.mKeys.size();
		  }
		 
	  }
	  public Object get(int pos)
	  {
		  synchronized (this) {
				  String key=this.mKeys.get(pos);
				  return this.mDatamap.get(key);
		  }
	  }
	  
	  public boolean add(Object ob)
	  {
		  synchronized (this) {
				  String key=ob.toString();
				  
				  if(this.mDatamap.containsKey(key)){
					  //已包含
					  return false;
				  }else{
					  //未存在
					  this.mDatamap.put(key, ob);
					  this.mKeys.add(key);
					  return true;
				  }
		  }
		  
	  }
	  public boolean Remove(Object ob)
	  {
		  synchronized (this){
				  String key=ob.toString();
				  
				  if(this.mDatamap.containsKey(key)){
					  //已包含
					 
					     this.mDatamap.remove(key);
					     this.mKeys.remove(key);
						  return true;
					 
				  }else{
					  return false;
				  }
		  }
	  }
	  public void clear()
	  {
		  synchronized (this){
			  this.mKeys.clear();
			  this.mDatamap.clear();
		  }
	  }
	  public void addAll(ArrayList<Object> Selected)
	  {
		  for(Object o:Selected){
			  this.add(o);
		  }
	  }
	  
	
}
