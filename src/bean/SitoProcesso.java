package bean;

public class SitoProcesso {
	int idSito; 
	String caratteristicaSito;
	Litologia litologia;
	public SitoProcesso(){
		idSito = 0;
		caratteristicaSito = "";
		litologia = null;
	}
	public int getIdSito(){
		return idSito;
	}
	public String getCaratteristica(){
		return caratteristicaSito;
	}
	public Litologia getLitologia(){
		return litologia;
	}
	public void setIdSito(int idSito){
		this.idSito = idSito;
	}
	public void setCaratteristicaSito(String caratteristicaSito){
		this.caratteristicaSito = caratteristicaSito;
	}
	public void setLitologia(Litologia litologia){
		this.litologia = litologia;
	}
}
