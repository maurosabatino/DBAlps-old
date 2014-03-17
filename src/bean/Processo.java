package bean;




import java.util.Date;

public class Processo {
	int idProcesso;
    String nome;
	Date data;
    String descrizione;
    String note;
    Double superficie;
    Double larghezza;
    Double altezza;
    Double volume_specifico;
    Ubicazione ubicazione;//referenziato
    //String caratteristicaSito;//id ? magari come oggetto
    //String litologia;
    //String proprietaTermiche;
    //String statoFratturazione;
    //arraylist allegati
    //ArrayList effetti
    //ArrayList danni
    // ArrayList caratteristiche de processo
    
    public Processo() {     
        idProcesso=0;
        nome = "sconosciuto";
        data = new Date(0);
        descrizione = "sconosciuto";
        note = "assente";
        superficie = 0.0;
        larghezza = 0.0;
        altezza = 0.0;
        volume_specifico = 0.0;
        ubicazione = new Ubicazione();
        //caratteristicaSito = null;
        //litologia = null;
        //proprietaTermiche = null;
        //statoFratturazione = null;
    }
    
   public int getIdProcesso(){
	   return idProcesso;
   }
   
   public String getNome(){
	   return nome;
   }
   
   public Date getData(){
	   return data;
   }
   public String getDescrizione(){
	   return descrizione;
   }
   public String getNote(){
	   return note;
   }
   public Double getSuperficie(){
	   return superficie;
   }
   public Double getLarghezza(){
	   return larghezza;
   }
   public Double getAltezza(){
	   return altezza;
   }
   public Double getVolumeSpecifico(){
	   return volume_specifico;
   }
   public Ubicazione getUbicazione(){
	   return ubicazione;
   }
   
   public void setIdprocesso(int idProcesso){
	   this.idProcesso = idProcesso;
   }
   public void setNome(String nome){
	   this.nome=nome;
   }
   public void setData(Date data){
	   this.data = data;
   }
   public void setDescrizione(String descrizione){
	   this.descrizione=descrizione;
   }
   public void setNote(String note){
	   this.note=note;
   }
   public void setSuperficie(Double superficie){
	   this.superficie = superficie;
   }
   public void setLarghezza(Double larghezza){
	   this.larghezza = larghezza;
   }
   public void setAltezza(Double altezza){
	   this.altezza = altezza;
   }
   public void setVolumeSpecifico(Double volume_specifico){
	   this.volume_specifico = volume_specifico;
   }
   public void setUbicazione(Ubicazione ubicazione){
	   this.ubicazione = ubicazione;
   }
 
  public String getVisualizzaprocesso(){
	 String out = "<p> descrizione: "+descrizione+"</p> <p> nome: "+nome+"</p> <p>note: "+note+"</p><p> data: "+data.toString()+"</p> <p>superficie: "+superficie+"</p>";
	 out+="<p>ubicazione</p>";
	 out+="<p>ubicazione "+ubicazione.toString()+"</p>";
	 System.out.println("esposizione nel bean: "+getUbicazione().getEsposizione());
	 return out;
  }
   
   /*public void inserisciProcesso(Processo p) throws SQLException{
	   String url = "jdbc:postgresql://localhost:5432/DBAlps";
	   String user = "admin";
	   String pwd = "dbalps";
	   Connection conn = DriverManager.getConnection(url,user,pwd);
	   Statement st = conn.createStatement();
	   st.executeUpdate("INSERT INTO processo(idUbicazione,idsito,nome,data,descrizione,note,superificie,larghezza,altezza) VALUES"
			   + "("+p.getUbicazione().getIdUbicazione()+","++","+p.getNome()+","+p.getData()+","+p.getDescrizione()+","+p.getSuperficie()+","+p.getSuperficie()+","+p.getLarghezza()+","+p.getAltezza()+")");
	   st.close(); conn.close();
   }*/
}
