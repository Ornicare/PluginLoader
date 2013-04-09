package com.ornicare.afficheur;

import com.space.plugin.PluginContentProvider;

public class MainClass extends PluginContentProvider implements IMainClass {
	
	@Override
	public void affiche(String s) {
		System.out.println(s);
	}
}
