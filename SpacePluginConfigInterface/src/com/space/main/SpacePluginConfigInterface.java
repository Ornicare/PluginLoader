/**
 * 
 */
package com.space.main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import com.space.annotations.LaunchInfo;
import com.space.enums.LaunchPriority;
import com.space.gui.window.MainWindow;
import com.space.plugin.IPluginRunnable;
import com.space.plugin.PluginRunnable;


/**
 * @author CLEm
 *
 */
public class SpacePluginConfigInterface extends PluginRunnable {
	
	/*public static String[] getAvailablePlugins() {
		
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
	}*/
	
	@Override
	@LaunchInfo(priority = LaunchPriority.HIGHEST)
	public void run() {
		String[] availablePlugins = getPluginImplementationsOf(IPluginRunnable.class).toArray(new String[0]);
		@SuppressWarnings("unused")
		MainWindow mainWindow = new MainWindow( availablePlugins, this ); 
	}

	/**
	 * Launch the selected list of plugins.
	 * @param selectedList01
	 */
	public void launch(List<String> selectedList01) {
		for(String s : selectedList01) {
			try {
				Class<?> clazz = PluginRunnable.class;
				Method m = clazz.getMethod("run");
				
				Proxy proxy = (Proxy) getPlugin(s);
				InvocationHandler ih = Proxy.getInvocationHandler(proxy);
				ih.invoke(proxy, m, null);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
