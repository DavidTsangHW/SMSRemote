package helperclass;

import java.util.Locale;

import main.MobileDataFreeService;
import main.MobileDataService;
import main.WifiFreeService;
import main.WifiService;

import com.codemonkey.smsmobiledatafree.R;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


public class ProductManager {

	/**
	 * @param args
	 */
	
	private Context mContext;
	
    //1 - SMS mobile data - free
    //2 - SMS mobile data - full
    //3 - SMS wifi - free
    //4 - SMS wifi - full
    public static final int ProductId = 1;
    public static final String MobileData = "MobileData";
    public static final String Wifi = "Wifi";   
		
	public ProductManager(Context context) {
		  mContext = context;
	  }
	
	public String ProductName()
	{
		String retVal = "";
		
		if(ProductId==1)
		{
			retVal = mContext.getString(R.string.app_name1);			
		}
		else if(ProductId==2)
		{
			retVal = mContext.getString(R.string.app_name2);			
		}
		else if(ProductId==3)
		{
			retVal = mContext.getString(R.string.app_name3);			
		}
		else if(ProductId==4)
		{
			retVal = mContext.getString(R.string.app_name4);			
		}
		
		return retVal;
	}
	
	public String PackageName(int mProgId)
	{
			String url = "com.codemonkey.";
			
			if(mProgId==1)
			{
				url = url + "smsmobiledatafree";
			}
			
			if(mProgId==2)
			{
				url = url + "smsmobiledata";
			}
			
			if(mProgId==3)
			{
				url = url + "smswififree";
			}
			
			
			if(mProgId==4)
			{
				url = url + "smswifi";
			}
			

			
			
			return url;
	}
	
	public Bitmap getIcon()
	{
		Bitmap bitmap = null;
		
		bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher2);
		
		if (ProductId == 1) {
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.ic_launcher1);
		} else if (ProductId == 2) {
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.ic_launcher2);
		} else if (ProductId == 3) {
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.ic_launcher3);
		} else if (ProductId == 4) {
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),
					R.drawable.ic_launcher4);
		}
		
		return bitmap;
	}
	
	
	public void ForwardPasscode(Context mContext)
	{
		Intent sendIntent = new Intent();
		String text = "";
		PasscodeHandler passcodeHandler = new PasscodeHandler(mContext);
		if(ConnectionType()==ProductManager.MobileData)
		{
			text = mContext.getString(R.string.STR22029) + "\n\n";
			text = text + mContext.getString(R.string.STR22001) + ": #" + passcodeHandler.getEnableCode() + "\n";
			text = text + mContext.getString(R.string.STR22002) + ": #" + passcodeHandler.getDisableCode() + "\n\n";			
			text = text + mContext.getString(R.string.STR22024);
		}
		else
		{
			text = mContext.getString(R.string.STR22030) + "\n\n";
			text = text + mContext.getString(R.string.STR22001) + ": #" + passcodeHandler.getEnableCode() + "\n";
			text = text + mContext.getString(R.string.STR22002) + ": #" + passcodeHandler.getDisableCode() + "\n\n";			
			
			text = text + mContext.getString(R.string.STR22025);
		}
		sendIntent.setAction(Intent.ACTION_SEND);
		text = text + "\n\n" + Consts.URL + PackageName(ProductId);
		sendIntent.putExtra(Intent.EXTRA_TEXT, text);
		sendIntent.setType("text/plain");
		mContext.startActivity(sendIntent);
	}
	
	public void TellaFriend(Context mContext)
	{
		Intent sendIntent = new Intent();
		String text = "";
		if(ConnectionType()==ProductManager.MobileData)
		{
			text = mContext.getString(R.string.STR22024);
		}
		else
		{
			text = mContext.getString(R.string.STR22025);
		}
		sendIntent.setAction(Intent.ACTION_SEND);
		text = text + "\n\n" + Consts.URL + this.PackageName(ProductId);
		sendIntent.putExtra(Intent.EXTRA_TEXT, text);
		sendIntent.setType("text/plain");
		mContext.startActivity(sendIntent);
	}
			
	public void About(Context mContext)
	{
		//final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
		
		String appPackageName = PackageName(2);
		
		openGooglePlay(mContext, appPackageName);
	}
	
	public void openGooglePlay(Context mContext, String appPackageName)
	{	
		try {
		    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (android.content.ActivityNotFoundException anfe) {
		    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}
	
	public void Blog(Context mContext)
	{
		//1.6.5002		
		String url = "";
		
		if(Locale.getDefault().getISO3Language().equalsIgnoreCase("zho"))
		{
				url = "/";
		}
		
		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
		
	public void Facebook(Context mContext)
	{
		//1.6.5003
		try {
		    mContext.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/365439576994872")));
		   } catch (Exception e) {
			   mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("/")));				
		   }
	}
	
	public boolean showNotification()
	{
		boolean ret = true;
		
		/*
		if(ProductId==2||ProductId==4)
		{
			ret = true;
		}*/
		
		return ret;
	}

	public boolean Reply()
	{
		boolean ret = false;
		
		if(ProductId==2||ProductId==4)
		{
			ret = true;
		}
		
		return ret;
	}
	
	public boolean Log()
	{
		boolean ret = false;
		
		if(ProductId==2||ProductId==4)
		{
			ret = true;
		}
		
		return ret;
	}
	
	public boolean ShowAd()
	{
		boolean ret = false;
		
		if(ProductId==1||ProductId==3)
		{
			ret = true;
		}
		
		return ret;
	}
	
	public boolean Reset()
	{
		boolean ret = false;
		
		if(ProductId==2||ProductId==4)
		{
			ret = true;
		}
		
		return ret;
	}

	public String ConnectionType()
	{
		String ret = "";
		
		if(ProductId==1||ProductId==2)
		{
			ret = MobileData;
		}
		else
		{
			ret = Wifi;
		}		
		
		return ret;
	}
	
	private boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			
			if (ProductManager.ProductId == 1) {

				if (MobileDataFreeService.class.getName().equals(
						service.service.getClassName())) {
					return true;
				}

			}
			
			if (ProductManager.ProductId == 2) {

				if (MobileDataService.class.getName().equals(
						service.service.getClassName())) {
					return true;
				}

			}
			
			if (ProductManager.ProductId == 3) {

				if (WifiFreeService.class.getName().equals(
						service.service.getClassName())) {
					return true;
				}

			}
			
			if (ProductManager.ProductId == 4) {

				if (WifiService.class.getName().equals(
						service.service.getClassName())) {
					return true;
				}

			}
		}
		return false;
	}
	
	public void startService() {
		
		if (isServiceRunning()) {
			LogManager.log("MainActivity", "SMS Service is running");
		} else {
			Intent intent1 = new Intent(mContext, WifiService.class);
			if(ProductManager.ProductId==1)
			{
				intent1 = new Intent(mContext, MobileDataFreeService.class);
			}
			else if(ProductManager.ProductId==2)
			{
				intent1 = new Intent(mContext, MobileDataService.class);
			}
			else if(ProductManager.ProductId==3)
			{
				intent1 = new Intent(mContext, WifiFreeService.class);
			}
			else if(ProductManager.ProductId==4)
			{
				intent1 = new Intent(mContext, WifiService.class);
			}
			
			 mContext.startService(intent1);
		}
	}
	


	
}
