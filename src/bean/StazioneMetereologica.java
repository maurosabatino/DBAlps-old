package bean;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Ubicazione;

public class StazioneMetereologica {
	public int idStazioneMetereologica;
	public SitoStazioneMetereologica sito;
	public Ente ente;
	public ArrayList<Sensori> sensori;
	public Ubicazione ubicazione;
	public String nome;
	public String aggregazioneGiornaliera;
	public String note;
	public Date dataInizio;
	public Date dataFine;
	public boolean oraria;
	public int idUtente;
	
	public double distanzaProcesso;
	
	
	public StazioneMetereologica(){
		idStazioneMetereologica=0;
		sito=new SitoStazioneMetereologica();
		ubicazione=new Ubicazione();
		nome="";
		ente=new Ente();
		sensori=new ArrayList<Sensori>() ;
		aggregazioneGiornaliera="";
	    note="";
		dataInizio=new Date(0);
		dataFine=new Date(0);
		oraria=false;
		
		distanzaProcesso=-1;
	}
	
	public void setIdStazioneMetereologica(int stazione){
		idStazioneMetereologica=stazione;
		}
	public int getIdStazioneMetereologica(){
		return idStazioneMetereologica;
	}
	
	public void setSito(SitoStazioneMetereologica sito){
		this.sito=sito;
	}
	public SitoStazioneMetereologica getSito(){
		return sito;
	}
	
	
	public void setUbicazione(Ubicazione ubicazione){
		this.ubicazione=ubicazione;
	}
	public Ubicazione getUbicazione(){
		return ubicazione;
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
		return aggregazioneGiornaliera;
	}
	
	
	public void setOraria(boolean oraria){
		this.oraria=oraria;
		}
	public boolean getOraria(){
		return oraria;
	}	
	
	public void setNote(String note){
		this.note=note;
	}
	public String getNote(){
		return note;
	}
	
	public String getVisualizza(){
		
		String out="";
		out+="<p>quota: "+ubicazione.getQuota()+"</p> <p> nome "+getNome()+"";
	
		return out;
	}
	
	public Date getDataInizio(){
		return dataInizio;
	}
	public void setDataInizio(Date data){
		this.dataInizio=data;
	}
	
	public Date getDataFine(){
		return dataFine;
	}
	public void setDataFine(Date data){
		this.dataFine=data;
	}
	
	public void setEnte(Ente ente){
		this.ente=ente;
	}
	public Ente getEnte(){
		return ente;
	}
	
	public void setSensori(ArrayList<Sensori> sensori){
		this.sensori=sensori;
	}
	public ArrayList<Sensori> getSensori(){
		return sensori;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public double getDistanzaProcesso() {
		return distanzaProcesso;
	}

	public void setDistanzaProcesso(double distanzaProcesso) {
		this.distanzaProcesso = distanzaProcesso;
	}
}