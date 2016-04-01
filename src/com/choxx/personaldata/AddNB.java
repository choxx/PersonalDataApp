package com.choxx.personaldata;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNB extends Activity {

	EditText etPname,etBranch,etAcc,etUid,etLoginPass,etTranPass,etBank;
	private File sd;
	private File dcFolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_nb);
		etPname=(EditText) findViewById(R.id.etPname);
		etBank=(EditText) findViewById(R.id.etBank);
		etBranch=(EditText) findViewById(R.id.etBranch);
		etAcc=(EditText) findViewById(R.id.etAcc);
		etUid=(EditText) findViewById(R.id.etUid);
		etLoginPass=(EditText) findViewById(R.id.etLoginPass);
		etTranPass=(EditText) findViewById(R.id.etTranPass);
		
		sd=Environment.getExternalStorageDirectory();
		
		/*dcFolder=new File(sd, "Net Banking");
		dcFolder.mkdir();
		if(!dcFolder.exists())
		{
			dcFolder.mkdir();
		}
		*/
		
	}

	public void save(View v)
	{
		
		String pname,bname,brname,acno,uid,login,tran;
		pname=etPname.getText().toString().trim();
		bname="Bank====>  "+etBank.getText().toString();
		brname="Branch====>  "+etBranch.getText().toString().trim();
		acno="A/C No.====>  "+etAcc.getText().toString().trim();
		uid="User ID====>  "+etUid.getText().toString().trim();
		login="Login Password====>  "+etLoginPass.getText().toString().trim();
		tran="Transaction password====>  "+etTranPass.getText().toString().trim();
		String temp= "Name====>  "+pname+"\n"+bname+"\n"+brname+"\n"+acno+"\n"+uid+"\n"+login+"\n"+tran;
		
		try {
			FileOutputStream fos=new FileOutputStream(sd.getAbsolutePath()+"/.Personal Data/Net Banking/"+pname+".choxx");
			
			fos.write(temp.getBytes());
			
			fos.flush();
			
			fos.close();
			
			Toast.makeText(this, "Saved Successfully!!", 2000).show();
			
			Intent i=new Intent(this,NetBanking.class);
			startActivity(i);
			finish();
		} catch (Exception e) {
			
			Toast.makeText(this, ""+e, 2000).show();
			finish();
		}
		
		
	}

}
