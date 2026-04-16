package ui;

import helperclass.AppSettings;
import helperclass.Broadcast;
import helperclass.Consts;
import helperclass.LogManager;
import helperclass.MobileDataManager;
import helperclass.NetworkMonitor;
import helperclass.PasscodeHandler;
import helperclass.ProductManager;

import java.util.ArrayList;
import java.util.List;

import main.MobileDataService;
import main.WifiService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemonkey.smsmobiledatafree.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends Activity implements View.OnClickListener {

	public AppSettings appSettings;
	
	private PasscodeHandler passcodeHandler;
	private ProductManager productManager;

	Button startStopButton;
	Button passcodeButton;
	Button sendPasscodeButton;
	Button settingsButton;
	Button fullVersionButton;
	EditText edtCode1;
	EditText edtCode2;
	TextView textCount;
	TextView textDesc;
	TextView textVersion;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		setupReceiver();
    	
    	appSettings = new AppSettings(this);
    	passcodeHandler = new PasscodeHandler(this);
    	productManager = new ProductManager(this);
    	
		productManager.startService();

		Button enableMobileDataButton = (Button)findViewById(R.id.buttonEnableMobileData);
		Button disableMobileDataButton = (Button)findViewById(R.id.buttonDisableMobileData);
		Button enableWifiButton = (Button)findViewById(R.id.buttonEnableWifi);
		Button disableWifiButton = (Button)findViewById(R.id.buttonDisableWifi);
		startStopButton = (Button)findViewById(R.id.buttonStartStop);
		passcodeButton = (Button)findViewById(R.id.buttonPasscode);
		settingsButton = (Button)findViewById(R.id.buttonSettings);
		edtCode1 = (EditText)findViewById(R.id.edt_code1);
		edtCode2 = (EditText)findViewById(R.id.edt_code2);
		textCount = (TextView)findViewById(R.id.textCount);
		textDesc = (TextView)findViewById(R.id.textDesc);
		textVersion = (TextView)findViewById(R.id.textVersion);
		fullVersionButton = (Button)findViewById(R.id.buttonFullVersion);
		sendPasscodeButton = (Button)findViewById(R.id.buttonSendPasscode);

		startStopButton.setOnClickListener(this);
		passcodeButton.setOnClickListener(this);
		sendPasscodeButton.setOnClickListener(this);	
		settingsButton.setOnClickListener(this);
		fullVersionButton.setOnClickListener(this);
		
		fullVersionButton.setVisibility(View.GONE);
		
		enableMobileDataButton.setOnClickListener(this);
		disableMobileDataButton.setOnClickListener(this);	
		enableWifiButton.setOnClickListener(this);
		disableWifiButton.setOnClickListener(this);	
		
		//if (!Consts.debugMode) {
			enableMobileDataButton.setVisibility(View.GONE);
			disableMobileDataButton.setVisibility(View.GONE);
			enableWifiButton.setVisibility(View.GONE);
			disableWifiButton.setVisibility(View.GONE);
		//}
		setVersion();
		setupIcon();
		if(productManager.ShowAd())
		{
			showConnectionError();
		}
		
		initPasscode();
		
		setupUI();
	}
	
	private void setVersion()
	{
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = getString(R.string.pref_title_version) + " " + pInfo.versionName;
			textVersion.setText(version);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		if(ProductManager.ProductId==2||ProductManager.ProductId==4)
		{

			fullVersionButton.setVisibility(View.GONE);
		}

	}
	
	private void initPasscode()
	{
		if(passcodeHandler.getEnableCode()=="")
		{
			passcodeHandler.saveCode(PasscodeHandler.genCode());
		}
	}
	
	private void setupReceiver()
	{
		IntentFilter filter = new IntentFilter(); 
    	filter.addAction(Broadcast.UpdateCount); 
    	registerReceiver(receiver, filter);
	}
	
	private void setupIcon()
	{		
		ImageView icon = (ImageView) findViewById(R.id.icon);

		Bitmap bitmap = productManager.getIcon();
		
		icon.setImageBitmap(bitmap);
	}
	
	private void setupUI()
	{
		if (productManager.ConnectionType().equals(ProductManager.MobileData)) {
			textDesc.setText(getString(R.string.STR22009));
		} else {
			textDesc.setText(getString(R.string.STR22008));
		}

	  refreshAd();		
	  setupStartStopButton();
  	  updateCount();
  	  showCode1();
  	  showCode2();
  	  updateCount();
	}
	
	
    private void refreshAd()
	{    	
    	AdView adView2 = (AdView) findViewById(R.id.adView2);   	       
    	AdView adView3 = (AdView) findViewById(R.id.adView3); 
    	
    	if(productManager.ShowAd())
    	{      	
    		//Default product id 1
    		AdView adView = adView2;
    		AdView hideView = adView3;
    		
    		if(ProductManager.ProductId==3)
        	{
        		adView = adView3;
        		hideView = adView2;
        	}
        	hideView.setVisibility(View.GONE);
    		AdRequest adRequest = new AdRequest.Builder().build();
  	  		adView.loadAd(adRequest);
    	}
    	else
    	{ 
    		adView2.setVisibility(View.GONE);
    		adView3.setVisibility(View.GONE);

    	}
	}
	
	private void showCode1()
	{
		String code = passcodeHandler.getEnableCode();
		
		boolean enable = false;
		edtCode1.setFocusable(enable);
	    edtCode1.setEnabled(enable);
	    edtCode1.setCursorVisible(enable);
				
		if(code!=null)
		{
			edtCode1.setText("#" + code);
		}
	}
	
	private void showCode2()
	{
		String code = passcodeHandler.getDisableCode();
		
		boolean enable = false;
		
		edtCode2.setFocusable(enable);
	    edtCode2.setEnabled(enable);
	    edtCode2.setCursorVisible(enable);
		
		if(code!=null)
		{
			edtCode2.setText("#" + code);
		}
	}
	
	private boolean isRunning()
	{
		int count = appSettings.getInt(AppSettings.Key_UsageCount, 0);
		boolean ret = false;
		
		if(count!=0)
		{
			ret = true;
		}
		
		return ret;
	}
	
	private void setupStartStopButton()
	{
		if(!isRunning())
		{
			startStopButton.setText(getString(R.string.start));
		}
		else
		{
			startStopButton.setText(getString(R.string.stop));				
		}
		

		
	}
	
	private void updateCount()
	{
		int count = appSettings.getInt(AppSettings.Key_UsageCount, 0);
		if(count>0)
		{
			textCount.setText(String.valueOf(count) + getString(R.string.STR22027));
		}
		else if(count<0)
		{
			textCount.setText(getString(R.string.STR22026));
		}
		else
		{
			textCount.setText(getString(R.string.STR22012));
		}
	}
	
	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			refreshAd();
		      if(action.equals(Broadcast.UpdateCount))
		      {
		    	  setupUI();
		      }

		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
    		Intent intent = new Intent(this,SettingsActivity.class);
      		startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		int id = arg0.getId();
		MobileDataManager mDataManager = new MobileDataManager(this);
		NetworkMonitor networkMonitor = new NetworkMonitor(this);
		refreshAd();
		if(id==R.id.buttonEnableMobileData)
		{
			mDataManager.enableMobileData();
		}
		else if(id==R.id.buttonDisableMobileData)
		{
			mDataManager.disableMobileData();
		}
		else if(id==R.id.buttonEnableWifi)
		{
			networkMonitor.enableWifi();
		}
		else if(id==R.id.buttonDisableWifi)
		{
			networkMonitor.disableWifi();
		}
		else if(id==R.id.buttonStartStop)
		{
			changeState();
		}
		else if(id==R.id.buttonPasscode)
		{
			passcodeHandler.saveCode(PasscodeHandler.genCode());
			setupUI();
		}
		else if(id==R.id.buttonSettings)
		{
    		Intent intent = new Intent(this,SettingsActivity.class);
      		startActivity(intent);
		}
		else if(id==R.id.buttonSendPasscode)
		{
			productManager.ForwardPasscode(this);
		}
		
	}
	
	public void changeState()
	{
						
			if(!isRunning())
			{
				selectResetTime();				
			}
			else
			{
				appSettings.putInt(AppSettings.Key_UsageCount, 0);
			}
			
			setupUI();
	}
	
	private void selectResetTime()
	{
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
   	 	
		final List<String> Options = new ArrayList<String>();
		
		Options.add("1");
		Options.add("3");
		Options.add("5");
		Options.add("10");
		if(productManager.Reset())
		{
			Options.add(getString(R.string.STR22011));
		}
		else
		{
			Options.add(getString(R.string.STR22014));
		}
				
  	    alert.setTitle(getString(R.string.STR22010));  
  	    
  	    alert.setSingleChoiceItems(Options.toArray(new String[Options.size()]), -1,  
  	    		new DialogInterface.OnClickListener() {  
  	    		@Override  
  	    		public void onClick(DialogInterface dialog, int which) {
  	    			
  	    			int value = 0;
  	    			
  	    			if(which==0)
  	    			{
  	    				value = 1;
  	    			}
  	    			else if(which==1)
  	    			{
  	    				value = 3;
  	    			}
  	    			else if(which==2)
  	    			{
  	    				value = 5;
  	    			}
  	    			else if(which==3)
  	    			{
  	    				value = 10;
  	    			}
  	    			else if(which==4)
  	    			{
  	    				if(productManager.Reset())
  	    				{
  	    					value = -1;
  	    				}
  	    			}
  	    			appSettings.putInt(AppSettings.Key_UsageCount, value);
  	    			if(value!=0)
  	    			{
  	    				setupUI();
  	    				dialog.dismiss();
  	    			}
                }  
  	    });

    	alert.setNegativeButton(getString(R.string.STR10004), new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int whichButton) {
    		  appSettings.putInt(AppSettings.Key_UsageCount, 0);	
  			  setupUI();
    		  dialog.dismiss();
    	  }
    	});
 
    	alert.show();
    	
	}
	
	
    @Override
    protected void onPause() {
        super.onPause();   
    	unregisterReceiver(receiver);

    }
	
    
	@Override
    protected void onResume() {
		super.onResume();
		setupReceiver();
        setupUI();
    }
	
	

	
	private void showConnectionError()
 	{
 		
		MobileDataManager	mMobileDataManager;
		
		mMobileDataManager = new MobileDataManager(this);
		
		String message = getString(R.string.STR12012);
		String title = getString(R.string.STR12013);
		
		if(mMobileDataManager.isMobileDataEnabled()==false)
		{
			showFailedDialog(title, message);
		}
 	}
	
	private void showFailedDialog(String title, String message) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(title);

		alertDialog.setMessage(message);

		alertDialog.setPositiveButton(getString(R.string.STR40002),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		alertDialog.show();
	}
	
}
