/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import utilitaire.ModelView;
import annotation.Annotation;
import utilitaire.Url;
/**
 *
 * @author mahery
 */
public class Emp {
    int id;
    String nom;
    String prenom;
    Date date_de_naissance;
    public Emp(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Emp() {
    }
    public Emp(int id, String nom, String prenom, Date date_de_naissance) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);;
        this.setDate_de_naissance(date_de_naissance);;
    }
    @Url(method = "emp-jsp")
    public ModelView methodeAAnnoter(){
        ModelView modelView = new ModelView("Emp.jsp");
        return modelView;
    }
    @Url(method = "get-emp")
    public ModelView getAllEmp(){
        ModelView modelView = new ModelView("listeEmp.jsp");
        List<Emp> Emps = new ArrayList<>();
        Emp Emp1 = new Emp(1,"Jeon", "JK", new Date(1997, 7, 1));
        Emp Emp2 = new Emp(2,"Wang", "Jackson", new Date(1994, 3, 26));
        Emp Emp3 = new Emp(3,"Tuan", "Mark", new Date(1995, 12, 30));  
        Emps.add(Emp1);
        Emps.add(Emp2);
        Emps.add(Emp3);
        for (int i = 0; i < Emps.size(); i++) {
            System.out.println(Emps.get(i).getNom());
        }
        modelView.addItem("allEmp", Emps); 
        return modelView;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDate_de_naissance() {
        return date_de_naissance;
    }
    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }
}
