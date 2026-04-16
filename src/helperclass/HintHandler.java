/**
 * 
 */
package helperclass;

import com.codemonkey.smsmobiledatafree.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.CheckBox;

/**
 * @author David Tsang
 *
 */
public class HintHandler implements DialogInterface.OnClickListener{

	/**
	 * 
	 */
	private final Context mContext;
	private AlertDialog.Builder alert;
	private final AppSettings appSettings;
	private String PrefKey;
	private CheckBox input;
		
	public HintHandler(Context context) {
		this.mContext = context;
		appSettings = new AppSettings(mContext);

	}
	
	
	
	public void showHint(String key, String title, String text)
	{
		showDialog(key, title, text, true);
	}
	
	public void showDialog(String key, String title, String text, Boolean canHide)
	{
		
		alert = new AlertDialog.Builder(mContext);
				
		if(key!=null)
		{
			if(!appSettings.getBoolean(key,true))
			{
				return;
			}
		}
		
	    PrefKey = key;

	    /*
	    if(!appSettings.getBoolean(AppSettings.Key_ShowHint, true)&&canHide)
	    {
	    	return;
	    }
	    */	    

    	alert.setTitle(title);
    	
    	if(text.equals(""))
    	{
    		return;
    	}
    	
    	
    	
    	alert.setMessage(text);
    	//alert.setIcon(R.drawable.ic_questionmark_dark);
    	
    	alert.setPositiveButton(mContext.getString(R.string.STR40002), this);
    	if(key!=null)
    	{
    		if(canHide)
    		{
    			input = new CheckBox(mContext);		
    			input.setText(mContext.getString(R.string.STR40001));
    			alert.setView(input);
    			//alert.setNegativeButton(mContext.getString(R.string.STR40003), this); 
    		}
    	}
    	alert.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
 
		if(which==AlertDialog.BUTTON_NEGATIVE)
		{
			 appSettings.putBoolean(AppSettings.Key_ShowHint, false);
		}
		
		if(input!=null)
		{
			if(input.isChecked())
			{
				appSettings.putBoolean(PrefKey, false); 				
			}
		}
		
		dialog.cancel();
		
	}

}
