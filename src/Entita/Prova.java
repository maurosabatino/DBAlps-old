package Entita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Prova {
	
	public static void insertEnte(String ente) throws SQLException{
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		System.out.println(conn);
		Statement st = conn.createStatement();
		st.executeUpdate("insert into ente(nome) values "+ente+"");
		conn.close();
		st.close();
	}
}
