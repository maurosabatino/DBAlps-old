package html;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bean.Processo;
import controller.ControllerDatabase;

public class HTMLProcesso {
	
	public static String mostraTuttiProcessi() throws SQLException{
		ArrayList<Processo>  ap = ControllerDatabase.prendiTuttiProcessi(); 
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		/*script per google maps*///centrerei la mappa al centro delle alpi 
		
		
		sb.append("<table> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> dettagli</th> <th> modifica</th> <th>sottobacino</th></tr>");
		for(Processo p: ap){
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+dateFormat.format(p.getData())+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ " <td>"+p.getUbicazione().getLocAmm().getNazione()+"</td> <td>"+p.getUbicazione().getLocIdro().getIdSottobacino()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td>"
							+ "<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="+p.getIdProcesso()+"\">modifica</a> </td></tr>");
		}
		return sb.toString();
	}
	public static String mostraProcesso(int idProcesso) throws SQLException{
		Processo p = ControllerDatabase.prendiProcesso(idProcesso);
		StringBuilder sb = new StringBuilder();
		
		//script per google maps
		sb.append("<style type=\"text/css\">"
				+ "html { height: 100% }"
				+ "body { height: 100%; margin: 0; padding: 0 }"
				+ "#map-canvas { height: 100% }"
				+ " </style>");
		sb.append("<script type=\"text/javascript\""
				+ "src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false\">"
				+ "</script>"
				+ " <script type=\"text/javascript\">"
				+ "function initialize() {"
				+ "var myLatlng = new google.maps.LatLng("+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+");"
				+ "var mapOptions = {"
					+ "center: new google.maps.LatLng("+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+"),"
					+ "zoom: 8,"
					+ "mapTypeId: google.maps.MapTypeId.SATELLITE"
					+ "};"
				+ "var map = new google.maps.Map(document.getElementById(\"map-canvas\"),"
					+ "mapOptions);"
				+ "var marker = new google.maps.Marker({"
					+ "position: myLatlng,"
					+ "title:\""+p.getNome()+"\""
					+ "});"
				+ "marker.setMap(map);"
				+ "var contentString ='<p>nome processo:"+p.getNome()+"</p>'+"
						+ "'<p>coordinate: "+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+"</p>';"
						
				+ "var infowindow = new google.maps.InfoWindow({"
					+ "content: contentString"
					+ "});"
				+ "google.maps.event.addListener(marker, 'click', function() {"
					+ " infowindow.open(map,marker);"
					+ "});"
				+ "}"
				+ "google.maps.event.addDomListener(window, 'load', initialize);"
				+ " </script>");
		
		
		sb.append("<table> <tr> <th>Nome</th> <th>data</th> <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		sb.append("<div id=\"map-canvas\"/>");
		return sb.toString();
	}
	public static String mostraCercaProcessi(ArrayList<Processo> ap) throws SQLException{
		
		StringBuilder sb = new StringBuilder();
		sb.append("<table> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(Processo p: ap){
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td>"+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}
	
	
	public static String modificaProcesso(Processo p){
		StringBuilder sb = new StringBuilder();
		Calendar cal = new GregorianCalendar();
		cal.setTime(p.getData());
		System.out.println(cal.toString());
		cal.add(Calendar.MONTH, 1);
	
		sb.append(scriptData());
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">"
				+ "<p>Nome:<input type=\"text\" name=\"nome\" value=\""+p.getNome()+"\"></p>"
				+ "<p>Data:</p>"
				+ "<p> <input type=\"text\" id=\"data\" name=\"data\" value=\""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH)+"\"></p>"
				+ "<p> <input type=\"time\" id=\"ora\" name=\"ora\" value=\""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+"\" > </p>"
				+ "<p>descrizione:<input type=\"text\" name=\"descrizione\" value=\""+p.getDescrizione()+"\" ></p>"
				+ "<p>note:<input type=\"text\" name=\"note\" value=\" "+p.getNote()+" \"></p>"
				+ "<p>superficie:<input type=\"text\" name=\"superficie\" value=\" "+p.getSuperficie()+" \"></p>"
				+ "<p>larghezza:<input type=\"text\" name=\"larghezza\" value=\""+p.getLarghezza()+"\"></p>"
				+ "<p>altezza:<input type=\"text\" name=\"altezza\" value=\" "+p.getAltezza()+" \"></p>"
				+ "<p>volume_specifico<input type=\"number\" name=\"volume_specifico\" value=\" "+p.getVolumeSpecifico()+"\"></p>"
				+ "<p>modifica ubicazione</p>"
				+"<p>bacino:<input type=\"text\" name=\"bacino\" value=\""+p.getUbicazione().getLocIdro().getBacino()+" \"></p>"
				+ "<p>sottobacino:<input type=\"text\" name=\"sottobacino\" value=\""+p.getUbicazione().getLocIdro().getSottobacino()+"\">"
				+ "<p>comune:<input type=\"text\" name=\"comune\" value=\""+p.getUbicazione().getLocAmm().getComune()+"\"></p>"
				+ "<p>provncia:<input type=\"text\" name=\"provincia\" value=\""+p.getUbicazione().getLocAmm().getProvincia()+"\"></p>"
				+ "<p>regione:<input type=\"text\" name=\"regione\" value=\""+p.getUbicazione().getLocAmm().getRegione()+"\"></p> "
				+ "<p>nazione:<input type=\"text\" name=\"nazione\" value=\""+p.getUbicazione().getLocAmm().getNazione()+"\"></p>"
				+ "<p>latitudine:<input type=\"text\" name=\"latitudine\" value=\""+p.getUbicazione().getCoordinate().getX()+"\"></p>"
				+ "<p>longitudine:<input type=\"text\" name=\"longitudine\"value=\""+p.getUbicazione().getCoordinate().getY()+"\"></p>"
				+ "<p>quota:<input type=\"text\" name=\"quota\" value=\""+p.getUbicazione().getQuota()+"\"></p>"
				+ "<p>esposizione:<input type=\"text\" name=\"esposizione\" value=\""+p.getUbicazione().getEsposizione()+"\"></p>"
				+ "<input type=\"hidden\" name=\"operazione\" value=\"modificaProcesso\">"
				+ "<input type=\"hidden\" name=\"idProcesso\" value=\""+p.getIdProcesso()+"\"/>"
				+ "<input type=\"submit\" name =\"submit\" value=\"OK\">"
				+ "</form>");
			
		
		
		return sb.toString();
	}
	
	public static String scriptData(){
		StringBuilder sb = new StringBuilder();
		sb.append("<script src=\"js/jquery-1.11.0.js\"></script>"
				+ "<script src=\"js/jquery-ui-1.10.4.custom.js\"></script>"
				+ "<script src=\"js/globalize.js\"></script>"
				+ "<script src=\"js/globalize.culture.de-DE.js\"></script>");
			sb.append("<script>"
				+ "$(function() {"
				+ "$( \"#data\" ).datepicker({"
				+ "changeMonth: true,"
				+ "changeYear: true,"
				+ "dateFormat: \"yy-mm-dd\"});"
				+ "});</script>"
				+"<script>");
				
				sb.append("$.widget( \"ui.timespinner\", $.ui.spinner, {"
				+ "options: {"
				+ "step: 60 * 1000,"
				+ "page: 60"
				+ "},"
				+ "_parse: function( value ) {"
				+ "if ( typeof value === \"string\" ) {"
				+ "if ( Number( value ) == value ) {"
				+ "return Number( value );"
				+ "}"
				+ "return +Globalize.parseDate( value );"
				+ "}"
				+ "return value;"
				+ "},"
				+ "_format: function( value ) {"
				+ "return Globalize.format( new Date(value), \"t\" );"
				+ "}"
				+ "});"
				+ "$(function() {"
				+ "$( \"#ora\" ).timespinner();"
				+ "var current = $( \"#ora\" ).timespinner( \"value\" );"
				+ "Globalize.culture( \"de-DE\");"
				+ "$( \"#ora\" ).timespinner( \"value\", current );"
				+ "});"
				+ "</script>");
		return sb.toString();
	}
}
