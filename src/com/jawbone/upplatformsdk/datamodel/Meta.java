/**
 * @author Omer Muhammed
 * Copyright 2014 (c) Jawbone. All rights reserved.
 *
 */
package com.jawbone.upplatformsdk.datamodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Meta implements Serializable{
    public int code;
    public String message;
    public String userXid;
    public int time;
    public String errorType;
    public String errorDetail;
    public String errorUserMsg;

    public int getCode() {
        return code;
    }
    
    public void setCode(int c){
    	code = c;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String m){
    	message = m;
    }

    public String getUserXid() {
        return userXid;
    }
    
    public void setUserXid(String u){
    	userXid = u;
    }

    public int getTime() {
        return time;
    }
    
    public void setTime(int t){
    	time = t;
    }

    public String getErrorType() {
        return errorType;
    }
    
    public void setErrorType(String e){
    	errorType = e;
    }

    public String getErrorDetail() {
        return errorDetail;
    }
    
    public void setErrorDetails(String e){
    	errorDetail = e;
    }

    public String getErrorUserMsg() {
        return errorUserMsg;
    }
    
    public void setErrorUserMsg(String e){
    	errorUserMsg = e;
    }
    
}
