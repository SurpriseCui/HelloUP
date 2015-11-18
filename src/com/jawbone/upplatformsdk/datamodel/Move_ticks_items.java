package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Move_ticks_items implements Serializable{
	private double distence;
	private double time_completed;
	private double active_time;
	private double calories;
	private double steps;
	private String time;
	private double speed;
	
	public double getDistence(){
		return distence;
	}
	
	public void setDistence(double d){
		distence = d;
	}
	
	public double getTime_completed(){
		return time_completed;
	}
	
	public void setTime_completed(double t){
		time_completed = t;
	}
	
	public double getActive_time(){
		return active_time;
	}
	
	public void setActive_time(double a){
		active_time = a;
	}
	
	public double getCalories(){
		return calories;
	}
	
	public void setCalories(double c){
		calories = c;
	}
	
	public double getSteps(){
		return steps;
	}
	
	public void setSteps(double s){
		steps = s;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String t){
		time = t;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void setSpeed(double s){
		speed = s;
	}
}
