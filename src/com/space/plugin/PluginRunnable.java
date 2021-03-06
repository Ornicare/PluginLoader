package com.space.plugin;

public abstract class PluginRunnable extends PluginCommonMethods implements IPluginRunnable {

	/**
	 * Do nothing
	 */
	public abstract void run() throws Exception;

	public void run(Object... args) throws Exception {
		run();
	}
}
