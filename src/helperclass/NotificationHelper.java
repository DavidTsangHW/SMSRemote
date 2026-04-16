/**
 * 
 */
package helperclass;
        
import java.util.Calendar;

import ui.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.codemonkey.smsmobiledatafree.R;

 /**
 * @author David Tsang
 *
 */
public class NotificationHelper {
        
	/**
	 * 
	 */
	private final Context mContext;
	private ProductManager productManager;
	
	String TAG = "NotificationHelper";
	
	public NotificationHelper(Context context) {
		mContext = context;
		productManager = new ProductManager(context);
	}
        
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
	}

	private String getTimeText(long timeMillis)
	{
		Calendar cal = Calendar.getInstance();
		
		String dspText = "";
		
		cal.setTimeInMillis(timeMillis);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		StringBuilder time = new StringBuilder().append(hour)
				.append(":").append(pad(minute));
			
		dspText = time + "";
		
		return dspText;
	}

	private String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	public void notifyDisabled(String phoneNumber)
	{
		String title = "";
		String text = "";
		
		if(Consts.debugMode)
		{
			title = Consts.maskedPhone;
		}
		
		title = phoneNumber;
	
		text = mContext.getString(R.string.STR22020);
		
		notifyInfo(title, text);
	}
	
	public void notify(String phoneNumber, boolean enabled)
	{
		String title = "";
		String text = "";
		

		title = mContext.getString(R.string.app_name);
		
		title = phoneNumber;
		
		if(Consts.debugMode)
		{
			title = Consts.maskedPhone;
		}
		
		if(productManager.ConnectionType()==ProductManager.MobileData)
		{
			text = text + mContext.getString(R.string.STR22018);
		}
		else if(productManager.ConnectionType()==ProductManager.Wifi)
		{
			text = text + mContext.getString(R.string.STR22017);				
		}
			
		
		if(enabled)
		{
			text = text + mContext.getString(R.string.STR22015);
    	}
		else
		{
			text = text + mContext.getString(R.string.STR22016);
		}
		
		notifyInfo(title, text);
	}
	
	public void notifyInfo(String title, String text)
	{
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification n  = new Notification.Builder(mContext)
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pIntent)
        .setAutoCancel(true).build();
		
		n.defaults |= Notification.DEFAULT_VIBRATE;
		n.defaults |= Notification.DEFAULT_LIGHTS;
		n.defaults |= Notification.DEFAULT_SOUND;
		
		NotificationManager notificationManager = 
		  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        
		notificationManager.notify(0, n);
		

	}
	

	
	
	
        
}
