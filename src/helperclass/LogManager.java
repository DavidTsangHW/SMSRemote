/**
 * 
 */
package helperclass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @author David
 *
 */
public class LogManager{
	
	private static Context mContext;
	
	
	public static void log(String tag, String text)
	{
        mContext = MyApplication.getAppContext();        
		if(Consts.debugMode)
		{
			Log.d(tag,text);
		}
		
	}
	
	public static void log(String tag, String text, String speech)
	{
        mContext = MyApplication.getAppContext();
        
		if(Consts.debugMode)
		{
			if(tag=="LocatorService")
			{
				Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
			}
			Log.d(tag,text);
		}
		
	}

}
