/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitaire;

import etu1758.framework.servlet.Frontservlet;
import etu1758.framework.Mapping;
import java.io.File;
import java.util.Vector;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
/**
 *
 * @author mahery
 */
public class Utilitaire {


    public static Object cast(String toCast, Class typeOfCast) throws Exception {
        if (typeOfCast == int.class || typeOfCast == Integer.class) {
            return Integer.parseInt(toCast);
        } else if (typeOfCast == double.class || typeOfCast == Double.class) {
            return Double.parseDouble(toCast);
        } else if (typeOfCast == Date.class) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = format.parse(toCast);
            return new java.sql.Date(utilDate.getTime());
        } else if (typeOfCast == Boolean.class) {
            return Boolean.parseBoolean(toCast);
        }
    
        return toCast;
    }

    //Obtenir toutes les classes dans chaque dossier
    private static List<Class<?>> getClassesDansDossiers(File dossier, String nomDePackage)throws Exception{
        //System.out.println(dossier.getAbsolutePath() + " PATH");
        List<Class<?>> classes = new ArrayList<>();
        if(dossier.getAbsolutePath().toString().contains("%20")){
            dossier = new File(dossier.getAbsolutePath().toString().replace("%20", " "));
        }
        //System.out.println(dossier.getAbsolutePath() + " PATH 2");
        try {
            if(!dossier.exists()){
                return classes;
            }else{
                    File[] fichiersDansDossier = dossier.listFiles();
                    for (File fichier : fichiersDansDossier) {
                        if(fichier.isDirectory()){
                            assert !fichier.getName().contains(".");
                            classes.addAll(getClassesDansDossiers(fichier, nomDePackage + "." + fichier.getName()));
                        }else if(fichier.getName().endsWith(".class") == true){
                            String nomDeClasse = nomDePackage + "." + fichier.getName().substring(0, fichier.getName().length()-6);
                            classes.add(Class.forName(nomDeClasse));
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
         // TODO: handle exception
        }
        return classes;
    }
    
    //Avoir toutes les classes dans un package spécifié
    private static List<Class<?>> getLesClasses(String packageScannes) throws Exception{
        //System.out.println(" packageScannes : " + packageScannes);
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String chemin = packageScannes.replace('.', '/');
            Enumeration<URL> ressources = classLoader.getResources(chemin);
            //System.out.println(ressources.nextElement().getFile());
            List<File> dossiers = new ArrayList<>();
            while(ressources.hasMoreElements()){
                URL ressource = ressources.nextElement();
                dossiers.add(new File(ressource.getFile()));
            }
            // System.out.println(dossiers.size());
            for (File dossier : dossiers) {
                classes.addAll(getClassesDansDossiers(dossier, packageScannes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        // TODO: handle exception
        }
        return classes;
    }

    public static HashMap<String, Mapping> getMethodesAnnotees(String nomDePackage, Class<? extends Annotation> annotationDeClasse) throws Exception{
        HashMap<String, Mapping> methodesAnnotees = new HashMap<String, Mapping>();
        try {
            List<Class<?>> classes = getLesClasses(nomDePackage);
            for (Class<?> class1 : classes) {
                Method[] listesMethodes = class1.getDeclaredMethods();
                for (Method methode : listesMethodes) {
                    Annotation annotation = methode.getAnnotation(annotationDeClasse);
                    if(annotation != null){/* 
                        System.out.println("methode " + ((Url) annotation).method());
                        System.out.println("nomdeclasse " + class1.getName());
                        System.out.println("nomdemethode " + methode.getName()); */
                        methodesAnnotees.put(((Url) annotation).method(), new Mapping( class1.getName(), methode.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
          // TODO: handle exception
        }
        return methodesAnnotees;
    }
    
    
    public static ModelView modelDeRedirection (HttpServletRequest request, HashMap<String, Mapping> mappingUrls)throws Exception, ServletException, IOException{
        System.out.println(request.getServletPath() + " SERVLET PATH");
        String servletPath = request.getServletPath();
        String[] path = servletPath.split("/");
        ModelView modelView = new ModelView();
            for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
                String clef = entry.getKey();// clef
                Mapping map = entry.getValue(); // valeur
                System.out.println(clef + " - " + map.getMethod().toString());
                if(path[1].equals(clef) == true){
                    String nomDeClasseDeMethode = mappingUrls.get(path[1]).getClassName();
                    //Prendre la classe mère
                    String laClasse = nomDeClasseDeMethode;
                    System.out.println(laClasse + " LA CLASEEEEEEEEEEEEEEEE");
                    //Prendre la méthode en string
                    String laMethode = map.getMethod();
                    System.out.println(laMethode + " LA METHODEEEEEEEEEEEEEEE");
        
                    //Invocation de la méthode
                    Class<?> appel = Class.forName(laClasse);
                    Object objectC = appel.getDeclaredConstructor().newInstance();
                    modelView = (ModelView)appel.getDeclaredMethod(laMethode).invoke(objectC);
                    return modelView;
                }
            }
        return modelView;
    }

}

