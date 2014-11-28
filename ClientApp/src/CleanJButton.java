import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class CleanJButton extends CustomJButton{
	static Color initForegroundColor = new Color(70,130,180);
	static Color initBackgroundColor = new Color(255,255,255);
	
	public CleanJButton(){
		
		super( initForegroundColor, initBackgroundColor, true );
	}
	
	public CleanJButton(String arg0){
		
		super( arg0, initForegroundColor, initBackgroundColor, true );
	}
}
