package bean;

public class SitoStazione {
	int idSito; 
	String caratteristica;
	
	public SitoStazione(){
		idSito = 0;
		caratteristica = "";
		
	}
	int getIdSito(){
		return idSito;
	}
	String getCaratteristica(){
		return caratteristica;
	}
	
	void setIdSito(int idSito){
		this.idSito = idSito;
	}
	void setCaratteristica(String caratteristica){
		this.caratteristica = caratteristica;
	}
	
}
