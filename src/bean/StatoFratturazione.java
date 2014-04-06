package bean;

public class StatoFratturazione {
	int idStatoFratturazione;
	String statoFratturazione_IT;
	String statoFratturazione_ENG;
	
	public StatoFratturazione(){
		idStatoFratturazione=0;
		statoFratturazione_IT="";
		statoFratturazione_ENG="";
	}
	
	public int getIdStato_fratturazione(){
		return idStatoFratturazione;
	}
	public String getStato_fratturazione_IT(){
		return statoFratturazione_IT;
	}
	public String getStato_fratturazione_ENG(){
		return statoFratturazione_ENG;
	}
	public void setIdStatoFratturazione(int idStatoFratturazione){
		this.idStatoFratturazione=idStatoFratturazione;
	}
	public void setStatoFratturazione_IT(String statoFratturazione_IT){
		this.statoFratturazione_IT=statoFratturazione_IT;
	}
	public void setStatoFratturazione_ENG(String statoFratturazione_ENG){
		this.statoFratturazione_ENG=statoFratturazione_ENG;
	}
}
