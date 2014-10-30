import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;

//import org.eclipse.wb.swing.FocusTraversalOnArray;

public class ClientInterface extends JFrame {

	private JPanel contentPane;
	private final JLabel lblEmail = new JLabel("Email:");
	private final JLabel lblPassword = new JLabel("Password:");
	private final JTextField txtEmail = new JTextField();
	private final JTextField txtPassword = new JTextField();
	private final JButton btnLogin = new JButton("Login");
	private final JLabel lblStoreInterface = new JLabel("Store Interface");
	private final JTable table = new JTable();
	private final JScrollPane scrollPane = new JScrollPane(table);
	private final JButton btnLogout = new JButton("Logout");
	private DefaultTableModel model = null;
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

	// ******************
	// ******************
	// MAIN METHOD --we don't need a main here. The interface will be launched
	// only after a connection is established.
	// ******************
	// ******************

	// public void createInterface() {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ClientInterface frame = new ClientInterface();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }//end main

	public ClientInterface() {
		// createInterface();
		txtPassword.setBounds(89, 46, 93, 20);
		txtPassword.setColumns(10);
		txtEmail.setBounds(89, 23, 93, 20);

		txtEmail.setColumns(10);
		initGUI();
		this.setVisible(true);
	}

	private void initGUI() {

		// ******************
		// ******************
		// GUI COMPONENTS AND DIMENSIONS
		// ******************
		// ******************

		// end program when user exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// dimensions of outer jframe
		setBounds(100, 100, 574, 406);

		// content pane within jframe holds components
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(txtEmail);
		contentPane.add(txtPassword);
		btnLogin.setBounds(10, 74, 83, 23);
		contentPane.add(btnLogin);

		// ******************
		// ******************
		// LOG IN BUTTON
		// ******************
		// ******************
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// load driver
				try {

					q = "SELECT [user_Id], [user_email], [user_password] FROM [dbo].[user] WHERE [user_email] = '"
							+ txtEmail.getText()
							+ "' AND [user_password] = '"
							+ txtPassword.getText() + "'";
					dbc dbc = new dbc();
					dbc.setQuery(q);
					rs = dbc.getRs();
					int count = 0;
					while (rs.next()) {
						++count;
						Object[] users = new Object[3];
						for (int i = 0; i < 3; i++) {
							users[i] = rs.getObject(i + 1);
						}
						userID = ((int) users[0]);
					}// end while

					// if no results or more than 1 result, login fails; reset
					// login fields and change status label message
					if (count == 0 && loggedIn == false) {
						lblLoginStatus
								.setText("Login failed. Please try again.");
						txtEmail.setText("");
						txtEmail.requestFocus();
						txtPassword.setText("");
						dbc.closeConn();

						// message if user is already logged in
					} else if (loggedIn) {
						lblLoginStatus.setText("Already logged in.");
					}
					// begin login else for successful login
					else {

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
								+ "WHERE [user_id] = " + userID;
						dbc.setQuery(q);
						rs = dbc.getRs();
						dbc.getRsmd();

						int numCols = dbc.getNumCols();

						// this method of filling the JTable was taken from
						// http://www.rgagnon.com/javadetails/java-0309.html,
						// last visited 10/26/14 by MGE
						String[] colNames = { "Store ID", "Name",
								"Description", "Price", "Qty", "Min Qty" };
						model = (DefaultTableModel) table.getModel();
						model.setColumnIdentifiers(colNames);

						while (rs.next()) {
							Object[] objects = new Object[numCols];
							for (int i = 0; i < numCols; i++) {
								objects[i] = rs.getObject(i + 1);
							}
							model.addRow(objects);
						}// end while
						table.setModel(model);
					}// end login else

					dbc.closeConn();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}// end mouseClicked
		});// end event handler for btnLogin
		btnLogout.setBounds(99, 74, 83, 23);
		contentPane.add(btnLogout);

		// ******************
		// ******************
		// LOG OUT BUTTON
		// ******************
		// ******************
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// clear contents of jtable & delete column headers
				model.setRowCount(0);
				String[] colNames = null;
				model.setColumnIdentifiers(colNames);

				// reset login fields and status message
				txtEmail.setText("");
				txtPassword.setText("");
				txtEmail.requestFocus();
				lblLoginStatus.setText("Not logged in.");
				lblTable.setText("");
				loggedIn = false;
			}// end mouseClicked
		});// end btnLogout handler

		// add user components to contentPane; establish dimensions of
		// components
		lblEmail.setHorizontalTextPosition(SwingConstants.LEFT);
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setBounds(10, 26, 50, 14);
		contentPane.add(lblEmail);
		lblPassword.setHorizontalTextPosition(SwingConstants.LEFT);
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(10, 49, 69, 14);
		contentPane.add(lblPassword);
		lblStoreInterface.setBounds(233, 0, 92, 14);
		contentPane.add(lblStoreInterface);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 129, 536, 195);
		contentPane.add(scrollPane);
		lblLoginStatus.setBounds(10, 104, 172, 14);
		contentPane.add(lblLoginStatus);

		// ******************
		// ******************
		// Stores Button
		// ******************
		// ******************
		btnViewStores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure user is logged in before accessing db
				if (!loggedIn) {
					lblLoginStatus.setText("Log in first.");
				} else {
					// load driver
					try {

						dbc dbc = new dbc();

						q = "SELECT [store_id],[store_name],[store_street_address],[store_city],"
								+ "[store_state],[store_zip_code],[store_phone_number]"
								+ "FROM [dbo].[store]";
						dbc.setQuery(q);
						rs = dbc.getRs();
						dbc.getRsmd();

						int numCols = dbc.getNumCols();
						model.setRowCount(0);
						String[] oldCols = null;
						model.setColumnIdentifiers(oldCols);
						String[] colNames = { "Store ID", "Name", "Address",
								"City", "State", "Zip", "Phone" };

						model = (DefaultTableModel) table.getModel();
						model.setColumnIdentifiers(colNames);

						while (rs.next()) {
							Object[] objects = new Object[numCols];
							for (int i = 0; i < numCols; i++) {
								objects[i] = rs.getObject(i + 1);
							}
							model.addRow(objects);
						}// end while
						table.setModel(model);
						lblTable.setText("Viewing all stores:");

						dbc.closeConn();
					} catch (ClassNotFoundException | SQLException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}// end login else
			}// end handler
		});// end listener
		btnViewStores.setBounds(435, 74, 113, 23);

		contentPane.add(btnViewStores);
		btnPlaceOrder.setBounds(192, 74, 113, 23);

		contentPane.add(btnPlaceOrder);

		// ******************
		// ******************
		// Distributors Button
		// ******************fdgtdgdfg
		// ******************
		btnViewDistributors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure user is logged in before accessing db
				if (!loggedIn) {
					lblLoginStatus.setText("Log in first.");
				} else {

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
						// this method of filling the JTable was taken from
						// http://www.rgagnon.com/javadetails/java-0309.html,
						// last visited 10/26/14 by MGE
						String[] colNames = { "Dist. ID", "Name" };

						model = (DefaultTableModel) table.getModel();
						model.setColumnIdentifiers(colNames);

						// fill jtable with rows from result set
						while (rs.next()) {
							Object[] objects = new Object[numCols];
							for (int i = 0; i < numCols; i++) {
								objects[i] = rs.getObject(i + 1);
							}
							model.addRow(objects);
						}// end while
						table.setModel(model);
						lblTable.setText("Viewing all distributors:");
						// close connection
						dbc.closeConn();
					} catch (ClassNotFoundException | SQLException ex) {
						ex.printStackTrace();
					}
				}// end login else
			}// end handler
		});// end listener
		btnViewDistributors.setBounds(435, 48, 113, 23);

		contentPane.add(btnViewDistributors);
		btnTrackOrder.setPreferredSize(new Dimension(56, 23));
		btnTrackOrder.setBounds(315, 74, 113, 23);

		contentPane.add(btnTrackOrder);

		// ******************
		// ******************
		// Products Button
		// ******************
		// ******************
		btnViewProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// make sure user is logged in before accessing db
				if (!loggedIn) {
					lblLoginStatus.setText("Log in first.");
				} else {
					// load driver
					try {
						dbc dbc = new dbc();
						q = "SELECT [product_name],[product_description],[dbo].[distributor].[distributor_name],"
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
						// this method of filling the JTable was taken from
						// http://www.rgagnon.com/javadetails/java-0309.html,
						// last visited 10/26/14 by MGE
						String[] colNames = { "Name", "Description",
								"Distributor", "Qty" };

						model = (DefaultTableModel) table.getModel();
						model.setColumnIdentifiers(colNames);

						// fill jtable with rows from result set
						while (rs.next()) {
							Object[] objects = new Object[numCols];
							for (int i = 0; i < numCols; i++) {
								objects[i] = rs.getObject(i + 1);
							}
							model.addRow(objects);
						}// end while
						table.setModel(model);
						lblTable.setText("Viewing products by distributor:");
						// close connection
						dbc.closeConn();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}// end login else
			}// end event handler
		});// end listener
		btnViewProducts.setBounds(192, 48, 113, 23);

		contentPane.add(btnViewProducts);

		// ******************
		// ******************
		// In Stock Button
		// ******************
		// ******************
		btnInStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make sure user is logged in before accessing db
				if (!loggedIn) {
					lblLoginStatus.setText("Log in first.");
				} else {
					// load driver
					try {
						User u = new User();
						ObjectWrapper data = new ObjectWrapper("connect", u);
						ClientApp.session.getRemote().sendBytes(data.getBuffer());
						
						dbc dbc = new dbc();
						q = "SELECT [dbo].[store_member].[store_id],[product_name],[product_description],[store_product_price],"
								+ "[store_product_quantity],[min_product_quantity]"
								+ "FROM [dbo].[store_member] "
								+ "INNER JOIN [dbo].[store_product] ON [dbo].[store_product].[store_id] = [dbo].[store_member].[store_id]"
								+ "INNER JOIN [dbo].[product] ON [dbo].[product].[product_upc] = [dbo].[store_product].[product_upc]"
								+ "WHERE [user_id] = " + userID;
						dbc.setQuery(q);
						rs = dbc.getRs();
						dbc.getRsmd();

						int numCols = dbc.getNumCols();

						model.setRowCount(0);
						String[] oldCols = null;
						model.setColumnIdentifiers(oldCols);
						String[] colNames = { "Store ID", "Name",
								"Description", "Price", "Qty", "Min Qty" };

						model = (DefaultTableModel) table.getModel();
						model.setColumnIdentifiers(colNames);

						// fill jtable with rows from result set
						while (rs.next()) {
							Object[] objects = new Object[numCols];
							for (int i = 0; i < numCols; i++) {
								objects[i] = rs.getObject(i + 1);
							}
							model.addRow(objects);
						}// end while
						table.setModel(model);
						lblTable.setText("Viewing this store's products:");
						// close connection
						dbc.closeConn();
					} catch (ClassNotFoundException | SQLException ex) {
						ex.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}// end login else
			}// end event handler
		});// end listener
		btnInStock.setBounds(315, 48, 113, 23);

		contentPane.add(btnInStock);
		lblTable.setBounds(182, 104, 194, 14);

		contentPane.add(lblTable);
		// contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new
		// Component[]{txtEmail, txtPassword, btnLogin, btnLogout, scrollPane,
		// lblStoreInterface, lblPassword, lblLoginStatus, lblEmail, table,
		// lblTable, btnPlaceOrder, btnViewProducts, btnInStock, btnViewStores,
		// btnViewDistributors, btnTrackOrder}));
	}// end Init GUI
}// end JFrame class