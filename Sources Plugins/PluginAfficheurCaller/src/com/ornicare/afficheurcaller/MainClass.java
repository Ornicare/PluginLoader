package com.ornicare.afficheurcaller;

import com.ornicare.afficheur.IMainClass;
import com.space.plugin.PluginRunnable;

public class MainClass extends PluginRunnable {
	
	public void run() {
		System.out.println("Avant");
		IMainClass afficheur = (IMainClass)getPlugin("Afficheur");
		System.out.println("Apres");
		afficheur.affiche("Hello world !");
		System.out.println("Fin");
		
	}
}
