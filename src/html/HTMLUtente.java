package html;

import java.sql.SQLException;


public class HTMLUtente {
	public static String creaUtente() throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">" +
				"		<p>Nome:<input type=\"text\" name=\"nome\" value=\"mauro\"></p>" +
				"	    <p>Cognome:<input type=\"text\" name=\"cognome\" value=\"rossi\"></p>	" +
				"   	<p>Username:<input type=\"text\" name=\"username\" value=\"ros\"></p>" +
				"		<p>password:<input type=\"text\" name=\"password\" value=\"mauro\"></p>" +
				"		<p>email:<input type=\"text\" name=\"email\" value=\"rossi@mail\" ></p>" +
				"		<p>ruolo:<input type=\"text\" name=\"ruolo\" value=\"admin\"></p>" +
				"		<input type=\"hidden\" name=\"operazione\" value=\"inserisciUtente\">" +
				"		<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}

}
