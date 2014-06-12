package controller;


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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;





import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import bean.ClasseVolume;
import bean.Danni;
import bean.EffettiMorfologici;
import bean.Litologia;
import bean.Processo;
import bean.ProprietaTermiche;
import bean.SitoProcesso;
import bean.StatoFratturazione;
import bean.TipologiaProcesso;
import bean.Ubicazione;

public class ControllerProcesso {
	public static Processo creaProcesso(HttpServletRequest request) throws ParseException{//qui creo le parti solo del processo
		Processo p = new Processo();
		if(!(request.getParameter("nome")==null))
		p.setNome(request.getParameter("nome"));
		String data="";
		String ora="00:00";
		StringBuilder formatoData = new StringBuilder();
		String anno="0000";
		String mese="01";
		String giorno="01";
		
		if(!(request.getParameter("anno")==null)){
			if(!(request.getParameter("anno").equals(""))){
				formatoData.append("1");
				anno = request.getParameter("anno");
			}else formatoData.append("0");
		}else formatoData.append("0");
		
		if(!(request.getParameter("mese")==null)){
			if(!(request.getParameter("mese").equals("vuoto"))){
				mese = request.getParameter("mese");
				formatoData.append("1");
			}else formatoData.append("0");
		}else formatoData.append("0");
		
		if(!(request.getParameter("girno")==null)){
			if(!(request.getParameter("giorno").equals("vuoto"))){
				giorno = request.getParameter("giorno");
				formatoData.append("1");
			} else formatoData.append("0");
		}else formatoData.append("0");
		data = ""+anno+"-"+mese+"-"+giorno+"";
		if(!(request.getParameter("ora")==null)){
		if(!(request.getParameter("ora").equals(""))){
			ora = request.getParameter("ora");
			formatoData.append("1");
		}else formatoData.append("0");
		}else formatoData.append("0");
		String dataCompleta = ""+data+" "+ora+":00";
		p.setData((Timestamp.valueOf(dataCompleta)));
		System.out.println("formato data: "+formatoData);
		if(!(formatoData.toString().equals("")))
		p.setFormatoData(Integer.parseInt(formatoData.toString()));
		if(!(request.getParameter("descrizione")==null)){
			String descrizione = request.getParameter("descrizione");
			p.setDescrizione(descrizione);
		}
		System.out.println("descrizione da htm: "+(request.getParameter("descrizione")));
		System.out.println("descrizione da processo: "+(p.getDescrizione()));
		if(!(request.getParameter("note")==null))
		p.setNote(request.getParameter("note"));
		if(!(request.getParameter("altezza")==null)){
			if(!(request.getParameter("altezza").equals("")))
				p.setAltezza(Double.parseDouble(request.getParameter("altezza")));
		}
		if(!(request.getParameter("larghezza")==null)){
			if(!(request.getParameter("larghezza").equals("")))
				p.setLarghezza(Double.parseDouble(request.getParameter("larghezza")));
		}
		if(!(request.getParameter("superficie")==null)){
			if(!(request.getParameter("superficie").equals("")))
				p.setSuperficie(Double.parseDouble(request.getParameter("superficie")));
		}
		if(!(request.getParameter("volumespecifico")==null)){
			if(!(request.getParameter("volumespecifico").equals("")))
				p.setVolumeSpecifico(Double.parseDouble(request.getParameter("volumespecifico")));
		}
		
		return p;
	}
	public static Processo nuovoProcesso(HttpServletRequest request,String loc) throws ParseException, SQLException{ //qui ci metto tutte le informazioni e la pubblicazione sul db + ubicazione, sito, allegati, effetti
		Processo p = creaProcesso(request);
		
		Ubicazione u = ControllerUbicazione.creaUbicazione(request);
		
		SitoProcesso sp = creaSito(request,loc);
		ClasseVolume cv = creaClasseVolume(request);
		ArrayList<EffettiMorfologici> em = creaEffettiMorfologici(request,loc);
		ArrayList<Danni> d = creaDanni(request,loc);
		ArrayList<TipologiaProcesso> tp = creaTipologiaProcesso(request,loc);
		Litologia l = creaLitologia(request,loc);
		StatoFratturazione sf = creaStatoFratturazione(request,loc);
		ProprietaTermiche pt = creaProprietaTermiche(request,loc);
		p.setProprietaTermiche(pt);
		p.setStatoFratturazione(sf);
		p.setLitologia(l);
		p.setTipologiaProcesso(tp);
		p.setDanni(d);
		p.setEffetti(em);
		p.setClasseVolume(cv);
		p.setSitoProcesso(sp);
		p.setUbicazione(u);
		return p;
	}
	
	
	/*
	 * caratteristiche del processo
	 */
	public static SitoProcesso creaSito(HttpServletRequest request,String loc) throws SQLException{
		SitoProcesso sp = new SitoProcesso();
		if(!(request.getParameter("idsito")==null)){
			if(!(request.getParameter("idsito").equals("")))
				sp.setIdSito(Integer.parseInt(request.getParameter("idsito")));
			if(loc.equals("IT"))
				sp.setCaratteristicaSito_IT(request.getParameter("caratteristicaSito_IT"));
			if(loc.equals("ENG"))
				sp.setCaratteristicaSito_ENG(request.getParameter("caratteristicaSito_ENG"));
		}
		return sp; 
	}
	public static Litologia creaLitologia(HttpServletRequest request,String loc) throws SQLException{
		Litologia l = new Litologia();
		if(!(request.getParameter("idLitologia")==null)){
		if(!(request.getParameter("idLitologia").equals("")))
		l.setIdLitologia(Integer.parseInt(request.getParameter("idLitologia")));
		if(loc.equals("IT"))
		l.setNomeLitologia_IT(request.getParameter("nomeLitologia_IT"));
		if(loc.equals("ENG"))
		l.setNomeLitologia_ENG(request.getParameter("nomeLitologia_ENG"));
		}
		return l;
	}
	public static ClasseVolume creaClasseVolume(HttpServletRequest request){
		ClasseVolume cv = new ClasseVolume();
		if(!(request.getParameter("idClasseVolume")==null)){
			if(!(request.getParameter("idclasseVolume").equals("")))
				cv.setIdClasseVolume(Integer.parseInt(request.getParameter("idclasseVolume")));
			cv.setIntervallo(request.getParameter("intervallo"));
		}
		return cv;
	}
	

	public static ProprietaTermiche creaProprietaTermiche(HttpServletRequest request,String loc){
		ProprietaTermiche pt = new ProprietaTermiche();
		if(!(request.getParameter("idProprietaTermiche")==null)){
			if(!(request.getParameter("idProprietaTermiche").equals("")))
				pt.setIdProprietaTermiche(Integer.parseInt(request.getParameter("idProprietaTermiche")));
			if(loc.equals("IT"))
				pt.setProprietaTermiche_IT(request.getParameter("proprietaTermiche_IT"));
			if(loc.equals("ENG"))
				pt.setProprietaTermiche_ENG(request.getParameter("proprietaTermiche_ENG"));
		}
		return pt;
	}
	public static StatoFratturazione creaStatoFratturazione(HttpServletRequest request,String loc){
		StatoFratturazione pt = new StatoFratturazione();
		if(!(request.getParameter("idStatoFratturazione")==null)){
			if(!(request.getParameter("idStatoFratturazione").equals("")))
				pt.setIdStatoFratturazione(Integer.parseInt(request.getParameter("idStatoFratturazione")));
			if(loc.equals("IT"))
				pt.setStatoFratturazione_IT(request.getParameter("statoFratturazione_IT"));
			if(loc.equals("ENG"))
				pt.setStatoFratturazione_ENG(request.getParameter("statoFratturazione_ENG"));
		}
		return pt;
	}
	
	public static ArrayList<EffettiMorfologici> creaEffettiMorfologici(HttpServletRequest request,String loc) throws SQLException{
	ArrayList<EffettiMorfologici> em = new ArrayList<EffettiMorfologici>();
	String[] emtipo_it;
	String[] emtipo_eng;

	if(loc.equals("IT")){
		if(!(request.getParameter("emtipo_IT")==null)){
			if(!(request.getParameter("emtipo_IT")==null)){
				emtipo_it = request.getParameterValues("emtipo_IT");
				for(int i = 0;i<emtipo_it.length;i++){
					EffettiMorfologici e = new EffettiMorfologici();
					e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_it[i], "IT"));
					e.setTipo_IT(emtipo_it[i]);
					em.add(e);
				}
			}
		}
	}
	if(loc.equals("ENG")){
		if(!(request.getParameter("emtipo_ENG")==null)){
			if(!(request.getParameter("emtipo_ENG")==null)){
				emtipo_eng = request.getParameterValues("emtipo_ENG");
				for(int i = 0;i<emtipo_eng.length;i++){
					EffettiMorfologici e = new EffettiMorfologici();
					e.setIdEffettiMOrfologici(ControllerDatabase.prendiIdEffettiMorfologici(emtipo_eng[i], "ENG"));
					e.setTipo_ENG(emtipo_eng[i]);
					em.add(e);
				}
			}	
		}
	}
	
		return em;
	}
	public static ArrayList<Danni> creaDanni(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<Danni> d = new ArrayList<Danni>();
		String[] dtipo_it;
		String[] dtipo_eng;
		if(loc.equals("IT")){
			if(!(request.getParameterValues("dtipo_IT")==null)){
				dtipo_it = request.getParameterValues("dtipo_IT");
				for(int i = 0;i<dtipo_it.length;i++){
					Danni da = new Danni();
					da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_it[i], "IT"));
					da.setTipo_IT(dtipo_it[i]);
					d.add(da);
				}
			}
		}
		if(loc.equals("ENG")){
			if(!(request.getParameter("dtipo_ENG")==null)){
				dtipo_eng = request.getParameterValues("dtipo_ENG");
				for(int i = 0;i<dtipo_eng.length;i++){
					Danni da = new Danni();
					da.setIdDanni(ControllerDatabase.prendiIdDanni(dtipo_eng[i], "ENG"));
					da.setTipo_ENG(dtipo_eng[i]);
					d.add(da);
				}
			}
		}
		return d;
	}
	public static ArrayList<TipologiaProcesso> creaTipologiaProcesso(HttpServletRequest request,String loc) throws SQLException{
		ArrayList<TipologiaProcesso> t = new ArrayList<TipologiaProcesso>();
		String[] tpnome_it;
		String[] tpnome_eng;
		if(loc.equals("IT")){
			if(!(request.getParameterValues("tpnome_IT")==null)){
				tpnome_it = request.getParameterValues("tpnome_IT");
				for(int i = 0;i<tpnome_it.length;i++){
					TipologiaProcesso tp = new TipologiaProcesso(); 
					tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_it[i],"IT"));
					tp.setNome_IT(tpnome_it[i]);
					t.add(tp);
				}
			}
		}
		if(loc.equals("ENG")){
			if(!(request.getParameterValues("tpnome_ENG")==null)){
				tpnome_eng = request.getParameterValues("tpnome_ENG");
				for(int i = 0;i<tpnome_eng.length;i++){
					TipologiaProcesso tp = new TipologiaProcesso(); 
					tp.setIdTipologiaProcesso(ControllerDatabase.prendiIdTipologiaProcesso(tpnome_eng[i],"ENG"));
					tp.setNome_ENG(tpnome_eng[i]);
					t.add(tp);
				}
			}
		}
			return t;
	}
	
	public static void fileInput(HttpServletRequest request, String path) throws IllegalStateException, IOException, ServletException{
		final Part filePart = request.getPart("file");
    final String fileName = getFileName(filePart);
    OutputStream out = null;
    InputStream filecontent = null;
    
    try {
    	System.out.println(path);
    	File file = new File (path + File.separator+ fileName);
    	out = new FileOutputStream(file);
    	filecontent = filePart.getInputStream();
    	int read = 0;
    	final byte[] bytes = new byte[1024];
    	while ((read = filecontent.read(bytes)) != -1) {
    		out.write(bytes, 0, read);
    	}
    
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
	
	private static String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    for (String content : part.getHeader("content-disposition").split(";")) {
        if (content.trim().startsWith("filename")) {
        	return content.substring(
        			content.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return null;	
	}

	
	public static void fileMultiplo(HttpServletRequest request) throws Exception{
		 ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
		 JsonArray json = new JsonArray();
		 try {
       List<FileItem> items = uploadHandler.parseRequest(request);
       for (FileItem item : items) {
           if (!item.isFormField()) {
                   File file = new File(request.getServletContext().getRealPath("/")+"imgs/", item.getName());
                   item.write(file);
                   JsonObject jsono = new JsonObject();
                   jsono.addProperty("name", item.getName());
                   jsono.addProperty("size", item.getSize());
                   jsono.addProperty("url", "UploadServlet?getfile=" + item.getName());
                   jsono.addProperty("thumbnail_url", "UploadServlet?getthumb=" + item.getName());
                   jsono.addProperty("delete_url", "UploadServlet?delfile=" + item.getName());
                   jsono.addProperty("delete_type", "GET");
                   json.add(jsono);
                   System.out.println(json.toString());
           }
       }
   } catch (FileUploadException e) {
           throw new RuntimeException(e);
   }
	}
}


