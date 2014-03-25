package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bean.*;

public class ControllerDatabase {
	static String url = "jdbc:postgresql://localhost:5432/DBAlps";
	static String user = "admin";
	static String pwd = "dbalps";
	
	
	
	/**
	 * Processo
	 */
	
	public static Processo salvaProcesso(Processo p) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append(""+p.getUbicazione().getIdUbicazione());
		sb.append(",1");
		sb.append(",'"+p.getNome()+"'");
		sb.append(",'"+p.getData()+"'");
		sb.append(",'"+p.getDescrizione()+"'");
		sb.append(",'"+p.getNote()+"'");
		sb.append(","+p.getAltezza());
		sb.append(","+p.getLarghezza());
		sb.append(","+p.getSuperficie());
		sb.append(","+p.getVolumeSpecifico());
		
		st.executeUpdate("INSERT INTO processo(idUbicazione,idSito,nome,data,descrizione,note,altezza,larghezza,superficie,volumespecifico) values("+sb.toString()+")");
		
		ResultSet rs = st.executeQuery("SELECT * FROM processo WHERE idUbicazione="+p.getUbicazione().getIdUbicazione()+" AND idSito=1 AND nome='"+p.getNome()+"' AND data='"+p.getData()+"' ");
		while(rs.next()){
			p.setIdprocesso(rs.getInt("idProcesso"));
		}
		st.close();
		conn.close();
		return p;
		//qui salvo nel db e mi devo andare a ricavare l'id del processo
	}
	
	
	public static Processo prendiProcesso(int idProcesso) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		Processo p = new Processo();
		ResultSet rs = st.executeQuery("SELECT * FROM processo WHERE idProcesso="+idProcesso+" ");
		while(rs.next()){
			p.setIdprocesso(rs.getInt("idProcesso"));
			p.setNome(rs.getString("nome"));
			p.setData(rs.getTimestamp("data"));
			p.setDescrizione(rs.getString("descrizione"));
			p.setNote(rs.getString("note"));
			p.setAltezza(rs.getDouble("altezza"));
			p.setLarghezza(rs.getDouble("larghezza"));
			p.setSuperficie(rs.getDouble("superficie"));
			Ubicazione u = prendiUbicazione(rs.getInt("idUbicazione"));
			p.setUbicazione(u);
		}
		return p;
	}
	
	public static ArrayList<Processo> prendiTuttiProcessi() throws SQLException{
		ArrayList<Processo> al = new ArrayList<Processo>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM processo");
		while(rs.next()){
			Processo p = new Processo();
			p.setIdprocesso(rs.getInt("idProcesso"));
			p.setNome(rs.getString("nome"));
			p.setData(rs.getTimestamp("data"));
			p.setDescrizione(rs.getString("descrizione"));
			p.setNote(rs.getString("note"));
			p.setAltezza(rs.getDouble("altezza"));
			p.setLarghezza(rs.getDouble("larghezza"));
			p.setSuperficie(rs.getDouble("superficie"));
			Ubicazione u = prendiUbicazione(rs.getInt("idUbicazione"));
			p.setUbicazione(u);
			al.add(p);
		}
		return al;
	}
	
	public static ArrayList<Processo> ricercaProcesso(Processo p,Ubicazione u) throws SQLException{
		ArrayList<Processo> al = new ArrayList<Processo>();
		StringBuilder sb = new StringBuilder();
		StringBuilder su = new StringBuilder();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		if(!(p.getAltezza()==null || p.getAltezza()==0)){
			sb.append(" where altezza="+p.getAltezza()+"");
		}
		
		if(!(p.getNome()==null || p.getNome().equals(""))){
			if(sb.toString().equals("") || sb == null)
				sb.append(" where nome="+p.getNome()+"");
			else
				sb.append(" and nome="+p.getNome()+"");
		}
		if(!(p.getSuperficie()==null || p.getSuperficie()==0)){
			if(sb.toString().equals("") || sb == null)
				sb.append(" where superficie="+p.getSuperficie()+"");
			else
				sb.append(" and superficie="+p.getSuperficie()+"");
		}
		if(!(p.getLarghezza()==null || p.getLarghezza()==0)){
			if(sb.toString().equals("") || sb == null)
				sb.append(" where larghezza="+p.getLarghezza()+"");
			else
				sb.append(" and larghezza="+p.getLarghezza()+"");
		}
		if(!(p.getData()==null)){
			if(sb.toString().equals("") || sb == null){
				sb.append(" where data='"+p.getData()+"'");
			}
			else
				sb.append(" and data='"+p.getData()+"'");
		}
		//da fare ancora i volumi e le date
		/*
		 * 
		 */
		if(sb.toString().equals("") || sb == null)
		su.append("where idubicazione in(SELECT idubicazione FROM ubicazione u,comune c, provincia p, regione r, nazione n   "
				+ "where  u.idcomune=c.idcomune and c.idprovincia=p.idprovincia and p.idregione=r.idregione and n.idnazione=r.idnazione");
		else
			su.append(" and idubicazione in(SELECT idubicazione FROM ubicazione u,comune c, provincia p, regione r, nazione n   "
					+ "where  u.idcomune=c.idcomune and c.idprovincia=p.idprovincia and p.idregione=r.idregione and n.idnazione=r.idnazione");
		if(!(u.getLocAmm().getComune()==null || u.getLocAmm().getComune().equals(""))){
			System.out.println("comune= "+u.getLocAmm().getComune());
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
		su.append(")");
		
		ResultSet rs = null;
		
		if(u.isEmpty()==true){
			System.out.println(sb.toString());
		 rs = st.executeQuery("SELECT * FROM processo  "+sb.toString()+" ");
		}
		else {
			rs = st.executeQuery("SELECT * FROM processo  "+sb.toString()+" "+su.toString()+" ");
		}
		while(rs.next()){
			Processo ps = new Processo();
			ps.setIdprocesso(rs.getInt("idProcesso"));
			ps.setNome(rs.getString("nome"));
			ps.setData(rs.getTimestamp("data"));
			ps.setDescrizione(rs.getString("descrizione"));
			ps.setNote(rs.getString("note"));
			ps.setAltezza(rs.getDouble("altezza"));
			ps.setLarghezza(rs.getDouble("larghezza"));
			ps.setSuperficie(rs.getDouble("superficie"));
			Ubicazione ub = prendiUbicazione(rs.getInt("idUbicazione"));
			ps.setUbicazione(ub);
			al.add(ps);
		}
		
		
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
		if(!(p.getDescrizione()==null || p.getDescrizione().equals(""))){
			sb.append("descrizione = '"+p.getDescrizione()+"'");
		}
		sb.append("where idProcesso="+p.getIdProcesso());
		System.out.println("query: "+sb.toString());
		st.executeUpdate(""+sb.toString());
		
		/*
		 * modifica dell'ubicazione
		 */
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
			System.out.println(su.toString());
			st.executeUpdate(""+su.toString());
		}
		
	}
	
	/*
	 * stazione metereologica
	 */
	public static void salvaStazione(StazioneMetereologica s)throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb=new StringBuilder();
		sb.append("1");//idEnte
		sb.append(",'"+s.getNome()+"'");
		sb.append(",'"+s.getAggregazioneGiornaliera()+"'");
		sb.append(","+s.getOraria());
		sb.append(",'"+s.getNote()+"'");
		sb.append(",'"+s.getPeriodoFunzionamento()+"'");
		sb.append(",1");//idsito
		sb.append(","+s.getUbicazione().getIdUbicazione());
		
		 st.executeUpdate("INSERT INTO STAZIONE_METEREOLOGICA(IDENTE,NOME,AGGREGAZIONEGIORNALIERA,ORARIA,NOTE,PERIODOFUNZIONAMENTO,idSitoStazione,IDUBICAZIONE) VALUES( "+sb+")");
		
		 ResultSet rs =st.executeQuery("SELECT * FROM STAZIONE_METEREOLOGICA WHERE  idUbicazione="+s.getUbicazione().getIdUbicazione()+" and periodofunzionamento="+s.getPeriodoFunzionamento()+" ");
		while(rs.next()) 
			s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));

		 st.close(); conn.close();
	}
	
	public static StazioneMetereologica prendiStazioneMetereologica(int idStazioneMetereologica)throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM STAZIONE_METEREOLOGICA WHERE IDstazionemetereologica="+idStazioneMetereologica+"");
		StazioneMetereologica s=new StazioneMetereologica();
		while(rs.next()){
			s.setIdStazioneMetereologica(idStazioneMetereologica);
			s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
			s.setNote(rs.getString("note"));
			s.setOraria(rs.getBoolean("oraria"));
			s.setPeriodoFunzionamento(rs.getString("periodoFunzionamento"));
			s.setNome(rs.getString("nome"));
			Ubicazione u = prendiUbicazione(rs.getInt("idUbicazione"));
			s.setUbicazione(u);
			//s.setIDEnte();
			//s.setSitoStazione;
		}
		
	    st.close(); conn.close();
	    return s;
	}
	
	public static ArrayList<StazioneMetereologica> prendiTutteStazioniMetereologiche() throws SQLException{
		ArrayList<StazioneMetereologica> al = new ArrayList<StazioneMetereologica>();
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM stazione_metereologica");
		while(rs.next()){
			StazioneMetereologica s=new StazioneMetereologica();
			s.setIdStazioneMetereologica(rs.getInt("idStazioneMetereologica"));
			s.setAggregazioneGiornaliera(rs.getString("aggregazioneGiornaliera"));
			s.setNote(rs.getString("note"));
			s.setOraria(rs.getBoolean("oraria"));
			s.setPeriodoFunzionamento(rs.getString("periodoFunzionamento"));
			s.setNome(rs.getString("nome"));
			Ubicazione u = prendiUbicazione(rs.getInt("idUbicazione"));
			s.setUbicazione(u);
			al.add(s);
		}
		return al;
			
	}
	
	
	/*
	 * ubicazione
	 */
	
	public static Ubicazione salvaUbicazione(Ubicazione u) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append("1");
		sb.append(",1");
		sb.append(","+u.getQuota());
		sb.append(",'"+u.getEsposizione()+"'");
		sb.append(",ST_GeometryFromText("+u.getCoordinate().toDB()+")");
		st.executeUpdate("INSERT INTO ubicazione(idSottobacino,idComune,quota,esposizione,coordinate) values("+sb.toString()+") ");
		
		ResultSet rs = st.executeQuery("SELECT * FROM ubicazione WHERE coordinate= "+u.getCoordinate().toDB()+" ");
		
		//TODO fare in una sola riga selezionando solo l'id
		while(rs.next()){
			u.setIdUbicazione(rs.getInt("idUbicazione"));
		}
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
		st.close();
		conn.close();
		return locAmm;
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
		st.close();
		conn.close();
		return locIdro;
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
		return id;
	}
}
