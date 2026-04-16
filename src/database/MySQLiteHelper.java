/**
 * 
 */
package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_LOG = "log";
 
  private static final String DATABASE_NAME = "smslog.db";
  private static final int DATABASE_VERSION = 1;

  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_UTCTIME= "UTCTIME";  
  public static final String COLUMN_DESC = "DESC";
  public static final String COLUMN_TAG = "TAG";
  
  private static final String TAG = "MySQLiteHelper";  
  
  private static final String DATABASE_CREATE_LOG= "create table "
	      + TABLE_LOG + "(" 
		  + COLUMN_ID + " integer primary key autoincrement, "
	      + COLUMN_UTCTIME + " double, "
	      + COLUMN_TAG + " char(256), "
	      + COLUMN_DESC + " char(256)"
	      + ");";
 

  private final Context mContext;

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase database) {

    database.execSQL(DATABASE_CREATE_LOG);


  }
  
  public static int boolToint(boolean value)
  {
	  if(value==Boolean.TRUE)
	  {
		  return 1; 
	  }
	  else
	  {
		  return 0;
	  }
  }
  
  public static boolean intTobool(int value)
  {
	  if(value!=1)
	  {
		  return false; 
	  }
	  else
	  {
		  return true;
	  }
  }

@Override
public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub
	
}

} 