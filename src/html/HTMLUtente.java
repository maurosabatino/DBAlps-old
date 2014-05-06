package html;

import java.sql.SQLException;

import bean.partecipante.Partecipante;


public class HTMLUtente {
	public static String creaUtente() throws SQLException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
		sb.append("<p>Nome:<input type=\"text\" name=\"nome\"></p>");
		sb.append("<p>Cognome:<input type=\"text\" name=\"cognome\"></p>	");
		sb.append("<p>Username:<input type=\"text\" name=\"username\"></p>");
		sb.append("<p>password:<input type=\"password\" name=\"password\"></p>");
		sb.append("<p>email:<input type=\"text\" name=\"email\"  ></p>");
		sb.append("<p>ruolo:<select name=\"ruolo\">");
		sb.append("<option value=\"amministratore\">Amministartore</option>");
		sb.append("<option value=\"avanzato\">Utente Avanzato</option>");
		sb.append("<option value=\"base\">Utente Base</option>");
		sb.append("</select>");
		sb.append("</p>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciUtente\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
		sb.append("</form>");
		
		return sb.toString();
	}
	
	public static String login(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"login-form\" title=\"Login\">");
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
		sb.append("<div class=\"jumbotron\">");
		sb.append("<p>Username:<input type=\"text\" name=\"username\"</p>"); 
		sb.append("<p>password:<input type=\"text\" name=\"password\"></p>"); 
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"login\">"); 
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append("</div>");
		sb.append("</form>");
		sb.append("</div>");
		return sb.toString();
	}
	public static String visualizzaUtente(Partecipante p){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<p>"+p.getUsername()+"</p>");
		sb.append("<p>"+p.getPassword()+"</p>");
		sb.append("");
		sb.append("");
		
		return sb.toString();
	}

}
