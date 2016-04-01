package com.choxx.personaldata;

import java.io.File;
import java.io.FileInputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class NetBanking extends Activity {

	File sd,nbFolder;
	String path,arr[],tmp="";
	ImageButton addNewNb;
	TextView tvDetailsNb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_banking);
		addNewNb=(ImageButton) findViewById(R.id.addNewNb);
		tvDetailsNb=(TextView) findViewById(R.id.tvDetailsNb);
		sd=Environment.getExternalStorageDirectory();
		path=sd+"/.Personal Data/Net Banking";
		nbFolder=new File(path);
		nbFolder.mkdir();
		
		addNewNb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				startActivity(new Intent(getApplicationContext(),AddNB.class));
				
			}
		});
		
	}
	
	
	public void show(View v)
	{
		

		arr=nbFolder.list();
		AlertDialog.Builder ob=new AlertDialog.Builder(this);
		ob.setTitle("Select a file to open:");
		ob.setItems(arr, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				try {
					FileInputStream fis=new FileInputStream(nbFolder.getAbsolutePath()+"/"+arr[which]);
					int a=0;
					
					while((a=fis.read())>=1)
					{
						tmp+=(char)a;
					}
					tvDetailsNb.setText(tmp);
					
					fis.close();
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		ob.show();
	}

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	startActivity(new Intent(getApplicationContext(),TypesActivity.class));
	finish();
}
	
}
