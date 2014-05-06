package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import bean.partecipante.*;


public class ControllerUtente {

	public static Partecipante creazioneUtente(HttpServletRequest request) throws ParseException{
		Partecipante partecipante = new PartecipanteConcreto();
		switch(request.getParameter("ruolo")){
		case "amministratore" : partecipante = new Amministratore(partecipante); break;
		case "avanzato" : partecipante = new UtenteAvanzato(partecipante); break;
		case "base" : partecipante = new UtenteBase(partecipante); break;
		}
		partecipante.setDataCreazione(Timestamp.valueOf(data()));
		partecipante.setNome(request.getParameter("nome"));
		partecipante.setCognome(request.getParameter("cognome"));
		partecipante.setEmail(request.getParameter("email"));
		partecipante.setUsername(request.getParameter("username"));
		partecipante.setPassword(request.getParameter("password"));
		partecipante.setRuolo(request.getParameter("ruolo"));
		return partecipante;
	}
	public static String data(){
		Calendar cal = new GregorianCalendar();
	    int giorno = cal.get(Calendar.DAY_OF_MONTH);
	    int mese = cal.get(Calendar.MONTH)+1;
	    int anno = cal.get(Calendar.YEAR);
	    int ora = cal.get(Calendar.HOUR_OF_DAY);
	    int min = cal.get(Calendar.MINUTE);
	    int sec = cal.get(Calendar.SECOND);
	    return (anno+"-"+mese+"-"+giorno+" "+ora+":"+min+":"+sec);
	}
	
	public static Partecipante nuovoUtente(HttpServletRequest request) throws ParseException, SQLException{
		Partecipante partecipante=creazioneUtente(request);
		partecipante=ControllerDatabase.salvaUtente(partecipante);
		return partecipante;
	}
}
