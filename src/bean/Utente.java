package bean;

import java.sql.Timestamp;
import java.util.Date;

public class Utente {
	int idUtente;
	String nome;
	String cognome;
	String username;
	String password;
	String email;
	String ruolo;
	Timestamp dataCreazione;
	Timestamp dataultimoAccesso;
	
	public Utente(){
		idUtente=0;
		nome="";
		cognome="";
		username="";
		password="";
		email="";
		ruolo="";
		dataCreazione=new Timestamp(0);
		dataultimoAccesso=new Timestamp(0);
	}
	
	public void setIdUtente(int id){
		idUtente=id;
	}
	public int getIdUtente(){
		return idUtente;
	}
	
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}
	
	public void setCognome(String cognome){
		this.cognome=cognome;
	}
	public String getCognome(){
		return cognome;
	}
	
	public void setUsername(String user){
		username=user;
	}
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}
	
	public void setRuolo(String ruolo){
		this.ruolo=ruolo;
	}
	public String getRuolo(){
		return ruolo;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp creazione) {
		this.dataCreazione = creazione;
	}

	public Timestamp getDataUltimoAccesso() {
		return dataultimoAccesso;
	}

	public void setDataUltimoAccesso(Timestamp ultimoAccesso) {
		this.dataultimoAccesso = ultimoAccesso;
	}
	
	public String getstampautente(){
		System.out.println("nome"+nome);
		String s="nome="+nome;
		return s;
	}
	
}