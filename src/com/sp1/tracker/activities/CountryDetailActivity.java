package com.sp1.tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sp1.tracker.R;

public class CountryDetailActivity extends FragmentActivity {

	private static String pais = "";
	public  static final String COUNTRY_NAME = "pais";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country_detail);
		
		pais = getIntent().getStringExtra(COUNTRY_NAME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static String getPais()
	{
		return pais; 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_eliminar:
			
			if(!pais.equals(""))
			{
				String url  = "http://es.m.wikipedia.org/wiki/"+ pais;
				String message =getString( R.string.msg_share, pais, url);
				Intent share = new Intent();
				share.setAction(Intent.ACTION_SEND);
				share.putExtra(Intent.EXTRA_TEXT, message);
				share.setType("text/plain");
				startActivity(Intent.createChooser(share, getString(R.string.action_share)));
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
		
		
	}
}
