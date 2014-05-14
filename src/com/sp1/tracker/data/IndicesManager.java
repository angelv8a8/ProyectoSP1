package com.sp1.tracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sp1.tracker.businessObjects.Categoria;
import com.sp1.tracker.businessObjects.Indice;
import com.sp1.tracker.businessObjects.Indice;

public class IndicesManager extends DataManager {

	
	public IndicesManager(SQLiteDatabase db) {
		super(db);
	}

	public static final String INDICE_TABLE= "indice";
	
	public static final String _ID = "_id"; // needed for android
	public static final String NOMBRE = "nombre";
	public static final String ORDEN = "orden";
	
	public static final String CREATE_INDICE_TABLE_SQL= 
		"CREATE TABLE " + INDICE_TABLE +
		" ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			NOMBRE + " STRING," +
			ORDEN + " INTEGER DEFAULT 1" +
			");";	
	
	public Cursor getAllIndicesCursor() {
		return db.query(INDICE_TABLE, null, null, null, null, null, null);
	}
	public Cursor getIndicesCursor() {
		return db.query(INDICE_TABLE, null, null, null, null, null, ORDEN);
	}
	
	
	public long addIndice(Indice indice) {
		
		if(IndiceExists(indice.nombre))
			return getIndiceByName(indice.nombre).ID;
		
		ContentValues values = new ContentValues();
		
		values.put(NOMBRE, indice.nombre);
		
		long IndiceId =  db.insert(INDICE_TABLE, NOMBRE, values);
		/*if(IndiceId > 0 && Indice.getAlbumImages().size() > 0) {
			// Storing the album was successful and this album has images associated with it
			PicasaImageManager imageTable = new PicasaImageManager(this.db);
			ImageAlbumRelationshipManager imageAlbumRelationships = new ImageAlbumRelationshipManager(this.db);
			for(PicasaImage image : Indice.getAlbumImages()) {
				long imageId = imageTable.addOrUpdateImage(image);
				boolean storedNewRelation = imageAlbumRelationships.addImageAlbumRelation(IndiceId, imageId);
				if(!storedNewRelation) {
					Log.e("ca.christopersaunders.tutorials.sqlite.db.PicasaAlbumManager", "Could not store relation for album: " + Indice.getTitle() + " and image: " + image.getThumbnailLocation());
					Log.e("ca.christopersaunders.tutorials.sqlite.db.PicasaAlbumManager", "Aborting...");
					System.exit(1);
				}
			}
		}*/
		
		return IndiceId;
	}

	private Indice getIndiceByQuery(String[] params, String query) {
		Indice Indice = null;
		Cursor result = db.query(INDICE_TABLE, null, query, params, null, null, null);
		try {
			if(result.getCount() == 1) {
				result.moveToFirst();
				Indice = new Indice();
				
				Indice.nombre = result.getString(result.getColumnIndex(NOMBRE));
				Indice.ID = result.getInt(result.getColumnIndex(_ID));
			
			}
		} finally {
			result.close();
		}
		return Indice;
	}
	
	public boolean IndiceExists(String albumName) {
		return getIndiceByName(albumName) != null;
	}
	
	public Indice getIndiceByName(String nombre) {
		String[] params = { nombre };
		String query = NOMBRE +"=?";
		return getIndiceByQuery(params, query);
	}
	
	public Indice getIndiceById(int indiceId) {
		String[] params = { Integer.toString(indiceId) };
		String query = _ID +"=?";
		return getIndiceByQuery(params, query);
	}
	
}
