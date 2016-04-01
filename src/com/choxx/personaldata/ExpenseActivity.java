package com.choxx.personaldata;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends Activity implements OnItemLongClickListener {

	EditText etTitle,etAmount;
	ImageButton ibAdd,ibSaveLog;
	ListView lvExpense;
	TextView tvTotal;
	Date date;
	SQLiteDatabase db;
	float sum= 0;
	ArrayList<String> pTitle,pAmount,pDate;
	String title,amount,currDate,delRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense);
		etTitle=(EditText) findViewById(R.id.etTitle);
		etTitle.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
		etAmount=(EditText) findViewById(R.id.etAmount);
		etAmount.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
		ibAdd=(ImageButton) findViewById(R.id.ibAdd);
		ibSaveLog=(ImageButton) findViewById(R.id.ibSaveLog);
		lvExpense=(ListView) findViewById(R.id.lvExpense);
		lvExpense.setBackgroundResource(R.drawable.bn_bg_wide);
		tvTotal=(TextView) findViewById(R.id.tvTotal);
		pTitle=new ArrayList<String>();
		pAmount=new ArrayList<String>();
		pDate=new ArrayList<String>();
		db=openOrCreateDatabase("expense", MODE_PRIVATE, null);
		
		db.execSQL("create table if not exists myexpenses(title char(150),amt char(6),date char(20))");
		
		update();
		ibAdd.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				title=etTitle.getText().toString();
				amount=etAmount.getText().toString();
				if(title.length()==0 || amount.length()==0)
				{
					AlertDialog.Builder ob=new AlertDialog.Builder(ExpenseActivity.this);
					ob.setTitle("Error");
					ob.setMessage("Invalid Entry!! Enter Again..");
					
					ob.show();
				}
				else
				{
					
					Date cal = (Date) Calendar.getInstance().getTime();
				    currDate = cal.toLocaleString();
					
					//Toast.makeText(getApplicationContext(), dt, 2000).show();
					
					db.execSQL("insert into myexpenses values('"+title+"','"+amount+"','"+ currDate+"')");
					Toast.makeText(getApplicationContext(), "Saved Successfully!!", 2000).show();
					
					etTitle.setText(null);
					etAmount.setText(null);
					
					//sum=0;
					update();
					
				}
				
			}
		});
		
		lvExpense.setOnItemLongClickListener(this);
		
		ibSaveLog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				export();
				TypesActivity obj =new TypesActivity();
				try{
					obj.exportDataBase();
				}catch(Exception e){
				}
				
				//startActivity(new Intent(getApplicationContext(),TypesActivity.class));
			}
		});
		
	}//onCreate closed

	public void update()
	{
		pTitle.clear();
		pAmount.clear();
		pDate.clear();
		sum=0;
		Cursor c=db.rawQuery("select * from myexpenses", null);
		while(c.moveToNext())
		{
			pTitle.add(c.getString(0));
			pAmount.add("Rs. "+c.getString(1));
			pDate.add(c.getString(2));
			sum=sum+c.getFloat(1);
			
		}
		tvTotal.setText("Total Expense: "+sum);
		lvExpense.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, pTitle));
	}
	
	
	private class MyAdapter extends ArrayAdapter
	{

		public MyAdapter(Context context, int resource,ArrayList<String> list) {
			super(context, resource, list);
			 
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
			
			LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = li.inflate(R.layout.expense_list,parent,false);
			
			TextView tv  = (TextView)row.findViewById(R.id.desc);
			TextView tvd = (TextView)row.findViewById(R.id.amt);
			TextView d = (TextView)row.findViewById(R.id.tvDate);
			tv.setText(pTitle.get(position));
			tvd.setText(pAmount.get(position));
			d.setText(pDate.get(position));
			return row;
		}//end of getView
	}	// class myAdapter closed
	
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
			
		
		
		}
		
		
		return super.onContextItemSelected(item);
	}
	
	
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(),TypesActivity.class));
		finish();
	}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View v, final int pos,long arg3) 
	{
		
	
		
		AlertDialog.Builder delet=new AlertDialog.Builder(ExpenseActivity.this);
		delet.setMessage("Delete this Record??");
		delet.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			int i=0;
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Cursor c=db.rawQuery("select * from myexpenses", null);
				while(c.moveToNext())
				{
					
					if(i==pos)
					{
						delRecord=c.getString(0);
						sum=sum-c.getFloat(1);
						db.execSQL("delete from myexpenses where title='"+delRecord+"'");
						Toast.makeText(getApplicationContext(), delRecord+" deleted successfully..", 2000).show();
						update();
						break;
					}
					i++;
				}
			}
		});
		
		delet.show();
		return false;
	}
	
	public void export()
	{
		String logInfo="Title,"+"\t\t\t Date,"+"\t\t\t\t\t\tAmount\n--------------------------------------------------------------------\n";
		File exp=new File(Environment.getExternalStorageDirectory()+"/MyExpense");
		exp.mkdir();
		String logName[]={"January","februARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
		int month= Calendar.getInstance().get(Calendar.MONTH);
		int year=  Calendar.getInstance().get(Calendar.YEAR);
		File l=new File(exp, logName[month]+" "+year+".csv");
		Cursor c=db.rawQuery("select * from myexpenses", null);
		while(c.moveToNext())
		{
			logInfo=logInfo+c.getString(0)+",\t\t\t   "+c.getString(2)+",\t\t   Rs. "+c.getString(1)+"\n";
		}
		
		try {
			FileOutputStream fos=new FileOutputStream(l);
			fos.write(logInfo.getBytes());
			fos.flush();
			fos.close();
			Toast.makeText(this, "Log saved", 1000).show();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e+"", 1000).show();
		}
		
	}
	
}// class closed
