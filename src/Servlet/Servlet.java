package Servlet;

import html.HTMLElaborazioni;
import html.HTMLProcesso;
import html.HTMLStazioneMetereologica;
import html.HTMLUtente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import javax.websocket.Session;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

import bean.Grafici;
import bean.HTMLContent;
import bean.Processo;
import bean.StazioneMetereologica;
import bean.Ubicazione;
import bean.partecipante.Partecipante;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controller.ControllerDatabase;
import controller.ControllerJson;
import controller.ControllerProcesso;
import controller.ControllerStazioneMetereologica;
import controller.ControllerUbicazione;
import controller.ControllerUtente;



/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
@MultipartConfig
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String operazione = request.getParameter("operazione");    
		String path = System.getProperty("catalina.base")+"\\resources\\";
		System.out.println("Catalina" +System.getProperty("catalina.base"));
		System.out.println(path);
		HttpSession session = request.getSession();
		String loc = "IT";//oggetto sessione
		
		System.out.println("operazione eseguita: "+operazione);
		/*
		 * Processo
		 */
		if(operazione.equals("formInserisciProcesso")){
			Partecipante part = (Partecipante)session.getAttribute("partecipante");
			String content = HTMLProcesso.formInserisciProcesso(path,loc,part);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		if(operazione.equals("inserisciProcesso")){
			Partecipante part = (Partecipante)session.getAttribute("partecipante");
			Processo p = ControllerProcesso.nuovoProcesso(request,loc);
			if(!(p.getUbicazione().isEmpty()))
			ControllerDatabase.salvaUbicazione(p.getUbicazione());
			System.out.println("descrizione: "+p.getDescrizione());
			ControllerDatabase.salvaProcesso(p,part);
			ControllerDatabase.salvaEffetti(p.getIdProcesso(), p.getEffetti(), p.getDanni());
			ControllerDatabase.salvaTipologiaProcesso(p.getIdProcesso(), p.getTipologiaProcesso());
			System.out.println("id processo"+p.getIdProcesso());
			String content = HTMLProcesso.mostraProcesso(p.getIdProcesso());
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("mostraProcesso")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			String content = HTMLProcesso.mostraProcesso(idProcesso);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("mostraTuttiProcessi")){
			String content=HTMLProcesso.mostraTuttiProcessi();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		
		}
		else if(operazione.equals("mostraTuttiProcessiModifica")){
			String content=HTMLProcesso.mostraTuttiProcessiModifica();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("queryProcesso")){
			String content=HTMLProcesso.listaQueryProcesso();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		if(operazione.equals("formCercaProcessi")){
			
			String content = HTMLProcesso.formCercaProcessi(path, loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("cercaProcesso")){
			
			Processo p = ControllerProcesso.nuovoProcesso(request, loc);
			Ubicazione u = ControllerUbicazione.creaUbicazione(request);
			System.out.println("ubicazione"+u.toString());
			ArrayList<Processo> ap =ControllerDatabase.ricercaProcesso(p,u);
			String content = HTMLProcesso.mostraCercaProcessi(ap);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("mostraModificaProcesso")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			Processo p = ControllerDatabase.prendiProcesso(idProcesso);
			String content = HTMLProcesso.modificaProcesso(p,path,loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("modificaProcesso")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			Processo p = ControllerProcesso.nuovoProcesso(request,loc);
			p.getUbicazione().setIdUbicazione(ControllerDatabase.getIdUbicazione(idProcesso));
			p.setIdprocesso(idProcesso);
			ControllerDatabase.modificaProcesso(p);
			String content = HTMLProcesso.mostraProcesso(idProcesso);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("eliminaProcesso")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			Processo p = ControllerDatabase.prendiProcesso(idProcesso);
			ControllerDatabase.eliminaProcesso(p);
			String content = "ho eliminato il processo "+p.getNome()+"";
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("scegliRaggio")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			//Processo p  = ControllerDatabase.prendiProcesso(idProcesso);
			String content = HTMLProcesso.mostraCerchioProcesso(idProcesso,loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");

		}
		else if(operazione.equals("mostraStazioniRaggio")){
			int idProcesso=Integer.parseInt(request.getParameter("id"));
			Processo p  = ControllerDatabase.prendiProcesso(idProcesso);
			int raggio=Integer.parseInt(request.getParameter("raggio"));
			String content = HTMLProcesso.mostraStazioniRaggio(p,loc,raggio);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("idProcesso", idProcesso);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");

		}
		
		else if(operazione.equals("scegliTemperature")||operazione.equals("scegliDeltaT")||operazione.equals("scegliPrecipitazioni")){
			String [] id= request.getParameterValues("id");
			int idProcesso= Integer.parseInt(request.getParameter("idProcesso"));
			ArrayList<StazioneMetereologica> stazione=new ArrayList<StazioneMetereologica>();
			String content="";
			System.out.println("id"+id[0]);
			for(int i=0;i<id.length;i++){
				StazioneMetereologica s=new StazioneMetereologica();
				s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
				s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
				stazione.add(s);
			}
			if(operazione.equals("scegliTemperature")){
				content=HTMLStazioneMetereologica.temperatureDaProcesso(stazione,idProcesso);
			}else if(operazione.equals("scegliDeltaT")){
				content=HTMLStazioneMetereologica.deltaTDaProcesso(stazione);
			}else{
				content=HTMLStazioneMetereologica.precipitazioniDaProcesso(stazione);
			}
			
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("idProcesso", idProcesso);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");
		}
		else if(operazione.equals("mostraProcessiMaps")){
			String content=HTMLProcesso.mostraProcessiMaps();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		else if(operazione.equals("formRicercaSingola")){
			String attributi = request.getParameter("attributi");
			String content = HTMLProcesso.formCercaSingola(attributi, path, loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		/*
		 * Stazione metereologica
		 */
		else if(operazione.equals("formInserisciStazione")){
			String content = HTMLStazioneMetereologica.formStazioneMetereologica(path);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("inserisciStazione")){

			Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
			ControllerDatabase.salvaUbicazione(u);
			StazioneMetereologica s=ControllerStazioneMetereologica.nuovaStazioneMetereologica(request,loc,u);
			ControllerDatabase.salvaStazione(s);
			String content = HTMLStazioneMetereologica.mostraStazioneMetereologica(s.getIdStazioneMetereologica(),loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		
		}
		else if(operazione.equals("mostraStazioneMetereologica")){

			int idStazioneMetereologica=Integer.parseInt(request.getParameter("idStazioneMetereologica"));
			String content=HTMLStazioneMetereologica.mostraStazioneMetereologica(idStazioneMetereologica,loc);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc", c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("mostraTutteStazioniMetereologiche")){
			Partecipante part =(Partecipante)session.getAttribute("partecipante");
			String content=HTMLStazioneMetereologica.mostraTutteStazioniMetereologiche(part);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("ricercaStazione")){
		
			Ubicazione u = ControllerUbicazione.creaUbicazione(request);
			StazioneMetereologica s = ControllerStazioneMetereologica.nuovaStazioneMetereologica(request,loc,u);
			ArrayList<StazioneMetereologica> ap =ControllerDatabase.ricercaStazioneMetereologica(s,u);
			String content = HTMLStazioneMetereologica.mostraCercaStazioneMetereologica(ap);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("formRicercaStazione")){
			String content = HTMLStazioneMetereologica.formRicercaMetereologica(path);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("elencoStazioneModifica")){
			String content = HTMLStazioneMetereologica.scegliStazioneModifica();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("modificaStazione")){
	
			System.out.print(Integer.parseInt(request.getParameter("idStazioneMetereologica")));
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(request.getParameter("idStazioneMetereologica")),loc);

			//StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(Integer.parseInt(request.getParameter("idStazioneMetereologica")),loc);
			String ente=s.getEnte().getEnte();
			String content =HTMLStazioneMetereologica.modificaStazioneMetereologica(s,path);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			//request.setAttribute("enteVecchio", idente);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("inserisciStazioneModificata")){

			Ubicazione u = ControllerUbicazione.nuovaUbicazione(request);
			ControllerDatabase.salvaUbicazione(u);
			StazioneMetereologica s=ControllerStazioneMetereologica.nuovaStazioneMetereologica(request, loc,u);
			String enteVecchio=request.getParameter("enteVecchio");
			int idStazione=Integer.parseInt(request.getParameter("idStazione"));

			ControllerDatabase.modificaStazioneMetereologica(s,enteVecchio,idStazione);
			String content=HTMLStazioneMetereologica.mostraStazioneMetereologica(idStazione,loc);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("queryStazione")){
			String content = HTMLStazioneMetereologica.listaQueryStazione();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
	
		//elaborazioni
		else if(operazione.equals("eleborazioniDeltaT")){	
			String [] id= request.getParameterValues("id");
			String [] temperature= request.getParameterValues("temperature");

			int finestra=Integer.parseInt(request.getParameter("finestra"));
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"))/2;
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			String titolo="DeltaT";
			String unita="(°C)";
			ArrayList<Grafici> g=HTMLElaborazioni.deltaT(id,finestra,aggregazione,data);
			String content=HTMLElaborazioni.grafici(g,titolo,unita);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");

		}
		else if(operazione.equals("scegliStazioniDeltaT")){	
			String content=HTMLStazioneMetereologica.scegliStazioniMetereologicheDeltaT();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");

		}
		else if(operazione.equals("scegliStazioniT")){	
			String op="eleborazioniTemperatura";
			String content=HTMLStazioneMetereologica.scegliStazioniMetereologicheT(op);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");

		}
		else if(operazione.equals("eleborazioniTemperatura")){
			String [] id= request.getParameterValues("id");
			String [] tipi= request.getParameterValues("temperature");
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"))/2;
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			ArrayList<Grafici> g=HTMLElaborazioni.mediaTemperatura(id, aggregazione,data,-9999,-9999,tipi);
			String titolo="Temperatura";
			String unita="(°C)";
			String content=HTMLElaborazioni.grafici(g,titolo,unita);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			session.setAttribute("grafici", g);
			ArrayList<Grafici> p=(ArrayList<Grafici>) session.getAttribute("grafici");
			System.out.println("graf="+p.size());
			forward(request,response,"/elaborazioni.jsp");
		}
		else if(operazione.equals("elaborazioniPrecipitazioni")){
			String [] id= request.getParameterValues("id");
			int finestra=Integer.parseInt(request.getParameter("finestra"));
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"));
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			ArrayList<Grafici> g=HTMLElaborazioni.mediaPrecipitazioni(id, finestra, aggregazione/2, data);
			
			String titolo="Precipitazioni";
			String unita="(mm)";
			String content=HTMLElaborazioni.grafici(g,titolo,unita);
			
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			session.setAttribute("grafici", g);
			
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");
		}
		else if(operazione.equals("scegliStazioniPrecipitazioni")){	
			String content=HTMLStazioneMetereologica.scegliStazioniMetereologichePrecipitazioni();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");

		}
		else if(operazione.equals("mostraStazioniMaps")){	
			String content=HTMLStazioneMetereologica.mostraStazioniMaps();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");

		
		}
		else if(operazione.equals("temperatureDaProcesso")){
			String [] id= (String[]) request.getParameterValues("id");
			String [] tipi= request.getParameterValues("temperature");
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			Processo p=ControllerDatabase.prendiProcesso(idProcesso);
			double gradiente=0;
			double quota=p.getUbicazione().getQuota();
			if(!(request.getParameter("gradiente").equals("")))  gradiente=Double.parseDouble(request.getParameter("gradiente"));
			else gradiente=-9999;
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"));
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			System.out.println("id="+id[0]);
			ArrayList<Grafici> g=HTMLElaborazioni.mediaTemperatura(id,aggregazione,data,gradiente,quota,tipi);
			String titolo="Temperatura";
			String unita="(°C)";
			String content=HTMLElaborazioni.grafici(g, titolo, unita);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			session.setAttribute("grafici", g);

			request.setAttribute("HTMLc",c);
			
			forward(request,response,"/elaborazioni.jsp");
		}
		else if(operazione.equals("elencocaricaDatiClimatici")){
			String content = HTMLStazioneMetereologica.caricaDatiMetereologici();
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("caricaDatiClimatici")){
			int idstazione = Integer.parseInt(request.getParameter("idStazioneMetereologica"));
			String content=HTMLStazioneMetereologica.UploadCSV(idstazione);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("uploadCSVDatiClimatici")){
			StringBuilder content = new StringBuilder();
			int idStazione = Integer.parseInt(request.getParameter("idStazioneMetereologica"));
			String data="00-00-00";
			String ora="00:00";
			Timestamp dataInizio = null;
			if(!(request.getParameter("data").equals(""))){
				 data = request.getParameter("data");
				if(!(request.getParameter("data").equals(""))){
					if(!(request.getParameter("ora")==null)){
						if(!(request.getParameter("ora").equals(""))){
						ora = request.getParameter("ora");
						}  
					}else ora="00:00";
					String dataCompleta = ""+data+" "+ora+":00";
					System.out.println("data daala request: "+dataCompleta);
					dataInizio = Timestamp.valueOf(dataCompleta);
				}
			}
			String tabella = request.getParameter("tabella");
			String attributo = tabella.substring(tabella.indexOf('$')+1, tabella.length());
			tabella = tabella.substring(0,tabella.indexOf('$'));
			StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idStazione, loc);
			String pathStaz=path+"/"+sm.getNome()+"";
			File theDir = new File(pathStaz);
			if(!theDir.exists())
				theDir.mkdir();
				List<File> csv = uploadByJavaServletAPI(request, pathStaz);
				content.append("<h4>stazione "+sm.getNome()+"</h4>");
				for(File f:csv){
					int count = ControllerDatabase.lettoreCSVT(f, tabella,attributo, idStazione,dataInizio);
					content.append("<h5>hai caricato "+count+" dati climatici nella tabella "+tabella+" dal file "+f.getName()+"</h5>");
				}
				HTMLContent c = new HTMLContent();
				c.setContent(content.toString());
				request.setAttribute("HTMLc",c);
				forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("listaElaborazioni")){
			String content = HTMLElaborazioni.listaElaborazioni();
			HTMLContent c = new HTMLContent();
			c.setContent(content.toString());
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");
		}
		
		//utente
		else if(operazione.equals("formCreaUtente")){
			String content = HTMLUtente.creaUtente();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/utente.jsp");
		}
		else if(operazione.equals("inserisciUtente")){
			Partecipante utente=ControllerUtente.nuovoUtente(request);
			String content ="<h2> l'utente "+utente.getUsername()+" è stato creato correttamente</h2>";
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/utente.jsp");
		}
		else if(operazione.equals("formLogin")){
			String content = HTMLUtente.login();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/utente.jsp");
		}
		else if(operazione.equals("login")){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(ControllerDatabase.login(username, password)){
				HTMLContent c = new HTMLContent();
				Partecipante utente=ControllerDatabase.prendiUtente(username);
				session.setAttribute("partecipante", utente);
				String content ="<h3>Login Utente effettuato</h3>";
				content +="<h5>Informazione sull'utente</h5>";
				content += HTMLUtente.visualizzaUtente(utente);
				c.setContent(content);
				request.setAttribute("HTMLc",c);
				forward(request,response,"/utente.jsp");
			}else{
				String content ="<h2>spiacente, il login non è corretto</h2>";
				HTMLContent c = new HTMLContent();
				c.setContent(content);
				request.setAttribute("HTMLc",c);
				forward(request,response,"/utente.jsp");
			}
		}
		else if(operazione.equals("logout")){
			response.setHeader("Cache-Control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		else if(operazione.equals("visualizzaTuttiUtenti")){
			String content = HTMLUtente.visualizzaTuttiUtente();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/utente.jsp");
		}
	//query
		else if(operazione.equals("queryClimatiche")){
			String content=HTMLElaborazioni.sceltaQuery();
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");

		}
	else if(operazione.equals("datiQueryPrecipitazioniAnno")||operazione.equals("datiQueryTemperaturaAnno")){
			String [] id=request.getParameterValues("id");
			String content;
			
			ArrayList<StazioneMetereologica> stazione=new ArrayList<StazioneMetereologica>();
			for(int i=0;i<id.length;i++){
				StazioneMetereologica s=new StazioneMetereologica();
				s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
				s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
				stazione.add(s);
			}
			if(operazione.equals("datiQueryPrecipitazioniAnno")) content=HTMLElaborazioni.datiAnno(stazione,"queryPrecipitazioniAnno");
			else content=HTMLElaborazioni.datiAnno(stazione,"queryTemperaturaAnno");
		
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("queryPrecipitazioniAnno")){
		String [] id=request.getParameterValues("id");
		//String aggregazione=request.getParameter("aggregazione");
		String anno=request.getParameter("anno");
		String content=HTMLElaborazioni.precipitazioniQuery(anno,id);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("precipitazioniMese")||operazione.equals("precipitazioniTrimestre")||operazione.equals("precipitazioniAnno")||operazione.equals("temperaturaEPrecipitazioneAnno")||operazione.equals("temperaturaAnno")||operazione.equals("temperaturaTrimestre")){
		String op;
		if(operazione.equals("precipitazioniMese"))  op="datiQueryPrecipitazioniMese";
		else if(operazione.equals("precipitazioniTrimestre")) op="datiQueryPrecipitazioniTrimestre";
		else if(operazione.equals("precipitazioniAnno")) op="datiQueryPrecipitazioniAnno";
		else if(operazione.equals("temperaturaAnno")) op="datiQueryTemperaturaAnno";
		else if(operazione.equals("temperaturaTrimestre")) op="datiQueryTemperaturaTrimestre";

		else op="datiTemperaturaEPrecipitazioneAnno";
			String content=HTMLStazioneMetereologica.scegliStazioniQuery(op);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("datiQueryPrecipitazioniMese")){
		String [] id=request.getParameterValues("id");
		ArrayList<StazioneMetereologica> stazione=new ArrayList<StazioneMetereologica>();
		for(int i=0;i<id.length;i++){
			StazioneMetereologica s=new StazioneMetereologica();
			s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
			s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
			stazione.add(s);
		}
		String op="queryPrecipitazioniMese";
		String content=HTMLElaborazioni.DatiTrimestre(stazione,op);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("queryPrecipitazioniMese")){
		String [] id=request.getParameterValues("id");
		String mese=request.getParameter("mese");
		String anno=request.getParameter("anno");
		String content=HTMLElaborazioni.precipitazioniQueryMese(mese,anno,id);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("datiQueryPrecipitazioniTrimestre")||operazione.equals("datiQueryTemperaturaTrimestre")){
		String [] id=request.getParameterValues("id");
		ArrayList<StazioneMetereologica> stazione=new ArrayList<StazioneMetereologica>();
		for(int i=0;i<id.length;i++){
			StazioneMetereologica s=new StazioneMetereologica();
			s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
			s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
			stazione.add(s);
		}
		String op;
		if(operazione.equals("datiQueryPrecipitazioniTrimestre")) op="queryPrecipitazioniTrimestre";
		else  op="queryTemperaturaTrimestre";
		String content=HTMLElaborazioni.DatiTrimestre(stazione,op);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}
	else if(operazione.equals("queryPrecipitazioniTrimestre")||operazione.equals("queryTemperaturaTrimestre")){
		String [] id=request.getParameterValues("id");
		String mese=request.getParameter("mese");
		String anno=request.getParameter("anno");
		String content;
		String titolo;
		String unita;
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		if(operazione.equals("queryPrecipitazioniTrimestre")){
			g=HTMLElaborazioni.precipitazioniQueryTrimestre(mese,anno,id);
			 titolo="precipitazioni";
			 unita="mm";
			content=HTMLElaborazioni.graficiMultipliPrecipitazioni( g, "column", titolo,unita, unita, titolo,"cumulata",anno,mese);
		}
		else {
			g=HTMLElaborazioni.queryTemperaturaTrimestre(mese,anno,id);
			content=HTMLElaborazioni.graficiCategorie(g,"line","temperatura","C",anno,mese);
		}
		
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}
	else if(operazione.equals("datiTemperaturaEPrecipitazioneAnno")){
		String [] id=request.getParameterValues("id");
		ArrayList<StazioneMetereologica> stazione=new ArrayList<StazioneMetereologica>();
		for(int i=0;i<id.length;i++){
			StazioneMetereologica s=new StazioneMetereologica();
			s.setNome(ControllerDatabase.prendiNome(Integer.parseInt(id[i])));
			s.setIdStazioneMetereologica(Integer.parseInt(id[i]));
			stazione.add(s);
		}
		String content=HTMLElaborazioni.datiAnno(stazione,"queryPrecipitazioniTemperaturaAnno");
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}else if(operazione.equals("queryPrecipitazioniTemperaturaAnno")){
		String [] id=request.getParameterValues("id");
		//String aggregazione=request.getParameter("aggregazione");
		String anno=request.getParameter("anno");
		String content=HTMLElaborazioni.precipitazioniTemperaturaQueryAnno(anno,id);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}
	
	else if(operazione.equals("queryTemperaturaAnno")){
		String [] id=request.getParameterValues("id");
		//String aggregazione=request.getParameter("aggregazione");
		String anno=request.getParameter("anno");
		ArrayList<Grafici> g=HTMLElaborazioni.queryTemperaturaAnno(anno,id);
		
		String content=HTMLElaborazioni.graficiCategorie(g,"line","temperatura","C",anno,"1");
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/elaborazioni.jsp");
	}
	
	
	//download
	else if(operazione.equals("download")){
		
		ArrayList<Grafici> g=(ArrayList<Grafici>) session.getAttribute("grafici");


		String titolo=request.getParameter("titolo");
		System.out.println("titolo="+titolo);
		session.removeAttribute("grafici");
		System.out.println("grafici:"+g.size());
		String test="/Users/daler/Desktop/"+titolo+".csv";
		FileWriter outFile=new FileWriter(test,false);
		PrintWriter out = new PrintWriter(outFile);
		int cont=0;
		for(Grafici gra:g){
			
			out.print(""+gra.getNome()+";\n");
			out.print("\n");
		 for(int i=0;i<gra.getX().size();i++){	
			if(i!=(gra.getX().size()-1)){
				if((gra.getX().get(i).equals(gra.getX().get(i+1)))==false){
					out.println(""+gra.getX().get(i)+";"+gra.getY().get(cont)+";");
					cont++;
				}
				}else{
					out.println(""+gra.getX().get(i)+";"+gra.getY().get(cont)+";");
					cont++;
			}      
					
		} cont=0;out.print("\n");	}	
	/*	for(int i=0;i<g.size();i++){
			out.print("\n");
			out.print(""+g.get(i).getNome()+";\n");
			for(int j=0;j<g.get(i).getX().size();j++){
				out.println(""+g.get(i).getX().get(j)+";"+g.get(i).getY().get(j)+";\n");
			}
			out.println();
				
				
			}*/
			
		
		
		out.close();
		
	        File downloadFile = new File(test);
	        FileInputStream inStream = new FileInputStream(downloadFile);
	   		         
	        // obtains ServletContext
	        ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(test);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close();
	}
	
	else if(operazione.equals("scegliProcessoAllegato")){
		String content = HTMLProcesso.mostraTuttiProcessiAllega();
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/processo.jsp");
	}
	else if(operazione.equals("formAllegatoProcesso")){
		int idprocesso = Integer.parseInt(request.getParameter("idprocesso"));
		String content = HTMLProcesso.formAllegatoProcesso(idprocesso);
		HTMLContent c=new HTMLContent();
		c.setContent(content);
		request.setAttribute("HTMLc",c);
		forward(request,response,"/processo.jsp");
	}
			
		else if(operazione.equals("uploadAllegatoProcesso")){
			String uploadPath = path + "\\" + "allegatiProcesso";
			File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }
	    int idProcesso = Integer.parseInt(request.getParameter("idprocesso"));
	    Processo p = ControllerDatabase.prendiProcesso(idProcesso);
	    String autore = "";
	    String anno = "";
	    String titolo = "";
	    String in = "";
	    String fonte = "";
	    String urlWeb = "";
	    String note = "";
	    String tipo = "";
	    Partecipante part = (Partecipante) session.getAttribute("partecipante");
	    String uploadPathProcesso = path + "\\" + "allegatiProcesso\\"+""+p.getNome()+"";
			File uploadDirP = new File(uploadPathProcesso);
	    if (!uploadDirP.exists()) {
	        uploadDirP.mkdir();
	    }
	    
	    List<File> uploadFile = uploadByJavaServletAPI(request, uploadPathProcesso);
	    if(!(uploadFile.isEmpty())){
	    	for(File f:uploadFile){
	    		autore = request.getParameter("autore");
	    		anno = request.getParameter("anno");
	    		titolo = request.getParameter("titolo");
	    		in = request.getParameter("in");
	    		fonte = request.getParameter("fonte");
	    		urlWeb = request.getParameter("urlWeb");
	    		note = request.getParameter("note");
	    		tipo = request.getParameter("tipo");
	    		ControllerDatabase.salvaAllegatoProcesso(idProcesso,part.getIdUtente(),autore,anno,titolo,in,fonte,urlWeb,note,tipo,f.getAbsolutePath());
	    	}
	    }
	    String content = "<h5>allegato il file per il proceso: "+p.getNome()+"</h5>";
	    HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}
		
		else if(operazione.equals("scegliStazioneAllegato")){
			String content = HTMLStazioneMetereologica.scegliStazioneAllegati();
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		else if(operazione.equals("formAllegatoStazione")){
			int idstazione = Integer.parseInt(request.getParameter("idstazione"));
			String content = HTMLStazioneMetereologica.formAllegatoStazione(idstazione);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
		
		else if(operazione.equals("uploadAllegatoStazione")){
			String uploadPath = path + "\\" + "allegatiStazione";
			File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }
	    int idstazione = Integer.parseInt(request.getParameter("idstazione"));
	    StazioneMetereologica sm = ControllerDatabase.prendiStazioneMetereologica(idstazione, loc);
	    String autore = "";
	    String anno = "";
	    String titolo = "";
	    String in = "";
	    String fonte = "";
	    String urlWeb = "";
	    String note = "";
	    String tipo = "";
	    Partecipante part = (Partecipante) session.getAttribute("partecipante");
	    String uploadPathStazione = path + "\\" + "allegatiStazione\\"+""+sm.getNome()+"";
			File uploadDirP = new File(uploadPathStazione);
	    if (!uploadDirP.exists()) {
	        uploadDirP.mkdir();
	    }
	    
	    List<File> uploadFile = uploadByJavaServletAPI(request, uploadPathStazione);
	    if(!(uploadFile.isEmpty())){
	    	for(File f:uploadFile){
	    		autore = request.getParameter("autore");
	    		System.out.println("autore:"+request.getParameter("autore"));
	    		anno = request.getParameter("anno");
	    		titolo = request.getParameter("titolo");
	    		in = request.getParameter("in");
	    		fonte = request.getParameter("fonte");
	    		urlWeb = request.getParameter("urlWeb");
	    		note = request.getParameter("note");
	    		tipo = request.getParameter("tipo");
	    		ControllerDatabase.salvaAllegatoStazione(idstazione,part.getIdUtente(),autore,anno,titolo,in,fonte,urlWeb,note,tipo,f.getAbsolutePath());
	    	}
	    }
	    String content = "<h5>allegato il file per la stazione: "+sm.getNome()+"</h5>";
	    HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
	    
		}
		else if(operazione.equals("ricaricaJson")){
			ControllerJson.creaJson(path);
			forward(request,response,"/index.jsp");
		}
		
		
	}
								
			
	    
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String page)  throws ServletException, IOException {
		        ServletContext sc = getServletContext();
		        RequestDispatcher rd = sc.getRequestDispatcher(page);
		        rd.forward(request,response);
	}
	
	
	public static List<File> uploadByJavaServletAPI(HttpServletRequest request,String path) throws IOException, ServletException{
		OutputStream out = null;
    InputStream filecontent = null;
    List<File> files = new LinkedList<File>();
    Collection<Part> parts = request.getParts();	
    for(Part part:parts){   
    	if(part.getContentType() != null){
    		try {
    			System.out.println(path);
    			String fileName = getFilename(part);
    			System.out.println("nome del file: "+fileName);
    			File file = new File (path +File.separator+fileName);
    			out = new FileOutputStream(file);
    			filecontent = part.getInputStream();
    			int read = 0;
    			final byte[] bytes = new byte[1024];
    			while ((read = filecontent.read(bytes)) != -1) {
    				out.write(bytes, 0, read);
    			}
          	files.add(file);
    		} catch (FileNotFoundException fne) {
    			
    		} finally {
    			if (out != null) {
    				out.close();
    			}
    			if (filecontent != null) {
    				filecontent.close();
    			}
    		}	
    	}
    }
    return files;
	}
	
	
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

}
