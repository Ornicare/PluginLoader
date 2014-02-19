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
	private Object[] args;
	private Class<?>[] argsType;
	
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
	
	public InstanceHandler(PluginBase pluginBase, boolean lazy,
			Object[] args, Class<?>[] argsType) {
		super();
		this.pluginBase = pluginBase;
		this.args = args;
		this.argsType = argsType;
		if(!lazy) createNewInstance();
	}

	public Object invoke(Object pseudoObject, Method m, Object[] args)
			throws Throwable {
		if(realObject == null) createNewInstance();
		if(args==null) {
			return m.invoke(realObject);
		}
		else {
//			System.out.println(args.length+ " "+args.getClass().getName()+" "+args[0].getClass().getName());
//			System.out.println(m.getParameterTypes()[0].getName());
//			if(args.length==1) {
//				
//				if(args.)
//			}
//			else {
//			System.out.println(m.getName());
			
			
			
			
			
			
			
			
			
			
			
//			if(m.getParameterTypes()[0].isArray() && !args[0].getClass().isArray() && args.length<2) {
//				return m.invoke(realObject, new Object[]{args});
//			}
//			else {
				return m.invoke(realObject, args);
//			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
//			if(m.getParameterTypes()[0].isArray() && !(args[0].getClass().isArray()) && !(((Object[])args[0]).length==1)) {
//				return m.invoke(realObject, new Object[]{args});
//			}
//			else {
//				return m.invoke(realObject, args);
//			}
//			}
		}
		
	}

	/**
	 * Give an instance of the plugin.
	 */
	private void createNewInstance() {
		//System.out.println("Instanciation !");
		try {
			if(args==null) {
				this.realObject = pluginBase.getInstance();
			}
			else {
				this.realObject = pluginBase.getInstance(args, argsType);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}