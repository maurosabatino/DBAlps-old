package bean;

public class Litologia {
	int idLitologia;
	String nomeLitologia;
	int idProprietaTermiche;
	String proprietaTermiche;
	int idStatoFratturazione;
	String statoFratturazione;
	
	public Litologia(){
		idLitologia=0;
		nomeLitologia="";
		idProprietaTermiche=0;
		proprietaTermiche="";
		idStatoFratturazione=0;
		statoFratturazione="";
	}
	
	public int getidLitologia(){
		return idLitologia;
	}
	
	public String getNomeLitologia(){
		return nomeLitologia;
	}
	public int getIdProprieta_termiche(){
		return idProprietaTermiche;
	}
	public String getProprieta_termiche(){
		return proprietaTermiche;
	}
	public int getIdStato_fratturazione(){
		return idStatoFratturazione;
	}
	public String getStato_fratturazione(){
		return statoFratturazione;
	}
	
	public void setIdLitologia(int idLitologia){
		this.idLitologia=idLitologia;
	}
	public void setNomeLitologia(String nomeLitologia){
		this.nomeLitologia=nomeLitologia;
	}
	public void setIdProprietaTermiche(int idProprieta_termiche){
		this.idProprietaTermiche=idProprieta_termiche;
	}
	public void setProprietaTermiche(String proprietaTermiche){
		this.proprietaTermiche=proprietaTermiche;
	}
	public void setIdStatoFratturazione(int idStatoFratturazione){
		this.idStatoFratturazione=idStatoFratturazione;
	}
	public void setStatoFratturazione(String statoFratturazione){
		this.statoFratturazione=statoFratturazione;
	}
}
