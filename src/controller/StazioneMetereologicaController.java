package controller;

import bean.StazioneMetereologica;
import bean.Ubi;
import bean.Ubicazione;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class StazioneMetereologicaController {
	public static StazioneMetereologica inserisciStazioneMetereologica(HttpServletRequest request) throws SQLException{
		StazioneMetereologica s=new StazioneMetereologica();
		Ubicazione u=new Ubicazione();
		u=ControllerUbicazione.creaUbicazione(request);
		s.setNome(request.getParameter("nome"));
		//s.setIdEnte(Integer.parseInt(request.getParameter("idEnte")));
		s.setUbicazione(u);
		//System.out.println(s.ubicazioneS.getQuota());
		//s.setSito(sito);
		s.setAggregazioneGiornaliera(request.getParameter("aggregazioneGiornaliera"));
		s.setNote(request.getParameter("note"));	
		s.setPeriodoFunzionamento(request.getParameter("periodoFunzionamento"));
		s.setOraria(Boolean.parseBoolean(request.getParameter("oraria")));
		
		DBController.salvaStazione(s);
		return s;
	}
	
	
}
