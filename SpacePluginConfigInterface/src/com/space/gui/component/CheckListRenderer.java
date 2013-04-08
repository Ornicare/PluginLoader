/**
 * 
 */
package com.space.gui.component;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author CLEm
 *
 */
//Handles rendering cells in the list using a check box

public class CheckListRenderer extends JCheckBox implements ListCellRenderer<Object>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7790738510580456539L;
	
	@Override
	public Component getListCellRendererComponent( 
			@SuppressWarnings("rawtypes") JList list, 
			Object value,
			int index,
			boolean isSelected, 
			boolean hasFocus
	)
	{
		setEnabled( list.isEnabled() );
		setSelected( ( (CheckListItem)value ).isSelected() );
		setFont( list.getFont() );
		setBackground( list.getBackground() );
		setForeground( list.getForeground() );
		setText( value.toString() );
		
		return null;
	}
	
	
	
} 
