/**
 * 
 */
package com.space.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.space.gui.window.MainWindow;
import com.space.plugin.PluginRunnable;


/**
 * @author CLEm
 *
 */
public class SpacePluginConfigInterface extends PluginRunnable {
	
	public static String[] getAvailablePlugins() {
		
		// TODO demander au manager
		
//		String path= "";
//		String filtre = ".jar$"; 
//		
//		Pattern p = Pattern.compile(filtre);
//		String[] s = new File(path).list();
//		List<String> listeFichiers = new ArrayList<String>();
//		for ( int i = 0 ; i < s.length ; i++ ) {
//			Matcher m = p.matcher(s[i]);
//			if (m.matches()) {
//				listeFichiers.add(s[i]);
//			}
//		}
//		
//		return listeFichiers.toArray(new String[listeFichiers.size()]);
		
		List<String> listeFichiers = new ArrayList<String>();
		
		String path = "";
		String filtre = ".*jar$"; 
		
		Pattern p = Pattern.compile(filtre);
		
		path = new File("").getAbsolutePath();
		System.out.println(path);
		String[] s = new File(path + "/plugins").list();
		System.out.println(s.length);
		
		for ( int i = 0 ; i < s.length ; i++ ) {
			Matcher m = p.matcher(s[i]);
			if (m.matches()) {
				listeFichiers.add(s[i]);
			}
		}
		
		return listeFichiers.toArray(new String[listeFichiers.size()]);
	}
	
	@Override
	public void run() {
		String[] availablePlugins = getAvailablePlugins();
		@SuppressWarnings("unused")
		MainWindow mainWindow = new MainWindow( availablePlugins ); 
	}
	
}
