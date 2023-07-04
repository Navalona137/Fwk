package model;

import etu1884.annotation.*;

@ClassAnnotation
public class Emp {
    @MyAnnotation
    public void findAll(){
        System.out.println("findAll");
    }
    
    @MyAnnotation
    public void add(){
        System.out.println("add");
    }
}
