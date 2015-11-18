/**
 * @author Omer Muhammed
 * Copyright 2014 (c) Jawbone. All rights reserved.
 *
 */
package com.jawbone.helloup;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jawbone.upplatformsdk.api.ApiManager;
import com.jawbone.upplatformsdk.datamodel.Move_data;
import com.jawbone.upplatformsdk.datamodel.Move_details;
import com.jawbone.upplatformsdk.datamodel.Move_event;
import com.jawbone.upplatformsdk.datamodel.Move_ticks;
import com.jawbone.upplatformsdk.datamodel.Move_ticks_data;
import com.jawbone.upplatformsdk.datamodel.Move_ticks_items;
import com.jawbone.upplatformsdk.datamodel.Moves_details;
import com.jawbone.upplatformsdk.datamodel.Moves_items;
import com.jawbone.upplatformsdk.datamodel.Moves_list;
import com.jawbone.upplatformsdk.datamodel.Sleep_list;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Activity that provides a list view of all the API calls available
 * for UP Platform
 */
public class UpApiListActivity extends ListActivity {

    private static final String TAG = UpApiListActivity.class.getSimpleName();

    private String mAccessToken;
    private String mClientSecret;
    private int pointNumber = -1;
    
    private String event_xid = "7VotIusjxHNeyGUN3H3unUsl4JGTE637";
    private String now_event_xid = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.white);

        Intent intent = getIntent();
        if (intent != null) {
            mClientSecret = intent.getStringExtra(UpPlatformSdkConstants.CLIENT_SECRET);
        }

        ListView listView = getListView();
        LayoutInflater inflater = LayoutInflater.from(this);
        TextView header = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, listView, false);
        header.setText("List of UP Platform API Calls");
        header.setTextColor(Color.WHITE);
        header.setBackgroundColor(Color.DKGRAY);
        header.setGravity(Gravity.CENTER);
        listView.addHeaderView(header);

        listView.setCacheColorHint(Color.TRANSPARENT);
        
        String[] displayStrings = new String[UpPlatformSdkConstants.RestApiRequestType.size];
        int index = 0;
        for (UpPlatformSdkConstants.RestApiRequestType r: UpPlatformSdkConstants.RestApiRequestType.values()) {
            displayStrings[index] = r.toString();
            index++;
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.up_api_list, displayStrings));

        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.black_overlay));
        listView.setDivider(sage);
        listView.setDividerHeight(1);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mAccessToken = preferences.getString(UpPlatformSdkConstants.UP_PLATFORM_ACCESS_TOKEN, null);

        if (mAccessToken != null) {
            ApiManager.getRequestInterceptor().setAccessToken(mAccessToken);
            listView.setOnItemClickListener(restApiListener());
        }
        
        
    }

    /**
     * Listener for the API listview, note that some API calls will not work because:
     * 1. They need a valid xid, and 2. we cannot delete what we didn't create, that
     * is, we cannot delete a meal that we did not create.
     */
    private OnItemClickListener restApiListener() {
        return new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                } else {
                    // list header is position 0, so decrement to keep it consistent
                    position = position - 1;
                }
                Log.e(TAG, "position clicked is :" + position);
                UpPlatformSdkConstants.RestApiRequestType apiRequestType = UpPlatformSdkConstants.RestApiRequestType.values()[position];
                switch (apiRequestType) {
                    case GET_MEALS_EVENTS_LIST:
                        Log.e(TAG, "making Get Meal Events List api call ...");
                        ApiManager.getRestApiInterface().getMealEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getMealEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 0;
                        break;
                    case GET_MEALS_EVENT:
                        Log.e(TAG, "making Get Meal Event api call ...");
                        ApiManager.getRestApiInterface().getMealEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "JtN269m6S_xmX72fwD63cg", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 1;
                        break;
                    case DELETE_MEAL:
                        Log.e(TAG, "making Delete Meal api call ...");
                        // note, you can only delete meals that you created
                        // so first create it then delete
                        ApiManager.getRestApiInterface().deleteMealEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 2;
                        break;
                    case CREATE_MEAL:
                        Log.e(TAG, "making Create Meal api call ...");
                        ApiManager.getRestApiInterface().createMealEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                                getCreateOrUpdateMealEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 3;
                        break;
                    case UPDATE_MEAL:
                        Log.e(TAG, "making Update Meal api call ...");
                        ApiManager.getRestApiInterface().updateMealEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "JtN269m6S_xmX72fwD63cg", //hardcoded value, should be dynamic
                                getCreateOrUpdateMealEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 4;
                        break;
                    case GET_MOVES_EVENTS_LIST:
                        Log.e(TAG, "making Get Move Events List api call ...");
                        ApiManager.getRestApiInterface().getMoveEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getMoveEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 5;
                        break;
                    case GET_MOVES_EVENT:
                        Log.e(TAG, "making Get Move Event api call ...");
                        ApiManager.getRestApiInterface().getMoveEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            now_event_xid, //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 6;
                        break;
                    case GET_MOVES_GRAPH:
                        Log.e(TAG, "making Get Move Graph api call ...");
                        ApiManager.getRestApiInterface().getMoveGraph(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "40F7_htRRnSLLvJP0G6cbA", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 7;
                        break;
                    case GET_MOVES_TICKS:
                        Log.e(TAG, "making Get Move Ticks api call ...");
                        ApiManager.getRestApiInterface().getMoveTicks(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            event_xid, //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 8;
                        break;
                    case GET_CUSTOM_EVENTS_LIST:
                        Log.e(TAG, "making Get Custom Events List api call ...");
                        ApiManager.getRestApiInterface().getCustomEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCustomEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 9;
                        break;
                    case CREATE_CUSTOM_EVENT:
                        Log.e(TAG, "making Create Custom Event api call ...");
                        ApiManager.getRestApiInterface().createCustomEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateOrUpdateCustomEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 10;
                        break;
                    case UPDATE_CUSTOM_EVENT:
                        Log.e(TAG, "making Update Custom Event api call ...");
                        ApiManager.getRestApiInterface().updateCustomEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateOrUpdateCustomEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 11;
                        break;
                    case DELETE_CUSTOM_EVENT:
                        Log.e(TAG, "making Delete Custom Event api call ...");
                        ApiManager.getRestApiInterface().deleteCustomEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 12;
                        break;
                    case GET_WORKOUTS_EVENTS_LIST:
                        Log.e(TAG, "making Get Workout Events List api call ...");
                        ApiManager.getRestApiInterface().getWorkoutEventList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getWorkoutEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 13;
                        break;
                    case GET_WORKOUTS_EVENT:
                        Log.e(TAG, "making Get Workout Event api call ...");
                        ApiManager.getRestApiInterface().getWorkoutEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "JtN269m6S_xYwnSu7WQdfA", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 14;
                        break;
                    case GET_WORKOUTS_GRAPH:
                        Log.e(TAG, "making Get Workout Graph api call ...");
                        ApiManager.getRestApiInterface().getWorkoutGraph(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 15;
                        break;
                    case GET_WORKOUTS_TICKS:
                        Log.e(TAG, "making Get Workout Ticks api call ...");
                        ApiManager.getRestApiInterface().getWorkoutTicks(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 16;
                        break;
                    case CREATE_WORKOUT_EVENT:
                        Log.e(TAG, "making Create Workout Event api call ...");
                        ApiManager.getRestApiInterface().createWorkoutEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateOrUpdateWorkoutEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 17;
                        break;
                    case UPDATE_WORKOUT_EVENT:
                        Log.e(TAG, "making Update Workout Event api call ...");
                        ApiManager.getRestApiInterface().updateWorkoutEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateOrUpdateWorkoutEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 18;
                        break;
                    case DELETE_WORKOUT_EVENT:
                        Log.e(TAG, "making Delete Workout Event api call ...");
                        ApiManager.getRestApiInterface().deleteWorkoutEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 19;
                        break;
                    case GET_SLEEP_EVENTS_LIST:
                        Log.e(TAG, "making Get Sleep Events List api call ...");
                        ApiManager.getRestApiInterface().getSleepEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getSleepEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 20;
                        break;
                    case GET_SLEEP_EVENT:
                        Log.e(TAG, "making Get Sleep Event api call ...");
                        ApiManager.getRestApiInterface().getSleepEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "Zmz9yIE9kk0zi4-n8juddg", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 21;
                        break;
                    case GET_SLEEP_GRAPH:
                        Log.e(TAG, "making Get Sleep Graph api call ...");
                        ApiManager.getRestApiInterface().getSleepGraph(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 22;
                        break;
                    case GET_SLEEP_TICKS:
                        Log.e(TAG, "making Get Sleep Ticks api call ...");
                        ApiManager.getRestApiInterface().getSleepPhases(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 23;
                        break;
                    case CREATE_SLEEP_EVENT:
                        Log.e(TAG, "making Create Sleep Event api call ...");
                        ApiManager.getRestApiInterface().createSleepEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateSleepEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 24;
                        break;
                    case DELETE_SLEEP_EVENT:
                        Log.e(TAG, "making Delete Sleep Event api call ...");
                        ApiManager.getRestApiInterface().deleteSleepEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 25;
                        break;
                    case GET_BODY_EVENTS_LIST:
                        Log.e(TAG, "making Get Body Events List api call ...");
                        ApiManager.getRestApiInterface().getBodyEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getBodyEventsListRequestParams(),
                            genericCallbackListener);
                        pointNumber = 26;
                        break;
                    case GET_BODY_EVENT:
                        Log.e(TAG, "making Get Body Event api call ...");
                        ApiManager.getRestApiInterface().getBodyEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "JtN269m6S_yjZabtdYhsJQ", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 27;
                        break;
                    case CREATE_BODY_EVENT:
                        Log.e(TAG, "making Create Body Event api call ...");
                        ApiManager.getRestApiInterface().createBodyEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateBodyEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 28;
                        break;
                    case DELETE_BODY_EVENT:
                        Log.e(TAG, "making Delete Body Event api call ...");
                        ApiManager.getRestApiInterface().deleteBodyEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 29;
                        break;
                    case GET_BAND_EVENTS:
                        Log.e(TAG, "making Get Band Events api call ...");
                        ApiManager.getRestApiInterface().getBandEvents(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getBandEventsRequestParams(),
                            genericCallbackListener);
                        pointNumber = 30;
                        break;
                    case GET_GOALS:
                        Log.e(TAG, "making Get Users Goals api call ...");
                        ApiManager.getRestApiInterface().getUsersGoals(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            genericCallbackListener);
                        pointNumber = 31;
                        break;
                    case CREATE_OR_UPDATE_GOALS:
                        Log.e(TAG, "making Create or Update Users Goals api call ...");
                        ApiManager.getRestApiInterface().createOrUpdateUsersGoals(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateOrUpdateUsersGoalsRequestParams(),
                            genericCallbackListener);
                        pointNumber = 32;
                        break;
                    case GET_MOOD_EVENTS_LIST:
                        Log.e(TAG, "making Get Mood Events List api call ...");
                        ApiManager.getRestApiInterface().getMoodEventsList(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "20140908",
                            genericCallbackListener);
                        pointNumber = 33;
                        break;
                    case GET_MOOD_EVENT:
                        Log.e(TAG, "making Get Mood Event api call ...");
                        ApiManager.getRestApiInterface().getMoodEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 34;
                        break;
                    case CREATE_MOOD_EVENT:
                        Log.e(TAG, "making Create Mood Event api call ...");
                        ApiManager.getRestApiInterface().createMoodEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getCreateMoodEventRequestParams(),
                            genericCallbackListener);
                        pointNumber = 35;
                        break;
                    case DELETE_MOOD_EVENT:
                        Log.e(TAG, "making Delete Mood Event api call ...");
                        ApiManager.getRestApiInterface().deleteMoodEvent(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            "O-IjYEIQFz6dU0FWTA0U-w", //hardcoded value, should be dynamic
                            genericCallbackListener);
                        pointNumber = 36;
                        break;
                    case GET_REFRESH_TOKEN:
                        Log.e(TAG, "making Get Refresh Token api call ...");
                        if (mClientSecret != null) {
                            ApiManager.getRestApiInterface().getRefreshToken(
                                UpPlatformSdkConstants.API_VERSION_STRING,
                                mClientSecret,
                                genericCallbackListener);
                        } else {
                            Log.e(TAG, "client secret is null, so api call is aborted..");
                        }
                        pointNumber = 37;
                        break;
                    case GET_SETTINGS:
                        Log.e(TAG, "making Get User Settings api call ...");
                        ApiManager.getRestApiInterface().getUserSettings(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            genericCallbackListener);
                        pointNumber = 38;
                        break;
                    case GET_TIME_ZONE:
                        Log.e(TAG, "making Get Time Zone api call ...");
                        ApiManager.getRestApiInterface().getTimeZone(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getTimeZoneRequestParams(),
                            genericCallbackListener);
                        pointNumber = 39;
                        break;
                    case GET_TRENDS:
                        Log.e(TAG,  "making Get Trends api call ...");
                        ApiManager.getRestApiInterface().getTrends(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            getTrendsRequestParams(),
                            genericCallbackListener);
                        pointNumber = 40;
                        break;
                    case GET_USER:
                        Log.e(TAG, "making Get User api call ...");
                        ApiManager.getRestApiInterface().getUser(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            genericCallbackListener);
                        pointNumber = 41;
                        break;
                    case GET_USERS_FRIENDS:
                        Log.e(TAG, "making Get Users Friends api call ...");
                        ApiManager.getRestApiInterface().getUsersFriends(
                            UpPlatformSdkConstants.API_VERSION_STRING,
                            genericCallbackListener);
                        pointNumber = 42;
                        break;
                    default:
                        Log.e(TAG, "api endpoint not yet defined, position of clicked is:" + position );
                        pointNumber = 43;
                        break;
                }
            }
        };
    }

    //TODO the callbacks are not yet backed by data model, but will get json response,
    //TODO which for now is logged to console
    private Callback genericCallbackListener = new Callback<Object>() {
        @SuppressLint("NewApi")
		@Override
        public void success(Object o, Response response) {
            Log.e(TAG,  "api call successful, json output: " + o.toString());
//            Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
            String moveliststr = o.toString();
            
            //返回运动全体的处理
            if(pointNumber == 5){
            	String[] movelist = moveliststr.split("\\{|\\}|\"|\"|:|,|\\[|\\]|=| ");
            	String next = "";
            	
//            	for(int i = 0; i < movelist.length; i++){
//            		if(movelist[i].equals("今日"))
//            			Log.e(TAG, movelist[i]);
//            		else
//            			System.out.println(movelist[i]);
//            	}
            	
            	int flag = 0;							//用来判断当前读取到哪个标签
            	
            	Moves_list ml = new Moves_list();
            	Moves_items mi = new Moves_items();
            	Moves_details md = new Moves_details();
            	for(int i = 0; i < movelist.length; i++){
            		if(movelist[i].equals("xid")){
            			Log.e(TAG, "event_xid:" + movelist[i + 1]);
            		}
            		
            		if(flag == 0){
	            		mi = new Moves_items();
	            		md = new Moves_details();
            		}
            		if(movelist[i].equals("今日")){
            			event_xid = movelist[i - 3];
            			Log.e(TAG, event_xid);
            		}
//            		if(movelist[i].equals("xid")){
//            			event_xid = movelist[i - 3];
//            			Log.e(TAG, event_xid);
//            		}
            		if(movelist[i].equals("snapshot_image") && movelist[i - 3].equals("date") ){
            			flag ++;
            			String []temp = movelist[i - 2].split("\\+");
            			
            			//将表示日期的字符串中去除科学计数法的+
            			String format_date = "";
            			for(int j = 0; j < temp.length; j++)
            				format_date += temp[j];
            			
            			BigDecimal date = new BigDecimal(format_date);     
//            			Log.e(TAG, "date:" +  date.toPlainString());
            			
            			//将日期存入items中
            			mi.setDate(Integer.parseInt(date.toPlainString()));
            		}
            		else if(movelist[i].equals("type") && movelist[i - 3].equals("date") ){
            			flag ++;
            			String []temp = movelist[i - 2].split("\\+");
            			
            			//将表示日期的字符串中去除科学计数法的+
            			String format_date = "";
            			for(int j = 0; j < temp.length; j++)
            				format_date += temp[j];
            			
            			BigDecimal date = new BigDecimal(format_date);     
//            			Log.e(TAG, "date:" +  date.toPlainString());
            			
            			//将日期存入items中
            			mi.setDate(Integer.parseInt(date.toPlainString()));
            		}
            		if(movelist[i].equals("title")){
            			flag ++;
            			String steps = null;
            			if(movelist[i + 1].equals("今日")){
            				steps = movelist[i + 2] + movelist[i + 3];
            			}
            			else if(movelist[i + 2].equals("步"))
            				steps = movelist[i + 1];
            			else
            				steps = movelist[i + 1] + movelist[i + 2];
//            			Log.e(TAG, "steps:" +  steps);
            			
            			//将步数存入details中
            			md.setSteps(Integer.parseInt(steps));
            		}
            		if(movelist[i].equals("tz")){
            			flag ++;
            			String distance = movelist[i - 2];
//            			Log.e(TAG, "distance:" +  distance);
            			
            			//将距离存入details中
            			md.setDistance(Double.parseDouble(distance));
            		}
            		
            		if(movelist[i].equals("next")){
            			next += movelist[i + 1];
            			next += "=";
            			next += movelist[i + 2];
            			next += "=";
            			next += movelist[i + 3];
            		}
            		
            		if(flag == 3){
            			mi.setDetails(md);
            			ml.getData().getItems().add(mi);
            			flag = 0;
            		}
            	}
            	
            	Intent intent = new Intent(UpApiListActivity.this, MovesListActivity.class); 
            	Bundle bundle = new Bundle();
            	bundle.putSerializable("Moveslist", ml);
            	intent.putExtras(bundle);
                startActivity(intent); 
            	
//            	String result="";//访问返回结果
//            	BufferedReader read=null;//读取访问结果
//            	//创建url
//            	String url = "https://jawbone.com";
//            	url += next;
//            	System.out.println(url);
//                URL realurl;
//				try {
//					realurl = new URL(url);
//					//打开连接
//	                URLConnection connection=realurl.openConnection();
//	                 // 设置通用的请求属性
//	                connection.setRequestProperty("accept", "*/*");
//	                connection.setRequestProperty("connection", "Keep-Alive");
//	                connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//	                //建立连接
//	                connection.connect();
//	                // 获取所有响应头字段
//	                Map<String, List<String>> map = connection.getHeaderFields();
//	                // 遍历所有的响应头字段，获取到cookies等
//	                for (String key : map.keySet()) {
//	                	System.out.println(key + "--->" + map.get(key));
//	                }
//	                // 定义 BufferedReader输入流来读取URL的响应
//	                read = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
//	                String line;//循环读取
//	                while ((line = read.readLine()) != null) {
//	                	result += line;
//	                }
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
            }
            if(pointNumber == 6){
        		String[] move = moveliststr.split("\\{|\\}|\"|\"|:|,|\\[|\\]|=| ");
            	
            	int flag = 0;							//用来判断当前读取到哪个标签
            	
            	Move_event me = new Move_event();
            	Move_data mda = new Move_data();
            	Move_details mde = new Move_details();
            	for(int i = 0; i < move.length; i++){
            		if(flag == 0){
	            		mde = new Move_details();
            		}
            		if(move[i].equals("snapshot_image") && move[i - 3].equals("date") ){
            			flag ++;
            			String []temp = move[i - 2].split("\\+");
            			
            			//将表示日期的字符串中去除科学计数法的+
            			String format_date = "";
            			for(int j = 0; j < temp.length; j++)
            				format_date += temp[j];
            			
            			BigDecimal date = new BigDecimal(format_date);     
//            			Log.e(TAG, "date:" +  date.toPlainString());
            			
            			//将日期存入items中
            			mda.setDate(Integer.parseInt(date.toPlainString()));
            		}
            		else if(move[i].equals("type") && move[i - 3].equals("date") ){
            			flag ++;
            			String []temp = move[i - 2].split("\\+");
            			
            			//将表示日期的字符串中去除科学计数法的+
            			String format_date = "";
            			for(int j = 0; j < temp.length; j++)
            				format_date += temp[j];
            			
            			BigDecimal date = new BigDecimal(format_date);     
//            			Log.e(TAG, "date:" +  date.toPlainString());
            			
            			//将日期存入items中
            			mda.setDate(Integer.parseInt(date.toPlainString()));
            		}
            		if(move[i].equals("title")){
            			flag ++;
            			String steps = null;
            			if(move[i + 1].equals("今日")){
            				steps = move[i + 2] + move[i + 3];
            			}
            			else
            				steps = move[i + 1] + move[i + 2];
//            			Log.e(TAG, "steps:" +  steps);
            			
            			//将步数存入details中
            			mde.setSteps(Integer.parseInt(steps));
            		}
            		if(move[i].equals("tz")){
            			flag ++;
            			String distance = move[i - 2];
//            			Log.e(TAG, "distance:" +  distance);
            			
            			//将距离存入details中
            			mde.setDistance(Double.parseDouble(distance));
            		}
            		
            		if(flag == 3){
            			mda.setDetails(mde);
            			me.setData(mda);
            			flag = 0;
            		}
            	}
            	
            	me.getData().setXid(event_xid);
            	
            	Intent intent = new Intent(UpApiListActivity.this, MoveEventActivity.class); 
            	Bundle bundle = new Bundle();
            	bundle.putSerializable("Moveevent", me);
            	intent.putExtras(bundle);
                startActivity(intent); 
            	
        	}
            
            if(pointNumber == 8){
            	String[] move_ticksstr = moveliststr.split("\\{|\\}|\"|\"|:|,|\\[|\\]|=| ");
            	for(int i = 0; i < move_ticksstr.length; i++){
            		System.out.println(move_ticksstr[i]);
            	}
            	
            	int flag = 0;							//用来判断当前读取到哪个标签
            	int size = 0;
            	int time_r = 0;
            	
            	Move_ticks_items mti = new Move_ticks_items();
            	Move_ticks_data mtd = new Move_ticks_data();
            	Move_ticks mt = new Move_ticks();
            	for(int i = 0; i < move_ticksstr.length; i++){
            		if(flag == 0){
            			mti = new Move_ticks_items();
            		}
            		if(move_ticksstr[i].equals("distance")){
            			flag ++;
            			
            			Log.e(TAG, "distance:" +  move_ticksstr[i + 1]);
            			mti.setDistence(Double.parseDouble(move_ticksstr[i + 1]));
            		}
            		else if(move_ticksstr[i].equals("calories")){
            			flag ++;
            			
            			DecimalFormat df  = new DecimalFormat("###.0000"); 
            			
            			Log.e(TAG, "calories:" +  move_ticksstr[i + 1]);
            			mti.setCalories(Double.parseDouble(df.format(Double.parseDouble(move_ticksstr[i + 1]))));
            		}
            		else if(move_ticksstr[i].equals("active_time")){
            			flag ++;
            			
            			Log.e(TAG, "active_time::" +  move_ticksstr[i + 1]);
            			mti.setActive_time(Double.parseDouble(move_ticksstr[i + 1]));
            		}
            		else if(move_ticksstr[i].equals("steps")){
            			flag ++;
            			
            			Log.e(TAG, "steps:" +  move_ticksstr[i + 1]);
            			mti.setSteps(Double.parseDouble(move_ticksstr[i + 1]));
            		}
            		else if(move_ticksstr[i].equals("time")){
            			if(time_r != 0){
            				String []temp = move_ticksstr[i + 1].split("\\+");
                			
                			//将表示日期的字符串中去除科学计数法的+
                			String format_date = "";
                			for(int j = 0; j < temp.length; j++)
                				format_date += temp[j];
                			
                			BigDecimal time_bg = new BigDecimal(format_date);  
                			
            				String time_new = TimeStamp2Date(time_bg.toPlainString());
            				Log.e(TAG, "time:" +  time_new);
            				mti.setTime(time_new);
            			}
            			time_r ++;
            		}
            		
            		if(flag == 5){
            			mtd.getItems().add(mti);
            			size ++;
            			mtd.setSize(size);
            			flag = 0;
            		}
            	}
            	
            	Log.e(TAG, "size::" +  mtd.getSize());
            	mt.setData(mtd);
//            	me.getData().setXid(event_xid);
//            	
            	Intent intent = new Intent(UpApiListActivity.this, MoveTicksActivity.class); 
            	Bundle bundle = new Bundle();
            	bundle.putSerializable("Moveticks", mt);
            	intent.putExtras(bundle);
                startActivity(intent); 
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(TAG,  "api call failed, error message: " + retrofitError.getMessage());
            Toast.makeText(getApplicationContext(), retrofitError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    /**
     * The following are some handy methods to wrap up the parameters posted
     * to server. Retrofit allows them to be sent as Map<String, Object> and
     * we use that.
     */
    private static HashMap<String, Object> getTrendsRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        queryHashMap.put("end_date", "<insert-date>");
//        queryHashMap.put("bucket_size", 50);
//        queryHashMap.put("num_buckets", 10);

        return queryHashMap;
    }

    private static HashMap<String, Integer> getTimeZoneRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("timestamp", "<insert-time>");

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateMoodEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        queryHashMap.put("title", "<insert-title>");
//        queryHashMap.put("sub_tye", 1);
//        queryHashMap.put("time_created", 1409967653);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateOrUpdateUsersGoalsRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        float placeHolder = new Float(0);
//        queryHashMap.put("move_steps", "<insert-steps>");
//        queryHashMap.put("sleep_total", "<insert-sleep>");
//        queryHashMap.put("body_weight", placeHolder);
//        queryHashMap.put("body_weight_intent", "<insert-intent>");

        return queryHashMap;
    }

    private static HashMap<String, Integer> getBandEventsRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("created_after", "<insert-time>");

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateBodyEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        float placeHolder = new Float(0);
//        queryHashMap.put("title", "<insert-title>");
//        queryHashMap.put("weight", placeHolder);
//        queryHashMap.put("body_fat", placeHolder);
//        queryHashMap.put("lean_mass", placeHolder);
//        queryHashMap.put("bmi", placeHolder);
//        queryHashMap.put("note", "<insert-note>");
//        queryHashMap.put("time_created", 1409967653);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);

        return queryHashMap;
    }

    private static HashMap<String, Integer> getBodyEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");
//        queryHashMap.put("limit", "<insert-limit>");

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateSleepEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        queryHashMap.put("time_created", 1);
//        queryHashMap.put("time_completed", 1);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);

        queryHashMap.put("time_created", 1409967653);
        queryHashMap.put("time_completed", 1409967655);
        return queryHashMap;
    }

    private static HashMap<String, Integer> getSleepEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateOrUpdateWorkoutEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        File photo = new File(""); //path to image file
//        TypedFile typedFile = new TypedFile("application/octet-stream", photo);
//        float placeHolder = new Float(0);
//        queryHashMap.put("sub_type", 1);
//        queryHashMap.put("image_url", URI.create("<insert-uri>"));
//        queryHashMap.put("time_created", 1);
//        queryHashMap.put("time_completed", 1);
//        queryHashMap.put("place_lat", placeHolder);
//        queryHashMap.put("place_lon", placeHolder);
//        queryHashMap.put("place_acc", placeHolder);
//        queryHashMap.put("place_name", null);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);
//        queryHashMap.put("calories", 100);
//        queryHashMap.put("distance", 700);
//        queryHashMap.put("intensity", 5);

        queryHashMap.put("sub_type", 1);
        return queryHashMap;
    }

    private static HashMap<String, Integer> getWorkoutEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");
//        queryHashMap.put("limit", "<insert-limit>");

        return queryHashMap;
    }

    private HashMap<String, Object> getCreateOrUpdateCustomEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

        //uncomment to add as needed parameters
//        Object jsonObject = null;
//        float placeHolder = new Float(0);
//        queryHashMap.put("title", "<insert-title>");
//        queryHashMap.put("verb", "<insert-verb>");
//        queryHashMap.put("attributes", jsonObject);
//        queryHashMap.put("image_url", URI.create("<insert-uri>"));
//        queryHashMap.put("place_lat", placeHolder);
//        queryHashMap.put("place_lon", placeHolder);
//        queryHashMap.put("place_acc", placeHolder);
//        queryHashMap.put("place_name", null);
//        queryHashMap.put("time_created", 1);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);

        return queryHashMap;
    }

    private static HashMap<String, Integer> getCustomEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");
//        queryHashMap.put("limit", "<insert-limit>");

        return queryHashMap;
    }

    private static HashMap<String, Integer> getMoveEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");

        return queryHashMap;
    }

    private static HashMap<String, Object> getCreateOrUpdateMealEventRequestParams() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

//        //uncomment to add as needed parameters
//        File photo = new File(""); //path to image file
//        TypedFile typedFile = new TypedFile("application/octet-stream", photo);
//        float placeHolder = new Float(0);
//        queryHashMap.put("note", "<insert-title>");
//        queryHashMap.put("sub_type", 1);
//        queryHashMap.put("image_url", URI.create("<insert-uri>"));
//        queryHashMap.put("photo", typedFile);
//        queryHashMap.put("place_lat", placeHolder);
//        queryHashMap.put("place_lon", placeHolder);
//        queryHashMap.put("place_acc", placeHolder);
//        queryHashMap.put("time_created", 1);
//        queryHashMap.put("place_name", null);
//        queryHashMap.put("tz", null);
//        queryHashMap.put("share", false);

        queryHashMap.put("note", "Create Meal 1");
        queryHashMap.put("sub_type", 3);
        return queryHashMap;
    }

    private static HashMap<String, Integer> getMealEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();

        //uncomment to add as needed parameters
//        queryHashMap.put("date", "<insert-date>");
//        queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", "<insert-time>");
//        queryHashMap.put("end_time", "<insert-time>");
//        queryHashMap.put("updated_after", "<insert-time>");

        return queryHashMap;
    }
    
    public String TimeStamp2Date(String timestampString){  
    	  Long timestamp = Long.parseLong(timestampString)*1000;  
    	  String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(timestamp));  
    	  return date;  
    } 
}
