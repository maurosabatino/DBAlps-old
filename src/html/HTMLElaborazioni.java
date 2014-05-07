package html;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bean.Grafici;
import bean.StazioneMetereologica;

import controller.ControllerDatabase;
import controller.ControllerDatiClimatici;

public class HTMLElaborazioni {
	
	public static String deltaT(String[] id,int finestra,int aggregazione,Timestamp t) throws SQLException{
		String sb = "";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		//cambiare inserimenti metodi controller dati climatici
		double deltarif=0;
		double interpolazione=0;
		for(int i=0;i<id.length;i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(id[i]),"IT");
			int anno=ControllerDatiClimatici.annoRiferimento(t, s.getIdStazioneMetereologica());
			Grafici gra=new Grafici();
			ArrayList<Double> temperature = ControllerDatiClimatici.prendiTDelta(t, aggregazione,s.getIdStazioneMetereologica());
			ArrayList<Double> deltaT=ControllerDatiClimatici.mediaMobileDeltaT(temperature,finestra,aggregazione*2+1,21);
			deltarif=deltaT.get(deltaT.size()-1);
			deltaT.remove(deltaT.size()-1);
			String nome=s.getNome();
			ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(deltaT);
			 interpolazione=ControllerDatiClimatici.interpolazione(deltaT, probabilita,deltarif);
			 gra.setInterpolazione(interpolazione);
			 gra.setNome(nome);
			 gra.setRiferimento(deltarif);
			 gra.setX(deltaT);
			 gra.setY(probabilita);	 
			 g.add(gra);
				
		}
		
		for(Grafici ga:g) System.out.println(""+ga.getRiferimento());
		String titolo="DeltaT";
		String unita="(°C)";
		sb=grafici(g,titolo,unita);
		
		
		return sb;
	}
	public static String mediaTemperatura(String[]id,int aggregazione,Timestamp t,double gradiente,double quota,String[] tipi) throws SQLException{
		String sb = "";
		double riferimentoG=0;
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(id[i]),"IT");
			for(int k=0;k<tipi.length;k++){
		
				Grafici gra=new Grafici();
				ArrayList<Double> temperature=ControllerDatiClimatici.prendiT(t,s.getIdStazioneMetereologica(),aggregazione,tipi[k]);
				System.out.println("k="+k+" temperature"+temperature.size()+" tipo"+tipi[k]);
				ArrayList<Double> gradienteT=new ArrayList<Double>();
				if(gradiente!=-9999 || gradiente!=0){
					double q=quota-s.ubicazione.getQuota();

					for(int j=0;j<temperature.size();j++){
						double tempo=temperature.get(j)-(q/100)*gradiente;
						//tempo=ControllerDatiClimatici.arrotonda(tempo);
						gradienteT.add(tempo);
						System.out.println();
				}
				 riferimentoG=gradienteT.get(temperature.size()-1);
				 gradienteT.remove(temperature.size()-1);
			}
			double Triferimento=temperature.get(temperature.size()-1);
			temperature.remove(temperature.size()-1);
			ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(temperature);
			double interpolazione=ControllerDatiClimatici.interpolazione(temperature, probabilita,Triferimento);
			gra.setX(temperature);
			gra.setY(probabilita);
			gra.setInterpolazione(interpolazione);
			String nome=""+s.getNome()+"-"+tipi[k];
			gra.setRiferimento(Triferimento);
			gra.setNome(nome);
			g.add(gra);
			if(gradiente!=-9999 || gradiente!=0){
				Grafici grad=new Grafici();
				grad.setX(gradienteT);
				grad.setY(probabilita);
				String n=""+s.getNome()+"-gradiente-"+tipi[k]+"";
				grad.setInterpolazione(interpolazione);
				grad.setRiferimento(riferimentoG);
				grad.setNome(n);
				g.add(grad);

			}

			
			}
		}//
		String titolo="Temperatura";
		String unita="(°C)";
		sb=grafici(g,titolo,unita);
		return sb.toString();
	}
	
	public static String mediaPrecipitazioni(String[] id,int finestra,int aggregazione,Timestamp t) throws SQLException{
		String sb="";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		//String data=Timestamp.toString(t);
		System.out.println(""+id.length);
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			int idStazione=Integer.parseInt(id[i]);
			double precrif=0;
			int anno=ControllerDatiClimatici.annoRiferimento(t, idStazione);
			ArrayList<Double> precipitazioni=ControllerDatiClimatici.prendiPrecipitazioni(t,aggregazione,idStazione);
			ArrayList<Double> somma=ControllerDatiClimatici.mediaMobilePrecipitazioni(precipitazioni,finestra,aggregazione*2+1,anno);
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
		
				"		        }," +
				"		        subtitle: {" +
				"		            text: 'elaborazioni '," +
		
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
				"data: [["+g.getRiferimento()+","+g.getInterpolazione()+"]]}," );
		}
			sb.append(		"]" );
			
			sb.append(	"	    });" +
				"		});" +
				"</script>" +
				"<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div> ");

		return sb.toString();
	}
	
	
	public static String sceltaQuery(){
		StringBuilder sb=new StringBuilder();
		sb.append(" <li><a href=\"Servlet?operazione=precipitazioniAnno\"> precipitazioni anni </a></li> ");
		sb.append(" <li><a href=\"Servlet?operazione=precipitazioniMese\"> precipitazioni mese </a></li> ");
		sb.append(" <li><a href=\"Servlet?operazione=precipitazioniTrimestre\"> precipitazioni trimestre </a></li> ");
		sb.append(" <li><a href=\"Servlet?operazione=temperaturaEPrecipitazioneAnno\"> temperatura e precipitazione anno</a></li> ");

		return sb.toString();
	}
	
	public static String precipitazioniDatiAnno(ArrayList<StazioneMetereologica>s, String op){
		StringBuilder sb=new StringBuilder();
		sb.append("<script  type=\"text/javascript\">");
		sb.append("function Disabilita(stato1,stato2,stato3){");
		sb.append("	document.getElementById('anno').disabled = stato1;");
		sb.append("	document.getElementById('mesi').disabled = stato2;");
		sb.append("	document.getElementById('anni').disabled = stato3;");

		sb.append("}");
		sb.append("	</script>");
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th> <th>scelto</th> </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td> <td> <input type=\"checkbox\" name=\"id\" value=\""+stazione.getIdStazioneMetereologica()+"\" checked=\"checked\" >  </td> </tr>");
		}
		sb.append("</table>");
		//sb.append("serie <input type=\"radio\" name=\"aggregazione\" value=\"serie\" onClick=\"Disabilita(true,false,false);\"/>");
		//sb.append(" anno<input type=\"radio\" name=\"aggregazione\" value=\"anno\" onClick=\"Disabilita(false,true,true);\"/>");
		sb.append("<p>anno:<input type=\"text\" id=\"anno\" name=\"anno\"   \"></p>");
		
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append("</form>");
		return sb.toString();	
		}
	
	public static String precipitazioniDatiMese(ArrayList<StazioneMetereologica>s,String op){
		StringBuilder sb=new StringBuilder();
		
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th> <th>scelto</th> </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td> <td> <input type=\"checkbox\" name=\"id\" value=\""+stazione.getIdStazioneMetereologica()+"\" checked=\"checked\" >  </td> </tr>");
		}
		sb.append("</table>");
		sb.append("<select name=\"mese\" >" );
		sb.append("<option value=0> Seleziona un'opzione</option>");
		sb.append("<option value='1'> JAN </option>");
		sb.append("<option value='2'> FEB </option>");
		sb.append("<option value='3'> MAR </option>");
		sb.append("</select>");
		sb.append("<p>anno:<input type=\"text\" id=\"anno\" name=\"anno\"  \"></p>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
		sb.append("</form>");
		return sb.toString();	
		}
	
	
	public static String precipitazioniQuery(String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			//if(aggregazione.equals("anno")){// se anno specifico
				ArrayList<String> categorie=new ArrayList<String>();
				//String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				/*for(int j=0;j<12;j++){
					categorie.add(mesi[j]);
				}*/
				gra.setCategorie(categorie);
				gra.setY(ControllerDatabase.prendiSommaPrecipitazioniMeseGiornaliero(id[i],anno));
				g.add(gra);			
		/*	else{//se serie
				
					ArrayList<String> categorie=new ArrayList<String>();
				String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				for(int j=0;j<12;j++){
						categorie.add(mesi[j]);
					}
					gra.setCategorie(categorie);
					boolean a=false;
					//gra.setY(ControllerDatabase.prendiSommaPreciitazioniMese(id[i],anno,a));
					g.add(gra);	
				
			}*/
			
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(cumulata);
		}
		String titolo="precipitazioni";
		String unita="mm";
		sb.append(""+graficiMultipliPrecipitazioni( g, tipo, titolo,unita, unita, titolo,"cumulata",anno,"1"));
		return sb.toString();
	}
	
	
	public static String precipitazioniQueryMese(String mese,String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		Calendar ca=new GregorianCalendar();
		ca.set(Integer.parseInt(anno), Integer.parseInt(mese)-1, 1);
		int numero=ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			ArrayList<String> giorni=new ArrayList<String>();
			for(int j=0;j<numero;j++){
				giorni.add(""+(j+1));
			}
			gra.setCategorie(giorni);
			gra.setY(ControllerDatabase.prendiPrecipitazioniMeseMensile(id[i],anno,mese));
			g.add(gra);
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(cumulata);
		}
		String titolo="precipitazioni";
		String unita="mm";
		sb.append(""+graficiMultipliPrecipitazioni( g, tipo, titolo,unita, unita, titolo,"cumulata",anno,mese));
		return sb.toString();
	}
	
	public static String dateFormat(Calendar cal){
		String data = "" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH)+"";
		return data;
	}
	
	public static String precipitazioniQueryTrimestre(String mese,String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		Calendar ca=new GregorianCalendar();
		ca.set(Integer.parseInt(anno), Integer.parseInt(mese)-1, 1);
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			

			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			
			ArrayList<String> giorni=new ArrayList<String>();
			int m=Integer.parseInt(mese);
			int a=Integer.parseInt(anno);
			gra.setY(ControllerDatabase.prendiPrecipitazioniTrimestreGiornaliero(id[i],anno,mese));
			
			Calendar c=new GregorianCalendar();
			c.set(a, m-1, 1);
			
			for(int j=0;j<gra.getY().size();j++){
				giorni.add(""+dateFormat(c));
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			gra.setCategorie(giorni);
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(gra);
			g.add(cumulata);
		}
		String titolo="precipitazioni";
		String unita="mm";
		sb.append(""+graficiMultipliPrecipitazioni( g, tipo, titolo,unita, unita, titolo,"cumulata",anno,mese));
		return sb.toString();
	}
	
	
	public static String precipitazioniTemperaturaQueryAnno(String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			//if(aggregazione.equals("anno")){// se anno specifico
				ArrayList<String> categorie=new ArrayList<String>();
				String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				for(int j=0;j<12;j++){
					categorie.add(mesi[j]);
				}
				gra.setCategorie(categorie);
				boolean a=true;
				gra.setY(ControllerDatabase.prendiSommaPrecipitazioniMese(id[i],anno,a));
				g.add(gra);	
			Grafici avg=new Grafici();
			avg.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"avg"));
			g.add(avg);
			Grafici min=new Grafici();
			min.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"min"));
			g.add(min);
			Grafici max=new Grafici();
			max.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"max"));
			g.add(max);
			Grafici maxMax=new Grafici();
			maxMax.setY(ControllerDatabase.prendiMM(id[i],anno,a,"max"));
			
			Grafici minMin=new Grafici();
			minMin.setY(ControllerDatabase.prendiMM(id[i],anno,a,"min"));
			g.add(maxMax);
			g.add(minMin);
			//}
			/*else{//se serie
				
					ArrayList<String> categorie=new ArrayList<String>();
				String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				for(int j=0;j<12;j++){
						categorie.add(mesi[j]);
					}
					gra.setCategorie(categorie);
					boolean a=false;
					gra.setY(ControllerDatabase.prendiSommaPrecipitazioniMese(id[i],anno,a));
					g.add(gra);	
					
					Grafici avg=new Grafici();
					avg.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"avg"));
					g.add(avg);
					Grafici min=new Grafici();
					min.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"min"));
					g.add(min);
					Grafici max=new Grafici();
					max.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"max"));
					g.add(max);
					Grafici maxMax=new Grafici();
					maxMax.setY(ControllerDatabase.prendiMM(id[i],anno,a,"max"));
					
					Grafici minMin=new Grafici();
					minMin.setY(ControllerDatabase.prendiMM(id[i],anno,a,"min"));
					g.add(maxMax);
					g.add(minMin);
				
			}
			*/
			
			}
		
		String titolo="precipitazioni e temperatura";
		String unita="mm";
		String unita2="C";
		String titolo1="precipitazione";
		String titolo2="temperatura";
		sb.append(""+graficiMultipli( g, tipo, titolo,unita, unita2, titolo1,titolo2));
		return sb.toString();
	}
	
	
	
	
	
	public static String graficiCategorie(ArrayList<Grafici> g,String tipo,String titolo,String unita) throws SQLException{
		StringBuilder sb=new StringBuilder();
	
	sb.append("	<script src=\"js/jquery-1.10.2.js\"></script>");
	sb.append("	<script src=\"js/Charts/highcharts.js\"></script>");
	sb.append("	<script src=\"js/Charts/modules/exporting.js\"></script>");	
	sb.append("<script>");
	sb.append("	$(function () {");
	sb.append("	    $('#container').highcharts({");
	sb.append(" title: { text: '"+titolo+"',   }," );
	sb.append("	        chart: {");
	sb.append("      type: '"+tipo+"'");
	sb.append("	        },");
	sb.append("	        xAxis: {");
		sb.append("categories: [");
		int i=0;	
		for(String x:g.get(0).getCategorie()){
				sb.append("'"+x+"',");
						i++;
			}
		      sb.append("]");
	
	
	sb.append("	        },");
	
	sb.append(" yAxis: {");
    sb.append("    title: {text: '"+titolo+"("+unita+") '},");
    sb.append("},");
	
	sb.append("	        plotOptions: {");
	sb.append("	            series: {");
	sb.append("	                allowPointSelect: true");
		sb.append("            }");
		        sb.append("},");
		sb.append("        series: [");
			
			for(Grafici gra:g){
				sb.append("    {");
			sb.append("  name: '"+gra.getNome()+"'," );

	    		sb.append("      data:[");
	    		for(int j=0;j<gra.getY().size();j++){
	    			sb.append(""+gra.getY().get(j)+",");
	    		}
	    		sb.append("]");
	    		sb.append("   },");
			}

		      
		
		    		  
		        sb.append("],");
		      
		sb.append("    });");
		    
		    
	sb.append("});");
	sb.append("</script>");


	sb.append("		<div id=\"container\" style=\"height: 400px\"></div>");
		return sb.toString();	

	}
	
	public static String graficiMultipliPrecipitazioni(ArrayList<Grafici> g,String tipo,String titolo,String unita,String unita2,String titolo1,String titolo2,String anno,String mese){
		StringBuilder sb=new StringBuilder();
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>");
sb.append("<script src=\"http://code.highcharts.com/highcharts.js\"></script>");
sb.append("<script src=\"http://code.highcharts.com/modules/exporting.js\"></script>");
sb.append("<script >");
sb.append("$(function () {");
   sb.append(" $('#container').highcharts({");
       sb.append(" chart: {");
        sb.append("    zoomType: 'xy'");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo+"'");
       sb.append(" },");
       
        sb.append("xAxis: [{");
        
        sb.append("type: 'datetime',");
        sb.append("    dateTimeLabelFormats: {");
         sb.append("       day: '%e of %b'");
         sb.append("   }");
       
   		
        		sb.append(" }],");
        sb.append("yAxis: [{"); 
         sb.append("   labels: {");
           sb.append(  "   format: '{value}"+unita+"',");
           sb.append("     style: {");
           sb.append("         color: Highcharts.getOptions().colors[2]");
            sb.append("    }");
            sb.append("},");
            sb.append("title: {");
            sb.append("    text: '"+titolo1+"',");
            sb.append("  style: {");
            sb.append("       color: Highcharts.getOptions().colors[2]");
             sb.append("   }");
            sb.append("},");
           

        sb.append("}, {"); 
        sb.append(" gridLineWidth: 0,");
        sb.append(" title: {");
        sb.append("       text: '"+titolo2+"',");
        sb.append("       style: {");
        sb.append("    color: Highcharts.getOptions().colors[0]");
        sb.append(" }");
        sb.append(" },opposite: true,");
        sb.append(" labels: {");
        sb.append("  format: '{value}"+unita2+"',");
        sb.append("      style: {");
        sb.append(" color: Highcharts.getOptions().colors[0]");
        sb.append("   }  }");

        sb.append("},],");
        sb.append(" tooltip: {");
        sb.append("    shared: true");
        sb.append("},");
        sb.append("legend: {");
        sb.append("layout: 'vertical',");
        sb.append("   align: 'left',");
        sb.append("  x: 120,");
        sb.append("  verticalAlign: 'top',");
        sb.append("   y: 80,");
        sb.append(" floating: true,");
        sb.append(" backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'");
        sb.append("},");
        sb.append("series: [");
        
        for(int i=0;i<g.size();i+=2){
        	sb.append("{");
        	
             sb.append("   name: '"+g.get(i).getNome()+"',");
              sb.append("  type: 'column',");
              sb.append(" yAxis: 0,");
               sb.append(" data: [");
               for(int j=0;j<g.get(i).getY().size();j++){
            	   sb.append(""+g.get(i).getY().get(j)+",");
               }
            	   
               sb.append("],");
               sb.append("pointStart: Date.UTC("+anno+", "+mese+", 1),");
               sb.append("pointInterval: 24 * 3600*1000 , ");
                sb.append("tooltip: {");
                sb.append("    valueSuffix: ' "+unita+"'");
                sb.append("}");

            
        	sb.append("},");
        }
        for(int i=1;i<g.size();i+=2){
        	sb.append("{");
        	
             sb.append("   name: '"+g.get(i).getNome()+"',");
              sb.append("  type: 'line',");
              sb.append(" yAxis: 1,");
               sb.append(" data: [");
               for(int j=0;j<g.get(i).getY().size();j++){
            	   sb.append(""+g.get(i).getY().get(j)+",");
               }
            	   
               sb.append("],");
               sb.append("pointStart: Date.UTC("+anno+", "+mese+", 1),");
            sb.append("pointInterval: 24 * 3600*1000 , ");
                sb.append("tooltip: {");
                sb.append("    valueSuffix: ' "+unita2+"'");
                sb.append("}");

            
        	sb.append("},");
        }
	
	sb.append("]");
   sb.append(" });");
sb.append("});");
sb.append("</script>"); 

sb.append("<div id=\"container\" style=\"min-width: 400px; height: 400px; margin: 0 auto\"></div>");
		return sb.toString();
	}
	
	public static String graficiMultipli(ArrayList<Grafici> g,String tipo,String titolo,String unita,String unita2,String titolo1,String titolo2){
		StringBuilder sb=new StringBuilder();
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>");
sb.append("<script src=\"http://code.highcharts.com/highcharts.js\"></script>");
sb.append("<script src=\"http://code.highcharts.com/modules/exporting.js\"></script>");
sb.append("<script >");
sb.append("$(function () {");
   sb.append(" $('#container').highcharts({");
       sb.append(" chart: {");
        sb.append("    zoomType: 'xy'");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo+"'");
       sb.append(" },");
       
        sb.append("xAxis: [{");
         sb.append("   categories: [");
         
         for(int i=0;i<g.get(0).getCategorie().size();i++){
        	 
        	 sb.append("'"+g.get(0).getCategorie().get(i)+"',");
        	 
         }
        
        sb.append("]  }],");
        sb.append("yAxis: [");
        sb.append(" {"); 
        sb.append(" gridLineWidth: 0,");
        sb.append(" title: {");
        sb.append("       text: '"+titolo2+"',");
        sb.append("       style: {");
        sb.append("    color: 'black'");
        sb.append(" }");
        sb.append(" },");
        
        sb.append(" labels: {");
        sb.append("  format: '{value}"+unita2+"',");
        sb.append("      style: {");
        sb.append(" color: 'black'");
        sb.append("   }  }");

        sb.append("},");
        		sb.append("{"); 
        		sb.append("opposite: true,");
         sb.append("   labels: {");
           sb.append(  "   format: '{value}"+unita+"',");
           sb.append("     style: {");
           sb.append("         color: 'black'");
            sb.append("    }");
            sb.append("},");
            sb.append("title: {");
            sb.append("    text: '"+titolo1+"',");
            sb.append("  style: {");
            sb.append("       color: 'black'");
             sb.append("   }");
            sb.append("},");
           

        sb.append("},"); 
       
        		sb.append("],");
        sb.append(" tooltip: {");
        sb.append("    shared: true");
        sb.append("},");
        sb.append("legend: {");
        /*sb.append("layout: 'vertical',");
        sb.append("   align: 'left',");
        sb.append("  x: 0,");
        sb.append("  verticalAlign: 'top',");
        sb.append("   y: 100,");
        sb.append(" floating: true,");
        sb.append(" backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'");*/
        
        sb.append("legend: {" +
			"		            layout: 'vertical'," +
			"		            align: 'right'," +
			"		            verticalAlign: 'middle'," +
            "borderColor: '#C98657',"+
           " borderWidth: 10,"+
           
			"		        }");
       
        sb.append("},");
        sb.append("series: [");
        // precipitazioni
        	sb.append("{");
             sb.append("   name: 'precipitazioni',");
              sb.append("  type: 'column',");
              sb.append("color: 'grey',");
            
              sb.append(" yAxis: 1,");
               sb.append(" data: [");
               for(int j=0;j<g.get(0).getY().size();j++){
            	   sb.append(""+g.get(0).getY().get(j)+",");
               }
            	   
               sb.append("],");
                sb.append("tooltip: {");
                sb.append("    valueSuffix: ' "+unita+"'");
                sb.append("}");
            sb.append("},");
        
       //avg
        	sb.append("{");
        	
             sb.append("   name: 'avg',");
              sb.append("  type: 'line',");
              sb.append(" yAxis: 0,");
              sb.append("color: 'black',");
               sb.append(" data: [");
               for(int j=0;j<g.get(1).getY().size();j++){
            	   sb.append(""+g.get(1).getY().get(j)+",");
               }
            	   
               sb.append("],");
                sb.append("tooltip: {");
                sb.append("    valueSuffix: ' "+unita2+"'");
                sb.append("}");

            
        	sb.append("},");
       //min
        	sb.append("{");
        	
            sb.append("   name: 'min',");
             sb.append("  type: 'line',");
             sb.append(" yAxis: 0,");
             sb.append("color: 'blue',");
              sb.append(" data: [");
              for(int j=0;j<g.get(2).getY().size();j++){
           	   sb.append(""+g.get(2).getY().get(j)+",");
              }
           	   
              sb.append("],");
               sb.append("tooltip: {");
               sb.append("    valueSuffix: ' "+unita2+"'");
               sb.append("}");

           
       	sb.append("},");
       	
       	//max
       	sb.append("{");
    	
        sb.append("   name: 'Max',");
         sb.append("  type: 'line',");
         sb.append(" yAxis: 0,");
         sb.append("color: 'red',");
          sb.append(" data: [");
          for(int j=0;j<g.get(3).getY().size();j++){
       	   sb.append(""+g.get(3).getY().get(j)+",");
          }
       	   
          sb.append("],");
           sb.append("tooltip: {");
           sb.append("    valueSuffix: ' "+unita2+"'");
           sb.append("}");

       
   	sb.append("},");
   	//maxmax
   	sb.append("{");
	
    sb.append("   name: 'maxMax',");
     sb.append("  type: 'line',");
     sb.append("lineWidth : 0,");
     sb.append(" yAxis: 0,");
     sb.append("color: 'red',");
      sb.append(" data: [");
      for(int j=0;j<g.get(4).getY().size();j++){
   	   sb.append(""+g.get(4).getY().get(j)+",");
      }
   	   
      sb.append("],");
       sb.append("tooltip: {");
       sb.append("    valueSuffix: ' "+unita2+"'");
       sb.append("}");

   
	sb.append("},");
	
	//minmin
	sb.append("{");
	
    sb.append("   name: 'minMin',");
     sb.append("  type: 'line',");
     sb.append("lineWidth : 0,");

     sb.append(" yAxis: 0,");
     sb.append("color: 'blue',");
      sb.append(" data: [");
      for(int j=0;j<g.get(5).getY().size();j++){
   	   sb.append(""+g.get(5).getY().get(j)+",");
      }
   	   
      sb.append("],");
       sb.append("tooltip: {");
       sb.append("    valueSuffix: ' "+unita2+"'");
       sb.append("}");

   
	sb.append("},");
	
	sb.append("]");
   sb.append(" });");
sb.append("});");
sb.append("</script>"); 

sb.append("<div id=\"container\" style=\"min-width: 300px; height: 400px; margin: 0 auto\"></div>");
		return sb.toString();
	}

}
