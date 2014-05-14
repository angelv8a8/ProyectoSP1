package com.sp1.tracker.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sp1.tracker.R;
import com.sp1.tracker.activities.CountryDetailActivity;
import com.sp1.tracker.activities.MainActivity;
import com.sp1.tracker.activities.NuevoIndice;
import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.businessObjects.CategoriaIndice;
import com.sp1.tracker.businessObjects.Indice;
import com.sp1.tracker.businessObjects.IndiceAdapter;
import com.sp1.tracker.data.CategoriaIndiceManager;
import com.sp1.tracker.data.CategoriasManager;
import com.sp1.tracker.data.IndicesManager;
import com.sp1.tracker.data.TrackerDatabaseHelper;


public class IndicesListFragment extends Fragment implements
													OnItemClickListener {

	String categoriaNombre = "";
	ListView  list  ;
	int indicesCount;
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CargarLista();
		//registerForContextMenu(lista);
		
	}
	
	private void CargarLista()
	{
		ArrayList<CategoriaIndice> lista = new ArrayList<CategoriaIndice>();
		
		categoriaNombre = ((MainActivity)getActivity()).getCategoria();
		
		TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity().getApplicationContext());
        CategoriasManager categoriaManager = databaseHelper.getCategoriasManager();
        
        CategoriaIndiceManager cim = databaseHelper.getCategoriasIndicesManager();
        
        if(!categoriaNombre.equalsIgnoreCase(""))	
        {
        
	        Cursor cur = cim.getCategoriaIndiceByCategoria(categoriaManager.getCategoriaByName(categoriaNombre).ID);
	        
	        indicesCount = cur.getCount();
	        
	        cur.moveToFirst();
	        
	        //IndicesManager im = databaseHelper.getIndicesManager();
	        
	        while (cur.isAfterLast() == false) {
	        	CategoriaIndice ci = new CategoriaIndice();
	        	ci.ID = cur.getInt(0);
	        	ci.categoriaId = cur.getInt(1);
	        	ci.indiceId = cur.getInt(2);
	        	ci.nombre = cur.getString(3);
	        	
	        	lista.add( ci);
	       	    cur.moveToNext();
	        }
        }
        
        CategoriaIndice nuevo = new CategoriaIndice();
        nuevo.addIcon = -1;
        nuevo.nombre = "+ Nuevo";
        nuevo.iconGrafica = -1;
		lista.add(nuevo);
		
		IndiceAdapter adapter = new IndiceAdapter(getActivity(), R.layout.listview_row_adapter, lista);
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		//		android.R.layout.simple_list_item_1, lista);

		//ListView listaV = (ListView) findViewById(R.id.lista);

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v,
					int pos, long arg3) {

				//categoriaNombre = adapterView.getItemAtPosition(pos).toString();
			
				if(pos == indicesCount)
				{
					Toast.makeText(getActivity(), "Crear nuevo",Toast.LENGTH_LONG).show();
					crearIndice();
				}
			
			}
		});
        
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =inflater.inflate(R.layout.fragment_countries_list, container, false);
		
		list = (ListView)view.findViewById(R.id.lista);
		return view;	
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		
		//if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
		//	MenuItem itm = menu.getItem(menu.size() - 1);
		//	itm.setVisible(false);
		//}
		super.onPrepareOptionsMenu(menu);
	}
	
	
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_eliminar:
				
				if(!this.categoriaNombre.equals(""))
				{
					((MainActivity)getActivity()).startEditingCategorias();
					
					//String url  = "http://es.m.wikipedia.org/wiki/"+ categoriaNombre;
					//String message =getString( R.string.msg_share, categoriaNombre, url);
					//Intent share = new Intent();
					//share.setAction(Intent.ACTION_SEND);
					///share.putExtra(Intent.EXTRA_TEXT, message);
					//share.setType("text/plain");
					//startActivity(Intent.createChooser(share, getString(R.string.action_share)));
				}
				return true;
			case R.id.action_terminar:
				((MainActivity)getActivity()).endEditingCategorias();
				return true;
			case R.id.action_crear:
				((MainActivity)getActivity()).crearCategoria();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
		
		
	}
	

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(menu.size()==0)
			inflater.inflate(R.menu.main, menu);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		
		categoriaNombre = ((TextView)info.targetView).getText().toString();
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		return onOptionsItemSelected(item);	
	}
	
	public void crearIndice()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Nuevo Indice");
		alert.setMessage("Ingresa el nombre de el nuevo indice");

		// Set an EditText view to get user input 
		final EditText input = new EditText(getActivity());
		alert.setView(input);
		final String catN = categoriaNombre;
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
		  
		  		if(!value.equalsIgnoreCase(""))
		  		{
		  			
					TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getActivity());
			        CategoriasManager cm = databaseHelper.getCategoriasManager();
			        if(cm.categoriaExists(catN))
			        {
			        	CategoriaIndiceManager cim = databaseHelper.getCategoriasIndicesManager();
			        	
			        	IndicesManager im = databaseHelper.getIndicesManager();
			        	
			        	Indice indice;
			        	if( !im.IndiceExists(value ))
			        	{	
			        		im.addIndice(new Indice(value));
			        		
			        	}
			        	indice = im.getIndiceByName(value);
			        	Categoria cat = cm.getCategoriaByName(catN);
		        		cim.addCategoriaIndice(new CategoriaIndice(cat.ID, indice.ID));
		        		Toast.makeText(getActivity().getApplicationContext(), "Cateogr’a Creada", Toast.LENGTH_LONG).show();
			        	
		        		CargarLista();
		        		
			        }
			        else
			        {
			        	Toast.makeText(getActivity().getApplicationContext(), "Indice Invalido", Toast.LENGTH_SHORT).show();
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
