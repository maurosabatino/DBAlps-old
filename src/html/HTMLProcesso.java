package html;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import bean.Danni;
import bean.EffettiMorfologici;
import bean.Processo;
import bean.TipologiaProcesso;
import controller.ControllerDatabase;
import controller.ControllerJson;

public class HTMLProcesso {
	
	public static String formInserisciProcesso(String path,String loc) throws SQLException{
		StringBuilder sb = new StringBuilder();
		
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
		sb.append(scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		sb.append(scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(loc.equals("IT")){
		
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"> <input type=\"text\" name=\"nome\" class=\"form-control\" placeholder=\"nome\" value=\"nome\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"> <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" placeholder=\"data\"></div>");
			sb.append(" <div class=\"col-xs-6 col-md-4\"> <input type=\"time\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");
			
			sb.append("descrizione:<input type=\"text\" name=\"descrizione\" class=\"form-control\" value=\"descrizione\" >");
			sb.append("note:<input type=\"text\" name=\"note\" class=\"form-control\" value=\"not\">");
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-3\">superficie:<input type=\"text\" name=\"superficie\" class=\"form-control\" value=\"12\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\">larghezza:<input type=\"text\" name=\"larghezza\" class=\"form-control\" value=\"12\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\">altezza:<input type=\"text\" name=\"altezza\" class=\"form-control\" value=\"12\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\">volume specifico<input type=\"number\" name=volumespecifico class=\"form-control\" value=\"12\"></div>");
			sb.append("</div>");
			
			sb.append("</div> </div>");
			
			
			sb.append("<p>classe volume<input type=\"text\" id=\"intervallo\" name=intervallo></p>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" />");
				for(TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso())//da fare col json
			sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+tp.getNome_IT()+"\"/> "+tp.getNome_IT()+"");
		
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
			sb.append("<p>danni</p>");
			for(Danni d:ControllerDatabase.prendiDanni()){
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""+d.getTipo_IT()+"\"/>"+d.getTipo_IT()+"");
			}
			sb.append("<p>effetti morfologici:</p>");
			for(EffettiMorfologici em:ControllerDatabase.prendiEffettiMOrfologici()){
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""+em.getTipo_IT()+"\" />"+em.getTipo_IT()+"");
			}
		
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
		
		
		sb.append("<table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> dettagli</th> <th> modifica</th></tr>");
		for(Processo p: ap){
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+dateFormat.format(p.getData())+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ " <td>"+p.getUbicazione().getLocAmm().getNazione()+"</td> "
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
		
		
		sb.append("<table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table>");
		sb.append("<div id=\"map-canvas\"/>");
		return sb.toString();
	}
	
	public static String formCercaProcessi(String path,String loc) throws SQLException{
StringBuilder sb = new StringBuilder();
		
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
		sb.append(scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		sb.append(scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(loc.equals("IT")){
		
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"> <input type=\"text\" name=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"> <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" placeholder=\"data\"></div>");
			sb.append(" <div class=\"col-xs-6 col-md-4\"> <input type=\"time\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");
			
			
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-2\">Superficie<input type=\"text\" name=\"superficie\" class=\"form-control\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\">Larghezza<input type=\"text\" name=\"larghezza\" class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\">Altezza<input type=\"text\" name=\"altezza\" class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\">Volume Specifico<input type=\"number\" name=volumespecifico class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\">classe volume<input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" />");
			sb.append("</div>");
			sb.append("<br><h5> Tipologia Processo </h5>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			
			sb.append("<p>");
			for(TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso())
				sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+tp.getNome_IT()+"\"/> "+tp.getNome_IT()+" ");
			sb.append("</p>");
			
			sb.append("</div> </div>");
			sb.append("</div> </div>");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"> Sottobacino <input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"> Bacino <input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\"></div> ");
			sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
			sb.append("</div>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-3\">Comune<input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"/></div>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
			sb.append("<div class=\"col-xs-6 col-md-3\">Provncia<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\">Regione<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" /> </div>");
			sb.append("<div class=\"col-xs-6 col-md-3\">Nazione<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" /></div>");
			sb.append("</div>");
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\">Latitudine <input type=\"text\" name=\"latitudine\" class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\">Longitudine <input type=\"text\" name=\"longitudine\" class=\"form-control\"></div>");
			sb.append("</div>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\">Quota <input type=\"text\" name=\"quota\" class=\"form-control\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\">Esposizione <input type=\"text\" name=\"esposizione\" class=\"form-control\"></div>");
			sb.append("</div>");
			
			
			sb.append("</div> </div>");
		
			sb.append("<p>dati sul sito</p>");
			sb.append("<p> Caratteristiche Sito <input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" />");
		
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for(Danni d:ControllerDatabase.prendiDanni()){
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\" "+d.getTipo_IT()+"\"/> "+d.getTipo_IT()+" ");
			}
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for(EffettiMorfologici em:ControllerDatabase.prendiEffettiMOrfologici()){
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\" "+em.getTipo_IT()+"\" /> "+em.getTipo_IT()+" ");
			}
			sb.append("</p>");
			sb.append("</div> </div>");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("litologia:<input type=\"text\" id=\"nomeLitologia_"+loc+"\" name=\"nomeLitologia_"+loc+"\" class=\"form-control\"/></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\">proprieta termiche<input type=\"text\" id=\"proprietaTermiche_"+loc+"\" name=\"proprietaTermiche_"+loc+"\" class=\"form-control\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
			sb.append("<div class=\"col-xs-6 col-md-6\">stato fratturazione<input type=\"text\" id=\"statoFratturazione_"+loc+"\" name=\"statoFratturazione_"+loc+"\" class=\"form-control\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
			sb.append("</div>");
			sb.append("</div> </div>");
			
			sb.append("descrizione:<input type=\"text\" name=\"descrizione\" class=\"form-control\"  >");
			sb.append("note:<input type=\"text\" name=\"note\" class=\"form-control\" >");
			
			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
		
			sb.append("</form>");
		}else if(loc.equals("ENG")){
			//da fare
		}
	return sb.toString();
	}
	public static String mostraCercaProcessi(ArrayList<Processo> ap) throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("<table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for(Processo p: ap){
			sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td>"+p.getUbicazione().getLocAmm().getComune()+"</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}
	
	
	public static String modificaProcesso(Processo p,String path,String loc) throws SQLException{
		StringBuilder sb = new StringBuilder();
		Calendar cal = new GregorianCalendar();
		cal.setTime(p.getData());
		cal.add(Calendar.MONTH, 1);
		
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
		sb.append(scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append(scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc),loc));
		sb.append(scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		
	

		if(loc.equals("IT")){
			
			sb.append( "<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
			sb.append( "<p>Nome:<input type=\"text\" name=\"nome\" value=\""+p.getNome()+"\"></p>");
			
			sb.append( "<p> Data <input type=\"text\" id=\"data\" name=\"data\" value=\""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH)+"\"></p>");
			sb.append( "<p> Ora <input type=\"time\" id=\"ora\" name=\"ora\" value=\""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+"\" > </p>");
			sb.append( "<p>descrizione:<input type=\"text\" name=\"descrizione\" value=\""+p.getDescrizione()+"\" ></p>");
			sb.append( "<p>note:<input type=\"text\" name=\"note\" value=\" "+p.getNote()+" \"></p>");
			sb.append( "<p>superficie:<input type=\"text\" name=\"superficie\" value=\" "+p.getSuperficie()+" \"></p>");
			sb.append( "<p>larghezza:<input type=\"text\" name=\"larghezza\" value=\""+p.getLarghezza()+"\"></p>");
			sb.append( "<p>altezza:<input type=\"text\" name=\"altezza\" value=\" "+p.getAltezza()+" \"></p>");
			sb.append( "<p>volume_specifico<input type=\"number\" name=\"volumespecifico\" value=\" "+p.getVolumeSpecifico()+"\"></p>");
			sb.append("<p>classe volume<input type=\"text\" id=\"intervallo\" name=intervallo value=\""+p.getClasseVolume().getIntervallo()+"\"></p>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"value=\""+p.getClasseVolume().getIdClasseVolume()+"\" />");
			
			
			
			sb.append("<p> tipologia del processo</p>");
			for(TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()){
				boolean inserito = false;
				for(TipologiaProcesso tpp : p.getTipologiaProcesso()){
					if(tp.getNome_IT().equals(tpp.getNome_IT())){
						sb.append("<input type=\"checkbox\"  name=\"tpnome_IT\" value=\""+tp.getNome_IT()+"\" checked=\"checked\" /> "+tp.getNome_IT()+"");
						inserito = true;
					}
				}
				if(inserito==false)
				sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+tp.getNome_IT()+"\" /> "+tp.getNome_IT()+"");			
			}
			
			sb.append( "<p>ubicazione</p>");
			sb.append("<p>sottobacino:<input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" value=\""+p.getUbicazione().getLocIdro().getSottobacino()+"\">");
			sb.append("<p>bacino:<input type=\"text\"id=\"bacino\" name=\"bacino\" value=\""+p.getUbicazione().getLocIdro().getBacino()+"\"></p>");
			sb.append("<input  type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\" value=\""+p.getUbicazione().getLocIdro().getIdSottobacino()+"\" />");
			sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" value=\""+p.getUbicazione().getLocAmm().getComune()+"\" /> </p>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" value=\""+p.getUbicazione().getLocAmm().getIdComune()+"\"/>");
			sb.append("<p>provncia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" value=\""+p.getUbicazione().getLocAmm().getProvincia()+"\" /></p>");
			sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" value=\""+p.getUbicazione().getLocAmm().getRegione()+"\" /></p> ");
			sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" value=\""+p.getUbicazione().getLocAmm().getNazione()+"\" /></p>");
			sb.append( "<p>latitudine:<input type=\"text\" name=\"latitudine\" value=\""+p.getUbicazione().getCoordinate().getX()+"\"></p>");
			sb.append( "<p>longitudine:<input type=\"text\" name=\"longitudine\"value=\""+p.getUbicazione().getCoordinate().getY()+"\"></p>");
			sb.append( "<p>quota:<input type=\"text\" name=\"quota\" value=\""+p.getUbicazione().getQuota()+"\"></p>");
			sb.append( "<p>esposizione:<input type=\"text\" name=\"esposizione\" value=\""+p.getUbicazione().getEsposizione()+"\"></p>");
		
			sb.append("<p>dati sul sito</p>");
			sb.append("<p> caratteristiche sito:<input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" value=\""+p.getSitoProcesso().getCaratteristicaSito_IT()+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" value=\""+p.getSitoProcesso().getIdSito()+"\" />");
	
			sb.append("<p>effetti morfologici e danni</p>");
			sb.append("<p> danni</p>");
			for(Danni d : ControllerDatabase.prendiDanni()){
				boolean inserito = false;
				for(Danni da : p.getDanni()){
					if(d.getTipo_IT().equals(da.getTipo_IT())){
						sb.append("<input type=\"checkbox\"  name=\"dtipo_IT\" value=\""+d.getTipo_IT()+"\" checked=\"checked\" /> "+d.getTipo_IT()+"");
					inserito = true;
					}
				}
				if(inserito==false)
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""+d.getTipo_IT()+"\" /> "+d.getTipo_IT()+"");
			}
			
			
			sb.append("<p>effetti morfologici</p>");
			for(EffettiMorfologici em : ControllerDatabase.prendiEffettiMOrfologici()){
				boolean inserito = false;
				for(EffettiMorfologici emp : p.getEffetti()){
					if(emp.getTipo_IT().equals(em.getTipo_IT())){
						sb.append("<input type=\"checkbox\"  name=\"emtipo_IT\" value=\""+em.getTipo_IT()+"\" checked=\"checked\" /> "+em.getTipo_IT()+"");
					inserito = true;
					}
				}
				if(inserito==false)
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""+em.getTipo_IT()+"\" /> "+em.getTipo_IT()+"");
			}
	
			sb.append("<p>dati sulla litologia</p>");
			sb.append("<p> litologia:<input type=\"text\" id=\"nomeLitologia_"+loc+"\" name=\"nomeLitologia_"+loc+"\" value=\""+p.getLitologia().getNomeLitologia_IT()+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" value=\""+p.getLitologia().getidLitologia()+"\"/>");
			sb.append("<p> proprieta termiche:<input type=\"text\" id=\"proprietaTermiche_"+loc+"\" name=\"proprietaTermiche_"+loc+"\" value=\""+p.getProprietaTermiche().getProprieta_termiche_IT()+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" value=\""+p.getProprietaTermiche().getIdProprieta_termiche()+"\" />");
			sb.append("<p> stato fratturazione:<input type=\"text\" id=\"statoFratturazione_"+loc+"\" name=\"statoFratturazione_"+loc+"\"  value=\""+p.getStatoFratturazione().getStato_fratturazione_IT()+"\"/></p>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
		
		
			sb.append( "<input type=\"hidden\" name=\"operazione\" value=\"modificaProcesso\">");
			sb.append( "<input type=\"hidden\" name=\"idProcesso\" value=\""+p.getIdProcesso()+"\"/>");
			sb.append( "<input type=\"submit\" name =\"submit\" value=\"OK\">");
			sb.append( "</form>");
		}
		return sb.toString();
	}
	
	public static String scriptData(){
		StringBuilder sb = new StringBuilder();
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
