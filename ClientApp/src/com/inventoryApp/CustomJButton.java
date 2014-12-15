package com.inventoryApp;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class CustomJButton extends JButton{
	public CustomJButton( Color initForegroundColor, Color initBackgroundColor, boolean border){
		super();
		JButton btnThis = this;
		
	  this.setBackground(initBackgroundColor);
	  if(border){
		  this.setBorderPainted(true);
		  this.setBorder(BorderFactory.createLineBorder(initForegroundColor));
	  }
	  else{
		  this.setBorderPainted(false);
	  	this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	  }

	  this.setForeground(initForegroundColor);

	  this.addMouseListener(new MouseAdapter(){
	
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			  if(btnThis.isEnabled()){
					btnThis.setBackground(initForegroundColor);
					btnThis.setForeground(initBackgroundColor);
			  }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			  if(btnThis.isEnabled()){
					btnThis.setBackground(initBackgroundColor);
					btnThis.setForeground(initForegroundColor);
			  }
			}
	  });
	}
	public CustomJButton(String arg0, Color initForegroundColor, Color initBackgroundColor, boolean border){
		super(arg0);
		JButton btnThis = this;
		

	  this.setBackground(initBackgroundColor);
	  if(border){
		  this.setBorderPainted(true);
		  this.setBorder(BorderFactory.createLineBorder(initForegroundColor));
	  }
	  else{
		  this.setBorderPainted(false);
	  	this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	  }
	  this.setForeground(initForegroundColor);

	  this.addMouseListener(new MouseAdapter(){
	
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			  if(btnThis.isEnabled()){
					btnThis.setBackground(initForegroundColor);
					btnThis.setForeground(initBackgroundColor);
			  }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			  if(btnThis.isEnabled()){
					btnThis.setBackground(initBackgroundColor);
					btnThis.setForeground(initForegroundColor);
			  }
			}
	  });
	}
}