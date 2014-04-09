package html;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Grafici;

import controller.ControllerDatabase;
import controller.ControllerDatiClimatici;

public class HTMLElaborazioni {
	
	public static String deltaT(String[] id,int finestra,int aggregazione,Timestamp t) throws SQLException{
		String sb = "";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		//cambiare inserimenti metodi controller dati climatici
		double deltarif=0;
		double interpolazione=0;
		int limite=45;
		for(int i=0;i<id.length;i++){
			int idStazione=Integer.parseInt(id[i]);
			int anno=ControllerDatiClimatici.annoRiferimento(t, idStazione);
			Grafici gra=new Grafici();
			ArrayList<Double> temperature = ControllerDatiClimatici.prendiTDelta(t, limite,idStazione);
			ArrayList<Double> deltaT=ControllerDatiClimatici.mediaMobileDeltaT(temperature,finestra,aggregazione*2+1,21);
			deltarif=deltaT.get(deltaT.size()-1);
			deltaT.remove(deltaT.size()-1);
			String nome=ControllerDatabase.prendiNome(idStazione);
			ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(deltaT);
			 interpolazione=ControllerDatiClimatici.interpolazione(deltaT, probabilita,deltarif);
			 gra.setInterpolazione(interpolazione);
			 gra.setNome(nome);
			 gra.setRiferimento(deltarif);
			 gra.setX(deltaT);
			 gra.setY(probabilita);
			 
			 g.add(gra);
		}
		
		String titolo="DeltaT";
		String unita="(°C)";
		sb=grafici(g,titolo,unita);
		
		
		return sb;
	}
	public static String mediaTemperatura(String[]id,int aggregazione,Timestamp t) throws SQLException{
		String sb = "";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			int idStazione=Integer.parseInt(id[i]);
			Grafici gra=new Grafici();
			ArrayList<Double> temperature=ControllerDatiClimatici.prendiT(t);
			double Triferimento=temperature.get(temperature.size()-1);
			temperature.remove(temperature.size()-1);
			ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(temperature);
			double interpolazione=ControllerDatiClimatici.interpolazione(temperature, probabilita,Triferimento);
			System.out.println("interpolazione"+interpolazione+"  riferimento"+ Triferimento);
			gra.setX(temperature);
			gra.setY(probabilita);
			gra.setInterpolazione(interpolazione);
			String nome=ControllerDatabase.prendiNome(idStazione);
			gra.setRiferimento(Triferimento);
			gra.setNome(nome);
			g.add(gra);
		}
		String titolo="Temperatura";
		String unita="(°C)";
		sb=grafici(g,titolo,unita);
		return sb.toString();
	}
	
	public static String mediaPrecipitazioni(String[] id,int finestra,int aggregazione,Timestamp t) throws SQLException{
		String sb="";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		//String data=Timestamp.toString(t);
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			int idStazione=Integer.parseInt(id[i]);
			double precrif=0;
			int anno=ControllerDatiClimatici.annoRiferimento(t, idStazione);
			ArrayList<Double> precipitazioni=ControllerDatiClimatici.prendiPrecipitazioni(t,aggregazione);
			ArrayList<Double> somma=ControllerDatiClimatici.mediaMobilePrecipitazioni(precipitazioni,finestra,aggregazione,anno);
			precrif=somma.get(somma.size()-1);
			somma.remove(somma.size()-1);	
			ArrayList<Double> pro=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(somma);
			double interpolazione=ControllerDatiClimatici.interpolazione(somma, pro,precrif);
			gra.setInterpolazione(interpolazione);
			String nome=ControllerDatabase.prendiNome(idStazione);
			gra.setNome(nome);
			gra.setRiferimento(precrif);
			gra.setX(somma);
			gra.setY(pro);
			g.add(gra);
		}
	  	String titolo="Precipitazioni";
		String unita="(mm)";
	  	 sb=grafici(g,titolo,unita);
		return sb.toString();
	}
	
	
	public static String grafici(ArrayList<Grafici> gra,String titolo,String unita){
		StringBuilder sb= new StringBuilder();
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>" +
				"		<script src=\"js/Charts/highcharts.js\"></script>" +
				"		<script src=\"js/Charts/modules/exporting.js\"></script>" +
				"		<script type=\"text/javascript\">" +
				"		$(function () {" +
				"		    $('#container').highcharts({" +
				"		        title: {" +
				"		            text: '"+titolo+"'," +
			//	"		            x: -20 " +
				"		        }," +
				"		        subtitle: {" +
				"		            text: 'elaborazioni '," +
			//	"		            x: -20" +
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
				"		        },");
		sb.append("		        series: [");
		for(Grafici g:gra){
		sb.append("	{" +
				"		            name: '"+g.getNome()+"'," +
				"marker: {"+
                  "  enabled: false"+
                "},"+
				
				"		            data: ["); 	
			int cont=0;
		
			for(int i=0;i<g.getX().size();i++){	
				if(i!=(g.getX().size()-1)){
					if((g.getX().get(i).equals(g.getX().get(i+1)))==false){
						sb.append("["+g.getX().get(i)+","+g.getY().get(cont)+"],");
						cont++;
					}
					}else{
						sb.append("["+g.getX().get(i)+","+g.getY().get(cont)+"],");
						cont++;
				}      
						
			}	
			
			
			
		
				sb.append("],");
			sb.append("},{" +
					"name: '"+g.getNome()+"-riferimento'," +
					"marker:{" +
					"symbol: 'circle'},"+
					
					"          color:'red',"+
				//	"data: [["+riferimento+", 0],["+riferimento+",1], ["+riferimento+", "+interpolazione+"]]" );
				"data: [["+g.getRiferimento()+","+g.getInterpolazione()+"]]}," );
		}
			sb.append(		"]" );
			
			sb.append(	"	    });" +
				"		});" +
				"</script>" +
				"<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div> ");

		return sb.toString();
	}
	

}
