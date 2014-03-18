package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import bean.Ubicazione;

public class StazioneMetereologica {
	public int idStazioneMetereologica;
	public int idEnte;
	public int idUbicazione;
	public Ubicazione ubicazioneS;
	public String nome;
	public String aggregazioneGiornaliera;
	public String note;
	public String periodoFunzionamento;
	//public ArrayList<String> sensori;
	//public Sito sito;
	public boolean oraria;
	
	
	public StazioneMetereologica(){
		idStazioneMetereologica=0;
		idEnte=0;
		ubicazioneS=null;
		nome="";
		aggregazioneGiornaliera="";
	    note="";
		periodoFunzionamento="";
		oraria=false;
		//sito=null;
	//	sensori=null;
		
	}
	
	public void setIdStazioneMetereologica(int stazione){
		idStazioneMetereologica=stazione;
		}
	public int getIdStazioneMetereologica(){
		return idStazioneMetereologica;
	}
	
	public void setIdEnte(int ente){
		idEnte=ente;
	}
	public int getIdEnte(){
		return idEnte;
	}
	
	/*public void setIdUbicazione( int ubicazione){
		idUbicazione=ubicazione;
	}
	public int getIdUbicazione(){
		return idUbicazione;
	}
	*/
	public void setUbicazione(Ubicazione ubicazione){
		this.ubicazioneS=ubicazione;
	}
	public Ubicazione getUbicazione(){
		return ubicazioneS;
	}
	
	
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}
	
	public void setAggregazioneGiornaliera(String aggregazione){
		aggregazioneGiornaliera=aggregazione;
	}
	public String getAggregazioneGiornaliera(){
		return nome;
	}
	
	public void setPeriodoFunzionamento(String periodo){
		periodoFunzionamento=periodo;
	}
	public String getPeriodoFunzionamento(){
		return periodoFunzionamento;
	}
	
	public void setOraria(boolean oraria){
		this.oraria=oraria;
		}
	public boolean getOraria(){
		return oraria;
	}	
	/*
	public void setSito(Sito sito){
		this.sito=sito;
	}
	public String getSito(){
		return sito;
	}
	*/
	public void setNote(String note){
		this.note=note;
	}
	public String getNote(){
		return note;
	}
	
	public String getVisualizza(){
		
		String out="";
		out+="<p>quota: "+ubicazioneS.getQuota()+"</p> <p> nome "+getNome()+"";
	
		return out;
	}
	
	/*
	 * sensori
	 */
	
}