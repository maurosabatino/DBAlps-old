package bean;

public class SitoProcesso {
	int idSito; 
	String caratteristica;
	Litologia litologia;
	public SitoProcesso(){
		idSito = 0;
		caratteristica = "";
		litologia = null;
	}
	int getIdSito(){
		return idSito;
	}
	String getCaratteristica(){
		return caratteristica;
	}
	Litologia getLitologia(){
		return litologia;
	}
	void setIdSito(int idSito){
		this.idSito = idSito;
	}
	void setCaratteristica(String caratteristica){
		this.caratteristica = caratteristica;
	}
	void setLitologia(Litologia litologia){
		this.litologia = litologia;
	}
}
