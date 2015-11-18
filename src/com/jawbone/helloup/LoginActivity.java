package com.jawbone.helloup;

import com.jawbone.upplatformsdk.oauth.OauthWebViewActivity;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        
        Button bt = (Button)findViewById(R.id.signin_button);  
        
        bt.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, HelloUpActivity.class);
		        startActivity(intent);   
			}  
			
        });
    }
}