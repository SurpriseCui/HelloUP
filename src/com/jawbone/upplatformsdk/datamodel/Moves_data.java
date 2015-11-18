/**
 * @author Omer Muhammed
 * Copyright 2014 (c) Jawbone. All rights reserved.
 *
 */
package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Moves_data implements Serializable{

//    public String xid;
//
//    public String getXid() {
//        return xid;
//    }
//
//    public void setXid(String xid) {
//        this.xid = xid;
//    }
	private List<Moves_items> items;
	private Links links;
	private int size;
	
	public Moves_data(){
		items = new ArrayList<Moves_items>();
	}
	
	public List<Moves_items> getItems(){
		return  items;
	}
	
	public void setItems(List<Moves_items> i){
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

