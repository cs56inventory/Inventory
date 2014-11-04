import java.sql.SQLException;

import junit.framework.TestCase;


public class testDal extends TestCase{
	public void testDal1() {
		new DAL("SELECT * FROM user");
	}
}
