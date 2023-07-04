package model;

import etu1884.annotation.*;

@ClassAnnotation
public class Dept {
    @MyAnnotation
    public void findAllDept(){
        System.out.println("findAll");
    }

	@MyAnnotation
    public void addDept(){
        System.out.println("findAll");
    }
    
}
