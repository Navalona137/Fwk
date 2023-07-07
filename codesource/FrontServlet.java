package etu1884.framework.servlet;

import etu1884.framework.Mapping;
import etu1884.obj.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.*;
import java.lang.reflect.*;
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

    public Object[] getParameterValues(HttpServletRequest request, Parameter[] args) {
        Object[] valueArgs = new Object[args.length];

        for (int i=0; i<args.length; i++) {
            valueArgs[i] = Utilitaire.castToAppropriateClass(request.getParameter(args[i].getName()), args[i].getType());
        }
        return valueArgs;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Utilitaire u = new Utilitaire();
        String wordsPath = u.getLastOfPath(String.valueOf(request.getRequestURL()), request.getContextPath());
        
        try{

            init();
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Annotation</th>");
            out.println("<th>Classe</th>");
            out.println("<th>Methode</th>");
            out.println("</tr>");
            for(Map.Entry<String, Mapping> entry : mappingUrls.entrySet()){
                out.println("<tr>");
                out.println("<td>"+entry.getKey()+"</td>");
                out.println("<td>"+entry.getValue().getClassName()+"</td>");
                out.println("<td>"+entry.getValue().getMethod()+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            Mapping mapping=Utilitaire.getMapping(wordsPath, mappingUrls);
            Class<?> classInstance = Class.forName(mapping.getClassName());
            Object newObjet = classInstance.newInstance();
            Field[] attributs = newObjet.getClass().getDeclaredFields();
            for(int i=0; i<attributs.length; i++){
                String valeur = request.getParameter(attributs[i].getName());
                if(valeur!=null){
                    if(attributs[i].getType().getSimpleName().equals("int")){
                        int value = Integer.valueOf(valeur);
                        attributs[i].setAccessible(true);
                        attributs[i].set(newObjet, value);    
                    }else{
                        attributs[i].setAccessible(true);
                        attributs[i].set(newObjet, valeur);
                    }
                }
            }

            Method[] methods = classInstance.getDeclaredMethods();
            Class<?>[] functionParameters = null;
            for (Method method : methods) {
                if(method.getName() == mapping.getMethod()) {
                    functionParameters = method.getParameterTypes();
                }
            }
            
            Method function = newObjet.getClass().getMethod(mapping.getMethod(), functionParameters);
            Parameter[] args = function.getParameters();
            Object[] valueArgs = getParameterValues(request, args);
            out.println("Taille "+ args.length);

            ModelView view = (ModelView) function.invoke(newObjet, valueArgs);
            for(HashMap.Entry<String, Object> entry : view.getData().entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            out.println("tonga ve");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./"+ view.getView());
            dispatcher.forward(request, response);    
        }catch(Exception e){
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
