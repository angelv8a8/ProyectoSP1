package com.sp1.tracker.businessObjects;

import com.sp1.tracker.R;

public class Indice {

	public String nombre;
	public int ID;
	
	public int icon = R.drawable.ic_action_share;
	public int addIcon = R.drawable.ic_action_new;
	public int iconGrafica = R.drawable.ic_graph;
	
	public Indice(String _nombre)
	{
		nombre = _nombre;
	}

	public Indice()
	{
		
	}
	
	
}
