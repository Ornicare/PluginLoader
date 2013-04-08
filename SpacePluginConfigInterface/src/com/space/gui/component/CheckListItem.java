/**
 * 
 */
package com.space.gui.component;

/**
 * @author CLEm
 *
 *	Represents items in the list that can be selected
 */
public class CheckListItem
{
   private String  label;
   private boolean isSelected = false;

   public CheckListItem(String label)
   {
      this.label = label;
   }

   public boolean isSelected()
   {
      return isSelected;
   }

   public void setSelected(boolean isSelected)
   {
      this.isSelected = isSelected;
   }

   public String toString()
   {
      return label;
   }
   
   
   
}
