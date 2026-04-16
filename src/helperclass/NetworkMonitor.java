/**
 * 
 */
package helperclass;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * @author David Tsang
 *
 */
public class NetworkMonitor {

	private final Context mContext;	
	private WifiManager wifiManager;
	
	public NetworkMonitor(Context context) {
		// TODO Auto-generated constructor stub
		 this.mContext = context;		
		 wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	}


	public WifiManager getWifiManager()
	{
		return wifiManager;
	}
	
	public List<ScanResult> getScanResult()
	{
		return wifiManager.getScanResults();
	}
	
	public boolean startScan()
	{
		return wifiManager.startScan();
	}
	
	public String getSSIDs()
	{
		List<ScanResult> results;
		int size = 0;
        String SSIDs = "";
        String SSID = "";
        String BSSIDs = "";
		        
        results = this.getScanResult();
        size = results.size();
               
        if(size > 0)
        {
        	
        	for(int i=size-1;i>=0;i--){
        		
        		if(BSSIDs.indexOf(results.get(i).BSSID) == -1)
        		{
        			SSID = results.get(i).SSID;
        			if(SSID.length()==0)
        			{ 
        				SSID = results.get(i).BSSID;
        			}
        			SSIDs = SSIDs + SSID + " (" + this.wifiManager.calculateSignalLevel(results.get(i).level,5) + ")\n";
        			BSSIDs = BSSIDs + results.get(i).BSSID;
        		}
        	}
        }
        
        return SSIDs;
	 }
	
	 public void disableWifi()
	 {
		 //wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		 wifiManager.setWifiEnabled(false);
	 }
	 
	 public boolean enableWifi()
	 {
		//wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		 
		 wifiManager.setWifiEnabled(true);
		 	 		 		 
		 return wifiManager.isWifiEnabled();			 		 
		 
	 }

	 
	 private void setWifiTetheringEnabled(boolean enable) {
		 
		    //wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		    Method[] methods = wifiManager.getClass().getDeclaredMethods();
		    for (Method method : methods) {
		        if (method.getName().equals("setWifiApEnabled")) {
		            try {
		                method.invoke(wifiManager, null, enable);
		            } catch (Exception ex) {
		            }
		            break;
		        }
		    }
		}
	 
		 
	

}
