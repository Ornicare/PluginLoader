package com.space.plugin;

import java.util.Properties;

/**
 * Just a non-runnable plugin.
 * 
 * @author Ornicare
 *
 */
//TODO add some methods ? (getObject, ...)
public class PluginContentProvider extends PluginBase implements IPluginContentProvider {

	public PluginContentProvider(String pluginJarName, PluginManager pluginManager, Properties config) {
		super(pluginJarName, pluginManager, config);
	}

}
