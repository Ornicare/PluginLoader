package com.space.plugin;

import java.util.ArrayList;

public abstract class PluginCommonMethods {
	
	private static boolean stopRegistration = false;
	private static PluginManager pluginManager;
	
	public static final void registerPluginManager(PluginManager pluginManager) {
		if(!stopRegistration) {
			PluginCommonMethods.pluginManager = pluginManager;
			stopRegistration = true;
		}
	}
	
	protected Object getPlugin(String name) {
		return pluginManager.getPlugin(name).getInstance();
	}
	
	protected ArrayList<String> getPluginList() {
		return pluginManager.getPluginList();
	}
}
