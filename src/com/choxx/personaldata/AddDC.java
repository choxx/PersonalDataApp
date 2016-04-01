package com.choxx.personaldata;

import java.io.File;
import java.io.FileOutputStream;




import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDC extends Activity {

	EditText etCardName,etCardNum,etExpiry,etCvv,et3dPin;
	File sd,dcFolder;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dc);
		etCardName=(EditText) findViewById(R.id.etCardName);
		etCardNum=(EditText) findViewById(R.id.etCardNum);
		etExpiry=(EditText) findViewById(R.id.etExpiry);
		etCvv=(EditText) findViewById(R.id.etCvv);
		et3dPin=(EditText) findViewById(R.id.et3dPin);
	
		db=openOrCreateDatabase("expense", MODE_PRIVATE, null);
		sd=Environment.getExternalStorageDirectory();
		
		/*dcFolder=new File(sd, "Debit Cards");
		dcFolder.mkdir();
		if(!dcFolder.exists())
		{
			dcFolder.mkdir();
		}
		*/
		
		
	}
	
	public void save(View v)
	{
		
		String cname,cnum,cexp,cvv,c3d;
		cname=etCardName.getText().toString().trim();
		cnum=etCardNum.getText().toString();
		cexp=etExpiry.getText().toString().trim();
		cvv=etCvv.getText().toString().trim();
		c3d=et3dPin.getText().toString().trim();
		db.execSQL("create table if not exists debitcards(cname char(30),cnum char(20),cexp char(20),cvv char(4),c3d char(30))");
		if(cname.length()==0 || cnum.length()==0 || cexp.length()==0 || cvv.length()==0 || c3d.length()==0)
		{
			Toast.makeText(getApplicationContext(), "Fields cannot be empty!!", 2000).show();
		}else{
		db.execSQL("insert into debitcards values('"+cname+"','"+cnum+"','"+ cexp+"','"+cvv+"','"+c3d+"')");
		
		
		Toast.makeText(getApplicationContext(), "Saved Successfully!!", 2000).show();
		finish();
		startActivity(new Intent(this,DebitCard.class));
		}
		/*
		String temp= "Name====>  "+cname+"\n"+cnum+"\n"+cexp+"\n"+cvv+"\n"+c3d;
		
		try {
			FileOutputStream fos=new FileOutputStream(sd.getAbsolutePath()+"/.Personal Data/Debit Cards/"+cname+".choxx");
			
			fos.write(temp.getBytes());
			
			fos.flush();
			
			fos.close();
			
			Toast.makeText(this, "Saved Successfully!!", 2000).show();
			
			Intent i=new Intent(this,DebitCard.class);
			startActivity(i);
			finish();
		} catch (Exception e) {
			
			Toast.makeText(this, ""+e, 2000).show();
			finish();
		}
*/
		
	}

	

}
