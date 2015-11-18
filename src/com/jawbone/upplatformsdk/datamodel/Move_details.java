package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Move_details implements Serializable{
	private double distance;
	private double km;
	private int steps;
	private int active_time;
	private int longest_active;
	private int inactive_time;
	private int longest_idle;
	private double calories;
	private double bmr_day;
	private double bmr;
	private double bg_calories;
	private double wo_calories;
	private int wo_time;
	private int wo_active_time;
	private int wo_count;
	private int wo_longest;
	private int sunrise;
	private int sunset;
	private String tz;
	
	private List<Tsz> tsz;
	private List<Hourly_Totals> hourly_totals; 
	
	public Move_details(){
		tsz = new ArrayList<Tsz>();
		hourly_totals = new ArrayList<Hourly_Totals>();
	}
	
	
	public double getDistance(){
		return distance;
	}
	
	public void setDistance(double d){
		distance = d;
	}
	
	public double getkm(){
		return km;
	}
	
	public void setKm(double k){
		km = k;
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
	
	public int getLongest_active(){
		return longest_active;
	}
	
	public void setLongest_active(int la){
		longest_active = la;
	}
	
	public int getInactive_time(){
		return inactive_time;
	}
	
	public void setInactive_time(int it){
		inactive_time = it;
	}
	
	public int getLongest_idle(){
		return longest_idle;
	}
	
	public void setLongest_idle(int li){
		longest_idle = li;
	}
	
	public double getCalories(){
		return calories;
	}
	
	public void setCalories(double c){
		calories = c;
	}
	
	public double getBmr_day(){
		return bmr_day;
	}
	
	public void setBmr_day(double bd){
		bmr_day = bd;
	}
	
	public double getBmr(){
		return bmr;
	}
	
	public void setBmr(double b){
		bmr = b;
	}

	public double getBg_calories(){
		return bg_calories;
	}
	
	public void setBg_calories(double bc){
		bg_calories = bc;
	}
	
	public double getWo_calories(){
		return wo_calories;
	}
	
	public void setWo_calories(double wc){
		wo_calories = wc;
	}
	
	public int getWo_time(){
		return wo_time;
	}
	
	public void setWo_time(int wt){
		wo_time = wt;
	}
	
	public int getWo_active_time(){
		return wo_active_time;
	}
	
	public void setWo_active_time(int wat){
		wo_active_time = wat;
	}
	
	public int getWo_count(){
		return wo_count;
	}
	
	public void setWo_count(int wc){
		wo_count = wc;
	}
	
	public int getWo_longest(){
		return wo_longest;
	}
	
	public void setWo_longest(int wl){
		wo_longest = wl;
	}
	
	public int getSunrise(){
		return sunrise;
	}
	
	public void setSunrise(int s){
		sunrise = s;
	}
	
	public int getSunset(){
		return sunset;
	}
	
	public void setSunset(int s){
		sunset = s;
	}
	
	public String getTz(){
		return tz;
	}
	
	public void setTz(String t){
		tz = t;
	}
	
    public List<Tsz> getTsz()   
    {  
        return tsz;  
    }  
    public void setTsz(List<Tsz> t)   
    {  
        this.tsz = t;  
    } 
    
    public List<Hourly_Totals> getHourly_totals()   
    {  
        return hourly_totals;  
    }  
    public void setHourly_totals(List<Hourly_Totals> ht)   
    {  
        this.hourly_totals = ht;  
    } 
}
