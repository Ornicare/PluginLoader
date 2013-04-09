package com.ornicare.tableaucaller;

import com.ornicare.tableau.ITableau;
import com.space.plugin.PluginRunnable;

public class MainClass extends PluginRunnable {
	
	public void run() {
		System.out.println("Avant");
		ITableau tableau = (ITableau)getPluginUsingConstructor("Tableau", new Class<?>[]{int.class,int.class,int.class,int.class}, 1,2,3,4);
		System.out.println("Apres");
		System.out.println(tableau.getSomme());
		System.out.println("Fin");
		
	}
}
