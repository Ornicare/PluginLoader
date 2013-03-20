package com.space.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarFile;


/**
 * 
 * Global manager of all plugins : store the plugin's list, load all plugins, manage communication between plugins.
 * 
 * @author Ornicare
 *
 */
public class PluginManager {
	
	/**
	 * The main classLoader : it will know all others pluginClassLoaders
	 */
	private ClassLoader parent;
	
	/**
	 * Global list of plugins.
	 */
	private List<PluginBase> loadedPlugins = new ArrayList<PluginBase>();
	
	public PluginManager(ClassLoader parent) {
		this.parent = parent;
	}
	
	/**
     * Loads the plugins contained within the specified directory
     *
     * @param directory Directory to check for plugins
     * @return A list of all plugins loaded
	 * @throws Exception 
     */
    public List<PluginBase> preLoadPlugins(File directory) throws Exception {
        Validate.notNull(directory, "Directory cannot be null");
        Validate.isTrue(directory.isDirectory(), "Directory must be a directory");

        List<PluginBase> result = new ArrayList<PluginBase>();
        
        // All plugins are in folder /plugins
        for (File file : directory.listFiles()) {
        	if(file.isFile()) {
        		Properties prop = loadConfig(file);
            	if(prop!=null) {
            		if(prop.containsKey("runnable") && prop.getProperty("runnable").equals("true")) {
                		register(new PluginRunnable(file.getPath(),this, prop));
                	}
                	else {
                		register(new PluginContentProvider(file.getPath(),this, prop));
                	}
            	}
        	}
        }

        return result;
    }

    /**
     * Load the plugin's attributs.
     * 
     * @param pluginJarName
     * @return
     */
	@SuppressWarnings("resource")
	public Properties loadConfig(File pluginJarName) {
    	JarFile jarFileContainer = null;
		try {
			jarFileContainer = new JarFile(pluginJarName);
			InputStream defConfigStream = jarFileContainer.getInputStream(jarFileContainer.getEntry("plugin.properties"));
			if (defConfigStream != null) {
	        	Properties config = new Properties();
	        	config.load(defConfigStream);
	        	return config;
			}
		}
		catch (IOException e1) {
			System.out.println("Error while opening "+pluginJarName+". Is it a alid jar file ?");
			e1.printStackTrace();
		}
		return null;       
    }

	/**
	 * Add the plugin to the global list
	 * 
	 * @param plugin
	 */
	public void register(PluginBase plugin) {
		loadedPlugins.add(plugin);
	}

	/**
	 * Search to find class into plugin's dependencies.
	 * 
	 * @param name
	 * @param dependanciesNames
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public Class<?> findClass(String name, String... dependanciesNames) throws ClassNotFoundException, Exception {
		
		List<String> dependanciesNamesList = new ArrayList<String>(Arrays.asList(dependanciesNames));
		for(PluginBase plugin : loadedPlugins) {
			if(dependanciesNamesList.contains(plugin.getJarName())) {
				Class<?> clazz = plugin.getClassLoader().loadClass(name);
				return clazz;
			}
		}
		return null;
	}

	/**
	 * Try to find the plugin identified by <code>name</code>. If it doesn't find it, return null.
	 * 
	 * @param name
	 * @return
	 */
	public PluginBase getPlugin(String name) {
		for(PluginBase plugin : loadedPlugins) {
			if(plugin.getName().equals(name)) {
				return plugin;
			}
		}
		return null;
	}

	/**
	 * Create a new class loader for a plugin.
	 * 
	 * @param tabURL
	 * @param dependancies
	 * @return
	 */
	public PluginClassLoader getNewClassLoader(URL[] tabURL, String[] dependancies) {
		return new PluginClassLoader(tabURL, parent, this, dependancies);
	}

}
