package main;

import helperclass.AppSettings;
import helperclass.Broadcast;
import helperclass.MobileDataManager;
import helperclass.MyApplication;
import helperclass.NetworkMonitor;
import helperclass.NotificationHelper;
import helperclass.PasscodeHandler;
import helperclass.ProductManager;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.codemonkey.smsmobiledatafree.R;

import database.LogEvent;
import database.LogEventDataSource;

public class MainHandler {

    private final String TAG = "MainHandler";
    private ProductManager productManager;
    private AppSettings appSettings;
	private Context mContext;
    private PasscodeHandler passcodeHandler;
	
	public MainHandler(Context context) {
		mContext = context;
    	mContext = MyApplication.getAppContext();
    	productManager = new ProductManager(mContext);
    	appSettings = new AppSettings(mContext);
    	passcodeHandler = new PasscodeHandler(mContext);
	}
	
	
public void onSMSReceive(Context context, Intent intent) {
		
	    final Bundle bundle = intent.getExtras();
	    String code = "";
	    String phoneNumber = "";
	                                    		
	    try {            
	    	
	        if (bundle != null) 
	        {
	             
	            final Object[] pdusObj = (Object[]) bundle.get("pdus");
	                           
	            for (int i = 0; i < pdusObj.length; i++) {
	                 
	                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
	                phoneNumber = currentMessage.getDisplayOriginatingAddress();
	                
	                code = code + currentMessage.getDisplayMessageBody();
	                                                
	            } // end for loop
	            
	            if(code!="")
	            {
	            	parseCode(context, phoneNumber, code);
	            }
    
	            
	          } // bundle is null
	         
	    } catch (Exception e) {
	        Log.e(TAG, e.getMessage());
	         
	    }
	}
	
	private void log(String phoneNumber, boolean enable)
	{
        Boolean enableLog = appSettings.getBoolean(AppSettings.Key_EnableLog, false);
		if(!productManager.Log())
		{
			return;
		}
        
        if(enableLog)
		{
    		LogEventDataSource datasource = new LogEventDataSource(mContext);
    		LogEvent logEvent = new LogEvent();
    		
    		logEvent.settag(phoneNumber);
    		logEvent.setdesc(String.valueOf(enable));
    		logEvent.setutctime(System.currentTimeMillis());

    		datasource.open();
    		datasource.create(logEvent);
    		datasource.close();
		}
	}
	
	private void reply(String phoneNumber, boolean enable)
	{
        Boolean reply = appSettings.getBoolean(AppSettings.Key_Reply, false);
        if(!productManager.Reply())
        {
        	return;
        }
        
        if(reply)
        {
        	String text = "";
        	
    		if(productManager.ConnectionType()==ProductManager.MobileData)
    		{
    			text = mContext.getString(R.string.STR22018);
    		}
    		else if(productManager.ConnectionType()==ProductManager.Wifi)
    		{
    			text = mContext.getString(R.string.STR22017);				
    		}
        	
        	if(enable)
        	{
        		text = text + mContext.getString(R.string.STR22015);
        	}
        	else
        	{
        		text = text + mContext.getString(R.string.STR22016);
        	}
        			
        	sendSMS(phoneNumber, text);
        }
	}
	
	 private void sendLongSMS(String phoneNum, String message) {    	 

	        SmsManager smsManager = SmsManager.getDefault();
	        ArrayList<String> parts = smsManager.divideMessage(message); 
	        smsManager.sendMultipartTextMessage(phoneNum, null, parts, null, null);
	 
	 }

	 private void sendSMS(String phoneNum, String message) 
	 {
		 if(message.length()>0)
		 {
			 SmsManager smsManager = SmsManager.getDefault();        
			 smsManager.sendTextMessage(phoneNum, null, message, null, null);
		 }
	 }
	
	private void updateCount(int count)
	{
		appSettings.putInt(AppSettings.Key_UsageCount, count);
   	 	Intent intent = new Intent();
		intent.setAction(Broadcast.UpdateCount);		 
		mContext.sendBroadcast(intent);
	}
	
	private void parseCode(Context context, String phoneNumber, String codeReceived)
	{
        MobileDataManager mDataManager = new MobileDataManager(context);
        NetworkMonitor networkMonitor = new NetworkMonitor(context);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Boolean showNotification = appSettings.getBoolean(AppSettings.Key_Notification, false);

        Boolean isRunning = false;
        
        int count =	appSettings.getInt(AppSettings.Key_UsageCount, 0);
        
        if(count!=0)
        {
        	isRunning = true;
        }
        
		String enableCode = "#" + passcodeHandler.getEnableCode();
		String disableCode = "#" + passcodeHandler.getDisableCode();
		
		if (codeReceived.equalsIgnoreCase(enableCode)) {
			
			if (!isRunning) {
				
				if (productManager.showNotification()) {
					if (showNotification) {
						notificationHelper.notifyDisabled(phoneNumber);
					}
				}
				
				
			} else {
				
				if (productManager.ConnectionType() == ProductManager.MobileData) {
					mDataManager.enableMobileData();
				} else if (productManager.ConnectionType() == ProductManager.Wifi) {
					networkMonitor.enableWifi();
				}

				updateCount(count-1);
				
				if(productManager.showNotification())
				{
					if(showNotification)
					{
						notificationHelper.notify(phoneNumber, true);
					}
				}
				
				log(phoneNumber, true);
				reply(phoneNumber, true);
				
			}
			
		} else if (codeReceived.equalsIgnoreCase(disableCode)) {
			
			if (!isRunning) {
				
				if (productManager.showNotification()) {
					if (showNotification) {
						notificationHelper.notifyDisabled(phoneNumber);
					}
				}
				
				
			} else {			
			
				
				if(productManager.ConnectionType()==ProductManager.MobileData)
				{
					mDataManager.disableMobileData();
				}
				else if(productManager.ConnectionType()==ProductManager.Wifi)
				{
					networkMonitor.disableWifi();
				}
				
				if(productManager.showNotification())
				{
					if(showNotification)
					{
						notificationHelper.notify(phoneNumber, false);
					}
				}
				
				log(phoneNumber, false);
				reply(phoneNumber, false);
				
			}
			
		}
		
	}
        	

}
