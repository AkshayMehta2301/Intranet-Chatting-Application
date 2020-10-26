/* 1.Import---> java.sql
 * 2.Load and Register Drivers---> com.mysql.jdbc.drivers
 * 3.Create Conection---> Connection
 * 4.Create a Statement---> Statement
 * 5.Execute query---> 
 * 6.Process Results--->
 * 7.Close 
 */
package windowbuilder;
import java.sql.*;

public class database 
{
	public static void main(String []args) throws Exception
	{
		//String query = "select username from userlogin where empno=2";
		
		//Class.forName("Driver");
		String url = "jdbc:mysql://localhost:3306/logindata";
		String uname = "root";
		String pass = "incorrect12";
		String query = "select * from userlogin";
		
		//Class.forName("Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);
		while((rs.next()))
		{
				String dbusername = rs.getString("username");
				String dbpassword = rs.getString("password");
				System.out.println(dbusername+"\t"+ dbpassword);
		}		
		st.close();
		con.close();
	}
}
