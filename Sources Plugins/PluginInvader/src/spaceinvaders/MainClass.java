package spaceinvaders;

import com.space.plugin.PluginRunnable;

public class MainClass extends PluginRunnable{

	@Override
    public void run() {
        new SpaceInvaders(this);
    }
	
	public Object getPluginFromMain(String name) {
		return getPlugin(name);
	}
}
