import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


public class CustomJButton extends JButton{
	public CustomJButton(){
		super();
		JButton btnThis = this;
	  this.setBorderPainted(false);
	  this.setBackground(Color.WHITE);
	  this.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				btnThis.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnThis.setBackground(Color.WHITE);
				
			}
	  	
	  });
	}
	public CustomJButton(String arg0){
		super(arg0);
		JButton btnThis = this;
	  this.setBorderPainted(false);
	  this.setBackground(Color.WHITE);
	  this.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				btnThis.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnThis.setBackground(Color.WHITE);
				
			}
	  	
	  });
	}
}
