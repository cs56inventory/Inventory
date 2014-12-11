package com.inventoryApp;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;


public class CustomJPanel extends JPanel{
	
	public CustomJPanel(){
		super();
		this.setBackground(Color.WHITE);
	}
	public CustomJPanel(LayoutManager layout){
		super(layout);
		this.setBackground(Color.WHITE);
		
	}
}
