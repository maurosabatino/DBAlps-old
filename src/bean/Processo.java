package bean;
import java.sql.Timestamp;
import java.util.ArrayList;

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
	Ubicazione ubicazione;
	Litologia litologia;
	ProprietaTermiche proprietaTermiche;
	StatoFratturazione statoFratturazione;
	SitoProcesso SitoProcesso;
	ClasseVolume classeVolume;
	ArrayList<EffettiMorfologici> effetti;
	ArrayList <Danni> danni;
	ArrayList<TipologiaProcesso> tipologiaProcesso;
    
    
    //arraylist allegati
    
    public Processo() {     
        idProcesso=0;
        nome = "sconosciuto";
        data = Timestamp.valueOf("0001-01-01 00:00:00");
        descrizione = "sconosciuto";
        note = "assente";
        superficie = 0.0;
        larghezza = 0.0;
        altezza = 0.0;
        volume_specifico = 0.0;
        classeVolume = new ClasseVolume();
        ubicazione = new Ubicazione();
        litologia = new Litologia();
        statoFratturazione=new StatoFratturazione();
        proprietaTermiche = new ProprietaTermiche();
        SitoProcesso = new SitoProcesso();
        effetti = new ArrayList<EffettiMorfologici>();
        danni = new ArrayList<Danni>();
        tipologiaProcesso = new ArrayList<TipologiaProcesso>();
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



	public ArrayList<EffettiMorfologici> getEffetti() {
		return effetti;
	}

	public void setEffetti(ArrayList<EffettiMorfologici> effetti) {
		this.effetti = effetti;
	}

	public ArrayList<Danni> getDanni() {
		return danni;
	}

	public void setDanni(ArrayList<Danni> danni) {
		this.danni = danni;
	}

	public ArrayList<TipologiaProcesso> getTipologiaProcesso() {
		return tipologiaProcesso;
	}

	public void setTipologiaProcesso(ArrayList<TipologiaProcesso> tipologiaProcesso) {
		this.tipologiaProcesso = tipologiaProcesso;
	}

	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}

	public ClasseVolume getClasseVolume() {
		return classeVolume;
	}

	public void setClasseVolume(ClasseVolume classeVolume) {
		this.classeVolume = classeVolume;
	}

	public Litologia getLitologia() {
		return litologia;
	}

	public void setLitologia(Litologia litologia) {
		this.litologia = litologia;
	}

	public ProprietaTermiche getProprietaTermiche() {
		return proprietaTermiche;
	}

	public void setProprietaTermiche(ProprietaTermiche proprietaTermiche) {
		this.proprietaTermiche = proprietaTermiche;
	}

	public StatoFratturazione getStatoFratturazione() {
		return statoFratturazione;
	}

	public void setStatoFratturazione(StatoFratturazione statoFratturazione) {
		this.statoFratturazione = statoFratturazione;
	}
 
  
}
