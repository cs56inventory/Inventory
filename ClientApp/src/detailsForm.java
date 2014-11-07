import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import java.awt.Dimension;

public class detailsForm extends JFrame {

	private JPanel contentPane;
	private int rowID;
	private String name;
	private String desc;
	private String dist;
	private int qty;
	private final JLabel lblNewLabel = new JLabel("Product_UPC");
	private final JLabel lblProductName = new JLabel("Product Name");
	private final JLabel lblDescription = new JLabel("Description");
	private final JLabel lblDistributorName = new JLabel("Distributor Name");
	private final JLabel lblDistributorQuantity = new JLabel("Distributor Quantity");
	private final JTextField txtProduct_UPC = new JTextField();
	private final JTextField txtProductName = new JTextField();
	private final JTextField txtDistributorName = new JTextField();
	private final JTextField txtDistributorQuantity = new JTextField();
	private final JTextArea txaDescription = new JTextArea();

	//constructor	
	public detailsForm(int n, String name, String desc, String dist, int qty) {
		txtProduct_UPC.setBounds(168, 48, 210, 20);
		txtProduct_UPC.setColumns(10);
		
		this.rowID = n;
		this.name = name;
		this.desc = desc;
		this.dist = dist;
		this.qty = qty;
		
		txtProduct_UPC.setText(Integer.toString(rowID));
		txtProductName.setText(name);
		txaDescription.setText(desc);
		txtDistributorName.setText(dist);
		txtDistributorQuantity.setText(Integer.toString(qty));

		setTitle("Details View");
		setBounds(100, 100, 563, 438);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(400, 400));
		contentPane.setBounds(new Rectangle(0, 0, 400, 400));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pack();
		setVisible(true);
		
		lblNewLabel.setBounds(32, 47, 126, 23);
		
		contentPane.add(lblNewLabel);
		lblProductName.setBounds(32, 81, 126, 23);
		
		contentPane.add(lblProductName);
		lblDescription.setBounds(32, 115, 126, 23);
		
		contentPane.add(lblDescription);
		lblDistributorName.setBounds(32, 202, 126, 23);
		
		contentPane.add(lblDistributorName);
		lblDistributorQuantity.setBounds(32, 241, 126, 23);
		
		contentPane.add(lblDistributorQuantity);
		
		contentPane.add(txtProduct_UPC);
		txtProductName.setColumns(10);
		txtProductName.setBounds(168, 82, 210, 20);
		
		contentPane.add(txtProductName);
		txtDistributorName.setColumns(10);
		txtDistributorName.setBounds(168, 203, 210, 20);
		
		contentPane.add(txtDistributorName);
		txtDistributorQuantity.setColumns(10);
		txtDistributorQuantity.setBounds(168, 242, 210, 20);
		
		contentPane.add(txtDistributorQuantity);
		txaDescription.setBounds(168, 114, 210, 78);
		
		contentPane.add(txaDescription);	
	}//end constructor
}//end class