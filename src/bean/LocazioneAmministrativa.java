package bean;

public class LocazioneAmministrativa {
	int idComune;
	String comune;
	String provincia;
	String regione;
	String nazione;
	
	public LocazioneAmministrativa(){
		idComune = 0;
		comune = "sconosciuto";
		provincia = "sconosciuto";
		regione = "sconosciuto";
		nazione = "sconosciuto";
	}
	public int getIdComune(){
		return idComune;
	}
	public String getComune(){
		return comune;
	}
	public String getProvincia(){
		return provincia;
	}
	public String getRegione(){
		return regione;
	}
	public String getNazione(){
		return nazione;
	}
	public void setIdComune(int idComune){
		this.idComune = idComune;
	}
	public void setComune(String comune){
		this.comune = comune;
	}
	public void setProvincia(String provincia){
		this.provincia = provincia;
	}
	public void setRegione(String regione){
		this.regione = regione;
	}
	public void setNazione(String nazione){
		this.nazione = nazione;
	}
	public String toString(){
		String out = "LocazioneAmministrativa:[comune= "+comune+" provincia= "+provincia+"  regione="+regione+"  nazione="+nazione+"]";
		return out;
	}
	public boolean isEmpty(){
		if((comune == "" ||comune=="sconosciuto") && (provincia == ""||provincia=="sconosciuto") &&	(regione == "" || regione=="sconosciuto") &&	(nazione == "" || nazione=="sconosciuto"))
			return true;
			else return false;
		
	}
}
