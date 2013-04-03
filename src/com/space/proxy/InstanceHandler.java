package com.space.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.space.plugin.PluginBase;

/**
 * 
 * Handler for the lazy proxy.
 * @author Ornicare
 *
 */
public class InstanceHandler implements InvocationHandler{
	
	private Object realObject = null;
	private PluginBase pluginBase;
	
	/**
	 * Create a new handler.
	 * 
	 * @param pluginBase plugin to proxyfie
	 * @param lazy is it lazy ?
	 */
	public InstanceHandler(PluginBase pluginBase, boolean lazy) {
		super();
		this.pluginBase = pluginBase;
		if(!lazy) createNewInstance();
	}
	
	@Override
	public Object invoke(Object pseudoObject, Method m, Object[] args)
			throws Throwable {
		if(realObject == null) createNewInstance();
		return m.invoke(realObject, args);
	}
	
	/**
	 * Give an instance of the plugin.
	 */
	private void createNewInstance() {
		//System.out.println("Instanciation !");
		try {
			this.realObject = pluginBase.getInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}