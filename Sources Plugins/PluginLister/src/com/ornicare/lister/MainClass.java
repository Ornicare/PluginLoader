package com.ornicare.lister;

import com.space.plugin.PluginContentProvider;
import com.space.plugin.PluginRunnable;

public class MainClass extends PluginRunnable {
	
	public void run() {
		System.out.println("List of all plugins : ");
		for(String s : getPluginList()) {
			System.out.println("	"+s);
		}
		System.out.println();
		
		System.out.println("List of all runnables plugins : ");
		for(String s : getPluginImplementationsOf(PluginRunnable.class)) {
			System.out.println("	"+s);
		}
		System.out.println();
		
		System.out.println("List of all content providers plugins : ");
		for(String s : getPluginImplementationsOf(PluginContentProvider.class)) {
			System.out.println("	"+s);
		}
		System.out.println();
		
	}
}
