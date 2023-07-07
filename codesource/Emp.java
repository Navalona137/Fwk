package model;

import etu1884.annotation.*;
import java.util.ArrayList;
import etu1884.obj.*;

@ClassAnnotation
public class Emp {
    int id;
	String nom;
	String prenom;
    int age;

	public Emp(int id, String nom, String prenom, int age){
		this.id = id;
        this.nom = nom;
		this.prenom = prenom;
        this.age = age;
	}
    public Emp(){}

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getAge() {
        return age;
    }

    public void setid(int id) {
        this.id = id;
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
        System.out.println("Prenom "+this.getPrenom());
        System.out.println("Age "+this.getAge());
    }

    public ArrayList<Emp> listEmp() {
        ArrayList<Emp> list = new ArrayList<Emp>();
        Emp e1 = new Emp(0, "RASOLOMANANA", "Celine", 20);
        Emp e2 = new Emp(1, "ANDRIAMALALA", "Fitia", 21);
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
