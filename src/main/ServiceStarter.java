package main;
/**
 * 
 */


import helperclass.ProductManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author David Tsang
 *
 */
public class ServiceStarter extends BroadcastReceiver {

	/**
	 * 
	 */
	public ServiceStarter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		
		ProductManager productManager = new ProductManager(context);
		productManager.startService();
		

	}

}
