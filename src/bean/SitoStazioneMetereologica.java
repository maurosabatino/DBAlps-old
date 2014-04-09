package bean;

public class SitoStazioneMetereologica {
	
	public int idSitoStazioneMetereologica;
	public String caratteristiche_IT;
	public String caratteristiche_ENG;
	
	public SitoStazioneMetereologica(){
		idSitoStazioneMetereologica=0;
		caratteristiche_ENG="";
		caratteristiche_IT="";
	}

	public int getIdSitoStazioneMetereologica() {
		return idSitoStazioneMetereologica;
	}

	public void setIdSitoStazioneMetereologica(int idStazioneMetereologica) {
		this.idSitoStazioneMetereologica = idStazioneMetereologica;
	}

	public String getCaratteristiche_IT() {
		return caratteristiche_IT;
	}

	public void setCaratteristiche_IT(String caratteristiche_IT) {
		this.caratteristiche_IT = caratteristiche_IT;
	}

	public String getCaratteristiche_ENG() {
		return caratteristiche_ENG;
	}

	public void setCaratteristiche_ENG(String caratteristiche_ENG) {
		this.caratteristiche_ENG = caratteristiche_ENG;
	}
	
	

}
