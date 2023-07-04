package model;

import etu1884.annotation.*;
import java.util.ArrayList;
import etu1884.obj.*;

@ClassAnnotation
public class Emp {
	String nom;
	String prenom;
    int age;

	public Emp(String nom, String prenom, int age){
		this.nom = nom;
		this.prenom = prenom;
        this.age = age;
	}
    public Emp(){}

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getAge() {
        return age;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }
    public void setprenom(String prenom) {
        this.prenom = prenom;
    }
    public void setage(int age) {
        this.age = age;
    }

    @MyAnnotation(value = "emp-findAll")
    public void findAll(){
        System.out.println("findAll");
    }
    
    @MyAnnotation(value = "emp-add")
    public void add(){
        System.out.println(this.getNom());
        System.out.println(this.getAge());
        System.out.println("add");
    }

    public ArrayList<Emp> listEmp() {
        ArrayList<Emp> list = new ArrayList<Emp>();
        Emp e1 = new Emp("RASOLOMANANA", "Celine", 20);
        Emp e2 = new Emp("ANDRIAMALALA", "Fitia", 21);
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
}
