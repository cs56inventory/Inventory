package com.inventoryApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*******************************
 * Client Interface JFrame
 *******************************/
public class ClientInterface extends CleanJFrame {
	private ClientApp app;
	private ClientWebsocketHandler socket;
	private LoginPane loginPane;
	private ContentPane contentPane;
	private boolean loggedIn;
	//tables and their scroll panes
	//3 tables: products, orders placed, orders details
//	private DefaultTableModel model=null;
	private final RowTable tableProducts = new RowTable(null);
	private final JScrollPane scrollPaneProducts = new JScrollPane(tableProducts);
	private final RowTable tableOrders = new RowTable(null);
	private final JScrollPane scrollPaneOrdersPlaced = new JScrollPane(tableOrders);
	private final RowTable tableOrdersDetails = new RowTable(null);
	private final JScrollPane scrollPaneOrdersDetails = new JScrollPane(tableOrdersDetails);
	private final JButton btnLogout = new CleanJButton("Logout");
	JComboBox cmbOrderStatus = new JComboBox();
	
	
	public ClientInterface(ClientApp clientApp,  String title) {
		super(title);

		this.title=title;
		tableProducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableOrders.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableOrdersDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));

		initGUI();

		this.app = clientApp;
	}
	
	private void initGUI(){
		
		loginPane = new LoginPane();
		
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setTitle("Inventory");
		setContentPane(loginPane);
		setSize(1280, 700);
		setLocationRelativeTo(null);
		setVisible(true);

		/*******************************
		 * LOG OUT BUTTON
		 *******************************/
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

//				model.setColumnIdentifiers(colNames);
			    socket.timer.cancel();
			    socket.send("logout", socket.user);
					app.logout();
			}//end mouseClicked
		});
	}
	
	
	/*******************************
	 * Main Content Panel
	 *******************************/
	class  ContentPane extends CustomJPanel{
		JPanel clientInfoPanel;
	 	JPanel btnLogoutPanel = new CustomJPanel(new FlowLayout(FlowLayout.LEADING,30,0));
		ContentPane(){
			this.setLayout(new BorderLayout());
		 	JPanel contentPaneContent = new CustomJPanel();
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

		 	if(socket.distributor!=null){
		 		title="Distributor Inventory";
			 	clientInfoPanel = new CustomJPanel(new GridLayout(2,2));
			 	clientInfoPanel.add(new JLabel("User Name:"));
		 		clientInfoPanel.add(new JLabel(socket.user.getUser_first_name()+" "+socket.user.getUser_last_name()));	
		 		clientInfoPanel.add(new JLabel("Distributor Name:"));
		 		clientInfoPanel.add(new JLabel(socket.distributor.getDistributor_name()));		
		 	}
		 	else{
		 		title="Store Inventory";
			 	clientInfoPanel = new CustomJPanel(new GridLayout(5,2));
			 	clientInfoPanel.add(new JLabel("User Name:"));
		 		clientInfoPanel.add(new JLabel(socket.user.getUser_first_name()+" "+socket.user.getUser_last_name()));	
			 	clientInfoPanel.add(new JLabel("Store Name:"));
			 	clientInfoPanel.add(new JLabel(socket.store.getStore_name()));	
			 	clientInfoPanel.add(new JLabel("Store address:"));
			 	clientInfoPanel.add(new JLabel(socket.store.getStore_street_address()));	
			 	clientInfoPanel.add(new JLabel("")); 	
			 	clientInfoPanel.add(new JLabel(socket.store.getStore_city()+" "+socket.store.getStore_state()+" "+
			 			socket.store.getStore_zip_code()));	
		 	}
		 	btnLogoutPanel.add(clientInfoPanel);
		 	
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
			scrollPaneOrdersDetails.setPreferredSize(new Dimension(900,250));
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
		
		public void disableBtnLogin(){
			btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 36));
			btnLogin.setEnabled(false);
			btnLogin.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			btnLogin.setForeground(Color.LIGHT_GRAY);
		}
		public void loginFailed(){
			//if no results or more than 1 result, login fails; reset login fields and change status label message
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
		contentPane = new ContentPane();
		setContentPane(contentPane);
		this.getRootPane().setJMenuBar(new CustomTitleBar(this));
		this.setVisible(true);
	}
	
	/*******************************
	 * Fills Store Products table
	 *******************************/

	public void fillStoreProducts(){
	//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
		String[] colNames = {"Product UPC", "Name", "Description", "Price", "Qty", "Min Qty"};
		DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
		model.setColumnIdentifiers(colNames);

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
	 * Fills Distributor Products table
	 *******************************/

	public void fillDistributorProducts(){
	//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
		String[] colNames = {"Product UPC", "Name", "Description", "Qty"};
		DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
		model.setColumnIdentifiers(colNames);

		for(Entry<?, ?> entry: this.socket.distributorProducts.entrySet()){
			entry.getKey();
			Distributor_Product dpr = (Distributor_Product)entry.getValue();
			Object[] properties = new Object[]{dpr.getProduct_upc(),this.socket.products.get(dpr.getProduct_upc()).getProduct_name(), 
					this.socket.products.get(dpr.getProduct_upc()).getProduct_description(),dpr.getDistributor_product_quantity()};
			model.addRow(properties);

		}
	}
	/*******************************
	 * Fills Order Details table
	 *******************************/

	public void fillOrderDetails(int orderId){
	//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
		String[] colNames = {"Order ID", "Product UPC", "Name", "Description", "Per Unit Price", "Qty", "Total Price"};
//		DefaultTableModel model = (DefaultTableModel) tableOrdersDetails.getModel();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(colNames);
		tableOrdersDetails.setModel(model);
		LinkedHashMap<Integer, Order_Product> orderProducts = this.socket.orderProducts.get(orderId);
		for(Entry<?, ?> entry: orderProducts.entrySet()){
			entry.getKey();
			Order_Product opr = (Order_Product)entry.getValue();
			Product p = this.socket.products.get(entry.getKey());
			Object[] properties = new Object[]{opr.getOrder_id(), p.getProduct_upc(),p.getProduct_name(), p.getProduct_description(),
					opr.getOrder_product_total_price()/opr.getOrder_product_quantity(), opr.getOrder_product_quantity(), opr.getOrder_product_total_price()};
			model.addRow(properties);

		}
	}
	/*******************************
	 * Fills Orders  table
	 *******************************/
	public void fillOrders(){
		
		for(Entry<Integer, String> status: this.socket.statuses.entrySet()){
			this.cmbOrderStatus.addItem(status.getValue());
		}

	//this method of filling the JTable was taken from http://www.rgagnon.com/javadetails/java-0309.html, last visited 10/26/14 by MGE
		String[] colNames = {"Order ID", "Store ID", "Distributor ID", "Total Price", "Created At", "Updated At","Status"};
		DefaultTableModel model = (DefaultTableModel) tableOrders.getModel();
		model.setColumnIdentifiers(colNames);
		for(Entry<?, ?> entry: this.socket.orders.entrySet()){
			entry.getKey();
			Order order = (Order)entry.getValue();
			Object[] properties = new Object[]{order.getOrder_id(),order.getStore_id(), order.getDistributor_id(),order.getOrder_total_price(),
					order.getOrder_created_at(),order.getOrder_updated_at(), this.socket.statuses.get(order.getOrder_status_id()) };
			if(isNewOrder(order, model, properties)){
				model.addRow(properties);				
				model.fireTableDataChanged();
				int rowNumber = model.getRowCount()-1;
				//TODO - need to stop scrolling if a row is selected
				scrollToVisible(tableOrders,rowNumber,0);
			}
//			comboBox.setSelectedIndex(order.getOrder_status_id());

		}
		tableOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableOrders.getSelectedRow()>-1){
					fillOrderDetails(new Integer(tableOrders.getValueAt(tableOrders.getSelectedRow(), 0).toString()));
				}
				
			}
			
		});
		tableOrders.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(cmbOrderStatus));
		cmbOrderStatus.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {

		    if (e.getStateChange() == ItemEvent.SELECTED) {
		    	
		    	int orderId = (Integer)tableOrders.getValueAt(	tableOrders.getSelectedRow(), 0);
		    	Order order = socket.orders.get(orderId);
		    	order.setOrder_status_id(cmbOrderStatus.getSelectedIndex()+1);
		    	socket.send("update_order", order);
		    }
			}
		});
	}
	
	/*******************************
	 * Update/Add Order  table
	 *******************************/
	public void updateOrder(Order order){
	
//		String[] colNames = {"Order ID", "Store ID", "Distributor ID", "Total Price", "Created At", "Updated At","Status"};
		DefaultTableModel model = (DefaultTableModel) tableOrders.getModel();
//		model.setColumnIdentifiers(colNames);

		Object[] properties = new Object[]{order.getOrder_id(),order.getStore_id(), order.getDistributor_id(),order.getOrder_total_price(),
				order.getOrder_created_at(),order.getOrder_updated_at(),this.socket.statuses.get(order.getOrder_status_id())};
		if(isNewOrder(order, model, properties)){
			model.addRow(properties);
			model.fireTableDataChanged();
			int rowCount = model.getRowCount()-1;
			//TODO - need to stop scrolling if a row is selected
			scrollToVisible(tableOrders,rowCount,0);
		}
		else{
			for(int i=0;i<model.getRowCount();i++){
				int order_id = new Integer(model.getValueAt(i, 0).toString());
				if( order_id == order.getOrder_id()){
					scrollToVisible(tableOrders,i,0);
					tableOrders.setRowColor(i, Color.RED);
					tableOrders.repaint();

					updateRowValue(tableOrders, this.socket.statuses.get(order.getOrder_status_id()), i, 6);
				}
			}			
		}
		
	}	

	/*******************************
	 * Checks if order is new
	 *******************************/
	public boolean isNewOrder(Order o, DefaultTableModel model, Object[] properties){
		for(int i=0;i<model.getRowCount();i++){
			int order_id = new Integer(model.getValueAt(i, 0).toString());
			if( order_id == o.getOrder_id()){
					return false;
			}
		}
		return true;
	}
	/*******************************
	 * Updates table
	 *******************************/
	public void updateStoreProductsTable(Store_Product p){
		DefaultTableModel model = (DefaultTableModel)tableProducts.getModel();
		for(int i=0;i<model.getRowCount();i++){
			int upc = new Integer(model.getValueAt(i, 0).toString());
			if( upc == p.getProduct_upc()){
				tableProducts.setRowColor(i, Color.RED);
				tableProducts.repaint();
				updateRowValue(tableProducts, p.getStore_product_quantity(), i, 4);
				}
			}
	}

	public void updateRowValue(RowTable table, Object value, int row, int column){
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				table.getModel().setValueAt(value, row, column);
				table.repaint();
				resetTableRowColor(table, row);
			}
		}, 500);
	}
	public void resetTableRowColor(RowTable table, int row){
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				table.setRowColor(row, Color.WHITE);
				table.repaint();
				
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
	
	public void disconnected(){
		loginPane.disableBtnLogin();
		loginPane.lblConnectionStatus.setText("Connecting");
	}
	//got this method from http://stackoverflow.com/questions/853020/jtable-scrolling-to-a-specified-row-index
	public  void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
    if (!(table.getParent() instanceof JViewport)) {
        return;
    }
    JViewport viewport = (JViewport)table.getParent();
    
    // This rectangle is relative to the table where the
    // northwest corner of cell (0,0) is always (0,0).
    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

    // The location of the viewport relative to the table
    Point pt = viewport.getViewPosition();

    // Translate the cell location so that it is relative
    // to the view, assuming the northwest corner of the
    // view is (0,0)
    rect.setLocation(rect.x-pt.x, rect.y-pt.y);
//    rect.setLocation(rect.x-pt.x, rect.y+50);
//    table.scrollRectToVisible(rect);

    // Scroll the area into view
    viewport.scrollRectToVisible(rect);
//		table.repaint();
	}
}
/*******************************
 * End JFrame
 *******************************/