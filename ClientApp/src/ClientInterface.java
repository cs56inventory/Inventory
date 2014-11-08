import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ClientInterface extends JFrame {
	private ClientApp app;
	private ClientWebsocketHandler socket;
	
	private JPanel loginPane;
	private JPanel contentPane;
	private final JLabel lblEmail = new JLabel("Email:");
	private final JLabel lblPassword = new JLabel("Password:");
	private final JTextField txtEmail = new JTextField();
	private final JTextField txtPassword = new JTextField();
	private final JButton btnLogin = new JButton("Login");
	private final JTable table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane(table);
	private final JButton btnLogout = new JButton("Logout");
	private DefaultTableModel model=null;
	private final JLabel lblLoginStatus = new JLabel("Not logged in.");
	private final JButton btnViewStores = new JButton("Stores");
	private final JButton btnPlaceOrder = new JButton("Place Order");
	private final JButton btnViewDistributors = new JButton("Distributors");
	private final JButton btnTrackOrder = new JButton("Track Order");
	private final JButton btnViewProducts = new JButton("All Products");
	private boolean loggedIn;
	private int userID;
	private final JButton btnInStock = new JButton("In Stock");
	private final JLabel lblTable = new JLabel("");
	String q = "";
	ResultSet rs;
	private int rowID;
	private String name;
	private String desc;
	private String dist;
	private int qty;
	private int numCols;
	private int valueChangedCount = 0;
	
//	******************
//	******************
//	MAIN METHOD
//	******************
//	******************
//	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					storeInterface frame = new storeInterface();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}//end main

	public ClientInterface(ClientApp clientApp) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPassword.setBounds(343, 182, 119, 20);
		
		txtPassword.setColumns(10);
		txtEmail.setBounds(343, 151, 119, 20);
		
		
		txtEmail.setColumns(10);
		initGUI();
		this.app = clientApp;
		this.socket = app.getSocket();
	}
	
	private void initGUI() {
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setTitle("Store Interface");
		
//		******************
//		******************
//		GUI COMPONENTS AND DIMENSIONS
//		******************
//		******************
		
		//end program when user exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//dimensions of outer jframe
		setBounds(100, 100, 818, 512);
		
		//content pane within jframe holds components
		contentPane = new JPanel();
		loginPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		setContentPane(loginPane);
		scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setBounds(20, 154, 761, 309);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		btnViewStores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnViewStores.setBounds(629, 68, 152, 44);
		setVisible(true);
		
//		******************
//		******************
//		Stores Button
//		******************
//		******************	
		btnViewStores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//make sure user is logged in before accessing db
				if(!loggedIn){
					lblLoginStatus.setText("Log in first.");
				}else{
				//load driver
				try {
					
					dbc dbc = new dbc();
					
					q="SELECT [store_id],[store_name],[store_street_address],[store_city],"
							+ "[store_state],[store_zip_code],[store_phone_number]"
							+ "FROM [dbo].[store]";
					dbc.setQuery(q);
					rs = dbc.getRs();
					dbc.getRsmd();
					
					numCols = dbc.getNumCols();
					model.setRowCount(0);
					String[] oldCols = null;
					model.setColumnIdentifiers(oldCols);
					String[] colNames = {"Store ID", "Name", "Address", "City", "State", "Zip", "Phone"};
					
					model = (DefaultTableModel) table.getModel();
					model.setColumnIdentifiers(colNames);
					
					while(rs.next()){
						Object[] objects = new Object[numCols];
						for(int i=0; i<numCols; i++){
							objects[i] = rs.getObject(i+1);
						}
						model.addRow(objects);
					}//end while

					table.setModel(model);
					lblTable.setText("Viewing all stores:");
				
					dbc.closeConn();
			}catch (ClassNotFoundException | SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			}
			}//end login else
		}//end handler
	});
		
		btnViewDistributors.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnViewDistributors.setBounds(629, 16, 152, 44);
	
		
//		******************
//		******************
//		Distributors Button
//		******************
//		******************
		btnViewDistributors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//make sure user is logged in before accessing db
				if(!loggedIn){
					lblLoginStatus.setText("Log in first.");
				}else{
				
				try {
					
					dbc dbc = new dbc();
					q = "SELECT [distributor_id], [distributor_name] FROM [dbo].[distributor]";
					dbc.setQuery(q);
					rs = dbc.getRs();
					dbc.getRsmd();
				
					int numCols = dbc.getNumCols();
				
					model.setRowCount(0);
					String[] oldCols = null;
					model.setColumnIdentifiers(oldCols);
					//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
					String[] colNames = {"Dist. ID", "Name"};
					
					model = (DefaultTableModel) table.getModel();
					model.setColumnIdentifiers(colNames);
					
					//fill jtable with rows from result set
					while(rs.next()){
						Object[] objects = new Object[numCols];
						for(int i=0; i<numCols; i++){
							objects[i] = rs.getObject(i+1);
						}
						model.addRow(objects);
					}//end while
					table.setModel(model);
					lblTable.setText("Viewing all distributors:");
					//close connection
					dbc.closeConn();
			}catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			}
			}//end login else
		}//end handler
	});
		
		btnTrackOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTrackOrder.setBounds(467, 68, 152, 44);
		btnTrackOrder.setPreferredSize(new Dimension(56, 23));
		btnViewProducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnViewProducts.setBounds(305, 16, 152, 44);

		
//		******************
//		******************
//		Products Button
//		******************
//		******************
		btnViewProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//make sure user is logged in before accessing db
				if(!loggedIn){
					lblLoginStatus.setText("Log in first.");
				}else{
				//load driver
				try {
					dbc dbc = new dbc();
					q = "SELECT [dbo].[product].[product_upc],[product_name],[product_description],[dbo].[distributor].[distributor_name],"
						+ "[distributor_product_quantity]"
						+ "FROM [dbo].[product]"
						+ "INNER JOIN [dbo].[distributor_product] ON [dbo].[distributor_product].[product_upc] = [dbo].[product].[product_upc]"
						+ "INNER JOIN [dbo].[distributor] ON [dbo].[distributor].[distributor_id] = [dbo].[distributor_product].[distributor_id]";
					dbc.setQuery(q);
					rs = dbc.getRs();
					dbc.getRsmd();
				
					int numCols = dbc.getNumCols();
								
					model.setRowCount(0);
					String[] oldCols = null;
					model.setColumnIdentifiers(oldCols);
					//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
					String[] colNames = {"UPC", "Name", "Description", "Distributor", "Qty"};
					
					model = (DefaultTableModel) table.getModel();
					model.setColumnIdentifiers(colNames);
					
					//fill jtable with rows from result set
					while(rs.next()){
						Object[] objects = new Object[numCols];
						for(int i=0; i<numCols; i++){
							objects[i] = rs.getObject(i+1);
						}
						model.addRow(objects);
					}//end while
					table.setModel(model);
					
//					******************
//					******************
//					JTable Cell Click Event
//					******************
//					******************
					table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//							from stackoverflow.com on 11/2/14
				        	public void valueChanged(ListSelectionEvent event) {
//				        		each change in direction of the mouse button constitutes a change in value, so one click will open
//				        		2 new forms; the valueChangedCount control prevents that from happening
				        		valueChangedCount += 1;
				        		if(valueChangedCount % 2 != 0){
				        		try{						        	
			        				rowID = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
			        				try{
						        		name = (table.getValueAt(table.getSelectedRow(), 1).toString());
						        	}catch(NullPointerException ex){
			        					name = "Not entered";
			        				}try{
			        					desc = (table.getValueAt(table.getSelectedRow(), 2).toString());
			        				}catch(NullPointerException ex){
			        					desc = "Not entered";
			        				}try{
			        					dist = (table.getValueAt(table.getSelectedRow(), 3).toString());
			        				}catch(NullPointerException ex){
			        					dist = "Not entered";
			        				}try{
			        					qty = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 4).toString());
			        				}catch(NullPointerException ex){
			        					qty = -1;
			        				}
			        				
			        				//open the new form
				        			new detailsForm(rowID, name, desc, dist, qty);
				        			
					        	}catch(ArrayIndexOutOfBoundsException ex){
				        		}
				        		}//end valueChangedCount if statement
				        		
				        	}//end valueChanged listener			

				    });//end listener
					valueChangedCount = 0;
					lblTable.setText("Viewing products by distributor:");
					//close connection
					dbc.closeConn();
			}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			}
		}//end login else
		}//end event handler
	});
		btnInStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInStock.setBounds(467, 16, 152, 44);

		
//		******************
//		******************
//		In Stock Button
//		******************
//		******************
		btnInStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//make sure user is logged in before accessing db
				if(!loggedIn){
					lblLoginStatus.setText("Log in first.");
				}else{
				//load driver
				try {
					dbc dbc = new dbc();
					q = "SELECT [dbo].[product].[product_upc],[dbo].[store_member].[store_id],[product_name],[product_description],[store_product_price],"
						+ "[store_product_quantity],[min_product_quantity]"
						+ "FROM [dbo].[store_member] "
						+ "INNER JOIN [dbo].[store_product] ON [dbo].[store_product].[store_id] = [dbo].[store_member].[store_id]"
						+ "INNER JOIN [dbo].[product] ON [dbo].[product].[product_upc] = [dbo].[store_product].[product_upc]"
						+ "WHERE [user_id] = "  + userID;
					dbc.setQuery(q);
					rs = dbc.getRs();
					dbc.getRsmd();
				
					int numCols = dbc.getNumCols();	
				
					model.setRowCount(0);
					String[] oldCols = null;
					model.setColumnIdentifiers(oldCols);
					String[] colNames = {"UPC", "Store ID", "Name", "Description", "Price", "Qty", "Min Qty"};
					
					model = (DefaultTableModel) table.getModel();
					model.setColumnIdentifiers(colNames);
					
					//fill jtable with rows from result set
					while(rs.next()){
						Object[] objects = new Object[numCols];
						for(int i=0; i<numCols; i++){
							objects[i] = rs.getObject(i+1);
						}
						model.addRow(objects);
					}//end while
					table.setModel(model);
					lblTable.setText("Viewing this store's products:");
			//close connection
				dbc.closeConn();
			}catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			}
		}//end login else
			}//end event handler
		});//end listener
		
		contentPane.setLayout(null);
				btnLogout.setBounds(0, 0, 118, 25);
				contentPane.add(btnLogout);
				btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
				
						
				//		******************
				//		******************
				//		LOG OUT BUTTON
				//		******************
				//		******************
						btnLogout.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {
								//clear contents of jtable & delete column headers
								model.setRowCount(0);
								String[] colNames = null;
								model.setColumnIdentifiers(colNames);
								
								//reset login fields and status message
								txtEmail.setText("");
							    txtPassword.setText("");
							    txtEmail.requestFocus();
							    
							    loggedIn = false;
							    loginPane.setVisible(true);
								setContentPane(loginPane);
							}//end mouseClicked
						});
		contentPane.add(scrollPane);
		lblLoginStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginStatus.setBounds(20, 129, 172, 23);
		contentPane.add(lblLoginStatus);
		contentPane.add(btnViewStores);
		btnPlaceOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPlaceOrder.setBounds(305, 68, 152, 44);
		contentPane.add(btnPlaceOrder);
		contentPane.add(btnViewDistributors);
		contentPane.add(btnTrackOrder);
		contentPane.add(btnViewProducts);
		contentPane.add(btnInStock);
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTable.setBounds(254, 129, 293, 23);
		contentPane.add(lblTable);
		loginPane.setLayout(null);
		loginPane.setBounds(0, 0, 802, 474);
		loginPane.add(contentPane);
		btnLogin.setBounds(327, 216, 86, 25);
		contentPane.setVisible(false);
		loginPane.setVisible(true);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
				
		//		******************
		//		******************
		//		LOG IN BUTTON
		//		******************
		//		******************
				btnLogin.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						
						
						//load driver
						try {
							
							q = "SELECT [user_Id], [user_email], [user_password] FROM [dbo].[user] WHERE [user_email] = '" + txtEmail.getText()
															+ "' AND [user_password] = '" + txtPassword.getText()+"'";
							dbc dbc = new dbc();
							dbc.setQuery(q);
							rs = dbc.getRs();
							int count = 0;
							while(rs.next()) {
							    ++count;
							    Object[] users = new Object[3];
								for(int i=0; i<3; i++){
									users[i] = rs.getObject(i+1);
								}
								userID = ((int)users[0]);
							}//end while
						
						//if no results or more than 1 result, login fails; reset login fields and change status label message
						if (count == 0 && loggedIn==false) {
							lblLoginStatus.setText("Login failed. Please try again.");
						    txtEmail.setText("");
						    txtEmail.requestFocus();
						    txtPassword.setText("");
						    dbc.closeConn();
						
						  //message if user is already logged in  
						}else if(loggedIn){
							lblLoginStatus.setText("Already logged in.");
						}
						//begin login else for successful login
						else{
							
							loggedIn = true;
							lblLoginStatus.setText("Logged in.");
							txtEmail.setText("");
							txtPassword.setText("");
							lblTable.setText("Viewing Store Products Table");
							
							q = "SELECT [dbo].[store_member].[store_id],[product_name],[product_description],[store_product_price],"
									+ "[store_product_quantity],[min_product_quantity]"
									+ "FROM [dbo].[store_member] "
									+ "INNER JOIN [dbo].[store_product] ON [dbo].[store_product].[store_id] = [dbo].[store_member].[store_id]"
									+ "INNER JOIN [dbo].[product] ON [dbo].[product].[product_upc] = [dbo].[store_product].[product_upc]"
									+ "WHERE [user_id] = "  + userID;
							dbc.setQuery(q);
							rs = dbc.getRs();
							dbc.getRsmd();
							
							int numCols = dbc.getNumCols();
							
							//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
							String[] colNames = {"Store ID", "Name", "Description", "Price", "Qty", "Min Qty"};
							model = (DefaultTableModel) table.getModel();
							model.setColumnIdentifiers(colNames);
							
							while(rs.next()){
								Object[] objects = new Object[numCols];
								for(int i=0; i<numCols; i++){
									objects[i] = rs.getObject(i+1);
								}
								model.addRow(objects);
							}//end while
							table.setModel(model);
							
							contentPane.setVisible(true);
							setContentPane(contentPane);
						}//end login else
						
							dbc.closeConn();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}				
					}//end mouseClicked
				});
		lblEmail.setBounds(295, 151, 36, 17);
		loginPane.add(lblEmail);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		//add user components to contentPane; establish dimensions of components
		lblEmail.setHorizontalTextPosition(SwingConstants.LEFT);
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(273, 182, 63, 17);
		loginPane.add(lblPassword);
		loginPane.add(lblEmail);	
		loginPane.add(txtEmail);
		loginPane.add(txtPassword);
		loginPane.add(btnLogin);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setHorizontalTextPosition(SwingConstants.LEFT);
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
	}//end Init GUI	
}//end JFrame class

