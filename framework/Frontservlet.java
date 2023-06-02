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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import annotation.Annotation;
import etu1758.framework.Mapping;
import java.util.Map;
import utilitaire.Url;
import utilitaire.Utilitaire;

/**
 *
 * @author mahery
 */
public class Frontservlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls = new HashMap<>();
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    public void init()throws ServletException {
        String path = Frontservlet.class.getClassLoader().getResource("").getPath();   
        File files=new File(path);
        File[] dir=files.listFiles();
        Utilitaire fun = new Utilitaire();
        for (File pack : dir) {
            try{
                String packName = pack.getName();
                System.out.println("packName:: "+packName);
                Vector<Class> vc = fun.getAllClassInPackage(path, packName);
                
                
                // out.println("<h1>Url:: "+path  +" / "+vc.size()+ "</h1>");
                for (Class c : vc) {
                    
                    System.out.println("class:: "+c.getName() );
                    Method[] method=c.getDeclaredMethods();
                    
                    for (Method m : method) {
                        System.out.println("method:: "+m.getName());
                        if (m.isAnnotationPresent(Url.class)) {
                            System.out.println("annotation present,class ::"+c.getName());
                            Url urlMapping=(Url)m.getAnnotation(Url.class);
                            Mapping mapping = new Mapping();
                            mapping.setClassName(c.getName());
                            mapping.setMethod(m.getName());
                            MappingUrls.put(urlMapping.url(), mapping);
                        }
                    }
                    
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                System.out.println("error occured :: "+e.getMessage());
                e.printStackTrace();

            }
        }

        



    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Utilitaire fun = new Utilitaire();
        String path = Frontservlet.class.getClassLoader().getResource("").getPath();
        PrintWriter out = response.getWriter(); 
        String[] ans = fun.getUrl(url,request.getContextPath() );
        for (String a : ans) {
            
            out.println("ans :: "+a);
        }
        File files=new File(path);
        File[] dir=files.listFiles();
      //  for (File pack : dir) {
            try{
          
                for (Map.Entry<String, Mapping> entry : MappingUrls.entrySet()) {
                    if (entry.getKey().equals(ans[0])) {
                        out.println("hashmap:: "+entry.getKey()+" // "+entry.toString());
                    }
                
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                out.println("error occured :: "+e.getMessage());
                e.printStackTrace();

            }
        //}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String allURL = request.getRequestURL().toString();
        try {
            processRequest(request, response,allURL);
        } catch (Exception e) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String allURL = request.getRequestURL().toString();
        try {
            processRequest(request, response,allURL);
        } catch (Exception e) {
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
