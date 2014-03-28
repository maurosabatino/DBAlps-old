package bean;




import java.sql.Timestamp;

public class Processo {
	int idProcesso;
    String nome;
	Timestamp data;
    String descrizione;
    String note;
    Double superficie;
    Double larghezza;
    Double altezza;
    Double volume_specifico;
    Ubicazione ubicazione;//referenziato
    SitoProcesso SitoProcesso;
    //arraylist allegati
    //ArrayList effetti
    //ArrayList danni
    // ArrayList caratteristiche de processo
    
    public Processo() {     
        idProcesso=0;
        nome = "sconosciuto";
        data = new Timestamp(0);
        descrizione = "sconosciuto";
        note = "assente";
        superficie = 0.0;
        larghezza = 0.0;
        altezza = 0.0;
        volume_specifico = 0.0;
        ubicazione = new Ubicazione();
        SitoProcesso = new SitoProcesso();
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
   
   public Timestamp getData(){
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
   public SitoProcesso getSitoProcesso(){
  	 return SitoProcesso;
   }
   
   public void setIdprocesso(int idProcesso){
	   this.idProcesso = idProcesso;
   }
   public void setNome(String nome){
	   this.nome=nome;
   }
   public void setData(Timestamp data){
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
   public void setSitoProcesso(SitoProcesso SitoProcesso){
  	 this.SitoProcesso=SitoProcesso;
   }
 
  public String getVisualizzaprocesso(){
	 String out = "<p> descrizione: "+descrizione+"</p> <p> nome: "+nome+"</p> <p>note: "+note+"</p><p> data: "+data.toString()+"</p> <p>superficie: "+superficie+"</p>";
	 out+="<p>ubicazione</p>";
	 out+="<p>ubicazione "+ubicazione.toString()+"</p>";
	 System.out.println("esposizione nel bean: "+getUbicazione().getEsposizione());
	 return out;
  }
}
