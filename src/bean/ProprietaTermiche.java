package bean;

public class ProprietaTermiche {

	int idProprietaTermiche;
	String proprietaTermiche_IT;
	String proprietaTermiche_ENG;
	
	public ProprietaTermiche(){
		idProprietaTermiche=0;
		proprietaTermiche_IT="";
		proprietaTermiche_ENG="";
	}
	
	public int getIdProprieta_termiche(){
		return idProprietaTermiche;
	}
	
	public String getProprieta_termiche_IT(){
		return proprietaTermiche_IT;
	}
	public String getProprieta_termiche_ENG(){
		return proprietaTermiche_ENG;
	}
	
	public void setIdProprietaTermiche(int idProprieta_termiche){
		this.idProprietaTermiche=idProprieta_termiche;
	}
	
	public void setProprietaTermiche_IT(String proprietaTermiche_IT){
		this.proprietaTermiche_IT=proprietaTermiche_IT;
	}
	public void setProprietaTermiche_ENG(String proprietaTermiche_ENG){
		this.proprietaTermiche_ENG=proprietaTermiche_ENG;
	}
}
