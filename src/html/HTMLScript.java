package html;

import bean.Processo;

public class HTMLScript {
	public static String dialogMaps(){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("var map;");
		sb.append("var coords = new Object();");
		sb.append("coords.lat = 45.57560020947792;");
		sb.append("coords.lng = 9.613037109375;");
		sb.append("$(document).ready(function() {");
		sb.append(" $( \"#map_container\" ).dialog({");
		sb.append("autoOpen:false,");
		sb.append("width: 800,");
		sb.append("height: 555,");
		sb.append("resizeStop: function(event, ui) {google.maps.event.trigger(map, 'resize') },");
		sb.append("open: function(event, ui) {google.maps.event.trigger(map, 'resize'); },");   
		sb.append("buttons: {");
		sb.append("\"conferma\": function(){");
		sb.append(" document.getElementById(\"latitudine\").value = document.getElementById(\"lati\").value;");
		sb.append("document.getElementById(\"longitudine\").value = document.getElementById(\"long\").value;");
		sb.append("$( this ).dialog(\"close\" );");
		sb.append("}");
		sb.append("}");
		sb.append("}); "); 
		sb.append("$( \"#showMap\" ).click(function() {");           
		sb.append("$( \"#map_container\" ).dialog( \"open\" );");
		sb.append("map.setCenter(new google.maps.LatLng(coords.lat, coords.lng), 10);");
		sb.append("google.maps.event.addListener(map, \"rightclick\", function(event) {");
		sb.append(" var lat = event.latLng.lat();");
		sb.append("var lng = event.latLng.lng();");
		sb.append("document.getElementById(\"lati\").value = lat;");
		sb.append("document.getElementById(\"long\").value = lng;");        	    
		sb.append("});");
		sb.append("return false;");
		sb.append("});");      
		sb.append("$(  \"input:submit,input:button, a, button\", \"#controls\" ).button();");
		sb.append("initialize();");
		sb.append("});");
		sb.append("function initialize() {");      
		sb.append("var latlng = new google.maps.LatLng(coords.lat, coords.lng);");
		sb.append("var myOptions = {");
		sb.append("zoom: 6,");
		sb.append("center: latlng,");
		sb.append("mapTypeId: google.maps.MapTypeId.SATELLITE");
		sb.append("};");
		sb.append(" map = new google.maps.Map(document.getElementById(\"map_canvas\"),  myOptions);");
		sb.append(" }"); 
		sb.append("</script>");
		return sb.toString();
	}
	
	public static String mostraMappaProcesso(Processo p){
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\"");
		sb.append("src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false\">");
		sb.append("</script>");
		sb.append(" <script type=\"text/javascript\">");
		sb.append("function initialize() {");
		sb.append("var myLatlng = new google.maps.LatLng("+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+");");
		sb.append("var mapOptions = {");
		sb.append("center: new google.maps.LatLng("+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+"),");
		sb.append("zoom: 8,");
		sb.append("mapTypeId: google.maps.MapTypeId.SATELLITE");
		sb.append("};");
		sb.append("var map = new google.maps.Map(document.getElementById(\"mappa\"),");
		sb.append("mapOptions);");
		sb.append("var marker = new google.maps.Marker({");
		sb.append("position: myLatlng,");
		sb.append("title:\""+p.getNome()+"\"");
		sb.append("});");
		sb.append("marker.setMap(map);");
		sb.append("var contentString ='<p>nome processo:"+p.getNome()+"</p>'+");
		sb.append("'<p>coordinate: "+p.getUbicazione().getCoordinate().getX()+","+p.getUbicazione().getCoordinate().getY()+"</p>';");
		sb.append("var infowindow = new google.maps.InfoWindow({");
		sb.append("content: contentString");
		sb.append("});");
		sb.append("google.maps.event.addListener(marker, 'click', function() {");
		sb.append("infowindow.open(map,marker);");
		sb.append("});");
		sb.append("}");
		sb.append("google.maps.event.addDomListener(window, 'load', initialize);");
		sb.append("</script>");
		return sb.toString();
	}
	public static String scriptData(String nome){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>"
				+ "$(function() {"
				+ "$( \"#"+nome+"\" ).datepicker({"
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
	
	public static String scriptAutocompleteSensoriMultiplo(String json,String loc){
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("$(function() {");
		sb.append("var sensori = "+json+";");
		sb.append("   function split( val ) {");
		sb.append("    return val.split( /,\\s*/ );");
		sb.append("   }");
		sb.append("   function extractLast( term ) {");
		sb.append("     return split( term ).pop();");
		sb.append("   }");
		sb.append("   $( \"#tipo_"+loc+"\" )");
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
		sb.append("      sensori, extractLast( request.term ) ) );");
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
	
	public static String scriptAutocompleteSitoStazione(String json,String loc){
    StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var sitostazione ="+json+";");
    sb.append("$(\"#caratteristiche_"+loc+"\").autocomplete({");
	sb.append("   minLength: 0,");
    sb.append("source: sitostazione,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#caratteristiche_"+loc+"\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idsitostazione').val(ui.item.idSitoStazioneMetereologica);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}

	public static String scriptAutocompleteEnte(String json){
		StringBuilder sb = new StringBuilder();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(function() {");
    sb.append("var nomeente ="+json+";");
    sb.append("$(\"#ente\").autocomplete({");
    sb.append("   minLength: 0,");
    sb.append("source: nomeente,");
    sb.append("focus: function( event, ui ) {");
    sb.append("$(\"#ente\" ).val( ui.item.label);");
    sb.append("return false;");
    sb.append("},");
    sb.append("select: function(event, ui) {");
    sb.append("$('#idEnte').val(ui.item.idEnte);");
    sb.append("}");
    sb.append("});");
    sb.append("});");
    sb.append("</script>");
 	return sb.toString();
	}
	public static String scriptControlloInserimento(String controllo){
		StringBuilder sb = new StringBuilder();
		sb.append("if (modulo."+controllo+".value == \"\") {");
		sb.append("alert(\"Campo -"+controllo+"- mancante.Modulo non spedito.\");");
		sb.append("modulo."+controllo+".focus();");
		sb.append("return false;");
		sb.append("}");
	 	return sb.toString();
	}
	
	public static String scriptFilter(){
		StringBuilder sb = new StringBuilder();

		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>");
		sb.append("<script src=\"js/jquery.filtertable.js\"></script>");
		sb.append("<script>");


		sb.append("$(document).ready(function() {");
		sb.append("$('table').filterTable();");
		sb.append("});");
		sb.append("</script>");
	 	return sb.toString();

	}
}
