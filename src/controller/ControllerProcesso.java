package controller;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;






import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import bean.ClasseVolume;
import bean.Danni;
import bean.EffettiMorfologici;
import bean.Litologia;
import bean.Processo;
import bean.ProprietaTermiche;
import bean.SitoProcesso;
import bean.StatoFratturazione;
import bean.TipologiaProcesso;
import bean.Ubicazione;

public class ControllerProcesso {
	public static Processo creaProcesso(HttpServletRequest request) throws ParseException{//qui creo le parti solo del processo
		Processo p = new Processo();
		p.setNome(request.getParameter("nome"));
		String data="00-00-00";
		String ora="00:00";
		if(!(request.getParameter("data").equals(""))){
			 data = request.getParameter("data");
			if(!(request.getParameter("data").equals(""))){
				if(!(request.getParameter("ora").equals(""))){
					ora = request.getParameter("ora");
				}
				String dataCompleta = ""+data+" "+ora+":00";
				
				p.setData((Timestamp.valueOf(dataCompleta)));
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
		if(!(request.getParameter("volumespecifico").equals("")))
			p.setVolume_specifico(Double.parseDouble(request.getParameter("volumespecifico")));
		
		return p;
	}
	public static Processo nuovoProcesso(HttpServletRequest request,String loc) throws ParseException, SQLException{ //qui ci metto tutte le informazioni e la pubblicazione sul db + ubicazione, sito, allegati, effetti
		Processo p = creaProcesso(request);
		Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
		SitoProcesso sp = creaSito(request);
		ClasseVolume cv = creaClasseVolume(request);
		ArrayList<EffettiMorfologici> em = creaEffettiMorfologici(request,loc);
		ArrayList<Danni> d = creaDanni(request,loc);
		ArrayList<TipologiaProcesso> tp = creaTipologiaProcesso(request,loc);
		Litologia l = creaLitologia(request,loc);
		StatoFratturazione sf = creaStatoFratturazione(request,loc);
		ProprietaTermiche pt = creaProprietaTermiche(request,loc);
		p.setProprietaTermiche(pt);
		p.setStatoFratturazione(sf);
		p.setLitologia(l);
		p.setTipologiaProcesso(tp);
		p.setDanni(d);
		p.setEffetti(em);
		p.setClasseVolume(cv);
		p.setSitoProcesso(sp);
		p.setUbicazione(u);
		ControllerDatabase.salvaProcesso(p);
		ControllerDatabase.salvaEffetti(p.getIdProcesso(), em, d);
		ControllerDatabase.salvaTipologiaProcesso(p.getIdProcesso(), tp);
		return p;
	}
	
	
	/*
	 * caratteristiche del processo
	 */
	public static SitoProcesso creaSito(HttpServletRequest request) throws SQLException{
		SitoProcesso sp = new SitoProcesso();
		if(!(request.getParameter("idsito").equals("")))
			sp.setIdSito(Integer.parseInt(request.getParameter("idsito")));
		sp.setCaratteristicaSito_IT(request.getParameter("caratteristicaSito_IT"));
		sp.setCaratteristicaSito_ENG(request.getParameter("caratteristicaSito_ENG"));
			
		return sp; 
	}
	public static Litologia creaLitologia(HttpServletRequest request,String loc) throws SQLException{
		Litologia l = new Litologia();
		if(!(request.getParameter("idLitologia").equals("")))
		l.setIdLitologia(Integer.parseInt(request.getParameter("idLitologia")));
		if(loc.equals("IT"))
		l.setNomeLitologia_IT(request.getParameter("nomeLitologia_IT"));
		if(loc.equals("ENG"))
		l.setNomeLitologia_ENG(request.getParameter("nomeLitologia_ENG"));
		return l;
	}
	public static ClasseVolume creaClasseVolume(HttpServletRequest request){
		ClasseVolume cv = new ClasseVolume();
		if(!(request.getParameter("idclasseVolume").equals("")))
			cv.setIdClasseVolume(Integer.parseInt(request.getParameter("idclasseVolume")));
		cv.setIntervallo(request.getParameter("intervallo"));
		return cv;
	}
	
	public static ArrayList<EffettiMorfologici> creaEffettiMorfologici(HttpServletRequest request,String loc) throws SQLException{
	ArrayList<EffettiMorfologici> em = new ArrayList<EffettiMorfologici>();
	String[] emtipo_it;
	String[] emtipo_eng;
	int n = 0;
	if(loc.equals("IT")){
		if(!(request.getParameter("emtipo_IT").equals(""))){
			n = request.getParameter("emtipo_IT").split(", ").length;
			emtipo_it = request.getParameter("emtipo_IT").split(", ");
			for(int i = 0;i<n-1;i++){
				EffettiMorfologici e = new EffettiMorfologici();
				e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_it[i], "IT"));
				e.setTipo_IT(emtipo_it[i]);
				em.add(e);
			}
		}
	}
	if(loc.equals("ENG")){
		if(!(request.getParameter("emtipo_ENG").equals("") )){
			n= request.getParameter("emtipo_ENG").split(", ").length;
			emtipo_eng = request.getParameter("emtipo_ENG").split(", ");
			for(int i = 0;i<n-1;i++){
				EffettiMorfologici e = new EffettiMorfologici();
				e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_eng[i], "ENG"));
				e.setTipo_ENG(emtipo_eng[i]);
				em.add(e);
			}
		}
	}
	
		return em;
	}
	public static ArrayList<Danni> creaDanni(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<Danni> d = new ArrayList<Danni>();
	
		String[] dtipo_it;
		String[] dtipo_eng;
		int n = 0;
		if(loc.equals("IT")){
			if(!(request.getParameter("dtipo_IT").equals(""))){
				n = request.getParameter("dtipo_IT").split(", ").length;
				dtipo_it = request.getParameter("dtipo_IT").split(", ");
				for(int i = 0;i<n-1;i++){
					Danni da = new Danni();
					da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_it[i], "IT"));
					da.setTipo_IT(dtipo_it[i]);
					d.add(da);
				}
			}
		}
		if(loc.equals("ENG")){
			if(!(request.getParameter("dtipo_ENG").equals(""))){
				n = request.getParameter("dtipo_ENG").split(", ").length;
				dtipo_eng = request.getParameter("dtipo_ENG").split(", ");
				for(int i = 0;i<n-1;i++){
					Danni da = new Danni();
					da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_eng[i], "ENG"));
					da.setTipo_ENG(dtipo_eng[i]);
					d.add(da);
				}
			}
		}
		return d;
	}
	public static ArrayList<TipologiaProcesso> creaTipologiaProcesso(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<TipologiaProcesso> t = new ArrayList<TipologiaProcesso>();
		String[] tpnome_it;
		String[] tpnome_eng;
		int n = 0;
		if(loc.equals("IT")){
			if(!(request.getParameter("tpnome_IT").equals(""))){
				n = request.getParameter("tpnome_IT").split(", ").length;
				tpnome_it = request.getParameter("tpnome_IT").split(", ");
				for(int i = 0;i<n-1;i++){
					TipologiaProcesso tp = new TipologiaProcesso(); 
					tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_it[i],"IT"));
					tp.setNome_IT(tpnome_it[i]);
					t.add(tp);
				}
			}
		}
		if(loc.equals("ENG")){
			if(!(request.getParameter("tpnome_ENG").equals(""))){
				n = request.getParameter("tpnome_ENG").split(", ").length;
				tpnome_eng = request.getParameter("tpnome_ENG").split(", ");
				for(int i = 0;i<n-1;i++){
					TipologiaProcesso tp = new TipologiaProcesso(); 
					tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_eng[i],"ENG"));
					tp.setNome_ENG(tpnome_eng[i]);
					t.add(tp);
				}
			}
		}
			return t;
	}
	public static ProprietaTermiche creaProprietaTermiche(HttpServletRequest request,String loc){
		ProprietaTermiche pt = new ProprietaTermiche();
		if(!(request.getParameter("idProprietaTermiche").equals("")))
		pt.setIdProprietaTermiche(Integer.parseInt(request.getParameter("idProprietaTermiche")));
		if(loc.equals("IT"))
		pt.setProprietaTermiche_IT(request.getParameter("proprietaTermiche_IT"));
		if(loc.equals("ENG"))
		pt.setProprietaTermiche_ENG(request.getParameter("proprietaTermiche_ENG"));
		return pt;
	}
	public static StatoFratturazione creaStatoFratturazione(HttpServletRequest request,String loc){
		StatoFratturazione pt = new StatoFratturazione();
		if(!(request.getParameter("idStatoFratturazione").equals("")))
		pt.setIdStatoFratturazione(Integer.parseInt(request.getParameter("idStatoFratturazione")));
		if(loc.equals("IT"))
		pt.setStatoFratturazione_IT(request.getParameter("statoFratturazione_IT"));
		if(loc.equals("ENG"))
		pt.setStatoFratturazione_ENG(request.getParameter("statoFratturazione_ENG"));
		return pt;
	}
}

