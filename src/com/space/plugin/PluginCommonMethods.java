package com.space.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Commons methods for all plugins
 * @author Ornicare
 *
 */
public class PluginCommonMethods {
	
	private boolean stopRegistration = false;
	private PluginManager pluginManager;
	
	/**
	 * Used to register the plugin manager. Dont' touch it !
	 * 
	 * @param pluginManager
	 */
	public final void registerPluginManager(PluginManager pluginManager) {
		if(!stopRegistration) {
			this.pluginManager = pluginManager;
			stopRegistration = true;
		}
	}
	
	/**
	 * Dangerous !
	 * @return
	 */
	protected PluginManager getPluginManager() {
		return pluginManager;
	}
	
	/**
	 * Return a proxy of an instance of the main class of the plugin <code>name</code> if found, null otherwise.
	 * 
	 * @param name plugin's name.
	 * @return
	 */
	protected Object getPlugin(String name) {
		PluginBase plugin = pluginManager.getPlugin(name);
		return plugin==null?null:plugin.getProxy();
	}
	
	public String getPluginPath(String name) {
		PluginBase plugin = pluginManager.getPlugin(name);
		return plugin==null?null:plugin.getPath();
	}
	
	/**
	 * Run the plugin <code>name</code> if found and if implements <code>IPluginRunnable</code>, and return it. Return null if it get an error.
	 * 
	 * @param name plugin's name.
	 * @return
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public Object runPlugin(String name) throws Exception {
		Class<PluginRunnable> l_oClazz = PluginRunnable.class;
        Method m = null;
		try {
			m = l_oClazz.getMethod("run");
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			return null;
		}
        
        PluginBase plugin = pluginManager.getPlugin(name);
        Proxy l_oProxy = (Proxy) (plugin==null?null:plugin.getProxy());
		if(l_oProxy==null) return null;
	
        InvocationHandler l_oIh = Proxy.getInvocationHandler(l_oProxy);
        try {
//        	System.out.println(m.getParameterTypes().length);
			l_oIh.invoke(l_oProxy, m, null);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return l_oProxy;
//		System.out.println("Pas lààààà !!!!");
//		return runPlugin(name,new Object[0]);
	}
	
	/**
	 * Run the plugin <code>name</code> if found and if implements <code>IPluginRunnable</code>, and return it. Return null if it get an error.
	 * 
	 * @param name plugin's name.
	 * @return
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public Object runPlugin(String name, Object... args) throws Exception {
//		IPluginRunnable plugin = (IPluginRunnable) getPlugin(name);
//		System.out.println("@@@@@"+args.length);
//		System.out.println(((File)args[0]).getAbsolutePath());
//		plugin.run("tessstt");
//		return plugin;
		Class<PluginRunnable> l_oClazz = PluginRunnable.class;
        Method m = null;
        Class<?>[] argsType = {new Object[0].getClass()};
//        System.out.println(args.length);
        if(args.length==0) {
        	argsType = null;
        	args = null;
        }

		try {
			m = l_oClazz.getMethod("run", argsType);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			return null;
		}
        PluginBase plugin = pluginManager.getPlugin(name);
        Proxy l_oProxy = (Proxy) (plugin==null?null:plugin.getProxy());
		if(l_oProxy==null) return null;
        InvocationHandler l_oIh = Proxy.getInvocationHandler(l_oProxy);
        try {
			l_oIh.invoke(l_oProxy, m, new Object[]{args});
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return l_oProxy;
	}
	
	/**
	 * Return plugins that are assignables from <code>clazz</code>.
	 * 
	 * @param clazz
	 * @return
	 */
	protected List<String> getPluginImplementationsOf(Class<?> clazz) {
		List<String> retour = new ArrayList<String>();
		for(String plugin : pluginManager.getPluginList()) {
			PluginBase pB = pluginManager.getPlugin(plugin);
			if(pB!=null && clazz.isAssignableFrom(pB.getMainClass())) retour.add(plugin);
		}
		return retour;
	}
	
	/**
	 * Return plugin's names list.
	 * 
	 * @return
	 */
	protected ArrayList<String> getPluginList() {
		return pluginManager.getPluginList();
	}
	
	/**
	 * Return a proxy of an instance of the main class using constructor based on args model of the plugin <code>name</code> if found, null otherwise.
	 * Attention : incompatible with singleton attribut ! (always create a new object)
	 * 
	 * @param name plugin's name.
	 * @return
	 */
	protected Object getPlugin(String name, Object... args) {
		PluginBase plugin = pluginManager.getPlugin(name);
		return plugin==null?null:plugin.getProxy(args, null);
	}
	
	/**
	 * Return a proxy of an instance of the main class using constructor based on argType model of the plugin <code>name</code> if found, null otherwise.
	 * Attention : incompatible with singleton attribut ! (always create a new object)
	 * 
	 * @param name plugin's name.
	 * @return
	 */
	protected Object getPluginUsingConstructor(String name, Class<?>[] argsType, Object... args) {
		PluginBase plugin = pluginManager.getPlugin(name);
		return plugin==null?null:plugin.getProxy(args, argsType);
	}
}
