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
	
	public static ArrayList<Double> distribuzioneFrequenzaCumulativa(ArrayList<Double> dati){//array gia ordinato dal db
		ArrayList<Double> temp = new ArrayList<Double>();
		double probabilita=0;
		int cont=0;		
		for(int i=0;i<dati.size();i++){
			if(i!=dati.size()-1){
			 if((dati.get(i).equals(dati.get(i+1)))==false){
				cont++;
				probabilita=(double)(i+1)/(double)(dati.size()+1);
				temp.add(probabilita);
			}
			
		}else{
			probabilita=(double)(i+1)/(double)(dati.size()+1);
			temp.add(probabilita);
		}
		
	}
		return temp;
	}
	
	public static ArrayList<Double> mediaMobilePrecipitazioni(ArrayList<Double> dati, int finestra,int aggregazione,int annoriferimento){// riferimento è la posizione della temperatura del giorno scelto
		// finestra
		// aggregazione giorni tot
		ArrayList<Double> prec = new ArrayList<Double>();
		int inf=0;
		int anno=1;
		int sup=aggregazione;
		int riferimento=aggregazione/2+1;
		double precrif=0;
		double somma=0;
		 while(inf<(dati.size()-finestra)){
			while(inf<(sup-finestra+1)){
			  
			 	for(int i=inf;i<inf+finestra;i++){
				 	if(dati.get(i)==-9999) dati.set(i,0.0); 
				 	somma = somma+dati.get(i);
				 	somma=arrotonda(somma);
			 		}
			 		if(somma!=0){ //salvo somma se non è 0
			 			if(inf==(aggregazione*(annoriferimento-1)+riferimento-finestra) && anno==annoriferimento){
			 				precrif=somma;
			 				precrif=arrotonda(precrif);
			 			}
			 			else prec.add(somma);  
				}
				somma=0;
				inf++;
				}
			inf=sup;
			anno++;
			sup=(aggregazione)*anno;
		}
		 
		Collections.sort(prec);
		prec.add(precrif);
		return prec;
	}

	public static double arrotonda(double n){
	/*	double d2 = (int)(n*10);
		d2=Math.round(d2);
		 d2 /= 10;*/
		double d2 = (int)(n*10000);	
		d2 /= 100;	
		d2=Math.round(d2);
		 d2 /= 100;
		 
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
		int riferimento=aggregazione/2+1;
		double deltarif=0;
		while(inf<(dati.size()-finestra)){
			while(inf<(sup-finestra+1)){
				if(inf==(aggregazione*(annoriferimento-1)+riferimento-finestra) && anno==annoriferimento){
					deltarif=dati.get(inf+finestra-1)-dati.get(inf);
					deltarif=arrotonda(deltarif);
				}else {
					if(dati.get(inf)!=-9999 && dati.get(inf+finestra-1)!=-9999){
						 deltaT=dati.get(inf+finestra-1)-dati.get(inf);
						 deltaT=arrotonda(deltaT);
						 a.add(deltaT);
					 }
				}
				inf++;
			}
			inf=sup;
			anno++;
			sup=(aggregazione)*anno;
		}
	
		Collections.sort(a);
		a.add(deltarif);
		return a;
	}
	
	public static void lettoreCSVT(int idstazione) throws ParseException, IOException, SQLException{
		String csvFile = "/Users/mauro/Desktop/prova2.csv";
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
				st.executeUpdate("INSERT INTO temperatura_avg(idstazionemetereologica,temperaturaavg,data) values("+idstazione+","+t+",'"+dbFormat.format(d)+"')");
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
	
		st.close();
	}
	
	public static void lettoreCSVPrec(int id) throws ParseException, IOException, SQLException{
		String csvFile = "/Users/daler/Desktop/faP.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy =";";
		Date d;
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		double p=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] med = line.split(cvsSplitBy);
				
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				 d = dateformat.parse(med[0]);
				SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				System.out.println("data: "+dbFormat.format(d));
				if(!med[1].equals("NaN")) p=(Double.parseDouble(med[1]));
				else  p=-9999;
				st.executeUpdate("INSERT INTO precipitazione(idstazionemetereologica,quantita,data) values("+id+","+p+",'"+dbFormat.format(d)+"')");
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
		
		st.close();
	}
	public static double interpolazione(ArrayList<Double> temperature, ArrayList<Double> prob, double riferimento){
		double risultato=0;
		int t=0;
		int p=0;
		for(t=0;t<temperature.size();t++){
			if(temperature.get(t).compareTo(riferimento)==0) return prob.get(p);
			else if(temperature.get(t).compareTo(riferimento)<0 && 0<temperature.get(t+1).compareTo(riferimento)){
					risultato=((riferimento-temperature.get(t+1))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p)-((riferimento-temperature.get(t))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p+1);
					return risultato;
			}
			if(t==temperature.size()-1 || temperature.get(t).compareTo(temperature.get(t+1))!=0) {
				p++;
			}		
		}
		int n = temperature.size();
		System.out.println("numero di elementi; "+n);
		if(riferimento>temperature.get(n-1)){
			System.out.println("riferimento: "+riferimento+" "+temperature.get(n-1)+"");
			return 1;
		}
		else return -1;
	}
	
	
	public static ArrayList<Double> prendiTDelta(Timestamp t,int limite, int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		System.out.println("query: SELECT temperaturaavg FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" ");
		ResultSet rs =st.executeQuery("SELECT temperaturaavg FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+"");
		while(rs.next()){
			tem.add(rs.getDouble("temperaturaavg"));
		}
		rs.close();
		st.close();
		return tem;
	
	}
	
	public static ArrayList<Double> prendiPrecipitazioni(Timestamp t,int limite,int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> prec= new ArrayList<Double>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		ResultSet rs =st.executeQuery("SELECT quantita FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+"");
		while(rs.next()){
			prec.add(rs.getDouble("quantita"));
		}
		rs.close();
		st.close();
		return prec;
	
	}
	
	
	public static ArrayList<Double> prendiT(Timestamp d,int id,int limite) throws SQLException{
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		double riferimento=0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
	//	ArrayList<Double> media= new ArrayList<Double>();
		Calendar cal=new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);
		int limiteinf=dataLimite(d,-limite);
		int limitesup=dataLimite(d,0);
		ResultSet rs;
		System.out.println("limite inf  "+limiteinf);
		System.out.println("limite sup  "+limitesup);
		double cont=0;
		double med=0;
		boolean rif=false;
		boolean nullo=false;
		rs =st.executeQuery("SELECT temperaturaavg,data FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" ");
		//else rs =st.executeQuery("SELECT temperaturaavg,data FROM temperatura_avg  where date_part('month',data)="+cal.get(Calendar.MONTH)+" and date_part('day',data)="+cal.get(Calendar.DAY_OF_MONTH)+" and idstazionemetereologica"+id+"");
		while(rs.next()){
			if(cont<limite+1){
				
				if(rs.getDouble("temperaturaavg")!=-9999 && rs.getTimestamp("data").equals(d) ){
					//riferimento=rs.getDouble("temperaturaavg");
					rif=true;
					med=med+rs.getDouble("temperaturaavg");
					System.out.println("trovato");
					nullo=true;
				}
				else if(rs.getDouble("temperaturaavg")!=-9999) {
					
					//tem.add(rs.getDouble("temperaturaavg"));
					med=med+rs.getDouble("temperaturaavg");
					nullo=true;
				}
				cont++;
				if(cont==limite+1 ){
					System.out.println(rs.getString("data"));
					if(rif==true){
						System.out.println("data riferimento "+rs.getString("data"));
						System.out.println("cont="+cont);
						System.out.println("media="+med);
						System.out.println("denominatore="+(limite+1));

						riferimento=med/(limite+1);
						rif=false;
					}
					else{ 
						if(nullo==true) tem.add(med/(limite+1));
						med=0;
					}
					cont=0;
					nullo=false;
				}
			}
			
		}
		
		Collections.sort(tem);
		System.out.println("tem "+tem.size());
		tem.add(riferimento);
		System.out.println("riferimento "+riferimento);
		rs.close();
		st.close();
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
	
	public static int annoRiferimento(Timestamp t, int id) throws SQLException{
		int anno=0;
		String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		Calendar cal=new GregorianCalendar();
		cal.setTime(t);
		anno=cal.get(Calendar.YEAR);
		System.out.println("anno:"+anno);
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		ResultSet rs=st.executeQuery("select count(distinct date_part('year',data) ) from temperatura_avg where date_part('year',data)<"+anno+" and idstazionemetereologica="+id+"" );
		while(rs.next()) anno=rs.getInt("count");
		anno=anno+1;
		rs.close();
		st.close();
		return anno;
	}
	
	public static void incrementoTempo(Timestamp t){
		Calendar cal=new GregorianCalendar();
		cal.setTime(t);
		cal.add(Calendar.MONTH, 1);
		for(int i=0;i<10;i++){
					cal.add(Calendar.DAY_OF_YEAR,1);
					System.out.println(""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH));

		}
	}
	
	public static void main(String[] args) throws ParseException, IOException, SQLException{
		
		Timestamp date = new Timestamp(0);
		date = date.valueOf(("2013-12-28 00:00:00"));
		//------------temperature---------------------//
			//incrementoTempo(date);	
		lettoreCSVT(1001);
		//System.out.println(""+annoRiferimento(date,4));
		double Trif=0;
	/*			int cont=0;
		ArrayList<Double> temperature=prendiT(date,5,6);
		Trif=temperature.get(temperature.size()-1);
		temperature.remove(temperature.size()-1);
		ArrayList<Double> pro=distribuzioneFrequenzaCumulativa(temperature);
		System.out.println(Trif);
		int p=0;
	System.out.println("interpolazione"+interpolazione(temperature, pro,Trif));
		System.out.println("prob "+pro.size());
	for(int i=0;i<temperature.size();i++){
			System.out.println("i="+i+"   "+temperature.get(i));
		}
	*/
		//-----------------------deltaT---------------------//
	
	//double deltarif=0;
	//ArrayList<Double> temperature=prendiDeltaT(data,45);
	//ArrayList<Double> deltaT=mediaMobileDeltaT(temperature,7,91,21); //1899
			//deltarif=deltaT.get(deltaT.size()-1);
			//System.out.println(deltarif);
			//deltaT.remove(deltaT.size()-1);	
	
	//System.out.println("interpolazione"+interpolazione(temperature, pro,deltarif));
	//	System.out.println("prob "+pro.size());
//        System.out.println("lungh "+deltaT.size());
     
		
		
	/*	for(int i=0;i<deltaT.size();i++){
			if(i!=deltaT.size()-1 && (deltaT.get(i).equals(deltaT.get(i+1)))==false) {
			//System.out.println("i="+i+"  1:"+deltaT.get(i)+"     2:"+deltaT.get(i+1));	
			System.out.println("i="+i+"    p="+p+"   temperatura:"+deltaT.get(i) +"     probabilita"+pro.get(p));
			p++;
		}
		}	
	*/	
		//----------------------------precipitazioni--------------------------//
	
		//lettoreCSVPrec(5);
	/*			double precrif=0;
			     ArrayList<Double> precipitazioni=prendiPrecipitazioni(date,45);
			     System.out.println("1");
			     ArrayList<Double> somma=mediaMobilePrecipitazioni(precipitazioni,7,91,21);
			     System.out.println("2");
			     precrif=somma.get(somma.size()-1);
				 somma.remove(somma.size()-1);	
			  	 ArrayList<Double> pro=distribuzioneFrequenzaCumulativa(somma);
			  	  System.out.println("3");
			  /*	 for(int i=0;i<somma.size();i++){
			  		System.out.println("som "+somma.get(i));
			  	 }*/
	/*		  	 System.out.println("precipitazioni di riferimento"+precrif);
			     System.out.println("precipitazioni "+precipitazioni.size());
			     System.out.println("somma "+somma.size());
			     System.out.println("pro "+pro.size());
			     System.out.println("interpolazione"+interpolazione(somma, pro,precrif));
*/

	}
}
