package com.space.plugin;

public interface IPluginBase {

	/**
	 * Do we need to automatically run it ?
	 * @return
	 */
	public abstract boolean isLaunchable();

	/**
	 * Return plugin true name.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Calculate plugin's dependancies.
	 * 
	 * @return
	 */
	public abstract String[] getDependencies();

	/**
	 * Return plugin jar name.
	 * 
	 * @return
	 */
	public abstract Object getJarName();

	public abstract String getPath();

}