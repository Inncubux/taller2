package cl.ingenieriasoftware.demo_t2.services;

import cl.ingenieriasoftware.demo_t2.Util.AlertMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DatabaseService {
/*
    public static void createFile(String filename){
        File file = new File(filename);

        try{
            PrintWriter output = new PrintWriter(file);
            output.close();


        } catch(FileNotFoundException ex){
            ex.printStackTrace(System.out);
        }
    }
*/
    public static void write(String filename, String content){
        File file = new File(filename);

        try {
            PrintWriter output = new PrintWriter(file);
            output.println(content);
            output.close();
        } catch (FileNotFoundException ex) {
            //ex.printStackTrace(AlertMessage.show(,););
        }
    }


}
