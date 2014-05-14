package com.sp1.tracker.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

import com.sp1.tracker.R;
import com.sp1.tracker.activities.MainActivity;
import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.data.CategoriasManager;
import com.sp1.tracker.data.TrackerDatabaseHelper;

public class TrackerContentFragment extends Fragment implements TabListener { 
	
	Fragment fragmentoIndices = new IndicesListFragment();
	
	private int categoriasCount = 0;
	
	private boolean editandoCategorias = false;
	
	private boolean cargandoCategorias = false;
	
	private ArrayList<Long> categoriasIds;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	 
		 return inflater.inflate(R.layout.fragment_tracker_content, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		fragmentoIndices.setHasOptionsMenu(true);
		
		cargarTabs();

        
        FragmentManager manager =  ((MainActivity)getActivity()).getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.main_content, fragmentoIndices)     		        	   
        	    .commit();		
	}

	private void cargarTabs()
	{
		
		cargandoCategorias = true;
		
		final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.removeAllTabs();
		
		categoriasIds = new ArrayList<Long>();
		
		TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity().getApplicationContext());
        CategoriasManager categoriasManager = databaseHelper.getCategoriasManager();
        Cursor cur = categoriasManager.getAlbumCursor();
        categoriasCount = cur.getCount();
        
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
        	
        	categoriasIds.add(cur.getLong(0));
        	
        	String tabName = cur.getString(1);
        	
        	if(editandoCategorias)
        		tabName = " - " + tabName;
        	
        	actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabName)
                            .setTabListener(this) );
       	    cur.moveToNext();
        }

    	actionBar.addTab(
                actionBar.newTab()
                        .setText("+")
                        .setTabListener(this));
   		
    	if(categoriasCount > 0)
        {
        	((MainActivity)getActivity()).setCategoria(actionBar.getTabAt(0).getText().toString());
        	actionBar.selectTab(actionBar.getTabAt(0));
        }
    	
    	cargandoCategorias = false;
    	
	}
	
	
		
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {	
	}

	
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		if(cargandoCategorias)
			return;
		
		if(tab.getPosition() < categoriasCount )
		{
			
			if(!editandoCategorias)
			{
				FragmentManager manager =  ((MainActivity)getActivity()).getSupportFragmentManager();
				manager.beginTransaction().remove(fragmentoIndices).commit();
				//manager.beginTransaction().repl
				//manager.beginTransaction().remove(R.id.main_content,fragmentoIndices).commit();
				((MainActivity)getActivity()).setCategoria(tab.getText().toString());
				fragmentoIndices = new IndicesListFragment();
				fragmentoIndices.setHasOptionsMenu(true);
				
				
		        manager.beginTransaction()
		        	    .add(R.id.main_content, fragmentoIndices)     		        	   
		        	    .commit();
				
			}
			else
			{
				TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity().getApplicationContext());
		        CategoriasManager categoriasManager = databaseHelper.getCategoriasManager();
		        categoriasManager.removeCategoria(categoriasIds.get(tab.getPosition()));
		        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
		        editandoCategorias = false;
		        actionBar.removeTab(tab);
		        editandoCategorias = true;
			}
		}
		else 
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

			alert.setTitle("Nueva Categoria");
			alert.setMessage("Ingresa el nombre de la nueva categor’a");

			// Set an EditText view to get user input 
			final EditText input = new EditText(getActivity());
			alert.setView(input);
			
			final boolean categoriaCreada = false;

			
			
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  
			  		if(!value.equalsIgnoreCase(""))
			  		{
			  			TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity().getApplicationContext());
				        CategoriasManager categoriasManager = databaseHelper.getCategoriasManager();
				        
				        if(categoriasManager.categoriaExists(value))
				        {
				        	Toast.makeText(getActivity(), "La categor’a ya existe", Toast.LENGTH_LONG).show();
				        	
				        }
				        else
				        {
				        	categoriasManager.addCategoria(new Categoria(value));
				        	
				        	cargarTabs();
				        }
			  		}


			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();
		}
		
	}
	
	public void crearCategoria()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Nueva Categoria");
		alert.setMessage("Ingresa el nombre de la nueva categor’a");

		// Set an EditText view to get user input 
		final EditText input = new EditText(getActivity());
		alert.setView(input);
		
		final boolean categoriaCreada = false;

		
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  
		  		if(!value.equalsIgnoreCase(""))
		  		{
		  			TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity().getApplicationContext());
			        CategoriasManager categoriasManager = databaseHelper.getCategoriasManager();
			        
			        if(categoriasManager.categoriaExists(value))
			        {
			        	Toast.makeText(getActivity(), "La categor’a ya existe", Toast.LENGTH_LONG).show();
			        	
			        }
			        else
			        {
			        	categoriasManager.addCategoria(new Categoria(value));
			        	
			        	cargarTabs();
			        }
		  		}


		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
	}

	public void startEditingCategorias()
	{
		Toast.makeText(getActivity(), "Editando categorias", Toast.LENGTH_LONG).show();
		
		final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
		
		
		for(int i = 0; i < actionBar.getTabCount()-1; i++)
		{
			actionBar.getTabAt(i).setText(" - " +actionBar.getTabAt(i).getText());
		}
		editandoCategorias = true;
				
	}
	
	public void endEditingCategorias()
	{
		Toast.makeText(getActivity(), "Terminando edici—n", Toast.LENGTH_LONG).show();
		
		editandoCategorias = false;
		
		cargarTabs();
		
		
	}
	
		

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
	}

	public void setContent(int tab) {		

	}

}