package database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LogEventDataSource {

  // Database fields
  private Context mContext;
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  
  private String[] allColumns = {  
		  MySQLiteHelper.COLUMN_ID, 
		  MySQLiteHelper.COLUMN_UTCTIME,
		  MySQLiteHelper.COLUMN_TAG,
		  MySQLiteHelper.COLUMN_DESC
		 }; 
    
  public LogEventDataSource(Context context) {
	  mContext = context;
	  dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }
  
  public void deleteAll()
  {

      database.delete(MySQLiteHelper.TABLE_LOG, null, null);

  }
  
  public LogEvent update(LogEvent logEvent) {
      
	    ContentValues values = new ContentValues();
 
	    String selection = MySQLiteHelper.COLUMN_ID + "=" + String.valueOf(logEvent.getid())  + "";	

	    values.put(MySQLiteHelper.COLUMN_TAG, logEvent.gettag());
	    values.put(MySQLiteHelper.COLUMN_DESC, logEvent.getdesc());
	    values.put(MySQLiteHelper.COLUMN_UTCTIME, logEvent.getutctime());
	    
	    database.update(MySQLiteHelper.TABLE_LOG, values,
	  	        selection, null);
		  
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOG,
	  	        allColumns, selection, null,
	  	        null, null, null);
	    
	    cursor.moveToFirst();
	    
	    LogEvent savedLogEvent  = cursorToEntity(cursor);
	    
	    cursor.close();
	    
	    return savedLogEvent;
	  }
  
  public LogEvent create(LogEvent logEvent) {
        
    ContentValues values = new ContentValues();

    values.put(MySQLiteHelper.COLUMN_TAG, logEvent.gettag());
    values.put(MySQLiteHelper.COLUMN_DESC, logEvent.getdesc());
    values.put(MySQLiteHelper.COLUMN_UTCTIME, logEvent.getutctime());

    long insertId = database.insert(MySQLiteHelper.TABLE_LOG, null,
        values);
    
    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOG,
        allColumns, MySQLiteHelper.COLUMN_ID+ " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    LogEvent savedLogEvent = cursorToEntity(cursor);
    cursor.close();
    
    return savedLogEvent;

  }
  
  public void delete(LogEvent logEvent) {
    long id = logEvent.getid();
    database.delete(MySQLiteHelper.TABLE_LOG, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }
    
  public List<LogEvent> list(String selection) {
	  
	  String orderBy;
	  
	  List<LogEvent> logEvents = new ArrayList<LogEvent>();

	  orderBy = MySQLiteHelper.COLUMN_UTCTIME + " ASC";
	  
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_LOG,
		        allColumns, selection, null, null, null, orderBy);
	  
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      LogEvent logEvent = cursorToEntity(cursor);
      logEvents.add(logEvent);
      cursor.moveToNext();
    }

    cursor.close();
    return logEvents;
  }

  private LogEvent cursorToEntity(Cursor cursor) {
    
	LogEvent logEvent = new LogEvent();
    
    logEvent.setid(cursor.getLong(0));
    logEvent.setutctime(cursor.getLong(1));
    logEvent.settag(cursor.getString(2));
    logEvent.setdesc(cursor.getString(3));    
      
    return logEvent ;
  }
      
  
} 