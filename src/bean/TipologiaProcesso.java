package bean;

public class TipologiaProcesso {
	int idTipologiaProcesso;
	String nome_IT;
	String nome_ENG;
	
	public TipologiaProcesso() {
		this.idTipologiaProcesso = 0;
		this.nome_IT = "";
		this.nome_ENG = "";
	}

	public int getIdTipologiaProcesso() {
		return idTipologiaProcesso;
	}

	public void setIdTipologiaProcesso(int idTipologiaProcesso) {
		this.idTipologiaProcesso = idTipologiaProcesso;
	}

	public String getNome_IT() {
		return nome_IT;
	}

	public void setNome_IT(String nome_IT) {
		this.nome_IT = nome_IT;
	}

	public String getNome_ENG() {
		return nome_ENG;
	}

	public void setNome_ENG(String nome_ENG) {
		this.nome_ENG = nome_ENG;
	}
	
	
}
