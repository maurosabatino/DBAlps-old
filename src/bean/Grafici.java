package bean;

import java.util.ArrayList;

public class Grafici {
	ArrayList<Double> x;
	ArrayList<Double> y;
	String nome;
	double interpolazione;
	double riferimento;
	
	public ArrayList<Double> getX() {
		return x;
	}
	public void setX(ArrayList<Double> x) {
		this.x = x;
	}
	public ArrayList<Double> getY() {
		return y;
	}
	public void setY(ArrayList<Double> y) {
		this.y = y;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getInterpolazione() {
		return interpolazione;
	}
	public void setInterpolazione(double interpolazione) {
		this.interpolazione = interpolazione;
	}
	public double getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(double riferimento) {
		this.riferimento = riferimento;
	}
	
	
}
