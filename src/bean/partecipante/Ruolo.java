package bean.partecipante;

public interface Ruolo extends Partecipante {
	public Partecipante undecorate();
	public Partecipante undecorate(Role r);
	
	public boolean hasRole(Role r);
	public boolean isDecorated();
}
