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
		 
		if(!(request.getParameter("data").equals(""))){
			String data = request.getParameter("data");
			if(!(request.getParameter("data").equals(""))){
				String ora = request.getParameter("ora");
				String dataCompleta = ""+data+" "+ora+":00";
				Date parsedDate = dateFormat.parse(dataCompleta);
				p.setData(parsedDate);
			}
		}
		
		
		
	    String descrizione =request.getParameter("descrizione");
		p.setDescrizione(descrizione);
		p.setNote(request.getParameter("note"));
		if(!(request.getParameter("altezza").equals("")))
		p.setAltezza(Double.parseDouble(request.getParameter("altezza")));
		if(!(request.getParameter("larghezza").equals("")))
		p.setLarghezza(Double.parseDouble(request.getParameter("larghezza")));
		if(!(request.getParameter("superficie").equals("")))
		p.setSuperficie(Double.parseDouble(request.getParameter("superficie")));
		return p;
	}
	public static Processo nuovoProcesso(HttpServletRequest request) throws ParseException, SQLException{ //qui ci metto tutte le informazioni e la pubblicazione sul db + ubicazione, sito, allegati, effetti
		Processo p = creaProcesso(request);
		Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
		p.setUbicazione(u);
		ControllerDatabase.salvaProcesso(p);
		return p;
	}
	
	
}
