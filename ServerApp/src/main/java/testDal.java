import java.sql.SQLException;

import junit.framework.TestCase;


public class testDal extends TestCase{
	public void testDal1(){
		try {
			new DAL("SELECT * FROM user");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
