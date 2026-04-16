/**
 * 
 */
package helperclass;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * @author David Tsang
 *
 */
public class MobileDataManager {

	private ConnectivityManager cm;
	final Context mContext;
	/**
	 * 
	 */
	public MobileDataManager(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		cm= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		
	}
	
	public Boolean isMobileDataEnabled()
	{

		try
		{
			//Problem
			
			//When Wifi is on, it wont return mobile anyway
			//There should only be one active data network at any one time because Android will use the best available and shut down the others to conserve battery.
			if(cm.getActiveNetworkInfo().isConnected())
			{
					return true;
			}
			else
			{
					return false;
			}
		}
		catch(Exception ex)
		{
			return false;
		}
		
	}
	
	public ConnectivityManager getConnectivityManager()
	{
		return cm;
	}
		
	public void enableMobileData()
	{
		try
		{
		Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(true);
        dataMtd.invoke(cm, true);
		}
		catch (Exception e) {
            Log.e("enableMobileData", "Exception mobileDataManager" +e);
             
        }
	}

	public void disableMobileData()
	{
		try
		{
		Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(false);
        dataMtd.invoke(cm, false);
		}
		catch (Exception e) {
            Log.e("disableMobileData", "Exception mobileDataManager" +e);
             
        }
	}
	
	
}
