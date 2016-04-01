package com.choxx.personaldata;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


import java.util.zip.Inflater;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DebitCard extends Activity{

	ImageButton addNewDebitCard;
	TextView tvDetails;
	File sd,dcFolder;
	String tmp="";
	String path,arr[];
	ArrayList<String> details,dcNames,cardDetails;
	ListView lvDebitCards;
	SQLiteDatabase db;
	Cursor c,cc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debit_card);
		tvDetails=(TextView) findViewById(R.id.tvDetailsNb);
		addNewDebitCard=(ImageButton) findViewById(R.id.addNewNb);
		lvDebitCards=(ListView) findViewById(R.id.lvDebitCards);
		dcNames=new ArrayList<String>();
		details=new ArrayList<String>();
		cardDetails=new ArrayList<String>();
		sd=Environment.getExternalStorageDirectory();
		path=sd+"/.Personal Data/Debit Cards";
		dcFolder=new File(path);
		dcFolder.mkdir();
		db=openOrCreateDatabase("expense", MODE_PRIVATE, null);
		try{
		c=db.rawQuery("select * from debitcards ORDER BY cname", null);
		while(c.moveToNext()){
			dcNames.add(c.getString(0));
		}
		lvDebitCards.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, dcNames));
		}catch(Exception e)
		{
			
		}
		addNewDebitCard.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(DebitCard.this,AddDC.class));
				
			}
		});
		
		lvDebitCards.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) 
			{
				
				c=db.rawQuery("select * from debitcards ORDER BY cname", null);
				int i=0;
				String dcDetails[]={"","","","",""};
				while(c.moveToNext()){
					
					
						dcDetails[0]=c.getString(0);
						dcDetails[1]=c.getString(1);
						dcDetails[2]=c.getString(2);
						dcDetails[3]=c.getString(3);
						dcDetails[4]=c.getString(4);
					 
						i++;
						if(i>pos)
							break;
				}
				Intent ii=new Intent(DebitCard.this,ViewDebitCard.class);
				
				ii.putExtra("debit", dcDetails);
				
				startActivity(ii);
				finish();
				}
		});
		
	
		
		
		/*
		lvDebitCards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), ""+pos, 2000).show();
				String opt[]={"Edit","Delete"};
				AlertDialog.Builder options=new AlertDialog.Builder(getApplicationContext());
				options.setPositiveButton("dvgd", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				*/
				
				
				/*
				options.setItems(opt,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int p) {
						// TODO Auto-generated method stub
						if(p==0){
						
							
						}
						
						if(p==1){
							
						}
					}
				});
				
				options.show();
				return false;
				
			}
		});
		
		*/
	}
	
	

	public void disp(String abc){

		AlertDialog.Builder ob=new AlertDialog.Builder(DebitCard.this);
		ob.setMessage(abc);
		ob.show();
	}
	
	
	private class MyAdapter extends ArrayAdapter
	{

		public MyAdapter(Context context, int resource,ArrayList<String> list) {
			super(context, resource, list);
			 
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
			
			LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = li.inflate(R.layout.dc_list,parent,false);
			
			TextView tv  = (TextView)row.findViewById(R.id.tvDc);
			
			tv.setText(dcNames.get(position));
			return row;
		}//end of getView
	}	// class myAdapter closed
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(),TypesActivity.class));
		finish();
	}



	
}
