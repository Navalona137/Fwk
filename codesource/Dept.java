package model;

import etu1884.annotation.*;

@ClassAnnotation
public class Dept{
    @MyAnnotation(value = "dept-findAll")
    public void findAllDept(){
        System.out.println("findAll");
    }

	@MyAnnotation(value = "dept-add")
    public void addDept(){
        System.out.println("findAll");
    }

}
