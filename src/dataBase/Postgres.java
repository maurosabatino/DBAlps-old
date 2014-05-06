package dataBase;


import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.Random;


import bean.Coordinate;
import bean.Ente;
import bean.LocazioneAmministrativa;
import bean.LocazioneIdrologica;
import bean.Sensori;
import bean.SitoStazioneMetereologica;
import bean.StazioneMetereologica;
import bean.Ubicazione;
import controller.ControllerDatabase;
public class Postgres {
	public static void main(String [] args){
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";

		try{
			Connection conn = DriverManager.getConnection(url,user,pwd);
			//stazioni(conn);
			insertDatiClimaticiGiornalieri(conn);
			
			conn.close();
		} catch (SQLException e) {System.out.println(e.getMessage());}
	}
	
	
	
	public static void stazioni(Connection conn)throws SQLException{
		Statement st = conn.createStatement();
		Random r = new Random();
		String[] sensoriScelti ={"pioggia","neve"};
		for(int i =1002; i<2000;i++){
		Ubicazione u = ControllerDatabase.salvaUbicazione(creaUbicazione(randBetweenDouble(0, 50), randBetweenDouble(0, 50), "nord",randBetweenDouble(500, 4000), 3, 1));
		StazioneMetereologica s = creaStazione("prova"+i, "giornaliera", "note", dateRandom(), Timestamp.valueOf("0001-01-01 00:00:00"), true, 1, "spartiacque", "IT", 4, "ARPA abruzzo", sensoriScelti,1);
		s.setUbicazione(u);
		ControllerDatabase.salvaStazione(s);
		}
		st.close();
	}
	
		public static Ubicazione creaUbicazione(Double x,Double y,String esposizione,Double quota,int idComune,int idSottobacino) throws SQLException{
			Ubicazione u = new Ubicazione();
			Coordinate coord = new Coordinate();
			LocazioneAmministrativa locAmm = new LocazioneAmministrativa();
			LocazioneIdrologica locIdro = new LocazioneIdrologica(); 
			coord.setX(x);
			coord.setY(y);
			u.setCoordinate(coord);
			u.setEsposizione(esposizione);
			u.setQuota(quota);
			locAmm=ControllerDatabase.prendiLocAmministrativa(idComune);
			u.setLocAmm(locAmm);
			locIdro=ControllerDatabase.prendiLocIdrologica(idSottobacino);
			u.setLocIdro(locIdro);
			return u;
		}
		
		public static StazioneMetereologica creaStazione(String nome,String aggregazione,String note,Timestamp dataInizio,Timestamp dataFine,Boolean oraria,int idSito,String caratteristiche_,String loc,int idEnte,String nomeEnte,String[] sensoriScelti,int idUtente) throws SQLException{
			StazioneMetereologica s=new StazioneMetereologica();
			s.setNome(nome);
			s.setAggregazioneGiornaliera(aggregazione);
			s.setNote(note);
			s.setDataInizio(dataInizio);
			if(dataFine.equals("")) s.setDataFine(Timestamp.valueOf("0001-01-01 00:00:00"));
			else{
				s.setDataFine(dataFine);
			}
			s.setOraria(oraria);
			s.setSito(creaSitoStazione(idSito, caratteristiche_, loc));
			s.setEnte(creaEnte(idEnte, nomeEnte));
			s.setSensori(creaSensori(sensoriScelti, loc));
			s.setIdUtente(idUtente);
			return s;
		}
		
		public static SitoStazioneMetereologica creaSitoStazione(int idSito,String caratteristiche_,String loc){
			SitoStazioneMetereologica s=new SitoStazioneMetereologica();
		   s.setIdSitoStazioneMetereologica(idSito);
		   if(loc.equals("IT")) s.setCaratteristiche_IT(caratteristiche_+""+loc);
		   else s.setCaratteristiche_ENG(caratteristiche_+""+loc);
		   return s;
	}
	
	public static Ente creaEnte(int idEnte,String nome){
		Ente ente=new Ente();
		ente.setEnte(nome);
		ente.setIdEnte(idEnte);
		return ente;
	}
	
	public static ArrayList<Sensori> creaSensori(String[] sensoriScelti,String loc) throws SQLException{
		ArrayList<Sensori> sensori=new ArrayList<Sensori>();
		int n=0;
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
		
		return sensori;
	}
	
	public static Timestamp dateRandom(){
		Timestamp dataRandom = new Timestamp(0); 
		GregorianCalendar gc = new GregorianCalendar();
		
		int year = randBetween(1900, 2010);
		gc.set(Calendar.YEAR, year);
		int month = randBetween(1, 12);
		gc.set(Calendar.MONTH, month);
		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		
		System.out.println(gc.get(Calendar.YEAR)+ "-" +month+ "-" +gc.get(Calendar.DAY_OF_MONTH)+" 00:00:00.00");
		
		dataRandom = Timestamp.valueOf(gc.get(Calendar.YEAR) + "-" + month + "-" + gc.get(Calendar.DAY_OF_MONTH)+" 00:00:00.00");
	
		return dataRandom;
	}
	public static String dateFormat(Calendar cal){
		String data = ""+cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH)+" 00:00:00.00";
		return data;
	}
	public static void insertDatiClimaticiGiornalieri(Connection conn) throws SQLException{
		Statement st = conn.createStatement();
		ArrayList<StazioneMetereologica> stazioni = ControllerDatabase.prendiTutteStazioniMetereologiche();
		
		for(int i =25;i<stazioni.size();i++){
			StringBuilder temperatura_avg = new StringBuilder();
			StringBuilder temperatura_max = new StringBuilder();
			StringBuilder temperatura_min = new StringBuilder();
			StringBuilder neve = new StringBuilder();
			StringBuilder vento = new StringBuilder();
			StringBuilder radsol = new StringBuilder();
			StringBuilder rain = new StringBuilder();
			StringBuilder precipitazione = new StringBuilder();
			Calendar data = new GregorianCalendar();
			Calendar dataFine = new GregorianCalendar();
			data.setTime(stazioni.get(i).getDataInizio());
			dataFine.setTime(Timestamp.valueOf("2010-12-31 00:00:00.00"));
			System.out.println("iniziato stazione:"+i+" con data di inizio: "+dateFormat(data));
			while(data.compareTo(dataFine)<=0){
				if((data.compareTo(dataFine)<0)){
					temperatura_avg.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"'),");
					temperatura_max.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"'),");
					temperatura_min.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"'),");
					neve.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"'),");
					vento.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"'),");
					radsol.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"'),");
					rain.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"'),");
					precipitazione.append("("+i+","+randBetweenDouble(0, 1000)+",'"+dateFormat(data)+"'),");
				}else{
					temperatura_avg.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"');");
					temperatura_max.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"');");
					temperatura_min.append("("+i+","+randBetweenDouble(-20, 40)+",'"+dateFormat(data)+"');");
					neve.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"');");
					vento.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"');");
					radsol.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"');");
					rain.append("("+i+","+randBetweenDouble(0, 400)+",'"+dateFormat(data)+"');");
					precipitazione.append("("+i+","+randBetweenDouble(0, 1000)+",'"+dateFormat(data)+"');");
				}
				data.add(Calendar.HOUR_OF_DAY, 1);
			}
			
			st.executeUpdate("INSERT INTO temperatura_avg(idstazionemetereologica,temperaturaavg,data) values "+temperatura_avg.toString()+"");
			st.executeUpdate("INSERT INTO temperatura_max(idstazionemetereologica,temperaturamax,data) values "+temperatura_max.toString()+"");
			st.executeUpdate("INSERT INTO temperatura_min(idstazionemetereologica,temperaturamin,data) values "+temperatura_min.toString()+"");
			st.executeUpdate("INSERT INTO neve(idstazionemetereologica,neve,data) values "+neve.toString()+"");
			st.executeUpdate("INSERT INTO vento(idstazionemetereologica,velocita,data) values "+vento.toString()+"");
			st.executeUpdate("INSERT INTO radsol(idstazionemetereologica,quantita,data) values "+radsol.toString()+"");
			st.executeUpdate("INSERT INTO rain(idstazionemetereologica,quantita,data) values "+rain.toString()+"");
			st.executeUpdate("INSERT INTO precipitazione(idstazionemetereologica,quantita,data) values "+precipitazione+"");
			
			System.out.println("finita stazione: "+i);
		}
		st.close();
		conn.close();
	}
	public static double randBetweenDouble(double start,double end){
		return start + (double)Math.round(Math.random() * (end - start));
	}
	public static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}
}
