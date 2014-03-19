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
		String out = "<p>comune: "+comune+"</p> <p>provincia: "+provincia+"</p> <p> regione:"+regione+"</p> <p> nazione"+nazione+"</p>";
		return out;
	}
	public boolean isEmpty(){
		if(comune == "" && provincia == "" &&	regione == "" &&	nazione == "")
			return true;
			else return false;
		
	}
}
