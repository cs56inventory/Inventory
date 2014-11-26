import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class MainCustomJButton extends JButton{
	public MainCustomJButton(){
		super();
		
		JButton btnThis = this;
		Color c1 = new Color(255,127,80);
	//	Font f=new Font("Arial",Font.,25);
	  this.setBorderPainted(true);
	  this.setBackground(Color.WHITE);
	  this.setBorder(BorderFactory.createLineBorder(c1));
	  this.setForeground(c1);
	//  this.setFont(font);
	  this.addMouseListener(new MouseAdapter(){
	
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			  if(btnThis.isEnabled()){
					btnThis.setBackground(c1);
					btnThis.setForeground(Color.WHITE);
			  }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			  if(btnThis.isEnabled()){
					btnThis.setBackground(Color.WHITE);
					btnThis.setForeground(c1);
			  }
			}
	  	
	  });
	}
	public MainCustomJButton(String arg0){
		
		super(arg0);
		JButton btnThis = this;
		Color c1 = new Color(255,127,80);
		Color c2 = new Color(255,255,255);
		
	//	Font f=new Font("Arial",Font.,25);
	  this.setBorderPainted(true);
	  this.setBackground(c2);
	  this.setBorder(BorderFactory.createLineBorder(c1));
	  this.setForeground(c1);
	//  this.setFont(font);
	  this.addMouseListener(new MouseAdapter(){
	
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			  if(btnThis.isEnabled()){
					btnThis.setBackground(c1);
					btnThis.setForeground(c2);
			  }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			  if(btnThis.isEnabled()){
					btnThis.setBackground(c2);
					btnThis.setForeground(c1);
			  }
			}
	  	
	  });
	}
}
