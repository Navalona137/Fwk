package etu1884.obj;

import etu1884.framework.Mapping;
import etu1884.annotation.*;
import etu1884.obj.*;

import java.util.ArrayList;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.Class;
import java.lang.*;
import java.util.HashMap;


public class Utilitaire {
        public Utilitaire() {
    }
    
    public String getLastOfPath(String path, String context){
        String contxt = context+"/";
        String[] words = path.split(contxt);
        String[] lastpath = new String[words.length];
        String last = new String();
        String result = new String();
        char[] res; 
        for(int i=1; i<words.length; i++){
            lastpath[i]= words[i];
            last = lastpath[i];
            result = last;
        }
        
        if(last.contains("?")){
            char[] data = last.toCharArray();
            for(int i=0; i<data.length; i++){
                if(data[i]=='?'){
                    res = new char[i];
                    for(int j=0; j<data.length; j++){
                        if(data[j]!='?')
                            res[j] = data[j];
                        else
                            break;
                    }
                    result = new String(res);
                }   
            }
            
        }
        return result;
    }
    
    
    public static ArrayList<String> allPackage(String paths) throws Exception{
        ArrayList<String> allPack = new ArrayList<String>();
        String p = "\\WEB-INF\\classes\\";   
        File path = new File(paths+p);
        File[] contenus = path.listFiles();
        if(contenus == null){
            throw new Exception("Dossier vide ou inexistant");
        }
        for(int i=0; i<contenus.length; i++){
            String res = contenus[i].getName();
            if(!res.contains("."))
                allPack.add(res);
        }
        return allPack;
    }

    public static ArrayList<String> allClass(String p, String paths) throws Exception{
        ArrayList<String> allClass = new ArrayList<String>();
        String pa = "\\WEB-INF\\classes\\";
        String pathName = p+pa+paths;
        File path = new File(pathName);
        File[] contenus = path.listFiles();
        if(contenus == null){
            throw new Exception("Package vide ou inexistant");
        }
        for(int i=0; i<contenus.length; i++){
            String res = contenus[i].getName();
            if(res.contains("."))
                allClass.add(res);
        }
        return allClass;    
    }

    public static ArrayList<Mapping> allMapping(String path)throws Exception{
        ArrayList<Mapping> allMapping = new ArrayList<Mapping>();
        ArrayList<String> allPackage = allPackage(path);
        for(int i=0; i<allPackage.size(); i++){
            String m = allPackage.get(i);
            ArrayList<String> allClass = allClass(path, m);
            for(int j=0; j<allClass.size(); j++){
                String s = m+"."+allClass.get(j).split(".class")[0];
                Class<?> trueClasse = Class.forName(s);
                if(trueClasse.getAnnotation(ClassAnnotation.class)!=null){
                    String name = trueClasse.getName();
                    Method[] allMethodeInClass = trueClasse.getDeclaredMethods();
                    for (int k=0; k<allMethodeInClass.length; k++) {
                        if(allMethodeInClass[k].getAnnotation(MyAnnotation.class)!=null){
                            String met = allMethodeInClass[k].getName();
                            Mapping mapping = new Mapping(null, null); 
                            mapping.setClassName(s);
                            mapping.setMethod(met);
                            allMapping.add(mapping);
                        }
                    }
                }    
            }            
        }
        return allMapping;
    }

    public static ArrayList<String> allUrl(String path)throws Exception{
        ArrayList<String> allUrls = new ArrayList<String>();
        ArrayList<String> allPackage = allPackage(path);
        for(int i=0; i<allPackage.size(); i++){
            String m = allPackage.get(i);
            ArrayList<String> allClass = allClass(path, m);
            for(int j=0; j<allClass.size(); j++){
                String s = m+"."+allClass.get(j).split(".class")[0];
                Class<?> trueClasse = Class.forName(s);
                if(trueClasse.getAnnotation(ClassAnnotation.class)!=null){
                    String name = trueClasse.getName();
                    Method[] allMethodeInClass = trueClasse.getDeclaredMethods();
                    for (int k=0; k<allMethodeInClass.length; k++) {
                        if(allMethodeInClass[k].getAnnotation(MyAnnotation.class)!=null){
                            String url = allMethodeInClass[k].getAnnotation(MyAnnotation.class).value();
                            allUrls.add(url);
                        }
                    }
                }    
            }            
        }
        return allUrls;
    }    

    public static HashMap<String, Mapping> getUrls(ArrayList<Mapping> allMapping, ArrayList<String> allUrl){
        HashMap<String, Mapping> allUrls = new HashMap<String, Mapping>();
        for(int i=0; i<allMapping.size(); i++){
            Mapping m = allMapping.get(i);
            String url= allUrl.get(i);
            allUrls.put(url, m);
        }
        return allUrls;
    }    

    public static Mapping getMapping(String annotation,HashMap<String,Mapping> hashmap) throws Exception{
       Mapping mapping=hashmap.get(annotation);
       if(mapping==null)
           throw new Exception("tsy hita");
       return mapping;
   }
   
}
