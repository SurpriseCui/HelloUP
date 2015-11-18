package com.jawbone.upplatformsdk.datamodel;

public class Sleep_items {
	public String xid;
	public String title;
	public int sub_type;
	public int time_created;
	public int time_completed;
	public int date;
	public String place_lat;
	public String place_lon;
	public String place_acc;
	public String place_name;
	public String sampshot_image;
	
	public Sleep_details details;
	
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
	
	public int getSub_type(){
		return sub_type;
	}
	
	public void setSub_type(int s){
		sub_type = s;
	}
	
	public int getTime_created(){
		return time_created;
	}
	
	public void setTime_created(int t){
		time_created = t;
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
	
	public String getPlace_lat(){
		return place_lat;
	}
	
	public void setPlace_lat(String p){
		place_lat = p;
	}
	
	public String getPlace_lon(){
		return place_lon;
	}
	
	public void setPlace_lon(String p){
		place_lon = p;
	}
	
	public String getPlace_acc(){
		return place_acc;
	}
	
	public void setPlace_acc(String p){
		place_acc = p;
	}
	
	public String getPlace_namr(){
		return place_name;
	}
	
	public void setPlace_name(String p){
		place_name = p;
	}
	
	public String getSampshot_image(){
		return sampshot_image;
	}
	
	public void setsampshot_image(String s){
		sampshot_image = s;
	}
	
	public Sleep_details getDetails(){
		return details;
	}
	
	public void setDetails(Sleep_details d){
		details = d;
	}
}
