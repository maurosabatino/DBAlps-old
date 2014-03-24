package dataBase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Postgres {
	public static void main(String [] args){
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		String stazioni="stazione_metereologica";
		String temp_max="temperatura_max";
		try{
			Connection conn = DriverManager.getConnection(url,user,pwd);
			stazioni(conn,stazioni);
			for(int i=1;i<5;i++) {
				temperatura_max(conn,temp_max,i);
			}
			
			conn.close();
		} catch (SQLException e) {System.out.println(e.getMessage());}
	}
	
	
	
	public static void stazioni(Connection conn, String name)throws SQLException{
		Statement st = conn.createStatement();
		for(int i=1;i<5;i++) {
			st.executeUpdate("INSERT INTO "+name+" (NOME,IDENTE,IDUBICAZIONE,IDSITOSTAZIONE,AGGREGAZIONEGIORNALIERA,PERIODOFUNZIONAMENTO,ORARIA) VALUES ("+i+","+1+", "+1+", "+1+",'agg' ,'per',false)");
		}
		st.close();
	}
	
	public static void temperatura_max(Connection conn, String name,int staz)throws SQLException{
		Statement st = conn.createStatement();
		Date date = new Date();
		
		for(Integer i=1;i<50;i++) {
			Timestamp tm = new Timestamp(date.getTime());
			//Timestamp t=new Timestamp()
			SimpleDateFormat sf = new SimpleDateFormat("YYYY-d-MM hh:mm:ss");
			sf.format(tm);
			String data ="1989-02-27";
			//System.out.println(Timestamp.valueOf(data));
			System.out.println("INSERT INTO "+name+" (IDSTAZIONEMETEREOLOGICA,TEMPERATURAMAX,DATA) VALUES ("+staz+", "+i+","+data+" )");
			st.executeUpdate("INSERT INTO "+name+" (IDSTAZIONEMETEREOLOGICA,TEMPERATURAMAX,DATA) VALUES ("+staz+", "+i+",'"+data+" ')");
		}
		st.close();
	}
	
	
	
	public static void mostraTabella(Connection conn, String name) throws SQLException{
		String out="";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM "+name);
		
		while (rs.next())
			out += "("+rs.getInt("ID")+") "+rs.getString("TITOLO") + ", di "+ rs.getString("AUTORE") +", "+ rs.getDouble("PREZZO")+"\n"; 
		System.out.println(out);
		rs.close();
		st.close();
	
	}
}
