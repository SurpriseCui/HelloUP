package com.jawbone.helloup;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.jawbone.upplatformsdk.api.ApiManager;
import com.jawbone.upplatformsdk.datamodel.Move_data;
import com.jawbone.upplatformsdk.datamodel.Move_details;
import com.jawbone.upplatformsdk.datamodel.Move_event;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MoveEventActivity extends Activity {
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	@SuppressWarnings("deprecation")
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	private TableRow pre = null;
	private int pre_steps = 0;
	private double pre_dis = 0;
	private static final String TAG = MoveEventActivity.class.getSimpleName();
	private int state = 0;	//0：静止 	1：运动	2：异常
	private Move_event me;
	
	private Vibrator vibrator;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_move_event);
		
		Intent intent = this.getIntent(); 
		me = (Move_event)intent.getSerializableExtra("Moveevent");
		
		setView(me);
		
		handler.postDelayed(runnable, 60000);
	}
	
	private void setView(Move_event me){
		TableLayout tableLayout = (TableLayout)findViewById(R.id.TableLayout02);
		TextView tv_state = (TextView)findViewById(R.id.State_view);
		
		Button btnOK = (Button)findViewById(R.id.OK_btn);
		btnOK.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				call_timer.cancel();
				vibrator.cancel(); 
				
				TextView tv_state = (TextView)findViewById(R.id.State_view);
				tv_state.setText("状态：静止");
				state = 0;
			}
		});
		
		
		tableLayout.setStretchAllColumns(true);
		
		for(int row = 0; row < 1; row ++){
			TableRow tableRow = new TableRow(MoveEventActivity.this);
			tableRow.setBackgroundColor(Color.rgb(222, 220, 210));
			
			for(int col = 0; col < 3; col ++){
				TextView tv = new TextView(MoveEventActivity.this);
				if(col == 0){
					System.out.println(Integer.toString(me.getData().getDate()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Integer.toString(me.getData().getDate()));
					tableRow.addView(tv);
				}
				else if(col == 1){
					System.out.println(Integer.toString(me.getData().getdetails().getSteps()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Integer.toString(me.getData().getdetails().getSteps()));
					tableRow.addView(tv);
				}
				else if(col == 2){
					System.out.println(Double.toString(me.getData().getdetails().getDistance()));
					tv.setBackgroundResource(R.drawable.shapee);
					tv.setText(Double.toString(me.getData().getdetails().getDistance()));
					tableRow.addView(tv);
				}
			}
			
			if(pre != null)
				tableLayout.removeView(pre);
			
			//与上次结果相同并且当前状态为1
			if((pre_steps == me.getData().getdetails().getSteps()) && 
					(pre_dis == me.getData().getdetails().getDistance()) &&
					(state == 1)){
			    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);  
			    getSystemService(VIBRATOR_SERVICE); //获得一个震动的服务  
			    
			    vibrator.vibrate(30000);				//震动
			    call_timer.start();
			    
			    tv_state.setText("状态：静止");
			    state = 0;
			}
			
			if((pre_steps !=0 ) && (pre_dis != 0) &&
					(pre_steps != me.getData().getdetails().getSteps()) && 
					(pre_dis != me.getData().getdetails().getDistance())){
				tv_state.setText("状态：运动");
				state = 1;
			}
			
			tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
			
			//记录上一次的值
			pre = tableRow;
			pre_steps = me.getData().getdetails().getSteps();
			pre_dis = me.getData().getdetails().getDistance();
		}
	}
	
	private CountDownTimer call_timer = new CountDownTimer(30 * 1000, 1000){    
        @Override    
        public void onTick(long millisUntilFinished) {    
        	
        }    
           
        @Override    
        public void onFinish() {    
        	TextView tv_state = (TextView)findViewById(R.id.State_view);
        	tv_state.setText("状态：异常");
        	state = 2;
        	
		    // 获取电话号码
		    String mobile = "10086";
		    // 生成呼叫意图
		    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mobile));
		    // 开始呼叫
		    startActivity(intent);
        }    
     };
	
	private Handler handler=new Handler();
	Runnable runnable=new Runnable() {
	    @SuppressLint("SimpleDateFormat")
		@SuppressWarnings("unchecked")
		@Override
	    public void run() {
	    	// TODO Auto-generated method stub
			System.out.println("making Get Move Event api call ...");
            ApiManager.getRestApiInterface().getMoveEvent(
                UpPlatformSdkConstants.API_VERSION_STRING,
                me.getData().getXid(), //hardcoded value, should be dynamic
                genericCallbackListener);
            
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
            String date = sDateFormat.format(new java.util.Date());  
            
            Log.e(TAG, date);
            
	        handler.postDelayed(this, 60000);
	    }
	};
	
	@SuppressWarnings("rawtypes")
	private Callback genericCallbackListener = new Callback<Object>() {
        @SuppressLint("NewApi")
		@Override
        public void success(Object o, Response response) {
        	System.out.println("api call successful, json output: " + o.toString());
        	
            String moveliststr = o.toString();

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
            			//将步数存入details中
            			mde.setSteps(Integer.parseInt(steps));
            		}
            		if(move[i].equals("tz")){
            			flag ++;
            			String distance = move[i - 2];
            			//将距离存入details中
            			mde.setDistance(Double.parseDouble(distance));
            			
            		}
            		
            		if(flag == 3){
            			mda.setDetails(mde);
            			me.setData(mda);
            			flag = 0;
            		}
            	}
            	setView(me);
            	
        }
        @Override
        public void failure(RetrofitError retrofitError) {
        	System.out.println("api call failed, error message: " + retrofitError.getMessage());
            Toast.makeText(getApplicationContext(), retrofitError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    
    protected void onDestroy() {
        System.out.println("first activity: onDestroy()");
        handler.removeCallbacks(runnable); 
        super.onDestroy();
    }
}
