package html;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import controller.ControllerDatiClimatici;

public class HTMLElaborazioni {
	
	public static String deltaT() throws SQLException{
		String sb = "";
		Timestamp date = new Timestamp(0);
		//cambiare inserimenti metodi controller dati climatici
		double deltarif=0;
		date = date.valueOf(("2013-09-30 00:00:00"));
		int limite=45;
		ArrayList<Double> temperature = ControllerDatiClimatici.prendiTDelta(date, limite);
		ArrayList<Double> deltaT=ControllerDatiClimatici.mediaMobileDeltaT(temperature,7,91,21);
		deltarif=deltaT.get(deltaT.size()-1);
		deltaT.remove(deltaT.size()-1);
		ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(deltaT);
		double interpolazione=ControllerDatiClimatici.interpolazione(deltaT, probabilita,deltarif);
		String titolo="DeltaT";
		String unita="(°C)";
		sb=grafici(deltaT,probabilita,deltarif,interpolazione,titolo,unita);
		
		
		return sb;
	}
	public static String mediaTemperatura() throws SQLException{
		String sb = "";
		Timestamp date = new Timestamp(0);
		//cambiare inserimenti metodi controller dati climatici
		date = date.valueOf(("2013-09-30 00:00:00"));
		//String data=date.toString();//modificare!!!!!!!!!!
		ArrayList<Double> temperature=ControllerDatiClimatici.prendiT(date);
		double Triferimento=temperature.get(temperature.size()-1);
		temperature.remove(temperature.size()-1);
		ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(temperature);
		double interpolazione=ControllerDatiClimatici.interpolazione(temperature, probabilita,Triferimento);
		System.out.println("interpolazione"+interpolazione+"  riferimento"+ Triferimento);
		String titolo="Temperatura";
		String unita="(°C)";
		sb=grafici(temperature,probabilita,Triferimento,interpolazione,titolo,unita);
		return sb.toString();
	}
	
	public static String mediaPrecipitazioni() throws SQLException{
		String sb="";
		Timestamp date = new Timestamp(0);
		//cambiare inserimenti metodi controller dati climatici
		date = date.valueOf(("2013-09-30 00:00:00"));
		String data=date.toString();
		double precrif=0;
	     ArrayList<Double> precipitazioni=ControllerDatiClimatici.prendiPrecipitazioni(date,45);
	     ArrayList<Double> somma=ControllerDatiClimatici.mediaMobilePrecipitazioni(precipitazioni,7,91,21);
	     precrif=somma.get(somma.size()-1);
		 somma.remove(somma.size()-1);	
	  	 ArrayList<Double> pro=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(somma);
	  	 double interpolazione=ControllerDatiClimatici.interpolazione(somma, pro,precrif);
	  	String titolo="Precipitazioni";
		String unita="(mm)";
	  	 sb=grafici(somma,pro,precrif,interpolazione,titolo,unita);
		return sb.toString();
	}
	
	
	public static String grafici(ArrayList<Double> x,ArrayList<Double> y,double riferimento,double interpolazione,String titolo,String unita){
		StringBuilder sb= new StringBuilder();
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>" +
				"		<script src=\"js/Charts/highcharts.js\"></script>" +
				"		<script src=\"js/Charts/modules/exporting.js\"></script>" +
				"		<script type=\"text/javascript\">" +
				"		$(function () {" +
				"		    $('#container').highcharts({" +
				"		        title: {" +
				"		            text: '"+titolo+"'," +
				"		            x: -20 " +
				"		        }," +
				"		        subtitle: {" +
				"		            text: 'elaborazioni '," +
				"		            x: -20" +
				"		        }," +
				"		        xAxis: {" +
				"					title: {" +
					"		                text: '"+titolo+""+unita+" '" +
					"		            }" +
					"},	yAxis: {" +
				"		            title: {" +
				"		                text: 'Probabilità '" +
				"		            }," +
				"		            plotLines: [{" +
				"		                value: 0," +
				"		                width: 1," +
				"		                color: '#808080'" +
				"		            }]," +
				"min:0," +
				" max:1,"+
				"		        }," +
			
				
				"		        legend: {" +
				"		            layout: 'vertical'," +
				"		            align: 'right'," +
				"		            verticalAlign: 'middle'," +
				"		            borderWidth: 0" +
				"		        }," +
				"		        series: [{" +
				"		            name: 'Stazione 4'," +
				"marker: {"+
                  "  enabled: false"+
                "},"+
				
				"		            data: ["); 	
			int cont=0;
		
			for(int i=0;i<x.size();i++){	
				if(i!=(x.size()-1)){
					if((x.get(i).equals(x.get(i+1)))==false){
						sb.append("["+x.get(i)+","+y.get(cont)+"],");
						cont++;
					}
					}else{
						sb.append("["+x.get(i)+","+y.get(cont)+"],");
						cont++;
				}      
						
			}		
		
				sb.append("],");
			sb.append("},{" +
					"name: 'riferimento'," +
					"marker:{" +
					"symbol: 'circle'},"+
					
					"          color:'red',"+
				//	"data: [["+riferimento+", 0],["+riferimento+",1], ["+riferimento+", "+interpolazione+"]]" );
				"data: [["+riferimento+","+interpolazione+"]]" );
			sb.append(		"}]" +
				"	    });" +
				"		});" +
				"</script>" +
				"<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div> ");

		return sb.toString();
	}
}
