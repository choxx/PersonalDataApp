package com.choxx.personaldata;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ViewDebitCard extends Activity {

	EditText etCname,etCnum,etCexpiry,etCvv,etCpassword;
	String details[];
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_debit_card);
		etCname=(EditText) findViewById(R.id.etCname);
		etCnum=(EditText) findViewById(R.id.etCnum);
		etCexpiry=(EditText) findViewById(R.id.etCexpiry);
		etCvv=(EditText) findViewById(R.id.etCvv);
		etCpassword=(EditText) findViewById(R.id.etCpassword);
		Bundle b=getIntent().getExtras();
		details=b.getStringArray("debit");
		db=openOrCreateDatabase("expense", MODE_PRIVATE, null);
		
		etCname.setText(details[0]);
		etCnum.setText(details[1]);
		etCexpiry.setText(details[2]);
		etCvv.setText(details[3]);
		etCpassword.setText(details[4]);
		
		
		etCnum.setShadowLayer(10, 1, -3, Color.RED);;
		etCname.setShadowLayer(10, 1, -3, Color.RED);
		etCexpiry.setShadowLayer(10, 1, -3, Color.RED);
		etCvv.setShadowLayer(10, 1, -3, Color.RED);
		etCpassword.setShadowLayer(10, 1, -3, Color.RED);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_debit_card, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		Cursor c=db.rawQuery("select * from debitcards",null);
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		}
		
		if (id == R.id.save) {
			
			while(c.moveToNext()){
				
				db.execSQL("delete from debitcards where cnum='"+details[1]+"'");
			}
			String update[] = {null,null,null,null,null};
			update[0]=etCname.getText().toString();
			update[1]=etCnum.getText().toString();
			update[2]=etCexpiry.getText().toString();
			update[3]=etCvv.getText().toString();
			update[4]=etCpassword.getText().toString();
			if(update[0].length()==0 || update[1].length()==0 || update[2].length()==0 || update[3].length()==0 || update[4].length()==0)
			{
				Toast.makeText(getApplicationContext(), "Fields cannot be empty!! Enter again..", 2000).show();
			}
			else{
			db.execSQL("insert into debitcards values('"+update[0]+"','"+update[1]+"','"+update[2]+"','"+update[3]+"','"+update[4]+"')");
			Toast.makeText(getApplicationContext(), "Record updated", 2000).show();
			startActivity(new Intent(getApplicationContext(),DebitCard.class));
			finish();
			}
			return true;
		
		}
		if (id == R.id.delete) {
			while(c.moveToNext()){
			String s=c.getString(1);
			db.execSQL("delete from debitcards where cnum='"+details[1]+"'");
			//Toast.makeText(getApplicationContext(), details[1]+"", 2000).show();
			}
			startActivity(new Intent(getApplicationContext(),DebitCard.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Cursor c=db.rawQuery("select * from debitcards", null);
		while(c.moveToNext()){
			
			db.execSQL("delete from debitcards where cnum='"+details[1]+"'");
		}
		String update[] = {null,null,null,null,null};
		update[0]=etCname.getText().toString();
		update[1]=etCnum.getText().toString();
		update[2]=etCexpiry.getText().toString();
		update[3]=etCvv.getText().toString();
		update[4]=etCpassword.getText().toString();
		
		db.execSQL("insert into debitcards values('"+update[0]+"','"+update[1]+"','"+update[2]+"','"+update[3]+"','"+update[4]+"')");
		Toast.makeText(getApplicationContext(), "Record updated", 2000).show();
		startActivity(new Intent(getApplicationContext(),DebitCard.class));
		finish();
		
	}
}
