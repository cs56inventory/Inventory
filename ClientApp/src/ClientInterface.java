import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*******************************
 * Client Interface JFrame
 *******************************/
public class ClientInterface extends CleanJFrame {
	private ClientApp app;
	private ClientWebsocketHandler socket;
	private LoginPane loginPane;
	private JPanel contentPane;
	private boolean loggedIn;

	//tables and their scroll panes
	//3 tables: products, orders placed, orders details
	private DefaultTableModel model=null;
	private final RowTable tableProducts = new RowTable(model);
	private final JScrollPane scrollPaneProducts = new JScrollPane(tableProducts);
	private final RowTable tableOrdersPlaced = new RowTable(model);
	private final JScrollPane scrollPaneOrdersPlaced = new JScrollPane(tableOrdersPlaced);
	private final JTable tableOrdersDetails = new JTable();
	private final JScrollPane scrollPaneOrdersDetails = new JScrollPane(tableOrdersDetails);
	private final JButton btnLogout = new CleanJButton("Logout");
	
	
	
	public ClientInterface(ClientApp clientApp) {

		
		tableProducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableOrdersPlaced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableOrdersDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));

		initGUI();

		this.app = clientApp;
	}
	
	private void initGUI(){
		
		contentPane = new ContentPane();
		loginPane = new LoginPane();
		
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setTitle("Store Interface");
		setContentPane(loginPane);
		setSize(1280, 654);
		setLocationRelativeTo(null);
		setVisible(true);

		/*******************************
		 * LOG OUT BUTTON
		 *******************************/
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//clear contents of jtable & delete column headers
				model.setRowCount(0);
				String[] colNames = null;
				model.setColumnIdentifiers(colNames);
				
				//reset login fields and status message
					loginPane.enableBtnLogin();
			    loggedIn = false;
			    loginPane.setVisible(true);
			    loginPane.btnLogin.setEnabled(true);
				setContentPane(loginPane);
			}//end mouseClicked
		});
	}
	
	
	/*******************************
	 * Main Content Panel
	 *******************************/
	class  ContentPane extends CustomJPanel{
		ContentPane(){
			this.setLayout(new BorderLayout());
		 	JPanel contentPaneContent = new CustomJPanel();
		 	JPanel btnLogoutPanel = new CustomJPanel(new FlowLayout(FlowLayout.LEADING,30,0));
		 	JLabel lblProducts = new JLabel("Available Products");
		 	JLabel lblOrders = new JLabel("Created Orders");
		 	JLabel lblOrderDetails = new JLabel("Order Details");
		 	
		 	lblProducts.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 	lblOrders.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 	lblOrderDetails.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 	
		 	JPanel lblProductsPanel = new CustomJPanel(new FlowLayout(FlowLayout.CENTER));
		 	JPanel lblOrdersPanel = new CustomJPanel(new FlowLayout(FlowLayout.CENTER));
		 	JPanel lblOrderDetailsPanel = new CustomJPanel(new FlowLayout(FlowLayout.CENTER));
		 	lblProductsPanel.add(lblProducts);
		 	lblOrdersPanel.add(lblOrders);
		 	lblOrderDetailsPanel.add(lblOrderDetails);
		 	
		 	
		 	btnLogoutPanel.add(btnLogout);
		 	contentPaneContent.setLayout(new FlowLayout());
		 	
		 	JPanel productsPanel = new CustomJPanel(new BorderLayout());
		 	JPanel ordersPanel = new CustomJPanel(new BorderLayout());
		 	JPanel orderDetailsPanel = new CustomJPanel(new BorderLayout());
		 	
		 	scrollPaneProducts.setBackground(Color.WHITE);
			scrollPaneProducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
	//		scrollPaneProducts.setBounds(20, 154, 600, 200);
			scrollPaneProducts.setPreferredSize(new Dimension(600,250));
			scrollPaneProducts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			scrollPaneOrdersPlaced.setFont(new Font("Tahoma", Font.PLAIN, 14));
	//		scrollPaneOrdersPlaced.setBounds(640, 154, 600, 200);
			scrollPaneOrdersPlaced.setPreferredSize(new Dimension(600,250));
			scrollPaneOrdersPlaced.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			scrollPaneOrdersDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
	//		scrollPaneOrdersDetails.setBounds(20, 374, 600, 200);
			scrollPaneOrdersDetails.setPreferredSize(new Dimension(600,250));
			scrollPaneOrdersDetails.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnLogout.setPreferredSize(new Dimension(150,40));
			
			productsPanel.add(lblProductsPanel, BorderLayout.NORTH);
			productsPanel.add(scrollPaneProducts, BorderLayout.CENTER);
			ordersPanel.add(lblOrdersPanel, BorderLayout.NORTH);
			ordersPanel.add(scrollPaneOrdersPlaced, BorderLayout.CENTER);
			orderDetailsPanel.add(lblOrderDetailsPanel, BorderLayout.NORTH);
			orderDetailsPanel.add(scrollPaneOrdersDetails, BorderLayout.CENTER);
			
			contentPaneContent.add(productsPanel);
			contentPaneContent.add(ordersPanel);
			contentPaneContent.add(orderDetailsPanel);
			this.add(btnLogoutPanel,BorderLayout.NORTH);
			this.add(contentPaneContent,BorderLayout.CENTER);
		}
	}
	/*******************************
	 * END  Main Content Panel
	 *******************************/
	
	/*******************************
	 * Login Panel
	 *******************************/
	class LoginPane extends CustomJPanel{
		 final JLabel lblConnectionStatus = new JLabel("Connecting...");
		 final JLabel lblEmail = new JLabel("Email:");
		 final JLabel lblPassword = new JLabel("Password:");
		 final JTextField txtEmail = new JTextField();
		 final JPasswordField txtPassword = new JPasswordField();
		 final JButton btnLogin = new CleanJButton("Login");
		 final JLabel lblLoginStatus = new JLabel("Not logged in.");
		 
		 LoginPane(){
			
			 	this.setLayout(new BorderLayout());
			 	
			 	JPanel loginPaneContent = new CustomJPanel();
			 	JPanel connectStatusPanel = new CustomJPanel(new FlowLayout(FlowLayout.LEADING,30,0));
			 	connectStatusPanel.add(lblConnectionStatus);
			 	loginPaneContent.setLayout(new GridBagLayout());


				btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 36));
				btnLogin.setEnabled(false);
				btnLogin.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				btnLogin.setForeground(Color.LIGHT_GRAY);
				btnLogin.setPreferredSize(new Dimension(150,50));
				
				txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 36));
				txtPassword.setColumns(15);
				
				txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 36));
				txtEmail.setColumns(15);
				
				lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
				lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
				lblLoginStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

				//		******************
				//		******************
				//		LOG IN BUTTON
				//		******************
				//		******************
				btnLogin.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(btnLogin.isEnabled()){
							socket = app.getSocket();
							socket.send( "login", new User(txtEmail.getText(), txtPassword.getText()) );
							btnLogin.setEnabled(false);					
						}
					}//end mouseClicked
				});
				JPanel loginP = new CustomJPanel();
				GroupLayout layout = new GroupLayout(loginP);
				loginP.setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				layout.setHorizontalGroup(
					   layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					           .addComponent(lblEmail)
					           .addComponent(lblPassword))
 					      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER )
					           .addComponent(txtEmail)
					           .addComponent(txtPassword)
					           .addComponent(btnLogin,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					);
				layout.setVerticalGroup(
					   layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER )
					           .addComponent(lblEmail)
					           .addComponent(txtEmail)
					           )
					      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER )
					           .addComponent(lblPassword)
					           .addComponent(txtPassword)
					           )					           
					      .addComponent(btnLogin,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					);
				

				loginPaneContent.add(loginP,new GridBagConstraints());
				this.add(loginPaneContent,BorderLayout.CENTER);
				this.add(connectStatusPanel, BorderLayout.NORTH);
				
				this.addComponentListener ( new ComponentAdapter (){
		        public void componentShown ( ComponentEvent e )
		        {
							txtEmail.setText("");
					    txtPassword.setText("");
					    txtEmail.requestFocus();
		        }

		        public void componentHidden ( ComponentEvent e ){
							txtEmail.setText("");
					    txtPassword.setText("");
					    txtEmail.requestFocus();
		        }
		    } );
		}	
		 
		public void enableBtnLogin(){
			Color c1 = new Color(70,130,180);
			btnLogin.setEnabled(true);
			btnLogin.setBorder(BorderFactory.createLineBorder(c1));
			btnLogin.setForeground(c1);
			btnLogin.setBackground(Color.WHITE);
		
		}

		public void loginFailed(){
			//if no results or more than 1 result, login fails; reset login fields and change status label message
			System.out.println(socket.user.getUser_Id() );
			if (socket.user.getUser_Id() == 0 && loggedIn==false) {
					lblLoginStatus.setText("Login failed. Please try again.");
			    txtEmail.setText("");
			    txtPassword.setText("");
			    txtEmail.requestFocus();
			    enableBtnLogin();
			} 
		}		
	}
	/*******************************
	 * END Login Panel
	 *******************************/
	
	
	
	public void login(){

		loggedIn = true;
		setContentPane(contentPane);
		this.setVisible(true);
	}
	
	
	/*******************************
	 * Fills Store Products table
	 *******************************/

	public void fillStoreProducts(){
	//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
		String[] colNames = {"Product UPC", "Name", "Description", "Price", "Qty", "Min Qty"};
		model = (DefaultTableModel) tableProducts.getModel();
		model.setColumnIdentifiers(colNames);
		Store_Product m = new Store_Product();

		for(Entry<?, ?> entry: this.socket.storeProducts.entrySet()){
			entry.getKey();
			Store_Product spr = (Store_Product)entry.getValue();
			Object[] properties = new Object[]{spr.getProduct_upc(),this.socket.products.get(spr.getProduct_upc()).getProduct_name(), 
					this.socket.products.get(spr.getProduct_upc()).getProduct_description(),spr.getStore_product_price(),spr.getStore_product_quantity(),
					spr.getMin_product_quantity()};
			model.addRow(properties);

		}
	}
	
	/*******************************
	 * Updates table
	 *******************************/
	public void updateTable(Store_Product p){
		for(int i=0;i<model.getRowCount();i++){
			int upc = new Integer(model.getValueAt(i, 0).toString());
			if( upc == p.getProduct_upc()){
				tableProducts.setRowColor(i, Color.RED);
				tableProducts.repaint();
				updateRowValue(p.getStore_product_quantity(), i, 4);
				}
			}
	}
	public void updateRowValue(Object value, int row, int column){
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				model.setValueAt(value, row, column);
				tableProducts.repaint();
				resetTableRowColor(row);
			}
		}, 500);
	}
	public void resetTableRowColor(int row){
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				tableProducts.setRowColor(row, Color.WHITE);
				tableProducts.repaint();
				
			}
		}, 1000);
	}

	public void loginFailed() {
		loginPane.loginFailed();
	}

	public void connected() {
		loginPane.enableBtnLogin();
		loginPane.lblConnectionStatus.setText("Connected");
	}
}
/*******************************
 * End JFrame
 *******************************/