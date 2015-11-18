/**
 * @author Omer Muhammed
 * Copyright 2014 (c) Jawbone. All rights reserved.
 *
 */
package com.jawbone.helloup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.jawbone.upplatformsdk.api.ApiManager;
import com.jawbone.upplatformsdk.api.response.OauthAccessTokenResponse;
import com.jawbone.upplatformsdk.oauth.OauthUtils;
import com.jawbone.upplatformsdk.oauth.OauthWebViewActivity;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main activity of the Hello Up test app, it makes the OAuth API
 * call and obtains the access token.
 */
public class HelloUpActivity extends Activity {

    private static final String TAG = HelloUpActivity.class.getSimpleName();

    // These are obtained after registering on Jawbone Developer Portal
    // Credentials used here are created for "Test-App1"
    private static final String CLIENT_ID = "_W1Vw3ksfpQ";
    private static final String CLIENT_SECRET = "ed46a27e5d3441317607bac4ea99de9617790637";

    // This has to be identical to the OAuth redirect url setup in Jawbone Developer Portal
    private static final String OAUTH_CALLBACK_URL = "http://localhost/helloup?";

    private List<UpPlatformSdkConstants.UpPlatformAuthScope> authScope;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.hello_up);

        // Set required levels of permissions here, for demonstration purpose
        // we are requesting all permissions
        authScope  = new ArrayList<UpPlatformSdkConstants.UpPlatformAuthScope>();
        authScope.add(UpPlatformSdkConstants.UpPlatformAuthScope.ALL);

		//获得BluetoothAdapter对象，该API是android 2.0开始支持的  
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();  
        //adapter不等于null，说明本机有蓝牙设备  
        if(adapter != null){  
            System.out.println("本机有蓝牙设备！");  
            //如果蓝牙设备未开启  
            if(!adapter.isEnabled()){  
            	adapter.enable();
            }  
//            //获得已配对的远程蓝牙设备的集合  
//            Set<BluetoothDevice> devices = adapter.getBondedDevices();  
//            if(devices.size()>0){  
//                for(Iterator<BluetoothDevice> it = devices.iterator();it.hasNext();){  
//                    BluetoothDevice device = (BluetoothDevice)it.next();  
//                    //打印出远程蓝牙设备的物理地址  
//                    System.out.println(device.getAddress());  
//                }  
//            }else{  
//                System.out.println("还没有已配对的远程蓝牙设备！");  
//            }  
        }else{  
            System.out.println("本机没有蓝牙设备！");  
        }  
        
        
        Button oAuthAuthorizeButton = (Button) findViewById(R.id.authorizeButton);
        oAuthAuthorizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = getIntentForWebView();
            startActivityForResult(intent, UpPlatformSdkConstants.JAWBONE_AUTHORIZE_REQUEST_CODE);
            }
        });
        
        
        Intent intent=new Intent();  
        intent.setComponent(new ComponentName("com.jawbone.up", "com.jawbone.up.oobe.LaunchActivity"));  
        startActivity(intent);  
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UpPlatformSdkConstants.JAWBONE_AUTHORIZE_REQUEST_CODE && resultCode == RESULT_OK) {

            String code = data.getStringExtra(UpPlatformSdkConstants.ACCESS_CODE);
            if (code != null) {
                //first clear older accessToken, if it exists..
                ApiManager.getRequestInterceptor().clearAccessToken();

                ApiManager.getRestApiInterface().getAccessToken(
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    accessTokenRequestListener);
            }
        }
   }

    private Callback accessTokenRequestListener = new Callback<OauthAccessTokenResponse>() {
        @Override
        public void success(OauthAccessTokenResponse result, Response response) {

            if (result.access_token != null) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HelloUpActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UpPlatformSdkConstants.UP_PLATFORM_ACCESS_TOKEN, result.access_token);
                editor.putString(UpPlatformSdkConstants.UP_PLATFORM_REFRESH_TOKEN, result.refresh_token);
                editor.commit();

                Intent intent = new Intent(HelloUpActivity.this, UpApiListActivity.class);
                intent.putExtra(UpPlatformSdkConstants.CLIENT_SECRET, CLIENT_SECRET);
                startActivity(intent);

                Log.e(TAG, "accessToken:" + result.access_token);
            } else {
                Log.e(TAG, "accessToken not returned by Oauth call, exiting...");
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(TAG, "failed to get accessToken:" + retrofitError.getMessage());
        }
    };

    private Intent getIntentForWebView() {
        Uri.Builder builder = OauthUtils.setOauthParameters(CLIENT_ID, OAUTH_CALLBACK_URL, authScope);

        Intent intent = new Intent(OauthWebViewActivity.class.getName());
        intent.putExtra(UpPlatformSdkConstants.AUTH_URI, builder.build());
        return intent;
    }
}