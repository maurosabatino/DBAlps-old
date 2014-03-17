package Entità;

public class LocazioneIdrologica {
	int idSottobacino;
	String sottobacino;
	String bacino;
	public LocazioneIdrologica(){
		idSottobacino = 0;
		sottobacino = "sconosciuto";
		bacino = "sconosciuto";
	}
	public int getIdSottobacino(){
		return idSottobacino;
	}
	public String getSottobacino(){
		return sottobacino;
	}
	public String getBacino(){
		return bacino;
	}
	public void setIdSottoBacino(int idSottobacino){
		
	}
	public void setSottobacino(String sottobacino){
		this.sottobacino = sottobacino;
	}
	public void setBacino(String bacino){
		this.bacino = bacino;
	}
	
	public String toString(){
		String out = "<p> bacino: "+bacino+"</p> <p>sottobacino: "+sottobacino+"</p>";
		return out;
	}
}
