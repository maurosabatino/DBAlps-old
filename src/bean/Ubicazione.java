package bean;


public class Ubicazione {

	int idUbicazione;
	LocazioneIdrologica locIdr;
	LocazioneAmministrativa locAmm;
	Double quota;
	String esposizione;
	Coordinate coordinate;
	public Ubicazione(){
		idUbicazione = 0;
		locIdr = new LocazioneIdrologica();
		locAmm= new LocazioneAmministrativa();
		quota = 0.0;
		esposizione = "sconosciuto";
		coordinate = new Coordinate();
	}
	public int getIdUbicazione(){
		return idUbicazione;
	}
	public LocazioneIdrologica getLocIdro(){
		return locIdr;
	}
	public LocazioneAmministrativa getLocAmm(){
		return locAmm;
	}
	public Double getQuota(){
		return quota;
	}
	public String getEsposizione(){
		return esposizione;
	}
	public Coordinate getCoordinate(){
		return coordinate;
	}
	public void setIdUbicazione (int idUbicazione){
		this.idUbicazione = idUbicazione;
	}
	public void setLocIdro(LocazioneIdrologica locIdr){
		this.locIdr = locIdr;
	}
	public void setLocAmm(LocazioneAmministrativa locAmm){
		this.locAmm = locAmm;
	}
	public void setQuota(Double quota){
		this.quota = quota;
	}
	public void setEsposizione(String esposizione){
		this.esposizione = esposizione;
	}
	public void setCoordinate(Coordinate coord){
		this.coordinate = coord;
	}
	
	public String toString(){
		String out = "<p> amministrazione"+locAmm.toString()+"</p><p> idrologia"+locIdr.toString()+"</p><p> coordinate"+coordinate.toString()+"</p><p> esposizione"+esposizione+"</p><p>quota"+quota+"</p>";
		
		System.out.println("esposizione nel bean ubicazione: "+esposizione);
		return out;
	}
	/*
	public void inserisciUbicazione(Ubicazione ub) throws SQLException{
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		   String user = "admin";
		   String pwd = "dbalps";
		   Connection conn = DriverManager.getConnection(url,user,pwd);
		   Statement st = conn.createStatement();
		   st.executeUpdate("INSERT INTO ubicazione(idSottobacino,idComune,quota,esposizione,coordinate) VALUES"
				   + "("+locIdr.getIdSottobacino()+","+locAmm.getIdComune()+","+quota+","+esposizione+",ST_GeographyFromText('SRID=4326;POINT("+coordinate.getX()+" "+coordinate.getY()+")'))");
		   st.close(); conn.close();
	}*/
}
