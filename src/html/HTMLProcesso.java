package html;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import bean.Danni;
import bean.EffettiMorfologici;
import bean.Processo;
import bean.StazioneMetereologica;
import bean.TipologiaProcesso;
import bean.partecipante.Partecipante;
import bean.partecipante.Role;
import controller.ControllerDatabase;
import controller.ControllerJson;

public class HTMLProcesso {

	public static String formInserisciProcesso(String path, String loc,
			Partecipante part) throws SQLException {
		StringBuilder sb = new StringBuilder();
		if (part != null
				&& (part.hasRole(Role.AMMINISTRATORE) || part.hasRole(Role.AVANZATO))) {
			sb.append(HTMLScript.scriptData("data"));
			sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson
					.getJsonLocazioneAmminitrativa(path)));
			sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(
					ControllerJson.getJsonProprietaTermiche(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(
					ControllerJson.getJsonStatoFratturazione(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson
					.getJsonClasseVolume(path)));
			sb.append(HTMLScript.scriptAutcompleteLitologia(
					ControllerJson.getJsonLitologia(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteSitoProcesso(
					ControllerJson.getJsonSitoProcesso(path, loc), loc));
			sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson
					.getJsonLocazioneIdrologica(path)));
			sb.append(HTMLScript.dialogMaps());
			if (loc.equals("IT")) {

				sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");

				sb.append("<div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
				sb.append("</div>");
				sb.append("<br><div class=\"row \">");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" placeholder=\"Anno\"></div>");

				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" placeholder=\"Mese\">");
				sb.append("<option value=\"vuoto\"> </option>");
				for (int i = 1; i <= 12; i++)
					sb.append("<option value=\"" + i + "\">" + i + "</option>");
				sb.append("</select>");
				sb.append("</div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" placeholder=\"Giorno\">");
				sb.append("<option value=\"vuoto\"> </option>");
				for (int i = 1; i <= 31; i++)
					sb.append("<option value=\"" + i + "\">" + i + "</option>");
				sb.append("</select>");
				sb.append(" <input type=\"hidden\" id=\"datepicker\" />");
				sb.append("</div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
				sb.append("</div>");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\"placeholder=\"Superficie\" ></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" placeholder=\"Larghezza\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" placeholder=\"Altezza\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"number\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" placeholder=\"Volume\" ></div>");
				sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" placeholder=\"Intervallo\"></div>");
				sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"  />");
				sb.append("</div>");
				sb.append("<br><div class =\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_"
						+ loc
						+ "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_"
						+ loc
						+ "\" name=\"caratteristicaSito_"
						+ loc
						+ "\" class=\"form-control\"placeholder=\"Caratteristica del Sito\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\"/>");
				sb.append("</div>");
				sb.append("<br><div class=\"wrapper\">");
				sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
				sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\">  </textarea></div>");
				sb.append("</div>");
				sb.append("<br><h4>Tipologia Processo</h4>");
				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

				sb.append("<p>");
				for (TipologiaProcesso tp : ControllerDatabase
						.prendiTipologiaProcesso())
					sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""
							+ tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
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
				sb.append("<div id=\"controls\">");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" placeholder=\"Latitudine\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" placeholder=\"Longitudine\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-4\"><br><input type=\"button\" name=\"showMap\" value=\"Prendi Le Coordinate Dalla Mappa\" id=\"showMap\" /></div>");
				sb.append("</div>");
				sb.append("</div>");

				sb.append(" <div id=\"map_container\" title=\"Location Map\">");
				sb.append("<div id=\"map_canvas\" style=\"width:100%;height:80%;\"></div>");
				sb.append("<div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"lati\">Latitudine</label><input type=\"text\" id =\"lati\"name=\"lati\" class=\"form-control\" placeholder=\"latitudine\"></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"long\">Longitudine</label><input type=\"text\" id=\"long\" name=\"long\" class=\"form-control\"  placeholder=\"longitudine\"></div>");
				sb.append("</div>");
				sb.append("</div>");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" placeholder=\"Quota\"/></div>");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" placeholder=\"Esposizione\" /></div>");
				sb.append("</div>");

				sb.append("</div> </div>");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
				sb.append("<h4>Danni</h4>");
				sb.append("<p>");
				for (Danni d : ControllerDatabase.prendiDanni()) {
					sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""
							+ d.getTipo_IT() + "\"/> " + d.getTipo_IT() + " ");
				}
				sb.append("</p>");
				sb.append("<p>");
				sb.append("<h4>Effetti Morfologici</h4>");
				for (EffettiMorfologici em : ControllerDatabase
						.prendiEffettiMOrfologici()) {
					sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
							+ em.getTipo_IT() + "\" /> " + em.getTipo_IT() + " ");
				}
				sb.append("</p>");
				sb.append("</div> </div>");

				sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
				sb.append("<label for=\"nomeLitologia_" + loc
						+ "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"
						+ loc + "\" name=\"nomeLitologia_" + loc
						+ "\" class=\"form-control\" placeholder=\"Litologia\"/></p>");
				sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
				sb.append("<br><div class=\"row\">");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"
						+ loc
						+ "\">Proprietà Termiche</label><input type=\"text\" id=\"proprietaTermiche_"
						+ loc
						+ "\" name=\"proprietaTermiche_"
						+ loc
						+ "\" class=\"form-control\" placeholder=\"Proprietà Termiche\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
				sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"
						+ loc
						+ "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"
						+ loc
						+ "\" name=\"statoFratturazione_"
						+ loc
						+ "\" class=\"form-control\" placeholder=\"Stato Fratturazione\"/></div>");
				sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" />");
				sb.append("</div>");
				sb.append("</div> </div>");

				sb.append("<br><div class=\"wrapper\">");
				sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
				sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\">  </textarea></div>");
				sb.append("</div>");

				sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciProcesso\">");
				sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
				sb.append("</div> </div>");
				sb.append("</form>");
			} else if (loc.equals("ENG")) {
				// da fare
			}
		} else {
			sb.append("<h1>Pagina delle segnalazioni(Da implementare)</h1>");
		}
		return sb.toString();
	}

	public static String mostraTuttiProcessi() throws SQLException {
		ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		/* script per google maps */// centrerei la mappa al centro delle alpi

		sb.append(HTMLScript.scriptFilter());   
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Modifica</th><th>Elimina</th></tr>");
		for (Processo p : ap) {
			sb.append("<tr> <td>" + p.getNome() + " </td> ");
			sb.append("	<td>"	+ dataFormattata(p.getFormatoData(), p.getData()) + "</td>" );
			sb.append("	<td>"+ p.getUbicazione().getLocAmm().getComune() + "</td>");
			sb.append("<td>" + p.getUbicazione().getLocAmm().getNazione() + "</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="
					+ p.getIdProcesso() + "\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="
					+ p.getIdProcesso() + "\">modifica</a> </td>");
			sb.append("<td><a href=\"Servlet?operazione=eliminaProcesso&idProcesso="
					+ p.getIdProcesso() + "\">Elimina</a> </td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}
	public static String mostraTuttiProcessiModifica() throws SQLException{
		ArrayList<Processo> ap = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

		/* script per google maps */// centrerei la mappa al centro delle alpi

		sb.append(HTMLScript.scriptFilter());   
		sb.append("<h3>Scegli un Processo da modificare</h3>");
		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th>nazione</th> <th> Report </th> <th> Modifica</th></tr>");
		for (Processo p : ap) {
			sb.append("<tr> <td>" + p.getNome() + " </td> ");
			sb.append("	<td>"	+ dataFormattata(p.getFormatoData(), p.getData()) + "</td>" );
			sb.append("	<td>"+ p.getUbicazione().getLocAmm().getComune() + "</td>");
			sb.append("<td>" + p.getUbicazione().getLocAmm().getNazione() + "</td> ");
			sb.append("<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="+ p.getIdProcesso() + "\">dettagli</a></td>");
			sb.append("<td><a href=\"Servlet?operazione=mostraModificaProcesso&idProcesso="+ p.getIdProcesso() + "\">modifica</a> </td>");
			sb.append("</tr>");
		}
		sb.append("</table></div>");
		return sb.toString();
	}

	public static String mostraProcesso(int idProcesso) throws SQLException {

		Processo p = ControllerDatabase.prendiProcesso(idProcesso);
		StringBuilder sb = new StringBuilder();

		sb.append(HTMLScript.mostraMappaProcesso(p));

		sb.append("<div class=\"table-responsive\"><table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> </tr>");
		sb.append(" <tr> <td>" + p.getNome() + " </td> <td> "
				+ dataFormattata(p.getFormatoData(), p.getData()) + "</td> <td> "
				+ p.getUbicazione().getLocAmm().getComune() + "</td>");
		sb.append("<td><a href=\"Servlet?operazione=scegliRaggio&idProcesso="
				+ idProcesso + "\">stazioni</a></td>");
		sb.append("</tr>");
		sb.append("</table></div>");
		sb.append("<div id=\"mappa\" style=\"width:400px;height:500px\"/>");
		return sb.toString();
	}

	public static String dataFormattata(int formatoData, Timestamp data) {
		StringBuilder sb = new StringBuilder();
		String fd = String.valueOf(formatoData);
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		if (fd.length()==4) {
			if (fd.charAt(0) == '1') {
				sb.append(cal.get(Calendar.YEAR));
			}
			if (fd.charAt(1) == '1') {
				sb.append("-"
						+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ITALY));
			}
			if (fd.charAt(2) == '1') {
				sb.append("-"+ cal.get(Calendar.DAY_OF_MONTH));
			}
			if (fd.charAt(3) == '1') {
				sb.append(" " + cal.get(Calendar.HOUR_OF_DAY));
				sb.append(":" + cal.get(Calendar.MINUTE));
			}
		}
		return sb.toString();
	}

	public static String formCercaProcessi(String path, String loc)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append(HTMLScript.scriptData("data"));
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		if (loc.equals("IT")) {

			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");

			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" placeholder=\"nome\" ></div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row \">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" placeholder=\"Anno\"></div>");

			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" placeholder=\"Mese\">");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 12; i++)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" placeholder=\"Giorno\">");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 31; i++)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");

			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\"placeholder=\"Superficie\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" placeholder=\"Larghezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" placeholder=\"Altezza\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"number\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" placeholder=\"Volume\" ></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" placeholder=\"Intervallo\"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\"  />");
			sb.append("</div>");
			sb.append("<br><div class =\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_"
					+ loc
					+ "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_"
					+ loc
					+ "\" name=\"caratteristicaSito_"
					+ loc
					+ "\" class=\"form-control\"placeholder=\"Caratteristica del Sito\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\">  </textarea></div>");
			sb.append("</div>");
			sb.append("<br><h4>Tipologia Processo</h4>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

			sb.append("<p>");
			for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso())
				sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""
						+ tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
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

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for (Danni d : ControllerDatabase.prendiDanni()) {
				sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""
						+ d.getTipo_IT() + "\"/> " + d.getTipo_IT() + " ");
			}
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for (EffettiMorfologici em : ControllerDatabase
					.prendiEffettiMOrfologici()) {
				sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
						+ em.getTipo_IT() + "\" /> " + em.getTipo_IT() + " ");
			}
			sb.append("</p>");
			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("<label for=\"nomeLitologia_" + loc
					+ "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"
					+ loc + "\" name=\"nomeLitologia_" + loc
					+ "\" class=\"form-control\" placeholder=\"Litologia\"/></p>");
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" />");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"
					+ loc
					+ "\">Proprietà Termiche</label><input type=\"text\" id=\"proprietaTermiche_"
					+ loc
					+ "\" name=\"proprietaTermiche_"
					+ loc
					+ "\" class=\"form-control\" placeholder=\"Proprietà Termiche\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" />");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"
					+ loc
					+ "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"
					+ loc
					+ "\" name=\"statoFratturazione_"
					+ loc
					+ "\" class=\"form-control\" placeholder=\"Stato Fratturazione\"/></div>");
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
		} else if (loc.equals("ENG")) {
			// da fare
		}
		return sb.toString();
	}

	public static String mostraCercaProcessi(ArrayList<Processo> ap)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class=\"table\"> <tr> <th>Nome</th> <th>data</th> <th>comune</th> <th> dettagli</th> </tr>");
		for (Processo p : ap) {
			sb.append(" <tr> <td>" + p.getNome() + " </td> <td> " + p.getData()
					+ "</td> <td>" + p.getUbicazione().getLocAmm().getComune() + "</td>"
					+ "<td><a href=\"Servlet?operazione=mostraProcesso&idProcesso="
					+ p.getIdProcesso() + "\">dettagli</a></td></tr>");
		}
		return sb.toString();
	}

	public static String modificaProcesso(Processo p, String path, String loc)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		Calendar cal = new GregorianCalendar();
		cal.setTime(p.getData());
		cal.add(Calendar.MONTH, 1);

		sb.append(HTMLScript.scriptData("data"));

		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.dialogMaps());
		

		if (loc.equals("IT")) {

			sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sul Processo</h4>");

			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"nome\">Nome Del Processo</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\""+ p.getNome() + "\" ></div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row \">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"anno\">Anno</label> <input type=\"text\" id=\"anno\" name=\"anno\" class=\"form-control\" value=\""+cal.get(Calendar.YEAR)+" \"></div>");

			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"mese\">Mese</label> <select id=\"mese\" name=\"mese\" class=\"form-control\" >");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 12; i++){
				if(cal.get(Calendar.MONTH)!=i)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
				else
					sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
			}
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"giorno\">Giorno</label> <select id=\"giorno\" name=\"giorno\" class=\"form-control\" >");
			sb.append("<option value=\"vuoto\"> </option>");
			for (int i = 1; i <= 31; i++){
				if(cal.get(Calendar.DAY_OF_MONTH)!=i)
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
				else
					sb.append("<option selected=\"selected\" value=\"" + i + "\">" + i + "</option>");
			}
			sb.append("</select>");
			sb.append(" <input type=\"hidden\" id=\"datepicker\" />");
			sb.append("</div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"ora\">Ora</label> <input type=\"text\" id=\"ora\" name=\"ora\"  class=\"form-control\" placeholder=\"ora\"></div> ");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"superficie\">Superficie</label><input type=\"text\" name=\"superficie\" id=\"superficie\" class=\"form-control\" value=\""+p.getSuperficie()+" \"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"larghezza\">Larghezza</label><input type=\"text\" name=\"larghezza\" id=\"larghezza\" class=\"form-control\" value=\""+p.getLarghezza()+" \"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"altezza\">Altezza</label><input type=\"text\" name=\"altezza\" id=\"altezza\" class=\"form-control\" value=\""+p.getAltezza()+" \"></div>");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"volumeSpecifico\">Volume Specifico</label><input type=\"number\" name=volumespecifico id=\"volumeSpecifico\"class=\"form-control\" value=\""+p.getVolumeSpecifico()+" \" ></div>");
			if(p.getClasseVolume().getIntervallo()==null) p.getClasseVolume().setIntervallo("");
			sb.append("<div class=\"col-xs-6 col-md-2\"><label for=\"intervallo\">Classe Volume</label><input type=\"text\" id=\"intervallo\" name=intervallo class=\"form-control\" value=\""+p.getClasseVolume().getIntervallo()+" \"></div>");
			sb.append("<input type=\"hidden\" id=\"idclasseVolume\" name=\"idclasseVolume\" value=\""+p.getClasseVolume().getIdClasseVolume()+" \" />");
			sb.append("</div>");
			sb.append("<br><div class =\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"caratteristicaSito_"+ loc+ "\">Caratteristica del sito</label><input type=\"text\" id=\"caratteristicaSito_"+ loc+ "\" name=\"caratteristicaSito_"+ loc+ "\" class=\"form-control\"value=\""+p.getSitoProcesso().getCaratteristicaSito_IT()+" \"/></div>");
			sb.append("<input type=\"hidden\" id=\"idsito\" name=\"idsito\" value=\""+p.getSitoProcesso().getIdSito()+" \"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"descrizione\">Descrizione</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"descrizione\" id=\"descrizione\" class=\"textarea\" placeholder=\"Descrizione\"> "+p.getDescrizione()+" </textarea></div>");
			sb.append("</div>");
			sb.append("<br><h4>Tipologia Processo</h4>");
			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");

			sb.append("<p>");
			for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()){
				boolean inserito = false;
				for(TipologiaProcesso tpp:p.getTipologiaProcesso()){
					if (tp.getNome_IT().equals(tpp.getNome_IT())) {
					sb.append("<input type=\"checkbox\" checked=\"checked\" name=\"tpnome_IT\" value=\""+ tp.getNome_IT() + "\"/> " + tp.getNome_IT() + " ");
					}
					if (inserito == false)
						sb.append("<input type=\"checkbox\" name=\"tpnome_IT\" value=\""+ tp.getNome_IT() + "\" /> " + tp.getNome_IT() + "");
				}
			}
			sb.append("</p>");

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sull'ubicazione</h4>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"sottobacino\">Sottobacino</label><input type=\"text\" id=\"sottobacino\" name=\"sottobacino\" class=\"form-control\" value=\""+p.getUbicazione().getLocIdro().getSottobacino()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"bacino\">Bacino</label><input readonly=\"readonly\" type=\"text\"id=\"bacino\" name=\"bacino\" class=\"form-control\" value=\""+p.getUbicazione().getLocIdro().getBacino()+"\"/></div> ");
			sb.append("<input type=\"hidden\" id=\"idSottobacino\" name=\"idSottobacino\"/>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"comune\">Comune</label><input type=\"text\" id=\"comune\" name=\"comune\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getComune()+"\"/></div>");
			sb.append("<input  type=\"hidden\" id=\"idcomune\" name=\"idcomune\" />");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"provincia\">Provincia</label><input readonly=\"readonly\" type=\"text\" id=\"provincia\" name=\"provincia\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getProvincia()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"regione\">Regione</label><input readonly=\"readonly\" type=\"text\" id=\"regione\" name=\"regione\" class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getRegione()+"\" /> </div>");
			sb.append("<div class=\"col-xs-6 col-md-3\"><label for=\"nazione\">Nazione</label><input readonly=\"readonly\" type=\"text\" id=\"nazione\" name=\"nazione\"class=\"form-control\" value=\""+p.getUbicazione().getLocAmm().getNazione()+"\" /></div>");
			sb.append("</div>");
			sb.append("<div id=\"controls\">");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"latitudine\">Latitudine</label><input type=\"text\" id=\"latitudine\"name=\"latitudine\" class=\"form-control\" value=\""+p.getUbicazione().getCoordinate().getX()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><label for=\"longitudine\">Longitudine</label><input type=\"text\" id=\"longitudine\" name=\"longitudine\" class=\"form-control\" value=\""+p.getUbicazione().getCoordinate().getY()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-4\"><br><input type=\"button\" name=\"showMap\" value=\"Prendi Le Coordinate Dalla Mappa\" id=\"showMap\" /></div>");
			sb.append("</div>");
			sb.append("</div>");

			sb.append(" <div id=\"map_container\" title=\"Location Map\">");
			sb.append("<div id=\"map_canvas\" style=\"width:100%;height:80%;\"></div>");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"lati\">Latitudine</label><input type=\"text\" id =\"lati\"name=\"lati\" class=\"form-control\" placeholder=\"latitudine\"></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"long\">Longitudine</label><input type=\"text\" id=\"long\" name=\"long\" class=\"form-control\"  placeholder=\"longitudine\"></div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"quota\">Quota</label> <input type=\"text\" id=\"quota\"name=\"quota\" class=\"form-control\" value\""+p.getUbicazione().getQuota()+"\"/></div>");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"esposizione\">Esposizione</label> <input type=\"text\" id=\"esposizione\" name=\"esposizione\" class=\"form-control\" value\""+p.getUbicazione().getEsposizione()+"\"/></div>");
			sb.append("</div>");

			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\">");
			sb.append("<h4>Danni</h4>");
			sb.append("<p>");
			for (Danni d : ControllerDatabase.prendiDanni()) {
				boolean inserito = false;
				for(Danni da : p.getDanni()){
					if (d.getTipo_IT().equals(da.getTipo_IT())) {
						sb.append("<input type=\"checkbox\"  name=\"dtipo_IT\" value=\""+ d.getTipo_IT() + "\" checked=\"checked\" /> "+ d.getTipo_IT() + "");
						inserito = true;
					}
				}
				if (inserito == false)
					sb.append("<input type=\"checkbox\" name=\"dtipo_IT\" value=\""+ d.getTipo_IT() + "\" /> " + d.getTipo_IT() + "");
			}
					
				
			
			sb.append("</p>");
			sb.append("<p>");
			sb.append("<h4>Effetti Morfologici</h4>");
			for (EffettiMorfologici em : ControllerDatabase
					.prendiEffettiMOrfologici()) {
				boolean inserito = false;
				for (EffettiMorfologici emp : p.getEffetti()) {
					if (emp.getTipo_IT().equals(em.getTipo_IT())) {
						sb.append("<input type=\"checkbox\"  name=\"emtipo_IT\" value=\""
								+ em.getTipo_IT() + "\" checked=\"checked\" /> "
								+ em.getTipo_IT() + "");
						inserito = true;
					}
				}
				if (inserito == false)
					sb.append("<input type=\"checkbox\" name=\"emtipo_IT\" value=\""
							+ em.getTipo_IT() + "\" /> " + em.getTipo_IT() + "");
			}
			sb.append("</p>");
			sb.append("</div> </div>");

			sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati sulla litologia</h4>");
			sb.append("<label for=\"nomeLitologia_" + loc+ "\">Litologia</label>:<input type=\"text\" id=\"nomeLitologia_"+ loc + "\" name=\"nomeLitologia_" + loc+ "\" class=\"form-control\" value=\""+p.getLitologia().getNomeLitologia_IT()+"\"/></p>");
			System.out.println("litologia: "+p.getLitologia().getNomeLitologia_IT());
			sb.append("<input type=\"hidden\" id=\"idLitologia\" name=\"idLitologia\" value\""+p.getLitologia().getidLitologia()+"\"/>");
			sb.append("<br><div class=\"row\">");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"proprietaTermiche_"+ loc+ "\">Proprietà Termiche</label><input type=\"text\" id=\"proprietaTermiche_"+ loc+ "\" name=\"proprietaTermiche_"+ loc+ "\" class=\"form-control\" value=\""+p.getProprietaTermiche().getProprieta_termiche_IT()+"\"/></div>");
			sb.append("<input type=\"hidden\" id=\"idProprietaTermiche\" name=\"idProprietaTermiche\" value\""+p.getProprietaTermiche().getIdProprieta_termiche()+" />");
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\"statoFratturazione_"+ loc+ "\">Stato Fratturazione</label><input type=\"text\" id=\"statoFratturazione_"+ loc+ "\" name=\"statoFratturazione_"+ loc+ "\" class=\"form-control\" value=\""+p.getStatoFratturazione().getStato_fratturazione_IT()+"\" /></div>");
			sb.append("<input type=\"hidden\" id=\"idStatoFratturazione\" name=\"idStatoFratturazione\" value=\""+p.getStatoFratturazione().getIdStato_fratturazione()+" />");
			sb.append("</div>");
			sb.append("</div> </div>");

			sb.append("<br><div class=\"wrapper\">");
			sb.append("<div class=\"content-main\"><label for=\"note\">Note</label></div>");
			sb.append("<div class=\"content-secondary\"><textarea rows=\"5\" cols=\"140\" name=\"note\" id=\"note\" class=\"textarea\" placeholder=\"Note\"> "+p.getNote()+" </textarea></div>");
			sb.append("</div>");

			sb.append("<input type=\"hidden\" name=\"operazione\" value=\"inserisciProcesso\">");
			sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
			sb.append("</div> </div>");
			sb.append("</form>");
		}
		return sb.toString();
	}

	public static String mostraCerchioProcesso(int id, String loc)
			throws SQLException {
		StringBuilder sb = new StringBuilder();

		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">"
				+ "raggio<input type=\"text\" name=\"raggio\" > <br>"
				+ "<input type=\"hidden\" name=\"operazione\" value=\"mostraStazioniRaggio\">"
				+ "  <input type=\"hidden\" name=\"id\" value=\"" + id + "\"  />"
				+ "<input type=\"submit\" name =\"submit\" value=\"OK\">" + "</form>");
		return sb.toString();
	}

	public static String mostraStazioniRaggio(Processo p, String loc, int raggio)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		double x = p.getUbicazione().getCoordinate().getX();
		double y = p.getUbicazione().getCoordinate().getY();
		ArrayList<StazioneMetereologica> s = ControllerDatabase
				.prendiStazionidaRaggio(x, y, p, raggio);
		sb.append("<table class=\"table\"> <tr> <th>distanza</th> <th>quota</th> <th>nome</th> </tr>");
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\">");
		for (StazioneMetereologica stazione : s) {
			sb.append(" <tr> <td>" + stazione.getDistanzaProcesso() + " </td> <td>"
					+ stazione.getUbicazione().getQuota() + "</td> <td>"
					+ stazione.getNome()
					+ " </td> <td> <input type=\"checkbox\" name=\"id\" value=\""
					+ stazione.getIdStazioneMetereologica() + "\" > </td></tr>");
		}
		sb.append("</table>");
		sb.append("<div class=\"row\">");
		sb.append("<input type=\"hidden\" name=\"idProcesso\" value=\""
				+ p.getIdProcesso() + "\">");
		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliTemperature\"> </div>");

		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliDeltaT\"> </div>");

		sb.append("<div class=\"col-xs-6 col-md-4\"><input type=\"submit\" name =\"operazione\" value=\"scegliPrecipitazioni\"> </div>");
		sb.append("</div>");
		sb.append("</form>");
		return sb.toString();
	}

	public static String mostraProcessiMaps() throws SQLException {
		ArrayList<Processo> p = ControllerDatabase.prendiTuttiProcessi();
		StringBuilder sb = new StringBuilder();

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
		for(int i =0;i<p.size();i++){
			sb.append("stazioni["+i+"] = {");
			sb.append(" nome: \" "+p.get(i).getNome()+" \",");
			sb.append(" comune: \" "+p.get(i).getUbicazione().getLocAmm().getComune()+" \",");
			sb.append(  " x: "+p.get(i).getUbicazione().getCoordinate().getX()+",");
			sb.append(  " y: "+p.get(i).getUbicazione().getCoordinate().getY()+"");
			sb.append(  " };");
		}
		sb.append("for (var i = 0; i < "+p.size()+"; i++) { "); 
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
	
	/*
	 * query delle ricerche S...
	 */
	public static String listaQueryProcesso(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"panel panel-default\">");
		sb.append("	<div class=\"panel-heading\">Query sui processi</div>");

		sb.append("		<div class=\"list-group\">");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=nome\" class=\"list-group-item\">Cerca per nome</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=anno\" class=\"list-group-item\">Cerca per anno</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=caratteristicaSito_IT\" class=\"list-group-item\">Cerca per caratteristica sito</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=tpnome_IT\" class=\"list-group-item\">cerca per tipologia processo</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=comune-provincia-regione-nazione-sottobacino-bacino\" class=\"list-group-item\">ricerca territoriale</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=quota\" class=\"list-group-item\">ricerca per quota</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=dtipo_IT\" class=\"list-group-item\">ricerca per danni</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=nomeLitologia_IT-proprietaTermiche_IT-statoFratturazione_IT\" class=\"list-group-item\">ricerca per litologia</a>");
		sb.append("  			<a href=\"Servlet?operazione=formRicercaSingola&attributi=\" class=\"list-group-item\">ricerca sulla mappa(da implementare)</a>");
		sb.append("          	<a href=\"Servlet?operazione=mostraTuttiProcessi\" class=\"list-group-item\"> mostra tutti i processi</a>");
		sb.append("			<a href=\"Servlet?operazione=mostraProcessiMaps\" class=\"list-group-item\"> mostra processi sulla mappa</a>");
		sb.append(" 			<a href=\"Servlet?operazione=formCercaProcessi\" class=\"list-group-item\"> ricerca processo</a>");
		sb.append("  		</div>");
		sb.append("  		</div>	");             		
		return sb.toString();
	}
	public static String formCercaSingola(String attributi,String path,String loc){
		StringBuilder sb = new StringBuilder();
		StringBuilder attributiBulder = new StringBuilder();
		attributiBulder.append(attributi);
		String[] attributiArray = attributi.split("-");
		
		sb.append(HTMLScript.scriptData("data"));
		
		
		sb.append(HTMLScript.scriptAutocompleteLocAmm(ControllerJson.getJsonLocazioneAmminitrativa(path)));
		sb.append(HTMLScript.scriptAutocompleteProprietaTermiche(ControllerJson.getJsonProprietaTermiche(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteStatoFratturazione(ControllerJson.getJsonStatoFratturazione(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteClasseVolume(ControllerJson.getJsonClasseVolume(path)));
		sb.append(HTMLScript.scriptAutcompleteLitologia(ControllerJson.getJsonLitologia(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteSitoProcesso(ControllerJson.getJsonSitoProcesso(path, loc), loc));
		sb.append(HTMLScript.scriptAutocompleteLocIdro(ControllerJson.getJsonLocazioneIdrologica(path)));
		sb.append("<form action=\"/DBAlps/Servlet\" name=\"dati\" method=\"POST\" role=\"form\">");

		sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Ricerca tra i Processi</h4>");
		
			
		
		sb.append("<div class=\"row\">");
		for(int i = 0;i<attributiArray.length;i++){
			System.out.println("attributi"+attributiArray[i]);
			sb.append("<div class=\"col-xs-6 col-md-6\"><label for=\""+attributiArray[i]+"\">"+attributiArray[i]+"</label> <input type=\"text\" name=\""+attributiArray[i]+"\" id=\""+attributiArray[i]+"\" class=\"form-control\" placeholder=\""+attributiArray[i]+"\" ></div>");
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\"cercaProcesso\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">");
		sb.append("</form>");
		
		
		return sb.toString();
	}
	

}
