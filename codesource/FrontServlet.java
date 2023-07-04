package etu1884.framework.servlet;

import etu1884.framework.Mapping;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import etu1884.obj.Utilitaire;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    
    public void init() {
        try{
            String p = this.getServletContext().getRealPath("");
            this.mappingUrls = Utilitaire.getUrls(Utilitaire.allMapping(p));      
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Utilitaire u = new Utilitaire();
        String wordsPath = u.getLastOfPath(String.valueOf(request.getRequestURL()), request.getContextPath());
        out.println(wordsPath+"<br>");
        
        try{
            init();
            for(Map.Entry<String, Mapping> entry : mappingUrls.entrySet()){
                out.println("Classe: "+entry.getValue().getClassName()+"\tMethod: "+entry.getValue().getMethod()+"<br>");
            }   
        }catch(Exception e){
            out.print(e);
            out.print("tsy mety");
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
