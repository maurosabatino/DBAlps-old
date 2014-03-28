package controller;

import bean.StazioneMetereologica;
import bean.Ubicazione;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;


public class ControllerStazioneMetereologica {
	
	public static StazioneMetereologica creaStazioneMetereologica(HttpServletRequest request){
		StazioneMetereologica s=new StazioneMetereologica();
		s.setNome(request.getParameter("nome"));
		s.setAggregazioneGiornaliera(request.getParameter("aggregazioneGiornaliera"));
		s.setNote(request.getParameter("note"));	
		s.setPeriodoFunzionamento(request.getParameter("periodoFunzionamento"));
		s.setOraria(Boolean.parseBoolean(request.getParameter("oraria")));
		return s;
	}
	public static StazioneMetereologica nuovaStazioneMetereologica(HttpServletRequest request) throws SQLException{
		StazioneMetereologica s= creaStazioneMetereologica(request);
		Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
		s.setUbicazione(u);
		ControllerDatabase.salvaStazione(s);
		return s;
	}
	
	
}
