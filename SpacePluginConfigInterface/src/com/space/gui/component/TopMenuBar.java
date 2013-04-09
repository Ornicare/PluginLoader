/**
 * 
 */
package com.space.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.space.gui.handler.HandleQuitter;


/**
 * @author CLEm
 *
 */
public class TopMenuBar {
	
	private JMenuBar menuBar; 
	private JMenu menu; 
	private JMenuItem menuItem; 
	
	private HandleQuitter handleQuitter; 
	
	public TopMenuBar( javax.swing.JFrame parentFrame ){
        menuBar = new JMenuBar();

        //*/ Fichier 
        menu = new JMenu( "File" ); 
        
        menu.setMnemonic( KeyEvent.VK_F ); 
        menu.getAccessibleContext().setAccessibleDescription( 
        	"File" 
        ); 
        menuBar.add( menu ); 
        {
			// Nouveau 
//			menuItem = new JMenuItem( "Nouvelle partie..." ); 
//				
//				handleNouvellePartie = new controleur.handler.HandleNouvellePartie(); 
//				menuItem.addActionListener( handleNouvellePartie ); 
//				
//				menuItem.setMnemonic( KeyEvent.VK_N ); 
//				menuItem.setAccelerator( 
//					KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK ) 
//				); 
//				menuItem.getAccessibleContext().setAccessibleDescription( 
//					"Cr�er" 
//				); 
//			
//			menu.add( menuItem );
			
			// Separator
			menu.add( new JSeparator() );
			
			// Quit 
			menuItem = new JMenuItem( "Quit" ); 
				
				handleQuitter = new HandleQuitter( parentFrame ); 
				menuItem.addActionListener( handleQuitter ); 
				
				menuItem.setMnemonic( KeyEvent.VK_Q );
				menuItem.setAccelerator( 
					KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.ALT_MASK ) 
				); 
				menuItem.getAccessibleContext().setAccessibleDescription( 
					"Quit" 
				); 
				
			menu.add( menuItem ); 
        }
        //*/ 
        
	}
	
	public JMenuBar getMenuBar(){
		return menuBar; 
	}
	
}
