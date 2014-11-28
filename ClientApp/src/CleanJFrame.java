import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;


public class CleanJFrame extends JFrame{

		int pX,pY;
		public CleanJFrame(){
			super();
//			this.setLayout(new BorderLayout());
			this.setUndecorated(true);
			this.getRootPane().setJMenuBar(new CustomTitleBar(this));
			this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		          setLocation(getLocation().x+e.getX()-pX,getLocation().y+e.getY()-pY);
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
		  g[0].setFullScreenWindow(g[0].getFullScreenWindow()==this?null:this);
	  }

}
