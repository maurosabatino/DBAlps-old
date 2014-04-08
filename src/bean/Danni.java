package bean;

public class Danni {
	int idDanni;
	String tipo_IT;
	String tipo_ENG;
	
	public Danni(){
		idDanni=0;
		tipo_IT="";
		tipo_ENG="";
	}
	
	public String getTipo_IT(){
		return tipo_IT;
	}
	public String getTipo_ENG(){
		return tipo_ENG;
	}
	
	
	public void setTipo_IT(String tipo_IT){
		this.tipo_IT=tipo_IT;
	}
	public void setTipo_ENG(String tipo_ENG){
		this.tipo_ENG = tipo_ENG;
	}

	public int getIdDanni() {
		return idDanni;
	}

	public void setIdDanni(int idDanni) {
		this.idDanni = idDanni;
	}
}
