package com.choxx.personaldata;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	File sd,fldr,dbcards;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sd=Environment.getExternalStorageDirectory();
	
		fldr = new File(sd,".Personal Data");
		fldr.mkdir();
		if(!fldr.exists())
		{
			Toast.makeText(this, "Folder created", 1000).show();
			fldr.mkdir();	
		}
		
		sp=getSharedPreferences("personal_data", MODE_PRIVATE);
		
		if(!sp.getBoolean("created", false))
		{
			startActivity(new Intent(this,Registration.class));
			finish();
		}
		else
		{
			
		startActivity(new Intent(this,Login.class));
		finish();
		}
	}

	

}
