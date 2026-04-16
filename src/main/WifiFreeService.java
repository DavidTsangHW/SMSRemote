package main;

import helperclass.MyApplication;
import helperclass.PasscodeHandler;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
 
public class WifiFreeService extends Service
{   
    private final String TAG = "WifiFreeService";
    private PasscodeHandler passcodeHandler;
    private MainHandler mainHandler;
    private Context mContext;
        
    @Override
    public void onCreate() {
    	
    	mContext = MyApplication.getAppContext();
    	passcodeHandler = new PasscodeHandler(mContext);
     	mainHandler = new MainHandler(mContext);
    	    	
    	if(passcodeHandler.getEnableCode()=="")
    	{
    		passcodeHandler.saveCode(PasscodeHandler.genCode());
    	}
    	
		IntentFilter filter = new IntentFilter(); 
    	filter.addAction("android.provider.Telephony.SMS_RECEIVED"); 
    	filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    	filter.addAction("android.location.PROVIDERS_CHANGED");
    	registerReceiver(receiver, filter);
    	
    }
            
    @Override
    public void onDestroy() {

    	unregisterReceiver(receiver);

    }
    
	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
		      if(action.equals("android.provider.Telephony.SMS_RECEIVED"))
		      {		    	  
		    	  mainHandler.onSMSReceive(context, intent);
		      }

		}
	};


	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

   
	
}