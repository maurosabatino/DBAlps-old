package bean;

public class SitoProcesso {
	int idSito; 
	String caratteristicaSito_IT;
	String caratteristicaSito_ENG;
	public SitoProcesso(){
		idSito = 0;
		caratteristicaSito_IT = "";
		caratteristicaSito_ENG="";
	}
	public int getIdSito(){
		return idSito;
	}
	public void setIdSito(int idSito){
		this.idSito = idSito;
	}
	public String getCaratteristicaSito_IT() {
		return caratteristicaSito_IT;
	}
	public void setCaratteristicaSito_IT(String caratteristicaSito_IT) {
		this.caratteristicaSito_IT = caratteristicaSito_IT;
	}
	public String getCaratteristicaSito_ENG() {
		return caratteristicaSito_ENG;
	}
	public void setCaratteristicaSito_ENG(String caratteristicaSito_ENG) {
		this.caratteristicaSito_ENG = caratteristicaSito_ENG;
	}
	
	
}
