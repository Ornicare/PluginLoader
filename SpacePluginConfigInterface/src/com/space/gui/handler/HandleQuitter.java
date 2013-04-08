/**
 * 
 */
package com.space.gui.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author CLEm
 *
 */
public class HandleQuitter implements ActionListener {
	
	private javax.swing.JFrame parentFrame;
	
	public HandleQuitter( javax.swing.JFrame parentFrame  ) {
		this.parentFrame = parentFrame; 
	}
	
	public void actionPerformed( ActionEvent context ) { 
		this.parentFrame.dispose(); 
//		System.exit(0); 
	}
}
