package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Hourly_Totals implements Serializable{
	private int distance; 
	private double calories;
	private int steps;
	private int active_time;
	private int inactive_time;
	private int longest_active_time;
	private int longest_idle_time;
	
	public int getDistance(){
		return distance;
	}
	
	public void setDistance(int d){
		distance = d;
	}
	
	public double getCalories(){
		return calories;
	}
	
	public void setCalories(double c){
		calories = c;
	}
	
	public int getSteps(){
		return steps;
	}
	
	public void setSteps(int s){
		steps = s;
	}
	
	public int getActive_time(){
		return active_time;
	}
	
	public void setActive_time(int at){
		active_time = at;
	}
	
	public int getInactive_time(){
		return inactive_time;
	}
	
	public void setInactive_time(int it){
		inactive_time = it;
	}
	
	public int getLongest_active_time(){
		return longest_active_time;
	}
	
	public void setLongest_active_time(int lat){
		longest_active_time = lat;
	}
	
	public int setLongest_idle_time(){
		return longest_idle_time;
	}
	
	public void getLongest_idle_time(int llt){
		longest_idle_time = llt;
	}
}
