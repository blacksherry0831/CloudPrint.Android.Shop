package com.example.cloudprintshop;

import java.io.File;

public class OperLocalFile
{
	
/**
 * 
 **/
		 public synchronized static void SaveTask2Disk(PrinterOrderItem poi)
		 {
			 LibCui.SaveString2Fille(poi.getFileName(),poi.getTaskContent(),LibCui.GetCloudPrintTaskDone());
		 }
 /**
  * 
  */
		 public synchronized static void SaveContacts2Disk(PrinterOrderItem poi)
		 {
			LibCui.SaveString2Fille(poi.getFileName(),poi.getTaskContent(),LibCui.GetCloudPrintContacts());
		 }
/**
 * 
 **/
}
