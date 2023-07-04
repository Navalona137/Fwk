-package etu1884.framework.servlet;

import etu1884.framework.Mapping;
import etu1884.obj.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Method;
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
            this.mappingUrls = Utilitaire.getUrls(Utilitaire.allMapping(p), Utilitaire.allUrl(p));      
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
                out.println("Annotation "+entry.getKey()+"\tClasse: "+entry.getValue().getClassName()+"\tMethod: "+entry.getValue().getMethod()+"<br>");
            }
            
            Mapping mapping=Utilitaire.getMapping(wordsPath, mappingUrls);
            Class<?> classInstance = Class.forName(mapping.getClassName());
            Method function = classInstance.getMethod(mapping.getMethod());
            ModelView invomethode = (ModelView) function.invoke(classInstance.newInstance());
            out.println("methode: /"+ invomethode.getView());
            RequestDispatcher dispat = request.getRequestDispatcher("./"+ invomethode.getView());
            dispat.forward(request, response);    
        }catch(Exception e){
            out.print(e);
            e.printStackTrace();
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
