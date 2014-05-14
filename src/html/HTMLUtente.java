package html;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import controller.ControllerDatabase;
import bean.Processo;
import bean.partecipante.Partecipante;


public class HTMLUtente {
	public static String creaUtente() throws SQLException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<form class=\"form-horizontal\" action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\"  >");
		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Crea un nuovo Utente</h4>");
		sb.append("<br>");
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"nome\" class=\"col-sm-2 control-label\">Nome</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"cognome\" class=\"col-sm-2 control-label\">Cognome</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"cognome\" id=\"cognome\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"username\" class=\"col-sm-2 control-label\">Username</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"text\" name=\"username\" id=\"username\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"password\" class=\"col-sm-2 control-label\">Password</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"password\" name=\"password\" id=\"password\" class=\"form-control\">");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"email\" class=\"col-sm-2 control-label\">Email</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" >");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class=\"form-group\">");
		sb.append("<label for=\"ruolo\" class=\"col-sm-2 control-label\">Ruolo</label>");
		sb.append("<div class=\"col-sm-10\">");
		sb.append("<select class=\"form-control\" name=\"ruolo\" id=\"ruolo\">");
		sb.append("<option value=\"amministratore\">Amministartore</option>");
		sb.append("<option value=\"avanzato\">Utente Avanzato</option>");
		sb.append("<option value=\"base\">Utente Base</option>");
		sb.append("</select>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciUtente\">");
		sb.append("<div class=\"form-group\">");
		sb.append("<div class=\"col-sm-10\">");
		sb.append(" <button type=\"submit\" class=\"btn btn-default\">Crea Utente</button>");
		sb.append("</div>");
		sb.append("</div>");
		
		sb.append("</div>");
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
		sb.append("<p>da aggiungere tutte le informazioni a piacere</p>");
		sb.append("");
		
		return sb.toString();
	}
	
	public static String visualizzaTuttiUtente() throws SQLException{
		StringBuilder sb = new StringBuilder();
		ArrayList<Partecipante> part = ControllerDatabase.PrendiTuttiUtenti();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>Cognome</th> <th>email</th> <th>Username</th> <th> Password</th> <th> ruolo</th> <th>data creazione</th> <th>data ultimo accesso</th></tr>");
		for(Partecipante p: part){
			sb.append("<tr> <td>"+p.getNome()+" </td> <td> "+p.getCognome()+"</td> <td> "+p.getEmail()+"</td>");
			sb.append("<td>"+p.getUsername()+"</td> ");
			sb.append("<td>"+p.getPassword()+"</td>");
			sb.append("<td>"+p.getRuolo()+"</td>");
			sb.append("<td>"+dateFormat.format(p.getDataCreazione())+"</td>");
			sb.append("<td>"+dateFormat.format(p.getDataUltimoaccesso())+"</td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		
		return sb.toString();
	}

}
