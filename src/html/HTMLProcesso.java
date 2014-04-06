package html;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bean.Processo;
import controller.ControllerDatabase;
import controller.ControllerJson;

public class HTMLProcesso {
	
	public static String formInserisciProcesso(String path) throws SQLException{
		StringBuilder sb = new StringBuilder();
		String loc ="IT";
		ControllerJson.createJsonProprietaTermiche(path);
		ControllerJson.createJsonDanni(path);
		ControllerJson.createJsonEffettiMorfologici(path);
		ControllerJson.createJsontipologiaProcesso(path);
		ControllerJson.craeteJsonstatoFratturazione(path);
		ControllerJson.CreateJsonClasseVolume(path);
		ControllerJson.createJsonLitologia(path);
		ControllerJson.CreateJsonSitoProcesso(path);
		ControllerJson.CreateJsonLocazioneIdrologica(path);
		
		
		sb.append(scriptData());
		
		
		sb.append(scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc),loc));
		sb.append(scriptAutocompleteDanniMultiplo(ControllerJson.getJsonDanni(path, loc), loc));
		sb.append(scriptAutocompleteEffettiMorfologiciMultiplo(ControllerJson.getJsonEffettiMorfologici(path, loc), loc));
		sb.append(scriptAutocompleteTipologiaProcesso(ControllerJson.getJsonipologiaProcesso(path, loc), loc));
		sb.append(scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		sb.append(scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(loc.equals("IT")){
		
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			
			sb.append("<p>Nome:<input type=\"text\" name=\"nome\" value=\"nome\"></p>");
			sb.append("<p>Data:</p>");
			sb.append("<p> <input type=\"text\" id=\"data\" name=\"data\"></p>");
			sb.append("<p> <input type=\"time\" id=\"ora\" name=\"ora\" > </p>");
			sb.append("<p>descrizione:<input type=\"text\" name=\"descrizione\" value=\"descrizione\" ></p>");
			sb.append("<p>note:<input type=\"text\" name=\"note\" value=\"not\"></p>");
			sb.append("<p>superficie:<input type=\"text\" name=\"superficie\" value=\"12\"></p>");
			sb.append("<p>larghezza:<input type=\"text\" name=\"larghezza\" value=\"12\"></p>");
			sb.append("<p>altezza:<input type=\"text\" name=\"altezza\" value=\"12\"></p>");
			sb.append("<p>volume specifico<input type=\"number\" name=volumespecifico value=\"12\"></p>");
			sb.append("<p>classe volume<input type=\"text\" id=\"intervallo\" name=intervallo></p>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" />");
		
			sb.append("<p> tipologia del processo:<input type=\"text\" id=\"tpnome_IT\" name=\"tpnome_IT\" /></p>");
		
			sb.append("<p>dati sull'ubicazione</p>");
			sb.append("<p>sottobacino:<input type=\"text\" id=\"sottobacino\" name=\"sottobacino\">");
			sb.append("<p>bacino:<input type=\"text\"id=\"bacino\" name=\"bacino\"></p>");
			sb.append("<input  type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" />");
		
			sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" /> </p>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
			sb.append("<p>provncia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" /></p>");
			sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" /></p> ");
			sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" /></p>");
			sb.append("<p>latitudine:<input type=\"text\" name=\"latitudine\" value=\"12\"></p>");
			sb.append("<p>longitudine:<input type=\"text\" name=\"longitudine\"value=\"14\"></p>");
			sb.append("<p>quota:<input type=\"text\" name=\"quota\" value=\"12\"></p>");
			sb.append("<p>esposizione:<input type=\"text\" name=\"esposizione\" value=\"nord\"></p>");
		
			sb.append("<p>dati sul sito</p>");
			sb.append("<p> caratteristiche sito:<input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" />");
		
			sb.append("<p>effetti morfologici e danni</p>");
			sb.append("<p> danni:<input type=\"text\" id=\"dtipo_IT\" name=\"dtipo_IT\" /></p>");
			sb.append("<p> effetti morfologici:<input type=\"text\" id=\"emtipo_IT\" name=\"emtipo_IT\" /></p>");
		
			sb.append("<p>dati sulla litologia</p>");
			sb.append("<p> litologia:<input type=\"text\" id=\"nomeLitologia_"+loc+"\" name=\"nomeLitologia_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
			sb.append("<p> proprieta termiche:<input type=\"text\" id=\"proprietaTermiche_"+loc+"\" name=\"proprietaTermiche_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
			sb.append("<p> stato fratturazione:<input type=\"text\" id=\"statoFratturazione_"+loc+"\" name=\"statoFratturazione_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
		
			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciProcesso\">");
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
		
			sb.append("</form>");
		}else if(loc.equals("ENG")){
			//da fare
		}
	return sb.toString();
	}
	
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
	
	/*
	 * script autocomplete
	 */
	public static String scriptAutocompleteLocAmm(String json){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var states ="+json+";");
    sb.append("$(\"#comune\").autocomplete({");
    sb.append("source: states,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#comune\" ).val( ui.item.comune);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idcomune').val(ui.item.idComune);");
    sb.append("$('#provincia').val(ui.item.provincia);	");
    sb.append("$('#regione').val(ui.item.regione);");
    sb.append("$('#nazione').val(ui.item.nazione);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	public static String scriptAutocompleteLocIdro(String json){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var idro ="+json+";");
    sb.append("$(\"#sottobacino\").autocomplete({");
    sb.append("source: idro,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#sottobacino\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idSottobacino').val(ui.item.idSottobacino);");
    sb.append("$('#bacino').val(ui.item.bacino);	");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	
	public static String scriptAutocompleteStatoFratturazione(String json,String loc){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var statoFratturazione ="+json+";");
    sb.append("$(\"#statoFratturazione_"+loc+"\").autocomplete({");
    sb.append("source: statoFratturazione,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#statoFratturazione_"+loc+"\" ).val(ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idStatoFratturazione').val(ui.item.idStatoFratturazione);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	
	public static String scriptAutocompleteProprietaTermiche(String json,String loc){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var proprietaTermiche ="+json+";");
    sb.append("$(\"#proprietaTermiche_"+loc+"\").autocomplete({");
    sb.append("source: proprietaTermiche,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#proprietaTermiche_"+loc+"\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idProprietaTermiche').val(ui.item.idProprietaTermiche);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	public static String scriptAutocompleteLitologia(String json,String loc){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var proprietaTermiche ="+json+";");
    sb.append("$(\"#proprietaTermiche_"+loc+"\").autocomplete({");
    sb.append("source: proprietaTermiche,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#proprietaTermiche_"+loc+"\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idProprietaTermiche').val(ui.item.idProprietaTermiche);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	
	public static String scriptAutocompleteClasseVolume(String json){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var classeVolume ="+json+";");
    sb.append("$(\"#intervallo\").autocomplete({");
    sb.append("source: classeVolume,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#intervallo\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idclasseVolume').val(ui.item.idClasseVolume);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	
	public static String scriptAutcompleteLitologia(String json,String loc){
		StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var litologia ="+json+";");
    sb.append("$(\"#nomeLitologia_"+loc+"\").autocomplete({");
    sb.append("source: litologia,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#nomeLitologia_"+loc+"\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idLitologia').val(ui.item.idLitologia);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	public static String scriptAutocompleteSitoProcesso(String json,String loc){
		StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var sitoProcesso ="+json+";");
    sb.append("$(\"#caratteristicaSito_"+loc+"\").autocomplete({");
    sb.append("source: sitoProcesso,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#caratteristicaSito_"+loc+"\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idsito').val(ui.item.idSito);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	
	
	/*
	 * 
	 * multipli autocomplete
	 * 
	 * */
	
	public static String scriptAutocompleteDanniMultiplo(String json,String loc){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("$(function() {");
		sb.append("var danni = "+json+";");
		sb.append("   function split( val ) {");
		sb.append("    return val.split( /,\\s*/ );");
		sb.append("   }");
		sb.append("   function extractLast( term ) {");
		sb.append("     return split( term ).pop();");
		sb.append("   }");
		sb.append("   $( \"#dtipo_IT\" )");
		sb.append("  .bind( \"keydown\", function( event ) {");
		sb.append("   if ( event.keyCode === $.ui.keyCode.TAB &&");
		sb.append("       $( this ).data( \"ui-autocomplete\" ).menu.active ) {");
		sb.append("      event.preventDefault();");
		sb.append("    }");
		sb.append("  })");
		sb.append("  .autocomplete({");
		sb.append("   minLength: 0,");
		sb.append("   source: function( request, response ) {");
		sb.append("    response( $.ui.autocomplete.filter(");
		sb.append("      danni, extractLast( request.term ) ) );");
		sb.append("  },");
		sb.append("  focus: function() {");   
		sb.append("    return false;");
		sb.append("    },");
		sb.append("   select: function( event, ui ) {");
		sb.append("      var terms = split( this.value );");
		sb.append("     terms.pop();");
		sb.append("     terms.push( ui.item.value );");
		sb.append("    terms.push(\"\" );");
		sb.append("    this.value = terms.join( \", \" );");
		sb.append("    return false;");
		sb.append("  }");
		sb.append(" });");
		sb.append(" });");
		sb.append(" </script>");
		return sb.toString();
	}
	public static String scriptAutocompleteEffettiMorfologiciMultiplo(String json,String loc){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("$(function() {");
		sb.append("var effettiMorfologici = "+json+";");
		sb.append("   function split( val ) {");
		sb.append("    return val.split( /,\\s*/ );");
		sb.append("   }");
		sb.append("   function extractLast( term ) {");
		sb.append("     return split( term ).pop();");
		sb.append("   }");
		sb.append("   $( \"#emtipo_IT\" )");
		sb.append("  .bind( \"keydown\", function( event ) {");
		sb.append("   if ( event.keyCode === $.ui.keyCode.TAB &&");
		sb.append("       $( this ).data( \"ui-autocomplete\" ).menu.active ) {");
		sb.append("      event.preventDefault();");
		sb.append("    }");
		sb.append("  })");
		sb.append("  .autocomplete({");
		sb.append("   minLength: 0,");
		sb.append("   source: function( request, response ) {");
		sb.append("    response( $.ui.autocomplete.filter(");
		sb.append("      effettiMorfologici, extractLast( request.term ) ) );");
		sb.append("  },");
		sb.append("  focus: function() {");   
		sb.append("    return false;");
		sb.append("    },");
		sb.append("   select: function( event, ui ) {");
		sb.append("      var terms = split( this.value );");
		sb.append("     terms.pop();");
		sb.append("     terms.push( ui.item.value );");
		sb.append("    terms.push(\"\" );");
		sb.append("    this.value = terms.join( \", \" );");
		sb.append("    return false;");
		sb.append("  }");
		sb.append(" });");
		sb.append(" });");
		sb.append(" </script>");
		return sb.toString();
	}
	public static String scriptAutocompleteTipologiaProcesso(String json,String loc){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("$(function() {");
		sb.append("var tipologiaProcesso = "+json+";");
		sb.append("   function split( val ) {");
		sb.append("    return val.split( /,\\s*/ );");
		sb.append("   }");
		sb.append("   function extractLast( term ) {");
		sb.append("     return split( term ).pop();");
		sb.append("   }");
		sb.append("   $( \"#tpnome_IT\" )");
		sb.append("  .bind( \"keydown\", function( event ) {");
		sb.append("   if ( event.keyCode === $.ui.keyCode.TAB &&");
		sb.append("       $( this ).data( \"ui-autocomplete\" ).menu.active ) {");
		sb.append("      event.preventDefault();");
		sb.append("    }");
		sb.append("  })");
		sb.append("  .autocomplete({");
		sb.append("   minLength: 0,");
		sb.append("   source: function( request, response ) {");
		sb.append("    response( $.ui.autocomplete.filter(");
		sb.append("      tipologiaProcesso, extractLast( request.term ) ) );");
		sb.append("  },");
		sb.append("  focus: function() {");   
		sb.append("    return false;");
		sb.append("    },");
		sb.append("   select: function( event, ui ) {");
		sb.append("      var terms = split( this.value );");
		sb.append("     terms.pop();");
		sb.append("     terms.push( ui.item.value );");
		sb.append("    terms.push(\"\" );");
		sb.append("    this.value = terms.join( \", \" );");
		sb.append("    return false;");
		sb.append("  }");
		sb.append(" });");
		sb.append(" });");
		sb.append(" </script>");
		return sb.toString();
	}
	
}
