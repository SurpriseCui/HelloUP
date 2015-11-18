package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Move_ticks implements Serializable{
	private Move_ticks_meta meta;
	private Move_ticks_data data;
	
	public Move_ticks(){
		meta = new Move_ticks_meta();
		data = new Move_ticks_data();
	}
	
	public Move_ticks_meta getMeta(){
		return meta;
	}
	
	public void setMeta(Move_ticks_meta m){
		meta = m;
	}
	
	public Move_ticks_data getData(){
		return data;
	}
	
	public void setData(Move_ticks_data d){
		data = d;
	}
}
