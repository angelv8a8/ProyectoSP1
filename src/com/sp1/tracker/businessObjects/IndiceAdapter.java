package com.sp1.tracker.businessObjects;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sp1.tracker.R;
import com.sp1.tracker.data.CategoriaIndiceManager;
import com.sp1.tracker.data.CategoriasManager;
import com.sp1.tracker.data.IndicesManager;
import com.sp1.tracker.data.TrackerDatabaseHelper;

public class IndiceAdapter extends ArrayAdapter<CategoriaIndice>{

    Context context; 
    int layoutResourceId;    
    ArrayList<CategoriaIndice> data = null;
    
    public IndiceAdapter(Context context, int layoutResourceId, ArrayList<CategoriaIndice> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        IndiceHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new IndiceHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            holder.imgAgregarRecord = (ImageView)row.findViewById(R.id.imgAgregarRecord);
            holder.imgVerGrafica =  (ImageView)row.findViewById(R.id.imgVerGrafica);
            
            
            
            holder.imgVerGrafica.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Toast.makeText(getContext(), "Ver Grafica", Toast.LENGTH_SHORT).show();
				}
			});
            
            row.setTag(holder);
        }
        else
        {
            holder = (IndiceHolder)row.getTag();
        }
        
        CategoriaIndice indice = data.get(position);
        holder.txtTitle.setText(indice.nombre);
        holder.imgIcon.setImageResource(indice.icon);
        holder.categoriaIndiceId = indice.ID;
        
        if(indice.addIcon != -1)
        	holder.imgAgregarRecord.setImageResource(indice.addIcon);
        if(indice.iconGrafica != -1)
        	holder.imgVerGrafica.setImageResource(indice.iconGrafica);
        
        return row;
    }
    
    class IndiceHolder implements OnClickListener
    {
        ImageView imgIcon;
        TextView txtTitle;
        ImageView imgAgregarRecord;
        ImageView imgVerGrafica;
        int categoriaIndiceId;
		@Override
		public void onClick(View v) {
			
			
			if(v.getId() == R.id.imgAgregarRecord)
			{
			Toast.makeText( getContext(), "Agregar", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

			alert.setTitle("Nuevo Registro");
			alert.setMessage("Ingresa el valor del nuevo registro");

			// Set an EditText view to get user input 
			final EditText input = new EditText(getContext());
			alert.setView(input);
			final int nCategoriaIndiceId = categoriaIndiceId ;
			
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString();
			  
			  		if(!value.equalsIgnoreCase(""))
			  		{
			  			
						/*TrackerDatabaseHelper databaseHelper = new TrackerDatabaseHelper(getContext() );
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
			        		Toast.makeText(getContext(), "Cateogr’a Creada", Toast.LENGTH_LONG).show();
				        	
			        		//CargarLista();
			        		
				        }
				        else
				        {
				        	Toast.makeText(getContext(), "Registro Invalido", Toast.LENGTH_SHORT).show();
				        }*/
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
    }
        
        
        
    }
