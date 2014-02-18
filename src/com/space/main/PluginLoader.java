package com.space.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.space.plugin.PluginBase;
import com.space.plugin.PluginExternalizer;
import com.space.plugin.PluginManager;

public class PluginLoader {
	
	public PluginExternalizer createPluginManager(String directory) throws Exception {
		return createPluginManager(directory,false);	
	}
	
	public PluginExternalizer createPluginManager(String directory, boolean ignoreDirectory) throws Exception {
		
		/*
		 * Create the pluginManager
		 */
		PluginManager pluginManager = new PluginManager(this.getClass().getClassLoader());
	
		return commonTreatment(pluginManager, directory, ignoreDirectory);	
	}
	
	public PluginExternalizer createPluginManager(String directory, boolean ignoreDirectory, ClassLoader masterClassLoader) throws Exception {
		
		/*
		 * Create the pluginManager
		 */
		PluginManager pluginManager = new PluginManager(masterClassLoader);
	
		return commonTreatment(pluginManager, directory, ignoreDirectory);	
	}

	private PluginExternalizer commonTreatment(PluginManager pluginManager, String directory, boolean ignoreDirectory) throws FileNotFoundException, IOException {
		/*
		 * Register it.
		 */
//		PluginCommonMethods pCM = new PluginCommonMethods();
//		pCM.registerPluginManager(pluginManager);
		
		/*
		 * Load the "plugins" folder.
		 */
		File pluginFolder = new File(directory);
//		System.out.println("Plugins folder in use : "+pluginFolder.getAbsolutePath());
		
		pluginManager.preLoadPlugins(pluginFolder,ignoreDirectory);
		
		
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
		 * Create a hook plugin for the main core
		 */
    	PluginExternalizer externalizer = new PluginExternalizer(pluginManager);
		return externalizer;
	}

}
