package controller;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import bean.*;

public class ControllerProcesso {
	public static Processo creaProcesso(HttpServletRequest request) throws ParseException{//qui creo le parti solo del processo
		Processo p = new Processo();
		p.setNome(request.getParameter("nome"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.setLenient(false);
	    Date parsedDate = dateFormat.parse("2014-11-21 08:03:55");
	    //Timestamp timestamp = new Timestamp(parsedDate.getTime());
	    
	    p.setData(parsedDate);
		String descrizione =request.getParameter("descrizione");
		p.setDescrizione(descrizione);
		p.setNote(request.getParameter("note"));
		//p.setAltezza(Double.parseDouble(request.getParameter("altezza")));
		//p.setLarghezza(Double.parseDouble(request.getParameter("larghezza")));
		//p.setSuperficie(Double.parseDouble(request.getParameter("superficie")));
		return p;
	}
	public static Processo nuovoProcesso(HttpServletRequest request) throws ParseException, SQLException{ //qui ci metto tutte le informazioni e la pubblicazione sul db + ubicazione, sito, allegati, effetti
		Processo p = creaProcesso(request);
		Ubicazione u = ControllerUbicazione.creaUbicazione(request);
		System.out.println("esposizione prima processo"+u.getEsposizione());
		p.setUbicazione(u);
		System.out.println("esposizione processo"+p.getUbicazione().getEsposizione());
		ControllerDatabase.salvaProcesso(p);
		return p;
		
	}
	
	
}
