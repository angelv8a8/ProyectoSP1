package com.sp1.tracker.businessObjects;

import com.sp1.tracker.R;

public class CategoriaIndice {
	
	public int categoriaId;
	public int indiceId;
	public int ID;
	
	public String nombre = "";
	
	public int icon = R.drawable.ic_action_share;
	public int addIcon = R.drawable.ic_action_new;
	public int iconGrafica = R.drawable.ic_graph;
	
	
	public CategoriaIndice(int _categoriaId, int _indiceId)
	{
		categoriaId = _categoriaId;
		indiceId = _indiceId;
	}
	
	public CategoriaIndice()
	{
	}


}
