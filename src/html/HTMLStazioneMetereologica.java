package html;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bean.*;
import bean.partecipante.Partecipante;
import bean.partecipante.Role;
import controller.ControllerDatabase;
import controller.ControllerJson;

public class HTMLStazioneMetereologica {
	public static String mostraTutteStazioniMetereologiche(Partecipante part) throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> ");
		sb.append("<tr> <th>Nome</th>  <th>comune</th> <th> dettagli</th> ");
		if(part!=null && (part.hasRole(Role.AMMINISTRATORE)||part.hasRole(Role.AVANZATO))){
			sb.append("<th>modifica stazione</th><th> carica dati climatici</th>");
		}
		sb.append("</tr>");
		for(StazioneMetereologica s: ap){
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>  ");
			sb.append("<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td>");
			if(part!=null &&(part.hasRole(Role.AMMINISTRATORE)||part.hasRole(Role.AVANZATO))){
				sb.append("<td><a href=\"Servlet?operazione=modificaStazione&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> modifica</a></td>");
				sb.append("<td><a href=\"Servlet?operazione=caricaDatiClimatici&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\"> Carica</a></td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String mostraStazioneMetereologica(int idStazioneMetereologica,String loc) throws SQLException{
		StazioneMetereologica s = ControllerDatabase.prendiStazioneMetereologica(idStazioneMetereologica,loc);
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th>  <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}
	public static String mostraCercaStazioneMetereologica(ArrayList<StazioneMetereologica> al) throws SQLException{
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"table-responsive\"><table class=\"table\">  <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(StazioneMetereologica s: al){
			sb.append(" <tr> <td>"+s.getNome()+" </td> <td> "+s.getDataInizio()+"</td> <td> "+s.getUbicazione().getLocAmm().getComune()+"</td>"
				/*?*/	+ "<td><a href=\"Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica="+s.getIdStazioneMetereologica()+"\">dettagli</a></td></tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String formStazioneMetereologica(String path) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));

	
		
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">" +
				"			<p>Nome:<input type=\"text\" name=\"nome\" value=\"prova\"></p>" +
				"			<p>Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\" value=\"agg\"></p>" +
				"			<p>Note:<input type=\"text\" name=\"note\" value=\"note\"></p>" +
				"			<p>Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\" value=\"periodo\"></p>" +
				"			<p>Oraria:<input type=\"text\" name=\"oraria\" value=\"false\"></p>");
		
		sb.append("<p> ente:<input type=\"text\" id=\"ente\" name=\"ente\" /></p>");
		sb.append("<p>enteid:<input readonly=\"readonly\" type=\"text\" id=\"idEnte\" name=\"idEnte\" /></p>");
		sb.append("<p> sito:<input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristiche_it\" /></p>");
		sb.append("<p>sitoid:<input readonly=\"readonly\" type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" /></p>");
		
		sb.append("<p>dati sull'ubicazione</p>");
		sb.append("<p>sottobacino:<input type=\"text\" id=\"sottobacino\" name=\"sottobacino\">");
		sb.append("<p>bacino:<input type=\"text\"id=\"bacino\" name=\"bacino\"></p>");
		sb.append("<input  type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" />");
		
		sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" /> </p>");
		sb.append("<p>idcomune:<input readonly=\"readonly\" type=\"text\" id=\"idcomune\" name=\"idcomune\" /></p>");
		sb.append("<p>provincia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" /></p>");
		sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" /></p> ");
		sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" /></p>");
		
		sb.append("<p>latitudine:<input type=\"text\" name=\"latitudine\" value=\"12\"></p>");
		sb.append("<p>longitudine:<input type=\"text\" name=\"longitudine\"value=\"14\"></p>");
		sb.append("<p>quota:<input type=\"text\" name=\"quota\" value=\"12\"></p>");
		sb.append("<p>esposizione:<input type=\"text\" name=\"esposizione\" value=\"nord\"></p>");
		sb.append( "<p> data inizio <input type=\"text\" id=\"datainizio\" name=\"datainizio\" ></p>");
		sb.append( "<p> data fine <input type=\"text\" id=\"datafine\" name=\"datafine\" ></p>");

		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		
		sb.append("<br>");
			sb.append(	"			<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazione\">" +
				"			<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	
	public static String modificaStazioneMetereologica(StazioneMetereologica s,String path) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.setTime(s.getDataInizio());
		inizio.add(Calendar.MONTH, 1);
		Calendar fine = new GregorianCalendar();
		fine.setTime(s.getDataFine());
		fine.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		sb.append(HTMLScript.scriptData("datafine"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		

		
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">" +
				"			<p>Nome:<input type=\"text\" name=\"nome\" value=\""+s.getNome()+"\"></p>" +
				"			<p>Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\" value=\""+s.getAggregazioneGiornaliera()+"\"></p>" +
				"			<p>Note:<input type=\"text\" name=\"note\" value=\""+s.getNote()+"\"></p>" +
				"			<p>Oraria:<input type=\"text\" name=\"oraria\" value=\""+s.getOraria()+"\"></p>" +
				"			<p>dati sull'ubicazione</p>");
		
		sb.append("<p> ente:<input type=\"text\" id=\"ente\" name=\"ente\" value=\""+s.getEnte().getEnte()+"\" ></p>");
		sb.append("<p>enteid:<input readonly=\"readonly\" type=\"text\" id=\"idEnte\" name=\"idEnte\" value="+s.getEnte().getIdEnte()+"></p>");
		sb.append("<p> sito:<input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristiche_it\" value=\""+s.getSito().getCaratteristiche_IT()+"\" /></p>");
		sb.append("<p>sitoid:<input readonly=\"readonly\" type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" value="+s.getSito().getIdSitoStazioneMetereologica()+" /></p>");
		sb.append("<p>dati sull'ubicazione</p>");
		sb.append("<p>bacino:<input type=\"text\" name=\"bacino\"></p>");
		sb.append("<p>sottobacino:<input type=\"text\" name=\"sottobacino\">");
		sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" value=\""+s.getUbicazione().getLocAmm().getComune()+"\"/> </p>");
		sb.append("<p>idcomune:<input readonly=\"readonly\" type=\"text\" id=\"idcomune\" name=\"idcomune\" value=\""+s.getUbicazione().getLocAmm().getIdComune()+"\"/></p>");
		sb.append("<p>provncia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" value=\""+s.getUbicazione().getLocAmm().getProvincia()+"\" /></p>");
		sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" value=\""+s.getUbicazione().getLocAmm().getRegione()+"\" /></p> ");
		sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" value=\""+s.getUbicazione().getLocAmm().getNazione()+"\" /></p>"+
				"			<p>latitudine:<input type=\"text\" name=\"latitudine\" value=\""+s.getUbicazione().getCoordinate().getX()+"\"></p" +
				"			<p>longitudine:<input type=\"text\" name=\"longitudine\" value=\""+s.getUbicazione().getCoordinate().getY()+"\"></p>" +
				"			<p>quota:<input type=\"text\" name=\"quota\" value=\""+s.getUbicazione().getQuota()+"\"></p>" +
				"			<p>esposizione:<input type=\"text\" name=\"esposizione\" value=\""+s.getUbicazione().getEsposizione()+"\"></p>");
				for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
					boolean inserito=false;
					for(int i=0;i<s.getSensori().size();i++){
						if(sens.getSensori_IT().equals(s.getSensori().get(i).getSensori_IT())){
							sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" checked=\"checked\" > "+sens.getSensori_IT()+" "); 
							inserito=true;

						}
					}
					if(inserito==false) sb.append("<input type=\"checkbox\" name=\"tipo_it\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
					inserito=false;
				}
				
				//sb.append("<p> sensori:<input type=\"text\" id=\"tipo_"+loc+"\" name=\"tipo_"+loc+"\" value="+sensori+"></p>"); 
				
				sb.append( "<p> data inizio <input type=\"text\" id=\"datainizio\" name=\"datainizio\" value=\""+inizio.get(Calendar.YEAR)+"-"+inizio.get(Calendar.MONTH)+"-"+inizio.get(Calendar.DAY_OF_MONTH)+"\"></p>"+
						"			<input type=\"hidden\"  name=\"enteVecchio\" value=\""+s.getEnte().getEnte()+"\">" +
						"			<input type=\"hidden\" name=\"idStazione\" value=\""+s.getIdStazioneMetereologica()+"\">");
				String da=s.getDataFine().toString();
				System.out.println(""+da);
				//if(s.getDataFine().compareTo(Timestamp.valueOf("0001-01-01 00:00:00"))==0){
		     if(s.getDataFine().toString().equals("0001-01-01")){	
				sb.append("<p> data fine <input type=\"text\" id=\"datafine\" name=\"datafine\"></p>");
					System.out.println("pirla");
				}
				else
				sb.append("<p> data fine <input type=\"text\" id=\"datafine\" name=\"datafine\" value=\""+fine.get(Calendar.YEAR)+"-"+fine.get(Calendar.MONTH)+"-"+fine.get(Calendar.DAY_OF_MONTH)+"\"></p>");

				sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"inserisciStazioneModificata\">" +
				"			<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	
	public static String formRicercaMetereologica(String path) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		Calendar inizio = new GregorianCalendar();
		inizio.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("datainizio"));
		Calendar fine = new GregorianCalendar();
		fine.add(Calendar.MONTH, 1);
	 	sb.append(HTMLScript.scriptData("datafine"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteSitoStazione(ControllerJson.getJsonSitoStazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteEnte(ControllerJson.getJsonEnte(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
	
		
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">" +
				"			<p>Nome:<input type=\"text\" name=\"nome\"></p>" +
				"			<p>Aggregazione giornaliera:<input type=\"text\" name=\"aggregazioneGiornaliera\"></p>" +
				"			<p>Note:<input type=\"text\" name=\"note\"></p>" +
				"			<p>Periodo Funzionamento:<input type=\"text\" name=\"periodoFunzionamento\" ></p>" +
				"			<p>Oraria:<input type=\"text\" name=\"oraria\"></p>" +
				"			<p>dati sull'ubicazione</p>");
		
		sb.append("<p> ente:<input type=\"text\" id=\"ente\" name=\"ente\" /></p>");
		sb.append("<p>enteid:<input readonly=\"readonly\" type=\"text\" id=\"idEnte\" name=\"idEnte\" /></p>");
		sb.append("<p> sito:<input type=\"text\" id=\"caratteristiche_"+loc+"\" name=\"caratteristiche_it\" /></p>");
		sb.append("<p>sitoid:<input readonly=\"readonly\" type=\"hidden\" id=\"idsitostazione\" name=\"idsitostazione\" /></p>");
		
		
		sb.append("<p>dati sull'ubicazione</p>");
		
		sb.append("<p>sottobacino:<input type=\"text\" id=\"sottobacino\" name=\"sottobacino\">");
		sb.append("<p>bacino:<input type=\"text\"id=\"bacino\" name=\"bacino\"></p>");
		sb.append("<input  type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" />");
		sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" /> </p>");
		sb.append("<p>idcomune:<input readonly=\"readonly\" type=\"text\" id=\"idcomune\" name=\"idcomune\" /></p>");
		sb.append("<p>provincia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" /></p>");
		sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" /></p> ");
		sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" /></p>");
		
		sb.append("<p>latitudine:<input type=\"text\" name=\"latitudine\"></p>");
		sb.append("<p>longitudine:<input type=\"text\" name=\"longitudine\"></p>");
		sb.append("<p>quota:<input type=\"text\" name=\"quota\"></p>");
		sb.append("<p>esposizione:<input type=\"text\" name=\"esposizione\"></p>");
		sb.append( "<p> data inizio <input type=\"text\" id=\"datainizio\" name=\"datainizio\" ></p>");
		sb.append( "<p> data fine <input type=\"text\" id=\"datafine\" name=\"datafine\" ></p>");

		for(Sensori sens:ControllerDatabase.prendiTuttiSensori()){
			sb.append("<input type=\"checkbox\" name=\"tipo_IT\" value=\""+sens.getSensori_IT()+"\" > "+sens.getSensori_IT()+" ");
		}
		
		sb.append("<br>");
			sb.append(	"			<input type=\"hidden\" name=\"operazione\" value=\"ricercaStazione\">" +
				"			<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	
	
	
	public static String scegliStazioniMetereologicheDeltaT() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append(" <table class=\"table\"><thead> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr></thead>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append("<tbody>");
			sb.append("<tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			sb.append("</tbody>");
		}
		sb.append("</table>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniDeltaT\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologicheT() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append("<table class=\"table\"><thead> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr> </thead>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append("<tbody>");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			sb.append("</tbody>");
		}
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"eleborazioniTemperatura\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String scegliStazioniMetereologichePrecipitazioni() throws SQLException{
		ArrayList<StazioneMetereologica>  ap = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb = new StringBuilder();
		Calendar data = new GregorianCalendar();
		data.add(Calendar.MONTH, 1);
		sb.append(HTMLScript.scriptData("data"));
		sb.append("<table class=\"table\"><thead> <tr> <th>Nome</th>  <th>comune</th> <th> seleziona</th> </tr> </thead>");
		for(StazioneMetereologica s: ap){
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append("<tbody>");
			sb.append(" <tr> <td>"+s.getNome()+" </td>  <td> "+s.getUbicazione().getLocAmm().getComune()+"</td> <td> <input type=\"checkbox\" name=\"id\" value=\""+s.getIdStazioneMetereologica()+"\" > </td> </tr>");
			sb.append("</tbody>");
		}
		sb.append("</table>");
		sb.append("<p>aggregazione in giorni:<input type=\"text\" name=\"aggregazione\" \"></p>");
		sb.append("<p>finestra in giorni:<input type=\"text\" name=\"finestra\" \"></p>");
		sb.append("<p> data  <input type=\"text\" id=\"data\" name=\"data\" \"></p>");
		sb.append("			<input type=\"hidden\" name=\"operazione\" value=\"precipitazioni\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" +
				"		</form>");
		return sb.toString();
	}
	
	public static String mostraStazioniMaps() throws SQLException{
		ArrayList<StazioneMetereologica>  sm = ControllerDatabase.prendiTutteStazioniMetereologiche(); 
		StringBuilder sb=new StringBuilder();
		
		sb.append("<div id=\"gmap\" style=\"width:400px;height:500px\"></div>");
		sb.append("<script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js\"></script>");
		sb.append("<script>");
		sb.append("var map_center = new google.maps.LatLng(0.1700235000, 20.7319823000);");
		sb.append("var map = new google.maps.Map(document.getElementById(\"gmap\"), {");
		sb.append("zoom:1,");
		sb.append("center:map_center,");
		sb.append("mapTypeId:google.maps.MapTypeId.HYBRID});");
		sb.append("");
		sb.append("var pos;");
		sb.append("var marker;");
		sb.append("var marker_list = [];");
		sb.append("var stazioni = {};");
		for(int i =0;i<sm.size();i++){
			sb.append("stazioni["+i+"] = {");
			sb.append(" nome: \" "+sm.get(i).getNome()+" \",");
			sb.append(" comune: \" "+sm.get(i).getUbicazione().getLocAmm().getComune()+" \",");
			sb.append(  " x: "+sm.get(i).getUbicazione().getCoordinate().getX()+",");
			sb.append(  " y: "+sm.get(i).getUbicazione().getCoordinate().getY()+"");
			sb.append(  " };");
		}
		sb.append("for (var i = 0; i < "+sm.size()+"; i++) { "); 
		sb.append("pos = new google.maps.LatLng( stazioni[i].x , stazioni[i].y );");
		sb.append("marker = new google.maps.Marker({");
		sb.append("position:pos,");
		sb.append("map:map,");
		sb.append("title:'Title'");
		sb.append("});");
		sb.append("var infowindow = new google.maps.InfoWindow();");
		sb.append("google.maps.event.addListener(marker, 'click', (function(marker, i) {");
		sb.append("	return function() {");
		sb.append("infowindow.setContent(\"nome: \"+stazioni[i].nome+\" <br> comune: \"+stazioni[i].comune+\"\");" );
		sb.append("infowindow.open(map, marker);");
		sb.append("}");
		sb.append("})(marker, i));");
		sb.append("marker_list.push(marker);");
		sb.append("}");
		sb.append("var markerCluster = new MarkerClusterer(map, marker_list, {");
		sb.append("gridSize:40,");
		sb.append("minimumClusterSize: 4,");
		sb.append("calculator: function(markers, numStyles) {");
		sb.append("return {" );
		sb.append("text: markers.length,");
		sb.append("index: numStyles");
		sb.append("};");
		sb.append("}");
		sb.append("});");
		sb.append("</script>");
		return sb.toString();
	}
	
	public static String UploadCSV(int idstazione){
		StringBuilder sb = new StringBuilder();
		sb.append(""+HTMLScript.scriptData("data")+"");
		sb.append("<form  action=\"/DBAlps/Servlet\" method=\"POST\" enctype=\"multipart/form-data\">");
		sb.append("<input type=\"file\" name=\"files[]\" multiple>");
		sb.append("<input type=\"hidden\" name=\"idStazioneMetereologica\" value=\""+idstazione+"\">");
		sb.append("<select name=\"tabella\">");
		sb.append(" <option value=\"temperatura_avg$temperaturaavg\">temperatura media</option>");
		sb.append(" </select>");
		sb.append("<input type=\"text\" id=\"data\" name=\"data\">");
		sb.append("<input id=\"ora\" name=\"ora\" >");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"uploadCSVDatiClimatici\">");
		sb.append("<input type=\"submit\" name=\"invia\" value=\"carica\"/>");
		sb.append("</form>");
		return sb.toString();
	}
}
