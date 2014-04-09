package Servlet;

import html.HTMLElaborazioni;
import html.HTMLProcesso;
import html.HTMLStazioneMetereologica;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ControllerDatabase;
import controller.ControllerJson;
import controller.ControllerProcesso;
import controller.ControllerUbicazione;
import controller.ControllerStazioneMetereologica;
import controller.ControllerUtente;
import bean.HTMLContent;
import bean.LocazioneAmministrativa;
import bean.LocazioneIdrologica;
import bean.Processo;
import bean.StazioneMetereologica;
import bean.Ubicazione;
import bean.Utente;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
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
		}
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
		
		String operazione = (String) request.getParameter("operazione");
		String path = getServletContext().getRealPath("/");
		System.out.println(path);
		String loc = "IT";//oggetto sessione
		
		/*
		 * Processo
		 */
		if(operazione.equals("formInserisciProcesso")){
			String content = HTMLProcesso.formInserisciProcesso(path,loc);
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
		else if(operazione.equals("inserisciUtente")){
			Utente utente=ControllerUtente.nuovoUtente(request);
			utente=ControllerDatabase.prendiUtente(utente);
	/*		String content=HTMLUtente.creazioneUtente(utente);
			HTMLContent c=new HTMLContent();
			c.setContent(content);*/
			request.setAttribute("HTMLc",utente);
			forward(request,response,"/visualizzautente.jsp");
		}
	    
	}
			
								
			
	    
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String page)  throws ServletException, IOException {
		        ServletContext sc = getServletContext();
		        RequestDispatcher rd = sc.getRequestDispatcher(page);
		        rd.forward(request,response);
		  }

}
