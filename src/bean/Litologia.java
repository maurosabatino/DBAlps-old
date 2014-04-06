package bean;

public class Litologia {
	int idLitologia;
	String nomeLitologia_IT;
	String nomeLitologia_ENG;
	

	
	public Litologia(){
		idLitologia=0;
		nomeLitologia_IT="";
		nomeLitologia_ENG="";		
	}
	
	public int getidLitologia(){
		return idLitologia;
	}
	
	public String getNomeLitologia_IT(){
		return nomeLitologia_IT;
	}
	public String getNomeLitologia_ENG(){
		return nomeLitologia_ENG;
	}

	
	
	public void setIdLitologia(int idLitologia){
		this.idLitologia=idLitologia;
	}
	public void setNomeLitologia_IT(String nomeLitologia_IT){
		this.nomeLitologia_IT=nomeLitologia_IT;
	}
	public void setNomeLitologia_ENG(String nomeLitologia_ENG){
		this.nomeLitologia_ENG=nomeLitologia_ENG;
	}
	
}
