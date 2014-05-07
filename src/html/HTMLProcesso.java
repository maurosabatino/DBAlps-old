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
import bean.partecipante.Partecipante;
import bean.partecipante.Role;
import controller.ControllerDatabase;
import controller.ControllerJson;

public class HTMLProcesso {
	
	public static String formInserisciProcesso(String path,String loc,Partecipante part) throws SQLException{
		StringBuilder sb = new StringBuilder();
		if(part!=null && (part.hasRole(Role.AMMINISTRATORE) || part.hasRole(Role.AVANZATO))){
			sb.append(HTMLScript.scriptData("data"));
			sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
			sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc),loc));
			sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
			sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
			sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
			sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
			sb.append(HTMLScript.dialogMaps());
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
		
				sb.append("<p>comune:<input type=\"text\" id=\"comune\" name=\"comune\" required /> </p>");
				sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
				sb.append("<p>provncia:<input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" /></p>");
				sb.append("<p>regione:<input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" /></p> ");
				sb.append("<p>nazione:<input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\" /></p>");
			
				sb.append("<div id=\"controls\">");
				sb.append("<input type=\"button\" name=\"showMap\" value=\"Mappa\" id=\"showMap\" />");
				sb.append("latitudine:<input type=\"text\" name=\"latitudine\" id=\"latitudine\">");
				sb.append("longitudine:<input type=\"text\" name=\"longitudine\" id=\"longitudine\">");
				sb.append("</div>");
			
				sb.append(" <div id=\"map_container\" title=\"Location Map\">");    
				sb.append("<div id=\"map_canvas\" style=\"width:100%;height:80%;\"></div>");
				sb.append("<div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"lati\">Latitudine</label><input type=\"text\" id =\"lati\"name=\"lati\" class=\"form-control\" placeholder=\"latitudine\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"long\">Longitudine</label><input type=\"text\" id=\"long\" name=\"long\" class=\"form-control\"  placeholder=\"longitudine\"></div>");
				sb.append("</div>");
				sb.append("</div>");
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
		}else{
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}
	
	public static String mostraTuttiProcessi() throws SQLException{
		ArrayList<Processo>  ap = ControllerDatabase.prendiTuttiProcessi(); 
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		/*script per google maps*///centrerei la mappa al centro delle alpi 
		
		
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Modifica</th><th>Elimina</th></tr>");
		for(Processo p: ap){
			sb.append("<tr> <td>"+p.getNome()+" </td> <td> "+dateFormat.format(p.getData())+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td>");
			sb.append("<td>"+p.getUbicazione().getLocAmm().getNazione()+"</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+p.getIdProcesso()+"\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="+p.getIdProcesso()+"\">modifica</a> </td>");
			sb.append("<td><a href=\"Servlet?operazione=eliminaProcesso&idProcesso="+p.getIdProcesso()+"\">Elimina</a> </td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	
	public static String mostraProcesso(int idProcesso) throws SQLException{
		System.out.println("devo premdere");
		Processo p = ControllerDatabase.prendiProcesso(idProcesso);
		System.out.println("sono denntro e ho preso il processo: "+p.getNome());
		StringBuilder sb = new StringBuilder();
		
		sb.append(HTMLScript.mostraMappaProcesso(p));
		
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> </tr>");
		sb.append(" <tr> <td>"+p.getNome()+" </td> <td> "+p.getData()+"</td> <td> "+p.getUbicazione().getLocAmm().getComune()+"</td></tr>");
		sb.append("</table></div>");
		sb.append("<div id=\"mappa\"/>");
		return sb.toString();
	}
	
	public static String formCercaProcessi(String path,String loc) throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if(loc.equals("IT")){
		
			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"data\">Data</label> <input type=\"text\" id=\"data\" name=\"data\" class=\"form-control\" placeholder=\"data\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");
			
			
			
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\"placeholder=\"Superficie\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" placeholder=\"Larghezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" placeholder=\"Altezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"number\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" placeholder=\"Volume\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" placeholder=\"Intervallo\"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"  />");
			sb.append("</div>");
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\">  </textarea></div>");
			sb.append("</div>");
			sb.append("<br><h4>Tipologia Processo</h4>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			
			sb.append("<p>");
			for(TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso())
				sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+tp.getNome_IT()+"\"/> "+tp.getNome_IT()+" ");
			sb.append("</p>");
			
			sb.append("</div> </div>");
			
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" placeholder=\"Sottobacino\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" placeholder=\"Bacino\"/></div> ");
			sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\"placeholder=\"Comune\"/></div>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\"placeholder=\"Provincia\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" placeholder=\"Regione\" /> </div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" placeholder=\"Nazione\" /></div>");
			sb.append("</div>");
			
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"Latitudine\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"Longitudine\"/></div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
			sb.append("</div>");
			
			
			sb.append("</div> </div>");
		
			sb.append("<p>dati sul sito</p>");
			sb.append("<p> Caratteristiche Sito <input type=\"text\" id=\"caratteristicaSito_"+loc+"\" name=\"caratteristicaSito_"+loc+"\" /></p>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" />");
		
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for(Danni d:ControllerDatabase.prendiDanni()){
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""+d.getTipo_IT()+"\"/> "+d.getTipo_IT()+" ");
			}
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for(EffettiMorfologici em:ControllerDatabase.prendiEffettiMOrfologici()){
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""+em.getTipo_IT()+"\" /> "+em.getTipo_IT()+" ");
			}
			sb.append("</p>");
			sb.append("</div> </div>");
			
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("<label for=\"nomeLitologia_"+loc+"\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"+loc+"\" name=\"nomeLitologia_"+loc+"\" class=\"form-control\" placeholder=\"Litologia\"/></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"+loc+"\">Proprietà Termiche</label><input type=\"text\" id=\"proprietaTermiche_"+loc+"\" name=\"proprietaTermiche_"+loc+"\" class=\"form-control\" placeholder=\"Proprietà Termiche\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"+loc+"\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"+loc+"\" name=\"statoFratturazione_"+loc+"\" class=\"form-control\" placeholder=\"Stato Fratturazione\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
			sb.append("</div>");
			sb.append("</div> </div>");
			
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
			sb.append("</div>");
			
			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
			sb.append("</div> </div>");
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

		sb.append(HTMLScript.scriptData("data"));
		
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc),loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc),loc));
		
	

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
	
	
	
}
