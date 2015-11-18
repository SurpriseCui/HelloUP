package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Moves_items implements Serializable{
	private String xid;
	private String title;
	private String type;
	private int time_created;
	private int time_updated;
	private int time_completed;
	private int date;
	private String snapshot_image;
	
	private Moves_details detail;
	
	public Moves_items(){
		detail = new Moves_details();
	}
	
	public String getXid(){
		return xid;
	}
	
	public void setXid(String x){
		xid = x;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public String getType(){
		return type;
	}
	
	public int getTime_created(){
		return time_created;
	}
	
	public void setTime_created(int t){
		time_created = t;
	}
	
	public int getTime_updated(){
		return time_updated;
	}
	
	public void setTime_updated(int t){
		time_updated = t;
	}
	
	public int getTime_completed(){
		return time_completed;
	}
	
	public void setTime_completed(int t){
		time_completed = t;
	}
	
	public int getDate(){
		return date;
	}
	
	public void setDate(int d){
		date = d;
	}
	
	public String getSnapshot_image(){
		return snapshot_image;
	}
	
	public void setSnapshot_image(String s){
		snapshot_image = s;
	}
	
	public Moves_details getdetails(){
		return detail;
	}
	
	public void setDetails(Moves_details d){
		detail = d;
	}
}
