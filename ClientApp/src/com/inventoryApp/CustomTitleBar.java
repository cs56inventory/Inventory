package com.inventoryApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class CustomTitleBar extends JMenuBar{
	
	CleanJFrame frame;
	JPanel p;
	JButton close,min,max;
	JLabel title; 
	Font f=new Font("Arial",Font.PLAIN,25);
	int pX,pY;
	
	public CustomTitleBar(CleanJFrame frame){

		this.setLayout(new GridLayout(1,3));
	  this.frame=frame;
	  // Create panel
	  title = new JLabel(frame.title);
	  CustomJPanel buttonsPanel= new CustomJPanel(new BorderLayout());
	  CustomJPanel titlePanel = new CustomJPanel(new FlowLayout(FlowLayout.CENTER));
	  this.setBackground(Color.WHITE);
	  this.setOpaque(true);
	  this.setBorder(BorderFactory.createEmptyBorder());
	  p=new JPanel();
	  p.setBackground(Color.WHITE);
	  p.setOpaque(false);
	  p.setLayout(new GridLayout(1,3));
	  
	  // Create buttons
	  min = new CustomJButton("-", Color.LIGHT_GRAY, Color.WHITE, false);
	  max = new CustomJButton("+", Color.LIGHT_GRAY, Color.WHITE, false);
	  close  =new CustomJButton("X", Color.LIGHT_GRAY, Color.WHITE, false);

	  min.addActionListener(new ActionListener(){

	  		@Override
				public void actionPerformed(ActionEvent arg0) {
	        // minimize
	        frame.setState(JFrame.ICONIFIED);
					
				}
	  });
	  
	  max.addActionListener(new ActionListener(){
	  	
	      public void actionPerformed(ActionEvent ae)
	      {
	          maximize();
	      }
	  });
	  
	  close.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent ae)
	      {
	          // terminate program
	          System.exit(0);
	      }
	  });
	  
	  // set focus painted false
	  // i don't like it, so i removed it
	  // if you like, you can remove these steps
	  min.setFocusPainted(false);
	  max.setFocusPainted(false);
	  close.setFocusPainted(false);
	  
	  // font, again if you don't like you can
	  // remove these steps, also remove the Font object
	  min.setFont(f);
	  max.setFont(f);
	  close.setFont(f);
	  
	  // Add buttons

	  p.add(min);
	  p.add(max);
	  p.add(close);
	  title.setFont(f);
	  titlePanel.add(title);
	  buttonsPanel.add(p,BorderLayout.EAST);
	  add(new CustomJPanel());
	  add(titlePanel);
	  add(buttonsPanel);
	  // Add mouse listener for JMenuBar mb
	  addMouseListener(new MouseAdapter(){
	  	@Override
	      public void mousePressed(MouseEvent e)
	      {
	          // Get x,y and store them
	          pX=e.getX();
	          pY=e.getY();
	      }
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		            maximize();
		        }
		    }
	  });

	  // Add MouseMotionListener for detecting drag
	  addMouseMotionListener(new MouseAdapter(){
	      public void mouseDragged(MouseEvent e)
	      {
	          // Set the location
	          // get the current location x-co-ordinate and then get
	          // the current drag x co-ordinate, add them and subtract most recent
	          // mouse pressed x co-ordinate
	          // do same for y co-ordinate
	          frame.setLocation(frame.getLocation().x+e.getX()-pX,frame.getLocation().y+e.getY()-pY);
	      }
	  });
	}
	
	private void maximize(){
	  // Get GraphicsEnvironment object for getting GraphicsDevice object
	  GraphicsEnvironment env=GraphicsEnvironment.getLocalGraphicsEnvironment();
	  
	  // Get the screen devices
	  GraphicsDevice[] g=env.getScreenDevices();
	  
	  // I only have one, the first one
	  // If current window is full screen, set fullscreen window to null
	  // else set the current screen
	  g[0].setFullScreenWindow(g[0].getFullScreenWindow()==frame?null:frame);
  }
}
