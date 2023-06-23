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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.text.Utilities;

import org.jcp.xml.dsig.internal.dom.Utilitaireils;

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
import Model.*;
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
            // entrySet -> ampiasaina ao am boucle angalana an le clef sy valeur
            out.println("You are being redirected to FRONTsERVLET");

            Mapping map = this.getMapping(request);
            Object obj = Class.forName(map.getClassName()).newInstance();
            sendData(request, obj);

            Method method = getMethod(map, obj);
            ModelView modelView = (ModelView) getModelView(request, map, obj);
            addData(request, modelView);
            RequestDispatcher dispat = request.getRequestDispatcher(modelView.getVueRedirection());

            System.out.println(modelView.getVueRedirection() + " VUE DE REDIRECTION");

            dispat.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void addData(HttpServletRequest request, ModelView modelView) {
        Map<String, String[]> donneesJSP;
        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            System.out.println("NILA ARGUMENTS");
            donneesJSP = request.getParameterMap();

            // out.println(donneesJSP.toString() + " donneesJSP");
            for (String parameterName : donneesJSP.keySet()) {
                String[] values = donneesJSP.get(parameterName);
                modelView.addItem(parameterName, donneesJSP.get(parameterName)[0]);
            }
        } else {
            System.out.println("TSY NILA ARGUMENTS");
        }
        for (Map.Entry<String, Object> obj : modelView.getData().entrySet()) {
            request.setAttribute(obj.getKey(), obj.getValue());
        }
    }

    public void sendData(HttpServletRequest request, Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String value = request.getParameter(field.getName());
            if (value != null) {
                field.setAccessible(true);
                Class castValue = (Class<?>) field.getType();
                field.set(obj, Utilitaire.cast(value, castValue));
            }
        }
    }

    public String[] avoirLaListeArguments(Method methodCalled) {
        String[] thoseAre = null;
        Arguments argumentsAnnotation = methodCalled.getAnnotation(Arguments.class);

        // Vérifiez si l'annotation existe
        if (argumentsAnnotation != null) {
            // Obtenez les valeurs de arguments()
            thoseAre = argumentsAnnotation.arguments();
        }
        return thoseAre;
    }

    // Prendre Mapping : Class,Methode,Argument correspondant de L'URL
    public Mapping getMapping(HttpServletRequest request) throws Exception {
        String servletPath = request.getServletPath();
        String[] path = servletPath.split("/");
        System.out.println(path[1]);
        for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
            String clef = entry.getKey();
            String method = entry.getValue().getMethod();
            if (path[1].compareToIgnoreCase(clef) == 0) {
                System.out.println(entry.getValue() + " - " + entry.getValue().getMethod());
                return entry.getValue();
            }
        }
        throw new Exception("URL INTROUVABLE");
    }

    public Object[] getArgumentsDeMethode(HttpServletRequest request, Method methodCalled) throws Exception {
        // Prendre les arguments de la méthode
        System.out.println(methodCalled + " methodCalled");
        Parameter[] parameters = methodCalled.getParameters();
        Object[] argumentsDeMethode = new Object[parameters.length];
        String[] listeArguments = avoirLaListeArguments(methodCalled);
        for (int i = 0; i < argumentsDeMethode.length; i++) {
            // prendre la type de la classe de chaque argument
            Class typeArguments = parameters[i].getType();
            // Avadika string le valeur an le paramètre tany am le méthode
            String value = request.getParameter(listeArguments[i]);
            System.out.println(parameters[i].getName() + " parameters[i].getName()");
            // Avadika ho le type originaleny amzay le izy aveo
            argumentsDeMethode[i] = Utilitaire.cast(value, typeArguments);
            System.out.println(argumentsDeMethode[i] + " argumentsDeMethode[i]");
        }
        return argumentsDeMethode;
    }

    public Method getMethod(Mapping map, Object obj) throws Exception {
        return obj.getClass().getDeclaredMethod(map.getMethod(), map.getArguments());
    }

    public ModelView getModelView(HttpServletRequest request, Mapping map, Object obj) throws Exception {

        Method method = getMethod(map, obj);
        method.setAccessible(true);
        Object[] argument = getArgumentsDeMethode(request, method);
        ModelView mv = (ModelView) method.invoke(obj, argument);
        return mv;

    }

    public void init() throws ServletException {
        this.setNomDePackage(this.getInitParameter("packageDeScan"));
        try {
            System.out.println(this.getNomDePackage() + " nom de package");
            setMappingUrls(Utilitaire.getMethodesAnnotees(this.getNomDePackage(), Url.class));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Frontservlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Frontservlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Frontservlet.class.getName()).log(Level.SEVERE, null, ex);
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
