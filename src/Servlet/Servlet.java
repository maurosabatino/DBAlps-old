package Servlet;

import html.HTMLElaborazioni;
import html.HTMLProcesso;
import html.HTMLStazioneMetereologica;
import html.HTMLUtente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
			Partecipante part = (Partecipante)session.getAttribute("partecipante");
			Processo p = ControllerProcesso.nuovoProcesso(request,loc);
			ControllerDatabase.salvaUbicazione(p.getUbicazione());
			ControllerDatabase.salvaProcesso(p,part);
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
					if(!(request.getParameter("ora").equals(""))){
						ora = request.getParameter("ora");
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
		else if(operazione.equals("visualizzaTuttiUtenti")){
			String content = HTMLUtente.visualizzaTuttiUtente();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/utente.jsp");
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
