package com.space.plugin;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Wrapper to a runnable plugin.
 * 
 * @author Ornicare
 *
 */
public class PluginRunnable extends PluginBase implements IPluginRunnable {
	private Method method;
	private Object instance;
	
	public PluginRunnable(String path, PluginManager pluginManager, Properties config) {
		super(path, pluginManager, config);
	}

	/**
	 * Try to run the main class of the plugin (gives in the plugin's configuration.
	 */
	@Override
	public void run() throws Exception   {
		if(instance == null || !singleton) createRunMethod();
		method.invoke(instance);
	}
	
	/**
	 * Create objects to run the plugin.
	 * 
	 * @throws Exception
	 */
	//TODO in case of STATIC class ? Vérifier Lazy.
	protected void createRunMethod() throws Exception {
		if(!initialized) initialize();
		
		if(lazy) this.initialized = true;
		
		Class<?> classToLoad = Class.forName(mainClass, true, classLoader);
		Validate.notNull(classToLoad, "Invalid main class.");
		
		//TODO vérifier condition
		Validate.isTrue(IPluginRunnable.class.isAssignableFrom(classToLoad),"Main class doesn't implement IPluginRunnable");
		
		method = classToLoad.getDeclaredMethod("run");
		instance = classToLoad.newInstance();
	}

}
