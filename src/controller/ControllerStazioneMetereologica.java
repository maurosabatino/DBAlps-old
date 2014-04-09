package controller;

import bean.Ente;
import bean.Sensori;
import bean.SitoStazioneMetereologica;
import bean.StazioneMetereologica;
import bean.Ubicazione;
import bean.Utente;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;


public class ControllerStazioneMetereologica {
	
	public static StazioneMetereologica creaStazioneMetereologica(HttpServletRequest request) throws ParseException{
		StazioneMetereologica s=new StazioneMetereologica();
		s.setNome(request.getParameter("nome"));
		String data="";
		String ora="00:00:00";
		s.setAggregazioneGiornaliera(request.getParameter("aggregazioneGiornaliera"));
		s.setNote(request.getParameter("note"));
	/*	Timestamp inizio=Timestamp.valueOf(request.getParameter("datainizio"));
		Calendar cal=new GregorianCalendar();
		cal.setTime(inizio);
		if(cal.get(Calendar.DAY_OF_MONTH)<10)
			data=""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"0-"+cal.get(Calendar.DAY_OF_MONTH);
		else data=
		s.setDataInizio(Timestamp.valueOf(data));*/
		if(!(request.getParameter("datainizio").equals(""))){
			String dataInizio = request.getParameter("datainizio");
			s.setDataInizio(Timestamp.valueOf(""+dataInizio+" "+ora+""));
		}
		
		if(request.getParameter("datafine").equals("")) s.setDataFine(Timestamp.valueOf("0001-01-01 00:00:00"));
		else{
			String fine=request.getParameter("datafine");
			s.setDataFine(Timestamp.valueOf(""+fine+" "+ora+""));
		}
		if(!(request.getParameter("oraria").equals("")))  s.setOraria(Boolean.parseBoolean(request.getParameter("oraria")));
	   
	   // sensori da fare
		return s;
	}
	
	public static StazioneMetereologica nuovaStazioneMetereologica(HttpServletRequest request,String loc,Ubicazione u) throws SQLException, ParseException{
		StazioneMetereologica s= creaStazioneMetereologica(request);
		s.setUbicazione(u);
		if(!(request.getParameter("idsitostazione").equals(""))){
		SitoStazioneMetereologica sito=creaSitoStazione(request,loc);
		s.setSito(sito);}
		if(!(request.getParameter("idEnte").equals(""))){
			Ente ente=creaEnte(request);
			s.setEnte(ente);
		}
		if(!(request.getParameterValues("tipo_"+loc+"")==null)){
			System.out.println("sensori!");
			s.setSensori(creaSensori(request,loc));
		}

		
		
		s.setIdUtente(1);
		
		
		
		return s;
	}
	
	public static SitoStazioneMetereologica creaSitoStazione(HttpServletRequest request,String loc){
			SitoStazioneMetereologica s=new SitoStazioneMetereologica();
		   s.setIdSitoStazioneMetereologica(Integer.parseInt(request.getParameter("idsitostazione")));
		   if(loc.equals("IT")) s.setCaratteristiche_IT(request.getParameter("caratteristiche_it"));
		   else s.setCaratteristiche_ENG(request.getParameter("caratteristiche_it"));
		   return s;
	}
	
	public static Ente creaEnte(HttpServletRequest request){
		Ente ente=new Ente();
		ente.setEnte(request.getParameter("ente"));
		ente.setIdEnte(Integer.parseInt(request.getParameter("idEnte")));
		return ente;
	}
	
	public static ArrayList<Sensori> creaSensori(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		int n=0;
		String[] tipo_it;
		String[] tipo_eng;
		String[] sensoriScelti= request.getParameterValues("tipo_IT");
		int idsensore=0;
				
		if(loc.equals("IT")){			
		if(!(sensoriScelti==null)){
			for(int i=0;i<sensoriScelti.length;i++){
				idsensore=ControllerDatabase.idSensore(sensoriScelti[i],loc);	
				Sensori s=new Sensori();
				s.setIdsensori(idsensore);
				s.setSensori_IT(sensoriScelti[i]);
				sensori.add(s);
			}
			
		}
		}	else {
			if(!(sensoriScelti==null)){
				for(int i=0;i<n-1;i++){
					idsensore=ControllerDatabase.idSensore(sensoriScelti[i],loc);	
					Sensori s=new Sensori();
					s.setIdsensori(idsensore);
					s.setSensori_ENG(sensoriScelti[i]);
					sensori.add(s);
				}
			}
		}
		for(Sensori s:sensori){
			System.out.println("crea sensore "+s.getSensori_IT());
		}
		return sensori;
	}
		
	
	
	
}
