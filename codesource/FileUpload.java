package etu1884.obj;

import java.util.HashMap;
import java.io.FileOutputStream;

public class FileUpload {
    String name;
    byte[] bits;

    public FileUpload(String name,byte[] bits){
        this.name=name;
        this.bits=bits;
    }

    public String getName(){
        return this.name;
    }

    public byte[] getBits(){
        return this.bits;
    }

    public void setNamePhoto(String name){
        this.name=name;
    }

    public void setBits(byte[] bits){
        this.bits=bits;
    }

    public void upload(String emplacement){
        try(FileOutputStream fileOutputStream=new FileOutputStream(emplacement+"/"+this.getName())){
            fileOutputStream.write(this.getBits());
            fileOutputStream.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}