package Servlet;

import html.HTMLElaborazioni;
import html.HTMLProcesso;
import html.HTMLStazioneMetereologica;
import html.HTMLUtente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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
		String path = getServletContext().getRealPath("/");
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
			Processo p = ControllerProcesso.nuovoProcesso(request,loc);
			ControllerDatabase.salvaUbicazione(p.getUbicazione());
			ControllerDatabase.salvaProcesso(p);
			ControllerDatabase.salvaEffetti(p.getIdProcesso(), p.getEffetti(), p.getDanni());
			ControllerDatabase.salvaTipologiaProcesso(p.getIdProcesso(), p.getTipologiaProcesso());
			String content = HTMLProcesso.mostraProcesso(p.getIdProcesso());
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		
		}
		else if(operazione.equals("mostraProcesso")){
			System.out.println("stampa");
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
		if(operazione.equals("formCercaProcessi")){
			ControllerJson.creaJson(path);
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
			String content=HTMLStazioneMetereologica.mostraTutteStazioniMetereologiche();
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
	//elaborazioni
		else if(operazione.equals("eleborazioniDeltaT")){	
			String [] id= request.getParameterValues("id");
			int finestra=Integer.parseInt(request.getParameter("finestra"));
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"));
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			String content=HTMLElaborazioni.deltaT(id,finestra,aggregazione,data);
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
			String content=HTMLStazioneMetereologica.scegliStazioniMetereologicheT();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");

		}
		else if(operazione.equals("eleborazioniTemperatura")){
			String [] id= request.getParameterValues("id");
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"));
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			String content=HTMLElaborazioni.mediaTemperatura(id, aggregazione,data);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/elaborazioni.jsp");
		}
		else if(operazione.equals("precipitazioni")){
			String [] id= request.getParameterValues("id");
			int finestra=Integer.parseInt(request.getParameter("finestra"));
			int aggregazione=Integer.parseInt(request.getParameter("aggregazione"));
			String ora="00:00:00";
			Timestamp data=Timestamp.valueOf(""+request.getParameter("data")+" "+ora);
			String content=HTMLElaborazioni.mediaPrecipitazioni(id, finestra, aggregazione, data);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
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
				System.out.println("login corretto");
				HTMLContent c = new HTMLContent();
				Partecipante utente=ControllerDatabase.prendiUtente(username);
				session.setAttribute("partecipante", utente);
				String content = HTMLUtente.visualizzaUtente(utente);
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
		
	}
								
			
	    
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String page)  throws ServletException, IOException {
		        ServletContext sc = getServletContext();
		        RequestDispatcher rd = sc.getRequestDispatcher(page);
		        rd.forward(request,response);
	}
	



}
