package com.space.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Properties;

import com.space.proxy.InstanceHandler;

/**
 * Just a non-runnable plugin.
 * 
 * @author Ornicare
 *
 */
//TODO add some methods ? (getObject, ...)
public class PluginContentProviderWrapper extends PluginBase implements IPluginContentProvider {

	public PluginContentProviderWrapper(String pluginJarName, PluginManager pluginManager, Properties config) {
		super(pluginJarName, pluginManager, config);
	}

	/**
	 * Stub
	 */
	@Override
	public Object getObject(Object... args) {
		return null;
	}
	
	private Object getProxy(Class<?> clazz, boolean lazy) {
		InvocationHandler h = new InstanceHandler(clazz, lazy);
		return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), clazz.getInterfaces(), h);
	}

}
