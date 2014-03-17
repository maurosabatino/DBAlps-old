package bean;

public class Ubi {
	int idUbicazione;
	Double quota;
	
	public Ubi(){
		idUbicazione = 0;
		quota = 0.0;
	}
	public int getIdUbicazione(){
		return idUbicazione;
	}
	public Double getQuota(){
		return quota;
	}
	public void setIdUbicazione (int idUbicazione){
		this.idUbicazione = idUbicazione;
	}
	public void setQuota(Double quota){
		this.quota = quota;
	}
	
public String getVisualizza(){
		
		String out="";
		out+="<p>"+quota+"</p>";
		System.out.println(quota);
		return out;
	}
}
