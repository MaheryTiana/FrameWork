/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu1758.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import annotation.Annotation;
import etu1758.framework.Mapping;
import java.util.Map;
import java.util.List;
import utilitaire.Url;
import utilitaire.ModelView;
import utilitaire.Utilitaire;
// import Model.*;
/**
 *
 * @author mahery
 */
public class Frontservlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    String nomDePackage;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //entrySet -> ampiasaina ao am boucle angalana an le clef sy valeur     
            out.println("You are being redirected to FRONTSERVLET");

            ModelView modelView = (ModelView)Utilitaire.modelDeRedirection(request, mappingUrls);
            RequestDispatcher dispat = request.getRequestDispatcher(modelView.getVueRedirection());

            HashMap<String, Object> data = modelView.getData();                                    // Get all data of the mv

            if(data != null){
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    out.println(key + " - "+ value);
                    List<Emp> employe = (List<Emp>)value;
                    for (int i = 0; i < employe.size(); i++) {
                        out.println(employe.get(i).getNom());
                    }
                    request.setAttribute(key, value);
                }
            }

            dispat.forward(request, response); 
            /*  for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
                String clef = entry.getKey();// clef
                Mapping map = entry.getValue(); // valeur
                out.println("L' annotation: " + clef + " de valeur " + map.getClassName() + " de fonction appelée "
                        + map.getMethod());
            } */
        //mappingUrls.entrySet();    
        } catch (Exception e) {
            out.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void init() throws ServletException {
        this.setNomDePackage(this.getInitParameter("packageDeScan"));
        try {
            System.out.println(this.getNomDePackage() + " nom de package");
            setMappingUrls(Utilitaire.getMethodesAnnotees(this.getNomDePackage(), Url.class));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }

    public String getNomDePackage() {
        return nomDePackage;
    }

    public void setNomDePackage(String nomDePackage) {
        this.nomDePackage = nomDePackage;
    }


}
