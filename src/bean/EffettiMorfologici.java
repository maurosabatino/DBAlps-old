package bean;

public class EffettiMorfologici {
	int idEffettiMorfologici;
	String tipo_IT;
	String tipo_ENG;
	
	public EffettiMorfologici(){
		idEffettiMorfologici=0;
		tipo_IT="";
		tipo_ENG="";
	}
	
	public int getIdEffettiMorfoligici(){
		return idEffettiMorfologici;
	}
	public String getTipo_IT(){
		return tipo_IT;
	}
	public String getTipo_ENG(){
		return tipo_ENG;
	}
	public void setIdEffettiMOrfologici(int idEffettiMorfologici){
		this.idEffettiMorfologici = idEffettiMorfologici;
	}
	public void setTipo_IT(String tipo_IT){
		this.tipo_IT=tipo_IT;
	}
	public void setTipo_ENG(String tipo_ENG){
		this.tipo_ENG = tipo_ENG;
	}
	
}
