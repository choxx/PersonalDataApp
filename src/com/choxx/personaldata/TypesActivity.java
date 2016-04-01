package com.choxx.personaldata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;

import android.R.string;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TypesActivity extends Activity implements OnCheckedChangeListener {

	
	RadioGroup rgTypes;
	Button bExportDB,bImportDB;
	File sdCardPath=new File(Environment.getExternalStorageDirectory()+"/MyExpense");
    File data = Environment.getDataDirectory();
    String dbList[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_types);
		bExportDB=(Button) findViewById(R.id.bExportDB);
		bImportDB=(Button) findViewById(R.id.bImportBD);
		rgTypes=(RadioGroup) findViewById(R.id.rgTypes);
		rgTypes.clearCheck();
		dbList=sdCardPath.list();
		
		rgTypes.setOnCheckedChangeListener(this);
		bExportDB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ob= new AlertDialog.Builder(TypesActivity.this);
				ob.setMessage("Export DataBase to sdcard??");
				ob.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						exportDataBase();
					}
				});
				ob.setNegativeButton("No", null);
				ob.show();
				
			}
		});
		
		
		bImportDB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder ob =new AlertDialog.Builder(TypesActivity.this);
				ob.setTitle("Your old Data might be lost. Select DB to import.");
				ob.setItems(dbList, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub
						
						String tempDbName=dbList[item];
						//Toast.makeText(getApplicationContext(), ""+ tempDbName, 2000).show();
						importDataBase(tempDbName);
					}
				});
				ob.show();
         
			}
		});
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId) {
		case R.id.debitCard:
			startActivity(new Intent(this,DebitCard.class));
			finish();
			break;

		case R.id.netBankin:
			startActivity(new Intent(this,NetBanking.class));
			finish();
			break;
		
		case R.id.eId:
			startActivity(new Intent(this,EmailId.class));
			finish();
			break;
			
		case R.id.expenses:
			startActivity(new Intent(this,ExpenseActivity.class));
			finish();
			break;
		}
		
	}

	public void importDataBase(String name)
	{
		try {
			
			   // Toast.makeText(getApplicationContext(), "2", 2000).show();
			    if (sdCardPath.canWrite()) {
			        String currentDBPath = "//data//com.choxx.personaldata//databases//expense";
			        String backupDBPath = name;
			       // Toast.makeText(getApplicationContext(), name, 2000).show();
			        File currentDB = new File(data, currentDBPath);
			        File backupDB = new File(sdCardPath, backupDBPath);

			    //    Toast.makeText(getApplicationContext(), "3", 2000).show();
			        
			        if (backupDB.exists()) {
			            FileChannel src = new FileInputStream(backupDB).getChannel();
			            FileChannel dst = new FileOutputStream(currentDB).getChannel();
			            dst.transferFrom(src, 0, src.size());
			            src.close();
			            dst.close();
			            Toast.makeText(getApplicationContext(), "DataBase Imported Successfully!!", 2000).show();
			        }
			    }
			    
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error in importing", 2000).show();
			}
	}// importDatabase closed
	
	public void exportDataBase(){
		try {
			File sdCardPath=new File(Environment.getExternalStorageDirectory()+"/MyExpense");
		    File data = Environment.getDataDirectory();
		    if (sdCardPath.canWrite()) {
		        String currentDBPath = "//data//com.choxx.personaldata//databases//expense";
		        Date dt=new Date();
		        String backupDBPath = "expense";
		        String dbName="choxx"+Calendar.getInstance().get(Calendar.DATE)+Calendar.getInstance().get(Calendar.MONTH)+Calendar.getInstance().get(Calendar.YEAR)+"_"+dt.getHours()+dt.getMinutes()+dt.getSeconds();
		        //Toast.makeText(getApplicationContext(), "1"+dbName, 2000).show();
		        //String dbName="abhi";
		        File currentDB = new File(data, currentDBPath);
		        File backupDB = new File(sdCardPath, backupDBPath);
		        File backupDB1 = new File(sdCardPath, dbName);
		        //Toast.makeText(getApplicationContext(), "2", 2000).show();
		        if (currentDB.exists()) {
		            FileChannel src = new FileInputStream(currentDB).getChannel();
		           // FileChannel dst = new FileOutputStream(backupDB).getChannel();
		            FileChannel dst1 = new FileOutputStream(backupDB1).getChannel();
		            dst1.transferFrom(src, 0, src.size());
		            //dst.transferFrom(src, 0, src.size());
		            //dst1.transferFrom(src, 0, src.size());
		            //Toast.makeText(getApplicationContext(), "3", 2000).show();
		            src.close();
		            //dst.close();
		            dst1.close();
		        }
		    }
		    Toast.makeText(getApplicationContext(), "Database saved to MyExpense", 2000).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "error in creating db", 2000).show();
		}
	}
	
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	this.finish();
}	
	
}
