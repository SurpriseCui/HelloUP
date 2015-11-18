package com.jawbone.upplatformsdk.datamodel;

import java.util.List;

public class Sleep_list {
	public Meta meta;
	public Sleep_data data;
	
	public Meta getMeta(){
		return meta;
	}
	
	public void setMeta(Meta m){
		meta = m;
	}
	
	public Sleep_data getData(){
		return data;
	}
	
	public void setData(Sleep_data s){
		data = s;
	}
	
	public class Meta {
	    public Integer code;
	    public String message;
	    public String user_xid;
	    public Integer time;
//	    public String errorType;
//	    public String errorDetail;
//	    public String errorUserMsg;

	    public Integer getCode() {
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

	    public String getUser_xid() {
	        return user_xid;
	    }
	    
	    public void setUser_xid(String u){
	    	user_xid = u;
	    }

	    public Integer getTime() {
	        return time;
	    }
	    
	    public void setTime(int t){
	    	time = t;
	    }

//	    public String getErrorType() {
//	        return errorType;
//	    }
//	    
//	    public void setErrorType(String e){
//	    	errorType = e;
//	    }
//
//	    public String getErrorDetail() {
//	        return errorDetail;
//	    }
//	    
//	    public void setErrorDetails(String e){
//	    	errorDetail = e;
//	    }
//
//	    public String getErrorUserMsg() {
//	        return errorUserMsg;
//	    }
//	    
//	    public void setErrorUserMsg(String e){
//	    	errorUserMsg = e;
//	    }
	    
	}
	
	public class Sleep_data {
		public List<Sleep_items> items;
//		public Links links;
		public int size;
		
		public List<Sleep_items> getItems(){
			return items;
		}
		
		public void setItems(List<Sleep_items> i){
			items = i;
		}
		
//		public Links getLinks(){
//			return links;
//		}
//		
//		public void setLinks(Links l){
//			links = l;
//		}
		
		public int getSize(){
			return size;
		}
		
		public void setSize(int s){
			size = s;
		}
		
		public class Links {

		    public String next;
		    public String prev;

		    public String getNext() {
		        return next;
		    }

		    public void setNext(String next) {
		        this.next = next;
		    }

		    public String getPrev() {
		        return prev;
		    }

		    public void setPrev(String prev) {
		        this.prev = prev;
		    }
		}
		
		public class Sleep_items {
			public String xid;//
			public String title;//
			public int sub_type;//
			public int time_created;
			public int time_completed;//
			public int date;//
			public String place_lat;//
			public String place_lon;//
			public String place_acc;//
			public String place_name;//
			public String sampshot_image;//
			
			public String time_updated;//
			
			public sleep_details details;//
			public boolean shared;//
			
			public String getXid(){
				return xid;
			}
			
			public void setXid(String x){
				xid = x;
			}
			
			public String getTitle(){
				return title;
			}
			
			public void setTitle(String t){
				title = t;
			}
			
			public int getSub_type(){
				return sub_type;
			}
			
			public void setSub_type(int s){
				sub_type = s;
			}
			
			public int getTime_created(){
				return time_created;
			}
			
			public void setTime_created(int t){
				time_created = t;
			}
			
			public int getTime_completed(){
				return time_completed;
			}
			
			public void setTime_completed(int t){
				time_completed = t;
			}
			
			public int getDate(){
				return date;
			}
			
			public void setDate(int d){
				date = d;
			}
			
			public String getPlace_lat(){
				return place_lat;
			}
			
			public void setPlace_lat(String p){
				place_lat = p;
			}
			
			public String getPlace_lon(){
				return place_lon;
			}
			
			public void setPlace_lon(String p){
				place_lon = p;
			}
			
			public String getPlace_acc(){
				return place_acc;
			}
			
			public void setPlace_acc(String p){
				place_acc = p;
			}
			
			public String getPlace_namr(){
				return place_name;
			}
			
			public void setPlace_name(String p){
				place_name = p;
			}
			
			public String getSampshot_image(){
				return sampshot_image;
			}
			
			public void setsampshot_image(String s){
				sampshot_image = s;
			}
			
			public String getTime_updated(){
				return time_updated;
			}
			
			public void setTime_updated(String t){
				time_updated = t;
			}
			
			public boolean getShared(){
				return shared;
			}
			
			public void setShared(boolean s){
				shared = s;
			}
			
			public sleep_details getDetails(){
				return details;
			}
			
			public void setDetails(sleep_details d){
				details = d;
			}
			
			public class sleep_details {
				public int smart_alarm_fire;//
				public int awake_time;//
				public int asleep_time;//
				public int awakenings;//
				public int rem;//
				public int light;//
				public int sound;//
				public int awake;//
				public int duration;//
				public int quality;//
				public String tz;//
				
				public String body;//
				public String mind;//
				public String sunset;//
				public String sunrise;//
				
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
				
				public String getBody(){
					return body;
				}
				
				public void setBody(String b){
					body = b;
				}
				
				public String getMind(){
					return mind;
				}
				
				public void setMind(String m){
					mind = m;
				}
				
				public String getSunset(){
					return sunset;
				}
				
				public void setSunset(String s){
					sunset = s;
				}
				
				public String getSunrise(){
					return sunrise;
				}
				
				public void setSunrise(String s){
					sunrise = s;
				}
			}

		}

	}
}
