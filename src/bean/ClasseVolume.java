package bean;

public class ClasseVolume {
	int idClasseVolume;
	String intervallo;
	
	public ClasseVolume(){
		idClasseVolume=0;
		intervallo = "";
	}

	public int getIdClasseVolume() {
		return idClasseVolume;
	}

	public void setIdClasseVolume(int idClasseVolume) {
		this.idClasseVolume = idClasseVolume;
	}

	public String getIntervallo() {
		return intervallo;
	}

	public void setIntervallo(String intervallo) {
		this.intervallo = intervallo;
	}
}
