package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Move_ticks_meta implements Serializable{
	private String user_xid;
	private String message;
	private int code;
	private int time;
	
	public String getUser_xid(){
		return user_xid;
	}
	
	public void setUser_xid(String u){
		user_xid = u;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
	public int getCode(){
		return code;
	}
	
	public void setCode(int c){
		code = c;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int t){
		time = t;
	}
}
