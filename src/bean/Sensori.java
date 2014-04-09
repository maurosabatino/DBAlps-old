package bean;

import java.util.ArrayList;

public class Sensori {
	public  int idsensori;
	public  String sensori_IT;
	public String sensori_ENG;
	
	public Sensori(){
		idsensori=0;
		sensori_IT="";
		sensori_ENG="";
	}

	public int getIdsensori() {
		return idsensori;
	}

	public void setIdsensori(int idsensori) {
		this.idsensori = idsensori;
	}

	public String getSensori_IT() {
		return sensori_IT;
	}

	public void setSensori_IT(String sensori) {
		this.sensori_IT = sensori;
	}

	public String getSensori_ENG() {
		return sensori_ENG;
	}

	public void setSensori_ENG(String sensori_ENG) {
		this.sensori_ENG = sensori_ENG;
	}
}
