package com.sp1.tracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sp1.tracker.businessObjects.Categoria;

public class CategoriasManager extends DataManager {

	
	public CategoriasManager(SQLiteDatabase db) {
		super(db);
	}

	public static final String CATEGORIA_TABLE= "categoria";
	
	public static final String _ID = "_id"; // needed for android
	public static final String NOMBRE = "nombre";
	public static final String ORDEN = "orden";
	
	public static final String CREATE_CATEOGIA_TABLE_SQL= 
		"CREATE TABLE " + CATEGORIA_TABLE +
		" ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			NOMBRE + " STRING," +
			ORDEN + " INTEGER DEFAULT 1" +
			");";	
	
	public Cursor getAllImagesCursor() {
		return db.query(CATEGORIA_TABLE, null, null, null, null, null, null);
	}
	public Cursor getAlbumCursor() {
		return db.query(CATEGORIA_TABLE, null, null, null, null, null, ORDEN);
	}
	
	
	public boolean removeCategoria(long categoriaId)
	{
		String[] params = { Long.toString(categoriaId) }; 
		
		db.delete(CATEGORIA_TABLE, _ID + "=?", params);
		
		return true;
	}
	
	public long addCategoria(Categoria categoria) {
		ContentValues values = new ContentValues();
		
		values.put(NOMBRE, categoria.nombre);
		
		long categoriaId =  db.insert(CATEGORIA_TABLE, NOMBRE, values);
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
	
	

	private Categoria getCategoriaByQuery(String[] params, String query) {
		Categoria categoria = null;
		Cursor result = db.query(CATEGORIA_TABLE, null, query, params, null, null, null);
		try {
			if(result.getCount() == 1) {
				result.moveToFirst();
				categoria = new Categoria();
				
				categoria.nombre = result.getString(result.getColumnIndex(NOMBRE));
				categoria.ID = result.getInt(result.getColumnIndex(_ID));
			
			}
		} finally {
			result.close();
		}
		return categoria;
	}
	
	public boolean categoriaExists(String albumName) {
		return getCategoriaByName(albumName) != null;
	}
	
	public Categoria getCategoriaByName(String nombre) {
		String[] params = { nombre };
		String query = NOMBRE +"=?";
		return getCategoriaByQuery(params, query);
	}
	
	public Categoria getCategoriaById(long id) {
		String[] params = { Long.toString(id) };
		String query = _ID +"=?";
		return getCategoriaByQuery(params, query);
	}

	
	
}
