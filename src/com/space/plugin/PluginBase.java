package com.space.plugin;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;


/**
 * 
 * Contain all attributs and methods for the plugin's wrapper.
 * 
 * @author Ornicare
 *
 */
public abstract class PluginBase{

	
	private Properties config;
	protected URLClassLoader classLoader;
	private String pluginJarName;
	private String name;
	protected boolean initialized = false;
	private PluginManager pluginManager;
	private boolean runInFirst = false;
	
	protected String mainClass;
	protected boolean internalLazy;
	protected boolean singleton;
	protected boolean isRunnable;
	private int groupId;
	
	/**
	 * The plugin instance
	 */
	protected Object instance;
	
	protected Class<?> classToLoad;
	
	/**
	 * 
	 * @param pluginJarName Physical jar name
	 * @param pluginManager Global common manager
	 * @param config The properties version of config.properties
	 */
	public PluginBase(String pluginJarName, PluginManager pluginManager, Properties config) {
		Validate.notNull(pluginJarName, "Jar name cannot be null !");
		this.pluginJarName = pluginJarName;
		this.pluginManager=pluginManager;
		this.config = config;
		loadConfig(config);
	}
	
	/**
	 * Do we need to automatically run it ?
	 * @return
	 */
	public boolean isLaunchable() {
		return runInFirst;
	}

	/**
	 *  Get a new classLoader for this plugin
	 */
	public void initialize() {
		classLoader = pluginManager.getGroupClassLoader(groupId);
		initialized = true;
	}

	
	public Properties getConfig() {
		return config;
	}
	
	/**
	 * Parse and load the plugin configuration.
	 * 
	 * @param config
	 */
	private void loadConfig(Properties config) {
    	mainClass = config.getProperty("main");
    	if(config.getProperty("runnable").equals("true")) Validate.notNull(mainClass, getName()+" : Invalid main class in the properties file");
    	
    	internalLazy = getPropertyBool("lazy");
    	singleton = getPropertyBool("singleton");
    	isRunnable = getPropertyBool("runnable");
    	runInFirst = getPropertyBool("launch");
		
	}
	
	/**
	 * Submethod to transform a propertie into boolean.
	 * If it doesn't recognize any boolean, return false;
	 * 
	 * @param prop
	 * @return
	 */
	private boolean getPropertyBool(String prop) {
		if(!config.containsKey(prop)) return false;
		Boolean value = Boolean.valueOf(config.getProperty(prop));
		return value;
	}
    
    
	/**
	 * Return a file contained into the jarfile, in it InputStream state.
	 * 
	 * @param filename
	 * @return
	 */
    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        return getClass().getResourceAsStream(filename);
    }
    
    /**
     * Return the classLoader for this plugin. Create it if it doesn't exists.
     * 
     * @return
     * @throws Exception
     */
    public final URLClassLoader getClassLoader() throws Exception {
    	if(!initialized) initialize();
        return classLoader;
    }
    
    /**
     * Return plugin true name.
     * 
     * @return
     */
    public String getName() {
    	if(name==null) this.name = config.getProperty("name");
    	Validate.notNull(name, pluginJarName+" : invalide properties file : name not found.");
    	return name;
    }

    /**
     * Calculate plugin's dependancies.
     * 
     * @return
     */
	public String[] getDependencies() {
		String rawDependancies = config.getProperty("depend");
		String[] retour = null;
		if(rawDependancies!=null) retour = config.getProperty("depend").split(",");
		if(retour!=null) {
			for(int i = 0 ; i < retour.length ; i++) {
				retour[i] = retour[i].trim();
			}
		}
		return retour==null?new String[0]:retour;
	}

	/**
	 * Return plugin jar name.
	 * 
	 * @return
	 */
	public Object getJarName() {
		return pluginJarName.substring(8).substring(0, pluginJarName.length()-12);
	}

	public void setGrouId(int groupId) {
		this.groupId = groupId;
	}

	public Object getInstance() {
		return instance==null || !singleton ?createInstance():instance;
	}

	protected Object createInstance() {
		
		if(!initialized) initialize();
		
		if(internalLazy) this.initialized = true;
		
    	/*for(URL path : classLoader.getURLs()) {
    		System.out.println(getName()+" "+path.getPath());
    	}*/
    	
		

		try {
			classToLoad = classLoader.loadClass(mainClass);
			Validate.notNull(classToLoad, "Invalid main class.");
			
			//TODO v�rifier condition
			Validate.isTrue(IPluginRunnable.class.isAssignableFrom(classToLoad),"Main class doesn't implement IPluginRunnable");
			
			instance = classToLoad.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		

		return instance;
	}

}
