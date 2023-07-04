/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Emp;
import utilitaire.ModelView;
import annotation.Annotation;
import utilitaire.Url;
/**
 *
 * @author mahery
 */
public class Emp {
    String nom;
    Date dtn;
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Emp(String nom,Date dtn) {
        this.nom = nom;
        this.dtn = dtn;
    }
    
    @Url(url="get-all")
    public ModelView getAll(){
        ModelView modelView = new ModelView("listeEmp.jsp");
        List<Emp> employes = new ArrayList<>();
        Emp emp1 = new Emp("Jean", new Date(1997, 7, 1));
        Emp emp2 = new Emp("Jack", new Date(1994, 3, 26));
        employes.add(emp1);
        employes.add(emp2);
        modelView.addItem("allEmploye", employes); 
        return modelView;
    }
    
}
