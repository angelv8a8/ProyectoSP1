package com.sp1.tracker.businessObjects;

import java.sql.Date;
import java.util.List;

import android.database.Cursor;

public class Categoria {
	
	public String nombre;
	public int ID;
	
	public Categoria()
	{
	}
	
	public Categoria(String _nombre)
	{
		nombre = _nombre;
	}
	
}
