package com.jawbone.upplatformsdk.datamodel;

public class Sleep_details {
	public int smart_alarm_fire;
	public int awake_time;
	public int asleep_time;
	public int awakenings;
	public int rem;
	public int light;
	public int sound;
	public int awake;
	public int duration;
	public int quality;
	public String tz;
	
	public int getSmart_alarm_fire(){
		return smart_alarm_fire;
	}
	
	public void setSmart_alarm_fire(int s){
		smart_alarm_fire = s;
	}
	
	public int getAwake_time(){
		return awake_time;
	}
	
	public void setAwake_time(int a){
		awake_time = a;
	}
	
	public int getAsleep_time(){
		return asleep_time;
	}
	
	public void setAsleep_time(int a){
		asleep_time = a;
	}
	
	public int getAwakenings(){
		return awakenings;
	}
	
	public void setAwakenings(int a){
		awakenings = a;
	}
	
	public int getRem(){
		return rem;
	}
	
	public void setRem(int r){
		rem = r;
	}
	
	public int getLight(){
		return light;
	}
	
	public void setLight(int l){
		light = l;
	}
	
	public int getSound(){
		return sound;
	}
	
	public void setSound(int s){
		sound = s;
	}
	
	public int getAwake(){
		return awake;
	}
	
	public void setAwake(int a){
		awake = a;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public void setDuration(int d){
		duration = d;
	}
	
	public int getQuality(){
		return quality;
	}
	
	public void setQuality(int q){
		quality = q;
	}
	
	public String getTz(){
		return tz;
	}
	
	public void setTz(String t){
		tz = t;
	}
}
