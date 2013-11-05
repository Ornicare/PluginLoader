package com.space.plugin;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Reimplementation of URLClassLoader to permit communication between plugins.
 * 
 * @author Ornicare
 *
 */
public class PluginClassLoader extends URLClassLoader{
	/**
	 * Parent ClassLoader.
	 */
	private ClassLoader parent;
	
	/**
	 * Global plugin manager.
	 */
	private PluginManager pluginManager;
	
	/**
	 * All plugin's dependancies.
	 */
	//TODO On les laisse ici ?
	private String[] dependancies;
	
	/**
	 * Create a new PluginClassLoader.
	 * 
	 * @param urls Urls of class to load.
	 * @param parent Parent ClassLoader
	 * @param pluginManager Global PluginManager.
	 * @param dependancies Plugin's dependencies.
	 */
	//TODO Global pluginmanager : demander au plugin ?
    public PluginClassLoader( URL[] urls, ClassLoader parent, PluginManager pluginManager, String[] dependancies )
    {
        super(urls, null);
        System.out.println("zcdqds");
        for(URL u : urls) System.out.println(u.toString());
        
        
        this.parent = parent;
        this.pluginManager=pluginManager;
        this.dependancies = dependancies;
        
    }

    /**
     * Try to find <code>name</code>'s class.
     * 
     * @param name Class name
     * @return the answered class if find, null else.
     */
    //TODO english ?
    @Override
    public Class<?> findClass(String name)
    {
    	System.out.println("Kaboom");
        try
        {
            // first try to use the URLClassLoader findClass
            return super.findClass(name);
        }
        catch( ClassNotFoundException e )
        {
            try
            {
            	// if that fails, we ask our real parent classloader to load the class (we give up)
            	return parent.loadClass(name);
            }
            catch( ClassNotFoundException e2 )
            {
                try
                {

                	// And then check dependancies
                    return pluginManager.findClass(name, dependancies);
                }
                catch( Exception e3 )
                {
                    // Finally.... kill the chicken.
                	try {
						throw new Throwable("Error in dependancies !");
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
            
        }
		return null;
        
    }
}
