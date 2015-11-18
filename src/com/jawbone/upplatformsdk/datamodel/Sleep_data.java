package com.jawbone.upplatformsdk.datamodel;

import java.util.List;

public class Sleep_data {
	public List<Sleep_items> items;
	public Links links;
	public int size;
	
	public List<Sleep_items> getItems(){
		return items;
	}
	
	public void setItems(List<Sleep_items> i){
		items = i;
	}
	
	public Links getLinks(){
		return links;
	}
	
	public void setLinks(Links l){
		links = l;
	}
	
	public int getSize(){
		return size;
	}
	
	public void setSize(int s){
		size = s;
	}
}
