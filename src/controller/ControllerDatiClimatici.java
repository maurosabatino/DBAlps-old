package controller;

import java.util.*;

public class ControllerDatiClimatici {
	
	public static double[] distribuzioneFrequenzaCumulativa(double[] dati){//array gia ordinato dal db, serve aggregazione?
		double a[]=new double[dati.length];
		double probabilita=0;
		for(int i=0;i<dati.length;i++){
			
			if(i==dati.length-1 || dati[i]!=dati[i+1]) {
				probabilita=(double)(i+1)/(double)(dati.length+1);
				
			}else probabilita=-9999;
			a[i]=probabilita;
		}
		return a;
	}
	
	public static double[] mediaMobilePrecipitazioni(double[] dati, int aggregazione,int riferimento){// riferimento è la temperature del giorno scelto
		double a[]=new double[dati.length];
		int sup=aggregazione-1;
		int inf=0;
		
		int somma=0;
		int j=0;
		while(inf<=(dati.length-aggregazione)){
			 if(inf!=riferimento){
			 for(int i=inf;i<=sup;i++){
				 if(dati[i]==-9999) dati[i]=0;
				somma+=dati[i];
			 }
			 if(somma!=0){  //salvo somma se non è 0
					a[j]=somma;
					j++;
				}
			}
			somma=0;
			sup++;
			inf++;
		}
		double b[]=new double[j];
		for(int i=0;i<j;i++) b[i]=a[i];
		Arrays.sort(b);
		return b;
	 }
	
	public static double[] mediaMobileDeltaT(double[] dati, int aggregazione,int riferimento){// riferimento è la temperature del giorno scelto
		
		double a[]=new double[dati.length];
		int inf=0;
		double deltaT=0;
		int i=0;
		while(inf<(dati.length-aggregazione)){
			if(inf!=riferimento){
				 if(dati[inf]!=-9999 && dati[inf+aggregazione]!=-9999){
					 deltaT=dati[inf]-dati[inf+aggregazione];
					 a[i]=deltaT;
					 i++;
				 }
			}
			inf++;
		}
		double b[]=new double[i];
		for(int j=0;j<i;j++) b[j]=a[j];
		Arrays.sort(b);
		return b;
	}
	
	public static void main(String[] args){
		double a[]={2,3,4,-9999,6,7,9};
		double b[]=mediaMobileDeltaT(a,1,4);
		
		for(int i=0;i<b.length;i++) System.out.println(b[i]);
		//b=distribuzioneFrequenzaCumulativa(a);
		System.out.println("dati di c");
		double c[]=distribuzioneFrequenzaCumulativa(b);
			for(int i=0;i<c.length;i++) System.out.println(c[i]);
	}
	
}

