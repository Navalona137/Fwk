package model;

import etu1884.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import etu1884.obj.*;

@ClassAnnotation
public class Emp {
    int id;
	String nom;
    FileUpload file;
	
	public Emp(int id, String nom){
		this.id = id;
        this.nom = nom;
	}
    public Emp(){}

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public FileUpload getFile(){
        return this.file;
    }
    
    public void setid(int id) {
        this.id = id;
    }
    public void setnom(String nom) {
        this.nom = nom;
    }
    public void setfile(FileUpload file){
        this.file=file;
    }
    
    @MyAnnotation(value = "emp-findAll")
    public ModelView findAll(int id){
        System.out.println("findAll");
        ModelView view = new ModelView();
        view.setView("details.jsp");
        view.addItem("emp", this.listEmp().get(id));
        return view;
    }
    
    @MyAnnotation(value = "emp-add")
    public void add(){
        System.out.println("add");
        System.out.println("Id "+this.getId());
        System.out.println("Nom "+this.getNom());
    }

    public ArrayList<Emp> listEmp() {
        ArrayList<Emp> list = new ArrayList<Emp>();
        Emp e1 = new Emp(0, "RAKOTO");
        Emp e2 = new Emp(1, "ANDRIAMALALA");
        list.add(e1);
        list.add(e2);
        return list;
    }

    @MyAnnotation(value = "emp-view")
    public ModelView getView(){
    	ModelView view = new ModelView();
    	view.setView("hello.jsp");
    	view.addItem("list", this.listEmp());
    	return view;
    }

    @MyAnnotation(value = "emp-save")
    public ModelView save(){
        ModelView view=new ModelView("AffichageEmp.jsp");
        view.addItem("attribut",this.getFile().getName());
        return view;
    }
}
