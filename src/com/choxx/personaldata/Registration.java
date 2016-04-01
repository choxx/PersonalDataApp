package com.choxx.personaldata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity {

	EditText etGmailId,etPassword;
	Button bSaveReg;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		etGmailId=(EditText) findViewById(R.id.etGmailId);
		etPassword=(EditText) findViewById(R.id.etPassword);
		bSaveReg=(Button) findViewById(R.id.bSaveReg);
		sp=getSharedPreferences("personal_data", MODE_PRIVATE);
		bSaveReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String id,pass;
				id=etGmailId.getText().toString();
				pass=etPassword.getText().toString();
				SharedPreferences.Editor ed=sp.edit();
				ed.putString("id", id);
				ed.putString("pass", pass);
				ed.putBoolean("created", true);
				ed.commit();
				Toast.makeText(getApplicationContext(), "Registration Successfull..", 2000).show();
				startActivity(new Intent(getApplicationContext(),TypesActivity.class));
				finish();
			}
		});
	
	}// onCreate closed 

	

}
