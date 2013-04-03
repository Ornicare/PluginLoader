package com.space.main;

import java.io.File;
import java.util.ArrayList;

import com.space.plugin.PluginBase;
import com.space.plugin.PluginCommonMethods;
import com.space.plugin.PluginManager;
import com.space.plugin.PluginRunnableWrapper;


public class Principale {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		String directory = "plugins";
		if(args.length>0) {
			directory = args[0];
		}
		/*
		 * Create the pluginManager
		 */
		PluginManager pluginManager = new PluginManager(Thread.currentThread().getContextClassLoader());
		
		/*
		 * Register it.
		 */
		PluginCommonMethods.registerPluginManager(pluginManager);
		
		/*
		 * Load the "plugins" folder.
		 */
		File pluginFolder = new File(directory);
		System.out.println("Plugins folder in use : "+pluginFolder.getAbsolutePath());
		
		pluginManager.preLoadPlugins(pluginFolder);
		
		/*
		 * See groups of plugins dependencies.
		 */
		int id = 0;
		System.out.println("\nDependencies groups (linked plugins) : ");
    	for(ArrayList<PluginBase> pBL : pluginManager.groupedLoadedPlugins) {
    		System.out.println("Group "+id+" : ");
    		for(PluginBase pB : pBL) {
        		System.out.println("    "+pB.getName()+" [jar_name = "+pB.getJarName()+"]");
        	}
    		System.out.println();
    		id++;
    	}
		
    	/*
    	 * Run launchables plugins 
    	 */
    	for(String pluginName : pluginManager.getPluginList()) {
    		PluginBase pB = pluginManager.getPlugin(pluginName);
    		if(pB.isLaunchable() && pB.getClass().isAssignableFrom(PluginRunnableWrapper.class)) {
    			PluginRunnableWrapper runnablePB = (PluginRunnableWrapper) pB;
    			System.out.println("Running : "+pB.getName());
    			runnablePB.run();
    		}
    	}
	}

}
