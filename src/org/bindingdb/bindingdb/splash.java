package org.bindingdb.bindingdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.spplash_screen);
		
		//**************splash screen
		
		Thread splash_screen = new Thread(){
			
			public void run(){
				try{
					sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
					finish();
				}
			}
		};
		splash_screen.start();
		//**************splash ends

	}
}
