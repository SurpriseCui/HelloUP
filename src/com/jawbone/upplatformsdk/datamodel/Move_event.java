package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Move_event implements Serializable{
	private Meta meta;
	private Move_data data;
	
	public Move_event(){
		meta = new Meta();
		data = new Move_data();
	}
	
	public Meta getMeta(){
		return meta;
	}
	
	public void setMeta(Meta m){
		meta = m;
	}
	
	public Move_data getData(){
		return data;
	}
	
	public void setData(Move_data d){
		data = d;
	}
}
