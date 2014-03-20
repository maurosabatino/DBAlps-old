package html;

import java.sql.SQLException;
import java.util.ArrayList;

import controller.*;
import bean.*;

public class HTMLProcesso {
	
	public static String mostraTuttiProcessi() throws SQLException{
		ArrayList<Processo>  ap = ControllerDatabase.prendiTuttiProcessi(); 
		StringBuilder sb = new StringBuilder();
		
		/*script per google maps*///centrerei la mappa al centro delle alpi 
		
		
		sb.append("<table> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(Processo p: ap){
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td></tr>");
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
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}
}
