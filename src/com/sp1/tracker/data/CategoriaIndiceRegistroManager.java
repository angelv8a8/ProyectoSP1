package com.sp1.tracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sp1.tracker.businessObjects.CategoriaIndice;
import com.sp1.tracker.businessObjects.CategoriaIndiceRegistro;

public class CategoriaIndiceRegistroManager extends DataManager {

	
	public CategoriaIndiceRegistroManager(SQLiteDatabase db) {
		super(db);
	}

	public static final String CATEGORIA_INDICE_REGISTRO_TABLE= "categoria_indice_registro";
	
	public static final String _ID = "_id"; // needed for android
	public static final String CATEGORIA_INDICE_ID = "categoria_indice_id";
	public static final String VALOR = "valor";
	public static final String FECHA = "record_date";
	
	public static final String CREATE_CATEOGIA_INDICE_REGISTRO_TABLE_SQL= 
		"CREATE TABLE " + CATEGORIA_INDICE_REGISTRO_TABLE +
		" ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			CATEGORIA_INDICE_ID + " INTEGER NOT NULL," +
			VALOR + " REAL NOT NULL," +
			FECHA + " INTEGER NOT NULL" +
			");";	
	
	public Cursor getAllIndicesCursor() {
		return db.query(CATEGORIA_INDICE_REGISTRO_TABLE, null, null, null, null, null, null);
	}
	public Cursor getIndicesCursor() {
		return db.query(CATEGORIA_INDICE_REGISTRO_TABLE, null, null, null, null, null, null);
	}
	
	
	public long addCategoriaIndiceRegistro(CategoriaIndiceRegistro categoriaIndiceReistro) {
		ContentValues values = new ContentValues();
		
		values.put(CATEGORIA_INDICE_ID, categoriaIndiceReistro.categoriaIndiceId);
		values.put(VALOR, categoriaIndiceReistro.valor);
		values.put(FECHA, categoriaIndiceReistro.fecha.toString() );
		
		long categoriaIndiceRegistroId =  db.insert(CATEGORIA_INDICE_REGISTRO_TABLE, CATEGORIA_INDICE_ID, values);
		/*if(categoriaId > 0 && categoria.getAlbumImages().size() > 0) {
			// Storing the album was successful and this album has images associated with it
			PicasaImageManager imageTable = new PicasaImageManager(this.db);
			ImageAlbumRelationshipManager imageAlbumRelationships = new ImageAlbumRelationshipManager(this.db);
			for(PicasaImage image : categoria.getAlbumImages()) {
				long imageId = imageTable.addOrUpdateImage(image);
				boolean storedNewRelation = imageAlbumRelationships.addImageAlbumRelation(categoriaId, imageId);
				if(!storedNewRelation) {
					Log.e("ca.christopersaunders.tutorials.sqlite.db.PicasaAlbumManager", "Could not store relation for album: " + categoria.getTitle() + " and image: " + image.getThumbnailLocation());
					Log.e("ca.christopersaunders.tutorials.sqlite.db.PicasaAlbumManager", "Aborting...");
					System.exit(1);
				}
			}
		}*/
		
		return categoriaIndiceRegistroId;
	}
	
/*

	private CategoriaIndice getCategoriaIndiceByQuery(String[] params, String query) {
		CategoriaIndice categoriaIndice = null;
		Cursor result = db.query(CATEGORIA_INDICE_TABLE, null, query, params, null, null, null);
		try {
			if(result.getCount() == 1) {
				result.moveToFirst();
				categoriaIndice = new CategoriaIndice();
				
				categoriaIndice.categoriaId = result.getInt(result.getColumnIndex(CATEGORIA_ID));
				categoriaIndice.indiceId = result.getInt(result.getColumnIndex(INDICE_ID));
				categoriaIndice.ID = result.getInt(result.getColumnIndex(_ID));
			}
		} finally {
			result.close();
		}
		return categoriaIndice;
	}
	
	
	public Cursor getCategoriaIndiceByCategoria(int categoriaId) {
		String[] params = { Integer.toString(categoriaId) };
		String query = CATEGORIA_ID +"=?";
		
		
		return db.query(CATEGORIA_INDICE_TABLE, null, query, params, null, null, null);
		
	}*/
}
