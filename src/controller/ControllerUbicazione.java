package controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import bean.*;

public class ControllerUbicazione {
	public static Ubicazione creaUbicazione(HttpServletRequest request) throws SQLException{//questo metodo sar� poi cambiato
		Ubicazione u = new Ubicazione();
		Coordinate coord = new Coordinate();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		System.out.println("dato: "+(String)request.getParameter("longitudine"));
		System.out.println("dato parsificato: "+Double.parseDouble((String)request.getParameter("longitudine")));
		Double x = Double.parseDouble(request.getParameter("longitudine"));
		coord.setX(x);
		System.out.println("dato a: "+(String)request.getParameter("latitudine"));
		System.out.println("dato parsificato a: "+Double.parseDouble((String)request.getParameter("latitudine")));
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
		u = ControllerDatabase.salvaUbicazione(u);
		System.out.println("da dentro controller ubicazione"+u.getIdUbicazione());
		// e salva id di comune e bacino e poi anche salva l'id di ubicazione
		return u;
	}
}
