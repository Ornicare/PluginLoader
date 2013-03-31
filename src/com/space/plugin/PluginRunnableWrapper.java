package com.space.plugin;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Wrapper to a runnable plugin.
 * 
 * @author Ornicare
 *
 */
public class PluginRunnableWrapper extends PluginBase implements IPluginRunnable {
	private Method method;
	
	public PluginRunnableWrapper(String path, PluginManager pluginManager, Properties config) {
		super(path, pluginManager, config);
	}

	/**
	 * Try to run the main class of the plugin (gives in the plugin's configuration.
	 */
	@Override
	public void run() throws Exception   {
		if(instance == null || classToLoad == null || method == null || !singleton) createRunMethod();

    	/*for(URL path : classLoader.getURLs()) {
    		System.out.println(path.getPath());
    	}*/
		
		//System.out.println(instance);
		
		method.invoke(instance);
	}
	
	/**
	 * Create objects to run the plugin.
	 * 
	 * @throws Exception
	 */
	//TODO in case of STATIC class ? V�rifier Lazy.
	protected void createRunMethod() throws Exception {
		createInstance();
		method = classToLoad.getDeclaredMethod("run");
		//System.out.println(method.getReturnType().getName());
	}

}
