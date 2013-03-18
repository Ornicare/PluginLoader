package com.space.main;

import java.io.File;

import com.space.plugin.PluginManager;
import com.space.plugin.PluginRunnable;


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
		PluginManager pluginManager = new PluginManager(Thread.currentThread().getContextClassLoader());
		
		//Plugin test = new Plugin("plugins\\HelloWorld.jar",Thread.currentThread().getContextClassLoader(),pluginManager);

		//System.out.println(test.getConfig().getProperty("main"));
		//Plugin test2 = new Plugin("plugins\\test.jar",Thread.currentThread().getContextClassLoader(), pluginManager);
		
		//System.out.println("AA  T"+test.getJarName()+"T");
		//System.out.println(test.getDependancies()[0]);
		
		//for(int i =0;i<10;i++)
			//test.run();
		//test2.run();

		
		File pluginFolder = new File("plugins");
		pluginManager.preLoadPlugins(pluginFolder);
		
		PluginRunnable helloWorld = (PluginRunnable) pluginManager.getPlugin("Executor");

		
		helloWorld.run();
	}

}
