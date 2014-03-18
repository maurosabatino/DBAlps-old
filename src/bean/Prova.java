package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Prova {
	
	public static void insertEnte(String ente) throws SQLException, ClassNotFoundException{
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Class.forName("org.postgresql.Driver"); 
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		st.executeUpdate("insert into ente(nome) values "+ente+"");
		conn.close();
		st.close();
	}
}
