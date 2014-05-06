package bean.partecipante;

import java.sql.Timestamp;

public interface Partecipante {
	public int getIdUtente();
	public void setIdUtente(int idUtente);
	public String getNome();
	public void setNome(String nome);
	public String getCognome();
	public void setCognome(String cognome);
	public String getUsername();
	public void setUsername(String username);
	public String getPassword();
	public void setPassword(String password);
	public String getRuolo();
	public void setRuolo(String ruolo);
	public String getEmail();
	public void setEmail(String email);
	public Timestamp getDataCreazione();
	public void setDataCreazione(Timestamp dataCreazione);
	public boolean hasRole(Role r);
	public boolean isDecorated();
}
