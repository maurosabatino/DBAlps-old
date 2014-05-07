package bean.partecipante;

import java.sql.Timestamp;

public class Amministratore implements Ruolo{

	/**
	 * @param partecipante
	 */
	public Amministratore(Partecipante partecipante) {
		this.partecipante = partecipante;
	}

	Partecipante partecipante;
	
	public int getIdUtente() {
		return partecipante.getIdUtente();
	}

	public void setIdUtente(int idUtente) {
		partecipante.setIdUtente(idUtente);
	}

	public String getNome() {
		return partecipante.getNome();
	}

	public void setNome(String nome) {
		partecipante.setNome(nome);
	}

	public String getCognome() {
		return partecipante.getCognome();
	}

	public void setCognome(String cognome) {
		partecipante.setCognome(cognome);
	}

	public String getUsername() {
		return partecipante.getUsername();
	}

	public void setUsername(String username) {
		partecipante.setUsername(username);
	}

	public String getPassword() {
		return partecipante.getPassword();
	}

	public void setPassword(String password) {
		partecipante.setPassword(password);
	}
	
	public String getRuolo() {
		return partecipante.getRuolo();
	}

	public void setRuolo(String ruolo) {
		partecipante.setRuolo(ruolo);
	}

	public String getEmail() {
		return partecipante.getEmail();
	}

	public void setEmail(String email) {
		partecipante.setEmail(email);
	}
	public Timestamp getDataCreazione() {
		return partecipante.getDataCreazione();
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		partecipante.setDataCreazione(dataCreazione);
	}

	public Timestamp getDataUltimoaccesso(){
		return partecipante.getDataUltimoaccesso();
	}
	public void setDataUltimoaccesso(Timestamp dataUltimoaccesso){
		partecipante.setDataUltimoaccesso(dataUltimoaccesso);
	}
	
	public Partecipante undecorate() {
		if(partecipante.isDecorated()) return ((Ruolo)partecipante).undecorate();
		return partecipante;
	}

	public Partecipante undecorate(Role r) {
		if(r==Role.AMMINISTRATORE) return partecipante;
		if(partecipante.isDecorated()) partecipante = ((Ruolo)partecipante).undecorate(r);
		return this;
	}

	public boolean hasRole(Role r) {
		if(r==Role.AMMINISTRATORE) return true;
		return partecipante.hasRole(r);
	}

	public boolean isDecorated() {
		return true;
	}

}
