package ui;

import helperclass.AppSettings;
import helperclass.HintHandler;
import helperclass.ProductManager;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.codemonkey.smsmobiledatafree.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	
	private ProductManager productManager;
	private AppSettings appSettings;


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	productManager = new ProductManager(this);
    	appSettings = new AppSettings(this);
    	    	
    	/*
    	ActionBar actionBar = getActionBar();
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	*/
    	
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  	
	  menu.clear();
	   MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.settingsmenu, menu);
      	            	  
	   return true;
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	onBackPressed();
            return true;
            			
        case R.id.action_help:
        	HintHandler hintHandler = new HintHandler(this);
		    
		    //hintHandler.showDialog(null,getString(R.string.STR70007),getString(R.string.MSGTEXT1033),false);
			
            return true;    
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
		
	 @Override
	    protected void onPause() {
	        super.onPause();      

	 }
	
	 @Override
	    protected void onResume() {
	        super.onResume();

	 }
	 
	
	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}


		Preference pref;
		
		PreferenceCategory fakeHeader = new PreferenceCategory(this);
		fakeHeader.setTitle(R.string.pref_header_log);
		addPreferencesFromResource(R.xml.pref_parameters);
		getPreferenceScreen().addPreference(fakeHeader);
		
		fakeHeader = new PreferenceCategory(this);

		addPreferencesFromResource(R.xml.pref_log);
		getPreferenceScreen().addPreference(fakeHeader);

		fakeHeader.setTitle(R.string.pref_header_prod_info);
		fakeHeader = new PreferenceCategory(this);
		addPreferencesFromResource(R.xml.pref_product_info);		
		getPreferenceScreen().addPreference(fakeHeader);

		getPreferenceManager()
		   .findPreference(AppSettings.Key_OpenLog)
		   .setOnPreferenceClickListener(this);
	
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_Tell_A_Freind)
		   .setOnPreferenceClickListener(this);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_About)
		   .setOnPreferenceClickListener(this);
		
		pref = getPreferenceManager().findPreference(AppSettings.Key_Notification);
		
		if(productManager.ConnectionType().equals(ProductManager.MobileData))
		{
			pref.setSummary(getString(R.string.STR22032));
		}
		else if (productManager.ConnectionType().equals(ProductManager.Wifi))
		{
			pref.setSummary(getString(R.string.STR22031));
		}

		if(!productManager.Log())
		{
			findPreference(AppSettings.Key_EnableLog).setEnabled(false);
			findPreference(AppSettings.Key_OpenLog).setEnabled(false);
		}
		
		if(!productManager.Reply())
		{
			findPreference(AppSettings.Key_Reply).setEnabled(false);
		}
		setVersion();

		setProduct();
		
		fakeHeader.setTitle(R.string.pref_header_more_prod);
		fakeHeader = new PreferenceCategory(this);
		addPreferencesFromResource(R.xml.pref_more_product);		
		getPreferenceScreen().addPreference(fakeHeader);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FREE)
		   .setOnPreferenceClickListener(this);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FULL)
		   .setOnPreferenceClickListener(this);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_SMS_WIFI_FREE)
		   .setOnPreferenceClickListener(this);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_SMS_WIFI_FULL)
		   .setOnPreferenceClickListener(this);
		
		getPreferenceManager()
		   .findPreference(AppSettings.Key_Info_EXACTGPS)
		   .setOnPreferenceClickListener(this);
		
		if(productManager.ProductId==1)
		{
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_WIFI_FULL);
			getPreferenceScreen().removePreference(pref);
		}
	
		
		if(productManager.ProductId==2)
		{
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_WIFI_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FULL);
			getPreferenceScreen().removePreference(pref);

		}
		
		if(productManager.ProductId==3)
		{
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_WIFI_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FULL);
			getPreferenceScreen().removePreference(pref);
		}
	
		
		if(productManager.ProductId==4)
		{
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_MOBILE_DATA_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_WIFI_FREE);
			getPreferenceScreen().removePreference(pref);
			
			pref = getPreferenceManager().findPreference(AppSettings.Key_Info_SMS_WIFI_FULL);
			getPreferenceScreen().removePreference(pref);

		}
		


	}
	
	
	private void setProduct()
	{
		PackageInfo pInfo;
		ProductManager productManager;
		
		productManager = new ProductManager(this);
		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			findPreference(AppSettings.Key_Info_Product).setSummary(productManager.ProductName());
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void setVersion()
	{
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = pInfo.versionName;
			findPreference(AppSettings.Key_Info_Version).setSummary(version);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
				

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}	
	
		
	
	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */	
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();
												
			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else if (preference instanceof RingtonePreference) {
				// For ringtone preferences, look up the correct display value
				// using RingtoneManager.
				if (TextUtils.isEmpty(stringValue)) {
					// Empty values correspond to 'silent' (no ringtone).
					preference.setSummary(R.string.pref_ringtone_silent);

				} else {
					Ringtone ringtone = RingtoneManager.getRingtone(
							preference.getContext(), Uri.parse(stringValue));

					if (ringtone == null) {
						// Clear the summary if there was a lookup error.
						preference.setSummary(null);
					} else {
						// Set the summary to reflect the new ringtone display
						// name.
						String name = ringtone
								.getTitle(preference.getContext());
						preference.setSummary(name);
					}
				}

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
				
				
			}
			
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}


	@Override
	public boolean onPreferenceClick(Preference preference) {
									
		if(preference.getKey().equals(AppSettings.Key_Info_Tell_A_Freind))
		{
			productManager.TellaFriend(this);
		}
		
		if (preference.getKey().equals(AppSettings.Key_Info_About)) {
			productManager.About(this);
		}

		if (preference.getKey().equals(AppSettings.Key_OpenLog)) {
			Intent vintent = new Intent(this, ShowLogEventActivity.class);
			startActivity(vintent);
		}
		
		if (preference.getKey().equals(AppSettings.Key_Info_EXACTGPS)) {
			productManager.openGooglePlay(this, "com.codemonkey.locationapp");
		}
		
		
		if (preference.getKey().equals(AppSettings.Key_Info_SMS_MOBILE_DATA_FREE))
		{
			productManager.openGooglePlay(this, productManager.PackageName(1));
		}
		
		if(preference.getKey().equals(AppSettings.Key_Info_SMS_MOBILE_DATA_FULL))
		{
			productManager.openGooglePlay(this, productManager.PackageName(2));
		}
		
		if(preference.getKey().equals(AppSettings.Key_Info_SMS_WIFI_FREE))
		{
			productManager.openGooglePlay(this, productManager.PackageName(3));
		}
		
		if(preference.getKey().equals(AppSettings.Key_Info_SMS_WIFI_FULL))
		{
			productManager.openGooglePlay(this, productManager.PackageName(4));
		}
		
			
		return true;
	}

	
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
							
		String stringValue = newValue.toString();
		
		if (preference instanceof ListPreference) {
			// For list preferences, look up the correct display value in
			// the preference's 'entries' list.
			ListPreference listPreference = (ListPreference) preference;
			int index = listPreference.findIndexOfValue(stringValue);
	
			// Set the summary to reflect the new value.
			preference
					.setSummary(index >= 0 ? listPreference.getEntries()[index]
							: null);
			
		}
		
		
		return true;
	}
	
	// We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
 
    

    
    
   

    

    	
   
}
    
