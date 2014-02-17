package com.space.plugin;

import java.lang.reflect.Method;
import java.util.Properties;

import com.space.annotations.LaunchInfo;
import com.space.enums.LaunchPriority;

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
	public void run() throws Exception   {
		if(instance == null || classToLoad == null || method == null || !singleton) createRunMethod();

    	/*for(URL path : classLoader.getURLs()) {
    		System.out.println(path.getPath());
    	}*/
		
		//System.out.println(instance);
		System.out.println(method.getParameterTypes().length);
		method.invoke(instance);
	}
	
	@Override
	public void run(Object... args) throws Exception {
		System.out.println("ffdsfdsfdsfds@@@@@@@@@@@@@@");
		if(instance == null || classToLoad == null || method == null || !singleton) createRunMethodWithArgs();

		method.invoke(instance);
	}
	
	private void createRunMethodWithArgs() throws NoSuchMethodException, SecurityException {
		createInstance();
		Class<?>[] argsType = {(new Object[0]).getClass()};
		if(method==null) method = classToLoad.getDeclaredMethod("run", argsType);
		
		Validate.isTrue(IPluginRunnable.class.isAssignableFrom(classToLoad),"Main class doesn't implement IPluginRunnable");
	}

	/**
	 * Create objects to run the plugin.
	 * 
	 * @throws Exception
	 */
	//TODO in case of STATIC class ? V�rifier Lazy.
	protected void createRunMethod() throws Exception {
		createInstance();
		if(method==null) method = classToLoad.getDeclaredMethod("run");
		
		Validate.isTrue(IPluginRunnable.class.isAssignableFrom(classToLoad),"Main class doesn't implement IPluginRunnable");
		
		//System.out.println(method.getReturnType().getName());
	}
	
	public LaunchPriority getPriority() throws Exception {
		LaunchPriority retour = null;
		if(method==null) createRunMethod();
		if(method.isAnnotationPresent(LaunchInfo.class)) {
			LaunchInfo li = method.getAnnotation(LaunchInfo.class);
			return li.priority();
		}
		return retour==null?LaunchPriority.NORMAL:retour;
	}

}
