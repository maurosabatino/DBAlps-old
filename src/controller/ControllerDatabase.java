package controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.Part;

import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc2.EscapedFunctions;

import bean.*;
import bean.partecipante.Amministratore;
import bean.partecipante.Partecipante;
import bean.partecipante.PartecipanteConcreto;
import bean.partecipante.Role;
import bean.partecipante.UtenteAvanzato;
import bean.partecipante.UtenteBase;

public class ControllerDatabase {
	static String url = "jdbc:postgresql://localhost:5432/DBAlps";
	static String user = "admin";
	static String pwd = "dbalps";
	
	/**
	 * Processo
	 */
	
	public static Processo salvaProcesso(Processo p,Partecipante part) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		
		String sql = "INSERT INTO processo(idUbicazione,idSito,nome,data,descrizione,note,altezza,larghezza,superficie,volumespecifico,"
				+ "idutentecreatore,idutentemodifica,convalidato,idclassevolume,idlitologia,idproprietatermiche,idstatofratturazione)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,  p.getUbicazione().getIdUbicazione());
		ps.setInt(2, p.getSitoProcesso().getIdSito());
		ps.setString(3, p.getNome());
		ps.setTimestamp(4, p.getData());
		ps.setString(5, p.getDescrizione());
		ps.setString(6, p.getNote());
		ps.setDouble(7, p.getAltezza());
		ps.setDouble(8, p.getLarghezza());
		ps.setDouble(9, p.getSuperficie());
		ps.setDouble(10, p.getVolumeSpecifico());
		ps.setInt(11, part.getIdUtente());
		ps.setInt(12, part.getIdUtente());
		if(part.hasRole(Role.AMMINISTRATORE)|| part.hasRole(Role.AVANZATO)|| part.hasRole(Role.BASE))
			ps.setBoolean(13, true);
		else
			ps.setBoolean(13, false);
		ps.setInt(14, p.getClasseVolume().getIdClasseVolume());
		ps.setInt(15, p.getLitologia().getidLitologia());
		ps.setInt(16, p.getProprietaTermiche().getIdProprieta_termiche());
		ps.setInt(17, p.getStatoFratturazione().getIdStato_fratturazione());
		ps.executeUpdate();
		ResultSet rs = st.executeQuery("SELECT * FROM processo WHERE idUbicazione="+p.getUbicazione().getIdUbicazione()+" AND idSito="+p.getSitoProcesso().getIdSito()+" AND nome='"+p.getNome().replaceAll("'","''")+"' AND data='"+p.getData()+"' ");
		while(rs.next()){
			p.setIdprocesso(rs.getInt("idProcesso"));
		}
		rs.close();
		st.close();
		conn.close();
		return p;
	}
	public static void salvaEffetti(int idProcesso,ArrayList<EffettiMorfologici> em,ArrayList<Danni> d) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		for(EffettiMorfologici eff:em){
			st.executeUpdate("insert into effetti_processo(idprocesso,ideffettimorfologici) values("+idProcesso+","+eff.getIdEffettiMorfoligici()+")");
		}
		for(Danni da:d){
			System.out.println("danni:"+da.getTipo_IT());
			st.execute("insert into danni_processo(idprocesso,iddanno) values("+idProcesso+","+da.getIdDanni()+")");
			
		}
		st.close();
		conn.close();
	}
	
	public static void salvaTipologiaProcesso(int idProcesso,ArrayList<TipologiaProcesso> tp) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		for(TipologiaProcesso pt : tp){
			System.out.println("tip"+pt.getIdTipologiaProcesso());
			st.executeUpdate("insert into caratteristiche_processo(idprocesso,idtipologiaProcesso) values("+idProcesso+","+pt.getIdTipologiaProcesso()+")");
		}
		st.close();
		conn.close();
	}
	
	
	public static Processo prendiProcesso(int idProcesso) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		Processo p = new Processo();
		Ubicazione u = new Ubicazione();
		Coordinate coord = new Coordinate();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		StringBuilder sb = new StringBuilder();
		sb.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng ");
		sb.append(" from processo proc, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,");
		sb.append(" litologia l,proprieta_termiche pt, stato_fratturazione sf, sito_processo sp,classi_volume cv");
		sb.append(" where proc.idubicazione = u.idubicazione and proc.idprocesso="+idProcesso+"");
		sb.append(" and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and");
		sb.append(" b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and ");
		sb.append(" proc.idlitologia = l.idlitologia and pt.idproprietatermiche=proc.idproprietatermiche and sp.idsitoprocesso=proc.idsito and proc.idclassevolume=cv.idclassevolume");
		
		ResultSet rs = st.executeQuery(sb.toString());
		while(rs.next()){
			p.setIdprocesso(rs.getInt("idProcesso"));
			p.setNome(rs.getString("nome"));
			p.setData(rs.getTimestamp("data"));
			p.setDescrizione(rs.getString("descrizione"));
			p.setNote(rs.getString("note"));
			p.setAltezza(rs.getDouble("altezza"));
			p.setLarghezza(rs.getDouble("larghezza"));
			p.setSuperficie(rs.getDouble("superficie"));
			p.setVolumeSpecifico(rs.getDouble("volumespecifico"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			u.setCoordinate(coord);
			u.setLocAmm(locAmm);
			u.setLocIdro(locIdro);
			u.setEsposizione(rs.getString("esposizione"));
			u.setQuota(rs.getDouble("quota"));
			p.setUbicazione(u);
			ClasseVolume cv = new ClasseVolume();
			cv.setIdClasseVolume(rs.getInt("idclassevolume"));
			cv.setIntervallo(rs.getString("intervallo"));
			p.setClasseVolume(cv);
			Litologia l = new Litologia();
			l.setIdLitologia(rs.getInt("idlitologia"));
			l.setNomeLitologia_IT(rs.getString("lito_it"));
			l.setNomeLitologia_ENG(rs.getString("lito_eng"));
			p.setLitologia(l);
			ProprietaTermiche pt = new ProprietaTermiche();
			pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
			pt.setProprietaTermiche_IT(rs.getString("pt_it"));
			pt.setProprietaTermiche_ENG(rs.getString("pt_eng"));
			p.setProprietaTermiche(pt);
			StatoFratturazione sf = new StatoFratturazione();
			sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
			sf.setStatoFratturazione_IT(rs.getString("sf_it"));
			sf.setStatoFratturazione_ENG(rs.getString("sf_eng"));
			p.setStatoFratturazione(sf);
			SitoProcesso sp = new SitoProcesso();
			sp.setIdSito(rs.getInt("idsito"));
			sp.setCaratteristicaSito_IT(rs.getString("caratteristica_it"));
			sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
			p.setDanni(prendiDanniProcesso(idProcesso));
			p.setEffetti(prendiEffettiProcesso(idProcesso));
			p.setTipologiaProcesso(prendiCaratteristicheProcesso(idProcesso));
			p.setUbicazione(u);
		}
		rs.close();
		st.close();
		conn.close();
		return p;
	}
	
	public static ArrayList<Processo> prendiTuttiProcessi() throws SQLException{
		ArrayList<Processo> al = new ArrayList<Processo>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng ");
		sb.append(" from processo proc, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,");
		sb.append(" litologia l,proprieta_termiche pt, stato_fratturazione sf, sito_processo sp,classi_volume cv");
		sb.append(" where proc.idubicazione = u.idubicazione ");
		sb.append(" and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and");
		sb.append(" b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and ");
		sb.append(" proc.idlitologia = l.idlitologia and pt.idproprietatermiche=proc.idproprietatermiche and sp.idsitoprocesso=proc.idsito and proc.idclassevolume=cv.idclassevolume");
		ResultSet rs = st.executeQuery(sb.toString());
		while(rs.next()){
			Processo p = new Processo();
			Ubicazione u = new Ubicazione();
			Coordinate coord = new Coordinate();
			LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
			LocazioneIdrologica locIdro = new LocazioneIdrologica();
			p.setIdprocesso(rs.getInt("idProcesso"));
			p.setNome(rs.getString("nome"));
			p.setData(rs.getTimestamp("data"));
			p.setDescrizione(rs.getString("descrizione"));
			p.setNote(rs.getString("note"));
			p.setAltezza(rs.getDouble("altezza"));
			p.setLarghezza(rs.getDouble("larghezza"));
			p.setSuperficie(rs.getDouble("superficie"));
			p.setVolumeSpecifico(rs.getDouble("volumespecifico"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			u.setCoordinate(coord);
			u.setLocAmm(locAmm);
			u.setLocIdro(locIdro);
			u.setEsposizione(rs.getString("esposizione"));
			u.setQuota(rs.getDouble("quota"));
			p.setUbicazione(u);
			ClasseVolume cv = new ClasseVolume();
			cv.setIdClasseVolume(rs.getInt("idclassevolume"));
			cv.setIntervallo(rs.getString("intervallo"));
			p.setClasseVolume(cv);
			Litologia l = new Litologia();
			l.setIdLitologia(rs.getInt("idlitologia"));
			l.setNomeLitologia_IT(rs.getString("lito_it"));
			l.setNomeLitologia_ENG(rs.getString("lito_eng"));
			p.setLitologia(l);
			ProprietaTermiche pt = new ProprietaTermiche();
			pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
			pt.setProprietaTermiche_IT(rs.getString("pt_it"));
			pt.setProprietaTermiche_ENG(rs.getString("pt_eng"));
			p.setProprietaTermiche(pt);
			StatoFratturazione sf = new StatoFratturazione();
			sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
			sf.setStatoFratturazione_IT(rs.getString("sf_it"));
			sf.setStatoFratturazione_ENG(rs.getString("sf_eng"));
			p.setStatoFratturazione(sf);
			SitoProcesso sp = new SitoProcesso();
			sp.setIdSito(rs.getInt("idsito"));
			sp.setCaratteristicaSito_IT(rs.getString("caratteristica_it"));
			sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
			p.setDanni(prendiDanniProcesso(rs.getInt("idprocesso")));
			p.setEffetti(prendiEffettiProcesso(rs.getInt("idprocesso")));
			p.setTipologiaProcesso(prendiCaratteristicheProcesso(rs.getInt("idprocesso")));
			p.setUbicazione(u);
			al.add(p);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static ArrayList<Processo> ricercaProcesso(Processo p,Ubicazione u) throws SQLException{
		ArrayList<Processo> al = new ArrayList<Processo>();
		StringBuilder sb = new StringBuilder();
		StringBuilder su = new StringBuilder();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		if(!(p.getAltezza()==null || p.getAltezza()==0)){
			sb.append(" and altezza="+p.getAltezza()+"");
		}
		
		if(!(p.getNome()==null || p.getNome().equals(""))){
				sb.append(" and nome="+p.getNome()+"");
		}
		if(!(p.getSuperficie()==null || p.getSuperficie()==0)){
				sb.append(" and superficie="+p.getSuperficie()+"");
		}
		if(!(p.getLarghezza()==null || p.getLarghezza()==0)){
				sb.append(" and larghezza="+p.getLarghezza()+"");
		}
		if(!((p.getData()==null) ||p.getData().equals(Timestamp.valueOf("0001-01-01 00:00:00")))){
				sb.append(" and data='"+p.getData()+"'");
		}
		if(!(p.getClasseVolume().getIdClasseVolume()==0)){
				sb.append(" and idclassevolume="+p.getClasseVolume().getIdClasseVolume()+"");
			}
		if(!(p.getLitologia().getidLitologia()==0)){
				sb.append(" and idlitologia="+p.getLitologia().getidLitologia()+"");
		}
		if(!(p.getProprietaTermiche().getIdProprieta_termiche()==0)){
				sb.append(" and idproprietatermiche="+p.getProprietaTermiche().getIdProprieta_termiche()+"");
		}
		if(!(p.getStatoFratturazione().getIdStato_fratturazione()==0)){
				sb.append(" and idstatofratturazione="+p.getStatoFratturazione().getIdStato_fratturazione()+"");
		}
		if(!(p.getSitoProcesso().getIdSito()==0)){
				sb.append(" and idsito="+p.getSitoProcesso().getIdSito()+"");
		}
		
		if(!(p.getDanni().size()==0)){
			StringBuilder sd=new StringBuilder();
			sd.append(" and idprocesso in(");
			for(int i=0;i<p.getDanni().size();i++){
				if(i==0)
					sd.append("select distinct(idprocesso) from danni_processo where iddanno="+p.getDanni().get(i).getIdDanni()+"");
				else sd.append(" and idprocesso in(select distinct(idprocesso) from danni_processo where iddanno="+p.getDanni().get(i).getIdDanni()+"");
			}
			int i=0;
			while(i!=p.getDanni().size()){
				sd.append(")");
				i++;
			}
			if(!(sd.toString().equals(""))) sb.append(sd);
		}
		if(!(p.getEffetti().size()==0)){
			StringBuilder se=new StringBuilder();
			se.append(" and idprocesso in(");
			for(int i=0;i<p.getEffetti().size();i++){
				if(i==0)
					se.append("select distinct(idprocesso) from effetti_processo where ideffettimorfologici="+p.getEffetti().get(i).getIdEffettiMorfoligici()+"");
				else se.append(" and idprocesso in(select distinct(idprocesso) from effetti_processo where ideffettimorfologici="+p.getEffetti().get(i).getIdEffettiMorfoligici()+"");
			}
			int i=0;
			while(i!=p.getEffetti().size()){
				se.append(")");
				i++;
			}	
			if(!(se.toString().equals(""))) sb.append(se);
		}
		
		if(!(p.getTipologiaProcesso().size()==0)){
			StringBuilder stp=new StringBuilder();
			stp.append(" and idprocesso in(");
			for(int i=0;i<p.getTipologiaProcesso().size();i++){
				if(i==0)
					stp.append("select distinct(idprocesso) from caratteristiche_processo where idtipologiaprocesso="+p.getTipologiaProcesso().get(i).getIdTipologiaProcesso()+"");
				else stp.append(" and idprocesso in(select distinct(idprocesso) from caratteristiche_processo where idtipologiaprocesso="+p.getTipologiaProcesso().get(i).getIdTipologiaProcesso()+"");
			}
			int i=0;
			while(i!=p.getTipologiaProcesso().size()){
				stp.append(")");
				i++;
			}
			if(!(stp.toString().equals(""))) sb.append(stp);
		}
		
		if(!(u.getLocAmm().isEmpty())){
			if(!(u.getLocAmm().getComune()==null || u.getLocAmm().getComune().equals(""))){
				su.append(" and c.nomecomune='"+u.getLocAmm().getComune()+"'");
			}
			if(!(u.getLocAmm().getProvincia()==null || u.getLocAmm().getProvincia().equals(""))){
				su.append(" and p.nomeprovincia='"+u.getLocAmm().getProvincia()+"'");
			}
			if(!(u.getLocAmm().getRegione()==null || u.getLocAmm().getRegione().equals(""))){
				su.append(" and r.nomeregione ='"+u.getLocAmm().getRegione()+"'");
			}		
			if(!(u.getLocAmm().getNazione()==null || u.getLocAmm().getNazione().equals(""))){
				su.append(" and n.nomenazione ='"+u.getLocAmm().getNazione()+"'");
			}
		}
		
		if(!(u.getLocIdro().isEmpty())){
			if(!(u.getLocIdro().getBacino()==null || u.getLocIdro().getBacino().equals(""))){
				su.append(" and b.nomebacino='"+u.getLocIdro().getBacino()+"'");
			}
			if(!(u.getLocIdro().getSottobacino()==null || u.getLocIdro().getSottobacino().equals(""))){
				su.append(" and s.nomesottobacino='"+u.getLocIdro().getSottobacino()+"'");
			}
		}
		
		ResultSet rs = null;
		StringBuilder query = new StringBuilder();
		query.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng ");
		query.append(" from processo proc, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,");
		query.append(" litologia l,proprieta_termiche pt, stato_fratturazione sf, sito_processo sp,classi_volume cv");
		query.append(" where proc.idubicazione = u.idubicazione ");
		query.append(" and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and");
		query.append(" b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and ");
		query.append(" proc.idlitologia = l.idlitologia and pt.idproprietatermiche=proc.idproprietatermiche and sp.idsitoprocesso=proc.idsito and proc.idclassevolume=cv.idclassevolume");
		if(u.isEmpty()==true){
			System.out.println("query "+sb.toString());
		 rs = st.executeQuery(""+query.toString()+" "+sb.toString()+" ");
		}
		else {
			System.out.println("SELECT * FROM processo  "+sb.toString()+" "+su.toString()+" ");
			rs = st.executeQuery(""+query.toString()+"  "+sb.toString()+" "+su.toString()+" ");
		}
		while(rs.next()){
			Processo ps = new Processo();
			Ubicazione ubi = new Ubicazione();
			Coordinate coord = new Coordinate();
			LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
			LocazioneIdrologica locIdro = new LocazioneIdrologica();
			ps.setIdprocesso(rs.getInt("idProcesso"));
			ps.setNome(rs.getString("nome"));
			ps.setData(rs.getTimestamp("data"));
			ps.setDescrizione(rs.getString("descrizione"));
			ps.setNote(rs.getString("note"));
			ps.setAltezza(rs.getDouble("altezza"));
			ps.setLarghezza(rs.getDouble("larghezza"));
			ps.setSuperficie(rs.getDouble("superficie"));
			ps.setVolumeSpecifico(rs.getDouble("volumespecifico"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			ubi.setCoordinate(coord);
			ubi.setLocAmm(locAmm);
			ubi.setLocIdro(locIdro);
			ubi.setEsposizione(rs.getString("esposizione"));
			ubi.setQuota(rs.getDouble("quota"));
			ps.setUbicazione(ubi);
			ClasseVolume cv = new ClasseVolume();
			cv.setIdClasseVolume(rs.getInt("idclassevolume"));
			cv.setIntervallo(rs.getString("intervallo"));
			ps.setClasseVolume(cv);
			Litologia l = new Litologia();
			l.setIdLitologia(rs.getInt("idlitologia"));
			l.setNomeLitologia_IT(rs.getString("lito_it"));
			l.setNomeLitologia_ENG(rs.getString("lito_eng"));
			ps.setLitologia(l);
			ProprietaTermiche pt = new ProprietaTermiche();
			pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
			pt.setProprietaTermiche_IT(rs.getString("pt_it"));
			pt.setProprietaTermiche_ENG(rs.getString("pt_eng"));
			ps.setProprietaTermiche(pt);
			StatoFratturazione sf = new StatoFratturazione();
			sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
			sf.setStatoFratturazione_IT(rs.getString("sf_it"));
			sf.setStatoFratturazione_ENG(rs.getString("sf_eng"));
			ps.setStatoFratturazione(sf);
			SitoProcesso sp = new SitoProcesso();
			sp.setIdSito(rs.getInt("idsito"));
			sp.setCaratteristicaSito_IT(rs.getString("caratteristica_it"));
			sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
			ps.setDanni(prendiDanniProcesso(rs.getInt("idprocesso")));
			ps.setEffetti(prendiEffettiProcesso(rs.getInt("idprocesso")));
			ps.setTipologiaProcesso(prendiCaratteristicheProcesso(rs.getInt("idprocesso")));
			al.add(ps);
		}	
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static void modificaProcesso(Processo p) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		StringBuilder su = new StringBuilder();
		
		sb.append("update processo set ");
		if(!(p.getNome()==null || p.getNome().equals(""))){
			sb.append("nome = '"+p.getNome()+"',");
		}
		if(!(p.getSuperficie()==null || p.getSuperficie()==0)){
			sb.append("superficie = "+p.getSuperficie()+",");
		}
		if(!(p.getAltezza()==null || p.getAltezza()==0)){
			sb.append("altezza = "+p.getAltezza()+" ,");
		}
		if(!(p.getLarghezza()==null || p.getLarghezza()==0)){
			sb.append("larghezza = "+p.getLarghezza()+" ,");
		}
		if(!(p.getData()==null)){
			sb.append("data = '"+p.getData()+"' ,");
		}
		if(!(p.getNote()==null || p.getNote().equals(""))){
			sb.append("note = '"+p.getNote()+"',");
		}
		
		if(!(p.getClasseVolume().getIdClasseVolume()==0)){
			sb.append("idclassevolume ="+p.getClasseVolume().getIdClasseVolume()+",");
		}
		if(!(p.getLitologia().getidLitologia()==0)){
			sb.append("idlitologia = "+p.getLitologia().getidLitologia()+",");
		}
		if(!(p.getProprietaTermiche().getIdProprieta_termiche()==0)){
			sb.append("idproprietatermiche = "+p.getProprietaTermiche().getIdProprieta_termiche()+",");
		}
		if(!(p.getStatoFratturazione().getIdStato_fratturazione()==0)){
			sb.append("idstatofratturazione = "+p.getStatoFratturazione().getIdStato_fratturazione()+",");
		}
		if(!(p.getSitoProcesso().getIdSito()==0)){
			sb.append("idsito = "+p.getSitoProcesso().getIdSito()+",");
		}
		if(!(p.getDescrizione()==null || p.getDescrizione().equals(""))){
			sb.append("descrizione = '"+p.getDescrizione()+"'");
		}
		sb.append("where idProcesso="+p.getIdProcesso());
		
		st.executeUpdate(""+sb.toString());
		
		
		if(!(p.getEffetti().isEmpty()||p.getDanni().isEmpty()||p.getEffetti().size()==0||p.getDanni().size()==0)){
			st.executeUpdate("delete from danni_processo where idprocesso ="+p.getIdProcesso()+"" );
			st.executeUpdate("delete from effetti_processo where idprocesso = "+p.getIdProcesso()+"");
			salvaEffetti(p.getIdProcesso(), p.getEffetti(), p.getDanni());
		}else{
			st.executeUpdate("delete from danni_processo where idprocesso ="+p.getIdProcesso()+"" );
			st.executeUpdate("delete from effetti_processo where idprocesso = "+p.getIdProcesso()+"");
		}
		
		if(!(p.getTipologiaProcesso().isEmpty() ||p.getTipologiaProcesso().size()==0)){
			System.out.println("size: "+p.getTipologiaProcesso().size());
			st.executeUpdate("delete from caratteristiche_processo where idprocesso = "+p.getIdProcesso()+"" );
			salvaTipologiaProcesso(p.getIdProcesso(), p.getTipologiaProcesso());
		}else{
			st.executeUpdate("delete from caratteristiche_processo where idprocesso = "+p.getIdProcesso()+"" );
		}
	
		if(p.getUbicazione()!=null){
			su.append("update ubicazione set ");
			if(!(p.getUbicazione().getEsposizione()==null || p.getUbicazione().getEsposizione().equals(""))){
				su.append("esposizione = '"+p.getUbicazione().getEsposizione()+"',");
			}
			if(!(p.getUbicazione().getQuota()==null || p.getUbicazione().getQuota()==0)){
				su.append("quota = "+p.getUbicazione().getQuota()+",");
			}
			if(!(p.getUbicazione().getCoordinate().getX()==null || p.getUbicazione().getCoordinate().getX()==0)){
				
			if(!(p.getUbicazione().getCoordinate().getY()==null || p.getUbicazione().getCoordinate().getY()==0))
				
				su.append("coordinate = 'POINT("+p.getUbicazione().getCoordinate().getX()+" "+p.getUbicazione().getCoordinate().getY()+")',");
			}
			if(p.getUbicazione().getLocAmm()!=null){
				su.append("idcomune ="+p.getUbicazione().getLocAmm().getIdComune()+",");
			}
			if(p.getUbicazione().getLocIdro()!=null){
				su.append("idsottobacino ="+p.getUbicazione().getLocIdro().getIdSottobacino());
			}
			su.append(" where idubicazione="+p.getUbicazione().getIdUbicazione());
			
			st.executeUpdate(""+su.toString());
		}

		st.close();
		conn.close();
		
	}
	public static void eliminaProcesso(Processo p) throws SQLException{
		//cancellare processo e ubicazione
		Connection conn = DriverManager.getConnection(url,user,pwd);
		PreparedStatement psp = conn.prepareStatement("delete from processo where idprocesso="+p.getIdProcesso()+"");
		PreparedStatement psu = conn.prepareStatement("delete from ubicazione where idubicazione="+p.getUbicazione().getIdUbicazione()+"");
		psp.executeUpdate();
		psu.executeUpdate();
	}
	
	/*
	 * caratteristiche del processo
	 */
	
	public static ArrayList<EffettiMorfologici> prendiEffettiMOrfologici() throws SQLException{
		ArrayList<EffettiMorfologici> al = new ArrayList<EffettiMorfologici>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from effetti_Morfologici");
		while(rs.next()){
			EffettiMorfologici em = new EffettiMorfologici();
			em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
			em.setTipo_IT(rs.getString("tipo_it"));
			em.setTipo_ENG(rs.getString("tipo_eng"));
			al.add(em);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static EffettiMorfologici prendiEffettoMorfologico(int idEffetto) throws SQLException{
		EffettiMorfologici em  = new EffettiMorfologici();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from effetti_Morfologici where ideffettimorfologici="+idEffetto+"");
		while(rs.next()){
			em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
			em.setTipo_IT(rs.getString("tipo_it"));
			em.setTipo_ENG(rs.getString("tipo_eng"));
		}
		rs.close();
		st.close();
		conn.close();
		return em;
	}
	public static int prendiIdEffettiMorfologici(String effetto,String loc) throws SQLException{
		int i = 0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select idEffettimorfologici from effetti_morfologici where tipo_"+loc+"='"+effetto+"'");
		while(rs.next()){
			i = rs.getInt("idEffettimorfologici");
		}
		rs.close();
		st.close();
		conn.close();
		return i;
	}
	
	
	
	public static ArrayList<EffettiMorfologici> prendiEffettiProcesso(int idProcesso) throws SQLException{
		ArrayList<EffettiMorfologici> al = new ArrayList<EffettiMorfologici>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from effetti_morfologici where ideffettimorfologici in( select ideffettimorfologici from effetti_processo where idprocesso="+idProcesso+")");
		while(rs.next()){
			EffettiMorfologici em = new EffettiMorfologici();
			em.setIdEffettiMOrfologici(rs.getInt("ideffettimorfologici"));
			em.setTipo_IT(rs.getString("tipo_it"));
			em.setTipo_ENG(rs.getString("tipo_eng"));
			al.add(em);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static ArrayList<Danni> prendiDanni() throws SQLException{
		ArrayList<Danni> al = new ArrayList<Danni>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from danno");
		while(rs.next()){
			Danni d = new Danni();
			d.setIdDanni(rs.getInt("iddanno"));
			d.setTipo_IT(rs.getString("tipo_it"));
			d.setTipo_ENG(rs.getString("tipo_eng"));
			al.add(d);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	public static Danni prendiDanno(int idDanno) throws SQLException{
		Danni d = new Danni();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from danno where iddanno="+idDanno+"");
		while(rs.next()){
			d.setIdDanni(rs.getInt("iddanno"));
			d.setTipo_IT(rs.getString("tipo_it"));
			d.setTipo_ENG(rs.getString("tipo_eng"));
		}
		rs.close();
		st.close();
		conn.close();
		return d;
	}
	public static int prendiIdDanni(String danno,String loc) throws SQLException{
		int i = 0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select iddanno from danno where tipo_"+loc+" = '"+danno+"'");
		while(rs.next()){
			i = rs.getInt("iddanno");
		}
		rs.close();
		st.close();
		conn.close();
		return i;
	}
	public static ArrayList<Danni> prendiDanniProcesso(int idProcesso) throws SQLException{
		ArrayList<Danni> al = new ArrayList<Danni>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from danno where iddanno in( select iddanno from danni_processo where idprocesso="+idProcesso+")");
		while(rs.next()){
			Danni d = new Danni();
			d.setIdDanni(rs.getInt("iddanno"));
			d.setTipo_IT(rs.getString("tipo_it"));
			d.setTipo_ENG(rs.getString("tipo_eng"));
			al.add(d);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static ArrayList<ProprietaTermiche> prendiProprietaTermiche() throws SQLException{
		ArrayList<ProprietaTermiche> al = new ArrayList<ProprietaTermiche>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from proprieta_termiche");
		while(rs.next()){
			ProprietaTermiche pt = new ProprietaTermiche();
			pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
			pt.setProprietaTermiche_IT(rs.getString("nome_it"));
			pt.setProprietaTermiche_ENG(rs.getString("nome_eng"));
			al.add(pt);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	public static ProprietaTermiche prendiProprietaTermica(int idPropTermica) throws SQLException{
		ProprietaTermiche pt = new ProprietaTermiche();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from proprieta_termiche where idproprietatermiche="+idPropTermica+"");
		while(rs.next()){
			pt.setIdProprietaTermiche(rs.getInt("idproprietatermiche"));
			pt.setProprietaTermiche_IT(rs.getString("nome_it"));
			pt.setProprietaTermiche_ENG(rs.getString("nome_eng"));
		}
		rs.close();
		st.close();
		conn.close();
		return pt;
	}
	
	public static ArrayList<StatoFratturazione> prendiStatoFratturazione() throws SQLException{
		ArrayList<StatoFratturazione> al = new ArrayList<StatoFratturazione>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from stato_fratturazione");
		while(rs.next()){
			StatoFratturazione sf = new StatoFratturazione();
			sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
			sf.setStatoFratturazione_IT(rs.getString("nome_it"));
			sf.setStatoFratturazione_ENG(rs.getString("nome_ENG"));
			al.add(sf);
		}
		st.close();
		conn.close();
		return al;
	}
	public static StatoFratturazione prendiStatoFratturazione(int idStatofratturazione) throws SQLException{
		StatoFratturazione sf = new StatoFratturazione();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from stato_fratturazione where idstatofratturazione="+idStatofratturazione+"");
		while(rs.next()){
			sf.setIdStatoFratturazione(rs.getInt("idstatofratturazione"));
			sf.setStatoFratturazione_IT(rs.getString("nome_it"));
			sf.setStatoFratturazione_ENG(rs.getString("nome_ENG"));
		}
		rs.close();
		st.close();
		conn.close();
		return sf;
	}
	public static ArrayList<Litologia> prendiLitologia() throws SQLException{
		ArrayList<Litologia> al = new ArrayList<Litologia>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from litologia");
		while(rs.next()){
			Litologia l = new Litologia();
			l.setIdLitologia(rs.getInt("idlitologia"));
			l.setNomeLitologia_IT(rs.getString("nome_IT"));
			l.setNomeLitologia_ENG(rs.getString("nome_ENG"));
			al.add(l);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	public static Litologia prendiLitologia(int idLitologia) throws SQLException{
		Litologia l = new Litologia();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from litologia where idlitologia="+idLitologia+"");
		while(rs.next()){
			l.setIdLitologia(rs.getInt("idlitologia"));
			l.setNomeLitologia_IT(rs.getString("nome_IT"));
			l.setNomeLitologia_ENG(rs.getString("nome_ENG"));
		}
		rs.close();
		st.close();
		conn.close();
		return l;
	}
	
	public static ArrayList<SitoProcesso> prendiSitoProcesso() throws SQLException{
		ArrayList<SitoProcesso> al = new ArrayList<SitoProcesso>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from sito_processo");
		while(rs.next()){
			SitoProcesso sp = new SitoProcesso();
			sp.setIdSito(rs.getInt("idsitoprocesso"));
			sp.setCaratteristicaSito_IT(rs.getString("caratteristica_IT"));
			sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
			al.add(sp);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	public static SitoProcesso prendiSitoProcesso(int idSitoProcesso) throws SQLException{
		SitoProcesso sp = new SitoProcesso();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from sito_processo where idsitoprocesso="+idSitoProcesso+" ");
		while(rs.next()){
			sp.setIdSito(rs.getInt("idsitoprocesso"));
			sp.setCaratteristicaSito_IT(rs.getString("caratteristica_IT"));
			sp.setCaratteristicaSito_ENG(rs.getString("caratteristica_eng"));
		}
		rs.close();
		st.close();
		conn.close();
		return sp;
	}
	
	public static ArrayList<ClasseVolume> prendiClasseVolume() throws SQLException{
		ArrayList<ClasseVolume> al = new ArrayList<ClasseVolume>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from classi_volume");
		while(rs.next()){
			ClasseVolume cv = new ClasseVolume();
			cv.setIdClasseVolume(rs.getInt("idclassevolume"));
			cv.setIntervallo(rs.getString("intervallo"));
			al.add(cv);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static ClasseVolume prendiClasseVolume(int idClasseVolume) throws SQLException{
		ClasseVolume cv = new ClasseVolume();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from classi_volume where idclassevolume="+idClasseVolume+" ");
		while(rs.next()){
			cv.setIdClasseVolume(rs.getInt("idclassevolume"));
			cv.setIntervallo(rs.getString("intervallo"));
		}
		rs.close();
		st.close();
		conn.close();
		return cv;
	}
	
	public static ArrayList<TipologiaProcesso> prendiTipologiaProcesso() throws SQLException{
		ArrayList<TipologiaProcesso> al = new ArrayList<TipologiaProcesso>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from tipologia_processo");
		while(rs.next()){
			TipologiaProcesso tp = new TipologiaProcesso();
			tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
			tp.setNome_IT(rs.getString("nome_it"));
			tp.setNome_ENG(rs.getString("nome_eng"));
			al.add(tp);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	public static TipologiaProcesso prendiTipologiaProcesso(int idTipologiaProcesso) throws SQLException{
		TipologiaProcesso tp = new TipologiaProcesso();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from tipologia_processo where idtipologiaprocesso="+idTipologiaProcesso+" ");
		while(rs.next()){
			tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
			tp.setNome_IT(rs.getString("nome_it"));
			tp.setNome_ENG(rs.getString("nome_eng"));
		}
		rs.close();
		st.close();
		conn.close();
		return tp;
	}
	
	
	public static int prendiIdTipologiaProcesso(String tipologiaProcesso,String loc) throws SQLException{
		int i = 0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select idtipologiaprocesso from tipologia_processo where nome_"+loc+" ='"+tipologiaProcesso+"'");
		while(rs.next()){
			i = rs.getInt("idtipologiaprocesso");
		}
		rs.close();
		st.close();
		conn.close();
		return i;
	}
	
	public static ArrayList<TipologiaProcesso> prendiCaratteristicheProcesso(int idProcesso) throws SQLException{
		ArrayList<TipologiaProcesso> al = new ArrayList<TipologiaProcesso>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from tipologia_processo where idtipologiaprocesso in( select idtipologiaprocesso from caratteristiche_processo where idprocesso="+idProcesso+")");
		while(rs.next()){
			TipologiaProcesso tp = new TipologiaProcesso();
			tp.setIdTipologiaProcesso(rs.getInt("idtipologiaprocesso"));
			tp.setNome_IT(rs.getString("nome_it"));
			tp.setNome_ENG(rs.getString("nome_eng"));
			al.add(tp);
		}
		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	
	/*
	 * stazione metereologica
	 */
	public static void salvaStazione(StazioneMetereologica s)throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb=new StringBuilder();
		sb.append("'"+s.ente.getIdEnte()+"'");
		sb.append(",'"+s.getNome()+"'");
		sb.append(",'"+s.getAggregazioneGiornaliera()+"'");
		sb.append(","+s.getOraria());
		sb.append(",'"+s.getNote()+"'");
		sb.append(",'"+s.getDataInizio()+"'");
		sb.append(",'"+s.getDataFine()+"'");
		sb.append(",'"+s.sito.getIdSitoStazioneMetereologica()+"'");
		sb.append(","+s.getIdUtente());
		sb.append(","+s.getUbicazione().getIdUbicazione());
		
		 st.executeUpdate("INSERT INTO STAZIONE_METEREOLOGICA(IDENTE,NOME,AGGREGAZIONEGIORNALIERA,ORARIA,NOTE,datainizio,datafine,idSitoStazione,idutentecreatore,IDUBICAZIONE) VALUES( "+sb+")");

		 ResultSet rs =st.executeQuery("SELECT * FROM STAZIONE_METEREOLOGICA WHERE  idUbicazione="+s.getUbicazione().getIdUbicazione()+" and datainizio='"+s.getDataInizio()+"'and datafine='"+s.getDataFine()+"' ");
		while(rs.next())
			s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
		
		salvaSensoriStazione(s);
		rs.close();
		st.close();
		conn.close();
	}

	public static ArrayList<StazioneMetereologica> prendiTutteStazioniMetereologiche() throws SQLException{
		ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,ente e,sito_stazione ss where staz.idubicazione = u.idubicazione"
				+ " and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and"
				+ " b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and staz.idente=e.idente and staz.idsitostazione=ss.idsitostazione");
		while(rs.next()){
			StazioneMetereologica s=new StazioneMetereologica();
			Ubicazione u = new Ubicazione();
			Coordinate coord = new Coordinate();
			LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
			LocazioneIdrologica locIdro = new LocazioneIdrologica();
			s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
			s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
			s.setNote(rs.getString("note"));
			s.setOraria(rs.getBoolean("oraria"));
			s.setDataInizio(rs.getDate("datainizio"));
			s.setDataFine(rs.getDate("datafine"));
			s.setNome(rs.getString("nome"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			u.setCoordinate(coord);
			u.setLocAmm(locAmm);
			u.setLocIdro(locIdro);
			u.setEsposizione(rs.getString("esposizione"));
			u.setQuota(rs.getDouble("quota"));
			s.setUbicazione(u);
			Ente e = new Ente();
			e.setIdEnte(rs.getInt("idente"));
			e.setEnte(rs.getString("enome"));
			s.setEnte(e);
			SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
			sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
			sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
			sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
			s.setSito(sito);
			s.setIdUtente(rs.getInt("idutentecreatore"));
			al.add(s);
		}
		rs.close();
		st.close();
		conn.close();
		return al;

	}
	public static StazioneMetereologica prendiStazioneMetereologica(int idStazioneMetereologica,String loc)throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,ente e,sito_stazione ss  "
				+ "where  staz.idstazionemetereologica="+idStazioneMetereologica+"  and staz.idubicazione = u.idubicazione"
				+ " and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and"
				+ " b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and staz.idente=e.idente and staz.idsitostazione=ss.idsitostazione");
		StazioneMetereologica s=new StazioneMetereologica();
		Ubicazione u = new Ubicazione();
		Coordinate coord = new Coordinate();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		while(rs.next()){
			s.setIdStazioneMetereologica(idStazioneMetereologica);
			s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
			s.setNote(rs.getString("note"));
			s.setOraria(rs.getBoolean("oraria"));
			s.setDataInizio(rs.getDate("datainizio"));
			s.setDataFine(rs.getDate("datafine"));
			s.setNome(rs.getString("nome"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			u.setCoordinate(coord);
			u.setLocAmm(locAmm);
			u.setLocIdro(locIdro);
			u.setEsposizione(rs.getString("esposizione"));
			u.setQuota(rs.getDouble("quota"));
			s.setUbicazione(u);
			Ente e = new Ente();
			e.setIdEnte(rs.getInt("idente"));
			e.setEnte(rs.getString("enome"));
			s.setEnte(e);
			SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
			sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
			sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
			sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
			s.setSito(sito);
			s.setIdUtente(rs.getInt("idutentecreatore"));
		}
		ArrayList<Sensori> sensori=prendiSensori(idStazioneMetereologica,loc);
		s.setSensori(sensori);
		rs.close();
		st.close();
		conn.close();
	    return s;
	}
	
	public static Ente prendiEnte(int idente) throws SQLException{
		Ente e=new Ente();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM ente WHERE IDente="+idente+"");
		while(rs.next()){
			e.setEnte(rs.getString("nome"));
			e.setIdEnte(rs.getInt("idente"));
		}
		rs.close();
		st.close();
		conn.close();
		return e;
	}
	
	public static SitoStazioneMetereologica prendiSitoStazioneMetereologica(int id) throws SQLException{
		SitoStazioneMetereologica s=new SitoStazioneMetereologica();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM sito_Stazione where idsitostazione="+id+" ");
		while(rs.next()){
			s.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
			s.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
			s.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
		}
		rs.close();
		st.close();
		conn.close();
		return s;
	}

	
	
	public static void modificaStazioneMetereologica(StazioneMetereologica s,String enteVecchio,int idStazione) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		StringBuilder su = new StringBuilder();
		sb.append("update stazione_metereologica set ");
		if(!(s.getNome()==null || s.getNome().equals(""))){
			sb.append("nome = '"+s.getNome()+"',");
		}
		if(!(s.getAggregazioneGiornaliera()==null || s.getAggregazioneGiornaliera().equals(""))){
			sb.append("aggregazionegiornaliera = '"+s.getAggregazioneGiornaliera()+"',");
		}
		//ResultSet rs = st.executeQuery("SELECT idente FROM stazione_metereologica where idstazionemetereologica="+s.getIdStazioneMetereologica()+"");
	/*	while(rs.next()){
			ente = (rs.getInt("idente"));
		}*/
		if(!enteVecchio.equals(s.getEnte().getEnte())) sb.append("note = 'stazione passata da"+enteVecchio+"a "+s.ente.getEnte()+". "+s.getNote()+" ',"); 
		else   sb.append("note = "+s.getNote()+" ,");
		if(!(s.getDataInizio()==null)){
			sb.append("datainizio= '"+s.getDataInizio()+"' ,");
		}
		if(!(s.getDataFine()==null)){
			sb.append("dataFine= '"+s.getDataFine()+"' ,");
		}
	
			sb.append("oraria = '"+s.getOraria()+"',");
		
		if(!(s.sito.getIdSitoStazioneMetereologica()==0)){
			sb.append("idsitostazione="+s.sito.getIdSitoStazioneMetereologica()+",");
		}
		if(!(s.ente.getIdEnte()==0)){
			sb.append("idente ="+s.ente.getIdEnte()+",");   //?
		}
		
		if(!(s.getIdUtente()==0)){
			sb.append("idutentecreatore="+s.getIdUtente());
		}
		
		sb.append("where idStazioneMetereologica="+idStazione);
		System.out.println("query: "+sb.toString());
		st.executeUpdate(""+sb.toString());

		/*
		 * modifica dell'ubicazione
		 */
		if(s.getUbicazione()!=null){
			su.append("update ubicazione set ");
			if(!(s.getUbicazione().getEsposizione()==null || s.getUbicazione().getEsposizione().equals(""))){
				su.append("esposizione = '"+s.getUbicazione().getEsposizione()+"',");
			}
			if(!(s.getUbicazione().getQuota()==null || s.getUbicazione().getQuota()==0)){
				su.append("quota = "+s.getUbicazione().getQuota()+",");
			}
			if(!(s.getUbicazione().getCoordinate().getX()==null || s.getUbicazione().getCoordinate().getX()==0)){

			if(!(s.getUbicazione().getCoordinate().getY()==null || s.getUbicazione().getCoordinate().getY()==0))

				su.append("coordinate = 'POINT("+s.getUbicazione().getCoordinate().getX()+" "+s.getUbicazione().getCoordinate().getY()+")',");
			}
			if(s.getUbicazione().getLocAmm()!=null){
				su.append("idcomune ="+s.getUbicazione().getLocAmm().getIdComune()+",");
			}
			if(s.getUbicazione().getLocIdro()!=null){
				su.append("idsottobacino =1");
			}
			su.append(" where idubicazione="+s.getUbicazione().getIdUbicazione());
			System.out.println(su.toString());
			st.executeUpdate(""+su.toString());
		}
		
		
		/*
		 * modifica sensori
		 */
		st.executeUpdate("delete from sensore_stazione where idStazionemetereologica="+idStazione+"");
		for(Sensori i:s.getSensori()){
			if(i.getIdsensori()!=0){
				try{
					st.executeUpdate("INSERT INTO sensore_stazione (idstazionemetereologica,idsensore) VALUES ("+idStazione+","+i.getIdsensori()+")");
				}catch(SQLException e){System.out.println("sensore esistente");}
			}			
		}
		st.close();
		conn.close();
	}
	
	public static void salvaSensoriStazione(StazioneMetereologica s) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		for(Sensori i:s.getSensori()){
			 st.executeUpdate("INSERT INTO sensore_stazione(idsensore,idstazionemetereologica) VALUES( "+i.getIdsensori()+","+s.getIdStazioneMetereologica()+")");
		}
		st.close();
		conn.close();
	}
	
	public static int idSensore(String sensore,String loc) throws SQLException{
		int id=0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		
		ResultSet rs=st.executeQuery("select idsensore from sensore where tipo_"+loc+"='"+sensore+"'");
		while(rs.next()){
			id=rs.getInt("idsensore");
		}
		rs.close();
		st.close();
		conn.close();
		return id;
	}
	
	
	public static ArrayList<StazioneMetereologica> ricercaStazioneMetereologica(StazioneMetereologica s,Ubicazione u) throws SQLException{
		ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
		StringBuilder sb = new StringBuilder();
		StringBuilder su = new StringBuilder();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		
		if(!(s.getNome()==null || s.getNome().equals(""))){
			sb.append(" and staz.nome='"+s.getNome()+"'");
		}
		if(!(s.getAggregazioneGiornaliera()==null || s.getAggregazioneGiornaliera().equals(""))){
			
				sb.append(" and staz.aggregazionegiornaliera='"+s.getAggregazioneGiornaliera()+"'");
		}
	/*	if(!(s.getDataInizio()==null)){
			if(sb.toString().equals("") || sb == null)
			sb.append("where datainizio= '"+s.getDataInizio()+"' ");
			else sb.append("and datainzio='"+s.getDataInizio()+"'");
		}
		if(!(s.getDataFine()==null)){
			if(sb.toString().equals("") || sb == null)
				sb.append("where datafine= '"+s.getDataFine()+"' ");
				else sb.append("and datafine='"+s.getDataFine()+"'");
		}
	*/	
		
		
		/*	
		 * bisogna decidere come gestire l'oraria se come intero o booleano
		 * if(sb.toString().equals("") || sb == null)
			sb.append("where oraria = "+s.getOraria()+"");
			else sb.append("and oraria="+s.getOraria()+"");
			*/
	
		if(!(s.sito.getIdSitoStazioneMetereologica()==0)){
			 sb.append(" and staz.idsitostazione="+s.sito.getIdSitoStazioneMetereologica()+"");

		}
		if(!(s.ente.getIdEnte()==0)){
			sb.append("and idente="+s.ente.getIdEnte()+"");   
		}
		StringBuilder se=new StringBuilder();
		if(!(s.getSensori().size()==0)){
			 se.append(" and idstazionemetereologica in(");
			for(int i=0;i<s.getSensori().size();i++){
				if(i==0)
					se.append("select distinct(idstazionemetereologica) from sensore_stazione where idsensore="+s.getSensori().get(i).getIdsensori()+"");
				else se.append(" and idstazionemetereologica in(select distinct(idstazionemetereologica) from sensore_stazione where idsensore="+s.getSensori().get(i).getIdsensori()+"");
			}
			int i=0;
			while(i!=s.getSensori().size()){
				se.append(")");
				i++;
			}
			
		}
		if(!(se.toString().equals(""))) sb.append(se);
		
		if(!(u.getLocAmm().getComune()==null || u.getLocAmm().getComune().equals(""))){
			su.append(" and c.nomecomune='"+u.getLocAmm().getComune()+"'");
		}
		if(!(u.getLocAmm().getProvincia()==null || u.getLocAmm().getProvincia().equals(""))){
			su.append(" and p.nomeprovincia='"+u.getLocAmm().getProvincia()+"'");
		}
		if(!(u.getLocAmm().getRegione()==null || u.getLocAmm().getRegione().equals(""))){
			su.append(" and r.nomeregione ='"+u.getLocAmm().getRegione()+"'");
		}
		if(!(u.getLocAmm().getNazione()==null || u.getLocAmm().getNazione().equals(""))){
			su.append(" and n.nomenazione ='"+u.getLocAmm().getNazione()+"'");
		}
		if(!( u.getCoordinate().getX()==0)){
			su.append(" and st_x(coordinate) ='"+u.getCoordinate().getX()+"'");
		}
		if(!( u.getCoordinate().getY()==0)){
			su.append(" and st_y(coordinate) ='"+u.getCoordinate().getY()+"'");
		}
	

		ResultSet rs = null;

		if(u.isEmpty()==true){
			System.out.println("1-SELECT * FROM stazione_metereologica  "+sb.toString());
		 rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,ente e,sito_stazione ss where staz.idubicazione = u.idubicazione"
				+ " and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and"
				+ " b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and staz.idente=e.idente and staz.idsitostazione=ss.idsitostazione "+sb.toString()+" ");
		}
		else {
			System.out.println("2-SELECT * FROM stazione_metereologica  "+sb.toString()+" "+su.toString()+" ");
			rs = st.executeQuery("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y,e.nome as enome  from stazione_metereologica staz, ubicazione u,comune c,provincia p,regione r,nazione n,bacino b,sottobacino s,ente e,sito_stazione ss where staz.idubicazione = u.idubicazione"
				+ " and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and"
				+ " b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and staz.idente=e.idente and staz.idsitostazione=ss.idsitostazione "+sb.toString()+" "+su.toString()+" ");
		}
		while(rs.next()){
			StazioneMetereologica sm=new StazioneMetereologica();
			Ubicazione ubi = new Ubicazione();
			Coordinate coord = new Coordinate();
			LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
			LocazioneIdrologica locIdro = new LocazioneIdrologica();
			sm.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
			sm.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
			sm.setNote(rs.getString("note"));
			sm.setOraria(rs.getBoolean("oraria"));
			sm.setDataInizio(rs.getDate("datainizio"));
			sm.setDataFine(rs.getDate("datafine"));
			sm.setNome(rs.getString("nome"));
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			ubi.setCoordinate(coord);
			ubi.setLocAmm(locAmm);
			ubi.setLocIdro(locIdro);
			ubi.setEsposizione(rs.getString("esposizione"));
			ubi.setQuota(rs.getDouble("quota"));
			sm.setUbicazione(ubi);
			Ente e = new Ente();
			e.setIdEnte(rs.getInt("idente"));
			e.setEnte(rs.getString("enome"));
			sm.setEnte(e);
			SitoStazioneMetereologica sito = new SitoStazioneMetereologica();
			sito.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
			sito.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
			sito.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
			sm.setSito(sito);
			sm.setIdUtente(rs.getInt("idutentecreatore"));
			al.add(sm);
		}

		rs.close();
		st.close();
		conn.close();
		return al;
	}
	
	public static ArrayList<Sensori> prendiSensori(int idStazione,String loc) throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("select tipo_"+loc+",idsensore from sensore where idsensore in(select idsensore from sensore_stazione where idstazionemetereologica="+idStazione+")");
		while(rs.next()){
			Sensori s=new Sensori();
			if(loc.equals("IT")) s.setSensori_IT((rs.getString("tipo_it")));
			else s.setSensori_ENG((rs.getString("tipo_eng")));
			
			s.setIdsensori(rs.getInt("idsensore"));
			sensori.add(s);
		}
		rs.close();
		st.close();
		conn.close();
		return sensori;
		
	}
	
	public static String prendiNome(int id) throws SQLException{
		String nome="";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("select nome from stazione_metereologica where idstazionemetereologica="+id+"");
		while(rs.next()){
			nome=rs.getString("nome");
		}
		rs.close();
		st.close();
		conn.close();
		return nome;
	}
	
	public static ArrayList<SitoStazioneMetereologica> prendiTuttiSitoStazioneMetereologica() throws SQLException{
		ArrayList<SitoStazioneMetereologica> sito=new ArrayList<SitoStazioneMetereologica>();
				Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM sito_Stazione ");
		while(rs.next()){
			SitoStazioneMetereologica s=new SitoStazioneMetereologica();
			s.setCaratteristiche_ENG(rs.getString("caratteristiche_eng"));
			s.setCaratteristiche_IT(rs.getString("caratteristiche_it"));
			s.setIdSitoStazioneMetereologica(rs.getInt("idsitostazione"));
			sito.add(s);
		}
		rs.close();
		st.close();
		conn.close();
		return sito;
	}
	
	public static ArrayList<Ente>  prendiTuttiEnte() throws SQLException{
		ArrayList<Ente> ente=new ArrayList<Ente>();

		
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM ente ");
		while(rs.next()){
			Ente e=new Ente();
			e.setEnte(rs.getString("nome"));
			e.setIdEnte(rs.getInt("idente"));
			ente.add(e);
		}
		rs.close();
		st.close();
		conn.close();
		return ente;
	}
	
	public static ArrayList<Sensori> prendiTuttiSensori() throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM sensore ");
		while(rs.next()){
			Sensori s=new Sensori();
			s.setSensori_ENG((rs.getString("tipo_eng")));
			s.setSensori_IT((rs.getString("tipo_it")));
			s.setIdsensori(rs.getInt("idsensore"));
			sensori.add(s);
		}
		rs.close();
		st.close();
		conn.close();
		return sensori;
	}
	public static ArrayList<StazioneMetereologica> prendiStazionidaRaggio(double x,double y,Processo p,int r) throws SQLException{
		ArrayList<StazioneMetereologica> s=new ArrayList<StazioneMetereologica>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT *,st_distance((select coordinate from ubicazione where idubicazione="+p.getUbicazione().getIdUbicazione()+"),ubicazione.coordinate) as distance FROM ubicazione,stazione_metereologica WHERE ST_DWithin((select coordinate from ubicazione where idubicazione="+p.getUbicazione().getIdUbicazione()+"), ubicazione.coordinate,"+r+")=true and ubicazione.idubicazione=stazione_metereologica.idubicazione");
		while(rs.next()){
			StazioneMetereologica stazione=new StazioneMetereologica();
			stazione.setIdStazioneMetereologica(rs.getInt("idstazionemetereologica"));
			stazione.setDistanzaProcesso(rs.getDouble("distance"));
			stazione.setNome(rs.getString("nome"));
			//completare
			Ubicazione ub = prendiUbicazione(rs.getInt("idUbicazione"));
			stazione.setUbicazione(ub);
			s.add(stazione);
		}
		rs.close();
		st.close();
		conn.close();
		return s;
	}
	
	/*
	 * ubicazione
	 */
	
	public static Ubicazione salvaUbicazione(Ubicazione u) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append(""+u.getLocIdro().getIdSottobacino()+"");
		sb.append(","+u.getLocAmm().getIdComune()+"");
		sb.append(","+u.getQuota());
		sb.append(",'"+u.getEsposizione()+"'");
		sb.append(",ST_GeometryFromText("+u.getCoordinate().toDB()+")");
		st.executeUpdate("INSERT INTO ubicazione(idSottobacino,idComune,quota,esposizione,coordinate) values("+sb.toString()+") ");
		
		ResultSet rs = st.executeQuery("SELECT * FROM ubicazione WHERE coordinate= "+u.getCoordinate().toDB()+" ");
		
		while(rs.next()){
			u.setIdUbicazione(rs.getInt("idUbicazione"));
		}
		rs.close();
		st.close();
		conn.close();		
		return u;
	}
	
	public static Ubicazione prendiUbicazione(int idUbicazione) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		Ubicazione u = new Ubicazione();
		ResultSet rs = st.executeQuery("SELECT * FROM ubicazione WHERE idUbicazione="+idUbicazione+" ");
		while(rs.next()){
			u.setIdUbicazione(rs.getInt("idUbicazione"));
			u.setCoordinate(prendiCoordinate(idUbicazione));
			u.setLocAmm(prendiLocAmministrativa(rs.getInt("idComune")));
			u.setLocIdro(prendiLocIdrologica(rs.getInt("idsottobacino")));
			u.setEsposizione(rs.getString("esposizione"));
			u.setQuota(rs.getDouble("quota"));
		}
		rs.close();
		st.close();
		conn.close();
		return u;
	}
	
	public static LocazioneAmministrativa prendiLocAmministrativa(int idComune) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		ResultSet rs = st.executeQuery("select * from comune,provincia,regione,nazione where (comune.idProvincia=provincia.idProvincia) and ( regione.idregione=provincia.idregione) and(regione.idnazione=nazione.idnazione) and idcomune="+idComune+"");
		while(rs.next()){
			locAmm.setIdComune(rs.getInt("idcomune"));
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
		}
		rs.close();
		st.close();
		conn.close();
		return locAmm;
	}
	
	public static ArrayList<LocazioneAmministrativa> prendiLocAmministrativaAll() throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<LocazioneAmministrativa> localizAmm = new ArrayList<LocazioneAmministrativa>();
		ResultSet rs = st.executeQuery("select * from comune,provincia,regione,nazione where (comune.idProvincia=provincia.idProvincia) and ( regione.idregione=provincia.idregione) and(regione.idnazione=nazione.idnazione)");
		while(rs.next()){
				LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
				locAmm.setIdComune(rs.getInt("idcomune"));
				locAmm.setComune(rs.getString("nomecomune"));
				locAmm.setProvincia(rs.getString("nomeprovincia"));
				locAmm.setRegione(rs.getString("nomeregione"));
				locAmm.setNazione(rs.getString("nomenazione"));
				localizAmm.add(locAmm);
		}
		rs.close();
		st.close();
		conn.close();
		return localizAmm;
	}
	
	public static LocazioneIdrologica prendiLocIdrologica(int idSottobacino) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		ResultSet rs = st.executeQuery("select * from bacino,sottobacino where bacino.idbacino=sottobacino.idbacino and idsottobacino="+idSottobacino+"");
		
		while(rs.next()){
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
		}
		rs.close();
		st.close();
		conn.close();
		return locIdro;
	}
	public static ArrayList<LocazioneIdrologica> prendiLocIdrologicaAll() throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<LocazioneIdrologica> locIdrologica = new ArrayList<LocazioneIdrologica>();
		ResultSet rs = st.executeQuery("select * from bacino,sottobacino where sottobacino.idbacino=bacino.idbacino");
		while(rs.next()){
			LocazioneIdrologica locIdro = new LocazioneIdrologica();
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
			locIdrologica.add(locIdro);
		}
		rs.close();
		st.close();
		conn.close();
		return locIdrologica;
	}
	
	
	public static Coordinate prendiCoordinate(int idUbicazione) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		Coordinate coord = new Coordinate();
		ResultSet rs = st.executeQuery("select st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y from ubicazione where idubicazione = "+idUbicazione+"");
		while(rs.next()){
			coord.setX(rs.getDouble("x"));
			coord.setY(rs.getDouble("y"));
		}
		rs.close();
		st.close();
		conn.close();
		return coord;
	}
	
	public static LocazioneAmministrativa cercaLocazioneAmministrativa(String nomecomune) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
		ResultSet rs = st.executeQuery("select * from comune c,provincia p,regione r,nazione n where (c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione)"
				+ "and(r.idnazione=n.idnazione) and c.nomecomune= '"+nomecomune+"'");
		while(rs.next()){
			locAmm.setComune(rs.getString("nomecomune"));
			locAmm.setProvincia(rs.getString("nomeprovincia"));
			locAmm.setRegione(rs.getString("nomeregione"));
			locAmm.setNazione(rs.getString("nomenazione"));
			locAmm.setIdComune(rs.getInt("idcomune"));
		}
		rs.close();
		st.close();
		conn.close();
		return locAmm;
		
	}

	public static LocazioneIdrologica cercaLocazioneIdrologica(String nomesottobacino) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		LocazioneIdrologica locIdro = new LocazioneIdrologica();
		ResultSet rs = st.executeQuery("select * from bacino,sottobacino where bacino.idbacino=sottobacino.idbacino and nomesottobacino='"+nomesottobacino+"'");
		while(rs.next()){
			locIdro.setBacino(rs.getString("nomebacino"));
			locIdro.setIdSottoBacino(rs.getInt("idsottobacino"));
			locIdro.setSottobacino(rs.getString("nomesottobacino"));
		}
		rs.close();
		st.close();
		conn.close();
		return locIdro;
	}
	
	public static int getIdUbicazione(int idProcesso) throws SQLException{
		int id = 0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select u.idubicazione from ubicazione u, processo p where u.idubicazione=p.idubicazione and p.idprocesso="+idProcesso);
		while(rs.next()){
			id = (rs.getInt("idUbicazione"));
		}
		rs.close();
		st.close();
		conn.close();
		return id;
	}
	
	/*
	 * utente
	 */
	
	public static Partecipante salvaUtente(Partecipante p) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		st.executeUpdate("insert into utente(nome,cognome,username,password,ruolo,email,datacreazione,dataultimoaccesso) values "
				+ "('"+p.getNome().replaceAll("'", "''")+"','"+p.getCognome()+"','"+p.getUsername()+"','"+p.getPassword()+"','"+p.getRuolo()+"','"+p.getEmail()+"','"+p.getDataCreazione()+"','"+dataCorrente()+"')");
		ResultSet rs=st.executeQuery("Select idutente from utente where username='"+p.getUsername()+"' and email='"+p.getEmail()+"' ");
		while(rs.next()){
			p.setIdUtente((rs.getInt("idutente")));

		}
		rs.close();
		st.close();
		conn.close();
		return p;
	}
	
	public static Partecipante prendiUtente(String username) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		Partecipante p = new PartecipanteConcreto();
		ResultSet rs=st.executeQuery("select * from utente where username='"+username+"'");
		while(rs.next()){
			switch(rs.getString("ruolo")){
			case "amministratore" : p = new Amministratore(p); break;
			case "avanzato" : p = new UtenteAvanzato(p); break;
			case "base" : p = new UtenteBase(p); break;
			}
			p.setIdUtente(rs.getInt("idutente"));
			p.setCognome(rs.getString("cognome"));
			p.setDataCreazione(rs.getTimestamp("datacreazione"));
			p.setDataUltimoaccesso(rs.getTimestamp("dataultimoaccesso"));
			p.setEmail(rs.getString("email"));
			p.setNome(rs.getString("nome"));
			p.setPassword(rs.getString("password"));
			p.setRuolo(rs.getString("ruolo"));
			p.setUsername(rs.getString("username"));
		}
		rs.close();
		st.close();
		conn.close();
		return p;
	}
	
	public static boolean login(String username,String password) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("select * from utente where username='"+username+"' and password='"+password+"'");
		if(!(rs.next())){
			rs.close();
			st.close();
			conn.close();
			return false;
		}
		else{
			st.executeUpdate("update utente set dataultimoaccesso='"+dataCorrente()+"' where idutente = "+rs.getInt("idutente")+"");
			rs.close();
			st.close();
			conn.close();
			return true;
		}
		
	}
	public static ArrayList<Partecipante> PrendiTuttiUtenti() throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Partecipante> part = new ArrayList<Partecipante>();
		ResultSet rs=st.executeQuery("select * from utente");
		while(rs.next()){
			Partecipante p = new PartecipanteConcreto();
			p.setCognome(rs.getString("cognome"));
			p.setDataCreazione(rs.getTimestamp("datacreazione"));
			p.setDataUltimoaccesso(rs.getTimestamp("dataultimoaccesso"));
			p.setEmail(rs.getString("email"));
			p.setIdUtente(rs.getInt("idutente"));
			p.setNome(rs.getString("nome"));
			p.setPassword(rs.getString("password"));
			p.setRuolo(rs.getString("ruolo"));
			p.setUsername(rs.getString("username"));
			part.add(p);
		}
		rs.close();st.close();conn.close();
		return part;
	}
	public static String dataCorrente(){
		Calendar cal = new GregorianCalendar();
	    int giorno = cal.get(Calendar.DAY_OF_MONTH);
	    int mese = cal.get(Calendar.MONTH)+1;
	    int anno = cal.get(Calendar.YEAR);
	    int ora = cal.get(Calendar.HOUR_OF_DAY);
	    int min = cal.get(Calendar.MINUTE);
	    int sec = cal.get(Calendar.SECOND);
	    return (anno+"-"+mese+"-"+giorno+" "+ora+":"+min+":"+sec);
	}

	
	/*
	 * dati climatici
	 */
	public static void salvaTemperatureAvg(int idStazione,ArrayList<Double> dati,Calendar dataInizio,Calendar dataFine) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		for(Double d:dati){
			while(dataInizio.before(dataFine)){
				st.executeUpdate("INSERT INTO temperatura_avg(idstazionemetereologica,temperaturaavg,data) values("+idStazione+","+d+",'"+dateFormat(dataInizio)+"')");
				dataInizio.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		st.close();
		conn.close();
	}
	
	public static int lettoreCSVT(File f,String tabella,String attributo,int idstazione,Timestamp dataInizio) throws ParseException, IOException, SQLException{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy =";";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		
		double t=0;
		final int batchSize = 1000;
		int count = 0;
		Calendar data = new GregorianCalendar();
		data.setTime(dataInizio);
		try {
			br = new BufferedReader(new FileReader(f));
			try{
			String sql = "insert into "+tabella+"(idstazionemetereologica,"+attributo+",data) values(?,?,?)";
			PreparedStatement insert = conn.prepareStatement(sql);
			while ((line = br.readLine()) != null) {
				String[] med = line.split(cvsSplitBy);
				if(!med[0].equals("NaN")) t=(Double.parseDouble(med[0]));
				else  t=-9999;
				insert.setInt(1, idstazione);
				insert.setDouble(2, t);
				insert.setTimestamp(3, Timestamp.valueOf(dateFormat(data)));
				insert.addBatch();
				
				if(++count % batchSize == 0) {
	        insert.executeBatch();
				}
				data.add(Calendar.DAY_OF_MONTH, 1);
			}
			insert.executeBatch();
		
			insert.close(); conn.close();
			}catch(SQLException ex) {
				System.out.println(ex.getNextException());
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	
	return count;
	}
	public static String dateFormat(Calendar cal){
		String data = ""+cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH)+" 00:00:00.00";
		return data;
	}
	
	/*
	 * query climatiche
	 */

	public static ArrayList<Double> prendiSommaPrecipitazioniMese(String id,String anno,boolean a) throws SQLException{//ok
		ArrayList<Double> dati=new ArrayList<Double>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		if(a==true)  rs=st.executeQuery("select sum(quantita),month from (select sum(quantita) as quantita,EXTRACT(month FROM data) as month from precipitazione" +
				"				 where idstazionemetereologica="+id+" and quantita<>-9999 and EXTRACT(year FROM data)="+anno+" " +
				"				 group by EXTRACT(month FROM data),EXTRACT(year FROM data) having (count(quantita)>20) " +
				"				 order by EXTRACT(month FROM data), EXTRACT(year FROM data) ) as media group by month");
		else  	 rs=st.executeQuery("select sum(quantita),month from (select sum(quantita) as quantita,EXTRACT(month FROM data) as month from precipitazione" +
				"				 where idstazionemetereologica="+id+" and quantita<>-9999 " +
				"				 group by EXTRACT(month FROM data),EXTRACT(year FROM data) having (count(quantita)>20) " +
				"				 order by EXTRACT(month FROM data), EXTRACT(year FROM data) ) as media group by month");

		while(rs.next()){
			double d=rs.getInt("sum");
			dati.add(d);
		}
		rs.close();
		st.close();
		conn.close();
		return dati;
	}
	public static Grafici prendiSommaPrecipitazioniAnnoMensile(String id) throws SQLException{
		Grafici g=new Grafici();
		ArrayList<Double> precipitazioni=new ArrayList<Double>();
		ArrayList<String> anni=new ArrayList<String>();

		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		rs=st.executeQuery("select sum(quantita),EXTRACT(Year FROM data) as anni from precipitazione where idstazionemetereologica="+id+"  and quantita<>-9999 group by EXTRACT(Year FROM data) order by EXTRACT(year FROM data)");

		while(rs.next()){
			precipitazioni.add(rs.getDouble("count"));
			anni.add(String.valueOf(rs.getDouble("anni")));
		}
		rs.close();
		st.close();
		conn.close();
		g.setCategorie(anni);
		g.setY(precipitazioni);
		return g;
	}
	
	public static ArrayList<Double> prendiPrecipitazioniMeseMensile(String id,String anno,String mese) throws SQLException{
		ArrayList<Double> precipitazioni=new ArrayList<Double>();

		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		rs=st.executeQuery("select quantita from precipitazione where quantita<>-9999 and idstazionemetereologica="+id+" and EXTRACT(Year FROM data)="+anno+" and EXTRACT(month FROM data)="+mese+" order by EXTRACT(day FROM data)");

		while(rs.next()){
			precipitazioni.add(rs.getDouble("quantita"));
		}
		rs.close();
		st.close();
		conn.close();
		
		return precipitazioni;
	}
	
	public static ArrayList<Double> prendiPrecipitazioniTrimestreMensile(String id,String anno,String mese) throws SQLException{
		ArrayList<Double> precipitazioni=new ArrayList<Double>();
		int mesefinale=Integer.parseInt(mese)+2;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		rs=st.executeQuery("select distinct EXTRACT(MONTH FROM data),sum(quantita) from precipitazione" +
						   " where quantita<>-9999 and idstazionemetereologica="+id+" and (EXTRACT(MONTH FROM data) )::int BETWEEN "+mese+" AND "+mesefinale+" and extract(year from data)="+anno+"" +
						   "  group by(EXTRACT(MONTH FROM data)) order by  EXTRACT(MONTH FROM data)");

		while(rs.next()){
			precipitazioni.add(rs.getDouble("sum"));
		}
		rs.close();
		st.close();
		conn.close();
		
		return precipitazioni;
	}
	
	public static ArrayList<Double> prendiTemperatureAnno(String id,String anno, boolean a,String tipo) throws SQLException{
			Connection conn = DriverManager.getConnection(url,user,pwd);
			Statement st = conn.createStatement();
			ArrayList<Double> tem= new ArrayList<Double>();
		
			ResultSet rs;
			
			if(a){
				rs =st.executeQuery("SELECT avg(temperatura"+tipo+") as "+tipo+",EXTRACT(MONTH FROM data) FROM temperatura_"+tipo+" WHERE extract(year FROM data)="+anno+" and idstazionemetereologica="+id+" and temperatura"+tipo+"<>-9999 group by EXTRACT(MONTH FROM data) order by EXTRACT(MONTH FROM data)");
			} else 	rs =st.executeQuery("SELECT avg(temperatura"+tipo+") as "+tipo+",EXTRACT(MONTH FROM data) FROM temperatura_"+tipo+" WHERE  idstazionemetereologica="+id+" and temperatura"+tipo+"<>-9999 group by EXTRACT(MONTH FROM data) order by EXTRACT(MONTH FROM data)");
			while(rs.next()){
				tem.add(rs.getDouble(""+tipo+""));
				
			}
			
			rs.close();
			st.close();
			return tem;
		
	}
	
	public static ArrayList<Double> prendiMM(String id,String anno, boolean a,String tipo) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
	
		ResultSet rs;
		
		if(a){
			rs =st.executeQuery("SELECT "+tipo+"(temperatura"+tipo+") as "+tipo+",EXTRACT(MONTH FROM data) FROM temperatura_"+tipo+" WHERE extract(year FROM data)="+anno+" and idstazionemetereologica="+id+" and temperatura"+tipo+"<>-9999 group by EXTRACT(MONTH FROM data) order by  EXTRACT(MONTH FROM data) ");

		} else 	rs =st.executeQuery("SELECT "+tipo+"(temperatura"+tipo+") as "+tipo+",EXTRACT(MONTH FROM data) FROM temperatura_"+tipo+" WHERE  idstazionemetereologica="+id+" and temperatura"+tipo+"<>-9999 group by EXTRACT(MONTH FROM data) order by  EXTRACT(MONTH FROM data) ");

		while(rs.next()){
			tem.add(rs.getDouble(""+tipo+""));
			
		}
		
		rs.close();
		st.close();
		return tem;
	}
	
	
	public static ArrayList<Double> prendiPrecipitazioniTrimestreGiornaliero(String id,String anno,String mese) throws SQLException{
		ArrayList<Double> precipitazioni=new ArrayList<Double>();
		int mesefinale=Integer.parseInt(mese)+2;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		rs=st.executeQuery("select quantita,data from precipitazione where idstazionemetereologica="+id+" and (EXTRACT(MONTH FROM data))::int BETWEEN "+mese+" AND "+mesefinale+" and extract(year from data)="+anno+"");

		while(rs.next()){
			if(rs.getDouble("quantita")==-9999) precipitazioni.add(0.0);
			else precipitazioni.add(rs.getDouble("quantita"));
		}
		rs.close();
		st.close();
		conn.close();
		
		return precipitazioni;
	}
	
	public static ArrayList<Double> prendiSommaPrecipitazioniMeseGiornaliero(String id,String anno) throws SQLException{//ok
		ArrayList<Double> dati=new ArrayList<Double>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs;
		  rs=st.executeQuery("select quantita,data from precipitazione where idstazionemetereologica="+id+" and  extract(year from data)="+anno+"");
		
		while(rs.next()){
			if(rs.getDouble("quantita")==-9999) dati.add(0.0);
			else dati.add(rs.getDouble("quantita"));
		}
		rs.close();
		st.close();
		conn.close();
		return dati;
	}
}
