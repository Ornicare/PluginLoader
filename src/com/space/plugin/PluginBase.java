package com.space.plugin;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


/**
 * 
 * Contain all attributs and methods for the plugin's wrapper.
 * 
 * @author Ornicare
 *
 */
public abstract class PluginBase {

	
	private Properties config;
	protected PluginClassLoader classLoader;
	private String pluginJarName;
	private String name;
	protected boolean initialized = false;
	private PluginManager pluginManager;
	
	protected String mainClass;
	protected boolean lazy;
	protected boolean singleton;
	protected boolean isRunnable;
	
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
		this.pluginManager.register(this);
		this.config = config;
		loadConfig(config);
	}

	/**
	 *  Get a new classLoader for this plugin
	 */
	public void initialize() {
		URL fileURL = null;
		try {
			fileURL = new URL("file:///"+System.getProperty("user.dir")+"/"+pluginJarName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		URL[] tabURL = new URL[]{fileURL};
		classLoader = pluginManager.getNewClassLoader(tabURL, getDependancies());
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
    	
    	lazy = getPropertyBool("lazy");
    	singleton = getPropertyBool("singleton");
    	isRunnable = getPropertyBool("runnable");
		
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
    public final PluginClassLoader getClassLoader() throws Exception {
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
    	if(name==null) this.name = pluginJarName;
    	return name;
    }

    /**
     * Calculate plugin's dependancies.
     * 
     * @return
     */
	public String[] getDependancies() {
		String rawDependancies = config.getProperty("depend");
		String[] retour = null;
		if(rawDependancies!=null) retour = config.getProperty("depend").split(",");
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
	
	

}
