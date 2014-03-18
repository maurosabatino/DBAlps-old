package html;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.*;
import controller.ControllerDatabase;

public class HTMLStazioneMetereologica {
	public static String mostraTutteStazioniMetereologiche() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> </tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}
	public static String mostraStazioneMetereologica(int idStazioneMetereologica) throws SQLException{
		StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(idStazioneMetereologica);
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}
}
