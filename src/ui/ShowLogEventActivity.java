package ui;

import helperclass.Consts;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codemonkey.smsmobiledatafree.R;

import database.LogEvent;
import database.LogEventDataSource;

public class ShowLogEventActivity extends Activity {
	
  private LogEventDataSource datasource;
  
	private List<LogEvent> values = null;
	private LogEvent value = null;
	private LogAdapter adapter;
	private ListView mListView;

  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database);
		
	    datasource = new LogEventDataSource(this);
		
		datasource.open();
		
  		LogEvent logEvent = new LogEvent();
		
  		if(Consts.debugMode)
  		{
  			logEvent.settag("12345678");
  			logEvent.setdesc("Log");
  			logEvent.setutctime(System.currentTimeMillis());

  			datasource.create(logEvent);
  		}
	    
		mListView = (ListView)findViewById(android.R.id.list);
    	
    	mListView.setVisibility(View.VISIBLE);
		
		loaddata();

	}
  
	public void loaddata() {

		values = datasource.list(null);

		int index = mListView.getFirstVisiblePosition();
 	    
   	    View v =  mListView.getChildAt(0);
   	    
   	    int top = (v == null) ? 0 : v.getTop();

		adapter = new LogAdapter(this, values);

		mListView.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();

		mListView.setSelectionFromTop(index, top);

	}
  

  @Override
 	public boolean onCreateOptionsMenu(Menu menu) {
 	  	
 	  menu.clear();
 	  MenuInflater inflater = getMenuInflater();
 	  inflater.inflate(R.menu.eventlog_menu, menu);  
 	  return super.onCreateOptionsMenu(menu);
 		
 	}
  
  	private void clearLog()
  	{
  		datasource.deleteAll();
  		Toast.makeText(this, getString(R.string.STR22019), Toast.LENGTH_LONG).show();
  		loaddata();
  	}
  	

   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_clear:

			clearLog();
			break;

		default:
			Toast.makeText(getApplicationContext(), item.getItemId(),
					Toast.LENGTH_LONG).show();
			return super.onOptionsItemSelected(item);

		}

		return super.onOptionsItemSelected(item);

	}
  

  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }

} 