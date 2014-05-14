package com.sp1.tracker.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp1.tracker.R;
import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.businessObjects.CategoriaIndice;
import com.sp1.tracker.businessObjects.Indice;
import com.sp1.tracker.data.CategoriaIndiceManager;
import com.sp1.tracker.data.CategoriasManager;
import com.sp1.tracker.data.IndicesManager;
import com.sp1.tracker.data.TrackerDatabaseHelper;

public class NuevoIndice extends Activity implements OnClickListener{

	private String categoria = "";
	
	public static final String CATEGORIA = "categoria";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_indice);
		// Show the Up button in the action bar.
		setupActionBar();
		
		categoria = getIntent().getStringExtra(CATEGORIA);
		
		TextView txt = (TextView)(findViewById(R.id.lbl_categoria ));
		txt.setText(categoria);
		
		
		Button btn = (Button)findViewById(R.id.btnNuevoIndice);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText txt = (EditText)findViewById(R.id.txtNuevoIndice);
				
				if(!txt.getText().toString().equalsIgnoreCase("")){

					
					String indiceNombre = txt.getText().toString();
					TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getApplicationContext());
			        CategoriasManager cm = databaseHelper.getCategoriasManager();
			        if(cm.categoriaExists(categoria))
			        {
			        	CategoriaIndiceManager cim = databaseHelper.getCategoriasIndicesManager();
			        	
			        	IndicesManager im = databaseHelper.getIndicesManager();
			        	
			        	Indice indice;
			        	if( !im.IndiceExists(indiceNombre ))
			        	{	
			        		im.addIndice(new Indice(indiceNombre));
			        		
			        	}
			        	indice = im.getIndiceByName(indiceNombre);
			        	Categoria cat = cm.getCategoriaByName(categoria);
		        		cim.addCategoriaIndice(new CategoriaIndice(cat.ID, indice.ID));
		        		Toast.makeText(getApplicationContext(), "Cateogr’a Creada", Toast.LENGTH_LONG).show();
			        	finish();
			        }
			        else
			        {
			        	Toast.makeText(getApplicationContext(), "Cateogr’a Invalida", Toast.LENGTH_SHORT).show();
			        }
			        
			        
			        
					
				}else
				{
					Toast.makeText(getApplicationContext(), "El nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_indice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() ==  R.id.btnNuevoIndice )
		{
			EditText txt = (EditText)findViewById(R.id.txtNuevoIndice);
			
			if(!txt.getText().toString().equalsIgnoreCase("")){

				
				String indiceNombre = txt.getText().toString();
				TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getApplicationContext());
		        CategoriasManager cm = databaseHelper.getCategoriasManager();
		        if(cm.categoriaExists(categoria))
		        {
		        	CategoriaIndiceManager cim = databaseHelper.getCategoriasIndicesManager();
		        	
		        	IndicesManager im = databaseHelper.getIndicesManager();
		        	
		        	Indice indice;
		        	if( !im.IndiceExists(indiceNombre ))
		        	{	
		        		im.addIndice(new Indice(indiceNombre));
		        		
		        	}
		        	indice = im.getIndiceByName(indiceNombre);
		        	Categoria cat = cm.getCategoriaByName(categoria);
	        		cim.addCategoriaIndice(new CategoriaIndice(cat.ID, indice.ID));
	        		Toast.makeText(getApplicationContext(), "Cateogr’a Creada", Toast.LENGTH_LONG).show();
		        	this.finish();
		        }
		        else
		        {
		        	Toast.makeText(getApplicationContext(), "Cateogr’a Invalida", Toast.LENGTH_SHORT).show();
		        }
		        
		        
		        
				
			}else
			{
				Toast.makeText(getApplicationContext(), "El nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

}
