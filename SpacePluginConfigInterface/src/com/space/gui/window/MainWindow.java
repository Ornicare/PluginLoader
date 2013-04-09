/**
 * 
 */
package com.space.gui.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.space.gui.component.CheckBoxList;
import com.space.gui.component.TopMenuBar;
import com.space.main.SpacePluginConfigInterface;



/**
 * @author CLEm
 *
 */
public class MainWindow extends JFrame {

	/**
	 * generated serial version ID 
	 */
	private static final long serialVersionUID = 3234852817461869766L;
	
	
	
	/** */
    private JPanel contentPane;
    private CheckBoxList list01;
    
//    private CheckBoxListScrollPane list02;
	private List<String> selectedList01;
//    private List<String> selectedList02;



	@SuppressWarnings("unused")
	private SpacePluginConfigInterface spacePluginConfigInterface;
    
    
    
public MainWindow( String[] availablePlugins, final SpacePluginConfigInterface spacePluginConfigInterface ) {
		this.setTitle( "Space" ); 
		this.setSize( 320, 480 ); 
		this.setLocationRelativeTo( null ); 
		this.spacePluginConfigInterface = spacePluginConfigInterface;
		
		// *** components ***
			// top menu bar 
        	this.setJMenuBar( new TopMenuBar(this).getMenuBar() );
        	
        	// button: play
//        	buttonPlay = new JButton("Jouer !");
//    		buttonPlay.setMnemonic(KeyEvent.VK_N);
//    		buttonPlay.setActionCommand("enable");
//    		buttonPlay.setEnabled(false);
//    		buttonPlay.addActionListener( handleNouvellePartie );
        	
        	
        	this.contentPane = new JPanel();
        	this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        	this.setContentPane(this.contentPane);
        	this.contentPane.setLayout(null);
        	
        	this.list01 = new CheckBoxList();
        	this.list01.setBounds(5, 45, 290, 300);
        	this.contentPane.add(this.list01);
        	String[] initData01 = availablePlugins;
        	this.list01.addCheckbox(initData01);
        	
        	JButton btnNewButton = new JButton("Run");
        	btnNewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent arg0) {
                    selectedList01 = list01.getCheckedItems();
                    
                    //System.out.println(selectedList01);
                    
                    spacePluginConfigInterface.launch(selectedList01);
    
                    //dispose();
                }
            });
            btnNewButton.setBounds(5, 365, 80, 40);
            this.contentPane.add(btnNewButton);
            
            JLabel lblNewLabel = new JLabel("Sélectionner les plugins à activer: ");
            lblNewLabel.setBounds(5, 5, 300, 35);
            contentPane.add(lblNewLabel);
            
        	
        // exit 
        this.setDefaultCloseOperation( javax.swing.JFrame.DISPOSE_ON_CLOSE ); 
        
        // draw 
		this.setVisible( true ); 
		
		
	}
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		@SuppressWarnings("unused")
//		String[] ok = {"ok"};
//		MainWindow testMainWindow = new MainWindow(ok);
//	}

}
