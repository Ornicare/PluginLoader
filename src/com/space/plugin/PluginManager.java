package com.space.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	/**
	 * List of grouped plugins by dependencies.
	 */
	//TODO
	public List<ArrayList<PluginBase>> groupedLoadedPlugins = new ArrayList<ArrayList<PluginBase>>();
	
	/**
	 * List of groups ClassLoader
	 */
	private Map<Integer,URLClassLoader> groupedClassLoader = new HashMap<Integer,URLClassLoader>();
	
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
                		register(new PluginRunnableWrapper(file.getPath(),this, prop));
                	}
                	else {
                		register(new PluginContentProviderWrapper(file.getPath(),this, prop));
                	}
            	}
        	}
        }
        createDependenciesGroups();
        createGroupedClassLoaders(directory.getAbsolutePath());
        
        /*for(Integer i : groupedClassLoader.keySet()) {
        	System.out.println(groupedClassLoader.get(i));
        	URLClassLoader gCL = groupedClassLoader.get(i);
        	for(URL path : gCL.getURLs()) {
        		System.out.println(path.getPath());
        	}
        }*/
        return result;
    }

    
    private void createGroupedClassLoaders(String path) {
    	
    	/*int id = 0;
    	for(ArrayList<PluginBase> pBL : groupedLoadedPlugins) {
    		for(PluginBase pB : pBL) {
        		System.out.println(pB.getName()+" ["+id+"]");
        	}
    		id++;
    	}*/
    	
    	int groupId = 0;
    	for(ArrayList<PluginBase> pBL : groupedLoadedPlugins) {
    		//TODO truc mégalouche
    		//String s ="zz"+pBL.toString();
    		//System.out.println("zz"+pBL);
    		
    		List<URL> plugins = new ArrayList<URL>();
    		//@SuppressWarnings("unchecked")
			//ArrayList<PluginBase> pBL2 = (ArrayList<PluginBase>) pBL.clone();
    		for(PluginBase pB : pBL) {
    			URL fileURL = null;
    			try {
    				fileURL = new URL("file:///"+path+"/"+pB.getJarName()+".jar");
    			} catch (MalformedURLException e) {
    				e.printStackTrace();
    			}
    			
    			plugins.add(fileURL);
    			
    			pB.setGrouId(groupId);
    		}

			URL[] tabURL = new URL[plugins.size()];
			
			for(int i = 0;i<plugins.size();i++) {
				tabURL[i] = plugins.get(i);
			}
    		groupedClassLoader.put(groupId, getNewClassLoader(tabURL));
    		groupId++;
    	}	
	}

	/**
     * Create dependencies group.
     */
    private void createDependenciesGroups() {
    	/**
    	 * Plugins with their dependencies
    	 */
    	Map<PluginBase,String[]> pluginWithDependencies = new HashMap<PluginBase,String[]>();
    	
    	/**
    	 * List of slaves plugins
    	 */
    	Map<PluginBase,ArrayList<String>> pluginSonsDependencies = new HashMap<PluginBase,ArrayList<String>>();
    	
    	/**
    	 * List of plugins groups.
    	 */
    	Map<PluginBase,ArrayList<String>> pluginGroupDependencies = new HashMap<PluginBase,ArrayList<String>>();
    	
    	/**
    	 * Initialize the slve list.
    	 */
    	for(PluginBase pB: loadedPlugins) {
    		pluginSonsDependencies.put(pB, new ArrayList<String>());
    	}
    	
    	/**
    	 * Create slave list.
    	 */
    	for(PluginBase pB: loadedPlugins) {
    		String[] pluginDependencies = pB.getDependancies();
    		pluginWithDependencies.put(pB, pluginDependencies);
    		for(String pBDep : pluginDependencies) {
    			PluginBase tempPB = getPlugin(pBDep);
    			
    			ArrayList<String> tempPBL = pluginSonsDependencies.get(tempPB);
    			tempPBL.add(pB.getName());
    			
    			pluginSonsDependencies.put(tempPB, tempPBL);
    		}
    	}
    	
    	/**
    	 * Create group list.
    	 */
    	for(PluginBase pB: loadedPlugins) {
    		List<String> dependencies = new ArrayList<String>(Arrays.asList(pluginWithDependencies.get(pB)));
    		dependencies.addAll(pluginSonsDependencies.get(pB));
    		
    		pluginGroupDependencies.put(pB, (ArrayList<String>) dependencies);
    	}
    	
    	/**
    	 * Dependencies group given by Id
    	 */
    	Map<String,Integer> pluginGroupId = new HashMap<String,Integer>();
    	
    	for(PluginBase pB : pluginWithDependencies.keySet()) {
    		boolean find = false;
    		for(String dependencie : pluginWithDependencies.get(pB)) {
    			if(pluginGroupId.containsKey(dependencie) && !find) {
    				Integer groupId = pluginGroupId.get(dependencie);
    				ArrayList<PluginBase> tempList = (ArrayList<PluginBase>) groupedLoadedPlugins.get(groupId);
    				tempList.add(pB);
    				
    				groupedLoadedPlugins.set(groupId,tempList);
    				pluginGroupId.put(pB.getName(), new Integer(groupId));
    				find = true;
    			}
    		}
    		if(!find) {
    			groupedLoadedPlugins.add(new ArrayList<PluginBase>(Arrays.asList(pB)));
    			pluginGroupId.put(pB.getName(), new Integer(groupedLoadedPlugins.size()-1));
    		}
    	}
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
			System.out.println("Error while opening "+pluginJarName+". Is it a valid jar file ?");
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
	 * @return
	 */
	public URLClassLoader getNewClassLoader(URL[] tabURL) {
		return new URLClassLoader(tabURL,parent);
	}

	public URLClassLoader getGroupClassLoader(int groupId) {
		return groupedClassLoader.get(new Integer(groupId));
	}

	public ArrayList<String> getPluginList() {
		ArrayList<String> retour = new ArrayList<String>();
		for(PluginBase pB : loadedPlugins) {
			retour.add(pB.getName());
		}
		return retour;
	}

}
