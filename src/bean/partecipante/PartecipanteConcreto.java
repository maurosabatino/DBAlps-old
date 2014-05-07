package bean.partecipante;

import java.sql.Timestamp;

public class PartecipanteConcreto implements Partecipante {
	int idUtente;
	String nome;
	String cognome;
	String username;
	String password;
	String ruolo;
	String email;
	Timestamp dataCreazione;
	Timestamp dataUltimoaccesso;
	Timestamp ultimoAccesso;
	
	
	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente=idUtente;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome=cognome;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(String ruolo) {
		this.ruolo=ruolo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione=dataCreazione;
	}
	
	public boolean hasRole(Role r) {
		return false;
	}
	
	public boolean isDecorated() {
		return false;
	}

	public Timestamp getDataUltimoaccesso() {
		return dataUltimoaccesso;
	}

	public void setDataUltimoaccesso(Timestamp dataUltimoaccesso) {
		this.dataUltimoaccesso = dataUltimoaccesso;
	}

}
