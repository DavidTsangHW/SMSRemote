package ui;

import helperclass.Consts;
import helperclass.ProductManager;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.codemonkey.smsmobiledatafree.R;

import database.LogEvent;

public class LogAdapter extends ArrayAdapter<LogEvent> implements SectionIndexer {
  private final Context context;
  private final List<LogEvent> values;
  private final ProductManager productManager;
  
  
  public LogAdapter(
		  Context context, 
		  List<LogEvent> values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.values = values;
    productManager = new ProductManager(context);
    
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View rowView;
    
    LogEvent value = new LogEvent();
    
    value = values.get(position);
        
    Date dt = new Date(value.getutctime());

    String timeText = dt.toLocaleString();
    
    rowView = inflater.inflate(R.layout.row_log, parent, false);

    TextView textView = (TextView) rowView.findViewById(R.id.line1);
    TextView textView3 = (TextView) rowView.findViewById(R.id.timeLine); 
    if(Consts.debugMode)
    {
    	textView.setText(Consts.maskedPhone);        
    }
    else
    {
    	textView.setText(value.gettag());        
    }
    textView3.setText(timeText);
    
    setIcon(rowView, value);
	        
    return rowView;
  }

	public void setIcon(View rowView, LogEvent value) {

		boolean enabled = value.enabled();

		Bitmap bitmap = null;

		ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

		if (enabled) {
			if(productManager.ConnectionType().equals(ProductManager.MobileData))
			{
				bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_mobile_data_dark);
			}
			
			if(productManager.ConnectionType().equals(ProductManager.Wifi))
			{
				bitmap = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.ic_wifi_dark);
			}
			
		} else {
			if(productManager.ConnectionType().equals(ProductManager.MobileData))
			{
				bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_mobile_data_holo);
			}
			
			if(productManager.ConnectionType().equals(ProductManager.Wifi))
			{
				bitmap = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.ic_wifi_holo);
			}
		}

		icon.setImageBitmap(bitmap);

	}
  
@Override
public Object[] getSections() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getPositionForSection(int section) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getSectionForPosition(int position) {
	// TODO Auto-generated method stub
	return 0;
}


} 