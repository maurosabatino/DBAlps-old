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
		if(!(request.getParameter("longitudine").equals(""))){
		Double x = Double.parseDouble(request.getParameter("longitudine"));
		coord.setX(x);
		}
		if(!(request.getParameter("latitudine").equals(""))){
		Double y = Double.parseDouble(request.getParameter("latitudine"));
		coord.setY(y);
		}
		u.setCoordinate(coord);
		u.setEsposizione((String)request.getParameter("esposizione"));
		if(!(request.getParameter("quota").equals("")))
		u.setQuota(Double.parseDouble((String)request.getParameter("quota")));
		if(!(request.getParameter("idcomune").equals(""))){
			
			locAmm=ControllerDatabase.prendiLocAmministrativa(Integer.parseInt(request.getParameter("idcomune")));
		}
		u.setLocAmm(locAmm);
		if(!(request.getParameter("idSottobacino").equals(""))){
			
			locIdro=ControllerDatabase.prendiLocIdrologica(Integer.parseInt(request.getParameter("idSottobacino")));
		}
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
