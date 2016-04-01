package com.choxx.personaldata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	EditText etLoginPassword;
	TextView tvLoginId;
	Button bLogin;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sp=getSharedPreferences("personal_data", MODE_PRIVATE);
		etLoginPassword=(EditText) findViewById(R.id.etLoginPassword);
		tvLoginId=(TextView) findViewById(R.id.tvLoginId);
		bLogin=(Button) findViewById(R.id.bLogin);
		tvLoginId.setText(sp.getString("id", ""));
		bLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(etLoginPassword.getText().toString().equals(sp.getString("pass", "")))
				{
					startActivity(new Intent(getApplicationContext(),TypesActivity.class));
					finish();
				}
				else
				{
					startActivity(new Intent(getApplicationContext(),TypesActivity.class));
					Toast.makeText(getApplicationContext(), "Wrong Password!!!", 2000).show();
				}
				
			}
		});
	}

	
}
