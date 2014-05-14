package com.sp1.tracker.businessObjects;

import java.util.Date;

public class CategoriaIndiceRegistro {
	
	public int categoriaIndiceId;
	public float valor;
	public Date fecha;
	
	public CategoriaIndiceRegistro(int _categoriaIndiceId, float _valor)
	{
		categoriaIndiceId = _categoriaIndiceId;
		valor = _valor;
	}
	
	public CategoriaIndiceRegistro()
	{
	}


}
