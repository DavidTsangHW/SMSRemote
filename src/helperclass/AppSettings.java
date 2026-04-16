/**
 * 
 */
package helperclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

/**
 * @author David
 *
 */
public class AppSettings {
	
	private Context mContext;
	SharedPreferences prefs;

	public static String Key_ShowHint= "show_hint";
	
	public static String Key_Info_Version = "info_version";
	public static String Key_Info_VersionCode = "info_versionCode";
	public static String Key_Info_About = "info_about";
	public static String Key_Info_Upgrade = "info_upgrade";
	public static String Key_Info_SupportUs = "info_supportus";
	public static String Key_Info_Tell_A_Freind = "info_tell_a_friend";
	public static String Key_Info_Product = "info_product";
	
	public static String Key_Info_SMS_MOBILE_DATA_FREE = "info_sms_mobile_data_free";
	public static String Key_Info_SMS_MOBILE_DATA_FULL = "info_sms_mobile_data_full";
	public static String Key_Info_SMS_WIFI_FREE = "info_sms_wifi_free";
	public static String Key_Info_SMS_WIFI_FULL = "info_sms_wifi_full";
	public static String Key_Info_EXACTGPS = "info_exact_gps";

	public static String Key_UsageCount = "UsageCount";
	public static String Key_Notification = "notification";
	public static String Key_Reply = "reply";
	public static String Key_EnableLog = "enablelog";
	public static String Key_OpenLog = "openlog";
	public static String Key_Passcode = "Passcode";
	public static String Key_Enabled = "Enabled";	

	
	public AppSettings(Context context) {
		mContext = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	public boolean CallBackAllowed() {
		return prefs.getBoolean("enableCallBack_checkbox", false);
	}


	public void putString(String keyString, String value) {
		Editor editor = prefs.edit();
		editor.putString(keyString, value);
		editor.commit();
	}

	public void putDouble(String keyString, double value) {

		Editor editor = prefs.edit();
		editor.putString(keyString, String.valueOf(value));
		editor.commit();
	}

	public void putBoolean(String keyString, boolean value) {
		Editor editor = prefs.edit();
		editor.putBoolean(keyString, value);
		editor.commit();
	}

	public void putLong(String keyString, Long value) {
		Editor editor = prefs.edit();
		editor.putLong(keyString, value);
		editor.commit();
	}

	public void putInt(String keyString, int value) {
		Editor editor = prefs.edit();
		editor.putInt(keyString, value);
		editor.commit();
	}


public boolean getBoolean(String Key, Boolean defValue)
{
	try
	{
		return prefs.getBoolean(Key, defValue);
	}
	catch(Exception e)
	{
		return defValue;
	}
}

public long getLong(String Key, Long defValue)
{
	try
	{
		return prefs.getLong(Key, defValue);
	}
	catch(Exception e)
	{
		return defValue;
	}
}

public double getDouble(String Key)
{
	try
	{
		return Double.valueOf(prefs.getString(Key,null));
	}
	catch(Exception e)
	{
		return 0;
	}
}

public String getString(String Key, String defValue)
{
	try
	{
		return prefs.getString(Key, defValue);
	}
	catch(Exception e)
	{
		return defValue;
	}
}

public int getInt(String Key, int defValue)
{
	try
	{	
		return prefs.getInt(Key, defValue);
	}
	catch(Exception e)
	{
		return defValue;
	}
}

public void updateVersionCode()
{

try {
	PackageInfo pInfo;
	pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
	putString(AppSettings.Key_Info_VersionCode, String.valueOf(pInfo.versionCode));
} catch (NameNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}

}

