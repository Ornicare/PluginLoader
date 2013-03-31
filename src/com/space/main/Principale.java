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
		/*URLClassLoader child = new URLClassLoader(new URL[]{new URL("file:///"+System.getProperty("user.dir")+"/plugins/Test.jar")}, System.class.getClassLoader());
		Class<?> classToLoad = Class.forName("snippet.Test", true, child);
		Method method = classToLoad.getDeclaredMethod("run");
		Object instance = classToLoad.newInstance ();
		Object result = method.invoke (instance);*/
		
		
		
		//PluginManager pluginManager = new PluginManager(Thread.currentThread().getContextClassLoader());
		
		//Plugin test = new Plugin("plugins\\HelloWorld.jar",Thread.currentThread().getContextClassLoader(),pluginManager);

		//System.out.println(test.getConfig().getProperty("main"));
		//Plugin test2 = new Plugin("plugins\\test.jar",Thread.currentThread().getContextClassLoader(), pluginManager);
		
		//System.out.println("AA  T"+test.getJarName()+"T");
		//System.out.println(test.getDependancies()[0]);
		
		//for(int i =0;i<10;i++)
			//test.run();
		//test2.run();

		/*
		 * Create the pluginManager
		 */
		PluginManager pluginManager = new PluginManager(ClassLoader.getSystemClassLoader());
		
		/*
		 * Register it.
		 */
		PluginCommonMethods.registerPluginManager(pluginManager);
		
		/*
		 * Load the "plugins" folder.
		 */
		File pluginFolder = new File("plugins");
		System.out.println(pluginFolder.getAbsolutePath());
		
		pluginManager.preLoadPlugins(pluginFolder);
		
		/*
		 * See groups of plugins dependencies.
		 */
		int id = 0;
    	for(ArrayList<PluginBase> pBL : pluginManager.groupedLoadedPlugins) {
    		for(PluginBase pB : pBL) {
        		System.out.println(pB.getName()+" ["+id+"]");
        	}
    		id++;
    	}
		
    	/*
    	 * Run plugins 
    	 */

		//Grouped example
		PluginRunnableWrapper helloWorld2 = (PluginRunnableWrapper) pluginManager.getPlugin("Executor");
    	
    	//Function example
    	PluginRunnableWrapper helloWorld = (PluginRunnableWrapper) pluginManager.getPlugin("PluginLister");

		
		helloWorld.run();
		helloWorld2.run();
	}

}
