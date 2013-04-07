package com.ornicare.tablehelper;

import com.space.plugin.PluginContentProvider;

public class TableHelper extends PluginContentProvider implements ITableHelper{

	private int[] tab;

	public TableHelper() {
		this.tab = new int[0];
	}
	
	public TableHelper(int[] tab) {
		this.tab = tab;
	}
	
	@Override
	public int sum() {
		int sum = 0;
		for(int i : tab) sum+=i;
		return  sum;
	}
	
	@Override
	public int max() {
		int max = 0;
		if(tab.length>0) {
			max = tab[0];
			for(int i : tab) max=max>i?max:i;
		}
		return  max;
	}
	
	@Override
	public int min() {
		int min = 0;
		if(tab.length>0) {
			min = tab[0];
			for(int i : tab) min=min<i?min:i;
		}
		return  min;
	}
	
	@Override
	public int average() {
		return  tab.length>0?sum()/tab.length:0;
	}
	
	@Override
	public double squareType() {
		if(tab.length<0) return 0;
		int sum = 0;
		int ave = average();
		for(int i : tab) sum+=(i-ave)*(i-ave);
		return Math.sqrt(sum/tab.length);
	}

}
