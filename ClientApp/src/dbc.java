//dbc is the connection object that loads the JTDS driver, creates and opens a connection, creates a SQL statement and resultSet, executes the query
//1: create the dbc
//2: setQuery(query string)
//3: getRs()
//4: other operations as necessary, e.g., getRsmd() or getNumCols()
//5: closeConn()

import java.sql.*;

public class dbc {
	// the database connection driver
	String driver = "net.sourceforge.jtds.jdbc.Driver";

	// the SQL query statement
	String query = "";

	Connection connection;

	// database connection string
//	String conn = "jdbc:jtds:sqlserver://MTTVNS-HP:14433/imdb;instance=SQLEXPRESS12;TDS=7.0";
	String conn = "jdbc:jtds:sqlserver://ASUS:1433/imdb;instance=SQLEXPRESS;";
	ResultSet resultSet;
	ResultSetMetaData rsmd;

	// constructor
	dbc() throws SQLException, ClassNotFoundException {
		try{
			// load driver
			Class.forName(driver);
			// connect
			connection = DriverManager.getConnection(conn);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

	}// end dbc constructor

	// set the query string
	public void setQuery(String q) {
		this.query = q;
	}

	// get the ResultSet
	public ResultSet getRs() throws SQLException {
		// create sql statement object
		Statement statement = connection.createStatement();

		// create resultSet and execute query
		resultSet = statement.executeQuery(query);

		return this.resultSet;
	}

	// get the ResultSetMetaData
	public ResultSetMetaData getRsmd() throws SQLException {
		rsmd = resultSet.getMetaData();
		return this.rsmd;
	}

	// get the number of columns in the result set
	public int getNumCols() throws SQLException {
		int numCols = rsmd.getColumnCount();
		return numCols;
	}

	// close the connection
	public void closeConn() throws SQLException {
		connection.close();
	}

}// end class
