package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Move_ticks_data implements Serializable{
	private List<Move_ticks_items> items;
	private int size;
	
	public Move_ticks_data(){
		items = new ArrayList<Move_ticks_items>();
	}
	
	public List<Move_ticks_items> getItems(){
		return items;
	}
	
	public void setItems(List<Move_ticks_items> i){
		items = i;
	}
	
	public int getSize(){
		return size;
	}
	
	public void setSize(int s){
		size = s;
	}
}
