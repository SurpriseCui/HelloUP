package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Moves_list implements Serializable{
	private Meta meta;
	private Moves_data data;
	
	public Moves_list(){
		meta = new Meta();
		data = new Moves_data();
	}
	
	public Meta getMeta(){
		return meta;
	}
	
	public void setMeta(Meta m){
		meta = m;
	}
	
	public Moves_data getData(){
		return data;
	}
	
	public void setData(Moves_data d){
		data = d;
	}
}
