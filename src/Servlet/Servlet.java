package Servlet;

import html.HTMLProcesso;
import html.HTMLStazioneMetereologica;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ControllerDatabase;
import controller.ControllerProcesso;
import controller.ControllerUbicazione;
import controller.StazioneMetereologicaController;

import bean.HTMLContent;
import bean.Processo;
import bean.StazioneMetereologica;
import bean.Ubicazione;

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
		if(operazione.equals("inserisciProcesso")){
			Processo p = ControllerProcesso.nuovoProcesso(request);
			
			request.setAttribute("Processo",p);
			forward(request,response,"/visualizzaProcesso.jsp");
		}else if(operazione.equals("inserisciStazione")){
			StazioneMetereologica s=StazioneMetereologicaController.inserisciStazioneMetereologica(request);
			
			request.setAttribute("stazione",ControllerDatabase.prendiStazioneMetereologica(s.getIdStazioneMetereologica()));
			forward(request,response,"/stazione.jsp");
		}else if(operazione.equals("inserisciUbicazione")){
			Ubicazione u=new Ubicazione();
			u=ControllerUbicazione.creaUbicazione(request);
			
			forward(request,response,"/visualizzaUbicazione.jsp");
			
		}else if(operazione.equals("mostraProcesso")){
			int idProcesso=Integer.parseInt(request.getParameter("idProcesso"));
			String content = HTMLProcesso.mostraProcesso(idProcesso);
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		}else if(operazione.equals("mostraTuttiProcessi")){
			String content=HTMLProcesso.mostraTuttiProcessi();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/processo.jsp");
		
		}else if(operazione.equals("mostraStazioneMetereologica")){
			int idStazioneMetereologica=Integer.parseInt(request.getParameter("idStazioneMetereologica"));
			String content=HTMLStazioneMetereologica.mostraStazioneMetereologica(idStazioneMetereologica);
			HTMLContent c=new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc", c);
			forward(request,response,"/stazione.jsp");
		}else if(operazione.equals("mostraTutteStazioniMetereologiche")){
			String content=HTMLStazioneMetereologica.mostraTutteStazioniMetereologiche();
			HTMLContent c = new HTMLContent();
			c.setContent(content);
			request.setAttribute("HTMLc",c);
			forward(request,response,"/stazione.jsp");
		}
	    
	}
	private void forward(HttpServletRequest request, HttpServletResponse response, String page)  throws ServletException, IOException {
		        ServletContext sc = getServletContext();
		        RequestDispatcher rd = sc.getRequestDispatcher(page);
		        rd.forward(request,response);
		  }

}
