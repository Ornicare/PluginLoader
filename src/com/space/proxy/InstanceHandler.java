package com.space.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.space.plugin.PluginBase;

public class InstanceHandler implements InvocationHandler{

	private Object realObject = null;
	private PluginBase pluginBase;

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
	
	private void createNewInstance() {
		//System.out.println("Instanciation !");
		try {
			this.realObject = pluginBase.getInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}