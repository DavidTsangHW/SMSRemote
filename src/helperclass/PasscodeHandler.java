/**
 * 
 */
package helperclass;

import java.util.Random;

import android.content.Context;

/**
 * @author David Tsang
 *
 */
public class PasscodeHandler {

	private final Context mContext;	
	private AppSettings appSettings;
	
	public PasscodeHandler(Context context) {
		 mContext = context;		
		 appSettings = new AppSettings(mContext);
	}
	
	public String getEnableCode()
	{
		return appSettings.getString(AppSettings.Key_Passcode, "");
	}
	
	public String getDisableCode()
	{
		String passcode = getEnableCode();
		String code = "";
				
		code = reverseCode(passcode);
		
		return code;
	}
	
	public boolean validateCode(String code)
	{
		boolean ret = true;
		
		if(code==reverseCode(code))
		{
			ret = false;
		}
		
		return ret;
	}
	
	public void saveCode(String code)
	{
		appSettings.putString(AppSettings.Key_Passcode, code);
	}

	public static String genCode() {

		int max = 99999;
		int min = 10001;
		
		String code = "";
		String revCode = "";
		
		while (code == revCode) {
			Random rand = new Random();
			int randomNum = rand.nextInt((max - min) + 1) + min;
			code = String.valueOf(randomNum);
			revCode = reverseCode(code);

			if (code == revCode) {
				code = String.valueOf(randomNum-1);
			}
		}
		
		return code;

	}
	
	public static String reverseCode(String code)
	{
		StringBuilder input1 = new StringBuilder();
		input1.append(code);
		input1=input1.reverse(); 
		
		return input1.toString();
		
	}
	 
		 
	

}
