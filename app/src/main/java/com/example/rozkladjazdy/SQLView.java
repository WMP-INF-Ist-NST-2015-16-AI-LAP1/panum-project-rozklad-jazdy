package com.example.rozkladjazdy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends Activity{
	
	@Override
	protected void  onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		BazaDanych info = new BazaDanych(this);
		info.open();
		String dane = info.getDane();
		info.close();
		tv.setText(dane);
		
		
	}

}
