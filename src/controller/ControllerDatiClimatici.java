package controller;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ControllerDatiClimatici {
	
	public static ArrayList<Double> distribuzioneFrequenzaCumulativa(ArrayList<Double> dati){//array gia ordinato dal db, serve aggregazione?
		//dati.toArray();
		ArrayList<Double> temp = new ArrayList<Double>();
		double probabilita=0;
		double j=0;
		double k;
		
		//esclusione dato?
		
		for(int i=0;i<dati.size();i++){
			if(i!=dati.size()-1){
				
			
			 j=dati.get(i);
			 k=dati.get(i+1);
			
			//for(Double d:dati){
			//	System.out.println("i="+j+"   i+1="+k);
				
			//if(i!=dati.size()-1 || dati.get(i)!=dati.get(i+1)) {
				if( j!=k) {
				probabilita=(double)(i+1)/(double)(dati.size()+1);
			//	System.out.println(" delta 1"+dati.get(i)+" delta 2 "+dati.get(i+1));
				//System.out.println("entro");
				temp.add(probabilita);
				
			}
			}
		//}	
			else{
				probabilita=(double)(i+1)/(double)(dati.size()+1);
				temp.add(probabilita);
			}
		}
		// return probabilita e indice
		//controllo posizione del giorno scelto?
		//System.out.println(""+j);
		
		return temp;
	}
	
/*	public static double[] mediaMobilePrecipitazioni(double[] dati, int aggregazione,int riferimento){// riferimento è la temperature del giorno scelto
		double a[]=new double[dati.length];
		int sup=aggregazione-1;
		int inf=0;
		int somma=0;
		int j=0;
		while(inf<(dati.length-aggregazione)){
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
	*/
	public static double arrotonda(double n){
		double d2 = (int)(n*10000);
		 d2 /= 10000;
		return d2;
	}
	public static ArrayList<Double> mediaMobileDeltaT(ArrayList<Double> dati, int finestra,int aggregazione,int annoriferimento){// riferimento è la posizione della temperatura del giorno scelto
																											// finestra
																											// aggregazione giorni tot
		ArrayList<Double> a = new ArrayList<Double>();
		int inf=0;
		double deltaT=0;
		int anno=1;
		int sup=aggregazione;
		int riferimento=aggregazione/2;
		System.out.print("rierimento:" +dati.get(riferimento));
		double deltarif=0;
		while(inf<(dati.size()-finestra)){
			while(inf<(sup-finestra)){
				if(inf==(annoriferimento*aggregazione+(riferimento-finestra)) && anno==annoriferimento){//se siamo nell'anno di fireimento
					deltarif=dati.get(inf+finestra-1)-dati.get(inf);
				}else {
					if(dati.get(inf)!=-9999 && dati.get(inf+finestra-1)!=-9999){
						 deltaT=dati.get(inf+finestra-1)-dati.get(inf);
						 deltaT=arrotonda(deltaT);
						 a.add(deltaT);
					 }
				}
				inf++;
			}
			inf=sup+1;
			anno++;
			sup=(aggregazione)*anno;
			//System.out.println(sup);
		}
		
		Collections.sort(a);
		a.add(deltarif);
		return a;
	}
	
	public static void lettoreCSV() throws ParseException, IOException, SQLException{
		String csvFile = "/Users/Mauro/Desktop/prova2.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy =";";
		Date d;
		
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		double t=0;
		try {
			 
			
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] med = line.split(cvsSplitBy);
				
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				 d = dateformat.parse(med[0]);
				SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				System.out.println("data: "+dbFormat.format(d));
				if(!med[1].equals("NaN")) t=(Double.parseDouble(med[1]));
				else  t=-9999;
				st.executeUpdate("INSERT INTO temperatura_avg(idstazionemetereologica,temperaturaavg,data) values(4,"+t+",'"+dbFormat.format(d)+"')");
			}
			
			st.close(); conn.close();
			
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	 
		System.out.println("Done");

	}
	public static double interpolazione(ArrayList<Double> temperature, ArrayList<Double> prob, double riferimento){
		double risultato=0;
		int t=0;
		int p=0;
		double j;
		double k;
		for(t=0;t<temperature.size();t++){
			if(temperature.get(t)==riferimento) return prob.get(p);
			else if(temperature.get(t)<riferimento && riferimento<temperature.get(t+1)){
					risultato=((riferimento-temperature.get(t+1))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p)-((riferimento-temperature.get(t))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p+1);
					return risultato;
			}
			
			if(t==temperature.size()-1 || temperature.get(t)!=temperature.get(t+1)) {
				System.out.println("t"+temperature.get(t)+"t+1"+temperature.get(t++));
				p++;
			}
			
		}
		return -1;
	}
	
	public static double[][] matrice(ArrayList<Double> dati, ArrayList<Double> prob,double riferimento){
		double [][] matrice= new double[2][2];
		int t;
		int p=0;
		for(t=0;t<dati.size();t++){
			 if(dati.get(t)<riferimento && riferimento<dati.get(t+1)){
				 	matrice[0][0]=dati.get(t);
				 	matrice[0][1]=prob.get(p);
				 	matrice[1][0]=dati.get(t+1);
				 	matrice[1][1]=dati.get(p+1);
					return matrice;
			}
			
			if(t==dati.size()-1 || dati.get(t)!=dati.get(t+1)) {
				p++;
			}
			
		}
		return null;
	}
	
	public static ArrayList<Double> prendiTDelta(Timestamp t,int limite) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		ResultSet rs =st.executeQuery("SELECT temperaturaavg FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+"");
		while(rs.next()){
			tem.add(rs.getDouble("temperaturaavg"));
		}
		
		return tem;
	
	}
	
	
	public static ArrayList<Double> prendiT() throws SQLException{
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		
		ResultSet rs =st.executeQuery("SELECT temperaturaavg FROM temperatura_avg  where date_part('month',data)=9 and date_part('day',data)=30 and not date_part('year',data)=2013");
		while(rs.next()){
			if(rs.getDouble("temperaturaavg")!=-9999) tem.add(rs.getDouble("temperaturaavg"));
		}
		Collections.sort(tem);
		return tem;
	}
	
	public static int dataLimite(Timestamp d,int limite){
		int data = 0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, limite);
		cal.add(Calendar.MONTH, 1);
		if(cal.get(Calendar.DAY_OF_MONTH)<10)
			data=Integer.parseInt(""+cal.get(Calendar.MONTH)+"0"+cal.get(Calendar.DAY_OF_MONTH));
		else
			data=Integer.parseInt(""+cal.get(Calendar.MONTH)+""+cal.get(Calendar.DAY_OF_MONTH));
		return data;
	}
	
	
	public static void main(String[] args) throws ParseException, IOException, SQLException{
	//double tem[]=prendiT();
	//	double riferimento=10.7;
	//	double pro[]=distribuzioneFrequenzaCumulativa(tem);
	//lettoreCSV();
		Timestamp date = new Timestamp(0);
		double deltarif=0;
		date = Timestamp.valueOf(("2013-09-30 00:00:00"));
		
		ArrayList<Double> temperature=prendiTDelta(date,45);
		ArrayList<Double> deltaT=mediaMobileDeltaT(temperature,7,91,21); //1899
		deltarif=deltaT.get(deltaT.size()-1);
		deltaT.remove(deltaT.size()-1);
		ArrayList<Double> pro=distribuzioneFrequenzaCumulativa(deltaT);
		int p=0;
/*		double [][] matrice=matrice(deltaT, pro,deltarif) ;
		double[] delta= new double[1];
		delta[0]=deltarif;
		Interpolation inn = new Interpolation(delta, matrice);
		LinearInterpolator li=new LinearInterpolator();
		System.out.println("interpolazione="); */
		System.out.println("interpolazione"+interpolazione(deltaT, pro,deltarif));
		 System.out.println("prob "+pro.size());
		 System.out.println("delta t "+deltaT.size());
		
	/*	for(int i=0;i<deltaT.toArray().length;i++){
			if(i==deltaT.toArray().length-1 || deltaT.get(i)!=deltaT.get(i+1)) {
				
			System.out.println("temperatura:"+deltaT.get(i) +"     probabilita"+pro.get(p));
			p++;
		}
		}	
	*/	
		/*for(int i=0;i<pro.length;i++){
			System.out.println(pro[i]);
		}*/
		
		
		
		
	}
}// 81(senza riferimento) 0.5183
