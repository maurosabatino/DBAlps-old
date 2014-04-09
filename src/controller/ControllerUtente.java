package controller;

import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import bean.Processo;
import bean.Utente;

public class ControllerUtente {

	public static Utente creazioneUtente(HttpServletRequest request) throws ParseException{
		Utente utente=new Utente();
		System.out.println("data"+data());
		utente.setDataCreazione(Timestamp.valueOf(data()));
		utente.setNome(request.getParameter("nome"));
		utente.setCognome(request.getParameter("cognome"));
		utente.setEmail(request.getParameter("email"));
		utente.setUsername(request.getParameter("username"));
		utente.setPassword(request.getParameter("password"));
		utente.setRuolo(request.getParameter("ruolo"));
		return utente;
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
	
	public static Utente nuovoUtente(HttpServletRequest request) throws ParseException, SQLException{
		Utente utente=creazioneUtente(request);
		utente=ControllerDatabase.salvaUtente(utente);
		return utente;
	}
}
