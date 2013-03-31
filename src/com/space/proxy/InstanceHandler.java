package com.space.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InstanceHandler implements InvocationHandler{

	private Object realObject = null;
	private Class<?> clazz;

	public InstanceHandler(Class<?> clazz, boolean lazy) {
		this.clazz = clazz;
		if(!lazy) createNewInstance();
	}
	
	@Override
	public Object invoke(Object pseudoObject, Method m, Object[] args)
			throws Throwable {
		if(realObject == null) createNewInstance();
		return m.invoke(realObject, args);
	}
	
	private void createNewInstance() {
		System.out.println("Instanciation !");
		try {
			this.realObject = this.clazz.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}