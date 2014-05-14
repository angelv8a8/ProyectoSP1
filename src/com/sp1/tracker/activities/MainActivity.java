package com.sp1.tracker.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sp1.tracker.R;
import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.data.CategoriasManager;
import com.sp1.tracker.data.TrackerDatabaseHelper;
import com.sp1.tracker.fragments.TrackerContentFragment;
import com.sp1.tracker.fragments.FragmentAbout;

public class MainActivity extends ActionBarActivity {

	private ListView drawerList;
	private DrawerLayout drawerLayout;

	private String[] drawerOptions;
	
	public String categoria = ""; 
	public String indice = "";
	
	public String getCategoria()
	{
		return categoria;
	}
	
	public void setCategoria(String _categoria)
	{
		categoria = _categoria;
	}
	
	public TrackerContentFragment getTrackerContentFragment()
	{
		return (TrackerContentFragment) fragments[0];
	}
	
	private Fragment[] fragments = new Fragment[]{
		new TrackerContentFragment(),
		new FragmentAbout()
	};

	public void startEditingCategorias()
	{
		getTrackerContentFragment().startEditingCategorias();
	}
	
	public void endEditingCategorias()
	{
		getTrackerContentFragment().endEditingCategorias();
	}
	

	public void crearCategoria()
	{
		getTrackerContentFragment().crearCategoria();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawerList = (ListView) findViewById(R.id.leftDrawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerOptions = getResources().getStringArray(R.array.drawer_options);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerOptions));

		drawerList.setItemChecked(0, true);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		FragmentManager manager = getSupportFragmentManager();
	        manager.beginTransaction()
	        	    .add(R.id.contentFrame, fragments[0])
	        		.add(R.id.contentFrame, fragments[1])        		
	        		.hide(fragments[1])
	        	    .commit();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(drawerOptions[0]);
	}

	public void setContent(int pos) {
		
		ActionBar actionBar = getSupportActionBar(); 
		actionBar.setTitle(drawerOptions[pos]);
		
		Fragment toHide = null;
		Fragment toShow = null;
		switch (pos) {
			case 0:
				toHide = fragments[1];
				toShow = fragments[0];
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				break;
			case 1:
				toHide = fragments[0];
				toShow = fragments[1];
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				break;
		}
		
		FragmentManager fm = getSupportFragmentManager();
		
		fm.beginTransaction().hide(toHide).show(toShow).commit();
		drawerList.setItemChecked(pos, true);
		drawerLayout.closeDrawer(drawerList);
	}

	class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int pos,
				long arg3) {

			setContent(pos);

		}
	}
	
	

}