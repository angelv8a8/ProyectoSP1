package com.sp1.tracker.data;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TrackerDatabaseHelper {


	private Context context;
	private static SQLiteDatabase db;
	
	public TrackerDatabaseHelper(Context context) {
		this.context = context;
		if(db == null || !db.isOpen()) {
			db = new DatabaseHelper(this.context).getWritableDatabase();
		}
	}
	
	/**
	 * Utility constructor to make testing easier to perform
	 * @param context
	 * @param databaseName
	 */
	protected TrackerDatabaseHelper(Context context, String databaseName) {
		this.context = context;
		if(db == null || !db.isOpen()) {
			db = new DatabaseHelper(context, databaseName).getWritableDatabase();
		}
	}
	
	public CategoriasManager getCategoriasManager() {
		return new CategoriasManager(db);
	}
	
	public IndicesManager getIndicesManager() {
		return new IndicesManager(db);
	}
	
	public CategoriaIndiceManager getCategoriasIndicesManager() {
		return new CategoriaIndiceManager(db);
	}
	
	
	
	
private class DatabaseHelper extends SQLiteOpenHelper {
		
		private static final String DATABASE = "tracker.sqlite";
		private static final int VERSION = 3;

		
		private HashMap<String, String> tableNamesAndSQL = new HashMap<String, String>();
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE, null, VERSION);
		}
		
		public DatabaseHelper(Context context, String databaseName) {
			super(context, databaseName, null, VERSION);
		}
		
		public CategoriasManager getCategoriasAdapter()
		{
			return new CategoriasManager(db);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			populateTableNamesAndSQL(); // This would be done with a dictionary/hash in a language such as python
			String formatString = "SELECT name FROM sqlite_master WHERE type='table' AND name='%s'";
			for(String tableName : tableNamesAndSQL.keySet()) {
				Cursor c = db.rawQuery(String.format(formatString, tableName), null);
				try {
					if(c.getCount() == 0) {
						// No table exists, create a new one
						db.execSQL(tableNamesAndSQL.get(tableName));
					}
				} finally {
					c.close();
				}
			}
		}
		
		private void populateTableNamesAndSQL() {
			tableNamesAndSQL.put(CategoriasManager.CATEGORIA_TABLE, CategoriasManager.CREATE_CATEOGIA_TABLE_SQL );
			tableNamesAndSQL.put(CategoriaIndiceManager.CATEGORIA_INDICE_TABLE, CategoriaIndiceManager.CREATE_CATEOGIA_TABLE_SQL );
			tableNamesAndSQL.put(IndicesManager.INDICE_TABLE, IndicesManager.CREATE_INDICE_TABLE_SQL );
			tableNamesAndSQL.put(CategoriaIndiceRegistroManager.CATEGORIA_INDICE_REGISTRO_TABLE, CategoriaIndiceRegistroManager.CREATE_CATEOGIA_INDICE_REGISTRO_TABLE_SQL );
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Tracker.DatabaseHelper", "Upgrade required. Migrating from version " + oldVersion + " to " + newVersion + " will destroy existing data!");
			String dropTableSQL = "DROP TABLE IF EXISTS ";
			db.execSQL(dropTableSQL + CategoriasManager.CATEGORIA_TABLE);
			db.execSQL(dropTableSQL + IndicesManager.INDICE_TABLE ); 
			db.execSQL(dropTableSQL + CategoriaIndiceManager.CATEGORIA_INDICE_TABLE );
			db.execSQL(dropTableSQL + CategoriaIndiceRegistroManager.CATEGORIA_INDICE_REGISTRO_TABLE );
			onCreate(db);
			
		}
	}	



}
