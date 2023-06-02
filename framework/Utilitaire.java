/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitaire;

import etu1758.framework.servlet.Frontservlet;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author mahery
 */
public class Utilitaire {

    public Utilitaire() {
    }
    
    public String[] getUrl(String allURL,String contextPath )
    {
        String[] sub = allURL.split(contextPath);
        String[] sub2 = sub[1].split("/",2);
        String url =sub2[1];
        
        return url.split("/");
    }
    
    //packageUrl:chemin absolue du package
     // packageName:nom du package
    
    
    public Vector<Class> getAllClassInPackage(String packageUrl, String packageName)throws Exception{   
        File file = new File(packageUrl);
        File[] filesIn = file.listFiles();
        Vector<Class> allClass = new Vector<Class>();
        for(File fl : filesIn){
            if (fl.isDirectory() == true) {

                String pathFl = packageUrl +"/"+fl.getName();
    //ity ilay blem
                if (packageUrl.endsWith("/") == true) {
                    pathFl = packageUrl +fl.getName();
                }
                System.out.println("pathFl:: "+pathFl);
               Vector<Class> classTemp = getAllClassInPackage(pathFl, packageName);
               for(Class c : classTemp){
                   System.out.println("add class ::"+c.getName());
                   allClass.add(c);
                }


            }else{
                
                if (fl.getName().endsWith(".class") == true) {
                    String pathClass = packageName;
                    
                    if (packageUrl.substring(packageUrl.lastIndexOf("/")+1).equals(packageName) == true) {
                    System.out.println("package :: "+packageUrl.substring(packageUrl.lastIndexOf("/")+1)+"== "+packageName);
     
                    pathClass = packageUrl.substring(packageUrl.indexOf(packageName));
                    pathClass = pathClass.replace("/", ".");
                    String urlClass = pathClass+"."+fl.getName().substring(0,fl.getName().lastIndexOf(".class"));
                    System.out.println("urlClass :: "+urlClass);
                    Class c = Class.forName(urlClass);
                    System.out.println("else :: add class ::: "+c.getName());
                    allClass.add(c);
                    }

                }
            }
        }


        return allClass;
    }
    

}

