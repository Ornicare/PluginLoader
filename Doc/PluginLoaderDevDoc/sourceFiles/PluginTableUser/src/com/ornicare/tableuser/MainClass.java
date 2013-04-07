package com.ornicare.tableuser;

import com.ornicare.tablehelper.ITableHelper;
import com.space.plugin.PluginRunnable;

public class MainClass extends PluginRunnable implements IMainClass {
	
	@Override
	public void run() {
		//ITableHelper tableau = (ITableHelper)getPluginUsingConstructor("Tableau", new Class<?>[]{int.class,int.class,int.class,int.class}, 1,2,3,4);
		ITableHelper tableau = (ITableHelper)getPlugin("TableHelper", new int[]{1,2,3,4});
		
		System.out.println("Sum : "+tableau.sum());
		System.out.println("Min : "+tableau.min());
		System.out.println("Max : "+tableau.max());
		System.out.println("Average : "+tableau.average());
		System.out.println("SquareType : "+tableau.squareType());
	}
}
