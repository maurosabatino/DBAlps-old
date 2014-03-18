package controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import bean.*;

public class ControllerUbicazione {
	public static Ubicazione creaUbicazione(HttpServletRequest request) throws SQLException{
		Ubicazione u = new Ubicazione();
		Coordinate coord = new Coordinate();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		Double x = Double.parseDouble(request.getParameter("longitudine"));
		coord.setX(x);
		Double y = Double.parseDouble(request.getParameter("latitudine"));
		coord.setY(y);
		u.setCoordinate(coord);
		u.setEsposizione((String)request.getParameter("esposizione"));
		u.setQuota(Double.parseDouble((String)request.getParameter("quota")));
		locAmm.setComune((String)request.getParameter("comune"));
		locAmm.setProvincia((String)request.getParameter("provincia"));
		locAmm.setRegione((String)request.getParameter("regione"));
		locAmm.setNazione((String)request.getParameter("nazione"));
		u.setLocAmm(locAmm);
		locIdro.setBacino((String)request.getParameter("bacino"));
		locIdro.setSottobacino((String)request.getParameter("sottobacino"));
		u.setLocIdro(locIdro);
		return u;
	}
	
	public static Ubicazione nuovaUbicazione(HttpServletRequest request) throws SQLException{
		Ubicazione u = creaUbicazione(request);
		ControllerDatabase.salvaUbicazione(u);
		return u;
	}
}
