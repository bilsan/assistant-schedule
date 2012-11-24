/**
 * 
 */
package ru.kai.assistantschedule.status.open;

import org.eclipse.swt.graphics.Font;


/**
 * @author Роман
 * Консоль
 */
public interface IStatus {

   public void setText(String text);
   
   public void setFont(Font font);
   
   public void append(String string);

}
