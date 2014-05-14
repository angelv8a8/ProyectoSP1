package com.sp1.tracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.businessObjects.CategoriaIndice;
import com.sp1.tracker.businessObjects.Indice;

public class CategoriaIndiceManager extends DataManager {

	
	public CategoriaIndiceManager(SQLiteDatabase db) {
		super(db);
	}

	public static final String CATEGORIA_INDICE_TABLE= "categoria_indice";
	
	public static final String _ID = "_id"; // needed for android
	public static final String CATEGORIA_ID = "categoria_id";
	public static final String INDICE_ID = "indice_id";
	
	public static final String CREATE_CATEOGIA_TABLE_SQL= 
		"CREATE TABLE " + CATEGORIA_INDICE_TABLE +
		" ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			CATEGORIA_ID + " INTEGER NOT NULL," +
			INDICE_ID + " INTEGER NOT NULL" +
			");";	
	
	public Cursor getAllIndicesCursor() {
		return db.query(CATEGORIA_INDICE_TABLE, null, null, null, null, null, null);
	}
	public Cursor getIndicesCursor() {
		return db.query(CATEGORIA_INDICE_TABLE, null, null, null, null, null, null);
	}
	
	
	public long addCategoriaIndice(CategoriaIndice categoriaIndice) {
		ContentValues values = new ContentValues();
		
		values.put(CATEGORIA_ID, categoriaIndice.categoriaId);
		values.put(INDICE_ID, categoriaIndice.indiceId);
		
		long categoriaId =  db.insert(CATEGORIA_INDICE_TABLE, CATEGORIA_ID, values);
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
		
		return categoriaId;
	}
	


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
		String query = "SELECT ci."  + _ID + " , " + CATEGORIA_ID + ", ci." + INDICE_ID + " , i.nombre "
				+ "FROM " + CATEGORIA_INDICE_TABLE + " ci "
				+ " INNER JOIN " + IndicesManager.INDICE_TABLE + " i"
				+ " ON ci.indice_id = i." + _ID + " "
				+ " WHERE ci.categoria_id =  ?";
		

		return db.rawQuery(query, params );
		
				
	}
}
